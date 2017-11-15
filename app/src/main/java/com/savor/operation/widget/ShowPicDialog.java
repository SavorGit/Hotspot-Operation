package com.savor.operation.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.api.utils.DensityUtil;
import com.savor.operation.R;
import com.savor.operation.widget.imageshow.ImageShowViewPager;

/**
 * Created by hezd on 2016/12/26.
 */

public class ShowPicDialog extends Dialog implements View.OnClickListener {

    private String mImageUrl;
    private OnTakePhotoBtnClickListener onTakePhotoBtnClickListener;
    private OnAlbumBtnClickListener onAlbumBtnClickListener;
    private boolean iscolor = false;
    private ImageView mImageView;

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
    }

    private void setViews() {
        ViewGroup.LayoutParams layoutParams = mImageView.getLayoutParams();
        layoutParams.width = DensityUtil.getScreenWidth(getContext());
        layoutParams.height = DensityUtil.dip2px(getContext(),200);
        Glide.with(getContext()).load(mImageUrl).centerCrop().into(mImageView);
    }

    private void getViews() {
        mImageView = (ImageView) findViewById(R.id.iv_image);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public interface OnTakePhotoBtnClickListener {
        void onTakePhotoClick();
    }

    public interface OnAlbumBtnClickListener {
        void onAlbumBtnClick();
    }
}
