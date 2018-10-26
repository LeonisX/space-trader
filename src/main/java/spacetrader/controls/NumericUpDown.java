package spacetrader.controls;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import spacetrader.controls.enums.HorizontalAlignment;

public class NumericUpDown extends BaseComponent {

    private final SpinnerNumberModel model = new SpinnerNumberModel();
    private boolean thousandsSeparator;
    private HorizontalAlignment textAlign;

    public NumericUpDown() {
        super(new JSpinner());
        JSpinner spinner = asJSpinner();
        spinner.setModel(model);

        /// this bunch of code selects all text when entering the spinner.
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (e.getSource() instanceof JTextComponent) {
                    final JTextComponent textComponent = ((JTextComponent) e.getSource());
                    SwingUtilities.invokeLater(textComponent::selectAll);
                }
            }
        });
    }

    public boolean isThousandsSeparator() {
        return thousandsSeparator;
    }

    public void setThousandsSeparator(boolean thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
    }

    public HorizontalAlignment getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(HorizontalAlignment textAlign) {
        this.textAlign = textAlign;
    }

    public void select(int i, int length) {
        // TODO implement NumericUpDown.select (text)
    }

    public void setValueChanged(final EventHandler<Object, EventArgs> valueChanged) {
        asJSpinner().addChangeListener(e -> valueChanged.handle(NumericUpDown.this, null));
    }

    private JSpinner asJSpinner() {
        return (JSpinner) swingComponent;
    }

    public int getMaximum() {
        Integer maximum = (Integer) model.getMaximum();
        return (maximum == null) ? Integer.MAX_VALUE : maximum;
    }

    public void setMaximum(int maximum) {
        model.setMaximum(maximum);
    }

    public int getMinimum() {
        Integer minimum = (Integer) model.getMinimum();
        return (minimum == null) ? 0 : minimum;
    }

    public void setMinimum(int minimum) {
        model.setMinimum(minimum);
    }

    /**
     * In .NET, this means that the field can be manipulated by the buttons/arrows,
     * just not by directly inputing text into it.
     * TODO: implement this. Possibly by installing filter on key-presses.
     */
    public void setReadOnly(boolean readOnly) {
//		asJSpinner().setEnabled(!readOnly);
    }

    public void setLeave(EventHandler<Object, EventArgs> leave) {
        // TODO: implement. Used --> Important.
    }

    public int getValue() {
        return (Integer) asJSpinner().getValue();
    }

    public void setValue(int value) {
        asJSpinner().setValue(value);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    public void setIncrement(int increment) {
        model.setStepSize(increment);
    }
}
