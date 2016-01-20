package im.quar.autolayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

import im.quar.autolayout.AutoLayoutInfo;
import im.quar.autolayout.utils.AutoLayoutHelper;

/**
 * Created by DTHeaven on 15/12/14.
 */
public class AutoListView extends ListView {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoListView(Context context) {
        super(context);
    }

    public AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) mHelper.adjustChildren();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isInEditMode()) mHelper.applyAspectRatio();
    }

    public static class LayoutParams extends ListView.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
            mAutoLayoutInfo.fillAttrs(this);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo() {
            return mAutoLayoutInfo;
        }

    }
}
