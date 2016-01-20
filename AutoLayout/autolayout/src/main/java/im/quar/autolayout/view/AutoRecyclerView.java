package im.quar.autolayout.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.ViewGroup;

import im.quar.autolayout.AutoLayoutInfo;
import im.quar.autolayout.utils.AutoLayoutHelper;


/**
 * Created by DTHeaven on 15/12/9.
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
    public RecyclerView.LayoutParams generateLayoutParams(AttributeSet attrs) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return new GridLayoutParams(getContext(), attrs);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return new StaggeredGridLayoutParams(getContext(), attrs);
        }

        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) mHelper.adjustChildren();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isInEditMode()) mHelper.applyAspectRatio();
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

    public static class GridLayoutParams extends GridLayoutManager.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams {

        private AutoLayoutInfo mAutoLayoutInfo;

        public GridLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
            mAutoLayoutInfo.fillAttrs(this);
        }

        public GridLayoutParams(int width, int height) {
            super(width, height);
        }

        public GridLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public GridLayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public GridLayoutParams(RecyclerView.LayoutParams source) {
            super(source);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo() {
            return mAutoLayoutInfo;
        }
    }

    public static class StaggeredGridLayoutParams extends StaggeredGridLayoutManager.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams {

        private AutoLayoutInfo mAutoLayoutInfo;

        public StaggeredGridLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
            mAutoLayoutInfo.fillAttrs(this);
        }

        public StaggeredGridLayoutParams(int width, int height) {
            super(width, height);
        }

        public StaggeredGridLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public StaggeredGridLayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public StaggeredGridLayoutParams(RecyclerView.LayoutParams source) {
            super(source);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo() {
            return mAutoLayoutInfo;
        }
    }
}
