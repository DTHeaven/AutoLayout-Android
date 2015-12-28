package im.quar.autolayout.attr;

import android.view.ViewGroup;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class MarginRightAttr extends AutoAttr<ViewGroup.MarginLayoutParams> {
    public MarginRightAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.MARGIN_RIGHT;
    }

    @Override
    protected void execute(ViewGroup.MarginLayoutParams lp, int val) {
        lp.rightMargin = val;
    }
}
