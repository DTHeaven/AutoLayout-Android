package im.quar.autolayout.attr;

import android.view.ViewGroup;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class WidthAttr extends AutoAttr<ViewGroup.LayoutParams> {
    public WidthAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.WIDTH;
    }

    @Override
    protected void execute(ViewGroup.LayoutParams lp, int val) {
        lp.width = val;
    }
}
