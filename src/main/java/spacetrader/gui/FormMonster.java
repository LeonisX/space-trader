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
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.game.enums.ShipyardId;
import spacetrader.game.*;
import spacetrader.stub.ArrayList;
import spacetrader.util.CheatCode;
import spacetrader.util.ReflectionUtils;
import spacetrader.util.Util;

import java.awt.*;

@CheatCode
class FormMonster extends SpaceTraderForm {

    private static final int SPLIT_SYSTEMS = 31;

    private final Game game = Game.getCurrentGame();

    private Button btnClose = new Button();
    private SimplePanel pnlMercs = new SimplePanel();
    private SimplePanel pnlQuests = new SimplePanel();
    private SimplePanel pnlShipyards = new SimplePanel();
    private HorizontalLine picLine1 = new HorizontalLine();
    private HorizontalLine picLine0 = new HorizontalLine();
    private HorizontalLine picLine2 = new HorizontalLine();
    private Label lblMercLabel = new Label();
    private Label lblQuestsLabel = new Label();
    private Label lblShipyardsLabel = new Label();
    private LinkLabel lblMercIDLabel = new LinkLabel();
    private LinkLabel lblMercNameLabel = new LinkLabel();
    private LinkLabel lblMercSkillLabelPilot = new LinkLabel();
    private LinkLabel lblMercSkillLabelFighter = new LinkLabel();
    private LinkLabel lblMercSkillLabelTrader = new LinkLabel();
    private LinkLabel lblMercSkillLabelEngineer = new LinkLabel();
    private LinkLabel lblMercSystemLabel = new LinkLabel();
    private LinkLabel lblQuestSystemLabel = new LinkLabel();
    private LinkLabel lblQuestDescLabel = new LinkLabel();
    private LinkLabel lblShipyardsSystemLabel = new LinkLabel();
    private LinkLabel lblShipyardsDescLabel = new LinkLabel();
    private Label lblMercIds = new Label();
    private Label lblMercNames = new Label();
    private Label lblMercSkillsPilot = new Label();
    private Label lblMercSkillsFighter = new Label();
    private Label lblMercSkillsTrader = new Label();
    private Label lblMercSkillsEngineer = new Label();
    private LinkLabel lblMercSystems = new LinkLabel();
    private LinkLabel lblMercSystems2 = new LinkLabel();
    private LinkLabel lblQuestSystems = new LinkLabel();
    private Label lblQuests = new Label();
    private LinkLabel lblShipyardSystems = new LinkLabel();

    private Label lblShipyards = new Label();

    private Integer[] mercIds;
    private Integer[] questSystemIds;
    private Integer[] shipyardSystemIds;

