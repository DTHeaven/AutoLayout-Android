package im.quar.autolayout.attr;

/**
 * Created by DTHeaven on 15/12/5.
 * <p/>
 * 与attrs.xml中数值对应
 */
public interface Attrs {
    int WIDTH = 1;
    int HEIGHT = WIDTH << 1;
    int TEXT_SIZE = HEIGHT << 1;
    int PADDING = TEXT_SIZE << 1;
    int MARGIN = PADDING << 1;
    int MARGIN_LEFT = MARGIN << 1;
    int MARGIN_TOP = MARGIN_LEFT << 1;
    int MARGIN_RIGHT = MARGIN_TOP << 1;
    int MARGIN_BOTTOM = MARGIN_RIGHT << 1;
    int PADDING_LEFT = MARGIN_BOTTOM << 1;
    int PADDING_TOP = PADDING_LEFT << 1;
    int PADDING_RIGHT = PADDING_TOP << 1;
    int PADDING_BOTTOM = PADDING_RIGHT << 1;
    int MAX_WIDTH = PADDING_BOTTOM << 1;
    int MAX_HEIGHT = MAX_WIDTH << 1;
    int MIN_WIDTH = MAX_HEIGHT << 1;
    int MIN_HEIGHT = MIN_WIDTH << 1;
    int DRAWABLE_PADDING = MIN_HEIGHT << 1;

}
