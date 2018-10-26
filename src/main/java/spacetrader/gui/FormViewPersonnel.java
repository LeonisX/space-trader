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

package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.BorderStyle;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.CrewMemberId;
import spacetrader.game.enums.Difficulty;
import spacetrader.gui.debug.Launcher;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import java.util.List;

public class FormViewPersonnel extends SpaceTraderForm {

    private Game game = Game.getCurrentGame();

    private Button closeButton = new Button();
    private Panel mercenariesForHirePanel = new Panel();
    private Panel mercenaryInfoPanel = new Panel();
    private Panel currentCrewPanel = new Panel();
    private Button hireFireButton = new Button();
    private Label rateLabelValue = new Label();
    private Label nameLabelValue = new Label();
    private Label engineerLabelValue = new Label();
    private Label traderLabelValue = new Label();
    private Label fighterLabelValue = new Label();
    private Label pilotLabelValue = new Label();
    private Label engineerLabel = new Label();
    private Label traderLabel = new Label();
    private Label fighterLabel = new Label();
    private Label pilotLabel = new Label();
    private ListBox forHireListBox = new ListBox();
    private ListBox crewListBox = new ListBox();
    private Label crewNoQuartersLabel = new Label();
    private Label forHireNoneLabel = new Label();

    private CrewMember selectedCrewMember = null;
    private boolean handlingSelect = false;

    public static void main(String[] args) {
        new Game("name", Difficulty.BEGINNER, 8, 8, 8, 8, null);
        Launcher.runForm(new FormViewPersonnel());
    }

