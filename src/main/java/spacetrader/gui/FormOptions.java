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
//using System;
//using System.Drawing;
//using System.Collections;
//using System.ComponentModel;
//using System.Windows.Forms;
package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.game.Game;
import spacetrader.game.GameOptions;
import spacetrader.game.enums.AlertType;
import spacetrader.guifacade.GuiFacade;

import java.awt.*;

public class FormOptions extends SpaceTraderForm {
    // #region Control Declarations

    private Button btnOk;
    private Button btnCancel;
    private Label lblEmpty;
    private Label lblIgnore;
    private CheckBox chkFuel;
    private CheckBox chkContinuousAttack;
    private CheckBox chkAttackFleeing;
    private CheckBox chkDisable;
    private CheckBox chkNewspaper;
    private CheckBox chkNewspaperShow;
    private CheckBox chkRange;
    private CheckBox chkStopTracking;
    private CheckBox chkLoan;
    private CheckBox chkIgnoreTradersDealing;
    private CheckBox chkReserveMoney;
    private CheckBox chkIgnoreTraders;
    private CheckBox chkIgnorePirates;
    private CheckBox chkIgnorePolice;
    private CheckBox chkRepair;
    private NumericUpDown numEmpty;
    private Button btnSave;
    private Button btnLoad;

    // #endregion

    // #region Member Declarations

    private Game game = Game.currentGame();
    private boolean initializing = true;

    private GameOptions _options = new GameOptions(false);

    // #endregion

    // #region Methods

