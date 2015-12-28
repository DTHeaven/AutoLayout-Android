package im.quar.autolayout.config;

import android.content.Context;

import im.quar.autolayout.ScaleAdapter;
import im.quar.autolayout.utils.ScreenUtils;

/**
 * Created by DTHeaven on 15/12/16.
 */
public class DefaultScaleAdapter implements ScaleAdapter {

    private Context mContext;

    public DefaultScaleAdapter(Context context) {
        mContext = context;
    }

    @Override
    public float adapt(float scale, int screenWidth, int screenHeight) {
        if (screenWidth < 720 || screenHeight < 720) {//针对小屏（小分辨率）设备做调整
            if (screenWidth <= 480 || screenHeight <= 480) {//普通480设备
                scale *= 1.2f;
            } else {
                if (ScreenUtils.getDevicePhysicalSize(mContext) < 4.0) {//小屏手机，较高分辨率（如 mx）
                    scale *= 1.3f;
                } else {//华为U9200
                    scale *= 1.05f;
                }
            }
        }
        return scale;
    }
}
