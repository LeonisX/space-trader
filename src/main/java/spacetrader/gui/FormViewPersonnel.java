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

import jwinforms.*;
import spacetrader.CrewMember;
import spacetrader.Functions;
import spacetrader.Game;
import spacetrader.Strings;
import spacetrader.enums.AlertType;
import spacetrader.enums.CrewMemberId;
import spacetrader.guifacade.GuiFacade;

public class FormViewPersonnel extends SpaceTraderForm {
    //#region Control Declarations

    private jwinforms.Button btnClose;
    private jwinforms.GroupBox boxForHire;
    private jwinforms.GroupBox boxInfo;
    private jwinforms.GroupBox boxCurrentCrew;
    private jwinforms.Button btnHireFire;
    private jwinforms.Label lblRate;
    private jwinforms.Label lblName;
    private jwinforms.Label lblEngineer;
    private jwinforms.Label lblTrader;
    private jwinforms.Label lblFighter;
    private jwinforms.Label lblPilot;
    private jwinforms.Label lblEngineerLabel;
    private jwinforms.Label lblTraderLabel;
    private jwinforms.Label lblFighterLabel;
    private jwinforms.Label lblPilotLabel;
    private jwinforms.ListBox lstForHire;
    private jwinforms.ListBox lstCrew;
    private jwinforms.Label lblCrewNoQuarters;
    private jwinforms.Label lblForHireNone;
    private Game game = Game.CurrentGame();
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
        this.btnClose = new jwinforms.Button();
        this.boxCurrentCrew = new jwinforms.GroupBox();
        this.lstCrew = new jwinforms.ListBox();
        this.boxForHire = new jwinforms.GroupBox();
        this.lstForHire = new jwinforms.ListBox();
        this.boxInfo = new jwinforms.GroupBox();
        this.btnHireFire = new jwinforms.Button();
        this.lblRate = new jwinforms.Label();
        this.lblName = new jwinforms.Label();
        this.lblEngineer = new jwinforms.Label();
        this.lblTrader = new jwinforms.Label();
        this.lblFighter = new jwinforms.Label();
        this.lblPilot = new jwinforms.Label();
        this.lblEngineerLabel = new jwinforms.Label();
        this.lblTraderLabel = new jwinforms.Label();
        this.lblFighterLabel = new jwinforms.Label();
        this.lblPilotLabel = new jwinforms.Label();
        this.lblCrewNoQuarters = new jwinforms.Label();
        this.lblForHireNone = new jwinforms.Label();
        this.boxCurrentCrew.suspendLayout();
        this.boxForHire.suspendLayout();
        this.boxInfo.suspendLayout();
        this.suspendLayout();
        //
        // btnClose
        //
        this.btnClose.setDialogResult(DialogResult.Cancel);
        this.btnClose.setLocation(new java.awt.Point(-32, -32));
        this.btnClose.setName("btnClose");
        this.btnClose.setSize(new jwinforms.Size(32, 32));
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
        this.boxCurrentCrew.setSize(new jwinforms.Size(144, 114));
        this.boxCurrentCrew.setTabIndex(33);
        this.boxCurrentCrew.setTabStop(false);
        this.boxCurrentCrew.setText("Current Crew");
        //
        // lstCrew
        //
        this.lstCrew.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        this.lstCrew.setLocation(new java.awt.Point(8, 24));
        this.lstCrew.setName("lstCrew");
        this.lstCrew.setSize(new jwinforms.Size(126, 80));
        this.lstCrew.setTabIndex(6);
        this.lstCrew.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                HireFire(sender, e);
            }
        });
        this.lstCrew.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
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
        this.boxForHire.setSize(new jwinforms.Size(144, 114));
        this.boxForHire.setTabIndex(34);
        this.boxForHire.setTabStop(false);
        this.boxForHire.setText("Mercenaries For Hire");
        //
        // lstForHire
        //
        this.lstForHire.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        this.lstForHire.setLocation(new java.awt.Point(8, 24));
        this.lstForHire.setName("lstForHire");
        this.lstForHire.setSize(new jwinforms.Size(126, 80));
        this.lstForHire.setTabIndex(5);
        this.lstForHire.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                HireFire(sender, e);
            }
        });
        this.lstForHire.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
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
        this.boxInfo.setSize(new jwinforms.Size(168, 114));
        this.boxInfo.setTabIndex(35);
        this.boxInfo.setTabStop(false);
        this.boxInfo.setText("Mercenary Information");
        //
        // btnHireFire
        //
        this.btnHireFire.setFlatStyle(jwinforms.FlatStyle.Flat);
        this.btnHireFire.setLocation(new java.awt.Point(120, 80));
        this.btnHireFire.setName("btnHireFire");
        this.btnHireFire.setSize(new jwinforms.Size(36, 22));
        this.btnHireFire.setTabIndex(4);
        this.btnHireFire.setText("Hire");
        this.btnHireFire.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, jwinforms.EventArgs e) {
                HireFire(sender, e);
            }
        });
        //
        // lblRate
        //
        this.lblRate.setLocation(new java.awt.Point(104, 40));
        this.lblRate.setName("lblRate");
        this.lblRate.setSize(new jwinforms.Size(59, 13));
        this.lblRate.setTabIndex(97);
        this.lblRate.setText("88 cr. daily");
        //
        // lblName
        //
        this.lblName.setFont(FontCollection.bold825);
        this.lblName.setLocation(new java.awt.Point(12, 18));
        this.lblName.setName("lblName");
        this.lblName.setSize(new jwinforms.Size(72, 13));
        this.lblName.setTabIndex(96);
        this.lblName.setText("Xxxxxxxxxxx");
        //
        // lblEngineer
        //
        this.lblEngineer.setLocation(new java.awt.Point(64, 88));
        this.lblEngineer.setName("lblEngineer");
        this.lblEngineer.setSize(new jwinforms.Size(17, 13));
        this.lblEngineer.setTabIndex(95);
        this.lblEngineer.setText("88");
        //
        // lblTrader
        //
        this.lblTrader.setLocation(new java.awt.Point(64, 72));
        this.lblTrader.setName("lblTrader");
        this.lblTrader.setSize(new jwinforms.Size(17, 13));
        this.lblTrader.setTabIndex(94);
        this.lblTrader.setText("88");
        //
        // lblFighter
        //
        this.lblFighter.setLocation(new java.awt.Point(64, 56));
        this.lblFighter.setName("lblFighter");
        this.lblFighter.setSize(new jwinforms.Size(17, 13));
        this.lblFighter.setTabIndex(93);
        this.lblFighter.setText("88");
        //
        // lblPilot
        //
        this.lblPilot.setLocation(new java.awt.Point(64, 40));
        this.lblPilot.setName("lblPilot");
        this.lblPilot.setSize(new jwinforms.Size(17, 13));
        this.lblPilot.setTabIndex(92);
        this.lblPilot.setText("88");
        //
        // lblEngineerLabel
        //
        this.lblEngineerLabel.setAutoSize(true);
        this.lblEngineerLabel.setLocation(new java.awt.Point(12, 88));
        this.lblEngineerLabel.setName("lblEngineerLabel");
        this.lblEngineerLabel.setSize(new jwinforms.Size(53, 16));
        this.lblEngineerLabel.setTabIndex(91);
        this.lblEngineerLabel.setText("Engineer:");
        //
        // lblTraderLabel
        //
        this.lblTraderLabel.setAutoSize(true);
        this.lblTraderLabel.setLocation(new java.awt.Point(12, 72));
        this.lblTraderLabel.setName("lblTraderLabel");
        this.lblTraderLabel.setSize(new jwinforms.Size(41, 16));
        this.lblTraderLabel.setTabIndex(90);
        this.lblTraderLabel.setText("Trader:");
        //
        // lblFighterLabel
        //
        this.lblFighterLabel.setAutoSize(true);
        this.lblFighterLabel.setLocation(new java.awt.Point(12, 56));
        this.lblFighterLabel.setName("lblFighterLabel");
        this.lblFighterLabel.setSize(new jwinforms.Size(43, 16));
        this.lblFighterLabel.setTabIndex(89);
        this.lblFighterLabel.setText("Fighter:");
        //
        // lblPilotLabel
        //
        this.lblPilotLabel.setAutoSize(true);
        this.lblPilotLabel.setLocation(new java.awt.Point(12, 40));
        this.lblPilotLabel.setName("lblPilotLabel");
        this.lblPilotLabel.setSize(new jwinforms.Size(29, 16));
        this.lblPilotLabel.setTabIndex(88);
        this.lblPilotLabel.setText("Pilot:");
        //
        // lblCrewNoQuarters
        //
        this.lblCrewNoQuarters.setLocation(new java.awt.Point(16, 24));
        this.lblCrewNoQuarters.setName("lblCrewNoQuarters");
        this.lblCrewNoQuarters.setSize(new jwinforms.Size(120, 16));
        this.lblCrewNoQuarters.setTabIndex(7);
        this.lblCrewNoQuarters.setText("No quarters available");
        this.lblCrewNoQuarters.setVisible(false);
        //
        // lblForHireNone
        //
        this.lblForHireNone.setLocation(new java.awt.Point(16, 24));
        this.lblForHireNone.setName("lblForHireNone");
        this.lblForHireNone.setSize(new jwinforms.Size(120, 16));
        this.lblForHireNone.setTabIndex(8);
        this.lblForHireNone.setText("No one for hire");
        this.lblForHireNone.setVisible(false);
        //
        // FormViewPersonnel
        //
        this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setCancelButton(this.btnClose);
        this.setClientSize(new jwinforms.Size(488, 129));
        this.Controls.add(this.boxInfo);
        this.Controls.add(this.boxForHire);
        this.Controls.add(this.boxCurrentCrew);
        this.Controls.add(this.btnClose);
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormViewPersonnel");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
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
        CrewMember[] crew = game.Commander().getShip().Crew();

        lstCrew.Items.clear();
        for (int i = 1; i < crew.length; i++) {
            if (crew[i] == null)
                lstCrew.Items.add(Strings.PersonnelVacancy);
            else
                lstCrew.Items.add(crew[i]);
        }

        boolean entries = (lstCrew.Items.size() > 0);

        lstCrew.setVisible(entries);
        lblCrewNoQuarters.setVisible(!entries);

        if (entries)
            lstCrew.setHeight(lstCrew.getItemHeight() * Math.min(lstCrew.Items.size(), 6) + 2);
        else
            // TODO: remove this when Strings are moved to resource.
            lblCrewNoQuarters.setText(Strings.PersonnelNoQuarters);
    }

    private void UpdateForHire() {
        CrewMember[] mercs = game.Commander().getCurrentSystem().MercenariesForHire();

        lstForHire.Items.clear();
        for (int i = 0; i < mercs.length; i++)
            lstForHire.Items.add(mercs[i]);

        boolean entries = (lstForHire.Items.size() > 0);

        lstForHire.setVisible(entries);
        lblForHireNone.setVisible(!entries);

        if (entries)
            lstForHire.setHeight(lstForHire.getItemHeight() * Math.min(lstForHire.Items.size(), 6) + 2);
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
            lblRate.setText(Functions.StringVars(Strings.MoneyRateSuffix, Functions.formatMoney(selectedCrewMember
                    .Rate())));
            lblPilot.setText(selectedCrewMember.Pilot() + "");
            lblFighter.setText(selectedCrewMember.Fighter() + "");
            lblTrader.setText(selectedCrewMember.Trader() + "");
            lblEngineer.setText(selectedCrewMember.Engineer() + "");

            btnHireFire.setText(game.Commander().getShip().HasCrew(selectedCrewMember.Id()) ? Strings.MercenaryFire
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
        if (selectedCrewMember != null && btnHireFire.getVisible()) {
            if (game.Commander().getShip().HasCrew(selectedCrewMember.Id())) {
                if (GuiFacade.alert(AlertType.CrewFireMercenary, selectedCrewMember.Name()) == DialogResult.Yes) {
                    game.Commander().getShip().Fire(selectedCrewMember.Id());

                    UpdateAll();
                    game.getParentWindow().updateAll();
                }
            } else {
                if (game.Commander().getShip().FreeCrewQuarters() == 0)
                    GuiFacade.alert(AlertType.CrewNoQuarters, selectedCrewMember.Name());
                else {
                    game.Commander().getShip().Hire(selectedCrewMember);

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
