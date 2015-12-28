package im.quar.autolayout.attr;


import im.quar.autolayout.utils.AutoUtils;

/**
 * Created by zhy on 15/12/4.
 */
public abstract class AutoAttr<T> {
    private int pxVal;

    public AutoAttr(int pxVal) {
        this.pxVal = pxVal;
    }

    public void apply(T t) {
        int val = AutoUtils.scaleValue(pxVal);
        val = Math.max(val, 1);//for very thin divider
        execute(t, val);
    }

    protected abstract int attrVal();

    protected abstract void execute(T t, int val);

    @Override
    public String toString() {
        return "AutoAttr{" +
                "pxVal=" + pxVal +
                '}';
    }
}
