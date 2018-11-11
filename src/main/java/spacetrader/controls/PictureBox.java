package spacetrader.controls;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import spacetrader.controls.enums.BorderStyle;
import spacetrader.controls.enums.PictureBoxSizeMode;

public class PictureBox extends BaseComponent {

    public PictureBoxSizeMode sizeMode;
    private ImageMouseListener mouseListener;

    public PictureBox() {
        super(new SpecialImageJLabel());
        asJLabel().pictureBox = this;
        asJLabel().addMouseListener(mouseListener = new ImageMouseListener(this));
    }

    public void setMouseDown(EventHandler<Object, MouseEventArgs> mouseDown) {
        mouseListener.pressed = mouseDown;
    }

    @Override
    public void setBackground(Color background) {
        asJLabel().background = background;
    }

    public void refresh() {
        asJLabel().repaint();
    }

    public void setImage(Image image) {
        if (image != null) {
            asJLabel().setIcon(new ImageIcon(image.asSwingImage()));
        }
    }

    public void setPaint(EventHandler<Object, PaintEventArgs> paint) {
        if (asJLabel().paintEventHandler != null) {
            throw new Error("2 handlers same event");
        }
        asJLabel().paintEventHandler = paint;
    }

    @Override
    public void setBorderStyle(BorderStyle borderStyle) {
        switch (borderStyle) {
            case FIXED_SINGLE:
                asJLabel().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                break;

            default:
                throw new Error();
        }
    }

    private SpecialImageJLabel asJLabel() {
        return ((SpecialImageJLabel) swingComponent);
    }
}

class SpecialImageJLabel extends JLabel {
    EventHandler<Object, PaintEventArgs> paintEventHandler;
    PictureBox pictureBox;
    Color background;

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        tryBackground(background, graphics);
        tryEventHandler(paintEventHandler, graphics);
        super.paintComponent(graphics);
    }

    private void tryBackground(Color background, Graphics graphics) {
        if (background == null) {
            return;
        }
        graphics.setColor(background);
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    private void tryEventHandler(EventHandler<Object, PaintEventArgs> handler, Graphics graphics) {
        if (handler != null) {
            handler.handle(pictureBox, new PaintEventArgs(graphics));
        }
    }
}

class ImageMouseListener extends MouseAdapter {

    private final PictureBox pictureBox;
    EventHandler<Object, MouseEventArgs> pressed;

    ImageMouseListener(PictureBox pictureBox) {
        this.pictureBox = pictureBox;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        tryEvent(pressed, new MouseEventArgs(e));
    }

    private void tryEvent(EventHandler<Object, MouseEventArgs> handler, MouseEventArgs e) {
        if (handler != null) {
            handler.handle(pictureBox, e);
        }
    }
}
