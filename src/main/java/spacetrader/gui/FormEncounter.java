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

import spacetrader.controls.Button;
import spacetrader.controls.Container;
import spacetrader.controls.*;
import spacetrader.game.Consts;
import spacetrader.game.Functions;
import spacetrader.game.Game;
import spacetrader.game.Ship;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.EncounterResult;
import spacetrader.guifacade.Facaded;
import spacetrader.guifacade.GuiFacade;

import java.awt.*;

@Facaded
public class FormEncounter extends SpaceTraderForm {
    // #region Control Declarations

    private final Button[] buttons;
    private final int ATTACK = 0;
    private final int BOARD = 1;
    private final int BRIBE = 2;
    private final int DRINK = 3;
    private final int FLEE = 4;
    private final int IGNORE = 5;
    private final int INT = 6;
    private final int MEET = 7;
    private final int PLUNDER = 8;
    private final int SUBMIT = 9;
    private final int SURRENDER = 10;
    private final int TRADE = 11;
    private final int YIELD = 12;
    private final Game game = Game.getCurrentGame();
    private final Ship cmdrship = Game.getCurrentGame().getCommander().getShip();
    private final Ship opponent = Game.getCurrentGame().getOpponent();
    private spacetrader.controls.Label lblEncounter;
    private spacetrader.controls.PictureBox picShipYou;
    private spacetrader.controls.PictureBox picShipOpponent;
    private spacetrader.controls.Label lblOpponentLabel;
    private spacetrader.controls.Label lblYouLabel;
    private spacetrader.controls.Label lblOpponentShip;
    private spacetrader.controls.Label lblYouShip;
    private spacetrader.controls.Label lblYouHull;
    private spacetrader.controls.Label lblYouShields;
    private spacetrader.controls.Label lblOpponentShields;
    private spacetrader.controls.Label lblOpponentHull;
    private spacetrader.controls.Label lblAction;
    private spacetrader.controls.Button btnAttack;
    private spacetrader.controls.Button btnFlee;
    private spacetrader.controls.Button btnSubmit;
    private spacetrader.controls.Button btnBribe;
    private spacetrader.controls.Button btnSurrender;
    private spacetrader.controls.Button btnIgnore;
    private spacetrader.controls.Button btnTrade;
    private spacetrader.controls.Button btnPlunder;
    private spacetrader.controls.Button btnBoard;
    private spacetrader.controls.Button btnMeet;
    private spacetrader.controls.Button btnDrink;
    private spacetrader.controls.Button btnInt;
    private spacetrader.controls.Button btnYield;
    private spacetrader.controls.PictureBox picContinuous;
    private spacetrader.controls.ImageList ilContinuous;
    private spacetrader.controls.PictureBox picEncounterType;
    private spacetrader.controls.ImageList ilEncounterType;
    private spacetrader.controls.ImageList ilTribbles;
    private spacetrader.controls.PictureBox picTrib00;
    private spacetrader.controls.PictureBox picTrib50;
    private spacetrader.controls.PictureBox picTrib10;
    private spacetrader.controls.PictureBox picTrib40;
    private spacetrader.controls.PictureBox picTrib20;
    private spacetrader.controls.PictureBox picTrib30;
    private spacetrader.controls.PictureBox picTrib04;
    private spacetrader.controls.PictureBox picTrib03;
    private spacetrader.controls.PictureBox picTrib02;
    private spacetrader.controls.PictureBox picTrib01;
    private spacetrader.controls.PictureBox picTrib05;
    private spacetrader.controls.PictureBox picTrib11;
    private spacetrader.controls.PictureBox picTrib12;
    private spacetrader.controls.PictureBox picTrib13;
    private spacetrader.controls.PictureBox picTrib14;
    private spacetrader.controls.PictureBox picTrib15;
    private spacetrader.controls.PictureBox picTrib21;
    private spacetrader.controls.PictureBox picTrib22;
    private spacetrader.controls.PictureBox picTrib23;
    private spacetrader.controls.PictureBox picTrib24;
    private spacetrader.controls.PictureBox picTrib25;
    private spacetrader.controls.PictureBox picTrib31;

    // #endregion

    // #region Constants
    private spacetrader.controls.PictureBox picTrib32;
    private spacetrader.controls.PictureBox picTrib33;
    private spacetrader.controls.PictureBox picTrib34;
    private spacetrader.controls.PictureBox picTrib35;
    private spacetrader.controls.PictureBox picTrib41;
    private spacetrader.controls.PictureBox picTrib51;
    private spacetrader.controls.PictureBox picTrib42;
    private spacetrader.controls.PictureBox picTrib52;
    private spacetrader.controls.PictureBox picTrib43;
    private spacetrader.controls.PictureBox picTrib53;
    private spacetrader.controls.PictureBox picTrib44;
    private spacetrader.controls.PictureBox picTrib45;
    private spacetrader.controls.PictureBox picTrib54;

    // #endregion

    // #region Member Declarations
    private spacetrader.controls.PictureBox picTrib55;
    private spacetrader.controls.Timer tmrTick;
    private IContainer components;
    private int contImg = 1;

    private EncounterResult _result = EncounterResult.Continue;

    // #endregion

    // #region Methods

