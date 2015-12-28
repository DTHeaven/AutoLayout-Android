package im.quar.autolayout.attr;

import android.view.View;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class PaddingLeftAttr extends AutoAttr<View> {
    public PaddingLeftAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.PADDING_LEFT;
    }

    @Override
    protected void execute(View view, int val) {
        int l = val;
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();
        view.setPadding(l, t, r, b);

    }
}
