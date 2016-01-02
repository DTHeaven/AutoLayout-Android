# AutoLayout for Android
An easy way for supporting multiple screens. 
* **Auto Adaption:** Write once use every screen size.
* **Injection supported.**
* **Customize adaption policy**


This project is forked from [hongyangAndroid/AndroidAutoLayout](https://github.com/hongyangAndroid/AndroidAutoLayout), but different.
This project aim to to do the least changes to do the work, and not change the code habit.

### What you should do is:
* Add dependencies to root build.gradle like this
```
buildscript {
repositories {
jcenter()
}
dependencies {
classpath 'com.android.tools.build:gradle:1.3.1'
classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'

// NOTE: Do not place your application dependencies here; they belong
// in the individual module build.gradle files
}
}
```
* Change your subproject build.gradle
```
apply plugin: 'com.android.application'
apply plugin: 'com.neenbedankt.android-apt'

...

dependencies {
compile fileTree(dir: 'libs', include: ['*.jar'])
compile 'im.quar:autolayout:1.0.0'
apt 'im.quar:autolayout-compiler:1.0.0'
}
```
* Add your base width & height to AndroidManifest.xml, different screens will scale depends on those values.
```
<meta-data android:name="design_width" android:value="720"/>
<meta-data android:name="design_height" android:value="1280"/>
```
* Make your base activity extends form AutoLayoutActivity, or override onCreateView method.
```
public class BaseActivity extends AutoLayoutActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main_activity);
}

}
```
or
```
@Override
public View onCreateView(String name, Context context, AttributeSet attrs) {
View view = AutoLayoutHelper.createAutoLayoutView(name, context, attrs);
return view == null ? super.onCreateView(name, context, attrs) : view;
}
```

**Now you just need change your dimen values from dp to px.
Yes, px. Actually use it with design_with & design_height to calculate the percent .**

__If you just want to use auto layout partially, replace standard layout with Auto**Layout in xml files (e.g. LinearLayout -> AutoLinearLayout).__

### Supported layouts:
Auto adaption will work with those layouts:
* LinearLayout
* RelativeLayout
* FrameLayout
* ListView
* CardView
* RecyclerView

### Supported attrs:
* layout_width
* layout_height
* layout_margin (left, top, right, bottom)
* padding (left, top, right, bottom)
* textSize
* maxWidth, minWidth
* maxHeight, minHeight
* drawablePadding

### Extension:
If you want to make GridView or other else to be auto adaptable, you just need do this:
```
@AutoLayoutSimple
public class AutoGridView extends GridView {

public AutoGridView(Context context, AttributeSet attrs) {
super(context, attrs);
}
}
```
or more customizes:
```
@AutoLayout
public class AutoGridView extends GridView {
private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

public AutoGridView(Context context, AttributeSet attrs) {
super(context, attrs);
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

public static class LayoutParams extends GridView.LayoutParams
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
```
### Customize adaption policy:
If the default adaption policy is not adapt to you, you can do this
```
public class MyApplication extends Application {

@Override
public void onCreate() {
super.onCreate();
AutoLayoutConfig.init(this, new ScaleAdapter() {
@Override
public float adapt(float scale, int screenWidth, int screenHeight) {
//Do yourself adaption here.
if (screenWidth < 720 || screenHeight < 720) {//Small screen device
if (screenWidth <= 480 || screenHeight <= 480) {//480p
scale *= 1.2f;
} else {
if (ScreenUtils.getDevicePhysicalSize(getApplicationContext()) < 4.0) {//Meizu mx
scale *= 1.3f;
} else {//HUAWEI U9200
scale *= 1.05f;
}
}
}
return scale;
}
});
}
}
```

### Adapt in code:
```
//Scale values
int value = AutoUtils.scaleValue(pxVal);

//Adapt all view attrs.
AutoUtils.auto(view);
```

### Note:
**Don't** use dynamic view‘s **padding**, instead use child's **margin**.
(ListView, GridView, RecyclerView etc.)

### Thanks:
[hongyangAndroid](https://github.com/hongyangAndroid/AndroidAutoLayout)
