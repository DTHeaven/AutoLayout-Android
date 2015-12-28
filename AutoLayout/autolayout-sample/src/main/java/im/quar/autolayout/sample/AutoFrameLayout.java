package im.quar.autolayout.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import im.quar.autolayout.AutoLayout;

/**
 * Created by DTHeaven on 15/12/14.
 */
@AutoLayout
public class AutoFrameLayout extends FrameLayout{
    public AutoFrameLayout(Context context) {
        super(context);
    }

    public AutoFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
