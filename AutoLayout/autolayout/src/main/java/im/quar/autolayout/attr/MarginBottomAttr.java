package im.quar.autolayout.attr;

import android.view.ViewGroup;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class MarginBottomAttr extends AutoAttr<ViewGroup.MarginLayoutParams> {
    public MarginBottomAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.MARGIN_BOTTOM;
    }

    @Override
    protected void execute(ViewGroup.MarginLayoutParams lp, int val) {
        lp.bottomMargin = val;
    }
}
