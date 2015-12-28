package im.quar.autolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by DTHeaven on 15/12/14.
 */
public interface ViewCreator {
    View create(String name, Context context, AttributeSet attrs);
}