    public FormViewPersonnel() {
        initializeComponent();
        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formViewPersonnel");
        setText("Personnel");
        setAutoScaleBaseSize(5, 13);
        setClientSize(488, 129);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        suspendLayout();

        currentCrewPanel.setLocation(8, 8);
        currentCrewPanel.setSize(144, 114);
        currentCrewPanel.setTabStop(false);
        currentCrewPanel.setText("Current Crew");

        currentCrewPanel.getControls().addAll(crewListBox, crewNoQuartersLabel);

        crewListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        crewListBox.setLocation(8, 24);
        crewListBox.setSize(126, 80);
        crewListBox.setTabIndex(6);
        crewListBox.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                hireFireClick();
            }
        });
        crewListBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        crewNoQuartersLabel.setLocation(16, 24);
        crewNoQuartersLabel.setSize(120, 16);
        crewNoQuartersLabel.setTabIndex(7);
        crewNoQuartersLabel.setText("No quarters available");
        crewNoQuartersLabel.setVisible(false);

        mercenariesForHirePanel.setLocation(160, 8);
        mercenariesForHirePanel.setSize(144, 114);
        mercenariesForHirePanel.setTabStop(false);
        mercenariesForHirePanel.setText("Mercenaries For Hire");

        mercenariesForHirePanel.getControls().addAll(forHireListBox, forHireNoneLabel);

        forHireListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        forHireListBox.setLocation(8, 24);
        forHireListBox.setSize(126, 80);
        forHireListBox.setTabIndex(5);
        forHireListBox.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                hireFireClick();
            }
        });
        forHireListBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        forHireNoneLabel.setLocation(16, 24);
        forHireNoneLabel.setSize(120, 16);
        forHireNoneLabel.setTabIndex(8);
        forHireNoneLabel.setText("No one for hire");
        forHireNoneLabel.setVisible(false);

        mercenaryInfoPanel.setLocation(312, 8);
        mercenaryInfoPanel.setSize(168, 114);
        mercenaryInfoPanel.setTabStop(false);
        mercenaryInfoPanel.setText("Mercenary Information");

        mercenaryInfoPanel.getControls().addAll(nameLabelValue, pilotLabel, pilotLabelValue,
                fighterLabel, fighterLabelValue, traderLabel, traderLabelValue, engineerLabel,
                engineerLabelValue, rateLabelValue, hireFireButton);

        nameLabelValue.setFont(FontCollection.bold825);
        nameLabelValue.setLocation(12, 18);
        nameLabelValue.setSize(72, 13);
        nameLabelValue.setTabIndex(96);
        //nameLabelValue.setText("Alexey Leonov");

        pilotLabel.setAutoSize(true);
        pilotLabel.setLocation(12, 40);
        pilotLabel.setSize(29, 16);
        pilotLabel.setTabIndex(88);
        pilotLabel.setText("Pilot:");

        pilotLabelValue.setLocation(64, 40);
        pilotLabelValue.setSize(17, 13);
        pilotLabelValue.setTabIndex(92);
        //pilotLabelValue.setText("88");

        fighterLabel.setAutoSize(true);
        fighterLabel.setLocation(12, 56);
        fighterLabel.setSize(43, 16);
        fighterLabel.setTabIndex(89);
        fighterLabel.setText("Fighter:");

        fighterLabelValue.setLocation(64, 56);
        fighterLabelValue.setSize(17, 13);
        fighterLabelValue.setTabIndex(93);
        //fighterLabelValue.setText("88");

        traderLabel.setAutoSize(true);
        traderLabel.setLocation(12, 72);
        traderLabel.setSize(41, 16);
        traderLabel.setTabIndex(90);
        traderLabel.setText("Trader:");

        traderLabelValue.setLocation(64, 72);
        traderLabelValue.setSize(17, 13);
        traderLabelValue.setTabIndex(94);
        //traderLabelValue.setText("88");

        engineerLabel.setAutoSize(true);
        engineerLabel.setLocation(12, 88);
        engineerLabel.setSize(53, 16);
        engineerLabel.setTabIndex(91);
        engineerLabel.setText("Engineer:");

        engineerLabelValue.setLocation(64, 88);
        engineerLabelValue.setSize(17, 13);
        engineerLabelValue.setTabIndex(95);
        //engineerLabelValue.setText("88");

        rateLabelValue.setLocation(104, 40);
        rateLabelValue.setSize(59, 13);
        rateLabelValue.setTabIndex(97);
        //rateLabelValue.setText("88 cr. daily");

        hireFireButton.setFlatStyle(FlatStyle.FLAT);
        hireFireButton.setLocation(120, 80);
        hireFireButton.setSize(36, 22);
        hireFireButton.setTabIndex(4);
        hireFireButton.setText("Hire");
        hireFireButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                hireFireClick();
            }
        });

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabIndex(32);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(currentCrewPanel, mercenariesForHirePanel, mercenaryInfoPanel, closeButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void deselectAll() {
        forHireListBox.clearSelected();
        crewListBox.clearSelected();
    }

    private void updateAll() {
        selectedCrewMember = null;

        updateForHire();
        updateCurrentCrew();
        updateInfo();
    }

    private void updateCurrentCrew() {
        CrewMember[] crewMembers = game.getCommander().getShip().getCrew();

        crewListBox.getItems().clear();
        for (int i = 1; i < crewMembers.length; i++) {
            if (crewMembers[i] == null) {
                crewListBox.getItems().add(Strings.PersonnelVacancy);
            } else {
                crewListBox.getItems().add(crewMembers[i]);
            }
        }

        boolean isEntries = (crewListBox.getItems().size() > 0);

        crewListBox.setVisible(isEntries);
        crewNoQuartersLabel.setVisible(!isEntries);

        if (isEntries) {
            crewListBox.setHeight(crewListBox.getItemHeight() * Math.min(crewListBox.getItems().size(), 6) + 2);
        }
    }

    private void updateForHire() {
        List<CrewMember> mercs = game.getCommander().getCurrentSystem().getMercenariesForHire();

        forHireListBox.getItems().clear();
        forHireListBox.getItems().addAll(mercs);

        boolean isEntries = !mercs.isEmpty();

        forHireListBox.setVisible(isEntries);
        forHireNoneLabel.setVisible(!isEntries);

        if (isEntries) {
            forHireListBox.setHeight(forHireListBox.getItemHeight() * Math.min(forHireListBox.getItems().size(), 6) + 2);
        }
    }

    private void updateInfo() {
        boolean visible = false;
        boolean rateVisible = false;
        boolean hireFireVisible = false;

        if (selectedCrewMember != null) {
            visible = true;
            if (selectedCrewMember.getRate() > 0) {
                rateVisible = true;
            }

            nameLabelValue.setText(selectedCrewMember.getName());
            rateLabelValue.setText(Functions.stringVars(Strings.MoneyRateSuffix, Functions.formatMoney(selectedCrewMember
                    .getRate())));
            pilotLabelValue.setText(selectedCrewMember.getPilot());
            fighterLabelValue.setText(selectedCrewMember.getFighter());
            traderLabelValue.setText(selectedCrewMember.getTrader());
            engineerLabelValue.setText(selectedCrewMember.getEngineer());

            hireFireButton.setText(game.getCommander().getShip().hasCrew(selectedCrewMember.getId()) ? Strings.MercenaryFire
                    : Strings.MercenaryHire);
            hireFireVisible = rateVisible || (selectedCrewMember.getId() == CrewMemberId.ZEETHIBAL);
        }

        nameLabelValue.setVisible(visible);
        rateLabelValue.setVisible(rateVisible);
        pilotLabel.setVisible(visible);
        fighterLabel.setVisible(visible);
        traderLabel.setVisible(visible);
        engineerLabel.setVisible(visible);
        pilotLabelValue.setVisible(visible);
        fighterLabelValue.setVisible(visible);
        traderLabelValue.setVisible(visible);
        engineerLabelValue.setVisible(visible);
        hireFireButton.setVisible(hireFireVisible);
    }

    private void hireFireClick() {
        if (selectedCrewMember != null && hireFireButton.isVisible()) {
            if (game.getCommander().getShip().hasCrew(selectedCrewMember.getId())) {
                if (GuiFacade.alert(AlertType.CrewFireMercenary, selectedCrewMember.getName()) == DialogResult.YES) {
                    game.getCommander().getShip().fire(selectedCrewMember.getId());

                    updateAll();
                    game.getParentWindow().updateAll();
                }
            } else {
                if (game.getCommander().getShip().getFreeCrewQuartersCount() == 0)
                    GuiFacade.alert(AlertType.CrewNoQuarters, selectedCrewMember.getName());
                else {
                    game.getCommander().getShip().hire(selectedCrewMember);

                    updateAll();
                    game.getParentWindow().updateAll();
                }
            }
        }
    }

    private void selectedIndexChanged(Object sender) {
        if (!handlingSelect) {
            handlingSelect = true;

            Object obj = ((ListBox) sender).getSelectedItem();
            deselectAll();

            if (obj instanceof CrewMember) {
                ((ListBox) sender).setSelectedItem(obj);
                selectedCrewMember = (CrewMember) obj;
            } else {
                selectedCrewMember = null;
            }

            handlingSelect = false;
            updateInfo();
        }
    }
}
