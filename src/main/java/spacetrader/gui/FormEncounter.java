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

import jwinforms.Button;
import jwinforms.Container;
import jwinforms.*;
import spacetrader.Consts;
import spacetrader.Functions;
import spacetrader.Game;
import spacetrader.Ship;
import spacetrader.enums.AlertType;
import spacetrader.enums.EncounterResult;
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
    private final Game game = Game.CurrentGame();
    private final Ship cmdrship = Game.CurrentGame().Commander().getShip();
    private final Ship opponent = Game.CurrentGame().getOpponent();
    private jwinforms.Label lblEncounter;
    private jwinforms.PictureBox picShipYou;
    private jwinforms.PictureBox picShipOpponent;
    private jwinforms.Label lblOpponentLabel;
    private jwinforms.Label lblYouLabel;
    private jwinforms.Label lblOpponentShip;
    private jwinforms.Label lblYouShip;
    private jwinforms.Label lblYouHull;
    private jwinforms.Label lblYouShields;
    private jwinforms.Label lblOpponentShields;
    private jwinforms.Label lblOpponentHull;
    private jwinforms.Label lblAction;
    private jwinforms.Button btnAttack;
    private jwinforms.Button btnFlee;
    private jwinforms.Button btnSubmit;
    private jwinforms.Button btnBribe;
    private jwinforms.Button btnSurrender;
    private jwinforms.Button btnIgnore;
    private jwinforms.Button btnTrade;
    private jwinforms.Button btnPlunder;
    private jwinforms.Button btnBoard;
    private jwinforms.Button btnMeet;
    private jwinforms.Button btnDrink;
    private jwinforms.Button btnInt;
    private jwinforms.Button btnYield;
    private jwinforms.PictureBox picContinuous;
    private jwinforms.ImageList ilContinuous;
    private jwinforms.PictureBox picEncounterType;
    private jwinforms.ImageList ilEncounterType;
    private jwinforms.ImageList ilTribbles;
    private jwinforms.PictureBox picTrib00;
    private jwinforms.PictureBox picTrib50;
    private jwinforms.PictureBox picTrib10;
    private jwinforms.PictureBox picTrib40;
    private jwinforms.PictureBox picTrib20;
    private jwinforms.PictureBox picTrib30;
    private jwinforms.PictureBox picTrib04;
    private jwinforms.PictureBox picTrib03;
    private jwinforms.PictureBox picTrib02;
    private jwinforms.PictureBox picTrib01;
    private jwinforms.PictureBox picTrib05;
    private jwinforms.PictureBox picTrib11;
    private jwinforms.PictureBox picTrib12;
    private jwinforms.PictureBox picTrib13;
    private jwinforms.PictureBox picTrib14;
    private jwinforms.PictureBox picTrib15;
    private jwinforms.PictureBox picTrib21;
    private jwinforms.PictureBox picTrib22;
    private jwinforms.PictureBox picTrib23;
    private jwinforms.PictureBox picTrib24;
    private jwinforms.PictureBox picTrib25;
    private jwinforms.PictureBox picTrib31;

    // #endregion

    // #region Constants
    private jwinforms.PictureBox picTrib32;
    private jwinforms.PictureBox picTrib33;
    private jwinforms.PictureBox picTrib34;
    private jwinforms.PictureBox picTrib35;
    private jwinforms.PictureBox picTrib41;
    private jwinforms.PictureBox picTrib51;
    private jwinforms.PictureBox picTrib42;
    private jwinforms.PictureBox picTrib52;
    private jwinforms.PictureBox picTrib43;
    private jwinforms.PictureBox picTrib53;
    private jwinforms.PictureBox picTrib44;
    private jwinforms.PictureBox picTrib45;
    private jwinforms.PictureBox picTrib54;

    // #endregion

    // #region Member Declarations
    private jwinforms.PictureBox picTrib55;
    private jwinforms.Timer tmrTick;
    private IContainer components;
    private int contImg = 1;

    private EncounterResult _result = EncounterResult.Continue;

    // #endregion

    // #region Methods

    public FormEncounter() {
        InitializeComponent();

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
    private void InitializeComponent() {
        components = new Container();
        ResourceManager resources = new ResourceManager(FormEncounter.class);
        lblEncounter = new jwinforms.Label();
        picShipYou = new jwinforms.PictureBox();
        picShipOpponent = new jwinforms.PictureBox();
        lblAction = new jwinforms.Label();
        lblOpponentLabel = new jwinforms.Label();
        lblYouLabel = new jwinforms.Label();
        lblOpponentShip = new jwinforms.Label();
        lblYouShip = new jwinforms.Label();
        lblYouHull = new jwinforms.Label();
        lblYouShields = new jwinforms.Label();
        lblOpponentShields = new jwinforms.Label();
        lblOpponentHull = new jwinforms.Label();
        btnAttack = new jwinforms.Button();
        btnFlee = new jwinforms.Button();
        btnSubmit = new jwinforms.Button();
        btnBribe = new jwinforms.Button();
        btnSurrender = new jwinforms.Button();
        btnIgnore = new jwinforms.Button();
        btnTrade = new jwinforms.Button();
        btnPlunder = new jwinforms.Button();
        btnBoard = new jwinforms.Button();
        btnMeet = new jwinforms.Button();
        btnDrink = new jwinforms.Button();
        btnInt = new jwinforms.Button();
        btnYield = new jwinforms.Button();
        picContinuous = new jwinforms.PictureBox();
        ilContinuous = new jwinforms.ImageList(components);
        picEncounterType = new jwinforms.PictureBox();
        ilEncounterType = new jwinforms.ImageList(
                components);
        picTrib00 = new jwinforms.PictureBox();
        ilTribbles = new jwinforms.ImageList(components);
        picTrib50 = new jwinforms.PictureBox();
        picTrib10 = new jwinforms.PictureBox();
        picTrib40 = new jwinforms.PictureBox();
        picTrib20 = new jwinforms.PictureBox();
        picTrib30 = new jwinforms.PictureBox();
        picTrib04 = new jwinforms.PictureBox();
        picTrib03 = new jwinforms.PictureBox();
        picTrib02 = new jwinforms.PictureBox();
        picTrib01 = new jwinforms.PictureBox();
        picTrib05 = new jwinforms.PictureBox();
        picTrib11 = new jwinforms.PictureBox();
        picTrib12 = new jwinforms.PictureBox();
        picTrib13 = new jwinforms.PictureBox();
        picTrib14 = new jwinforms.PictureBox();
        picTrib15 = new jwinforms.PictureBox();
        picTrib21 = new jwinforms.PictureBox();
        picTrib22 = new jwinforms.PictureBox();
        picTrib23 = new jwinforms.PictureBox();
        picTrib24 = new jwinforms.PictureBox();
        picTrib25 = new jwinforms.PictureBox();
        picTrib31 = new jwinforms.PictureBox();
        picTrib32 = new jwinforms.PictureBox();
        picTrib33 = new jwinforms.PictureBox();
        picTrib34 = new jwinforms.PictureBox();
        picTrib35 = new jwinforms.PictureBox();
        picTrib41 = new jwinforms.PictureBox();
        picTrib51 = new jwinforms.PictureBox();
        picTrib42 = new jwinforms.PictureBox();
        picTrib52 = new jwinforms.PictureBox();
        picTrib43 = new jwinforms.PictureBox();
        picTrib53 = new jwinforms.PictureBox();
        picTrib44 = new jwinforms.PictureBox();
        picTrib45 = new jwinforms.PictureBox();
        picTrib54 = new jwinforms.PictureBox();
        picTrib55 = new jwinforms.PictureBox();
        tmrTick = new jwinforms.Timer(components);
        this.SuspendLayout();
        //
        // lblEncounter
        //
        lblEncounter.setLocation(new java.awt.Point(8, 152));
        lblEncounter.setName("lblEncounter");
        lblEncounter.setSize(new jwinforms.Size(232, 26));
        lblEncounter.setTabIndex(0);
        lblEncounter.setText("At 20 clicks from Tarchannen, you encounter the famous Captain Ahab.");
        //
        // picShipYou
        //
        picShipYou.setBackColor(java.awt.Color.white);
        picShipYou.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        picShipYou.setLocation(new java.awt.Point(26, 24));
        picShipYou.setName("picShipYou");
        picShipYou.setSize(new jwinforms.Size(70, 58));
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
        picShipOpponent.setBackColor(java.awt.Color.white);
        picShipOpponent.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        picShipOpponent.setLocation(new java.awt.Point(138, 24));
        picShipOpponent.setName("picShipOpponent");
        picShipOpponent.setSize(new jwinforms.Size(70, 58));
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
        lblAction.setSize(new jwinforms.Size(232, 39));
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
        lblOpponentLabel.setSize(new jwinforms.Size(59, 16));
        lblOpponentLabel.setTabIndex(16);
        lblOpponentLabel.setText("Opponent:");
        //
        // lblYouLabel
        //
        lblYouLabel.setAutoSize(true);
        lblYouLabel.setFont(FontCollection.bold825);
        lblYouLabel.setLocation(new java.awt.Point(45, 8));
        lblYouLabel.setName("lblYouLabel");
        lblYouLabel.setSize(new jwinforms.Size(28, 16));
        lblYouLabel.setTabIndex(17);
        lblYouLabel.setText("You:");
        //
        // lblOpponentShip
        //
        lblOpponentShip.setLocation(new java.awt.Point(138, 88));
        lblOpponentShip.setName("lblOpponentShip");
        lblOpponentShip.setSize(new jwinforms.Size(80, 13));
        lblOpponentShip.setTabIndex(18);
        lblOpponentShip.setText("Space Monster");
        //
        // lblYouShip
        //
        lblYouShip.setLocation(new java.awt.Point(26, 88));
        lblYouShip.setName("lblYouShip");
        lblYouShip.setSize(new jwinforms.Size(100, 13));
        lblYouShip.setTabIndex(19);
        lblYouShip.setText("Grasshopper");
        //
        // lblYouHull
        //
        lblYouHull.setLocation(new java.awt.Point(26, 104));
        lblYouHull.setName("lblYouHull");
        lblYouHull.setSize(new jwinforms.Size(68, 13));
        lblYouHull.setTabIndex(20);
        lblYouHull.setText("Hull at 100%");
        //
        // lblYouShields
        //
        lblYouShields.setLocation(new java.awt.Point(26, 120));
        lblYouShields.setName("lblYouShields");
        lblYouShields.setSize(new jwinforms.Size(86, 13));
        lblYouShields.setTabIndex(21);
        lblYouShields.setText("Shields at 100%");
        //
        // lblOpponentShields
        //
        lblOpponentShields.setLocation(new java.awt.Point(138, 120));
        lblOpponentShields.setName("lblOpponentShields");
        lblOpponentShields.setSize(new jwinforms.Size(86, 13));
        lblOpponentShields.setTabIndex(23);
        lblOpponentShields.setText("Shields at 100%");
        //
        // lblOpponentHull
        //
        lblOpponentHull.setLocation(new java.awt.Point(138, 104));
        lblOpponentHull.setName("lblOpponentHull");
        lblOpponentHull.setSize(new jwinforms.Size(68, 13));
        lblOpponentHull.setTabIndex(22);
        lblOpponentHull.setText("Hull at 100%");
        //
        // btnAttack
        //
        btnAttack.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnAttack.setLocation(new java.awt.Point(8, 240));
        btnAttack.setName("btnAttack");
        btnAttack.setSize(new jwinforms.Size(46, 22));
        btnAttack.setTabIndex(24);
        btnAttack.setText("Attack");
        btnAttack.setVisible(false);
        btnAttack.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnAttack_Click(sender, e);
            }
        });
        //
        // btnFlee
        //
        btnFlee.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnFlee.setLocation(new java.awt.Point(62, 240));
        btnFlee.setName("btnFlee");
        btnFlee.setSize(new jwinforms.Size(36, 22));
        btnFlee.setTabIndex(25);
        btnFlee.setText("Flee");
        btnFlee.setVisible(false);
        btnFlee.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnFlee_Click(sender, e);
            }
        });
        //
        // btnSubmit
        //
        btnSubmit.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSubmit.setLocation(new java.awt.Point(106, 240));
        btnSubmit.setName("btnSubmit");
        btnSubmit.setSize(new jwinforms.Size(49, 22));
        btnSubmit.setTabIndex(26);
        btnSubmit.setText("Submit");
        btnSubmit.setVisible(false);
        btnSubmit.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnSubmit_Click(sender, e);
            }
        });
        //
        // btnBribe
        //
        btnBribe.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBribe.setLocation(new java.awt.Point(163, 240));
        btnBribe.setName("btnBribe");
        btnBribe.setSize(new jwinforms.Size(41, 22));
        btnBribe.setTabIndex(27);
        btnBribe.setText("Bribe");
        btnBribe.setVisible(false);
        btnBribe.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBribe_Click(sender, e);
            }
        });
        //
        // btnSurrender
        //
        btnSurrender.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSurrender.setLocation(new java.awt.Point(106, 240));
        btnSurrender.setName("btnSurrender");
        btnSurrender.setSize(new jwinforms.Size(65, 22));
        btnSurrender.setTabIndex(28);
        btnSurrender.setText("Surrender");
        btnSurrender.setVisible(false);
        btnSurrender.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnSurrender_Click(sender, e);
            }
        });
        //
        // btnIgnore
        //
        btnIgnore.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnIgnore.setLocation(new java.awt.Point(62, 240));
        btnIgnore.setName("btnIgnore");
        btnIgnore.setSize(new jwinforms.Size(46, 22));
        btnIgnore.setTabIndex(29);
        btnIgnore.setText("Ignore");
        btnIgnore.setVisible(false);
        btnIgnore.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnIgnore_Click(sender, e);
            }
        });
        //
        // btnTrade
        //
        btnTrade.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnTrade.setLocation(new java.awt.Point(116, 240));
        btnTrade.setName("btnTrade");
        btnTrade.setSize(new jwinforms.Size(44, 22));
        btnTrade.setTabIndex(30);
        btnTrade.setText("Trade");
        btnTrade.setVisible(false);
        btnTrade.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnTrade_Click(sender, e);
            }
        });
        //
        // btnPlunder
        //
        btnPlunder.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnPlunder.setLocation(new java.awt.Point(62, 240));
        btnPlunder.setName("btnPlunder");
        btnPlunder.setSize(new jwinforms.Size(53, 22));
        btnPlunder.setTabIndex(31);
        btnPlunder.setText("Plunder");
        btnPlunder.setVisible(false);
        btnPlunder.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnPlunder_Click(sender, e);
            }
        });
        //
        // btnBoard
        //
        btnBoard.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnBoard.setLocation(new java.awt.Point(8, 240));
        btnBoard.setName("btnBoard");
        btnBoard.setSize(new jwinforms.Size(44, 22));
        btnBoard.setTabIndex(32);
        btnBoard.setText("Board");
        btnBoard.setVisible(false);
        btnBoard.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnBoard_Click(sender, e);
            }
        });
        //
        // btnMeet
        //
        btnMeet.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnMeet.setLocation(new java.awt.Point(116, 240));
        btnMeet.setName("btnMeet");
        btnMeet.setSize(new jwinforms.Size(39, 22));
        btnMeet.setTabIndex(34);
        btnMeet.setText("Meet");
        btnMeet.setVisible(false);
        btnMeet.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnMeet_Click(sender, e);
            }
        });
        //
        // btnDrink
        //
        btnDrink.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnDrink.setLocation(new java.awt.Point(8, 240));
        btnDrink.setName("btnDrink");
        btnDrink.setSize(new jwinforms.Size(41, 22));
        btnDrink.setTabIndex(35);
        btnDrink.setText("Drink");
        btnDrink.setVisible(false);
        btnDrink.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnDrink_Click(sender, e);
            }
        });
        //
        // btnInt
        //
        btnInt.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnInt.setLocation(new java.awt.Point(179, 240));
        btnInt.setName("btnInt");
        btnInt.setSize(new jwinforms.Size(30, 22));
        btnInt.setTabIndex(36);
        btnInt.setText("Int.");
        btnInt.setVisible(false);
        btnInt.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnInt_Click(sender, e);
            }
        });
        //
        // btnYield
        //
        btnYield.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnYield.setLocation(new java.awt.Point(106, 240));
        btnYield.setName("btnYield");
        btnYield.setSize(new jwinforms.Size(39, 22));
        btnYield.setTabIndex(37);
        btnYield.setText("Yield");
        btnYield.setVisible(false);
        btnYield.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnYield_Click(sender, e);
            }
        });
        //
        // picContinuous
        //
        picContinuous.setLocation(new java.awt.Point(214, 247));
        picContinuous.setName("picContinuous");
        picContinuous.setSize(new jwinforms.Size(9, 9));
        picContinuous.setTabIndex(38);
        picContinuous.setTabStop(false);
        picContinuous.setVisible(false);
        //
        // ilContinuous
        //
        ilContinuous.setImageSize(new jwinforms.Size(9, 9));
        ilContinuous.setImageStream(((jwinforms.ImageListStreamer) (resources
                .getObject("ilContinuous.ImageStream"))));
        ilContinuous.setTransparentColor(java.awt.Color.white);
        //
        // picEncounterType
        //
        picEncounterType.setLocation(new java.awt.Point(220, 2));
        picEncounterType.setName("picEncounterType");
        picEncounterType.setSize(new jwinforms.Size(12, 12));
        picEncounterType.setTabIndex(39);
        picEncounterType.setTabStop(false);
        //
        // ilEncounterType
        //
        ilEncounterType.setImageSize(new jwinforms.Size(12, 12));
        ilEncounterType.setImageStream(((jwinforms.ImageListStreamer) (resources
                .getObject("ilEncounterType.ImageStream"))));
        ilEncounterType.setTransparentColor(Color.white);
        //
        // picTrib00
        //
        picTrib00.setBackColor(SystemColors.Control);
        picTrib00.setLocation(new java.awt.Point(16, 16));
        picTrib00.setName("picTrib00");
        picTrib00.setSize(new jwinforms.Size(12, 12));
        picTrib00.setTabIndex(41);
        picTrib00.setTabStop(false);
        picTrib00.setVisible(false);
        picTrib00.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // ilTribbles
        //
        ilTribbles.setImageSize(new jwinforms.Size(12, 12));
        ilTribbles.setImageStream(((jwinforms.ImageListStreamer) (resources
                .getObject("ilTribbles.ImageStream"))));
        ilTribbles.setTransparentColor(java.awt.Color.white);
        //
        // picTrib50
        //
        picTrib50.setBackColor(SystemColors.Control);
        picTrib50.setLocation(new java.awt.Point(16, 224));
        picTrib50.setName("picTrib50");
        picTrib50.setSize(new jwinforms.Size(12, 12));
        picTrib50.setTabIndex(42);
        picTrib50.setTabStop(false);
        picTrib50.setVisible(false);
        picTrib50.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib10
        //
        picTrib10.setBackColor(SystemColors.Control);
        picTrib10.setLocation(new java.awt.Point(8, 56));
        picTrib10.setName("picTrib10");
        picTrib10.setSize(new jwinforms.Size(12, 12));
        picTrib10.setTabIndex(43);
        picTrib10.setTabStop(false);
        picTrib10.setVisible(false);
        picTrib10.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib40
        //
        picTrib40.setBackColor(SystemColors.Control);
        picTrib40.setLocation(new java.awt.Point(8, 184));
        picTrib40.setName("picTrib40");
        picTrib40.setSize(new jwinforms.Size(12, 12));
        picTrib40.setTabIndex(44);
        picTrib40.setTabStop(false);
        picTrib40.setVisible(false);
        picTrib40.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib20
        //
        picTrib20.setBackColor(SystemColors.Control);
        picTrib20.setLocation(new java.awt.Point(8, 96));
        picTrib20.setName("picTrib20");
        picTrib20.setSize(new jwinforms.Size(12, 12));
        picTrib20.setTabIndex(45);
        picTrib20.setTabStop(false);
        picTrib20.setVisible(false);
        picTrib20.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib30
        //
        picTrib30.setBackColor(SystemColors.Control);
        picTrib30.setLocation(new java.awt.Point(16, 136));
        picTrib30.setName("picTrib30");
        picTrib30.setSize(new jwinforms.Size(12, 12));
        picTrib30.setTabIndex(46);
        picTrib30.setTabStop(false);
        picTrib30.setVisible(false);
        picTrib30.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib04
        //
        picTrib04.setBackColor(SystemColors.Control);
        picTrib04.setLocation(new java.awt.Point(176, 8));
        picTrib04.setName("picTrib04");
        picTrib04.setSize(new jwinforms.Size(12, 12));
        picTrib04.setTabIndex(47);
        picTrib04.setTabStop(false);
        picTrib04.setVisible(false);
        picTrib04.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib03
        //
        picTrib03.setBackColor(SystemColors.Control);
        picTrib03.setLocation(new java.awt.Point(128, 8));
        picTrib03.setName("picTrib03");
        picTrib03.setSize(new jwinforms.Size(12, 12));
        picTrib03.setTabIndex(48);
        picTrib03.setTabStop(false);
        picTrib03.setVisible(false);
        picTrib03.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib02
        //
        picTrib02.setBackColor(SystemColors.Control);
        picTrib02.setLocation(new java.awt.Point(96, 16));
        picTrib02.setName("picTrib02");
        picTrib02.setSize(new jwinforms.Size(12, 12));
        picTrib02.setTabIndex(49);
        picTrib02.setTabStop(false);
        picTrib02.setVisible(false);
        picTrib02.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib01
        //
        picTrib01.setBackColor(SystemColors.Control);
        picTrib01.setLocation(new java.awt.Point(56, 8));
        picTrib01.setName("picTrib01");
        picTrib01.setSize(new jwinforms.Size(12, 12));
        picTrib01.setTabIndex(50);
        picTrib01.setTabStop(false);
        picTrib01.setVisible(false);
        picTrib01.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib05
        //
        picTrib05.setBackColor(SystemColors.Control);
        picTrib05.setLocation(new java.awt.Point(208, 16));
        picTrib05.setName("picTrib05");
        picTrib05.setSize(new jwinforms.Size(12, 12));
        picTrib05.setTabIndex(51);
        picTrib05.setTabStop(false);
        picTrib05.setVisible(false);
        picTrib05.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib11
        //
        picTrib11.setBackColor(SystemColors.Control);
        picTrib11.setLocation(new java.awt.Point(32, 80));
        picTrib11.setName("picTrib11");
        picTrib11.setSize(new jwinforms.Size(12, 12));
        picTrib11.setTabIndex(52);
        picTrib11.setTabStop(false);
        picTrib11.setVisible(false);
        picTrib11.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib12
        //
        picTrib12.setBackColor(SystemColors.Control);
        picTrib12.setLocation(new java.awt.Point(88, 56));
        picTrib12.setName("picTrib12");
        picTrib12.setSize(new jwinforms.Size(12, 12));
        picTrib12.setTabIndex(53);
        picTrib12.setTabStop(false);
        picTrib12.setVisible(false);
        picTrib12.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib13
        //
        picTrib13.setBackColor(SystemColors.Control);
        picTrib13.setLocation(new java.awt.Point(128, 40));
        picTrib13.setName("picTrib13");
        picTrib13.setSize(new jwinforms.Size(12, 12));
        picTrib13.setTabIndex(54);
        picTrib13.setTabStop(false);
        picTrib13.setVisible(false);
        picTrib13.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib14
        //
        picTrib14.setBackColor(SystemColors.Control);
        picTrib14.setLocation(new java.awt.Point(192, 72));
        picTrib14.setName("picTrib14");
        picTrib14.setSize(new jwinforms.Size(12, 12));
        picTrib14.setTabIndex(55);
        picTrib14.setTabStop(false);
        picTrib14.setVisible(false);
        picTrib14.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib15
        //
        picTrib15.setBackColor(SystemColors.Control);
        picTrib15.setLocation(new java.awt.Point(216, 48));
        picTrib15.setName("picTrib15");
        picTrib15.setSize(new jwinforms.Size(12, 12));
        picTrib15.setTabIndex(56);
        picTrib15.setTabStop(false);
        picTrib15.setVisible(false);
        picTrib15.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib21
        //
        picTrib21.setBackColor(SystemColors.Control);
        picTrib21.setLocation(new java.awt.Point(56, 96));
        picTrib21.setName("picTrib21");
        picTrib21.setSize(new jwinforms.Size(12, 12));
        picTrib21.setTabIndex(57);
        picTrib21.setTabStop(false);
        picTrib21.setVisible(false);
        picTrib21.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib22
        //
        picTrib22.setBackColor(SystemColors.Control);
        picTrib22.setLocation(new java.awt.Point(96, 80));
        picTrib22.setName("picTrib22");
        picTrib22.setSize(new jwinforms.Size(12, 12));
        picTrib22.setTabIndex(58);
        picTrib22.setTabStop(false);
        picTrib22.setVisible(false);
        picTrib22.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib23
        //
        picTrib23.setBackColor(SystemColors.Control);
        picTrib23.setLocation(new java.awt.Point(136, 88));
        picTrib23.setName("picTrib23");
        picTrib23.setSize(new jwinforms.Size(12, 12));
        picTrib23.setTabIndex(59);
        picTrib23.setTabStop(false);
        picTrib23.setVisible(false);
        picTrib23.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib24
        //
        picTrib24.setBackColor(SystemColors.Control);
        picTrib24.setLocation(new java.awt.Point(176, 104));
        picTrib24.setName("picTrib24");
        picTrib24.setSize(new jwinforms.Size(12, 12));
        picTrib24.setTabIndex(60);
        picTrib24.setTabStop(false);
        picTrib24.setVisible(false);
        picTrib24.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib25
        //
        picTrib25.setBackColor(SystemColors.Control);
        picTrib25.setLocation(new java.awt.Point(216, 96));
        picTrib25.setName("picTrib25");
        picTrib25.setSize(new jwinforms.Size(12, 12));
        picTrib25.setTabIndex(61);
        picTrib25.setTabStop(false);
        picTrib25.setVisible(false);
        picTrib25.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib31
        //
        picTrib31.setBackColor(SystemColors.Control);
        picTrib31.setLocation(new java.awt.Point(56, 128));
        picTrib31.setName("picTrib31");
        picTrib31.setSize(new jwinforms.Size(12, 12));
        picTrib31.setTabIndex(62);
        picTrib31.setTabStop(false);
        picTrib31.setVisible(false);
        picTrib31.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib32
        //
        picTrib32.setBackColor(SystemColors.Control);
        picTrib32.setLocation(new java.awt.Point(96, 120));
        picTrib32.setName("picTrib32");
        picTrib32.setSize(new jwinforms.Size(12, 12));
        picTrib32.setTabIndex(63);
        picTrib32.setTabStop(false);
        picTrib32.setVisible(false);
        picTrib32.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib33
        //
        picTrib33.setBackColor(SystemColors.Control);
        picTrib33.setLocation(new java.awt.Point(128, 128));
        picTrib33.setName("picTrib33");
        picTrib33.setSize(new jwinforms.Size(12, 12));
        picTrib33.setTabIndex(64);
        picTrib33.setTabStop(false);
        picTrib33.setVisible(false);
        picTrib33.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib34
        //
        picTrib34.setBackColor(SystemColors.Control);
        picTrib34.setLocation(new java.awt.Point(168, 144));
        picTrib34.setName("picTrib34");
        picTrib34.setSize(new jwinforms.Size(12, 12));
        picTrib34.setTabIndex(65);
        picTrib34.setTabStop(false);
        picTrib34.setVisible(false);
        picTrib34.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib35
        //
        picTrib35.setBackColor(SystemColors.Control);
        picTrib35.setLocation(new java.awt.Point(208, 128));
        picTrib35.setName("picTrib35");
        picTrib35.setSize(new jwinforms.Size(12, 12));
        picTrib35.setTabIndex(66);
        picTrib35.setTabStop(false);
        picTrib35.setVisible(false);
        picTrib35.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib41
        //
        picTrib41.setBackColor(SystemColors.Control);
        picTrib41.setLocation(new java.awt.Point(48, 176));
        picTrib41.setName("picTrib41");
        picTrib41.setSize(new jwinforms.Size(12, 12));
        picTrib41.setTabIndex(67);
        picTrib41.setTabStop(false);
        picTrib41.setVisible(false);
        picTrib41.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib51
        //
        picTrib51.setBackColor(SystemColors.Control);
        picTrib51.setLocation(new java.awt.Point(64, 216));
        picTrib51.setName("picTrib51");
        picTrib51.setSize(new jwinforms.Size(12, 12));
        picTrib51.setTabIndex(68);
        picTrib51.setTabStop(false);
        picTrib51.setVisible(false);
        picTrib51.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib42
        //
        picTrib42.setBackColor(SystemColors.Control);
        picTrib42.setLocation(new java.awt.Point(88, 168));
        picTrib42.setName("picTrib42");
        picTrib42.setSize(new jwinforms.Size(12, 12));
        picTrib42.setTabIndex(69);
        picTrib42.setTabStop(false);
        picTrib42.setVisible(false);
        picTrib42.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib52
        //
        picTrib52.setBackColor(SystemColors.Control);
        picTrib52.setLocation(new java.awt.Point(96, 224));
        picTrib52.setName("picTrib52");
        picTrib52.setSize(new jwinforms.Size(12, 12));
        picTrib52.setTabIndex(70);
        picTrib52.setTabStop(false);
        picTrib52.setVisible(false);
        picTrib52.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib43
        //
        picTrib43.setBackColor(SystemColors.Control);
        picTrib43.setLocation(new java.awt.Point(136, 176));
        picTrib43.setName("picTrib43");
        picTrib43.setSize(new jwinforms.Size(12, 12));
        picTrib43.setTabIndex(71);
        picTrib43.setTabStop(false);
        picTrib43.setVisible(false);
        picTrib43.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib53
        //
        picTrib53.setBackColor(SystemColors.Control);
        picTrib53.setLocation(new java.awt.Point(144, 216));
        picTrib53.setName("picTrib53");
        picTrib53.setSize(new jwinforms.Size(12, 12));
        picTrib53.setTabIndex(72);
        picTrib53.setTabStop(false);
        picTrib53.setVisible(false);
        picTrib53.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib44
        //
        picTrib44.setBackColor(SystemColors.Control);
        picTrib44.setLocation(new java.awt.Point(184, 184));
        picTrib44.setName("picTrib44");
        picTrib44.setSize(new jwinforms.Size(12, 12));
        picTrib44.setTabIndex(73);
        picTrib44.setTabStop(false);
        picTrib44.setVisible(false);
        picTrib44.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib45
        //
        picTrib45.setBackColor(SystemColors.Control);
        picTrib45.setLocation(new java.awt.Point(216, 176));
        picTrib45.setName("picTrib45");
        picTrib45.setSize(new jwinforms.Size(12, 12));
        picTrib45.setTabIndex(74);
        picTrib45.setTabStop(false);
        picTrib45.setVisible(false);
        picTrib45.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib54
        //
        picTrib54.setBackColor(SystemColors.Control);
        picTrib54.setLocation(new java.awt.Point(176, 224));
        picTrib54.setName("picTrib54");
        picTrib54.setSize(new jwinforms.Size(12, 12));
        picTrib54.setTabIndex(75);
        picTrib54.setTabStop(false);
        picTrib54.setVisible(false);
        picTrib54.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // picTrib55
        //
        picTrib55.setBackColor(SystemColors.Control);
        picTrib55.setLocation(new java.awt.Point(208, 216));
        picTrib55.setName("picTrib55");
        picTrib55.setSize(new jwinforms.Size(12, 12));
        picTrib55.setTabIndex(76);
        picTrib55.setTabStop(false);
        picTrib55.setVisible(false);
        picTrib55.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                picTrib_Click(sender, e);
            }
        });
        //
        // tmrTick
        //
        tmrTick.setInterval(1000);
        tmrTick.Tick = new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                tmrTick_Tick(sender, e);
            }
        };
        //
        // FormEncounter
        //
        this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setClientSize(new jwinforms.Size(234, 271));
        this.setControlBox(false);
        Controls.add(picTrib55);
        Controls.add(picTrib54);
        Controls.add(picTrib45);
        Controls.add(picTrib44);
        Controls.add(picTrib53);
        Controls.add(picTrib43);
        Controls.add(picTrib52);
        Controls.add(picTrib42);
        Controls.add(picTrib51);
        Controls.add(picTrib41);
        Controls.add(picTrib35);
        Controls.add(picTrib34);
        Controls.add(picTrib33);
        Controls.add(picTrib32);
        Controls.add(picTrib31);
        Controls.add(picTrib25);
        Controls.add(picTrib24);
        Controls.add(picTrib23);
        Controls.add(picTrib22);
        Controls.add(picTrib21);
        Controls.add(picTrib15);
        Controls.add(picTrib14);
        Controls.add(picTrib13);
        Controls.add(picTrib12);
        Controls.add(picTrib11);
        Controls.add(picTrib05);
        Controls.add(picTrib01);
        Controls.add(picTrib02);
        Controls.add(picTrib03);
        Controls.add(picTrib04);
        Controls.add(picTrib30);
        Controls.add(picTrib20);
        Controls.add(picTrib40);
        Controls.add(picTrib10);
        Controls.add(picTrib50);
        Controls.add(picTrib00);
        Controls.add(picEncounterType);
        Controls.add(picContinuous);
        Controls.add(btnYield);
        Controls.add(btnInt);
        Controls.add(btnMeet);
        Controls.add(btnPlunder);
        Controls.add(btnTrade);
        Controls.add(btnIgnore);
        Controls.add(btnSurrender);
        Controls.add(btnBribe);
        Controls.add(btnSubmit);
        Controls.add(btnFlee);
        Controls.add(lblOpponentShields);
        Controls.add(lblOpponentHull);
        Controls.add(lblYouShields);
        Controls.add(lblYouHull);
        Controls.add(lblYouShip);
        Controls.add(lblOpponentShip);
        Controls.add(lblYouLabel);
        Controls.add(lblOpponentLabel);
        Controls.add(lblAction);
        Controls.add(picShipOpponent);
        Controls.add(picShipYou);
        Controls.add(lblEncounter);
        Controls.add(btnDrink);
        Controls.add(btnBoard);
        Controls.add(btnAttack);
        this.setFormBorderStyle(jwinforms.FormBorderStyle.FixedDialog);
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
            Close();
    }

    private void Exit(EncounterResult result) {
        _result = result;
        Close();
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
            if (visible[i] != buttons[i].getVisible()) {
                buttons[i].setVisible(visible[i]);
                if (i == INT)
                    picContinuous.setVisible(visible[i]);
            }
        }

        if (picContinuous.getVisible())
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

        picShipYou.Refresh();
        picShipOpponent.Refresh();
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
            while (tribbles[index].getVisible())
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
            Close();
    }

    private void btnTrade_Click(Object sender, EventArgs e) {
        game.EncounterTrade();

        Exit(EncounterResult.Normal);
    }

    private void btnYield_Click(Object sender, EventArgs e) {
        if ((_result = game.EncounterVerifyYield()) != EncounterResult.Continue)
            Close();
    }

    private void picShipOpponent_Paint(Object sender,
                                       jwinforms.PaintEventArgs e) {
        Functions.PaintShipImage(opponent, e.Graphics,
                picShipOpponent.getBackColor());
    }

    private void picShipYou_Paint(Object sender,
                                  jwinforms.PaintEventArgs e) {
        Functions.PaintShipImage(cmdrship, e.Graphics, picShipYou.getBackColor());
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
