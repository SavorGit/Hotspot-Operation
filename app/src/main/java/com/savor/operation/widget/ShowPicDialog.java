package com.savor.operation.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.common.api.utils.DensityUtil;
import com.savor.operation.R;
import com.savor.operation.widget.imageshow.ImageShowViewPager;
import com.savor.operation.widget.imageshow.TouchImageView;

/**
 * Created by hezd on 2016/12/26.
 */

public class ShowPicDialog extends Dialog implements View.OnClickListener {

    private String mImageUrl;
    private OnTakePhotoBtnClickListener onTakePhotoBtnClickListener;
    private OnAlbumBtnClickListener onAlbumBtnClickListener;
    private boolean iscolor = false;
    private PinchImageView mImageView;
    private ProgressBar mLoadingPb;

    public ShowPicDialog(Context context) {
        super(context, R.style.loading_dialog);
    }

    public ShowPicDialog(Context context,String image_url) {
        super(context, R.style.ShowPicDialogStyle);
        this.mImageUrl = image_url;
    }

    public ShowPicDialog(Context context, OnTakePhotoBtnClickListener onTakePhotoBtnClickListener, OnAlbumBtnClickListener onAlbumBtnClickListener) {
        super(context, R.style.ShowPicDialogStyle);
        this.onTakePhotoBtnClickListener = onTakePhotoBtnClickListener;
        this.onAlbumBtnClickListener = onAlbumBtnClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_pic);
        getViews();
        setViews();
        setListeners();
    }

    private void setListeners() {
        mImageView.setOnClickListener(this);
    }

    private void setViews() {
        ViewGroup.LayoutParams layoutParams = mImageView.getLayoutParams();
        layoutParams.width = DensityUtil.getScreenWidth(getContext());
        layoutParams.height = DensityUtil.getScreenHeight(getContext())-DensityUtil.getStatusBarHeight(getContext());
        Glide.with(getContext()).load(mImageUrl).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mLoadingPb.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mLoadingPb.setVisibility(View.GONE);
                return false;
            }
        }).placeholder(R.drawable.kong_mrjz).into(mImageView);
    }

    private void getViews() {
        mImageView = (PinchImageView) findViewById(R.id.iv_image);
        mLoadingPb = (ProgressBar) findViewById(R.id.progressBar);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_image:
                dismiss();
                break;
        }
    }

    public interface OnTakePhotoBtnClickListener {
        void onTakePhotoClick();
    }

    public interface OnAlbumBtnClickListener {
        void onAlbumBtnClick();
    }
}