    FormMonster() {
        initializeComponent();

        populateIdArrays();

        setLabelHeights();

        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        this.setName("formMonster");
        this.setText("Monster.com Job Listing");
        this.setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setAutoScaleBaseSize(new Size(5, 13));
        this.setClientSize(new Size(617, 358));
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setShowInTaskbar(false);
        this.setCancelButton(btnClose);
        
        this.suspendLayout();

        picLine1.setBackground(java.awt.Color.darkGray);
        picLine1.setLocation(new Point(4, 40));
        picLine1.setSize(new Size(609, 1));
        picLine1.setTabIndex(133);
        picLine1.setTabStop(false);

        picLine0.setBackground(java.awt.Color.darkGray);
        picLine0.setLocation(new Point(234, 8));
        picLine0.setSize(new Size(1, 347));
        picLine0.setTabIndex(132);
        picLine0.setTabStop(false);

        lblQuestsLabel.setAutoSize(true);
        lblQuestsLabel.setFont(FontCollection.bold10);
        lblQuestsLabel.setLocation(new Point(88, 4));
        lblQuestsLabel.setSize(new Size(50, 19));
        lblQuestsLabel.setTabIndex(134);
        lblQuestsLabel.setText("Quests");

        lblMercLabel.setAutoSize(true);
        lblMercLabel.setFont(FontCollection.bold10);
        lblMercLabel.setLocation(new Point(348, 4));
        lblMercLabel.setSize(new Size(84, 19));
        lblMercLabel.setTabIndex(141);
        lblMercLabel.setText("Mercenaries");

        lblMercSkillLabelPilot.setAutoSize(true);
        lblMercSkillLabelPilot.setFont(FontCollection.bold825);
        lblMercSkillLabelPilot.setLocation(new Point(341, 24));
        lblMercSkillLabelPilot.setSize(new Size(12, 16));
        lblMercSkillLabelPilot.setTabIndex(7);
        lblMercSkillLabelPilot.setTabStop(true);
        lblMercSkillLabelPilot.setText("P");
        lblMercSkillLabelPilot.textAlign = ContentAlignment.TOP_RIGHT;
        lblMercSkillLabelPilot.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblMercSkillLabelFighter.setAutoSize(true);
        lblMercSkillLabelFighter.setFont(FontCollection.bold825);
        lblMercSkillLabelFighter.setLocation(new Point(362, 24));
        lblMercSkillLabelFighter.setSize(new Size(11, 16));
        lblMercSkillLabelFighter.setTabIndex(8);
        lblMercSkillLabelFighter.setTabStop(true);
        lblMercSkillLabelFighter.setText("F");
        lblMercSkillLabelFighter.textAlign = ContentAlignment.TOP_RIGHT;
        lblMercSkillLabelFighter.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblMercSkillLabelTrader.setAutoSize(true);
        lblMercSkillLabelTrader.setFont(FontCollection.bold825);
        lblMercSkillLabelTrader.setLocation(new Point(382, 24));
        lblMercSkillLabelTrader.setSize(new Size(11, 16));
        lblMercSkillLabelTrader.setTabIndex(9);
        lblMercSkillLabelTrader.setTabStop(true);
        lblMercSkillLabelTrader.setText("T");
        lblMercSkillLabelTrader.textAlign = ContentAlignment.TOP_RIGHT;
        lblMercSkillLabelTrader.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblMercSkillLabelEngineer.setAutoSize(true);
        lblMercSkillLabelEngineer.setFont(FontCollection.bold825);
        lblMercSkillLabelEngineer.setLocation(new Point(401, 24));
        lblMercSkillLabelEngineer.setSize(new Size(12, 16));
        lblMercSkillLabelEngineer.setTabIndex(10);
        lblMercSkillLabelEngineer.setTabStop(true);
        lblMercSkillLabelEngineer.setText("E");
        lblMercSkillLabelEngineer.textAlign = ContentAlignment.TOP_RIGHT;
        lblMercSkillLabelEngineer.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblMercSystemLabel.setAutoSize(true);
        lblMercSystemLabel.setFont(FontCollection.bold825);
        lblMercSystemLabel.setLocation(new Point(425, 24));
        lblMercSystemLabel.setSize(new Size(43, 16));
        lblMercSystemLabel.setTabIndex(11);
        lblMercSystemLabel.setTabStop(true);
        lblMercSystemLabel.setText("System");
        lblMercSystemLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblQuestSystemLabel.setAutoSize(true);
        lblQuestSystemLabel.setFont(FontCollection.bold825);
        lblQuestSystemLabel.setLocation(new Point(13, 24));
        lblQuestSystemLabel.setSize(new Size(43, 16));
        lblQuestSystemLabel.setTabIndex(1);
        lblQuestSystemLabel.setTabStop(true);
        lblQuestSystemLabel.setText("System");
        lblQuestSystemLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblQuestDescLabel.setAutoSize(true);
        lblQuestDescLabel.setFont(FontCollection.bold825);
        lblQuestDescLabel.setLocation(new Point(85, 24));
        lblQuestDescLabel.setSize(new Size(63, 16));
        lblQuestDescLabel.setTabIndex(2);
        lblQuestDescLabel.setTabStop(true);
        lblQuestDescLabel.setText("Description");
        lblQuestDescLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblMercIDLabel.setAutoSize(true);
        lblMercIDLabel.setFont(FontCollection.bold825);
        lblMercIDLabel.setLocation(new Point(247, 24));
        lblMercIDLabel.setSize(new Size(16, 16));
        lblMercIDLabel.setTabIndex(5);
        lblMercIDLabel.setTabStop(true);
        lblMercIDLabel.setText("ID");
        lblMercIDLabel.textAlign = ContentAlignment.TOP_RIGHT;
        lblMercIDLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblMercNameLabel.setAutoSize(true);
        lblMercNameLabel.setFont(FontCollection.bold825);
        lblMercNameLabel.setLocation(new Point(268, 24));
        lblMercNameLabel.setSize(new Size(35, 16));
        lblMercNameLabel.setTabIndex(6);
        lblMercNameLabel.setTabStop(true);
        lblMercNameLabel.setText("Name");
        lblMercNameLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblShipyardsDescLabel.setAutoSize(true);
        lblShipyardsDescLabel.setFont(FontCollection.bold825);
        lblShipyardsDescLabel.setLocation(new Point(85, 258));
        lblShipyardsDescLabel.setSize(new Size(63, 16));
        lblShipyardsDescLabel.setTabIndex(4);
        lblShipyardsDescLabel.setTabStop(true);
        lblShipyardsDescLabel.setText("Description");
        lblShipyardsDescLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblShipyardsSystemLabel.setAutoSize(true);
        lblShipyardsSystemLabel.setFont(FontCollection.bold825);
        lblShipyardsSystemLabel.setLocation(new Point(13, 258));
        lblShipyardsSystemLabel.setSize(new Size(43, 16));
        lblShipyardsSystemLabel.setTabIndex(3);
        lblShipyardsSystemLabel.setTabStop(true);
        lblShipyardsSystemLabel.setText("System");
        lblShipyardsSystemLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        lblShipyardsLabel.setAutoSize(true);
        lblShipyardsLabel.setFont(FontCollection.bold10);
        lblShipyardsLabel.setLocation(new Point(79, 238));
        lblShipyardsLabel.setSize(new Size(68, 19));
        lblShipyardsLabel.setTabIndex(155);
        lblShipyardsLabel.setText("Shipyards");

        pnlMercs.autoScroll = true;
        pnlMercs.setBorderStyle(BorderStyle.FIXED_SINGLE);
        pnlMercs.controls.add(lblMercSkillsPilot);
        pnlMercs.controls.add(lblMercSkillsFighter);
        pnlMercs.controls.add(lblMercSkillsTrader);
        pnlMercs.controls.add(lblMercSkillsEngineer);
        pnlMercs.controls.add(lblMercSystems);
        pnlMercs.controls.add(lblMercIds);
        pnlMercs.controls.add(lblMercNames);
        pnlMercs.controls.add(lblMercSystems2);
        pnlMercs.setLocation(new Point(239, 44));
        pnlMercs.setSize(new Size(371, 307));
        pnlMercs.setTabIndex(158);

        lblMercSkillsPilot.setLocation(new Point(93, 4));
        lblMercSkillsPilot.setSize(new Size(20, 563));
        lblMercSkillsPilot.setTabIndex(144);
        lblMercSkillsPilot.textAlign = ContentAlignment.TOP_RIGHT;

        lblMercSkillsFighter.setLocation(new Point(113, 4));
        lblMercSkillsFighter.setSize(new Size(20, 563));
        lblMercSkillsFighter.setTabIndex(145);
        lblMercSkillsFighter.textAlign = ContentAlignment.TOP_RIGHT;

        lblMercSkillsTrader.setLocation(new Point(133, 4));
        lblMercSkillsTrader.setSize(new Size(20, 563));
        lblMercSkillsTrader.setTabIndex(146);
        lblMercSkillsTrader.textAlign = ContentAlignment.TOP_RIGHT;

        lblMercSkillsEngineer.setLocation(new Point(153, 4));
        lblMercSkillsEngineer.setSize(new Size(20, 563));
        lblMercSkillsEngineer.setTabIndex(147);
        lblMercSkillsEngineer.textAlign = ContentAlignment.TOP_RIGHT;

        lblMercSystems.setLinkArea(new LinkArea(0, 0));
        lblMercSystems.setLocation(new Point(185, 4));
        lblMercSystems.setSize(new Size(160, 387));
        lblMercSystems.setTabIndex(14);
        lblMercSystems.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                systemLinkClicked(e);
            }
        });

        lblMercIds.setLocation(new Point(0, 4));
        lblMercIds.setSize(new Size(23, 563));
        lblMercIds.setTabIndex(142);
        lblMercIds.textAlign = ContentAlignment.TOP_RIGHT;

        lblMercNames.setLocation(new Point(28, 4));
        lblMercNames.setSize(new Size(69, 563));
        lblMercNames.setTabIndex(141);

        lblMercSystems2.setLinkArea(new LinkArea(0, 0));
        lblMercSystems2.setLocation(new Point(185, 391));
        lblMercSystems2.setSize(new Size(160, 175));
        lblMercSystems2.setTabIndex(148);
        lblMercSystems2.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                systemLinkClicked(e);
            }
        });

        pnlQuests.autoScroll = true;
        pnlQuests.setBorderStyle(BorderStyle.FIXED_SINGLE);
        pnlQuests.controls.add(lblQuests);
        pnlQuests.controls.add(lblQuestSystems);
        pnlQuests.setLocation(new Point(8, 44));
        pnlQuests.setSize(new Size(222, 182));
        pnlQuests.setTabIndex(159);

        lblQuests.setLocation(new Point(76, 4));
        lblQuests.setSize(new Size(120, 350));
        lblQuests.setTabIndex(48);

        lblQuestSystems.setLinkArea(new LinkArea(0, 0));
        lblQuestSystems.setLocation(new Point(4, 4));
        lblQuestSystems.setSize(new Size(68, 350));
        lblQuestSystems.setTabIndex(12);
        lblQuestSystems.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                systemLinkClicked(e);
            }
        });

        pnlShipyards.setBorderStyle(BorderStyle.FIXED_SINGLE);
        pnlShipyards.controls.add(lblShipyards);
        pnlShipyards.controls.add(lblShipyardSystems);
        pnlShipyards.setLocation(new Point(8, 278));
        pnlShipyards.setSize(new Size(222, 73));
        pnlShipyards.setTabIndex(160);

        lblShipyards.setLocation(new Point(76, 4));
        lblShipyards.setSize(new Size(120, 63));
        lblShipyards.setTabIndex(158);

        lblShipyardSystems.setLinkArea(new LinkArea(0, 0));
        lblShipyardSystems.setLocation(new Point(4, 4));
        lblShipyardSystems.setSize(new Size(68, 63));
        lblShipyardSystems.setTabIndex(13);
        lblShipyardSystems.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                systemLinkClicked(e);
            }
        });

        picLine2.setBackground(java.awt.Color.darkGray);
        picLine2.setLocation(new Point(4, 274));
        picLine2.setSize(new Size(222, 1));
        picLine2.setTabIndex(161);
        picLine2.setTabStop(false);

        btnClose.setDialogResult(DialogResult.CANCEL);
        btnClose.setLocation(new Point(-32, -32));
        btnClose.setSize(new Size(32, 32));
        btnClose.setTabIndex(32);
        btnClose.setTabStop(false);
        btnClose.setText("X");

        controls.add(picLine2);
        controls.add(pnlShipyards);
        controls.add(pnlQuests);
        controls.add(picLine1);
        controls.add(picLine0);
        controls.add(pnlMercs);
        controls.add(lblShipyardsLabel);
        controls.add(lblShipyardsDescLabel);
        controls.add(lblShipyardsSystemLabel);
        controls.add(lblMercNameLabel);
        controls.add(lblMercIDLabel);
        controls.add(lblQuestDescLabel);
        controls.add(lblQuestSystemLabel);
        controls.add(lblMercSystemLabel);
        controls.add(lblMercSkillLabelEngineer);
        controls.add(lblMercSkillLabelTrader);
        controls.add(lblMercSkillLabelFighter);
        controls.add(lblMercSkillLabelPilot);
        controls.add(lblMercLabel);
        controls.add(lblQuestsLabel);
        controls.add(btnClose);
    }

    private int compare(int a, int b, String sortWhat, String sortBy) {
        int compareVal = 0;

        if (sortWhat.equals("M")) { // Mercenaries
            CrewMember A = game.getMercenaries()[a];
            CrewMember B = game.getMercenaries()[b];

            boolean strCompare = false;
            Object valA = null;
            Object valB = null;

            switch (SomeStringsForCheatSwitch.valueOf(sortBy)) {
                case I: // Id
                    valA = A.getId().castToInt();
                    valB = B.getId().castToInt();
                    break;
                case N: // Name
                    valA = A.getName();
                    valB = B.getName();
                    strCompare = true;
                    break;
                case P: // Pilot
                    valA = A.getPilot();
                    valB = B.getPilot();
                    break;
                case F: // Fighter
                    valA = A.getFighter();
                    valB = B.getFighter();
                    break;
                case T: // Trader
                    valA = A.getTrader();
                    valB = B.getTrader();
                    break;
                case E: // Engineer
                    valA = A.getEngineer();
                    valB = B.getEngineer();
                    break;
                case S: // System
                    valA = currentSystemDisplay(A);
                    valB = currentSystemDisplay(B);
                    strCompare = true;
                    break;
            }

            if (strCompare)
                compareVal = ((String) valA).compareTo((String) valB);
            else
                compareVal = ((Integer) valA).compareTo((Integer) valB);

            // Secondary sort by Name
            if (compareVal == 0 && !sortBy.equals("N"))
                compareVal = A.getName().compareTo(B.getName());
        } else {
            StarSystem A = game.getUniverse()[a];
            StarSystem B = game.getUniverse()[b];

            if (sortBy.equals("D")) { // Description
                String nameA = "";
                String nameB = "";

                switch (SomeStringsForCheatSwitch.valueOf(sortWhat)) {
                    case Q: // Quests
                        nameA = A.specialEvent().getTitle();
                        nameB = B.specialEvent().getTitle();
                        break;
                    case S: // Shipyards
                        nameA = A.getShipyard().getName();
                        nameB = B.getShipyard().getName();
                        break;
                }

                compareVal = nameA.compareTo(nameB);
            }

            if (compareVal == 0) { // Default sort - System Name
                compareVal = A.getName().compareTo(B.getName());
            }
        }

        return compareVal;
    }

    private String currentSystemDisplay(CrewMember merc) {
        return (merc.getCurrentSystem() == null ? Strings.Unknown
                : (game.getCommander().getShip().hasCrew(merc.getId()) ? Functions.stringVars(Strings.MercOnBoard, merc
                .getCurrentSystem().getName()) : merc.getCurrentSystem().getName()));
    }

    private void populateIdArrays() {
        // Populate the mercenary ids array.
        ArrayList ids = new ArrayList();
        for (CrewMember merc : game.getMercenaries()) {
            if (!Util.arrayContains(Consts.SpecialCrewMemberIds, merc.getId()))
                ids.add(merc.getId().castToInt());
        }
        mercIds = (Integer[]) ids.toArray(new Integer[0]);

        // Populate the quest and shipyard system ids arrays.
        ArrayList quests = new ArrayList();
        ArrayList shipyards = new ArrayList();
        for (StarSystem system : game.getUniverse()) {
            if (system.showSpecialButton())
                quests.add(system.getId().castToInt());

            if (system.getShipyardId() != ShipyardId.NA)
                shipyards.add(system.getId().castToInt());
        }
        questSystemIds = (Integer[]) quests.toArray(new Integer[0]);
        shipyardSystemIds = (Integer[]) shipyards.toArray(new Integer[0]);

        // Sort the arrays.
        sort("M", "N"); // Sort mercenaries by name.
        sort("Q", "S"); // Sort quests by system name.
        sort("S", "S"); // Sort shipyards by system name.
    }

    private void setLabelHeights() {

        int questHeight = (int) Math.ceil(questSystemIds.length * 12.5) + 1;
        lblQuests.setHeight(questHeight);
        lblQuestSystems.setHeight(questHeight);

        int shipyardHeight = (int) Math.ceil(shipyardSystemIds.length * 12.5) + 1;
        lblShipyards.setHeight(shipyardHeight);
        lblShipyardSystems.setHeight(shipyardHeight);

        int mercHeight = (int) Math.ceil(mercIds.length * 12.5) + 1;
        lblMercIds.setHeight(mercHeight);
        lblMercNames.setHeight(mercHeight);
        lblMercSkillsPilot.setHeight(mercHeight);
        lblMercSkillsFighter.setHeight(mercHeight);
        lblMercSkillsTrader.setHeight(mercHeight);
        lblMercSkillsEngineer.setHeight(mercHeight);

        // Due to a limitation of the LinkLabel control, no more than 32 links
        // can exist in the LinkLabel.
        lblMercSystems.setHeight((int) Math.ceil(Math.min(mercIds.length, SPLIT_SYSTEMS) * 12.5) + 1);
        if (mercIds.length > SPLIT_SYSTEMS) {
            lblMercSystems2.setVisible(true);
            lblMercSystems2.setHeight((int) Math.ceil((mercIds.length - SPLIT_SYSTEMS) * 12.5) + 1);
        } else {
            lblMercSystems2.setVisible(false);
            lblMercSystems2.setTop(lblMercSystems.getTop());
        }
    }

    private void sort(String sortWhat, String sortBy) {
        Integer[] array = null;
        switch (SomeStringsForCheatSwitch.valueOf(sortWhat)) {
            case M:
                array = mercIds;
                break;
            case Q:
                array = questSystemIds;
                break;
            case S:
                array = shipyardSystemIds;
                break;
        }

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (compare(array[j], array[j + 1], sortWhat, sortBy) > 0) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    private void updateAll() {
        updateMercs();
        updateQuests();
        updateShipyards();
    }

    private void updateMercs() {
        lblMercIds.setText("");
        lblMercNames.setText("");
        lblMercSkillsPilot.setText("");
        lblMercSkillsFighter.setText("");
        lblMercSkillsTrader.setText("");
        lblMercSkillsEngineer.setText("");
        lblMercSystems.setText("");
        lblMercSystems2.setText("");
        lblMercSystems.getLinks().clear();
        lblMercSystems2.getLinks().clear();

        for (int i = 0; i < mercIds.length; i++) {
            CrewMember merc = game.getMercenaries()[mercIds[i]];
            boolean link = merc.getCurrentSystem() != null && !game.getCommander().getShip().hasCrew(merc.getId());

            lblMercIds.setText(lblMercIds.getText() + ((merc.getId().castToInt()) + Strings.newline));
            lblMercNames.setText(lblMercNames.getText() + (merc.getName() + Strings.newline));
            lblMercSkillsPilot.setText(lblMercSkillsPilot.getText() + (merc.getPilot() + Strings.newline));
            lblMercSkillsFighter.setText(lblMercSkillsFighter.getText() + (merc.getFighter() + Strings.newline));
            lblMercSkillsTrader.setText(lblMercSkillsTrader.getText() + (merc.getTrader() + Strings.newline));
            lblMercSkillsEngineer.setText(lblMercSkillsEngineer.getText() + (merc.getEngineer() + Strings.newline));

            if (i < SPLIT_SYSTEMS) {
                int start = lblMercSystems.getText().length();
                lblMercSystems.setText(lblMercSystems.getText() + (currentSystemDisplay(merc) + Strings.newline));
                if (link)
                    lblMercSystems.getLinks().add(start, merc.getCurrentSystem().getName().length(), merc.getCurrentSystem().getName());
            } else {
                int start = lblMercSystems2.getText().length();
                lblMercSystems2.setText(lblMercSystems2.getText() + (currentSystemDisplay(merc) + Strings.newline));
                if (link)
                    lblMercSystems2.getLinks().add(start, merc.getCurrentSystem().getName().length(), merc.getCurrentSystem().getName());
            }
        }

        lblMercIds.setText(lblMercIds.getText().trim());
        lblMercNames.setText(lblMercNames.getText().trim());
        lblMercSkillsPilot.setText(lblMercSkillsPilot.getText().trim());
        lblMercSkillsFighter.setText(lblMercSkillsFighter.getText().trim());
        lblMercSkillsTrader.setText(lblMercSkillsTrader.getText().trim());
        lblMercSkillsEngineer.setText(lblMercSkillsEngineer.getText().trim());
        lblMercSystems.setText(lblMercSystems.getText().trim());
        lblMercSystems2.setText(lblMercSystems2.getText().trim());
    }

    private void updateQuests() {
        lblQuestSystems.setText("");
        lblQuests.setText("");
        lblQuestSystems.getLinks().clear();

        for (Integer questSystemId : questSystemIds) {
            StarSystem system = game.getUniverse()[questSystemId];
            int start = lblQuestSystems.getText().length();

            lblQuestSystems.setText(lblQuestSystems.getText() + (system.getName() + Strings.newline));
            lblQuests.setText(lblQuests.getText() + (system.specialEvent().getTitle() + Strings.newline));

            lblQuestSystems.getLinks().add(start, system.getName().length(), system.getName());
        }

        lblQuestSystems.setText(lblQuestSystems.getText().trim());
        lblQuests.setText(lblQuests.getText().trim());
    }

    private void updateShipyards() {
        lblShipyardSystems.setText("");
        lblShipyards.setText("");
        lblShipyardSystems.getLinks().clear();

        for (Integer shipyardSystemId : shipyardSystemIds) {
            StarSystem system = game.getUniverse()[shipyardSystemId];
            int start = lblShipyardSystems.getText().length();

            lblShipyardSystems.setText(lblShipyardSystems.getText() + (system.getName() + Strings.newline));
            lblShipyards.setText(lblShipyards.getText() + (system.getShipyard().getName() + Strings.newline));

            lblShipyardSystems.getLinks().add(start, system.getName().length(), system.getName());
        }

        lblShipyardSystems.setText(lblShipyardSystems.getText().trim());
        lblShipyards.setText(lblShipyards.getText().trim());
    }

    private void systemLinkClicked(LinkLabelLinkClickedEventArgs e) {
        Game.getCurrentGame().setSelectedSystemByName(e.Link.LinkData.toString());
        Game.getCurrentGame().getParentWindow().updateAll();
        close();
    }

    private void sortLinkClicked(Object sender) {
        sort(((LinkLabel) sender).getName().substring(3, 1), ((LinkLabel) sender).getText().substring(0, 1));
        updateAll();
    }
}
