/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/
// using System;
// using System.Drawing;
// using System.Collections;
// using System.ComponentModel;
// using System.Windows.Forms;
package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.game.CrewMember;
import spacetrader.game.Functions;
import spacetrader.game.Game;
import spacetrader.game.Strings;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.CrewMemberId;
import spacetrader.guifacade.GuiFacade;

public class FormViewPersonnel extends SpaceTraderForm {
    //#region Control Declarations

    private spacetrader.controls.Button btnClose;
    private Panel boxForHire;
    private Panel boxInfo;
    private Panel boxCurrentCrew;
    private spacetrader.controls.Button btnHireFire;
    private spacetrader.controls.Label lblRate;
    private spacetrader.controls.Label lblName;
    private spacetrader.controls.Label lblEngineer;
    private spacetrader.controls.Label lblTrader;
    private spacetrader.controls.Label lblFighter;
    private spacetrader.controls.Label lblPilot;
    private spacetrader.controls.Label lblEngineerLabel;
    private spacetrader.controls.Label lblTraderLabel;
    private spacetrader.controls.Label lblFighterLabel;
    private spacetrader.controls.Label lblPilotLabel;
    private spacetrader.controls.ListBox lstForHire;
    private spacetrader.controls.ListBox lstCrew;
    private spacetrader.controls.Label lblCrewNoQuarters;
    private spacetrader.controls.Label lblForHireNone;
    private Game game = Game.getCurrentGame();
    private CrewMember selectedCrewMember = null;
    private boolean handlingSelect = false;

    //#endregion

    //#region Methods

    public FormViewPersonnel() {
        initializeComponent();

        UpdateAll();
    }