    public FormOptions() {
        initializeComponent();

        if (game != null)
            Options().CopyValues(game.Options());
        else {
            Options().LoadFromDefaults(false);
            btnOk.setEnabled(false);
            GuiFacade.alert(AlertType.OptionsNoGame);
        }

        UpdateAll();
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        this.btnOk = new Button();
        this.btnCancel = new Button();
        this.lblEmpty = new Label();
        this.chkFuel = new CheckBox();
        this.chkContinuousAttack = new CheckBox();
        this.chkAttackFleeing = new CheckBox();
        this.chkNewspaper = new CheckBox();
        this.chkRange = new CheckBox();
        this.chkStopTracking = new CheckBox();
        this.chkLoan = new CheckBox();
        this.chkIgnoreTradersDealing = new CheckBox();
        this.chkReserveMoney = new CheckBox();
        this.chkIgnoreTraders = new CheckBox();
        this.chkIgnorePirates = new CheckBox();
        this.chkIgnorePolice = new CheckBox();
        this.chkRepair = new CheckBox();
        this.lblIgnore = new Label();
        this.numEmpty = new NumericUpDown();
        this.btnSave = new Button();
        this.btnLoad = new Button();
        this.chkNewspaperShow = new CheckBox();
        this.chkDisable = new CheckBox();
        ((ISupportInitialize) (this.numEmpty)).beginInit();
        this.suspendLayout();
        //
        // btnOk
        //
        this.btnOk.setDialogResult(DialogResult.OK);
        this.btnOk.setFlatStyle(FlatStyle.FLAT);
        this.btnOk.setLocation(new Point(14, 240));
        this.btnOk.setName("btnOk");
        this.btnOk.setSize(new Size(40, 22));
        this.btnOk.setTabIndex(15);
        this.btnOk.setText("Ok");
        //
        // btnCancel
        //
        this.btnCancel.setDialogResult(DialogResult.CANCEL);
        this.btnCancel.setFlatStyle(FlatStyle.FLAT);
        this.btnCancel.setLocation(new Point(62, 240));
        this.btnCancel.setName("btnCancel");
        this.btnCancel.setSize(new Size(49, 22));
        this.btnCancel.setTabIndex(16);
        this.btnCancel.setText("Cancel");
        //
        // lblEmpty
        //
        this.lblEmpty.setAutoSize(true);
        this.lblEmpty.setLocation(new Point(52, 90));
        this.lblEmpty.setName("lblEmpty");
        this.lblEmpty.setSize(new Size(292, 16));
        this.lblEmpty.setTabIndex(38);
        this.lblEmpty.setText("Cargo Bays to leave empty when buying goods in-system");
        //
        // chkFuel
        //
        this.chkFuel.setLocation(new Point(8, 8));
        this.chkFuel.setName("chkFuel");
        this.chkFuel.setSize(new Size(160, 16));
        this.chkFuel.setTabIndex(1);
        this.chkFuel.setText("Get full fuel tanks on arrival");
        this.chkFuel.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkContinuousAttack
        //
        this.chkContinuousAttack.setLocation(new Point(8, 176));
        this.chkContinuousAttack.setName("chkContinuousAttack");
        this.chkContinuousAttack.setSize(new Size(163, 16));
        this.chkContinuousAttack.setTabIndex(13);
        this.chkContinuousAttack.setText("Continuous attack and flight");
        this.chkContinuousAttack.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkAttackFleeing
        //
        this.chkAttackFleeing.setLocation(new Point(24, 192));
        this.chkAttackFleeing.setName("chkAttackFleeing");
        this.chkAttackFleeing.setSize(new Size(177, 16));
        this.chkAttackFleeing.setTabIndex(14);
        this.chkAttackFleeing.setText("Continue attacking fleeing ship");
        this.chkAttackFleeing.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkNewspaper
        //
        this.chkNewspaper.setLocation(new Point(8, 40));
        this.chkNewspaper.setName("chkNewspaper");
        this.chkNewspaper.setSize(new Size(155, 16));
        this.chkNewspaper.setTabIndex(3);
        this.chkNewspaper.setText("Always pay for newspaper");
        this.chkNewspaper.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkRange
        //
        this.chkRange.setLocation(new Point(184, 8));
        this.chkRange.setName("chkRange");
        this.chkRange.setSize(new Size(175, 16));
        this.chkRange.setTabIndex(5);
        this.chkRange.setText("Show range to tracked system");
        this.chkRange.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkStopTracking
        //
        this.chkStopTracking.setLocation(new Point(184, 24));
        this.chkStopTracking.setName("chkStopTracking");
        this.chkStopTracking.setSize(new Size(139, 16));
        this.chkStopTracking.setTabIndex(6);
        this.chkStopTracking.setText("Stop tracking on arrival");
        this.chkStopTracking.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkLoan
        //
        this.chkLoan.setLocation(new Point(184, 56));
        this.chkLoan.setName("chkLoan");
        this.chkLoan.setSize(new Size(124, 16));
        this.chkLoan.setTabIndex(4);
        this.chkLoan.setText("Remind about loans");
        this.chkLoan.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkIgnoreTradersDealing
        //
        this.chkIgnoreTradersDealing.setLocation(new Point(152, 152));
        this.chkIgnoreTradersDealing.setName("chkIgnoreTradersDealing");
        this.chkIgnoreTradersDealing.setSize(new Size(133, 16));
        this.chkIgnoreTradersDealing.setTabIndex(12);
        this.chkIgnoreTradersDealing.setText("Ignore dealing traders");
        this.chkIgnoreTradersDealing.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkReserveMoney
        //
        this.chkReserveMoney.setLocation(new Point(184, 40));
        this.chkReserveMoney.setName("chkReserveMoney");
        this.chkReserveMoney.setSize(new Size(176, 16));
        this.chkReserveMoney.setTabIndex(7);
        this.chkReserveMoney.setText("Reserve money for warp costs");
        this.chkReserveMoney.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkIgnoreTraders
        //
        this.chkIgnoreTraders.setLocation(new Point(136, 136));
        this.chkIgnoreTraders.setName("chkIgnoreTraders");
        this.chkIgnoreTraders.setSize(new Size(62, 16));
        this.chkIgnoreTraders.setTabIndex(11);
        this.chkIgnoreTraders.setText("Traders");
        this.chkIgnoreTraders.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkIgnorePirates
        //
        this.chkIgnorePirates.setLocation(new Point(8, 136));
        this.chkIgnorePirates.setName("chkIgnorePirates");
        this.chkIgnorePirates.setSize(new Size(58, 16));
        this.chkIgnorePirates.setTabIndex(9);
        this.chkIgnorePirates.setText("Pirates");
        this.chkIgnorePirates.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkIgnorePolice
        //
        this.chkIgnorePolice.setLocation(new Point(74, 136));
        this.chkIgnorePolice.setName("chkIgnorePolice");
        this.chkIgnorePolice.setSize(new Size(54, 16));
        this.chkIgnorePolice.setTabIndex(10);
        this.chkIgnorePolice.setText("Police");
        this.chkIgnorePolice.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkRepair
        //
        this.chkRepair.setLocation(new Point(8, 24));
        this.chkRepair.setName("chkRepair");
        this.chkRepair.setSize(new Size(167, 16));
        this.chkRepair.setTabIndex(2);
        this.chkRepair.setText("Get full hull repairs on arrival");
        this.chkRepair.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // lblIgnore
        //
        this.lblIgnore.setAutoSize(true);
        this.lblIgnore.setLocation(new Point(8, 120));
        this.lblIgnore.setName("lblIgnore");
        this.lblIgnore.setSize(new Size(152, 16));
        this.lblIgnore.setTabIndex(52);
        this.lblIgnore.setText("Always ignore when it is safe:");
        //
        // numEmpty
        //
        this.numEmpty.setLocation(new Point(8, 88));
        this.numEmpty.setMaximum(99);
        this.numEmpty.setName("numEmpty");
        this.numEmpty.setSize(new Size(40, 20));
        this.numEmpty.setTabIndex(8);
        this.numEmpty.setValue(88);
        this.numEmpty.setValueChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // btnSave
        //
        this.btnSave.setFlatStyle(FlatStyle.FLAT);
        this.btnSave.setLocation(new Point(119, 240));
        this.btnSave.setName("btnSave");
        this.btnSave.setSize(new Size(107, 22));
        this.btnSave.setTabIndex(17);
        this.btnSave.setText("Save As Defaults");
        this.btnSave.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnSave_Click(sender, e);
            }
        });
        //
        // btnLoad
        //
        this.btnLoad.setFlatStyle(FlatStyle.FLAT);
        this.btnLoad.setLocation(new Point(234, 240));
        this.btnLoad.setName("btnLoad");
        this.btnLoad.setSize(new Size(114, 22));
        this.btnLoad.setTabIndex(18);
        this.btnLoad.setText("Load from Defaults");
        this.btnLoad.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnLoad_Click(sender, e);
            }
        });
        //
        // chkNewspaperShow
        //
        this.chkNewspaperShow.setLocation(new Point(24, 56));
        this.chkNewspaperShow.setName("chkNewspaperShow");
        this.chkNewspaperShow.setSize(new Size(160, 16));
        this.chkNewspaperShow.setTabIndex(53);
        this.chkNewspaperShow.setText("Show newspaper on arrival");
        this.chkNewspaperShow.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // chkDisable
        //
        this.chkDisable.setLocation(new Point(8, 208));
        this.chkDisable.setName("chkDisable");
        this.chkDisable.setSize(new Size(244, 16));
        this.chkDisable.setTabIndex(54);
        this.chkDisable.setText("Attempt to disable opponents when possible");
        this.chkDisable.setCheckedChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                controlChanged(sender, e);
            }
        });
        //
        // FormOptions
        //
        this.setAcceptButton(this.btnOk);
        this.setAutoScaleBaseSize(new Size(5, 13));
        this.setCancelButton(this.btnCancel);
        this.setClientSize(new Size(362, 271));
        this.Controls.add(this.chkDisable);
        this.Controls.add(this.chkLoan);
        this.Controls.add(this.chkNewspaperShow);
        this.Controls.add(this.btnLoad);
        this.Controls.add(this.btnSave);
        this.Controls.add(this.numEmpty);
        this.Controls.add(this.lblIgnore);
        this.Controls.add(this.lblEmpty);
        this.Controls.add(this.chkRepair);
        this.Controls.add(this.chkIgnorePolice);
        this.Controls.add(this.chkIgnorePirates);
        this.Controls.add(this.chkIgnoreTraders);
        this.Controls.add(this.chkReserveMoney);
        this.Controls.add(this.chkIgnoreTradersDealing);
        this.Controls.add(this.chkStopTracking);
        this.Controls.add(this.chkRange);
        this.Controls.add(this.chkNewspaper);
        this.Controls.add(this.chkAttackFleeing);
        this.Controls.add(this.chkContinuousAttack);
        this.Controls.add(this.chkFuel);
        this.Controls.add(this.btnCancel);
        this.Controls.add(this.btnOk);
        this.setFormBorderStyle(spacetrader.controls.FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormOptions");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Options");
        ((ISupportInitialize) (this.numEmpty)).endInit();

    }

    // #endregion

    private void UpdateAll() {
        initializing = true;

        chkFuel.setChecked(Options().getAutoFuel());
        chkRepair.setChecked(Options().getAutoRepair());
        chkNewspaper.setChecked(Options().getNewsAutoPay());
        chkNewspaperShow.setChecked(Options().getNewsAutoShow());
        chkLoan.setChecked(Options().getRemindLoans());
        chkRange.setChecked(Options().getShowTrackedRange());
        chkStopTracking.setChecked(Options().getTrackAutoOff());
        chkReserveMoney.setChecked(Options().getReserveMoney());
        numEmpty.setValue(Options().getLeaveEmpty());
        chkIgnorePirates.setChecked(Options().getAlwaysIgnorePirates());
        chkIgnorePolice.setChecked(Options().getAlwaysIgnorePolice());
        chkIgnoreTraders.setChecked(Options().getAlwaysIgnoreTraders());
        chkIgnoreTradersDealing.setChecked(Options().getAlwaysIgnoreTradeInOrbit());
        chkContinuousAttack.setChecked(Options().getContinuousAttack());
        chkAttackFleeing.setChecked(Options().getContinuousAttackFleeing());
        chkDisable.setChecked(Options().getDisableOpponents());

        UpdateContinueAttackFleeing();
        UpdateIgnoreTradersDealing();
        UpdateNewsAutoShow();

        initializing = false;
    }

    private void UpdateContinueAttackFleeing() {
        if (!chkContinuousAttack.isChecked())
            chkAttackFleeing.setChecked(false);

        chkAttackFleeing.setEnabled(chkContinuousAttack.isChecked());
    }

    private void UpdateIgnoreTradersDealing() {
        if (!chkIgnoreTraders.isChecked())
            chkIgnoreTradersDealing.setChecked(false);

        chkIgnoreTradersDealing.setEnabled(chkIgnoreTraders.isChecked());
    }

    private void UpdateNewsAutoShow() {
        if (!chkNewspaper.isChecked())
            chkNewspaperShow.setChecked(false);

        chkNewspaperShow.setEnabled(chkNewspaper.isChecked());
    }

    // #endregion

    // #region Event Handlers

    private void btnLoad_Click(Object sender, EventArgs e) {
        Options().LoadFromDefaults(true);
        UpdateAll();
    }

    private void btnSave_Click(Object sender, EventArgs e) {
        Options().SaveAsDefaults();
    }

    private void controlChanged(Object sender, EventArgs e) {
        if (!initializing) {
            initializing = true;
            UpdateContinueAttackFleeing();
            UpdateIgnoreTradersDealing();
            UpdateNewsAutoShow();
            initializing = false;

            Options().setAutoFuel(chkFuel.isChecked());
            Options().setAutoRepair(chkRepair.isChecked());
            Options().setNewsAutoPay(chkNewspaper.isChecked());
            Options().setNewsAutoShow(chkNewspaperShow.isChecked());
            Options().setRemindLoans(chkLoan.isChecked());
            Options().setShowTrackedRange(chkRange.isChecked());
            Options().setTrackAutoOff(chkStopTracking.isChecked());
            Options().setReserveMoney(chkReserveMoney.isChecked());
            Options().setLeaveEmpty(numEmpty.getValue());
            Options().setAlwaysIgnorePirates(chkIgnorePirates.isChecked());
            Options().setAlwaysIgnorePolice(chkIgnorePolice.isChecked());
            Options().setAlwaysIgnoreTraders(chkIgnoreTraders.isChecked());
            Options().setAlwaysIgnoreTradeInOrbit(chkIgnoreTradersDealing.isChecked());
            Options().setContinuousAttack(chkContinuousAttack.isChecked());
            Options().setContinuousAttackFleeing(chkAttackFleeing.isChecked());
            Options().setDisableOpponents(chkDisable.isChecked());
        }
    }

    // #endregion

    // #region Properties

    public GameOptions Options() {
        return _options;
    }

    // #endregion
}
