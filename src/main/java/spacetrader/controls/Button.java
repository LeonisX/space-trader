package spacetrader.controls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Button extends BaseComponent {

    DialogResult dialogResult;

    /*public Button() {
        super(new JButton());
    }*/

    public Button(String name) {
        super(new JButton());
        setName(name);
    }

    public String getText() {
        return asJButton().getText();
    }

    public void setText(String text) {
        asJButton().setText(text);
    }

    @Override
    public void setSize(Dimension size) {
        // width should be bigger because font is bigger(?).
        super.setSize(new Dimension(size.width, size.height));
    }

    JButton asJButton() {
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
        super.setClick(click);
    }

    public void setFlatStyle(FlatStyle flatStyle) {
        // In Ubuntu buttons are ugly. Disabled flat
        /*switch (flatStyle) {
            case FLAT:
                asJButton().setBorder(BorderFactory.createLineBorder(Color.black));
                return;

            default:
                throw new Error("Unknown FlatStyle");
        }*/
        // I think this is default.
        //asJButton().setBorder(BorderFactory.createRaisedBevelBorder());
    }
}
