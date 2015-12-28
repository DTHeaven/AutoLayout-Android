package im.quar.autolayout.config;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import im.quar.autolayout.ScaleAdapter;
import im.quar.autolayout.utils.ScreenUtils;

/**
 * Created by DTHeaven on 15/11/18.
 */
public class AutoLayoutConfig {

    private static AutoLayoutConfig sInstance;

    private static final String KEY_DESIGN_WIDTH = "design_width";
    private static final String KEY_DESIGN_HEIGHT = "design_height";

    private int mScreenWidth;
    private int mScreenHeight;

    private int mDesignWidth;
    private int mDesignHeight;

    private float mScale;

    private AutoLayoutConfig() {
    }

    public static void init(Context context) {
        init(context, new DefaultScaleAdapter(context));
    }

    public static void init(Context context, ScaleAdapter scaleAdapter) {
        if (sInstance == null) {
            sInstance = new AutoLayoutConfig();
            sInstance.initInternal(context, scaleAdapter);
        }
    }

    public static AutoLayoutConfig getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Must init before using.");
        }
        return sInstance;
    }

    private void initInternal(Context context, ScaleAdapter scaleAdapter) {
        getMetaData(context);
        checkParams();

        int[] size = ScreenUtils.getRealScreenSize(context);
        mScreenWidth = size[0];
        mScreenHeight = size[1];

        if (mScreenWidth > mScreenHeight) {//横屏状态下，宽高互换，按竖屏模式计算scale
            mScreenWidth = mScreenWidth + mScreenHeight;
            mScreenHeight = mScreenWidth - mScreenHeight;
            mScreenWidth = mScreenWidth - mScreenHeight;
        }

        float deviceScale = (float) mScreenHeight / mScreenWidth;
        float designScale = (float) mDesignHeight / mDesignWidth;
        if (deviceScale <= designScale) {//高宽比小于等于标准比（较标准屏宽一些），以高为基准计算scale（以短边计算），否则以宽为基准计算scale
            mScale = (float) mScreenHeight / mDesignHeight;
        } else {
            mScale = (float) mScreenWidth / mDesignWidth;
        }

        if (scaleAdapter != null) {
            mScale = scaleAdapter.adapt(mScale, mScreenWidth, mScreenHeight);
        }
    }

    private void getMetaData(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(context
                    .getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                mDesignWidth = (int) applicationInfo.metaData.get(KEY_DESIGN_WIDTH);
                mDesignHeight = (int) applicationInfo.metaData.get(KEY_DESIGN_HEIGHT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(
                    "you must set " + KEY_DESIGN_WIDTH + " and " + KEY_DESIGN_HEIGHT + "  in your manifest file.", e);
        }
    }

    private void checkParams() {
        if (mDesignHeight <= 0 || mDesignWidth <= 0) {
            throw new RuntimeException(
                    "you must set " + KEY_DESIGN_WIDTH + " and " + KEY_DESIGN_HEIGHT + "  in your manifest file.");
        }
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public int getDesignWidth() {
        return mDesignWidth;
    }

    public int getDesignHeight() {
        return mDesignHeight;
    }

    public float getScale() {
        return mScale;
    }
}
