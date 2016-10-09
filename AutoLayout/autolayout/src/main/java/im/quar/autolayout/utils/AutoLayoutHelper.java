/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.quar.autolayout.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import im.quar.autolayout.AutoLayoutInfo;
import im.quar.autolayout.R;
import im.quar.autolayout.ViewCreator;
import im.quar.autolayout.attr.DrawablePaddingAttr;
import im.quar.autolayout.attr.HeightAttr;
import im.quar.autolayout.attr.MarginAttr;
import im.quar.autolayout.attr.MarginBottomAttr;
import im.quar.autolayout.attr.MarginLeftAttr;
import im.quar.autolayout.attr.MarginRightAttr;
import im.quar.autolayout.attr.MarginTopAttr;
import im.quar.autolayout.attr.MaxHeight;
import im.quar.autolayout.attr.MaxWidth;
import im.quar.autolayout.attr.MinHeightAttr;
import im.quar.autolayout.attr.MinWidthAttr;
import im.quar.autolayout.attr.PaddingAttr;
import im.quar.autolayout.attr.PaddingBottomAttr;
import im.quar.autolayout.attr.PaddingLeftAttr;
import im.quar.autolayout.attr.PaddingRightAttr;
import im.quar.autolayout.attr.PaddingTopAttr;
import im.quar.autolayout.attr.TextSizeAttr;
import im.quar.autolayout.attr.WidthAttr;
import im.quar.autolayout.config.AutoLayoutConfig;

/**
 * Created by DTHeaven on 15/12/9.
 */
public class AutoLayoutHelper {
    private static final ViewCreator mDefaultViewCreator;
    private static ViewCreator mExtViewCreator;

    static {
        mDefaultViewCreator = new DefaultViewCreator();

        try {
            mExtViewCreator = (ViewCreator) Class.forName("im.quar.autolayout.ExtViewCreator").newInstance();
        } catch (Exception e) {
        }
    }

    private final ViewGroup mHost;

    private static final int[] LL = new int[]
            {
                    android.R.attr.textSize,
                    android.R.attr.padding,
                    android.R.attr.paddingLeft,
                    android.R.attr.paddingTop,
                    android.R.attr.paddingRight,
                    android.R.attr.paddingBottom,
                    android.R.attr.layout_width,
                    android.R.attr.layout_height,
                    android.R.attr.layout_margin,
                    android.R.attr.layout_marginLeft,
                    android.R.attr.layout_marginTop,
                    android.R.attr.layout_marginRight,
                    android.R.attr.layout_marginBottom,
                    android.R.attr.maxWidth,
                    android.R.attr.maxHeight,
                    android.R.attr.minWidth,
                    android.R.attr.minHeight,
                    android.R.attr.drawablePadding,

            };

    private static final int INDEX_TEXT_SIZE = 0;
    private static final int INDEX_PADDING = 1;
    private static final int INDEX_PADDING_LEFT = 2;
    private static final int INDEX_PADDING_TOP = 3;
    private static final int INDEX_PADDING_RIGHT = 4;
    private static final int INDEX_PADDING_BOTTOM = 5;
    private static final int INDEX_WIDTH = 6;
    private static final int INDEX_HEIGHT = 7;
    private static final int INDEX_MARGIN = 8;
    private static final int INDEX_MARGIN_LEFT = 9;
    private static final int INDEX_MARGIN_TOP = 10;
    private static final int INDEX_MARGIN_RIGHT = 11;
    private static final int INDEX_MARGIN_BOTTOM = 12;
    private static final int INDEX_MAX_WIDTH = 13;
    private static final int INDEX_MAX_HEIGHT = 14;
    private static final int INDEX_MIN_WIDTH = 15;
    private static final int INDEX_MIN_HEIGHT = 16;
    private static final int INDEX_DRAWABLE_PADDING = 17;

    private boolean mHasAdjustedChildren;
    private boolean mAppliedAspectRatio;

    public AutoLayoutHelper(ViewGroup host) {
        mHost = host;
        AutoLayoutConfig.init(host.getContext());

    }

    public void adjustChildren() {
        if (mHasAdjustedChildren) {
            return;
        }

        for (int i = 0, n = mHost.getChildCount(); i < n; i++) {
            View view = mHost.getChildAt(i);
            ViewGroup.LayoutParams params = view.getLayoutParams();

            if (params instanceof AutoLayoutParams) {
                AutoLayoutInfo info =
                        ((AutoLayoutParams) params).getAutoLayoutInfo();
                if (info != null) {
                    info.fillAttrs(view);
                }
            }
        }

        mHasAdjustedChildren = true;
    }

    public void applyAspectRatio() {
        if (mAppliedAspectRatio) {
            return;
        }

        for (int i = 0, n = mHost.getChildCount(); i < n; i++) {
            View view = mHost.getChildAt(i);
            ViewGroup.LayoutParams params = view.getLayoutParams();

            if (params instanceof AutoLayoutParams) {
                AutoLayoutInfo info =
                        ((AutoLayoutParams) params).getAutoLayoutInfo();
                if (info != null) {
                    info.applyAspectRatio(view);
                }
            }
        }

        mAppliedAspectRatio = true;
    }

