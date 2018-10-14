package spacetrader.controls;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextBox extends BaseComponent {

    public TextBox() {
        super(new JTextField());
    }

    public void setTextChanged(final EventHandler<Object, EventArgs> valueChanged) {
        asJTextField().getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }

            public void insertUpdate(DocumentEvent e) {
                valueChanged.handle(TextBox.this, null);
            }

            public void removeUpdate(DocumentEvent e) {
                valueChanged.handle(TextBox.this, null);
            }
        });
    }

    public String getText() {
        return asJTextField().getText();
    }

    public void setText(String text) {
        asJTextField().setText(text);
    }

    private JTextField asJTextField() {
        return (JTextField) swingComponent;
    }
}
