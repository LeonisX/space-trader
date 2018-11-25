package spacetrader.controls;

import spacetrader.controls.swingextra.JLinkLabel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LinkLabel extends Label implements MouseListener {

    private SimpleEventHandler<Object> linkClicked;

    public LinkLabel() {
        super(new JLinkLabel());
    }

    public LinkLabel(String text) {
        this();
        this.setText(text);
    }

    @Override
    public LinkLabel withAutoSize(boolean autoSize) {
        setAutoSize(autoSize);
        return this;
    }

    public void setLinkClicked(SimpleEventHandler<Object> linkClicked) {
        this.linkClicked = linkClicked;
        asJLinkLabel().addMouseListener(this);
    }

    private JLinkLabel asJLinkLabel() {
        return (JLinkLabel) swingComponent;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        linkClicked.handle(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
