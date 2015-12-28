package im.quar.autolayout.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import im.quar.autolayout.view.AutoCardView;
import im.quar.autolayout.view.AutoFrameLayout;
import im.quar.autolayout.view.AutoLinearLayout;
import im.quar.autolayout.view.AutoListView;
import im.quar.autolayout.view.AutoRecyclerView;
import im.quar.autolayout.view.AutoRelativeLayout;
import im.quar.autolayout.ViewCreator;

/**
 * Created by DTHeaven on 15/12/14.
 */
class DefaultViewCreator implements ViewCreator {
    @Override
    public View create(String name, Context context, AttributeSet attrs) {
        switch (name) {
            case "LinearLayout": return new AutoLinearLayout(context, attrs);
            case "RelativeLayout": return new AutoRelativeLayout(context, attrs);
            case "FrameLayout": return new AutoFrameLayout(context, attrs);
            case "ListView": return new AutoListView(context, attrs);
            case "android.support.v7.widget.CardView": return new AutoCardView(context, attrs);
            case "android.support.v7.widget.RecyclerView": return new AutoRecyclerView(context, attrs);
        }
        return null;
    }
}
