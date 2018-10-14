package spacetrader.controls;

import java.awt.Graphics;

public class PaintEventArgs extends EventArgs {

    public final spacetrader.controls.Graphics Graphics;

    private PaintEventArgs(spacetrader.controls.Graphics graphics) {
        Graphics = graphics;
    }

    PaintEventArgs(Graphics awtGraphics) {
        this(new spacetrader.controls.Graphics(awtGraphics));
    }
}
