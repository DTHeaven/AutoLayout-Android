package im.quar.autolayout.attr;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by DTHeaven on 15/12/27.
 */
public class MaxWidth extends AutoAttr<View> {
    public MaxWidth(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.MAX_WIDTH;
    }

    @Override
    protected void execute(View view, int val) {
        if (view instanceof TextView) {
            ((TextView) view).setMaxWidth(val);
        } else if (view instanceof ProgressBar) {
            try {
                Field field = view.getClass().getDeclaredField("mMaxWidth");
                field.setAccessible(true);
                field.set(view, val);
            } catch (Exception e) {
            }
        } else {
            try {
                Method method = view.getClass().getMethod("setMaxWidth", int.class);
                method.invoke(view, val);
            } catch (Exception e) {
            }
        }
    }
}
