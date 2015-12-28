package im.quar.autolayout.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import im.quar.autolayout.AutoLayoutInfo;
import im.quar.autolayout.utils.AutoLayoutHelper;


/**
 * Created by wangzenghui on 15/12/9.
 */
public class AutoRecyclerView extends RecyclerView {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoRecyclerView(Context context) {
        super(context);
    }

    public AutoRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode())
            mHelper.adjustChildren();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static class LayoutParams extends RecyclerView.LayoutParams
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
