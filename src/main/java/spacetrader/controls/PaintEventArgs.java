package spacetrader.controls;

import java.awt.Graphics;

public class PaintEventArgs extends EventArgs {

    private final spacetrader.controls.Graphics graphics;

    private PaintEventArgs(spacetrader.controls.Graphics graphics) {
        this.graphics = graphics;
    }

    PaintEventArgs(Graphics awtGraphics) {
        this(new spacetrader.controls.Graphics(awtGraphics));
    }

    public spacetrader.controls.Graphics getGraphics() {
        return graphics;
    }
}
