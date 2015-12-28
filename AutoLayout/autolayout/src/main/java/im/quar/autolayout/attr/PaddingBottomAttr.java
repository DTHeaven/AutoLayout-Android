package im.quar.autolayout.attr;

import android.view.View;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class PaddingBottomAttr extends AutoAttr<View> {
    public PaddingBottomAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.PADDING_BOTTOM;
    }

    @Override
    protected void execute(View view, int val) {
        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = val;
        view.setPadding(l, t, r, b);

    }
}
