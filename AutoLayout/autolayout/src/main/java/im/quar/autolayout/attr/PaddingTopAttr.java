package im.quar.autolayout.attr;

import android.view.View;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class PaddingTopAttr extends AutoAttr<View> {
    public PaddingTopAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.PADDING_TOP;
    }

    @Override
    protected void execute(View view, int val) {
        int l = view.getPaddingLeft();
        int t = val;
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();
        view.setPadding(l, t, r, b);

    }
}
