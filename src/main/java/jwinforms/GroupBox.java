package jwinforms;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class GroupBox extends WinformControl {

    public final WinformJPanel controls;
    private final TitledBorder border;
    protected AnchorStyles anchor;

    public GroupBox() {
        super(new WinformJPanel(null));
        controls = (WinformJPanel) swingVersion;
        border = BorderFactory.createTitledBorder("");
        asJPanel().setBorder(border);
    }

    @Override
    public void suspendLayout() {
        // TODO Auto-generated method stub
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
