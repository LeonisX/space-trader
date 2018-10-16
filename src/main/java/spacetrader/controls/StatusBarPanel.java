package spacetrader.controls;

import spacetrader.controls.swingextra.JStatusBarSection;

import java.awt.*;

public class StatusBarPanel implements ISupportInitialize, IName {

    private final StatusBarPanelAutoSize autoSize;
    private final JStatusBarSection jpanel = new JStatusBarSection(" ");

    public StatusBarPanel() {
        this(StatusBarPanelAutoSize.NONE);
    }

    public StatusBarPanel(StatusBarPanelAutoSize autoSize) {
        this.autoSize = autoSize;
        jpanel.setFont(BaseComponent.DEFAULT_FONT);
    }

    public StatusBarPanelAutoSize getAutoSize() {
        return autoSize;
    }

    public void setText(String text) {
        if (text.isEmpty()) {
            text = "  ";
        }
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

    @Override
    public String getName() {
        return jpanel.getName();
    }

    @Override
    public void setName(String name) {
        jpanel.setName(name);
    }
}
