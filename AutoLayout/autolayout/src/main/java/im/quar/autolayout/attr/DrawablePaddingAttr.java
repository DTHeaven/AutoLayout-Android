package im.quar.autolayout.attr;

import android.widget.TextView;

/**
 * Created by DTHeaven on 15/12/24.
 */
public class DrawablePaddingAttr extends AutoAttr<TextView> {

    public DrawablePaddingAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.DRAWABLE_PADDING;
    }

    @Override
    protected void execute(TextView textView, int val) {
        textView.setCompoundDrawablePadding(val);
    }
}
