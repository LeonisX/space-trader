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
import spacetrader.game.enums.ShipyardId;
import spacetrader.game.*;
import spacetrader.stub.ArrayList;
import spacetrader.util.CheatCode;
import spacetrader.util.Util;

@SuppressWarnings({"unchecked", "synthetic-access"})
@CheatCode
public class FormMonster extends SpaceTraderForm {
    // #region Constants

    private final int SplitSystems = 31;

    // #endregion

    // #region Control Declarations
    private final Game game = Game.getCurrentGame();
    private Button btnClose;
    private SimplePanel pnlMercs;
    private SimplePanel pnlQuests;
    private SimplePanel pnlShipyards;
    private PictureBox picLine1;
    private PictureBox picLine0;
    private PictureBox picLine2;
    private Label lblMercLabel;
    private Label lblQuestsLabel;
    private Label lblShipyardsLabel;
    private LinkLabel lblMercIDLabel;
    private LinkLabel lblMercNameLabel;
    private LinkLabel lblMercSkillLabelPilot;
    private LinkLabel lblMercSkillLabelFighter;
    private LinkLabel lblMercSkillLabelTrader;
    private LinkLabel lblMercSkillLabelEngineer;
    private LinkLabel lblMercSystemLabel;
    private LinkLabel lblQuestSystemLabel;
    private LinkLabel lblQuestDescLabel;
    private LinkLabel lblShipyardsSystemLabel;
    private LinkLabel lblShipyardsDescLabel;
    private Label lblMercIds;
    private Label lblMercNames;
    private Label lblMercSkillsPilot;
    private Label lblMercSkillsFighter;
    private Label lblMercSkillsTrader;
    private Label lblMercSkillsEngineer;
    private LinkLabel lblMercSystems;
    private LinkLabel lblMercSystems2;
    private LinkLabel lblQuestSystems;
    private Label lblQuests;
    private LinkLabel lblShipyardSystems;

    // #endregion

    // #region Member Declarations
    private Label lblShipyards;
    private Integer[] mercIds;
    private Integer[] questSystemIds;
    private Integer[] shipyardSystemIds;

    // #endregion

    // #region Methods

