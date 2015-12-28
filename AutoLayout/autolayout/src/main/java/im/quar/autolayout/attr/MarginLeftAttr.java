package im.quar.autolayout.attr;

import android.view.ViewGroup;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class MarginLeftAttr extends AutoAttr<ViewGroup.MarginLayoutParams> {
    public MarginLeftAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.MARGIN_LEFT;
    }

    @Override
    protected void execute(ViewGroup.MarginLayoutParams lp, int val) {
        lp.leftMargin = val;
    }

}
