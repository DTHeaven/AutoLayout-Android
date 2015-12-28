package im.quar.autolayout.attr;

import android.view.ViewGroup;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class MarginAttr extends AutoAttr<ViewGroup.MarginLayoutParams> {
    public MarginAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.MARGIN;
    }

    @Override
    protected void execute(ViewGroup.MarginLayoutParams lp, int val) {
        lp.leftMargin = lp.rightMargin = lp.topMargin = lp.bottomMargin = val;
    }
}