    public FormEncounter() {
        initializeComponent();

        // Set up the Game encounter variables.
        game.EncounterBegin();

        // Enable the control box (the X button) if cheats are enabled.
        if (game.getEasyEncounters())
            setControlBox(true);

        buttons = new Button[]{btnAttack, btnBoard, btnBribe, btnDrink,
                btnFlee, btnIgnore, btnInt, btnMeet, btnPlunder, btnSubmit,
                btnSurrender, btnTrade, btnYield};

        UpdateShipInfo();
        UpdateTribbles();
        UpdateButtons();

        if (game.EncounterImageIndex() >= 0)
            picEncounterType.setImage(ilEncounterType.getImages()[game.EncounterImageIndex()]);
        else
            picEncounterType.setVisible(false);

        lblEncounter.setText(game.EncounterTextInitial());
        lblAction.setText(game.EncounterActionInitial());
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        components = new Container();
        ResourceManager resources = new ResourceManager(FormEncounter.class);
        lblEncounter = new spacetrader.controls.Label();
        picShipYou = new spacetrader.controls.PictureBox();
        picShipOpponent = new spacetrader.controls.PictureBox();
        lblAction = new spacetrader.controls.Label();
        lblOpponentLabel = new spacetrader.controls.Label();
        lblYouLabel = new spacetrader.controls.Label();
        lblOpponentShip = new spacetrader.controls.Label();
        lblYouShip = new spacetrader.controls.Label();
        lblYouHull = new spacetrader.controls.Label();
        lblYouShields = new spacetrader.controls.Label();
        lblOpponentShields = new spacetrader.controls.Label();
        lblOpponentHull = new spacetrader.controls.Label();
        btnAttack = new spacetrader.controls.Button();
        btnFlee = new spacetrader.controls.Button();
        btnSubmit = new spacetrader.controls.Button();
        btnBribe = new spacetrader.controls.Button();
        btnSurrender = new spacetrader.controls.Button();
        btnIgnore = new spacetrader.controls.Button();
        btnTrade = new spacetrader.controls.Button();
        btnPlunder = new spacetrader.controls.Button();
        btnBoard = new spacetrader.controls.Button();
        btnMeet = new spacetrader.controls.Button();
        btnDrink = new spacetrader.controls.Button();
        btnInt = new spacetrader.controls.Button();
        btnYield = new spacetrader.controls.Button();
        picContinuous = new spacetrader.controls.PictureBox();
        ilContinuous = new spacetrader.controls.ImageList(components);
        picEncounterType = new spacetrader.controls.PictureBox();
        ilEncounterType = new spacetrader.controls.ImageList(components);
        picTrib00 = new spacetrader.controls.PictureBox();
        ilTribbles = new spacetrader.controls.ImageList(components);
        picTrib50 = new spacetrader.controls.PictureBox();
        picTrib10 = new spacetrader.controls.PictureBox();
        picTrib40 = new spacetrader.controls.PictureBox();
        picTrib20 = new spacetrader.controls.PictureBox();
        picTrib30 = new spacetrader.controls.PictureBox();
        picTrib04 = new spacetrader.controls.PictureBox();
        picTrib03 = new spacetrader.controls.PictureBox();
        picTrib02 = new spacetrader.controls.PictureBox();
        picTrib01 = new spacetrader.controls.PictureBox();
        picTrib05 = new spacetrader.controls.PictureBox();
        picTrib11 = new spacetrader.controls.PictureBox();
        picTrib12 = new spacetrader.controls.PictureBox();
        picTrib13 = new spacetrader.controls.PictureBox();
        picTrib14 = new spacetrader.controls.PictureBox();
        picTrib15 = new spacetrader.controls.PictureBox();
        picTrib21 = new spacetrader.controls.PictureBox();
        picTrib22 = new spacetrader.controls.PictureBox();
        picTrib23 = new spacetrader.controls.PictureBox();
        picTrib24 = new spacetrader.controls.PictureBox();
        picTrib25 = new spacetrader.controls.PictureBox();
        picTrib31 = new spacetrader.controls.PictureBox();
        picTrib32 = new spacetrader.controls.PictureBox();
        picTrib33 = new spacetrader.controls.PictureBox();
        picTrib34 = new spacetrader.controls.PictureBox();
        picTrib35 = new spacetrader.controls.PictureBox();
        picTrib41 = new spacetrader.controls.PictureBox();
        picTrib51 = new spacetrader.controls.PictureBox();
        picTrib42 = new spacetrader.controls.PictureBox();
        picTrib52 = new spacetrader.controls.PictureBox();
        picTrib43 = new spacetrader.controls.PictureBox();
        picTrib53 = new spacetrader.controls.PictureBox();
        picTrib44 = new spacetrader.controls.PictureBox();
        picTrib45 = new spacetrader.controls.PictureBox();
        picTrib54 = new spacetrader.controls.PictureBox();
        picTrib55 = new spacetrader.controls.PictureBox();
        tmrTick = new spacetrader.controls.Timer(components);
        this.suspendLayout();
        //
        // lblEncounter
        //
        lblEncounter.setLocation(new java.awt.Point(8, 152));
        lblEncounter.setName("lblEncounter");
        lblEncounter.setSize(new spacetrader.controls.Size(232, 26));
        lblEncounter.setTabIndex(0);
        lblEncounter.setText("At 20 clicks from Tarchannen, you encounter the famous Captain Ahab.");
        //
        // picShipYou
        //
        picShipYou.setBackground(java.awt.Color.white);
        picShipYou.setBorderStyle(spacetrader.controls.BorderStyle.FixedSingle);
        picShipYou.setLocation(new java.awt.Point(26, 24));
        picShipYou.setName("picShipYou");
        picShipYou.setSize(new spacetrader.controls.Size(70, 58));
        picShipYou.setTabIndex(13);
        picShipYou.setTabStop(false);
        picShipYou.setPaint(new EventHandler<Object, PaintEventArgs>() {
            @Override
            public void handle(Object sender, PaintEventArgs e) {
                picShipYou_Paint(sender, e);
            }
        });
        //
        // picShipOpponent
        //
        picShipOpponent.setBackground(java.awt.Color.white);
        picShipOpponent.setBorderStyle(spacetrader.controls.BorderStyle.FixedSingle);
        picShipOpponent.setLocation(new java.awt.Point(138, 24));
        picShipOpponent.setName("picShipOpponent");
        picShipOpponent.setSize(new spacetrader.controls.Size(70, 58));
        picShipOpponent.setTabIndex(14);
        picShipOpponent.setTabStop(false);
        picShipOpponent.setPaint(new EventHandler<Object, PaintEventArgs>() {
            @Override
            public void handle(Object sender, PaintEventArgs e) {
                picShipOpponent_Paint(sender, e);
            }
        });
        //
        // lblAction
        //
        lblAction.setLocation(new java.awt.Point(8, 192));
        lblAction.setName("lblAction");
        lblAction.setSize(new spacetrader.controls.Size(232, 39));
        lblAction.setTabIndex(15);
        lblAction.setText("\"We know you removed illegal goods from the Marie Celeste. You must give them up "
                + "at once!\"");
        //
        // lblOpponentLabel
        //
        lblOpponentLabel.setAutoSize(true);
        lblOpponentLabel.setFont(FontCollection.bold825);
        lblOpponentLabel.setLocation(new java.awt.Point(141, 8));
        lblOpponentLabel.setName("lblOpponentLabel");
        lblOpponentLabel.setSize(new spacetrader.controls.Size(59, 16));
        lblOpponentLabel.setTabIndex(16);
        lblOpponentLabel.setText("Opponent:");
        //
        // lblYouLabel
        //
        lblYouLabel.setAutoSize(true);
        lblYouLabel.setFont(FontCollection.bold825);
        lblYouLabel.setLocation(new java.awt.Point(45, 8));
        lblYouLabel.setName("lblYouLabel");
        lblYouLabel.setSize(new spacetrader.controls.Size(28, 16));
        lblYouLabel.setTabIndex(17);
        lblYouLabel.setText("You:");
        //
        // lblOpponentShip
        //
        lblOpponentShip.setLocation(new java.awt.Point(138, 88));
        lblOpponentShip.setName("lblOpponentShip");
        lblOpponentShip.setSize(new spacetrader.controls.Size(80, 13));
        lblOpponentShip.setTabIndex(18);
        lblOpponentShip.setText("Space Monster");
        //
        // lblYouShip
        //
        lblYouShip.setLocation(new java.awt.Point(26, 88));
        lblYouShip.setName("lblYouShip");
        lblYouShip.setSize(new spacetrader.controls.Size(100, 13));
        lblYouShip.setTabIndex(19);
        lblYouShip.setText("Grasshopper");
        //
        // lblYouHull
        //
        lblYouHull.setLocation(new java.awt.Point(26, 104));
        lblYouHull.setName("lblYouHull");
        lblYouHull.setSize(new spacetrader.controls.Size(68, 13));
        lblYouHull.setTabIndex(20);
        lblYouHull.setText("Hull at 100%");
        //
        // lblYouShields
        //
        lblYouShields.setLocation(new java.awt.Point(26, 120));
        lblYouShields.setName("lblYouShields");
        lblYouShields.setSize(new spacetrader.controls.Size(86, 13));
        lblYouShields.setTabIndex(21);
        lblYouShields.setText("Shields at 100%");
        //
        // lblOpponentShields
        //
        lblOpponentShields.setLocation(new java.awt.Point(138, 120));
        lblOpponentShields.setName("lblOpponentShields");
        lblOpponentShields.setSize(new spacetrader.controls.Size(86, 13));
        lblOpponentShields.setTabIndex(23);
        lblOpponentShields.setText("Shields at 100%");
        //
        // lblOpponentHull
        //
        lblOpponentHull.setLocation(new java.awt.Point(138, 104));
        lblOpponentHull.setName("lblOpponentHull");
        lblOpponentHull.setSize(new spacetrader.controls.Size(68, 13));
        lblOpponentHull.setTabIndex(22);
        lblOpponentHull.setText("Hull at 100%");
        //
        // btnAttack
        //
        btnAttack.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnAttack.setLocation(new java.awt.Point(8, 240));
        btnAttack.setName("btnAttack");
        btnAttack.setSize(new spacetrader.controls.Size(46, 22));
        btnAttack.setTabIndex(24);
        btnAttack.setText("Attack");
        btnAttack.setVisible(false);
        btnAttack.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnAttack_Click(sender, e);
            }
        });
        //
        // btnFlee
        //
        btnFlee.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnFlee.setLocation(new java.awt.Point(62, 240));
        btnFlee.setName("btnFlee");
        btnFlee.setSize(new spacetrader.controls.Size(36, 22));
        btnFlee.setTabIndex(25);
        btnFlee.setText("Flee");
        btnFlee.setVisible(false);
        btnFlee.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnFlee_Click(sender, e);
            }
        });
        //
        // btnSubmit
        //
        btnSubmit.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnSubmit.setLocation(new java.awt.Point(106, 240));
        btnSubmit.setName("btnSubmit");
        btnSubmit.setSize(new spacetrader.controls.Size(49, 22));
        btnSubmit.setTabIndex(26);
        btnSubmit.setText("Submit");
        btnSubmit.setVisible(false);
        btnSubmit.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnSubmit_Click(sender, e);
            }
        });
        //
        // btnBribe
        //
        btnBribe.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnBribe.setLocation(new java.awt.Point(163, 240));
        btnBribe.setName("btnBribe");
        btnBribe.setSize(new spacetrader.controls.Size(41, 22));
        btnBribe.setTabIndex(27);
        btnBribe.setText("Bribe");
        btnBribe.setVisible(false);
        btnBribe.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnBribe_Click(sender, e);
            }
        });
        //
        // btnSurrender
        //
        btnSurrender.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnSurrender.setLocation(new java.awt.Point(106, 240));
        btnSurrender.setName("btnSurrender");
        btnSurrender.setSize(new spacetrader.controls.Size(65, 22));
        btnSurrender.setTabIndex(28);
        btnSurrender.setText("Surrender");
        btnSurrender.setVisible(false);
        btnSurrender.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnSurrender_Click(sender, e);
            }
        });
        //
        // btnIgnore
        //
        btnIgnore.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnIgnore.setLocation(new java.awt.Point(62, 240));
        btnIgnore.setName("btnIgnore");
        btnIgnore.setSize(new spacetrader.controls.Size(46, 22));
        btnIgnore.setTabIndex(29);
        btnIgnore.setText("Ignore");
        btnIgnore.setVisible(false);
        btnIgnore.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnIgnore_Click(sender, e);
            }
        });
        //
        // btnTrade
        //
        btnTrade.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnTrade.setLocation(new java.awt.Point(116, 240));
        btnTrade.setName("btnTrade");
        btnTrade.setSize(new spacetrader.controls.Size(44, 22));
        btnTrade.setTabIndex(30);
        btnTrade.setText("Trade");
        btnTrade.setVisible(false);
        btnTrade.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnTrade_Click(sender, e);
            }
        });
        //
        // btnPlunder
        //
        btnPlunder.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnPlunder.setLocation(new java.awt.Point(62, 240));
        btnPlunder.setName("btnPlunder");
        btnPlunder.setSize(new spacetrader.controls.Size(53, 22));
        btnPlunder.setTabIndex(31);
        btnPlunder.setText("Plunder");
        btnPlunder.setVisible(false);
        btnPlunder.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnBoard
        //
        btnBoard.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnBoard.setLocation(new java.awt.Point(8, 240));
        btnBoard.setName("btnBoard");
        btnBoard.setSize(new spacetrader.controls.Size(44, 22));
        btnBoard.setTabIndex(32);
        btnBoard.setText("Board");
        btnBoard.setVisible(false);
        btnBoard.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnBoard_Click(sender, e);
            }
        });
        //
        // btnMeet
        //
        btnMeet.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnMeet.setLocation(new java.awt.Point(116, 240));
        btnMeet.setName("btnMeet");
        btnMeet.setSize(new spacetrader.controls.Size(39, 22));
        btnMeet.setTabIndex(34);
        btnMeet.setText("Meet");
        btnMeet.setVisible(false);
        btnMeet.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnMeet_Click(sender, e);
            }
        });
        //
        // btnDrink
        //
        btnDrink.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnDrink.setLocation(new java.awt.Point(8, 240));
        btnDrink.setName("btnDrink");
        btnDrink.setSize(new spacetrader.controls.Size(41, 22));
        btnDrink.setTabIndex(35);
        btnDrink.setText("Drink");
        btnDrink.setVisible(false);
        btnDrink.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnDrink_Click(sender, e);
            }
        });
        //
        // btnInt
        //
        btnInt.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnInt.setLocation(new java.awt.Point(179, 240));
        btnInt.setName("btnInt");
        btnInt.setSize(new spacetrader.controls.Size(30, 22));
        btnInt.setTabIndex(36);
        btnInt.setText("Int.");
        btnInt.setVisible(false);
        btnInt.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnInt_Click(sender, e);
            }
        });
        //
        // btnYield
        //
        btnYield.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        btnYield.setLocation(new java.awt.Point(106, 240));
        btnYield.setName("btnYield");
        btnYield.setSize(new spacetrader.controls.Size(39, 22));
        btnYield.setTabIndex(37);
        btnYield.setText("Yield");
        btnYield.setVisible(false);
        btnYield.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnYield_Click(sender, e);
            }
        });
        //
        // picContinuous
        //
        picContinuous.setLocation(new java.awt.Point(214, 247));
        picContinuous.setName("picContinuous");
        picContinuous.setSize(new spacetrader.controls.Size(9, 9));
        picContinuous.setTabIndex(38);
        picContinuous.setTabStop(false);
        picContinuous.setVisible(false);
        //
        // ilContinuous
        //
        ilContinuous.setImageSize(new spacetrader.controls.Size(9, 9));
        ilContinuous.setImageStream(((spacetrader.controls.ImageListStreamer) (resources
                .getObject("ilContinuous.ImageStream"))));
        ilContinuous.setTransparentColor(java.awt.Color.white);
        //
        // picEncounterType
        //
        picEncounterType.setLocation(new java.awt.Point(220, 2));
        picEncounterType.setName("picEncounterType");
        picEncounterType.setSize(new spacetrader.controls.Size(12, 12));
        picEncounterType.setTabIndex(39);
        picEncounterType.setTabStop(false);
        //
        // ilEncounterType
        //
        ilEncounterType.setImageSize(new spacetrader.controls.Size(12, 12));
        ilEncounterType.setImageStream(((spacetrader.controls.ImageListStreamer) (resources
                .getObject("ilEncounterType.ImageStream"))));
        ilEncounterType.setTransparentColor(Color.white);
        //
        // picTrib00
        //
        picTrib00.setBackground(SystemColors.CONTROL);
        picTrib00.setLocation(new java.awt.Point(16, 16));
        picTrib00.setName("picTrib00");
        picTrib00.setSize(new spacetrader.controls.Size(12, 12));
        picTrib00.setTabIndex(41);
        picTrib00.setTabStop(false);
        picTrib00.setVisible(false);
        picTrib00.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // ilTribbles
        //
        ilTribbles.setImageSize(new spacetrader.controls.Size(12, 12));
        ilTribbles.setImageStream(((spacetrader.controls.ImageListStreamer) (resources
                .getObject("ilTribbles.ImageStream"))));
        ilTribbles.setTransparentColor(java.awt.Color.white);
        //
        // picTrib50
        //
        picTrib50.setBackground(SystemColors.CONTROL);
        picTrib50.setLocation(new java.awt.Point(16, 224));
        picTrib50.setName("picTrib50");
        picTrib50.setSize(new spacetrader.controls.Size(12, 12));
        picTrib50.setTabIndex(42);
        picTrib50.setTabStop(false);
        picTrib50.setVisible(false);
        picTrib50.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib10
        //
        picTrib10.setBackground(SystemColors.CONTROL);
        picTrib10.setLocation(new java.awt.Point(8, 56));
        picTrib10.setName("picTrib10");
        picTrib10.setSize(new spacetrader.controls.Size(12, 12));
        picTrib10.setTabIndex(43);
        picTrib10.setTabStop(false);
        picTrib10.setVisible(false);
        picTrib10.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib40
        //
        picTrib40.setBackground(SystemColors.CONTROL);
        picTrib40.setLocation(new java.awt.Point(8, 184));
        picTrib40.setName("picTrib40");
        picTrib40.setSize(new spacetrader.controls.Size(12, 12));
        picTrib40.setTabIndex(44);
        picTrib40.setTabStop(false);
        picTrib40.setVisible(false);
        picTrib40.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib20
        //
        picTrib20.setBackground(SystemColors.CONTROL);
        picTrib20.setLocation(new java.awt.Point(8, 96));
        picTrib20.setName("picTrib20");
        picTrib20.setSize(new spacetrader.controls.Size(12, 12));
        picTrib20.setTabIndex(45);
        picTrib20.setTabStop(false);
        picTrib20.setVisible(false);
        picTrib20.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib30
        //
        picTrib30.setBackground(SystemColors.CONTROL);
        picTrib30.setLocation(new java.awt.Point(16, 136));
        picTrib30.setName("picTrib30");
        picTrib30.setSize(new spacetrader.controls.Size(12, 12));
        picTrib30.setTabIndex(46);
        picTrib30.setTabStop(false);
        picTrib30.setVisible(false);
        picTrib30.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib04
        //
        picTrib04.setBackground(SystemColors.CONTROL);
        picTrib04.setLocation(new java.awt.Point(176, 8));
        picTrib04.setName("picTrib04");
        picTrib04.setSize(new spacetrader.controls.Size(12, 12));
        picTrib04.setTabIndex(47);
        picTrib04.setTabStop(false);
        picTrib04.setVisible(false);
        picTrib04.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib03
        //
        picTrib03.setBackground(SystemColors.CONTROL);
        picTrib03.setLocation(new java.awt.Point(128, 8));
        picTrib03.setName("picTrib03");
        picTrib03.setSize(new spacetrader.controls.Size(12, 12));
        picTrib03.setTabIndex(48);
        picTrib03.setTabStop(false);
        picTrib03.setVisible(false);
        picTrib03.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib02
        //
        picTrib02.setBackground(SystemColors.CONTROL);
        picTrib02.setLocation(new java.awt.Point(96, 16));
        picTrib02.setName("picTrib02");
        picTrib02.setSize(new spacetrader.controls.Size(12, 12));
        picTrib02.setTabIndex(49);
        picTrib02.setTabStop(false);
        picTrib02.setVisible(false);
        picTrib02.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib01
        //
        picTrib01.setBackground(SystemColors.CONTROL);
        picTrib01.setLocation(new java.awt.Point(56, 8));
        picTrib01.setName("picTrib01");
        picTrib01.setSize(new spacetrader.controls.Size(12, 12));
        picTrib01.setTabIndex(50);
        picTrib01.setTabStop(false);
        picTrib01.setVisible(false);
        picTrib01.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib05
        //
        picTrib05.setBackground(SystemColors.CONTROL);
        picTrib05.setLocation(new java.awt.Point(208, 16));
        picTrib05.setName("picTrib05");
        picTrib05.setSize(new spacetrader.controls.Size(12, 12));
        picTrib05.setTabIndex(51);
        picTrib05.setTabStop(false);
        picTrib05.setVisible(false);
        picTrib05.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib11
        //
        picTrib11.setBackground(SystemColors.CONTROL);
        picTrib11.setLocation(new java.awt.Point(32, 80));
        picTrib11.setName("picTrib11");
        picTrib11.setSize(new spacetrader.controls.Size(12, 12));
        picTrib11.setTabIndex(52);
        picTrib11.setTabStop(false);
        picTrib11.setVisible(false);
        picTrib11.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib12
        //
        picTrib12.setBackground(SystemColors.CONTROL);
        picTrib12.setLocation(new java.awt.Point(88, 56));
        picTrib12.setName("picTrib12");
        picTrib12.setSize(new spacetrader.controls.Size(12, 12));
        picTrib12.setTabIndex(53);
        picTrib12.setTabStop(false);
        picTrib12.setVisible(false);
        picTrib12.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib13
        //
        picTrib13.setBackground(SystemColors.CONTROL);
        picTrib13.setLocation(new java.awt.Point(128, 40));
        picTrib13.setName("picTrib13");
        picTrib13.setSize(new spacetrader.controls.Size(12, 12));
        picTrib13.setTabIndex(54);
        picTrib13.setTabStop(false);
        picTrib13.setVisible(false);
        picTrib13.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib14
        //
        picTrib14.setBackground(SystemColors.CONTROL);
        picTrib14.setLocation(new java.awt.Point(192, 72));
        picTrib14.setName("picTrib14");
        picTrib14.setSize(new spacetrader.controls.Size(12, 12));
        picTrib14.setTabIndex(55);
        picTrib14.setTabStop(false);
        picTrib14.setVisible(false);
        picTrib14.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib15
        //
        picTrib15.setBackground(SystemColors.CONTROL);
        picTrib15.setLocation(new java.awt.Point(216, 48));
        picTrib15.setName("picTrib15");
        picTrib15.setSize(new spacetrader.controls.Size(12, 12));
        picTrib15.setTabIndex(56);
        picTrib15.setTabStop(false);
        picTrib15.setVisible(false);
        picTrib15.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib21
        //
        picTrib21.setBackground(SystemColors.CONTROL);
        picTrib21.setLocation(new java.awt.Point(56, 96));
        picTrib21.setName("picTrib21");
        picTrib21.setSize(new spacetrader.controls.Size(12, 12));
        picTrib21.setTabIndex(57);
        picTrib21.setTabStop(false);
        picTrib21.setVisible(false);
        picTrib21.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib22
        //
        picTrib22.setBackground(SystemColors.CONTROL);
        picTrib22.setLocation(new java.awt.Point(96, 80));
        picTrib22.setName("picTrib22");
        picTrib22.setSize(new spacetrader.controls.Size(12, 12));
        picTrib22.setTabIndex(58);
        picTrib22.setTabStop(false);
        picTrib22.setVisible(false);
        picTrib22.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib23
        //
        picTrib23.setBackground(SystemColors.CONTROL);
        picTrib23.setLocation(new java.awt.Point(136, 88));
        picTrib23.setName("picTrib23");
        picTrib23.setSize(new spacetrader.controls.Size(12, 12));
        picTrib23.setTabIndex(59);
        picTrib23.setTabStop(false);
        picTrib23.setVisible(false);
        picTrib23.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib24
        //
        picTrib24.setBackground(SystemColors.CONTROL);
        picTrib24.setLocation(new java.awt.Point(176, 104));
        picTrib24.setName("picTrib24");
        picTrib24.setSize(new spacetrader.controls.Size(12, 12));
        picTrib24.setTabIndex(60);
        picTrib24.setTabStop(false);
        picTrib24.setVisible(false);
        picTrib24.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib25
        //
        picTrib25.setBackground(SystemColors.CONTROL);
        picTrib25.setLocation(new java.awt.Point(216, 96));
        picTrib25.setName("picTrib25");
        picTrib25.setSize(new spacetrader.controls.Size(12, 12));
        picTrib25.setTabIndex(61);
        picTrib25.setTabStop(false);
        picTrib25.setVisible(false);
        picTrib25.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib31
        //
        picTrib31.setBackground(SystemColors.CONTROL);
        picTrib31.setLocation(new java.awt.Point(56, 128));
        picTrib31.setName("picTrib31");
        picTrib31.setSize(new spacetrader.controls.Size(12, 12));
        picTrib31.setTabIndex(62);
        picTrib31.setTabStop(false);
        picTrib31.setVisible(false);
        picTrib31.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib32
        //
        picTrib32.setBackground(SystemColors.CONTROL);
        picTrib32.setLocation(new java.awt.Point(96, 120));
        picTrib32.setName("picTrib32");
        picTrib32.setSize(new spacetrader.controls.Size(12, 12));
        picTrib32.setTabIndex(63);
        picTrib32.setTabStop(false);
        picTrib32.setVisible(false);
        picTrib32.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib33
        //
        picTrib33.setBackground(SystemColors.CONTROL);
        picTrib33.setLocation(new java.awt.Point(128, 128));
        picTrib33.setName("picTrib33");
        picTrib33.setSize(new spacetrader.controls.Size(12, 12));
        picTrib33.setTabIndex(64);
        picTrib33.setTabStop(false);
        picTrib33.setVisible(false);
        picTrib33.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib34
        //
        picTrib34.setBackground(SystemColors.CONTROL);
        picTrib34.setLocation(new java.awt.Point(168, 144));
        picTrib34.setName("picTrib34");
        picTrib34.setSize(new spacetrader.controls.Size(12, 12));
        picTrib34.setTabIndex(65);
        picTrib34.setTabStop(false);
        picTrib34.setVisible(false);
        picTrib34.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib35
        //
        picTrib35.setBackground(SystemColors.CONTROL);
        picTrib35.setLocation(new java.awt.Point(208, 128));
        picTrib35.setName("picTrib35");
        picTrib35.setSize(new spacetrader.controls.Size(12, 12));
        picTrib35.setTabIndex(66);
        picTrib35.setTabStop(false);
        picTrib35.setVisible(false);
        picTrib35.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib41
        //
        picTrib41.setBackground(SystemColors.CONTROL);
        picTrib41.setLocation(new java.awt.Point(48, 176));
        picTrib41.setName("picTrib41");
        picTrib41.setSize(new spacetrader.controls.Size(12, 12));
        picTrib41.setTabIndex(67);
        picTrib41.setTabStop(false);
        picTrib41.setVisible(false);
        picTrib41.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib51
        //
        picTrib51.setBackground(SystemColors.CONTROL);
        picTrib51.setLocation(new java.awt.Point(64, 216));
        picTrib51.setName("picTrib51");
        picTrib51.setSize(new spacetrader.controls.Size(12, 12));
        picTrib51.setTabIndex(68);
        picTrib51.setTabStop(false);
        picTrib51.setVisible(false);
        picTrib51.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib42
        //
        picTrib42.setBackground(SystemColors.CONTROL);
        picTrib42.setLocation(new java.awt.Point(88, 168));
        picTrib42.setName("picTrib42");
        picTrib42.setSize(new spacetrader.controls.Size(12, 12));
        picTrib42.setTabIndex(69);
        picTrib42.setTabStop(false);
        picTrib42.setVisible(false);
        picTrib42.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib52
        //
        picTrib52.setBackground(SystemColors.CONTROL);
        picTrib52.setLocation(new java.awt.Point(96, 224));
        picTrib52.setName("picTrib52");
        picTrib52.setSize(new spacetrader.controls.Size(12, 12));
        picTrib52.setTabIndex(70);
        picTrib52.setTabStop(false);
        picTrib52.setVisible(false);
        picTrib52.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib43
        //
        picTrib43.setBackground(SystemColors.CONTROL);
        picTrib43.setLocation(new java.awt.Point(136, 176));
        picTrib43.setName("picTrib43");
        picTrib43.setSize(new spacetrader.controls.Size(12, 12));
        picTrib43.setTabIndex(71);
        picTrib43.setTabStop(false);
        picTrib43.setVisible(false);
        picTrib43.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib53
        //
        picTrib53.setBackground(SystemColors.CONTROL);
        picTrib53.setLocation(new java.awt.Point(144, 216));
        picTrib53.setName("picTrib53");
        picTrib53.setSize(new spacetrader.controls.Size(12, 12));
        picTrib53.setTabIndex(72);
        picTrib53.setTabStop(false);
        picTrib53.setVisible(false);
        picTrib53.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib44
        //
        picTrib44.setBackground(SystemColors.CONTROL);
        picTrib44.setLocation(new java.awt.Point(184, 184));
        picTrib44.setName("picTrib44");
        picTrib44.setSize(new spacetrader.controls.Size(12, 12));
        picTrib44.setTabIndex(73);
        picTrib44.setTabStop(false);
        picTrib44.setVisible(false);
        picTrib44.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib45
        //
        picTrib45.setBackground(SystemColors.CONTROL);
        picTrib45.setLocation(new java.awt.Point(216, 176));
        picTrib45.setName("picTrib45");
        picTrib45.setSize(new spacetrader.controls.Size(12, 12));
        picTrib45.setTabIndex(74);
        picTrib45.setTabStop(false);
        picTrib45.setVisible(false);
        picTrib45.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib54
        //
        picTrib54.setBackground(SystemColors.CONTROL);
        picTrib54.setLocation(new java.awt.Point(176, 224));
        picTrib54.setName("picTrib54");
        picTrib54.setSize(new spacetrader.controls.Size(12, 12));
        picTrib54.setTabIndex(75);
        picTrib54.setTabStop(false);
        picTrib54.setVisible(false);
        picTrib54.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib55
        //
        picTrib55.setBackground(SystemColors.CONTROL);
        picTrib55.setLocation(new java.awt.Point(208, 216));
        picTrib55.setName("picTrib55");
        picTrib55.setSize(new spacetrader.controls.Size(12, 12));
        picTrib55.setTabIndex(76);
        picTrib55.setTabStop(false);
        picTrib55.setVisible(false);
        picTrib55.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // tmrTick
        //
        tmrTick.setInterval(1000);
        tmrTick.Tick = new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                tmrTick_Tick(sender, e);
            }
        };
        //
        // FormEncounter
        //
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setClientSize(new spacetrader.controls.Size(234, 271));
        this.setControlBox(false);
        controls.add(picTrib55);
        controls.add(picTrib54);
        controls.add(picTrib45);
        controls.add(picTrib44);
        controls.add(picTrib53);
        controls.add(picTrib43);
        controls.add(picTrib52);
        controls.add(picTrib42);
        controls.add(picTrib51);
        controls.add(picTrib41);
        controls.add(picTrib35);
        controls.add(picTrib34);
        controls.add(picTrib33);
        controls.add(picTrib32);
        controls.add(picTrib31);
        controls.add(picTrib25);
        controls.add(picTrib24);
        controls.add(picTrib23);
        controls.add(picTrib22);
        controls.add(picTrib21);
        controls.add(picTrib15);
        controls.add(picTrib14);
        controls.add(picTrib13);
        controls.add(picTrib12);
        controls.add(picTrib11);
        controls.add(picTrib05);
        controls.add(picTrib01);
        controls.add(picTrib02);
        controls.add(picTrib03);
        controls.add(picTrib04);
        controls.add(picTrib30);
        controls.add(picTrib20);
        controls.add(picTrib40);
        controls.add(picTrib10);
        controls.add(picTrib50);
        controls.add(picTrib00);
        controls.add(picEncounterType);
        controls.add(picContinuous);
        controls.add(btnYield);
        controls.add(btnInt);
        controls.add(btnMeet);
        controls.add(btnPlunder);
        controls.add(btnTrade);
        controls.add(btnIgnore);
        controls.add(btnSurrender);
        controls.add(btnBribe);
        controls.add(btnSubmit);
        controls.add(btnFlee);
        controls.add(lblOpponentShields);
        controls.add(lblOpponentHull);
        controls.add(lblYouShields);
        controls.add(lblYouHull);
        controls.add(lblYouShip);
        controls.add(lblOpponentShip);
        controls.add(lblYouLabel);
        controls.add(lblOpponentLabel);
        controls.add(lblAction);
        controls.add(picShipOpponent);
        controls.add(picShipYou);
        controls.add(lblEncounter);
        controls.add(btnDrink);
        controls.add(btnBoard);
        controls.add(btnAttack);
        this.setFormBorderStyle(spacetrader.controls.FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormEncounter");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Encounter");

    }

    // #endregion

    private void DisableAuto() {
        tmrTick.Stop();

        game.setEncounterContinueFleeing(false);
        game.setEncounterContinueAttacking(false);
        btnInt.setVisible(false);
        picContinuous.setVisible(false);
    }

    private void ExecuteAction() {
        if ((_result = game.EncounterExecuteAction()) == EncounterResult.Continue) {
            UpdateButtons();
            UpdateShipStats();

            lblEncounter.setText(game.EncounterText());
            lblAction.setText(game.EncounterAction());

            if (game.getEncounterContinueFleeing()
                    || game.getEncounterContinueAttacking())
                tmrTick.Start();
        } else
            close();
    }

    private void Exit(EncounterResult result) {
        _result = result;
        close();
    }

    private void UpdateButtons() {
        boolean[] visible = new boolean[buttons.length];

        switch (game.getEncounterType()) {
            case BottleGood:
            case BottleOld:
                visible[DRINK] = true;
                visible[IGNORE] = true;
                btnIgnore.setLeft(btnDrink.getLeft() + btnDrink.getWidth() + 8);
                break;
            case CaptainAhab:
            case CaptainConrad:
            case CaptainHuie:
                visible[ATTACK] = true;
                visible[IGNORE] = true;
                visible[MEET] = true;
                break;
            case DragonflyAttack:
            case FamousCaptainAttack:
            case ScorpionAttack:
            case SpaceMonsterAttack:
            case TraderAttack:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                btnInt.setLeft(btnFlee.getLeft() + btnFlee.getWidth() + 8);
                break;
            case DragonflyIgnore:
            case FamousCaptDisabled:
            case PoliceDisabled:
            case PoliceFlee:
            case PoliceIgnore:
            case PirateFlee:
            case PirateIgnore:
            case ScarabIgnore:
            case ScorpionIgnore:
            case SpaceMonsterIgnore:
            case TraderFlee:
            case TraderIgnore:
                visible[ATTACK] = true;
                visible[IGNORE] = true;
                break;
            case MarieCeleste:
                visible[BOARD] = true;
                visible[IGNORE] = true;
                btnIgnore.setLeft(btnBoard.getLeft() + btnBoard.getWidth() + 8);
                break;
            case MarieCelestePolice:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                visible[YIELD] = true;
                visible[BRIBE] = true;
                btnBribe.setLeft(btnYield.getLeft() + btnYield.getWidth() + 8);
                break;
            case PirateAttack:
            case PoliceAttack:
            case PoliceSurrender:
            case ScarabAttack:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                visible[SURRENDER] = true;
                btnInt.setLeft(btnSurrender.getLeft() + btnSurrender.getWidth() + 8);
                break;
            case PirateDisabled:
            case PirateSurrender:
            case TraderDisabled:
            case TraderSurrender:
                visible[ATTACK] = true;
                visible[PLUNDER] = true;
                break;
            case PoliceInspect:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                visible[SUBMIT] = true;
                visible[BRIBE] = true;
                break;
            case TraderBuy:
            case TraderSell:
                visible[ATTACK] = true;
                visible[IGNORE] = true;
                visible[TRADE] = true;
                break;
        }

        if (game.getEncounterContinueAttacking() || game.getEncounterContinueFleeing())
            visible[INT] = true;

        for (int i = 0; i < visible.length; i++) {
            if (visible[i] != buttons[i].isVisible()) {
                buttons[i].setVisible(visible[i]);
                if (i == INT)
                    picContinuous.setVisible(visible[i]);
            }
        }

        if (picContinuous.isVisible())
            picContinuous.setImage(ilContinuous.getImages()[contImg = (contImg + 1) % 2]);
    }

    private void UpdateShipInfo() {
        lblYouShip.setText(cmdrship.Name());
        lblOpponentShip.setText(opponent.Name());

        UpdateShipStats();
    }

    private void UpdateShipStats() {
        lblYouHull.setText(cmdrship.HullText());
        lblYouShields.setText(cmdrship.ShieldText());
        lblOpponentHull.setText(opponent.HullText());
        lblOpponentShields.setText(opponent.ShieldText());

        picShipYou.refresh();
        picShipOpponent.refresh();
    }

    private void UpdateTribbles() {
        PictureBox[] tribbles = new PictureBox[]{picTrib00, picTrib01,
                picTrib02, picTrib03, picTrib04, picTrib05, picTrib10,
                picTrib11, picTrib12, picTrib13, picTrib14, picTrib15,
                picTrib20, picTrib21, picTrib22, picTrib23, picTrib24,
                picTrib25, picTrib30, picTrib31, picTrib32, picTrib33,
                picTrib34, picTrib35, picTrib40, picTrib41, picTrib42,
                picTrib43, picTrib44, picTrib45, picTrib50, picTrib51,
                picTrib52, picTrib53, picTrib54, picTrib55};
        int toShow = Math.min(tribbles.length, (int) Math
                .sqrt(cmdrship.getTribbles()
                        / Math.ceil(Consts.MaxTribbles
                        / Math.pow(tribbles.length + 1, 2))));

        for (int i = 0; i < toShow; i++) {
            int index = Functions.GetRandom(tribbles.length);
            while (tribbles[index].isVisible())
                index = (index + 1) % tribbles.length;

            tribbles[index].setImage(ilTribbles.getImages()[Functions
                    .GetRandom(ilTribbles.getImages().length)]);
            tribbles[index].setVisible(true);
        }
    }

    // #endregion

    // #region Event Handlers

    private void btnAttack_Click(Object sender, EventArgs e) {
        DisableAuto();

        if (game.EncounterVerifyAttack())
            ExecuteAction();
    }

    private void btnBoard_Click(Object sender, EventArgs e) {
        if (game.EncounterVerifyBoard())
            Exit(EncounterResult.Normal);
    }

    private void btnBribe_Click(Object sender, EventArgs e) {
        if (game.EncounterVerifyBribe())
            Exit(EncounterResult.Normal);
    }

    private void btnDrink_Click(Object sender, EventArgs e) {
        game.EncounterDrink();

        Exit(EncounterResult.Normal);
    }

    private void btnFlee_Click(Object sender, EventArgs e) {
        DisableAuto();

        if (game.EncounterVerifyFlee())
            ExecuteAction();
    }

    private void btnIgnore_Click(Object sender, EventArgs e) {
        DisableAuto();

        Exit(EncounterResult.Normal);
    }

    private void btnInt_Click(Object sender, EventArgs e) {
        DisableAuto();
    }

    private void btnMeet_Click(Object sender, EventArgs e) {
        game.EncounterMeet();

        Exit(EncounterResult.Normal);
    }

    private void btnPlunder_Click(Object sender, EventArgs e) {
        DisableAuto();

        game.EncounterPlunder();

        Exit(EncounterResult.Normal);
    }

    private void btnSubmit_Click(Object sender, EventArgs e) {
        if (game.EncounterVerifySubmit())
            Exit(cmdrship.IllegalSpecialCargo() ? EncounterResult.Arrested
                    : EncounterResult.Normal);
    }

    private void btnSurrender_Click(Object sender, EventArgs e) {
        DisableAuto();

        if ((_result = game.EncounterVerifySurrender()) != EncounterResult.Continue)
            close();
    }

    private void btnTrade_Click(Object sender, EventArgs e) {
        game.EncounterTrade();

        Exit(EncounterResult.Normal);
    }

    private void btnYield_Click(Object sender, EventArgs e) {
        if ((_result = game.EncounterVerifyYield()) != EncounterResult.Continue)
            close();
    }

    private void picShipOpponent_Paint(Object sender,
                                       spacetrader.controls.PaintEventArgs e) {
        Functions.PaintShipImage(opponent, e.getGraphics(),
                picShipOpponent.getBackground());
    }

    private void picShipYou_Paint(Object sender,
                                  spacetrader.controls.PaintEventArgs e) {
        Functions.PaintShipImage(cmdrship, e.getGraphics(), picShipYou.getBackground());
    }

    private void picTrib_Click(Object sender, EventArgs e) {
        GuiFacade.alert(AlertType.TribblesSqueek);
    }

    private void tmrTick_Tick(Object sender, EventArgs e) {
        DisableAuto();

        ExecuteAction();
    }

    // #endregion

    // #region Properties


    public EncounterResult Result() {
        return _result;
    }

    // #endregion
}
