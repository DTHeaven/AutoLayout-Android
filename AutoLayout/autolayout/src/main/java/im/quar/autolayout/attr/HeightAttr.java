package im.quar.autolayout.attr;

import android.view.ViewGroup;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class HeightAttr extends AutoAttr<ViewGroup.LayoutParams> {
    public HeightAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.HEIGHT;
    }

    @Override
    protected void execute(ViewGroup.LayoutParams lp, int val) {
        lp.height = val;
    }

}
