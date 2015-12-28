package im.quar.autolayout;


import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import im.quar.autolayout.attr.AutoAttr;

public class AutoLayoutInfo {
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