package spacetrader.controls;

public class ToolTip {

    public ToolTip(IContainer components) {
    }

    public ToolTip() {
    }

    public void setToolTip(Button item, String text) {
        item.asJButton().setToolTipText(text);
    }

}
