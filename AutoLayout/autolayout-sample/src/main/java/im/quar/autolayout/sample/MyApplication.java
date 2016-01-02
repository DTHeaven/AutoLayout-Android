package im.quar.autolayout.sample;

import android.app.Application;

import im.quar.autolayout.ScaleAdapter;
import im.quar.autolayout.config.AutoLayoutConfig;
import im.quar.autolayout.utils.ScreenUtils;

/**
 * Created by DTHeaven on 16/1/2.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AutoLayoutConfig.init(this, new ScaleAdapter() {
            @Override
            public float adapt(float scale, int screenWidth, int screenHeight) {
                //Do yourself adaption here.
                if (screenWidth < 720 || screenHeight < 720) {//Small screen device
                    if (screenWidth <= 480 || screenHeight <= 480) {//480p
                        scale *= 1.2f;
                    } else {
                        if (ScreenUtils.getDevicePhysicalSize(getApplicationContext()) < 4.0) {//Meizu mx
                            scale *= 1.3f;
                        } else {//HUAWEI U9200
                            scale *= 1.05f;
                        }
                    }
                }
                return scale;
            }
        });
    }
}
