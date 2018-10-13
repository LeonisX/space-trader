package jwinforms;

public enum ContentAlignment {

    TopLeft(Horizontal.top, Vertical.left), TopCenter(Horizontal.top, Vertical.center), TopRight(Horizontal.top,
            Vertical.right), MiddleLeft(Horizontal.middle, Vertical.left), MiddleCenter(Horizontal.middle,
            Vertical.center), MiddleRight(Horizontal.middle, Vertical.right), ButtomLeft(Horizontal.bottom,
            Vertical.left), ButtomCenter(Horizontal.bottom, Vertical.center), ButtomRight(Horizontal.bottom,
            Vertical.right);

    public final Vertical vertical;
    public final Horizontal horizontal;

    ContentAlignment(Horizontal horizontal, Vertical vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public enum Horizontal {
        top, middle, bottom
    }

    public enum Vertical {
        left, center, right
    }
}
