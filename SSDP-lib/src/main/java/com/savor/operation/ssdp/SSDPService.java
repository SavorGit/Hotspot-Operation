package com.savor.operation.ssdp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


/**
 * 组播发现小平台类
 * 机顶盒ssdp
 * 收到的消息是：Savor-Type:box
 * Savor-HOST:192.168.0.102
 */
public class SSDPService extends IntentService {
    /**
     * 机顶盒组播
     */
    private static final String TYPE_SSDP_BOX = "box";
    private static final int PORT_LISTENING = 11900;
    private static final String IP_TARGET = "238.255.255.250";

    private static final int DATA_RECEIVE_SIZE = 1024;


    private static final String TYPE_LABEL_PREFIX = "Savor-Type:";
    private static final String ID_ROOM_ID_PREFIX = "Savor-Room-ID:";
    private static final String BOX_IP_LABEL_PREFIX = "Savor-Box-HOST:";
    private static final String IP_LABEL_PREFIX = "Savor-HOST:";
    private static final String COMMAND_PORT_LABEL_PREFIX = "Savor-Port-Command:";
    private static final String ID_HOTEL_ID_LABLE_PREFIX = "Savor-Hotel-ID:";
    private static final String CRLF = "\r\n";
    private static final int CLOSE_SERVICE = 0x1;
    private static final int CLOSE_FIRSTUSE_SERVICE = 0x2;


    private MulticastSocket mSocketReceive;

    private WifiManager.MulticastLock multicastLock;
    private boolean isLooping = true;
    private OnSSDPReceivedListener mListener;

    public SSDPService() {
        super("SSDPService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        startReceive();
    }

    private void startReceive() {
        WifiManager wm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        multicastLock = wm.createMulticastLock("multicastLock");
        multicastLock.setReferenceCounted(false);
        multicastLock.acquire();

        mSocketReceive = null;

        try {
            mSocketReceive = new MulticastSocket(PORT_LISTENING);
            mSocketReceive.setLoopbackMode(true);
            mSocketReceive.setTimeToLive(0);
//            mSocketReceive.setSoTimeout(1000*12);
            mSocketReceive.joinGroup(InetAddress.getByName(IP_TARGET));

            String type = null;
            String address = null;
            String boxAddress = null;
            int roomId = 0;
            int hotelId = 0;
            int nettyPort = -1, commandPort = 8080, downloadPort = -1;

            do {
                DatagramPacket packetReceived = new DatagramPacket(new byte[DATA_RECEIVE_SIZE], DATA_RECEIVE_SIZE);
                mSocketReceive.receive(packetReceived);

                final String msgReceived = new String(packetReceived.getData(), 0, packetReceived.getLength()).trim();

                if (msgReceived.length() > 0) {
                    // 解析并保存小平台信息到Session
                    type = parseStringMetadata(msgReceived, TYPE_LABEL_PREFIX);
                    address = parseStringMetadata(msgReceived, IP_LABEL_PREFIX);
                    hotelId = parseIntMetadata(msgReceived, ID_HOTEL_ID_LABLE_PREFIX);
                    roomId = parseIntMetadata(msgReceived, ID_ROOM_ID_PREFIX);
                }

                if(hotelId>0&&roomId>0) {
                    if(mListener!=null) {
                        mListener.onSSDPReceivedListener(address,boxAddress,hotelId,roomId);
                        isLooping = false;
                    }
                }

            } while (isLooping);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSocketReceive();
        }

        multicastLock.release();
    }

    private void closeSocketReceive() {
        if (mSocketReceive != null && !mSocketReceive.isClosed()) {
            mSocketReceive.close();
        }
    }

    private int parseIntMetadata(String data, String labelPrefix) {
        int metadata = -1;
        if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(labelPrefix)) {
            // Label开始
            int startIndex = data.indexOf(labelPrefix) + labelPrefix.length();
            // Label以换行结束时换行符的位置，endIndex可能是该项为message最末尾
            int endIndex = data.indexOf(CRLF, startIndex);
            if (startIndex >= 0 && (endIndex > startIndex || endIndex == -1)) {
                try {
                    if (endIndex == -1) {
                        metadata = Integer.parseInt(data.substring(startIndex).trim());
                    } else {
                        metadata = Integer.parseInt(data.substring(startIndex, endIndex).trim());
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        return metadata;
    }

    private String parseStringMetadata(String data, String labelPrefix) {
        String metadata = null;
        if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(labelPrefix) && data.indexOf(labelPrefix) != -1) {
            // Label开始
            int startIndex = data.indexOf(labelPrefix) + labelPrefix.length();
            // Label以换行结束时换行符的位置，endIndex可能是该项为message最末尾
            int endIndex = data.indexOf(CRLF, startIndex);
            if (startIndex >= 0 && (endIndex > startIndex || endIndex == -1)) {
                metadata = data.substring(startIndex, endIndex).trim();
            }
        }
        return metadata;
    }

    @Override
    public void onDestroy() {
        isLooping = false;
        super.onDestroy();
    }

    public void setOnSSDPReceivedListener(OnSSDPReceivedListener listener) {
        this.mListener = listener;
    }

    public interface OnSSDPReceivedListener {
        void onSSDPReceivedListener(String address ,String boxAddress ,int hotelId,int roomId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SSDPBinder();
    }

    public class SSDPBinder extends Binder {
        /**
         * 获取当前Service的实例
         * @return
         */
        public SSDPService getService(){
            return SSDPService.this;
        }
    }
}