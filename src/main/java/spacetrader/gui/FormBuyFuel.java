package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Commander;
import spacetrader.game.Game;
import spacetrader.util.ReflectionUtils;

public class FormBuyFuel extends SpaceTraderForm {

    private Label questionLabel = new Label();
    private NumericUpDown numericUpDown = new NumericUpDown();
    private Button okButton = new Button();
    private Button maxButton = new Button();
    private Button nothingButton = new Button();

    public FormBuyFuel() {
        initializeComponent();

        Commander cmdr = Game.getCurrentGame().getCommander();
        numericUpDown.setMaximum(Math.min(cmdr.getCash(),
                (cmdr.getShip().getFuelTanks() - cmdr.getShip().getFuel()) * cmdr.getShip().getFuelCost()));
        numericUpDown.setValue(numericUpDown.getMaximum());
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formBuyFuel");
        setText("Buy Fuel");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(305, 80);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(nothingButton);

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(8, 14);
        //questionLabel.setSize(211, 13);
        questionLabel.setText("How much do you want to spend on fuel?");

        numericUpDown.setLocation(238, 11);
        //numericUpDown.setMaximum(999);
        numericUpDown.setMinimum(1);
        numericUpDown.setSize(58, 20);
        numericUpDown.setTabIndex(1);
        //numericUpDown.setValue(888);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setAutoWidth(true);
        okButton.setControlBinding(ControlBinding.LEFT);
        okButton.setLocation(70, 40);
        okButton.setSize(41, 22);
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        //maxButton.setDialogResult(DialogResult.OK);
        maxButton.setAutoWidth(true);
        maxButton.setControlBinding(ControlBinding.CENTER);
        //TODO delete all sizes
        maxButton.setLocation(116, 40);
        maxButton.setSize(41, 22);
        maxButton.setTabIndex(3);
        //TODO delete all texts
        maxButton.setText("Max");
        maxButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numericUpDown.setValue(numericUpDown.getMaximum());
                FormBuyFuel.this.setResult(DialogResult.OK);
                FormBuyFuel.this.close();
            }
        });

        nothingButton.setDialogResult(DialogResult.CANCEL);
        nothingButton.setAutoWidth(true);
        nothingButton.setControlBinding(ControlBinding.RIGHT);
        nothingButton.setLocation(171, 40);
        nothingButton.setSize(53, 22);
        nothingButton.setTabIndex(4);
        nothingButton.setText("Nothing");

        controls.addAll(questionLabel, numericUpDown, okButton, maxButton, nothingButton);

        ReflectionUtils.loadControlsData(this);
    }

    int getAmount() {
        return numericUpDown.getValue();
    }
}
