package spacetrader.controls;

import spacetrader.controls.swingextra.JStatusBarSection;

import java.awt.*;

public class StatusBarPanel implements ISupportInitialize {

    final StatusBarPanelAutoSize AutoSize;
    private final JStatusBarSection jpanel = new JStatusBarSection(" ");

    public StatusBarPanel() {
        this(StatusBarPanelAutoSize.None);
    }

    public StatusBarPanel(StatusBarPanelAutoSize autoSize) {
        AutoSize = autoSize;
        jpanel.setFont(BaseComponent.DEFAULT_FONT);
    }

    public void setText(String text) {
        if (text.isEmpty()) text = "  ";
        jpanel.setText(text);
    }

    public void setWidth(int w) {
        int h = jpanel.getHeight();
        jpanel.setSize(w, h);
    }

    JStatusBarSection asJStatusBarSection() {
        return jpanel;
    }

    public void setMinWidth(int width) {
        int height = jpanel.getPreferredSize().height;
        jpanel.setPreferredSize(new Dimension(width, height));
        jpanel.setMinimumSize(new Dimension(width, height));
    }

    public void beginInit() {
        // TODO Auto-generated method stub
    }

    public void endInit() {
        // TODO Auto-generated method stub
    }
}