    public static AutoLayoutInfo getAutoLayoutInfo(Context context, AttributeSet attrs) {

        AutoLayoutInfo info = new AutoLayoutInfo();

        int resourceId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "textAppearance", -1);
        TypedArray array = context.obtainStyledAttributes(attrs, LL);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int index = array.getIndex(i);
            if (!isPxVal(array.peekValue(index))) continue;

            int pxVal = 0;
            try {
                pxVal = array.getDimensionPixelOffset(index, 0);
            } catch (Exception ignore) {//not dimension
                continue;
            }

            switch (index) {
                case INDEX_TEXT_SIZE:
                    info.addAttr(new TextSizeAttr(pxVal));
                    resourceId = -1;//已设置TextSize不需要在TextAppearance中读取
                    break;
                case INDEX_PADDING:
                    info.addAttr(new PaddingAttr(pxVal));
                    break;
                case INDEX_PADDING_LEFT:
                    info.addAttr(new PaddingLeftAttr(pxVal));
                    break;
                case INDEX_PADDING_TOP:
                    info.addAttr(new PaddingTopAttr(pxVal));
                    break;
                case INDEX_PADDING_RIGHT:
                    info.addAttr(new PaddingRightAttr(pxVal));
                    break;
                case INDEX_PADDING_BOTTOM:
                    info.addAttr(new PaddingBottomAttr(pxVal));
                    break;
                case INDEX_WIDTH:
                    info.addAttr(new WidthAttr(pxVal));
                    break;
                case INDEX_HEIGHT:
                    info.addAttr(new HeightAttr(pxVal));
                    break;
                case INDEX_MARGIN:
                    info.addAttr(new MarginAttr(pxVal));
                    break;
                case INDEX_MARGIN_LEFT:
                    info.addAttr(new MarginLeftAttr(pxVal));
                    break;
                case INDEX_MARGIN_TOP:
                    info.addAttr(new MarginTopAttr(pxVal));
                    break;
                case INDEX_MARGIN_RIGHT:
                    info.addAttr(new MarginRightAttr(pxVal));
                    break;
                case INDEX_MARGIN_BOTTOM:
                    info.addAttr(new MarginBottomAttr(pxVal));
                    break;
                case INDEX_MAX_WIDTH:
                    info.addAttr(new MaxWidth(pxVal));
                    break;
                case INDEX_MAX_HEIGHT:
                    info.addAttr(new MaxHeight(pxVal));
                    break;
                case INDEX_MIN_WIDTH:
                    info.addAttr(new MinWidthAttr(pxVal));
                    break;
                case INDEX_MIN_HEIGHT:
                    info.addAttr(new MinHeightAttr(pxVal));
                    break;
                case INDEX_DRAWABLE_PADDING:
                    info.addAttr(new DrawablePaddingAttr(pxVal));
                    break;
            }
        }
        array.recycle();

        if (resourceId > 0) {
            int[] attribute = new int[]{android.R.attr.textSize};
            TypedArray textSizeTypeArray = context.obtainStyledAttributes(resourceId, attribute);
            if (isPxVal(textSizeTypeArray.peekValue(0))) {
                try {
                    int textSize = textSizeTypeArray.getDimensionPixelSize(0 /* index */, -1 /* default size */);
                    info.addAttr(new TextSizeAttr(textSize));
                } catch (Exception ignore) {
                }
            }
            textSizeTypeArray.recycle();
        }

        //AspectRatio
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLayout_Layout);
        float value = -1;
        try {
            value = array.getFloat(R.styleable.AutoLayout_Layout_auto_aspectRatio, -1f);
        } catch (Exception e) {
            //Some rom(MIUI 8 beta) may crash.
        }
        if (value != -1) {
            info.aspectRatio = value;
        }
        typedArray.recycle();

        return info;
    }

    private static boolean isPxVal(TypedValue val) {
        if (val != null && val.type == TypedValue.TYPE_DIMENSION &&
                getComplexUnit(val.data) == TypedValue.COMPLEX_UNIT_PX) {
            return true;
        }
        return false;
    }

    private static int getComplexUnit(int data) {
        return TypedValue.COMPLEX_UNIT_MASK & (data >> TypedValue.COMPLEX_UNIT_SHIFT);
    }

    public interface AutoLayoutParams {
        AutoLayoutInfo getAutoLayoutInfo();
    }

    public static View createAutoLayoutView(String name, Context context, AttributeSet attrs) {
        View view = mExtViewCreator == null ? null : mExtViewCreator.create(name, context, attrs);
        if (view == null) {
            view = mDefaultViewCreator.create(name, context, attrs);
        }

        return view;
    }
}
