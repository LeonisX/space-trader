package spacetrader.controls;

import java.awt.event.MouseEvent;
import spacetrader.controls.enums.MouseButtons;

public class MouseEventArgs extends EventArgs {

    private final MouseButtons button;
    private final int x, y;

    MouseEventArgs(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        button = findMouseButton(e.getButton());
    }

    private MouseButtons findMouseButton(int button) {
        switch (button) {
            case MouseEvent.BUTTON1:
                return MouseButtons.LEFT;
            case MouseEvent.BUTTON2:
                return MouseButtons.RIGHT;
            default:
                throw new Error("Unknown mouse button: " + button);
        }
    }

    public MouseButtons getButton() {
        return button;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
