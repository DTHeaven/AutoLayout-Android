package im.quar.autolayout.attr;

import android.view.View;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class PaddingRightAttr extends AutoAttr<View> {
    public PaddingRightAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.PADDING_RIGHT;
    }

    @Override
    protected void execute(View view, int val) {
        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = val;
        int b = view.getPaddingBottom();
        view.setPadding(l, t, r, b);

    }
}
