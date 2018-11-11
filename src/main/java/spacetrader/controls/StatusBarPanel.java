package spacetrader.controls;

import spacetrader.controls.enums.StatusBarPanelAutoSize;
import spacetrader.controls.swingextra.JStatusBarSection;

import java.awt.*;

public class StatusBarPanel implements IName {

    private final StatusBarPanelAutoSize autoSize;
    private final JStatusBarSection jPanel = new JStatusBarSection(" ");

    public StatusBarPanel() {
        this(StatusBarPanelAutoSize.NONE);
    }

    public StatusBarPanel(StatusBarPanelAutoSize autoSize) {
        this.autoSize = autoSize;
        jPanel.setFont(BaseComponent.DEFAULT_FONT);
    }

    StatusBarPanelAutoSize getAutoSize() {
        return autoSize;
    }

    public void setText(String text) {
        jPanel.setText("  " + text);
    }

    public void setWidth(int w) {
        int h = jPanel.getHeight();
        jPanel.setSize(w, h);
    }

    JStatusBarSection asJStatusBarSection() {
        return jPanel;
    }

    public void setMinWidth(int width) {
        int height = jPanel.getPreferredSize().height;
        jPanel.setPreferredSize(new Dimension(width, height));
        jPanel.setMinimumSize(new Dimension(width, height));
    }

    @Override
    public String getName() {
        return jPanel.getName();
    }

    @Override
    public void setName(String name) {
        jPanel.setName(name);
    }
}
