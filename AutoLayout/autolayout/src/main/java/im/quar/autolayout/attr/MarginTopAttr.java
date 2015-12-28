package im.quar.autolayout.attr;

import android.view.ViewGroup;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class MarginTopAttr extends AutoAttr<ViewGroup.MarginLayoutParams> {
    public MarginTopAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.MARGIN_TOP;
    }

    @Override
    protected void execute(ViewGroup.MarginLayoutParams lp, int val) {
        lp.topMargin = val;
    }
}