    public FormMonster() {
        initializeComponent();

        PopulateIdArrays();

        SetLabelHeights();

        UpdateAll();
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        btnClose = new Button();
        picLine1 = new PictureBox();
        picLine0 = new PictureBox();
        lblQuestsLabel = new Label();
        lblMercLabel = new Label();
        lblMercSkillLabelPilot = new LinkLabel();
        lblMercSkillLabelFighter = new LinkLabel();
        lblMercSkillLabelTrader = new LinkLabel();
        lblMercSkillLabelEngineer = new LinkLabel();
        lblMercSystemLabel = new LinkLabel();
        lblQuestSystemLabel = new LinkLabel();
        lblQuestDescLabel = new LinkLabel();
        lblMercIDLabel = new LinkLabel();
        lblMercNameLabel = new LinkLabel();
        lblShipyardsDescLabel = new LinkLabel();
        lblShipyardsSystemLabel = new LinkLabel();
        lblShipyardsLabel = new Label();
        pnlMercs = new SimplePanel();
        lblMercSkillsPilot = new Label();
        lblMercSkillsFighter = new Label();
        lblMercSkillsTrader = new Label();
        lblMercSkillsEngineer = new Label();
        lblMercSystems = new LinkLabel();
        lblMercIds = new Label();
        lblMercNames = new Label();
        lblMercSystems2 = new LinkLabel();
        pnlQuests = new SimplePanel();
        lblQuests = new Label();
        lblQuestSystems = new LinkLabel();
        pnlShipyards = new SimplePanel();
        lblShipyards = new Label();
        lblShipyardSystems = new LinkLabel();
        picLine2 = new PictureBox();
        pnlMercs.suspendLayout();
        pnlQuests.suspendLayout();
        pnlShipyards.suspendLayout();
        this.suspendLayout();
        //
        // btnClose
        //
        btnClose.setDialogResult(DialogResult.CANCEL);
        btnClose.setLocation(new java.awt.Point(-32, -32));
        btnClose.setName("btnClose");
        btnClose.setSize(new Size(32, 32));
        btnClose.setTabIndex(32);
        btnClose.setTabStop(false);
        btnClose.setText("X");
        //
        // picLine1
        //
        picLine1.setBackground(java.awt.Color.darkGray);
        picLine1.setLocation(new java.awt.Point(4, 40));
        picLine1.setName("picLine1");
        picLine1.setSize(new Size(609, 1));
        picLine1.setTabIndex(133);
        picLine1.setTabStop(false);
        //
        // picLine0
        //
        picLine0.setBackground(java.awt.Color.darkGray);
        picLine0.setLocation(new java.awt.Point(234, 8));
        picLine0.setName("picLine0");
        picLine0.setSize(new Size(1, 347));
        picLine0.setTabIndex(132);
        picLine0.setTabStop(false);
        //
        // lblQuestsLabel
        //
        lblQuestsLabel.setAutoSize(true);
        lblQuestsLabel.setFont(FontCollection.bold10);
        lblQuestsLabel.setLocation(new java.awt.Point(88, 4));
        lblQuestsLabel.setName("lblQuestsLabel");
        lblQuestsLabel.setSize(new Size(50, 19));
        lblQuestsLabel.setTabIndex(134);
        lblQuestsLabel.setText("Quests");
        //
        // lblMercLabel
        //
        lblMercLabel.setAutoSize(true);
        lblMercLabel.setFont(FontCollection.bold10);
        lblMercLabel.setLocation(new java.awt.Point(348, 4));
        lblMercLabel.setName("lblMercLabel");
        lblMercLabel.setSize(new Size(84, 19));
        lblMercLabel.setTabIndex(141);
        lblMercLabel.setText("Mercenaries");
        //
        // lblMercSkillLabelPilot
        //
        lblMercSkillLabelPilot.setAutoSize(true);
        lblMercSkillLabelPilot.setFont(FontCollection.bold825);
        lblMercSkillLabelPilot.setLocation(new java.awt.Point(341, 24));
        lblMercSkillLabelPilot.setName("lblMercSkillLabelPilot");
        lblMercSkillLabelPilot.setSize(new Size(12, 16));
        lblMercSkillLabelPilot.setTabIndex(7);
        lblMercSkillLabelPilot.setTabStop(true);
        lblMercSkillLabelPilot.setText("P");
        lblMercSkillLabelPilot.textAlign = ContentAlignment.TOP_RIGHT;
        lblMercSkillLabelPilot.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblMercSkillLabelFighter
        //
        lblMercSkillLabelFighter.setAutoSize(true);
        lblMercSkillLabelFighter.setFont(FontCollection.bold825);
        lblMercSkillLabelFighter.setLocation(new java.awt.Point(362, 24));
        lblMercSkillLabelFighter.setName("lblMercSkillLabelFighter");
        lblMercSkillLabelFighter.setSize(new Size(11, 16));
        lblMercSkillLabelFighter.setTabIndex(8);
        lblMercSkillLabelFighter.setTabStop(true);
        lblMercSkillLabelFighter.setText("F");
        lblMercSkillLabelFighter.textAlign = ContentAlignment.TOP_RIGHT;
        lblMercSkillLabelFighter.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblMercSkillLabelTrader
        //
        lblMercSkillLabelTrader.setAutoSize(true);
        lblMercSkillLabelTrader.setFont(FontCollection.bold825);
        lblMercSkillLabelTrader.setLocation(new java.awt.Point(382, 24));
        lblMercSkillLabelTrader.setName("lblMercSkillLabelTrader");
        lblMercSkillLabelTrader.setSize(new Size(11, 16));
        lblMercSkillLabelTrader.setTabIndex(9);
        lblMercSkillLabelTrader.setTabStop(true);
        lblMercSkillLabelTrader.setText("T");
        lblMercSkillLabelTrader.textAlign = ContentAlignment.TOP_RIGHT;
        lblMercSkillLabelTrader.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblMercSkillLabelEngineer
        //
        lblMercSkillLabelEngineer.setAutoSize(true);
        lblMercSkillLabelEngineer.setFont(FontCollection.bold825);
        lblMercSkillLabelEngineer.setLocation(new java.awt.Point(401, 24));
        lblMercSkillLabelEngineer.setName("lblMercSkillLabelEngineer");
        lblMercSkillLabelEngineer.setSize(new Size(12, 16));
        lblMercSkillLabelEngineer.setTabIndex(10);
        lblMercSkillLabelEngineer.setTabStop(true);
        lblMercSkillLabelEngineer.setText("E");
        lblMercSkillLabelEngineer.textAlign = ContentAlignment.TOP_RIGHT;
        lblMercSkillLabelEngineer.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblMercSystemLabel
        //
        lblMercSystemLabel.setAutoSize(true);
        lblMercSystemLabel.setFont(FontCollection.bold825);
        lblMercSystemLabel.setLocation(new java.awt.Point(425, 24));
        lblMercSystemLabel.setName("lblMercSystemLabel");
        lblMercSystemLabel.setSize(new Size(43, 16));
        lblMercSystemLabel.setTabIndex(11);
        lblMercSystemLabel.setTabStop(true);
        lblMercSystemLabel.setText("System");
        lblMercSystemLabel.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblQuestSystemLabel
        //
        lblQuestSystemLabel.setAutoSize(true);
        lblQuestSystemLabel.setFont(FontCollection.bold825);
        lblQuestSystemLabel.setLocation(new java.awt.Point(13, 24));
        lblQuestSystemLabel.setName("lblQuestSystemLabel");
        lblQuestSystemLabel.setSize(new Size(43, 16));
        lblQuestSystemLabel.setTabIndex(1);
        lblQuestSystemLabel.setTabStop(true);
        lblQuestSystemLabel.setText("System");
        lblQuestSystemLabel.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblQuestDescLabel
        //
        lblQuestDescLabel.setAutoSize(true);
        lblQuestDescLabel.setFont(FontCollection.bold825);
        lblQuestDescLabel.setLocation(new java.awt.Point(85, 24));
        lblQuestDescLabel.setName("lblQuestDescLabel");
        lblQuestDescLabel.setSize(new Size(63, 16));
        lblQuestDescLabel.setTabIndex(2);
        lblQuestDescLabel.setTabStop(true);
        lblQuestDescLabel.setText("Description");
        lblQuestDescLabel.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblMercIDLabel
        //
        lblMercIDLabel.setAutoSize(true);
        lblMercIDLabel.setFont(FontCollection.bold825);
        lblMercIDLabel.setLocation(new java.awt.Point(247, 24));
        lblMercIDLabel.setName("lblMercIDLabel");
        lblMercIDLabel.setSize(new Size(16, 16));
        lblMercIDLabel.setTabIndex(5);
        lblMercIDLabel.setTabStop(true);
        lblMercIDLabel.setText("ID");
        lblMercIDLabel.textAlign = ContentAlignment.TOP_RIGHT;
        lblMercIDLabel.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblMercNameLabel
        //
        lblMercNameLabel.setAutoSize(true);
        lblMercNameLabel.setFont(FontCollection.bold825);
        lblMercNameLabel.setLocation(new java.awt.Point(268, 24));
        lblMercNameLabel.setName("lblMercNameLabel");
        lblMercNameLabel.setSize(new Size(35, 16));
        lblMercNameLabel.setTabIndex(6);
        lblMercNameLabel.setTabStop(true);
        lblMercNameLabel.setText("Name");
        lblMercNameLabel.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblShipyardsDescLabel
        //
        lblShipyardsDescLabel.setAutoSize(true);
        lblShipyardsDescLabel.setFont(FontCollection.bold825);
        lblShipyardsDescLabel.setLocation(new java.awt.Point(85, 258));
        lblShipyardsDescLabel.setName("lblShipyardsDescLabel");
        lblShipyardsDescLabel.setSize(new Size(63, 16));
        lblShipyardsDescLabel.setTabIndex(4);
        lblShipyardsDescLabel.setTabStop(true);
        lblShipyardsDescLabel.setText("Description");
        lblShipyardsDescLabel.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblShipyardsSystemLabel
        //
        lblShipyardsSystemLabel.setAutoSize(true);
        lblShipyardsSystemLabel.setFont(FontCollection.bold825);
        lblShipyardsSystemLabel.setLocation(new java.awt.Point(13, 258));
        lblShipyardsSystemLabel.setName("lblShipyardsSystemLabel");
        lblShipyardsSystemLabel.setSize(new Size(43, 16));
        lblShipyardsSystemLabel.setTabIndex(3);
        lblShipyardsSystemLabel.setTabStop(true);
        lblShipyardsSystemLabel.setText("System");
        lblShipyardsSystemLabel.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SortLinkClicked(sender, e);
            }
        };
        //
        // lblShipyardsLabel
        //
        lblShipyardsLabel.setAutoSize(true);
        lblShipyardsLabel.setFont(FontCollection.bold10);
        lblShipyardsLabel.setLocation(new java.awt.Point(79, 238));
        lblShipyardsLabel.setName("lblShipyardsLabel");
        lblShipyardsLabel.setSize(new Size(68, 19));
        lblShipyardsLabel.setTabIndex(155);
        lblShipyardsLabel.setText("Shipyards");
        //
        // pnlMercs
        //
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
        pnlMercs.setLocation(new java.awt.Point(239, 44));
        pnlMercs.setName("pnlMercs");
        pnlMercs.setSize(new Size(371, 307));
        pnlMercs.setTabIndex(158);
        //
        // lblMercSkillsPilot
        //
        lblMercSkillsPilot.setLocation(new java.awt.Point(93, 4));
        lblMercSkillsPilot.setName("lblMercSkillsPilot");
        lblMercSkillsPilot.setSize(new Size(20, 563));
        lblMercSkillsPilot.setTabIndex(144);
        lblMercSkillsPilot.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblMercSkillsFighter
        //
        lblMercSkillsFighter.setLocation(new java.awt.Point(113, 4));
        lblMercSkillsFighter.setName("lblMercSkillsFighter");
        lblMercSkillsFighter.setSize(new Size(20, 563));
        lblMercSkillsFighter.setTabIndex(145);
        lblMercSkillsFighter.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblMercSkillsTrader
        //
        lblMercSkillsTrader.setLocation(new java.awt.Point(133, 4));
        lblMercSkillsTrader.setName("lblMercSkillsTrader");
        lblMercSkillsTrader.setSize(new Size(20, 563));
        lblMercSkillsTrader.setTabIndex(146);
        lblMercSkillsTrader.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblMercSkillsEngineer
        //
        lblMercSkillsEngineer.setLocation(new java.awt.Point(153, 4));
        lblMercSkillsEngineer.setName("lblMercSkillsEngineer");
        lblMercSkillsEngineer.setSize(new Size(20, 563));
        lblMercSkillsEngineer.setTabIndex(147);
        lblMercSkillsEngineer.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblMercSystems
        //
        lblMercSystems.LinkArea = new LinkArea(0, 0);
        lblMercSystems.setLocation(new java.awt.Point(185, 4));
        lblMercSystems.setName("lblMercSystems");
        lblMercSystems.setSize(new Size(160, 387));
        lblMercSystems.setTabIndex(14);
        lblMercSystems.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SystemLinkClicked(sender, e);
            }
        };
        //
        // lblMercIds
        //
        lblMercIds.setLocation(new java.awt.Point(0, 4));
        lblMercIds.setName("lblMercIds");
        lblMercIds.setSize(new Size(23, 563));
        lblMercIds.setTabIndex(142);
        lblMercIds.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblMercNames
        //
        lblMercNames.setLocation(new java.awt.Point(28, 4));
        lblMercNames.setName("lblMercNames");
        lblMercNames.setSize(new Size(69, 563));
        lblMercNames.setTabIndex(141);
        //
        // lblMercSystems2
        //
        lblMercSystems2.LinkArea = new LinkArea(0, 0);
        lblMercSystems2.setLocation(new java.awt.Point(185, 391));
        lblMercSystems2.setName("lblMercSystems2");
        lblMercSystems2.setSize(new Size(160, 175));
        lblMercSystems2.setTabIndex(148);
        lblMercSystems2.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SystemLinkClicked(sender, e);
            }
        };
        //
        // pnlQuests
        //
        pnlQuests.autoScroll = true;
        pnlQuests.setBorderStyle(BorderStyle.FIXED_SINGLE);
        pnlQuests.controls.add(lblQuests);
        pnlQuests.controls.add(lblQuestSystems);
        pnlQuests.setLocation(new java.awt.Point(8, 44));
        pnlQuests.setName("pnlQuests");
        pnlQuests.setSize(new Size(222, 182));
        pnlQuests.setTabIndex(159);
        //
        // lblQuests
        //
        lblQuests.setLocation(new java.awt.Point(76, 4));
        lblQuests.setName("lblQuests");
        lblQuests.setSize(new Size(120, 350));
        lblQuests.setTabIndex(48);
        //
        // lblQuestSystems
        //
        lblQuestSystems.LinkArea = new LinkArea(0, 0);
        lblQuestSystems.setLocation(new java.awt.Point(4, 4));
        lblQuestSystems.setName("lblQuestSystems");
        lblQuestSystems.setSize(new Size(68, 350));
        lblQuestSystems.setTabIndex(12);
        lblQuestSystems.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SystemLinkClicked(sender, e);
            }
        };
        //
        // pnlShipyards
        //
        pnlShipyards.setBorderStyle(BorderStyle.FIXED_SINGLE);
        pnlShipyards.controls.add(lblShipyards);
        pnlShipyards.controls.add(lblShipyardSystems);
        pnlShipyards.setLocation(new java.awt.Point(8, 278));
        pnlShipyards.setName("pnlShipyards");
        pnlShipyards.setSize(new Size(222, 73));
        pnlShipyards.setTabIndex(160);
        //
        // lblShipyards
        //
        lblShipyards.setLocation(new java.awt.Point(76, 4));
        lblShipyards.setName("lblShipyards");
        lblShipyards.setSize(new Size(120, 63));
        lblShipyards.setTabIndex(158);
        //
        // lblShipyardSystems
        //
        lblShipyardSystems.LinkArea = new LinkArea(0, 0);
        lblShipyardSystems.setLocation(new java.awt.Point(4, 4));
        lblShipyardSystems.setName("lblShipyardSystems");
        lblShipyardSystems.setSize(new Size(68, 63));
        lblShipyardSystems.setTabIndex(13);
        lblShipyardSystems.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                SystemLinkClicked(sender, e);
            }
        };
        //
        // picLine2
        //
        picLine2.setBackground(java.awt.Color.darkGray);
        picLine2.setLocation(new java.awt.Point(4, 274));
        picLine2.setName("picLine2");
        picLine2.setSize(new Size(222, 1));
        picLine2.setTabIndex(161);
        picLine2.setTabStop(false);
        //
        // FormMonster
        //
        this.setAutoScaleBaseSize(new Size(5, 13));
        this.setCancelButton(btnClose);
        this.setClientSize(new Size(617, 358));
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
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormMonster");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setText("Monster.com Job Listing");
    }

    // #endregion

    private int Compare(int a, int b, String sortWhat, String sortBy) {
        int compareVal = 0;

        if (sortWhat == "M") // Mercenaries
        {
            CrewMember A = game.Mercenaries()[a];
            CrewMember B = game.Mercenaries()[b];

            boolean strCompare = false;
            Object valA = null;
            Object valB = null;

            switch (SomeStringsForCheatSwitch.valueOf(sortBy)) {
                case I: // Id
                    valA = (int) A.Id().castToInt();
                    valB = (int) B.Id().castToInt();
                    break;
                case N: // Name
                    valA = A.Name();
                    valB = B.Name();
                    strCompare = true;
                    break;
                case P: // Pilot
                    valA = A.Pilot();
                    valB = B.Pilot();
                    break;
                case F: // Fighter
                    valA = A.Fighter();
                    valB = B.Fighter();
                    break;
                case T: // Trader
                    valA = A.Trader();
                    valB = B.Trader();
                    break;
                case E: // Engineer
                    valA = A.Engineer();
                    valB = B.Engineer();
                    break;
                case S: // System
                    valA = CurrentSystemDisplay(A);
                    valB = CurrentSystemDisplay(B);
                    strCompare = true;
                    break;
            }

            if (strCompare)
                compareVal = ((String) valA).compareTo((String) valB);
            else
                compareVal = ((Integer) valA).compareTo((Integer) valB);

            // Secondary sort by Name
            if (compareVal == 0 && sortBy != "N")
                compareVal = A.Name().compareTo(B.Name());
        } else {
            StarSystem A = game.getUniverse()[a];
            StarSystem B = game.getUniverse()[b];

            if (sortBy == "D") // Description
            {
                String nameA = "";
                String nameB = "";

                switch (SomeStringsForCheatSwitch.valueOf(sortWhat)) {
                    case Q: // Quests
                        nameA = A.specialEvent().getTitle();
                        nameB = B.specialEvent().getTitle();
                        break;
                    case S: // Shipyards
                        nameA = A.getShipyard().Name();
                        nameB = B.getShipyard().Name();
                        break;
                }

                compareVal = nameA.compareTo(nameB);
            }

            if (compareVal == 0) // Default sort - System Name
            {
                compareVal = A.getName().compareTo(B.getName());
            }
        }

        return compareVal;
    }

    private String CurrentSystemDisplay(CrewMember merc) {
        return (merc.getCurrentSystem() == null ? Strings.Unknown
                : (game.getCommander().getShip().HasCrew(merc.Id()) ? Functions.stringVars(Strings.MercOnBoard, merc
                .getCurrentSystem().getName()) : merc.getCurrentSystem().getName()));
    }

    private void PopulateIdArrays() {
        // Populate the mercenary ids array.
        ArrayList ids = new ArrayList();
        for (CrewMember merc : game.Mercenaries()) {
            if (!Util.ArrayContains(Consts.SpecialCrewMemberIds, merc.Id()))
                ids.add(merc.Id().castToInt());
        }
        mercIds = (Integer[]) ids.toArray(new Integer[0]);

        // Populate the quest and shipyard system ids arrays.
        ArrayList quests = new ArrayList();
        ArrayList shipyards = new ArrayList();
        for (StarSystem system : game.getUniverse()) {
            if (system.showSpecialButton())
                quests.add(system.Id().castToInt());

            if (system.getShipyardId() != ShipyardId.NA)
                shipyards.add(system.Id().castToInt());
        }
        questSystemIds = (Integer[]) quests.toArray(new Integer[0]);
        shipyardSystemIds = (Integer[]) shipyards.toArray(new Integer[0]);

        // Sort the arrays.
        Sort("M", "N"); // Sort mercenaries by name.
        Sort("Q", "S"); // Sort quests by system name.
        Sort("S", "S"); // Sort shipyards by system name.
    }

    private void SetLabelHeights() {

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
        lblMercSystems.setHeight((int) Math.ceil(Math.min(mercIds.length, SplitSystems) * 12.5) + 1);
        if (mercIds.length > SplitSystems) {
            lblMercSystems2.setVisible(true);
            lblMercSystems2.setHeight((int) Math.ceil((mercIds.length - SplitSystems) * 12.5) + 1);
        } else {
            lblMercSystems2.setVisible(false);
            lblMercSystems2.setTop(lblMercSystems.getTop());
        }
    }

    private void Sort(String sortWhat, String sortBy) {
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
                if (Compare(array[j], array[j + 1], sortWhat, sortBy) > 0) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    private void UpdateAll() {
        UpdateMercs();
        UpdateQuests();
        UpdateShipyards();
    }

    private void UpdateMercs() {
        lblMercIds.setText("");
        lblMercNames.setText("");
        lblMercSkillsPilot.setText("");
        lblMercSkillsFighter.setText("");
        lblMercSkillsTrader.setText("");
        lblMercSkillsEngineer.setText("");
        lblMercSystems.setText("");
        lblMercSystems2.setText("");
        lblMercSystems.Links.clear();
        lblMercSystems2.Links.clear();

        for (int i = 0; i < mercIds.length; i++) {
            CrewMember merc = game.Mercenaries()[mercIds[i]];
            boolean link = merc.getCurrentSystem() != null && !game.getCommander().getShip().HasCrew(merc.Id());

            lblMercIds.setText(lblMercIds.getText() + ((merc.Id().castToInt()) + Strings.newline));
            lblMercNames.setText(lblMercNames.getText() + (merc.Name() + Strings.newline));
            lblMercSkillsPilot.setText(lblMercSkillsPilot.getText() + (merc.Pilot() + Strings.newline));
            lblMercSkillsFighter.setText(lblMercSkillsFighter.getText() + (merc.Fighter() + Strings.newline));
            lblMercSkillsTrader.setText(lblMercSkillsTrader.getText() + (merc.Trader() + Strings.newline));
            lblMercSkillsEngineer.setText(lblMercSkillsEngineer.getText() + (merc.Engineer() + Strings.newline));

            if (i < SplitSystems) {
                int start = lblMercSystems.getText().length();
                lblMercSystems.setText(lblMercSystems.getText() + (CurrentSystemDisplay(merc) + Strings.newline));
                if (link)
                    lblMercSystems.Links.add(start, merc.getCurrentSystem().getName().length(), merc.getCurrentSystem().getName());
            } else {
                int start = lblMercSystems2.getText().length();
                lblMercSystems2.setText(lblMercSystems2.getText() + (CurrentSystemDisplay(merc) + Strings.newline));
                if (link)
                    lblMercSystems2.Links.add(start, merc.getCurrentSystem().getName().length(), merc.getCurrentSystem().getName());
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

    private void UpdateQuests() {
        lblQuestSystems.setText("");
        lblQuests.setText("");
        lblQuestSystems.Links.clear();

        for (int i = 0; i < questSystemIds.length; i++) {
            StarSystem system = game.getUniverse()[questSystemIds[i]];
            int start = lblQuestSystems.getText().length();

            lblQuestSystems.setText(lblQuestSystems.getText() + (system.getName() + Strings.newline));
            lblQuests.setText(lblQuests.getText() + (system.specialEvent().getTitle() + Strings.newline));

            lblQuestSystems.Links.add(start, system.getName().length(), system.getName());
        }

        lblQuestSystems.setText(lblQuestSystems.getText().trim());
        lblQuests.setText(lblQuests.getText().trim());
    }

    private void UpdateShipyards() {
        lblShipyardSystems.setText("");
        lblShipyards.setText("");
        lblShipyardSystems.Links.clear();

        for (int i = 0; i < shipyardSystemIds.length; i++) {
            StarSystem system = game.getUniverse()[shipyardSystemIds[i]];
            int start = lblShipyardSystems.getText().length();

            lblShipyardSystems.setText(lblShipyardSystems.getText() + (system.getName() + Strings.newline));
            lblShipyards.setText(lblShipyards.getText() + (system.getShipyard().Name() + Strings.newline));

            lblShipyardSystems.Links.add(start, system.getName().length(), system.getName());
        }

        lblShipyardSystems.setText(lblShipyardSystems.getText().trim());
        lblShipyards.setText(lblShipyards.getText().trim());
    }

    // #endregion

    // #region Event Handlers

    private void SystemLinkClicked(Object sender, LinkLabelLinkClickedEventArgs e) {
        Game.getCurrentGame().setSelectedSystemByName(e.Link.LinkData.toString());
        Game.getCurrentGame().getParentWindow().updateAll();
        close();
    }

    private void SortLinkClicked(Object sender, LinkLabelLinkClickedEventArgs e) {
        Sort(((LinkLabel) sender).getName().substring(3, 1), ((LinkLabel) sender).getText().substring(0, 1));
        UpdateAll();
    }

    // #endregion
}
