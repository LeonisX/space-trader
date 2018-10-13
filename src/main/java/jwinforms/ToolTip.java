package jwinforms;

public class ToolTip {

    public ToolTip(IContainer components) {
    }

    public ToolTip() {
    }

    public void SetToolTip(Button item, String text) {
        item.asJButton().setToolTipText(text);
    }
}
