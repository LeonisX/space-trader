package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.*;
import spacetrader.game.*;
import spacetrader.game.enums.CargoBuyOp;
import spacetrader.guifacade.Facaded;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import static spacetrader.util.Functions.*;

public class FormCargoBuy extends SpaceTraderForm {
    
    private Button okButton = new Button();
    private Button allButton = new Button();
    private Button noneButton = new Button();
    private Label questionLabel = new Label();
    private Label statementLabelValue = new Label();
    private NumericUpDown numericUpDown = new NumericUpDown();
    private Label availableLabelValue = new Label();
    private Label affordLabelValue = new Label();

    @Facaded
    public FormCargoBuy(int item, int maxAmount, CargoBuyOp op) {
        initializeComponent();

        Game game = Game.getCurrentGame();
        Commander cmdr = game.getCommander();
        numericUpDown.setMaximum(maxAmount);
        numericUpDown.setMinimum(Math.min(maxAmount, 1));
        numericUpDown.setValue(numericUpDown.getMinimum());
        setText(stringVars(Strings.CargoTitle, capitalize(Strings.CargoBuyOps[op.castToInt()]), Consts.TradeItems[item].getName()));
        questionLabel.setText(stringVars(Strings.CargoBuyQuestion, Strings.CargoBuyOps[op.castToInt()].toLowerCase()));

        switch (op) {
            case BUY_SYSTEM:
                // At ^1 each, you can buy up to ^2 ^3.
                statementLabelValue.setText(stringVars(Strings.CargoBuyStatement,
                        formatMoney(game.getPriceCargoBuy()[item]), Functions.plural(maxAmount, Strings.ContainerUnit)));

                //TODO scale
                setHeight(okButton.getTop() + okButton.getHeight() + 34);
                break;
            case BUY_TRADER:
                int afford = Math.min(cmdr.getCash() / game.getPriceCargoBuy()[item], cmdr.getShip().getFreeCargoBays());
                if (afford < maxAmount) {
                    numericUpDown.setMaximum(afford);
                }
                statementLabelValue.setText(stringVars(Strings.CargoBuyStatementTrader, Consts.TradeItems[item].getName(),
                        formatMoney(game.getPriceCargoBuy()[item])));
                availableLabelValue.setText(stringVars(Strings.CargoBuyAvailable,
                        Functions.plural(game.getOpponent().getCargo()[item], Strings.CargoUnit)));
                affordLabelValue.setText(stringVars(Strings.CargoBuyAfford,
                        Functions.plural(afford, Strings.CargoUnit)));

                availableLabelValue.setVisible(true);
                affordLabelValue.setVisible(true);
                //TODO multiplier
                okButton.setTop(okButton.getTop() + 26);
                allButton.setTop(allButton.getTop() + 26);
                noneButton.setTop(noneButton.getTop() + 26);
                questionLabel.setTop(questionLabel.getTop() + 26);
                numericUpDown.setTop(numericUpDown.getTop() + 26);

                break;
            case PLUNDER:
                int count = game.getOpponent().getCargo()[item];
                // Your victim has ^1 of these goods.
                statementLabelValue.setText(stringVars(Strings.CargoBuyStatementSteal, Functions.plural(count, Strings.ContainerUnit)));
                //TODO scale
                setHeight(okButton.getTop() + okButton.getHeight() + 34);
                break;
        }
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formCargoBuy");
        //setText("Buy Xxxxxxxxxx");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(336, 120);
        setShowInTaskbar(false);
        setAcceptButton(okButton);
        setCancelButton(noneButton);

        //TODO delete all texts & sizes
        statementLabelValue.setAutoSize(true);
        statementLabelValue.setLocation(8, 8);
        //statementLabelValue.setSize(326, 13);
        statementLabelValue.setTabIndex(3);
        //statementLabelValue.setText("The trader wants to sell Machines for the price of 8,888 cr. each.");

        statementLabelValue.setAutoSize(true);
        availableLabelValue.setLocation(8, 21);
        //availableLabelValue.setSize(163, 13);
        availableLabelValue.setTabIndex(5);
        //availableLabelValue.setText("The trader has 88 units for sale.");
        availableLabelValue.setVisible(false);

        questionLabel.setAutoSize(true);
        questionLabel.setLocation(8, 24);
        //questionLabel.setSize(161, 16);
        questionLabel.setTabIndex(1);
        questionLabel.setText("How many do you want to buy?");

        numericUpDown.setLocation(170, 22);
        //numericUpDown.setMaximum(999);
        numericUpDown.setSize(44, 20);
        numericUpDown.setTabIndex(1);
        numericUpDown.setValue(1);

        affordLabelValue.setAutoSize(true);
        affordLabelValue.setLocation(8, 34);
        affordLabelValue.setSize(157, 13);
        affordLabelValue.setTabIndex(6);
        //affordLabelValue.setText("You can afford to buy 88 units.");
        affordLabelValue.setVisible(false);

        okButton.setDialogResult(DialogResult.OK);
        okButton.setAutoWidth(true);
        okButton.setControlBinding(ControlBinding.RIGHT);
        okButton.setLocation(95, 54);
        okButton.setSize(41, 22);
        okButton.setTabIndex(2);
        okButton.setText("Ok");

        //allButton.setDialogResult(DialogResult.OK);
        allButton.setAutoWidth(true);
        allButton.setControlBinding(ControlBinding.CENTER);
        allButton.setLocation(144, 54);
        allButton.setSize(41, 22);
        allButton.setTabIndex(3);
        allButton.setText("All");
        allButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numericUpDown.setValue(numericUpDown.getMaximum());
                FormCargoBuy.this.setResult(DialogResult.OK);
                FormCargoBuy.this.close();
            }
        });

        noneButton.setDialogResult(DialogResult.CANCEL);
        noneButton.setAutoWidth(true);
        noneButton.setControlBinding(ControlBinding.LEFT);
        noneButton.setLocation(191, 54);
        noneButton.setSize(41, 22);
        noneButton.setTabIndex(4);
        noneButton.setText("None");

        controls.addAll(statementLabelValue, availableLabelValue, questionLabel, numericUpDown, affordLabelValue,
                okButton, allButton, noneButton);

        ReflectionUtils.loadControlsData(this);
    }

    @Facaded
    public int Amount() {
        return numericUpDown.getValue();
    }
}
