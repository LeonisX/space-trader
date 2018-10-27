package spacetrader.controls.enums;


//TODO unused. May be delete
public enum ContentAlignment {

    TOP_LEFT(Horizontal.TOP, Vertical.LEFT),
    TOP_CENTER(Horizontal.TOP, Vertical.CENTER),
    TOP_RIGHT(Horizontal.TOP, Vertical.RIGHT),
    MIDDLE_LEFT(Horizontal.MIDDLE, Vertical.LEFT),
    MIDDLE_CENTER(Horizontal.MIDDLE, Vertical.CENTER),
    MIDDLE_RIGHT(Horizontal.MIDDLE, Vertical.RIGHT),
    BOTTOM_LEFT(Horizontal.BOTTOM, Vertical.LEFT),
    BOTTOM_CENTER(Horizontal.BOTTOM, Vertical.CENTER),
    BOTTOM_RIGHT(Horizontal.BOTTOM, Vertical.RIGHT);

    public final Vertical vertical;
    public final Horizontal horizontal;

    ContentAlignment(Horizontal horizontal, Vertical vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public enum Horizontal {
        TOP, MIDDLE, BOTTOM
    }

    public enum Vertical {
        LEFT, CENTER, RIGHT
    }
}
