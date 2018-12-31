package spacetrader.gui.cheat;

import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.TextBox;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.gui.SpaceTraderForm;
import spacetrader.util.ReflectionUtils;

public class FormInput extends SpaceTraderForm {

    private Button okButton = new Button();
    private Label questionLabel = new Label();
    private Button cancelButton = new Button();
    private TextBox valueTextBox = new TextBox();

    public FormInput(int value, String title, String message) {
        initializeComponent(value, title, message);
    }

    private void initializeComponent(int value, String title, String message) {
        ReflectionUtils.setAllComponentNames(this);

        setName("formInput");
        setText(title);
        setClientSize(280, 90);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        //setControlBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(cancelButton);

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(8, 4);
        questionLabel.setText(message);

        valueTextBox.setLocation(210, 22);
        valueTextBox.setSize(64, 20);
        valueTextBox.setText(Integer.toString(value));

        okButton.setDialogResult(DialogResult.OK);
        okButton.setAutoWidth(true);
        okButton.setControlBinding(ControlBinding.RIGHT);
        okButton.setLocation(85, 52);
        okButton.setSize(41, 22);
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        cancelButton.setDialogResult(DialogResult.CANCEL);
        cancelButton.setAutoWidth(true);
        cancelButton.setControlBinding(ControlBinding.LEFT);
        cancelButton.setLocation(135, 52);
        cancelButton.setSize(53, 22);
        cancelButton.setTabIndex(4);
        cancelButton.setText("Cancel");

        controls.addAll(questionLabel, valueTextBox, okButton, cancelButton);

        ReflectionUtils.loadControlsData(this);
    }

    public int getValue() {
        return Integer.valueOf(valueTextBox.getText());
    }
}
