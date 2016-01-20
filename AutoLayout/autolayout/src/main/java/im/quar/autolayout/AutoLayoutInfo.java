package im.quar.autolayout;


import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import im.quar.autolayout.attr.AutoAttr;

public class AutoLayoutInfo {
    public float aspectRatio;
    private List<AutoAttr> autoAttrs = new ArrayList<>();

    public void addAttr(AutoAttr autoAttr) {
        autoAttrs.add(autoAttr);
    }

    public void fillAttrs(Object o) {
        for (AutoAttr autoAttr : autoAttrs) {
            Class clazz = getActualTypeClass(autoAttr.getClass());
            if (clazz.isInstance(o)) {
                autoAttr.apply(o);
            }
        }
    }

    public void applyAspectRatio(View view) {
        if (aspectRatio > 0) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params != null) {
                int width = params.width < 0 ? view.getMeasuredWidth() : params.width;
                int height = params.height < 0 ? view.getMeasuredHeight() : params.height;

                final boolean widthNotSet = width == 0;
                final boolean heightNotSet = height == 0;
                if (widthNotSet && height > 0) {
                    params.width = (int) (height * aspectRatio);
                }

                if (heightNotSet && width > 0) {
                    params.height = (int) (width * aspectRatio);
                }
            }
        }
    }

    private Class getActualTypeClass(Class entity) {
        ParameterizedType type = (ParameterizedType) entity.getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }

    @Override
    public String toString() {
        return "AutoLayoutInfo{" +
                "autoAttrs=" + autoAttrs +
                '}';
    }
}