package com.savor.operation.core;

/*
 * Copyright (C) 2010 mAPPn.Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;

import com.common.api.utils.DesUtils;
import com.common.api.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.savor.operation.bean.BindBoxList;
import com.savor.operation.bean.BindBoxListBean;
import com.savor.operation.bean.BindBoxResponse;
import com.savor.operation.bean.BoxInfo;
import com.savor.operation.bean.DamageConfig;
import com.savor.operation.bean.ErrorDetail;
import com.savor.operation.bean.ErrorReport;
import com.savor.operation.bean.ExeUserList;
import com.savor.operation.bean.ExecutorInfo;
import com.savor.operation.bean.FixHistoryResponse;
import com.savor.operation.bean.HotelListResponse;
import com.savor.operation.bean.HotelMacInfo;
import com.savor.operation.bean.IndexInfo;
import com.savor.operation.bean.LoginResponse;
import com.savor.operation.bean.MissionTaskListBean;
import com.savor.operation.bean.PositionListInfo;
import com.savor.operation.bean.RepairRecordList;
import com.savor.operation.bean.SmallPlatformByGetIp;
import com.savor.operation.bean.Task;
import com.savor.operation.bean.TaskDetail;
import com.savor.operation.bean.TaskNum;
import com.savor.operation.bean.TaskListBean;
import com.savor.operation.bean.UserBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

/**
 * API 响应结果解析工厂类，所有的API响应结果解析需要在此完成。
 *
 * @author andrew
 * @date 2011-4-22
 */
public class ApiResponseFactory {
    public final static String TAG = "ApiResponseFactory";
    // 当前服务器时间
    private static String webtime = "";

