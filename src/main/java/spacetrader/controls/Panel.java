package spacetrader.controls;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import spacetrader.controls.enums.AnchorStyles;

public class Panel extends BaseComponent {

    private final WinformJPanel controls;
    private final TitledBorder border;
    protected AnchorStyles anchor;

    public Panel() {
        super(new WinformJPanel(null));
        controls = (WinformJPanel) swingComponent;
        border = BorderFactory.createTitledBorder("");
        asJPanel().setBorder(border);
    }

    @Override
    public void suspendLayout() {
        // TODO Auto-generated method stub
    }

    public WinformJPanel getControls() {
        return controls;
    }

    public String getText() {
        return border.getTitle();
    }

    public void setText(String text) {
        border.setTitle(text);
    }

    private WinformJPanel asJPanel() {
        return controls;
    }
}
