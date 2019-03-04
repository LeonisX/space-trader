package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Commander;
import spacetrader.game.Consts;
import spacetrader.game.Game;
import spacetrader.game.Strings;
import spacetrader.game.enums.CargoSellOp;
import spacetrader.guifacade.Facaded;
import spacetrader.util.ReflectionUtils;

import static spacetrader.util.Functions.*;

@Facaded
public class FormCargoSell extends SpaceTraderForm {

    private Label statementLabelValue = new Label();
    private Label questionLabel = new Label();
    private NumericUpDown numericUpDown = new NumericUpDown();
    private Label paidLabelValue = new Label();
    private Label profitLabelValue = new Label();
    private Button okButton = new Button();
    private Button allButton = new Button();
    private Button noneButton = new Button();

    public FormCargoSell(int item, int maxAmount, CargoSellOp op, int price) {
        initializeComponent();

        Commander commander = Game.getCurrentGame().getCommander();
        int cost = commander.getPriceCargo()[item] / commander.getShip().getCargo()[item];

        numericUpDown.setMaximum(maxAmount);
        numericUpDown.setMinimum(Math.min(maxAmount, 1));
        numericUpDown.setValue(numericUpDown.getMinimum());
        setText(stringVars(Strings.CargoTitle, capitalize(Strings.CargoSellOps[op.castToInt()]), Consts.TradeItems[item].getName()));
        questionLabel.setText(stringVars(Strings.CargoSellQuestion, Strings.CargoSellOps[op.castToInt()].toLowerCase()));
        paidLabelValue.setText(stringVars(op == CargoSellOp.SELL_TRADER ? Strings.CargoSellPaidTrader
                        : Strings.CargoSellPaid, formatMoney(cost), plural(maxAmount, Strings.ContainerUnit)));
        profitLabelValue.setText(stringVars(Strings.CargoSellProfitPerUnit,
                price >= cost ? Strings.CargoProfit : Strings.CargoLoss, formatMoney(price >= cost ? price - cost : cost - price)));

        // Override defaults for some ops.
        switch (op) {
            case DUMP:
                statementLabelValue.setText(stringVars(Strings.CargoSellStatementDump,
                        Strings.CargoSellOps[op.castToInt()].toLowerCase(), plural(maxAmount, Strings.ContainerUnitGen)));
                profitLabelValue.setText(stringVars(Strings.CargoSellDumpCost, formatMoney(-price)));
                break;
            case JETTISON:
                statementLabelValue.setText(stringVars(Strings.CargoSellStatementDump,
                        Strings.CargoSellOps[op.castToInt()].toLowerCase(), plural(maxAmount, Strings.ContainerUnitGen)));
                break;
            case SELL_SYSTEM:
                statementLabelValue.setText(stringVars(Strings.CargoSellStatement, formatNumber(maxAmount), formatMoney(price)));
                break;
            case SELL_TRADER:
                statementLabelValue.setText(stringVars(Strings.CargoSellStatementTrader,
                        Consts.TradeItems[item].getName(), formatMoney(price)));
                break;
        }
    }
    
    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formCargoSell");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(312, 120);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(noneButton);

        statementLabelValue.setAutoSize(true);
        statementLabelValue.setLocation(8, 8);
        //statementLabelValue.setSize(302, 13);
        //statementLabelValue.setText("The trader wants to by Machines, and offers 8,888 cr. each.");

        paidLabelValue.setAutoSize(true);
        paidLabelValue.setLocation(8, 23);
        //paidLabelValue.setSize(280, 13);
        paidLabelValue.setTabIndex(5);
        //paidLabelValue.setText("You paid about 8,888 cr. per unit, and can sell 88 units.");

        profitLabelValue.setAutoSize(true);
        profitLabelValue.setLocation(8, 38);
        //profitLabelValue.setSize(200, 13);
        //profitLabelValue.setText("It costs 8,888 cr. per unit for disposal.");

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(8, 57);
        //questionLabel.setSize(160, 13);
        questionLabel.setText("How many do you want to sell?");

        numericUpDown.setLocation(207, 54);
        numericUpDown.setMinimum(1);
        numericUpDown.setSize(38, 20);
        numericUpDown.setTabIndex(1);
        numericUpDown.setValue(88);
        
        okButton.setDialogResult(DialogResult.OK);
        okButton.setAutoWidth(true);
        okButton.setControlBinding(ControlBinding.RIGHT);
        okButton.setLocation(81, 81);
        okButton.setSize(41, 25);
        okButton.setTabIndex(2);
        okButton.setText("Ok");
        
        //allButton.setDialogResult(DialogResult.OK);
        allButton.setAutoWidth(true);
        allButton.setControlBinding(ControlBinding.CENTER);
        allButton.setLocation(132, 81);
        allButton.setSize(41, 25);
        allButton.setTabIndex(3);
        allButton.setText("All");
        allButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numericUpDown.setValue(numericUpDown.getMaximum());
                FormCargoSell.this.setResult(DialogResult.OK);
                FormCargoSell.this.close();
            }
        });
        
        noneButton.setDialogResult(DialogResult.CANCEL);
        noneButton.setAutoWidth(true);
        noneButton.setControlBinding(ControlBinding.LEFT);
        noneButton.setLocation(181, 81);
        noneButton.setSize(41, 25);
        noneButton.setTabIndex(4);
        noneButton.setText("None");

        controls.addAll(statementLabelValue, paidLabelValue, profitLabelValue, questionLabel, numericUpDown,
                okButton, allButton, noneButton);

        ReflectionUtils.loadControlsData(this);
    }

    public int Amount() {
        return numericUpDown.getValue();
    }
}
