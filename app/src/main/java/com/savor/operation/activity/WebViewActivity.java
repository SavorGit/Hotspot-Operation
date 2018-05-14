package com.savor.operation.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.utils.ConstantValues;
import com.savor.operation.utils.STIDUtil;

public class WebViewActivity extends BaseActivity implements View.OnClickListener{

    private String TAG = "GuideActivity";
    private ImageView iv_left;
    private TextView tv_center;
    private WebView mCustomWebView;
    private String webUrl = ConstantValues.H5_BASE_URL;
    //private String webUrl = "http://www.baidu.com";

    public static final int RECORD = 10;
    public static final int SUBJECT = 20;
    private int currentH5 = 0;
    private ImageView iv_right;
    private ImageView iv_left_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mContext = this;
        getViews();
        setViews();
        setListeners();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onBackPressed();
                break;
        }
    }

    @Override
    public void getViews() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        iv_left_b = (ImageView) findViewById(R.id.iv_left_b);
        tv_center = (TextView)findViewById(R.id.tv_center);
        iv_left = (ImageView)findViewById(R.id.iv_left);
        mCustomWebView = (WebView) findViewById(R.id.webview_custom);
    }

    @Override
    public void setViews() {
       // mCustomWebView.loadUrl(webUrl,null);
        tv_center.setText("排行榜");
        webUrl = "http://admin.littlehotspot.com/optionh5/index?userid="+mSession.getLoginResponse().getUserid();
        mCustomWebView.loadUrl(webUrl);
    }

    @Override
    public void setListeners() {
        iv_left.setOnClickListener(this);
    }
}
