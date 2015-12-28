package im.quar.autolayout.attr;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by DTHeaven on 15/12/24.
 */
public class MinWidthAttr extends AutoAttr<View> {

    public MinWidthAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.MIN_WIDTH;
    }

    @Override
    protected void execute(View view, int val) {
        view.setMinimumWidth(val);
        if (view instanceof TextView) {
            ((TextView) view).setMinWidth(val);
        } else if (view instanceof ProgressBar) {
            try {
                Field field = view.getClass().getDeclaredField("mMinWidth");
                field.setAccessible(true);
                field.set(view, val);
            } catch (Exception e) {
            }
        }
    }
}
