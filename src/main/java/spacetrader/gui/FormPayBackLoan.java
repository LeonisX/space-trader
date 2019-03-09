package spacetrader.gui;

import static spacetrader.util.Functions.plural;
import static spacetrader.util.Functions.stringVars;
import static spacetrader.game.Strings.BankLoanStatementDebt;
import static spacetrader.game.Strings.MoneyUnit;

import spacetrader.controls.Button;
import spacetrader.controls.enums.*;
import spacetrader.controls.EventArgs;
import spacetrader.controls.EventHandler;
import spacetrader.controls.Label;
import spacetrader.controls.NumericUpDown;
import spacetrader.game.Commander;
import spacetrader.game.Game;
import spacetrader.util.ReflectionUtils;

public class FormPayBackLoan extends SpaceTraderForm {
    
    private Button okButton = new Button();
    private Label questionLabel = new Label();
    private Button maxButton = new Button();
    private Button nothingButton = new Button();
    private NumericUpDown numAmount = new NumericUpDown();
    private Label statementLabelValue = new Label();

    public FormPayBackLoan() {
        initializeComponent();

        Commander commander = Game.getCurrentGame().getCommander();
        int max = Math.min(commander.getDebt(), commander.getCash());
        numAmount.setMaximum(max);
        numAmount.setMinimum(Math.min(commander.getDebt(), 1));
        numAmount.setValue(numAmount.getMinimum());
        statementLabelValue.setText(stringVars(BankLoanStatementDebt, plural(commander.getDebt(), MoneyUnit)));
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formPayBackLoan");
        setText("Pay Back Loan");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(274, 70);
        setControlBox(false);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(nothingButton);

        statementLabelValue.setAutoSize(true);
        statementLabelValue.setLocation(8, 8);
        //statementLabelValue.setSize(176, 13);
        //statementLabelValue.setText("You have a debt of 88,888 credits.");

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(8, 26);
        //questionLabel.setSize(188, 13);
        questionLabel.setText("How much do you want to pay back?");

        numAmount.setLocation(196, 24);
        numAmount.setSize(58, 20);
        numAmount.setTabIndex(1);
        numAmount.setThousandsSeparator(true);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setAutoWidth(true);
        okButton.setControlBinding(ControlBinding.RIGHT);
        okButton.setLocation(58, 52);
        okButton.setSize(41, 22);
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        //maxButton.setDialogResult(DialogResult.OK);
        maxButton.setAutoWidth(true);
        maxButton.setControlBinding(ControlBinding.CENTER);
        maxButton.setLocation(109, 52);
        maxButton.setSize(41, 22);
        maxButton.setTabIndex(3);
        maxButton.setText("Max");
        maxButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numAmount.setValue(numAmount.getMaximum());
            }
        });

        nothingButton.setDialogResult(DialogResult.CANCEL);
        nothingButton.setAutoWidth(true);
        nothingButton.setControlBinding(ControlBinding.LEFT);
        nothingButton.setLocation(158, 52);
        nothingButton.setSize(53, 22);
        nothingButton.setTabIndex(4);
        nothingButton.setText("Nothing");

        controls.addAll(statementLabelValue, questionLabel, numAmount, okButton, maxButton, nothingButton);

        ReflectionUtils.loadControlsData(this);
    }
    
    int getAmount() {
        return numAmount.getValue();
    }
}
