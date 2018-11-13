package spacetrader.controls;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Panel extends BaseComponent {

    private final WinformJPanel controls;
    private final TitledBorder border;

    public Panel() {
        super(new WinformJPanel(null));
        controls = (WinformJPanel) swingComponent;
        controls.putClientProperty("baseComponent", this);
        border = BorderFactory.createTitledBorder("");
        asJPanel().setBorder(border);
    }

    public WinformJPanel getControls() {
        return controls;
    }

    public String getText() {
        return border.getTitle();
    }

    public void setText(String text) {
        border.setTitle(text);
        //TODO need? It works???
        Graphics.resizeIfNeed(swingComponent, isAutoSize(), isAutoWidth(), isAutoHeight(), getControlBinding());
    }

    private WinformJPanel asJPanel() {
        return controls;
    }
}
