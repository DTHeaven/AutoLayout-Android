package im.quar.autolayout.sample;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridLayout;

import im.quar.autolayout.AutoLayout;
import im.quar.autolayout.AutoLayoutInfo;
import im.quar.autolayout.utils.AutoLayoutHelper;

/**
 * Created by DTHeaven on 15/12/14.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@AutoLayout
public class AutoGridLayout extends GridLayout {
    private AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoGridLayout(Context context) {
        super(context);
    }

    public AutoGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) mHelper.adjustChildren();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isInEditMode()) mHelper.applyAspectRatio();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends GridLayout.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
            mAutoLayoutInfo.fillAttrs(this);
        }

        public LayoutParams(Spec rowSpec, Spec columnSpec) {
            super(rowSpec, columnSpec);
        }

        public LayoutParams() {
            super();
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo() {
            return mAutoLayoutInfo;
        }
    }
}
