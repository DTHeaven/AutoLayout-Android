package im.quar.autolayout.utils;

import android.view.View;
import android.view.ViewGroup;

import im.quar.autolayout.config.AutoLayoutConfig;


/**
 * Created by DTHeaven on 15/12/4.
 */
public class AutoUtils {

    /**
     * 会直接将view的LayoutParams上设置的width，height直接进行百分比处理
     *
     * @param view
     */
    public static void auto(View view) {
        autoSize(view);
        autoPadding(view);
        autoMargin(view);
    }

    public static void autoMargin(View view) {
        if (!(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams))
            return;

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp == null) return;

        lp.leftMargin = scaleValue(lp.leftMargin);
        lp.topMargin = scaleValue(lp.topMargin);
        lp.rightMargin = scaleValue(lp.rightMargin);
        lp.bottomMargin = scaleValue(lp.bottomMargin);

    }

    public static void autoPadding(View view) {
        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();

        l = scaleValue(l);
        t = scaleValue(t);
        r = scaleValue(r);
        b = scaleValue(b);

        view.setPadding(l, t, r, b);
    }

    public static void autoSize(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();

        if (lp == null) return;

        if (lp.width > 0) {
            lp.width = scaleValue(lp.width);
        }

        if (lp.height > 0) {
            lp.height = scaleValue(lp.height);
        }
    }

    public static int scaleValue(int val) {
        return (int) (val * AutoLayoutConfig.getInstance().getScale());
    }
}
