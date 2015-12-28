package im.quar.autolayout;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import im.quar.autolayout.utils.AutoLayoutHelper;


/**
 * Created by DTHeaven on 15/11/19.
 */
public class AutoLayoutActivity extends AppCompatActivity {

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = AutoLayoutHelper.createAutoLayoutView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

}
