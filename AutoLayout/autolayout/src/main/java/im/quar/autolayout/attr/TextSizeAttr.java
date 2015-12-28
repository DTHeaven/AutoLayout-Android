package im.quar.autolayout.attr;

import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by DTHeaven on 15/12/4.
 */
public class TextSizeAttr extends AutoAttr<TextView> {

    public TextSizeAttr(int pxVal) {
        super(pxVal);
    }

    @Override
    protected int attrVal() {
        return Attrs.TEXT_SIZE;
    }

    @Override
    protected void execute(TextView textView, int val) {
        textView.setIncludeFontPadding(false);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, val);
    }


}
