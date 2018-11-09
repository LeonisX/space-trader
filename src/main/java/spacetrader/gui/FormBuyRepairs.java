package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.NumericUpDown;
import spacetrader.controls.WinformForm;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Commander;
import spacetrader.game.Game;
import spacetrader.util.ReflectionUtils;

public class FormBuyRepairs extends WinformForm {
    
    private Label questionLabel = new Label();
    private NumericUpDown numericUpDown = new NumericUpDown();
    private Button okButton = new Button();
    private Button maxButton = new Button();
    private Button nothingButton = new Button();

    public FormBuyRepairs() {
        initializeComponent();

        Commander cmdr = Game.getCurrentGame().getCommander();
        numericUpDown.setMaximum(Math.min(cmdr.getCash(),
                (cmdr.getShip().getHullStrength() - cmdr.getShip().getHull()) * cmdr.getShip().getRepairCost()));
        numericUpDown.setValue(numericUpDown.getMaximum());
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);
        
        setName("formBuyRepair");
        setText("Hull Repair");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(296, 80);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(nothingButton);
        
        //TODO need??
        numericUpDown.beginInit();
        suspendLayout();

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(8, 14);
        //questionLabel.setSize(227, 13);
        //questionLabel.setTabIndex(3);
        questionLabel.setText("How much do you want to spend on repairs?");

        numericUpDown.setLocation(240, 11);
        //numericUpDown.setMaximum(999);
        numericUpDown.setMinimum(1);
        numericUpDown.setSize(44, 20);
        numericUpDown.setTabIndex(1);
        //numericUpDown.setValue(888);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setAutoWidth(true);
        okButton.setControlBinding(ControlBinding.LEFT);
        okButton.setLocation(69, 40);
        okButton.setSize(41, 22);
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        maxButton.setDialogResult(DialogResult.OK);
        maxButton.setAutoWidth(true);
        maxButton.setControlBinding(ControlBinding.CENTER);
        maxButton.setLocation(117, 40);
        maxButton.setSize(41, 22);
        maxButton.setTabIndex(3);
        maxButton.setText("Max");

        nothingButton.setDialogResult(DialogResult.CANCEL);
        nothingButton.setAutoWidth(true);
        nothingButton.setControlBinding(ControlBinding.RIGHT);
        nothingButton.setLocation(173, 40);
        nothingButton.setSize(53, 22);
        nothingButton.setTabIndex(4);
        nothingButton.setText("Nothing");

        controls.addAll(questionLabel, numericUpDown, okButton, maxButton, nothingButton);
        
        numericUpDown.endInit();

        ReflectionUtils.loadControlsData(this);
    }

    int getAmount() {
        return numericUpDown.getValue();
    }
}
