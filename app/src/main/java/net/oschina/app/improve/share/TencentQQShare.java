package net.oschina.app.improve.share;

import android.os.Bundle;
import android.text.TextUtils;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import net.oschina.app.R;
import net.oschina.app.improve.widget.SimplexToast;


/**
 * QZoneShare
 * Created by huanghaibin on 2017/6/12.
 */
@SuppressWarnings("unused")
public class TencentQQShare extends BaseShare implements IUiListener {

    private static final String APP_ID = "100942993";
    private static final String APP_KEY = "8edd3cc7ca8dcc15082d6fe75969601b";

    private Tencent tencent;

    TencentQQShare(Builder mBuilder) {
        super(mBuilder);
        tencent = Tencent.createInstance(APP_ID, mBuilder.mActivity.getApplicationContext());
    }

    @Override
    public boolean share() {
        if (mBuilder.isShareImage && mBuilder.bitmap != null) {
            shareImage();
        } else {
            tencent.shareToQQ(mBuilder.mActivity, initShare(), this);
        }
        return true;
    }

    @Override
    public void shareImage() {
        String url = saveImage(mBuilder.bitmap);
        if (TextUtils.isEmpty(url)) return;
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mBuilder.title);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        tencent.shareToQQ(mBuilder.mActivity, params, this);
    }

    private Bundle initShare() {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, mBuilder.title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mBuilder.content);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mBuilder.url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mBuilder.imageUrl);
        params.putInt(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, R.mipmap.ic_share_app_logo);
        if (mBuilder.isShareApp) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mBuilder.title);
            params.putInt(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, R.mipmap.ic_share_app_logo);
        }
        return params;
    }


    @Override
    public void onComplete(Object o) {
        SimplexToast.show(mBuilder.mActivity, "成功分享");
    }

    @Override
    public void onError(UiError uiError) {
        SimplexToast.show(mBuilder.mActivity, "分享失败");
    }

    @Override
    public void onCancel() {
        SimplexToast.show(mBuilder.mActivity, "分享取消");
    }
}
