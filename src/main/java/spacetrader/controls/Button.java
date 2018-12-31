package spacetrader.controls;

import spacetrader.controls.enums.DialogResult;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Button extends BaseComponent {

    DialogResult dialogResult;

    public Button() {
        super(new JButton());
        asJButton().putClientProperty("baseComponent", this);
    }

    public String getText() {
        return asJButton().getText();
    }

    public void setText(String text) {
        asJButton().setText(text);
        Graphics.resizeIfNeed(swingComponent, isAutoSize(), isAutoWidth(), isAutoHeight(), getControlBinding());
    }

    public void setText(int number) {
        asJButton().setText(Integer.toString(number));
    }

    @Override
    public void setSize(int width, int height) {
        // width should be bigger because font is bigger(?).
        super.setSize(width, height);
    }

    public JButton asJButton() {
        return (JButton) swingComponent;
    }

    public void setDialogResult(DialogResult dialogResult) {
        this.dialogResult = dialogResult;
    }

    @Override
    public void setClick(final EventHandler<Object, EventArgs> click) {
        Action userAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                click.handle(Button.this, null);
            }
        };
        userAction.putValue(AbstractAction.NAME, getText());
        asJButton().setAction(userAction);
        //super.setClick(click);
    }
}
