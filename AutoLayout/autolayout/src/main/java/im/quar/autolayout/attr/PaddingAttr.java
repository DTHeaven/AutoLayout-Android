package im.quar.autolayout.attr;

import android.view.View;

/**
 * Created by DTHeaven on 15/12/5.
 */
public class PaddingAttr extends AutoAttr<View> {
    public PaddingAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.PADDING;
    }

    @Override
    protected void execute(View view, int val) {
        view.setPadding(val, val, val, val);
    }
}
