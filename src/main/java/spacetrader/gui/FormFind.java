package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.util.ReflectionUtils;

public class FormFind extends SpaceTraderForm {

    private Label questionLabel = new Label();
    private Button okButton = new Button();
    private Button cancelButton = new Button();
    private TextBox systemTextBox = new TextBox();
    private CheckBox trackSystemCheckBox = new CheckBox();

    public FormFind(String previousSearchPhrase) {
        initializeComponent(previousSearchPhrase);
    }

    private void initializeComponent(String previousSearchPhrase) {
        ReflectionUtils.setAllComponentNames(this);

        setName("formFind");
        setText("Find System");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(230, 107);
        //setControlBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(cancelButton);

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(8, 8);
        //questionLabel.setSize(177, 13);
        questionLabel.setText("Which system are you looking for?");

        systemTextBox.setLocation(8, 24);
        systemTextBox.setSize(180, 20);
        systemTextBox.setText(previousSearchPhrase);
        systemTextBox.setTabIndex(1);

        trackSystemCheckBox.setLocation(5, 48);
        trackSystemCheckBox.setSize(220, 16);
        trackSystemCheckBox.setText("Track this system");

        okButton.setDialogResult(DialogResult.OK);
        okButton.setAutoWidth(true);
        okButton.setControlBinding(ControlBinding.RIGHT);
        okButton.setLocation(60, 74);
        okButton.setSize(40, 22);
        okButton.setTabIndex(3);
        okButton.setText("Ok");

        cancelButton.setDialogResult(DialogResult.CANCEL);
        cancelButton.setAutoWidth(true);
        cancelButton.setControlBinding(ControlBinding.RIGHT);
        cancelButton.setLocation(110, 74);
        cancelButton.setSize(50, 22);
        cancelButton.setTabIndex(4);
        cancelButton.setText("cancel");

        controls.addAll(questionLabel, systemTextBox, trackSystemCheckBox, okButton, cancelButton);

        ReflectionUtils.loadControlsData(this);
    }

    String getSystemName() {
        return systemTextBox.getText();
    }

    boolean isTrackSystem() {
        return trackSystemCheckBox.isChecked();
    }

    String getSearchPhrase() {
        return systemTextBox.getText();
    }
}
