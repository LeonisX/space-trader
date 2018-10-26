/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron
 * Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to
 * http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/

package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.ContentAlignment;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.Difficulty;
import spacetrader.gui.debug.Launcher;
import spacetrader.guifacade.GuiFacade;
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

    public static void main(String[] args) {
        new Game("name", Difficulty.BEGINNER, 8, 8, 8, 8, null);
        Launcher.runForm(new FormViewBank());
    }

    public FormViewBank() {
        initializeComponent();

        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);
        
        setName("formViewBank");
        setText("Bank");
        setAutoScaleBaseSize(5, 13);
        setClientSize(226, 231);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        suspendLayout();

        loanLabel.setAutoSize(true);
        loanLabel.setFont(FontCollection.bold12);
        loanLabel.setLocation(8, 8);
        loanLabel.setSize(44, 19);
        loanLabel.setTabIndex(1);
        loanLabel.setText("Loan");

        currentDebtLabel.setAutoSize(true);
        currentDebtLabel.setFont(FontCollection.bold825);
        currentDebtLabel.setLocation(16, 32);
        currentDebtLabel.setSize(75, 13);
        currentDebtLabel.setTabIndex(2);
        currentDebtLabel.setText("Current Debt:");

        currentDebtLabelValue.setLocation(136, 32);
        currentDebtLabelValue.setSize(56, 13);
        currentDebtLabelValue.setTabIndex(4);
        //currentDebtLabelValue.setText("88,888 cr.");
        currentDebtLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        maxLoanLabel.setAutoSize(true);
        maxLoanLabel.setFont(FontCollection.bold825);
        maxLoanLabel.setLocation(16, 52);
        maxLoanLabel.setSize(88, 13);
        maxLoanLabel.setTabIndex(3);
        maxLoanLabel.setText("Maximum Loan:");

        maxLoanLabelValue.setLocation(136, 52);
        maxLoanLabelValue.setSize(56, 13);
        maxLoanLabelValue.setTabIndex(5);
        //maxLoanLabelValue.setText("88,888 cr.");
        maxLoanLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        getLoanButton.setFlatStyle(FlatStyle.FLAT);
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

        payBackButton.setFlatStyle(FlatStyle.FLAT);
        payBackButton.setLocation(88, 72);
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
        insuranceLabel.setSize(81, 19);
        insuranceLabel.setTabIndex(23);
        insuranceLabel.setText("Insurance");

        shipValueLabel.setAutoSize(true);
        shipValueLabel.setFont(FontCollection.bold825);
        shipValueLabel.setLocation(16, 136);
        shipValueLabel.setSize(65, 13);
        shipValueLabel.setTabIndex(24);
        shipValueLabel.setText("Ship Value:");

        shipValueLabelValue.setLocation(136, 136);
        shipValueLabelValue.setSize(56, 13);
        shipValueLabelValue.setTabIndex(26);
        //shipValueLabelValue.setText("88,888 cr.");
        shipValueLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        noClaimLabel.setAutoSize(true);
        noClaimLabel.setFont(FontCollection.bold825);
        noClaimLabel.setLocation(16, 156);
        noClaimLabel.setSize(106, 13);
        noClaimLabel.setTabIndex(25);
        noClaimLabel.setText("No-Claim Discount:");

        noClaimLabelValue.setLocation(154, 156);
        noClaimLabelValue.setSize(32, 13);
        noClaimLabelValue.setTabIndex(27);
        //noClaimLabelValue.setText("88%");
        noClaimLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        maxNoClaimLabel.setAutoSize(true);
        maxNoClaimLabel.setLocation(182, 156);
        maxNoClaimLabel.setSize(33, 13);
        maxNoClaimLabel.setTabIndex(33);
        maxNoClaimLabel.setText("(max)");
        maxNoClaimLabel.setVisible(false);

        insAmtLabel.setAutoSize(true);
        insAmtLabel.setFont(FontCollection.bold825);
        insAmtLabel.setLocation(16, 176);
        insAmtLabel.setSize(38, 13);
        insAmtLabel.setTabIndex(29);
        insAmtLabel.setText("Costs:");

        insAmtLabelValue.setLocation(136, 176);
        insAmtLabelValue.setSize(82, 13);
        insAmtLabelValue.setTabIndex(30);
        //insAmtLabelValue.setText("8,888 cr. daily");
        insAmtLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        buyInsuranceButton.setFlatStyle(FlatStyle.FLAT);
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

        ReflectionUtils.loadControlsDimensions(this.asSwingObject(), this.getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(this.asSwingObject(), this.getName(), GlobalAssets.getStrings());
    }

    private void updateAll() {
        // Loan Info
        currentDebtLabelValue.setText(Functions.formatMoney(commander.getDebt()));
        maxLoanLabelValue.setText(Functions.formatMoney(maxLoan));
        payBackButton.setVisible(commander.getDebt() > 0);

        // Insurance Info
        shipValueLabelValue.setText(Functions.formatMoney(commander.getShip().getBaseWorth(true)));
        noClaimLabelValue.setText(Functions.formatPercent(commander.getNoClaim()));
        maxNoClaimLabel.setVisible(commander.getNoClaim() == Consts.MaxNoClaim);
        insAmtLabelValue.setText(Functions.stringVars(Strings.MoneyRateSuffix,
                Functions.formatMoney(game.getInsuranceCosts())));
        buyInsuranceButton.setText(Functions.stringVars(Strings.BankInsuranceButtonText,
                commander.getInsurance() ? Strings.BankInsuranceButtonStop : Strings.BankInsuranceButtonBuy));
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