    //#region Windows Form Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void initializeComponent() {
        this.btnClose = new spacetrader.controls.Button();
        this.boxCurrentCrew = new Panel();
        this.lstCrew = new spacetrader.controls.ListBox();
        this.boxForHire = new Panel();
        this.lstForHire = new spacetrader.controls.ListBox();
        this.boxInfo = new Panel();
        this.btnHireFire = new spacetrader.controls.Button();
        this.lblRate = new spacetrader.controls.Label();
        this.lblName = new spacetrader.controls.Label();
        this.lblEngineer = new spacetrader.controls.Label();
        this.lblTrader = new spacetrader.controls.Label();
        this.lblFighter = new spacetrader.controls.Label();
        this.lblPilot = new spacetrader.controls.Label();
        this.lblEngineerLabel = new spacetrader.controls.Label();
        this.lblTraderLabel = new spacetrader.controls.Label();
        this.lblFighterLabel = new spacetrader.controls.Label();
        this.lblPilotLabel = new spacetrader.controls.Label();
        this.lblCrewNoQuarters = new spacetrader.controls.Label();
        this.lblForHireNone = new spacetrader.controls.Label();
        this.boxCurrentCrew.suspendLayout();
        this.boxForHire.suspendLayout();
        this.boxInfo.suspendLayout();
        this.suspendLayout();
        //
        // btnClose
        //
        this.btnClose.setDialogResult(DialogResult.CANCEL);
        this.btnClose.setLocation(new java.awt.Point(-32, -32));
        this.btnClose.setName("btnClose");
        this.btnClose.setSize(new spacetrader.controls.Size(32, 32));
        this.btnClose.setTabIndex(32);
        this.btnClose.setTabStop(false);
        this.btnClose.setText("X");
        //
        // boxCurrentCrew
        //
        this.boxCurrentCrew.controls.add(this.lblCrewNoQuarters);
        this.boxCurrentCrew.controls.add(this.lstCrew);
        this.boxCurrentCrew.setLocation(new java.awt.Point(8, 8));
        this.boxCurrentCrew.setName("boxCurrentCrew");
        this.boxCurrentCrew.setSize(new spacetrader.controls.Size(144, 114));
        this.boxCurrentCrew.setTabIndex(33);
        this.boxCurrentCrew.setTabStop(false);
        this.boxCurrentCrew.setText("Current Crew");
        //
        // lstCrew
        //
        this.lstCrew.setBorderStyle(spacetrader.controls.BorderStyle.FIXED_SINGLE);
        this.lstCrew.setLocation(new java.awt.Point(8, 24));
        this.lstCrew.setName("lstCrew");
        this.lstCrew.setSize(new spacetrader.controls.Size(126, 80));
        this.lstCrew.setTabIndex(6);
        this.lstCrew.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                HireFire(sender, e);
            }
        });
        this.lstCrew.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                SelectedIndexChanged(sender, e);
            }
        });
        //
        // boxForHire
        //
        this.boxForHire.controls.add(this.lblForHireNone);
        this.boxForHire.controls.add(this.lstForHire);
        this.boxForHire.setLocation(new java.awt.Point(160, 8));
        this.boxForHire.setName("boxForHire");
        this.boxForHire.setSize(new spacetrader.controls.Size(144, 114));
        this.boxForHire.setTabIndex(34);
        this.boxForHire.setTabStop(false);
        this.boxForHire.setText("Mercenaries For Hire");
        //
        // lstForHire
        //
        this.lstForHire.setBorderStyle(spacetrader.controls.BorderStyle.FIXED_SINGLE);
        this.lstForHire.setLocation(new java.awt.Point(8, 24));
        this.lstForHire.setName("lstForHire");
        this.lstForHire.setSize(new spacetrader.controls.Size(126, 80));
        this.lstForHire.setTabIndex(5);
        this.lstForHire.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                HireFire(sender, e);
            }
        });
        this.lstForHire.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                SelectedIndexChanged(sender, e);
            }
        });
        //
        // boxInfo
        //
        this.boxInfo.controls.add(this.btnHireFire);
        this.boxInfo.controls.add(this.lblRate);
        this.boxInfo.controls.add(this.lblName);
        this.boxInfo.controls.add(this.lblEngineer);
        this.boxInfo.controls.add(this.lblTrader);
        this.boxInfo.controls.add(this.lblFighter);
        this.boxInfo.controls.add(this.lblPilot);
        this.boxInfo.controls.add(this.lblEngineerLabel);
        this.boxInfo.controls.add(this.lblTraderLabel);
        this.boxInfo.controls.add(this.lblFighterLabel);
        this.boxInfo.controls.add(this.lblPilotLabel);
        this.boxInfo.setLocation(new java.awt.Point(312, 8));
        this.boxInfo.setName("boxInfo");
        this.boxInfo.setSize(new spacetrader.controls.Size(168, 114));
        this.boxInfo.setTabIndex(35);
        this.boxInfo.setTabStop(false);
        this.boxInfo.setText("Mercenary Information");
        //
        // btnHireFire
        //
        this.btnHireFire.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        this.btnHireFire.setLocation(new java.awt.Point(120, 80));
        this.btnHireFire.setName("btnHireFire");
        this.btnHireFire.setSize(new spacetrader.controls.Size(36, 22));
        this.btnHireFire.setTabIndex(4);
        this.btnHireFire.setText("Hire");
        this.btnHireFire.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                HireFire(sender, e);
            }
        });
        //
        // lblRate
        //
        this.lblRate.setLocation(new java.awt.Point(104, 40));
        this.lblRate.setName("lblRate");
        this.lblRate.setSize(new spacetrader.controls.Size(59, 13));
        this.lblRate.setTabIndex(97);
        this.lblRate.setText("88 cr. daily");
        //
        // lblName
        //
        this.lblName.setFont(FontCollection.bold825);
        this.lblName.setLocation(new java.awt.Point(12, 18));
        this.lblName.setName("lblName");
        this.lblName.setSize(new spacetrader.controls.Size(72, 13));
        this.lblName.setTabIndex(96);
        this.lblName.setText("Xxxxxxxxxxx");
        //
        // lblEngineer
        //
        this.lblEngineer.setLocation(new java.awt.Point(64, 88));
        this.lblEngineer.setName("lblEngineer");
        this.lblEngineer.setSize(new spacetrader.controls.Size(17, 13));
        this.lblEngineer.setTabIndex(95);
        this.lblEngineer.setText("88");
        //
        // lblTrader
        //
        this.lblTrader.setLocation(new java.awt.Point(64, 72));
        this.lblTrader.setName("lblTrader");
        this.lblTrader.setSize(new spacetrader.controls.Size(17, 13));
        this.lblTrader.setTabIndex(94);
        this.lblTrader.setText("88");
        //
        // lblFighter
        //
        this.lblFighter.setLocation(new java.awt.Point(64, 56));
        this.lblFighter.setName("lblFighter");
        this.lblFighter.setSize(new spacetrader.controls.Size(17, 13));
        this.lblFighter.setTabIndex(93);
        this.lblFighter.setText("88");
        //
        // lblPilot
        //
        this.lblPilot.setLocation(new java.awt.Point(64, 40));
        this.lblPilot.setName("lblPilot");
        this.lblPilot.setSize(new spacetrader.controls.Size(17, 13));
        this.lblPilot.setTabIndex(92);
        this.lblPilot.setText("88");
        //
        // lblEngineerLabel
        //
        this.lblEngineerLabel.setAutoSize(true);
        this.lblEngineerLabel.setLocation(new java.awt.Point(12, 88));
        this.lblEngineerLabel.setName("lblEngineerLabel");
        this.lblEngineerLabel.setSize(new spacetrader.controls.Size(53, 16));
        this.lblEngineerLabel.setTabIndex(91);
        this.lblEngineerLabel.setText("Engineer:");
        //
        // lblTraderLabel
        //
        this.lblTraderLabel.setAutoSize(true);
        this.lblTraderLabel.setLocation(new java.awt.Point(12, 72));
        this.lblTraderLabel.setName("lblTraderLabel");
        this.lblTraderLabel.setSize(new spacetrader.controls.Size(41, 16));
        this.lblTraderLabel.setTabIndex(90);
        this.lblTraderLabel.setText("Trader:");
        //
        // lblFighterLabel
        //
        this.lblFighterLabel.setAutoSize(true);
        this.lblFighterLabel.setLocation(new java.awt.Point(12, 56));
        this.lblFighterLabel.setName("lblFighterLabel");
        this.lblFighterLabel.setSize(new spacetrader.controls.Size(43, 16));
        this.lblFighterLabel.setTabIndex(89);
        this.lblFighterLabel.setText("Fighter:");
        //
        // lblPilotLabel
        //
        this.lblPilotLabel.setAutoSize(true);
        this.lblPilotLabel.setLocation(new java.awt.Point(12, 40));
        this.lblPilotLabel.setName("lblPilotLabel");
        this.lblPilotLabel.setSize(new spacetrader.controls.Size(29, 16));
        this.lblPilotLabel.setTabIndex(88);
        this.lblPilotLabel.setText("Pilot:");
        //
        // lblCrewNoQuarters
        //
        this.lblCrewNoQuarters.setLocation(new java.awt.Point(16, 24));
        this.lblCrewNoQuarters.setName("lblCrewNoQuarters");
        this.lblCrewNoQuarters.setSize(new spacetrader.controls.Size(120, 16));
        this.lblCrewNoQuarters.setTabIndex(7);
        this.lblCrewNoQuarters.setText("No quarters available");
        this.lblCrewNoQuarters.setVisible(false);
        //
        // lblForHireNone
        //
        this.lblForHireNone.setLocation(new java.awt.Point(16, 24));
        this.lblForHireNone.setName("lblForHireNone");
        this.lblForHireNone.setSize(new spacetrader.controls.Size(120, 16));
        this.lblForHireNone.setTabIndex(8);
        this.lblForHireNone.setText("No one for hire");
        this.lblForHireNone.setVisible(false);
        //
        // FormViewPersonnel
        //
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(this.btnClose);
        this.setClientSize(new spacetrader.controls.Size(488, 129));
        this.controls.add(this.boxInfo);
        this.controls.add(this.boxForHire);
        this.controls.add(this.boxCurrentCrew);
        this.controls.add(this.btnClose);
        this.setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormViewPersonnel");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setText("Personnel");
    }

    //#endregion

    private void DeselectAll() {
        lstForHire.clearSelected();
        lstCrew.clearSelected();
    }

    private void UpdateAll() {
        selectedCrewMember = null;

        UpdateForHire();
        UpdateCurrentCrew();
        UpdateInfo();
    }

    private void UpdateCurrentCrew() {
        CrewMember[] crew = game.getCommander().getShip().Crew();

        lstCrew.getItems().clear();
        for (int i = 1; i < crew.length; i++) {
            if (crew[i] == null)
                lstCrew.getItems().add(Strings.PersonnelVacancy);
            else
                lstCrew.getItems().add(crew[i]);
        }

        boolean entries = (lstCrew.getItems().size() > 0);

        lstCrew.setVisible(entries);
        lblCrewNoQuarters.setVisible(!entries);

        if (entries)
            lstCrew.setHeight(lstCrew.getItemHeight() * Math.min(lstCrew.getItems().size(), 6) + 2);
        else
            // TODO: remove this when Strings are moved to resource.
            lblCrewNoQuarters.setText(Strings.PersonnelNoQuarters);
    }

    private void UpdateForHire() {
        CrewMember[] mercs = game.getCommander().getCurrentSystem().mercenariesForHire();

        lstForHire.getItems().clear();
        for (int i = 0; i < mercs.length; i++)
            lstForHire.getItems().add(mercs[i]);

        boolean entries = (lstForHire.getItems().size() > 0);

        lstForHire.setVisible(entries);
        lblForHireNone.setVisible(!entries);

        if (entries)
            lstForHire.setHeight(lstForHire.getItemHeight() * Math.min(lstForHire.getItems().size(), 6) + 2);
        else
            // TODO: remove this when Strings are moved to resource.
            lblForHireNone.setText(Strings.PersonnelNoMercenaries);
    }

    private void UpdateInfo() {
        boolean visible = false;
        boolean rateVisible = false;
        boolean hireFireVisible = false;

        if (selectedCrewMember != null) {
            visible = true;
            if (selectedCrewMember.Rate() > 0)
                rateVisible = true;

            lblName.setText(selectedCrewMember.Name());
            lblRate.setText(Functions.stringVars(Strings.MoneyRateSuffix, Functions.formatMoney(selectedCrewMember
                    .Rate())));
            lblPilot.setText(selectedCrewMember.Pilot() + "");
            lblFighter.setText(selectedCrewMember.Fighter() + "");
            lblTrader.setText(selectedCrewMember.Trader() + "");
            lblEngineer.setText(selectedCrewMember.Engineer() + "");

            btnHireFire.setText(game.getCommander().getShip().HasCrew(selectedCrewMember.Id()) ? Strings.MercenaryFire
                    : Strings.MercenaryHire);
            hireFireVisible = rateVisible || selectedCrewMember.Id() == CrewMemberId.Zeethibal;
        }

        lblName.setVisible(visible);
        lblRate.setVisible(rateVisible);
        lblPilotLabel.setVisible(visible);
        lblFighterLabel.setVisible(visible);
        lblTraderLabel.setVisible(visible);
        lblEngineerLabel.setVisible(visible);
        lblPilot.setVisible(visible);
        lblFighter.setVisible(visible);
        lblTrader.setVisible(visible);
        lblEngineer.setVisible(visible);
        btnHireFire.setVisible(hireFireVisible);
    }

    //#endregion

    //#region Event Handlers

    private void HireFire(Object sender, EventArgs e) {
        if (selectedCrewMember != null && btnHireFire.isVisible()) {
            if (game.getCommander().getShip().HasCrew(selectedCrewMember.Id())) {
                if (GuiFacade.alert(AlertType.CrewFireMercenary, selectedCrewMember.Name()) == DialogResult.YES) {
                    game.getCommander().getShip().Fire(selectedCrewMember.Id());

                    UpdateAll();
                    game.getParentWindow().updateAll();
                }
            } else {
                if (game.getCommander().getShip().FreeCrewQuarters() == 0)
                    GuiFacade.alert(AlertType.CrewNoQuarters, selectedCrewMember.Name());
                else {
                    game.getCommander().getShip().Hire(selectedCrewMember);

                    UpdateAll();
                    game.getParentWindow().updateAll();
                }
            }
        }
    }

    private void SelectedIndexChanged(Object sender, EventArgs e) {
        if (!handlingSelect) {
            handlingSelect = true;

            Object obj = ((ListBox) sender).getSelectedItem();
            DeselectAll();

            if (obj instanceof CrewMember) {
                ((ListBox) sender).setSelectedItem(obj);
                selectedCrewMember = (CrewMember) obj;
            } else
                selectedCrewMember = null;

            handlingSelect = false;
            UpdateInfo();
        }
    }

    //#endregion
}
