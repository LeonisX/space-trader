package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.enums.*;
import spacetrader.util.ReflectionUtils;

import java.util.Arrays;

import static spacetrader.util.Functions.plural;
import static spacetrader.util.Functions.stringVars;
import static spacetrader.game.Strings.BankLoanStatementBorrow;
import static spacetrader.game.Strings.MoneyUnit;

public class FormGetLoan extends SpaceTraderForm {

    private Button okButton = new Button();
    private Label questionLabel = new Label();
    private Button maxButton = new Button();
    private Button nothingButton = new Button();
    private NumericUpDown numAmount = new NumericUpDown();
    private Label statementLabelValue = new Label();

    public FormGetLoan(int max) {
        initializeComponent();

        numAmount.setMaximum(max);
        numAmount.setValue(numAmount.getMinimum());
        statementLabelValue.setText(stringVars(BankLoanStatementBorrow, plural(max, MoneyUnit)));
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formGetLoan");
        setText("Get Loan");
        setClientSize(262, 90);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        //setControlBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(nothingButton);

        statementLabelValue.setAutoSize(true);
        statementLabelValue.setLocation(8, 8);
        //statementLabelValue.setSize(189, 13);
        //statementLabelValue.setText("You can borrow up to 88,888 credits.");

        questionLabel.setAutoSize(true);
        questionLabel.setAutoSize(true);
        questionLabel.setLocation(8, 25);
        //questionLabel.setSize(178, 13);
        questionLabel.setText("How much do you want to borrow?");

        numAmount.setLocation(190, 22);
        numAmount.setMinimum(1);
        numAmount.setSize(64, 20);
        numAmount.setTabIndex(1);
        numAmount.setThousandsSeparator(true);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setAutoWidth(true);
        okButton.setControlBinding(ControlBinding.RIGHT);
        okButton.setLocation(52, 52);
        okButton.setSize(41, 22);
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        //maxButton.setDialogResult(DialogResult.OK);
        maxButton.setAutoWidth(true);
        maxButton.setControlBinding(ControlBinding.CENTER);
        maxButton.setLocation(102, 52);
        maxButton.setSize(41, 22);
        maxButton.setTabIndex(3);
        maxButton.setText("Max");
        maxButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                numAmount.setValue(numAmount.getMaximum());
            }
        });

        nothingButton.setDialogResult(DialogResult.CANCEL);
        nothingButton.setAutoWidth(true);
        nothingButton.setControlBinding(ControlBinding.LEFT);
        nothingButton.setLocation(150, 52);
        nothingButton.setSize(53, 22);
        nothingButton.setTabIndex(4);
        nothingButton.setText("Nothing");

        controls.addAll(statementLabelValue, nothingButton, maxButton, okButton, numAmount, questionLabel);

        ReflectionUtils.loadControlsData(this);
    }

    int getAmount() {
        return numAmount.getValue();
    }
}
