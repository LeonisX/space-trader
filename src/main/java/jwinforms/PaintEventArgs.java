package jwinforms;

import java.awt.Graphics;

public class PaintEventArgs extends EventArgs {

    public final jwinforms.Graphics Graphics;

    private PaintEventArgs(jwinforms.Graphics graphics) {
        Graphics = graphics;
    }

    PaintEventArgs(Graphics awtGraphics) {
        this(new jwinforms.Graphics(awtGraphics));
    }
}