    public static Object getResponse(Context context, AppApi.Action action,
                                     Response response, String key, boolean isCache, String payType) {
        //转换器

        String requestMethod = "";
        Object result = null;
        boolean isDes = false;
        Session session = Session.get(context);
        String jsonResult = null;
        try {
            jsonResult = (String) response.body().string();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (jsonResult == null) {
            return null;
        }
        // long start = System.currentTimeMillis();
        String header = response.header("des");
        if (header != null && Boolean.valueOf(header)) {
            isDes = true;
        }
        if (isDes) {
            jsonResult = DesUtils.decrypt(jsonResult);
        }
        LogUtils.i("action:"+action.toString()+",jsonResult:" + jsonResult);
        JSONObject rSet;
        JSONObject info = null;
        JSONArray infoArray = null;
        String infoJson = "";
        ResponseErrorMessage error;
        try {
            rSet = new JSONObject(jsonResult);
//
            if (rSet.has("status")) {// cms平台处理
                int code = rSet.getInt("status");
                if (AppApi.CMS_RESPONSE_STATE_SUCCESS == code) {
                    try {
                        if(rSet.has("content")) {
                            info = rSet.getJSONObject("content");
                            infoJson = info.toString();
                        }else if(rSet.has("result")){
                            infoJson = rSet.getString("result");
                        }else {
                            infoJson = "";
                        }

                    } catch (JSONException ex) {
                        try {
                            infoArray = rSet.getJSONArray("content");
                            infoJson = infoArray.toString();
                        } catch (JSONException e) {
                            try {
                                infoJson = rSet.getString("content");
                            } catch (Exception e2) {
//								infoJson = null;
                                infoJson = rSet.toString();
                            }

                        }
                    }

                } else {
                    try {
//				    	info = rSet.getJSONObject("result");
                        if (rSet.has("result")) {
                            String msg = rSet.getString("result");
                            error = new ResponseErrorMessage();
                            error.setCode(code);
                            error.setMessage(msg);
                            error.setJson(jsonResult);
                            return error;
                        }
//				    	infoJson=info.toString();
                    } catch (JSONException ex) {
                        try {
                            String msg = rSet.getString("result");
                            error = new ResponseErrorMessage();
                            error.setCode(code);
                            error.setMessage(msg);
                            error.setJson(jsonResult);
                            return error;
                        } catch (JSONException e) {
                            try {
                                infoJson = rSet.getString("content");
                            } catch (Exception e2) {
                                LogUtils.d(e.toString());
                            }

                        }
                    }
                }
            }else if(rSet.has("code")) {// 小平台和云平台处理
                int code = rSet.getInt("code");
                if (AppApi.CLOUND_RESPONSE_STATE_SUCCESS == code) {
                    try {

                        info = rSet.getJSONObject("result");
                        infoJson = info.toString();

                    } catch (JSONException ex) {
                        try {
                            infoArray = rSet.getJSONArray("result");
                            infoJson = infoArray.toString();
                        } catch (JSONException e) {
                            try {
                                infoJson = rSet.getString("result");
                            } catch (Exception e2) {
//								infoJson = null;
                                infoJson = rSet.toString();
                            }

                        }
                    }

                    /**缓存返回数据包*/
//					if(isCache){
//						String serverKey = response.getFirstHeader("key").getValue();
//						String webtimeKey=response.getFirstHeader("webtime").getValue();
//						HttpCacheManager.getInstance(context).saveCacheData(key, serverKey,webtimeKey, infoJson);
//					}
                } else {
                    try {
//				    	info = rSet.getJSONObject("result");
                        if (rSet.has("msg")) {
                            String msg = rSet.getString("msg");
                            error = new ResponseErrorMessage();
                            error.setCode(code);
                            error.setMessage(msg);
                            error.setJson(jsonResult);
                            return error;
                        }
//				    	infoJson=info.toString();
                    } catch (JSONException ex) {
                        try {
                            String msg = rSet.getString("msg");
                            error = new ResponseErrorMessage();
                            error.setCode(code);
                            error.setMessage(msg);
                            error.setJson(jsonResult);
                            return error;
                        } catch (JSONException e) {
                            try {
                                infoJson = rSet.getString("msg");
                            } catch (Exception e2) {
                                LogUtils.d(e.toString());
                            }

                        }
                    }
                }
            }
            result = parseResponse(action, infoJson, rSet, payType);
        } catch (Exception e) {
            LogUtils.d(requestMethod + " has other unknown Exception", e);
            e.printStackTrace();
        }finally {
            response.body().close();
        }

        return result;
    }

    public static Object parseResponse(AppApi.Action action, String info, JSONObject ret, String payType) {
        Object result = null;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
//		LogUtils.i("info:-->" + info);
        if (info == null) {
            return result;
        }
        switch (action) {
            case TEST_POST_JSON:
                System.out.println(info);
                break;
            case TEST_GET_JSON:
                System.out.println(info);
                break;
            case POST_LOGIN_JSON:
                result = new Gson().fromJson(info, LoginResponse.class);
                break;
            case POST_INDEX_JSON:
                result = new Gson().fromJson(info, IndexInfo.class);
                break;
            case POST_REPAIR_USER_JSON:
                result = gson.fromJson(info, new TypeToken<List<UserBean>>() {
                }.getType());
                break;
            case POST_SEARCH_HOTEL_JSON:
                result = gson.fromJson(info, HotelListResponse.class);
                break;
            case POST_ERROR_REPORT_LIST_JSON:
                result = gson.fromJson(info, new TypeToken<ErrorReport>() {
                }.getType());
                break;
            case POST_ERROR_REPORT_DETAIL_JSON:
                result = gson.fromJson(info, new TypeToken<ErrorDetail>() {
                }.getType());
                break;
            case POST_REPAIR_RECORD_LIST_JSON:
                result = gson.fromJson(info, new TypeToken<RepairRecordList>() {
                }.getType());
                break;
            case POST_FIX_HISTORY_JSON:
                result = gson.fromJson(info, FixHistoryResponse.class);
                break;
            case POST_DAMAGE_CONFIG_JSON:
                result = gson.fromJson(info, DamageConfig.class);
                break;
            case POST_SUBMIT_DAMAGE_JSON:
                result = info;
                break;
            case POST_HOTEL_MACINFO_JSON:
                result = gson.fromJson(info, HotelMacInfo.class);
                break;
            case POST_TASK_TYPE_JSON:
                result = gson.fromJson(info, new TypeToken<List<Task>>() {
                }.getType());
                break;
            case POST_PUBLISH_JSON:
                result = info;
                break;
            case POST_BOX_LIST_JSON:
                result = gson.fromJson(info, new TypeToken<List<BoxInfo>>() {
                }.getType());
                break;
            case POST_TASK_NUM_JSON:
                result = gson.fromJson(info, new TypeToken<TaskNum>() {
                }.getType());
                break;
            case POST_VIEW_TASK_LIST_JSON:
            case POST_APPOINT_TASK_LIST_JSON:
            case POST_EXE_TASK_LIST_JSON:
            case POST_PUB_TASK_LIST_JSON:
                result = gson.fromJson(info, new TypeToken<List<MissionTaskListBean>>() {
                }.getType());
                break;
            case POST_ROOM_BOX_JSON:
                result = gson.fromJson(info, new TypeToken<BindBoxList>() {
                }.getType());
                break;
            case POST_BIND_BOX_JSON:
                result = gson.fromJson(info, new TypeToken<BindBoxResponse>() {
                }.getType());
                break;
            case POST_TASK_DETAIL_JSON:
                result = gson.fromJson(info, new TypeToken<TaskDetail>() {
                }.getType());
                break;
            case POST_REFUSE_TASK_JSON:
                result = "success";
                break;
            case POST_EXE_INFO_JSON:
                result = gson.fromJson(info, new TypeToken<ExecutorInfo>() {
                }.getType());
                break;
            case POST_REPORT_MISSION_JSON:
                result = "success";
                break;
            case POST_EXE_USER_LIST_JSON:
                result = gson.fromJson(info, new TypeToken<List<ExeUserList>>() {
                }.getType());
                break;
            case POST_APPOIN_TASK_JSON:
                result = "success";
                break;
            case GET_IP_JSON:
                result = gson.fromJson(info, new TypeToken<SmallPlatformByGetIp>() {
                }.getType());
                break;
            case POST_POSITION_LIST_JSON:
                result = gson.fromJson(info, PositionListInfo.class);
                break;
            case POST_SINGLESEARCH_HOTEL_JSON:
                result = gson.fromJson(info, HotelListResponse.class);
                break;
            case POST_SINGLE_SUBMIT_DAMAGE_JSON:
                result = info;
                break;
            default:
                break;
        }
        return result;
    }


}