package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.EventArgs;
import spacetrader.controls.EventHandler;
import spacetrader.controls.Label;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Commander;
import spacetrader.game.Consts;
import spacetrader.game.Game;
import spacetrader.game.Strings;
import spacetrader.game.enums.AlertType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FormViewBank extends SpaceTraderForm {
    
    private final Game game = Game.getCurrentGame();
    private final Commander commander = game.getCommander();

    private final int maxLoan = commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreClean
            ? min(25000, max(1000, commander.getWorth() / 5000 * 500)) : 500;
    
    private Label loanLabel = new Label();
    private Label currentDebtLabel = new Label();
    private Label maxLoanLabel = new Label();
    private Label currentDebtLabelValue = new Label();
    private Label maxLoanLabelValue = new Label();
    private Button getLoanButton = new Button();
    private Button buyInsuranceButton = new Button();
    private Label noClaimLabelValue = new Label();
    private Label shipValueLabelValue = new Label();
    private Label noClaimLabel = new Label();
    private Label shipValueLabel = new Label();
    private Label insuranceLabel = new Label();
    private Label insAmtLabelValue = new Label();
    private Label insAmtLabel = new Label();
    private Button payBackButton = new Button();
    private Button closeButton = new Button();
    private Label maxNoClaimLabel = new Label();

    public FormViewBank() {
        initializeComponent();

        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);
        
        setName("formViewBank");
        setText("Bank");
        setClientSize(210, 241);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        loanLabel.setAutoSize(true);
        loanLabel.setFont(FontCollection.bold12);
        loanLabel.setLocation(8, 8);
        //loanLabel.setSize(44, 19);
        loanLabel.setText("Loan");

        currentDebtLabel.setAutoSize(true);
        currentDebtLabel.setFont(FontCollection.bold825);
        currentDebtLabel.setLocation(16, 33);
        //currentDebtLabel.setSize(75, 13);
        currentDebtLabel.setText("Current Debt:");

        currentDebtLabelValue.setAutoSize(true);
        currentDebtLabelValue.setLocation(136, 32);
        //currentDebtLabelValue.setSize(56, 13);
        //currentDebtLabelValue.setText("88,888 cr.");

        maxLoanLabel.setAutoSize(true);
        maxLoanLabel.setFont(FontCollection.bold825);
        maxLoanLabel.setLocation(16, 53);
        //maxLoanLabel.setSize(88, 13);
        maxLoanLabel.setText("Maximum Loan:");

        maxLoanLabelValue.setAutoSize(true);
        maxLoanLabelValue.setLocation(136, 52);
        //maxLoanLabelValue.setSize(56, 13);
        //maxLoanLabelValue.setText("88,888 cr.");

        getLoanButton.setAutoWidth(true);
        getLoanButton.setLocation(16, 72);
        getLoanButton.setSize(61, 22);
        getLoanButton.setTabIndex(1);
        getLoanButton.setText("Get Loan");
        getLoanButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                getLoanButtonClick();
            }
        });

        payBackButton.setAutoWidth(true);
        payBackButton.setLocation(16, 72);
        payBackButton.setSize(90, 22);
        payBackButton.setTabIndex(2);
        payBackButton.setText("Pay Back Loan");
        payBackButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                payBackButtonClick();
            }
        });

        insuranceLabel.setAutoSize(true);
        insuranceLabel.setFont(FontCollection.bold12);
        insuranceLabel.setLocation(8, 112);
        //insuranceLabel.setSize(81, 19);
        insuranceLabel.setText("Insurance");

        shipValueLabel.setAutoSize(true);
        shipValueLabel.setFont(FontCollection.bold825);
        shipValueLabel.setLocation(16, 137);
        //shipValueLabel.setSize(65, 13);
        shipValueLabel.setText("Ship Value:");

        shipValueLabelValue.setAutoSize(true);
        shipValueLabelValue.setLocation(136, 136);
        //shipValueLabelValue.setSize(56, 13);
        //shipValueLabelValue.setText("88,888 cr.");

        noClaimLabel.setAutoSize(true);
        noClaimLabel.setFont(FontCollection.bold825);
        noClaimLabel.setLocation(16, 157);
        //noClaimLabel.setSize(106, 13);
        noClaimLabel.setText("No-Claim Discount:");

        noClaimLabelValue.setAutoSize(true);
        noClaimLabelValue.setLocation(154, 156);
        //noClaimLabelValue.setSize(32, 13);
        //noClaimLabelValue.setText("88%");

        maxNoClaimLabel.setAutoSize(true);
        maxNoClaimLabel.setLocation(182, 156);
        //maxNoClaimLabel.setSize(33, 13);
        maxNoClaimLabel.setText("(max)");
        //maxNoClaimLabel.setVisible(false);

        insAmtLabel.setAutoSize(true);
        insAmtLabel.setFont(FontCollection.bold825);
        insAmtLabel.setLocation(16, 177);
        //insAmtLabel.setSize(38, 13);
        insAmtLabel.setText("Costs:");

        insAmtLabelValue.setAutoSize(true);
        insAmtLabelValue.setLocation(136, 176);
        //insAmtLabelValue.setSize(82, 13);
        //insAmtLabelValue.setText("8,888 cr. daily");

        buyInsuranceButton.setAutoWidth(true);
        buyInsuranceButton.setLocation(16, 196);
        buyInsuranceButton.setSize(90, 22);
        buyInsuranceButton.setTabIndex(3);
        buyInsuranceButton.setText("Stop Insurance");
        buyInsuranceButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                buyInsuranceButtonClick();
            }
        });

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(26, 27);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(loanLabel, currentDebtLabel, currentDebtLabelValue, maxLoanLabel, maxLoanLabelValue,
                getLoanButton, payBackButton, insuranceLabel, shipValueLabel, shipValueLabelValue, noClaimLabel,
                noClaimLabelValue, maxNoClaimLabel, insAmtLabel, insAmtLabelValue, buyInsuranceButton, closeButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void updateAll() {
        // Loan Info
        currentDebtLabelValue.setText(Functions.formatMoney(commander.getDebt()));
        maxLoanLabelValue.setText(Functions.formatMoney(maxLoan));
        payBackButton.setLeft(getLoanButton.getLeft() + getLoanButton.getWidth() + 5);
        payBackButton.setVisible(commander.getDebt() > 0);

        // Insurance Info
        shipValueLabelValue.setText(Functions.formatMoney(commander.getShip().getBaseWorth(true)));
        noClaimLabelValue.setText(Functions.formatPercent(commander.getNoClaim()));
        maxNoClaimLabel.setVisible(commander.getNoClaim() == Consts.MaxNoClaim);
        insAmtLabelValue.setText(Functions.stringVars(Strings.MoneyRateSuffix,
                Functions.formatMoney(game.getInsuranceCosts())));
        buyInsuranceButton.setText(
                commander.getInsurance() ? Strings.BankInsuranceButtonStop : Strings.BankInsuranceButtonBuy);
    }

    private void getLoanButtonClick() {
        if (commander.getDebt() >= maxLoan) {
            GuiFacade.alert(AlertType.DebtTooLargeLoan);
        } else {
            FormGetLoan form = new FormGetLoan(maxLoan - commander.getDebt());
            if (form.showDialog(this) == DialogResult.OK) {
                commander.setCash(commander.getCash() + form.getAmount());
                commander.setDebt(commander.getDebt() + form.getAmount());

                updateAll();
                game.getParentWindow().updateAll();
            }
        }
    }

    private void payBackButtonClick() {
        if (commander.getDebt() == 0) {
            GuiFacade.alert(AlertType.DebtNone);
        } else {
            FormPayBackLoan form = new FormPayBackLoan();
            if (form.showDialog(this) == DialogResult.OK) {
                commander.setCash(commander.getCash() - form.getAmount());
                commander.setDebt(commander.getDebt() - form.getAmount());

                updateAll();
                game.getParentWindow().updateAll();
            }
        }
    }

    private void buyInsuranceButtonClick() {
        if (commander.getInsurance()) {
            if (GuiFacade.alert(AlertType.InsuranceStop) == DialogResult.YES) {
                commander.setInsurance(false);
                commander.setNoClaim(0);
            }
        } else if (!commander.getShip().getEscapePod())
            GuiFacade.alert(AlertType.InsuranceNoEscapePod);
        else {
            commander.setInsurance(true);
            commander.setNoClaim(0);
        }

        updateAll();
        game.getParentWindow().updateAll();
    }
}
