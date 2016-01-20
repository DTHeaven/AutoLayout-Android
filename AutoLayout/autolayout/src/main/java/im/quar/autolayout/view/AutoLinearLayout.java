package im.quar.autolayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import im.quar.autolayout.AutoLayoutInfo;
import im.quar.autolayout.utils.AutoLayoutHelper;


/**
 * Created by DTHeaven on 15/6/30.
 */
public class AutoLinearLayout extends LinearLayout {

    private AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
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


    public static class LayoutParams extends LinearLayout.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
            mAutoLayoutInfo.fillAttrs(this);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo() {
            return mAutoLayoutInfo;
        }


        public LayoutParams(int width, int height) {
            super(width, height);
        }


        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

    }

}
