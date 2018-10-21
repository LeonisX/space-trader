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
import spacetrader.controls.Container;
import spacetrader.controls.Label;
import spacetrader.game.Consts;
import spacetrader.game.Functions;
import spacetrader.game.Game;
import spacetrader.game.Ship;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.EncounterResult;
import spacetrader.guifacade.Facaded;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.Arrays;

import static java.lang.Math.*;

@Facaded
public class FormEncounter extends SpaceTraderForm {

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
    private final Ship commanderShip = Game.getCurrentGame().getCommander().getShip();
    private final Ship opponent = Game.getCurrentGame().getOpponent();

    private Button attackButton = new Button();
    private Button fleeButton = new Button();
    private Button submitButton = new Button();
    private Button bribeButton = new Button();
    private Button surrenderButton = new Button();
    private Button ignoreButton = new Button();
    private Button tradeButton = new Button();
    private Button plunderButton = new Button();
    private Button boardButton = new Button();
    private Button meetButton = new Button();
    private Button drinkButton = new Button();
    private Button interruptButton = new Button();
    private Button yieldButton = new Button();

    private Label encounterLabelValue = new Label();
    private PictureBox yourShipPicture = new PictureBox();
    private PictureBox opponentsShipPicture = new PictureBox();
    private Label opponentLabel = new Label();
    private Label youLabel = new Label();
    private Label opponentsShipLabelValue = new Label();
    private Label yourShipLabelValue = new Label();
    private Label yourHullLabelValue = new Label();
    private Label yourShieldsLabelValue = new Label();
    private Label opponentsShieldsLabelValue = new Label();
    private Label opponentsHullLabelValue = new Label();
    private Label actionLabelValue = new Label();

    //TODO need?
    private IContainer components = new Container();
    
    private PictureBox continuousPicture = new PictureBox();
    private ImageList continuousImageList = new ImageList(components);
    private PictureBox encounterTypePicture = new PictureBox();
    private ImageList encounterTypeImageList = new ImageList(components);
    private ImageList tribblesImageList = new ImageList(components);

    //TODO refactor, optimize, generate on fly.
    //TODO clean-up configs
    private PictureBox tribblePicture00 = new PictureBox();
    private PictureBox tribblePicture50 = new PictureBox();
    private PictureBox tribblePicture10 = new PictureBox();
    private PictureBox tribblePicture40 = new PictureBox();
    private PictureBox tribblePicture20 = new PictureBox();
    private PictureBox tribblePicture30 = new PictureBox();
    private PictureBox tribblePicture04 = new PictureBox();
    private PictureBox tribblePicture03 = new PictureBox();
    private PictureBox tribblePicture02 = new PictureBox();
    private PictureBox tribblePicture01 = new PictureBox();
    private PictureBox tribblePicture05 = new PictureBox();
    private PictureBox tribblePicture11 = new PictureBox();
    private PictureBox tribblePicture12 = new PictureBox();
    private PictureBox tribblePicture13 = new PictureBox();
    private PictureBox tribblePicture14 = new PictureBox();
    private PictureBox tribblePicture15 = new PictureBox();
    private PictureBox tribblePicture21 = new PictureBox();
    private PictureBox tribblePicture22 = new PictureBox();
    private PictureBox tribblePicture23 = new PictureBox();
    private PictureBox tribblePicture24 = new PictureBox();
    private PictureBox tribblePicture25 = new PictureBox();
    private PictureBox tribblePicture31 = new PictureBox();
    private PictureBox tribblePicture32 = new PictureBox();
    private PictureBox tribblePicture33 = new PictureBox();
    private PictureBox tribblePicture34 = new PictureBox();
    private PictureBox tribblePicture35 = new PictureBox();
    private PictureBox tribblePicture41 = new PictureBox();
    private PictureBox tribblePicture51 = new PictureBox();
    private PictureBox tribblePicture42 = new PictureBox();
    private PictureBox tribblePicture52 = new PictureBox();
    private PictureBox tribblePicture43 = new PictureBox();
    private PictureBox tribblePicture53 = new PictureBox();
    private PictureBox tribblePicture44 = new PictureBox();
    private PictureBox tribblePicture45 = new PictureBox();
    private PictureBox tribblePicture54 = new PictureBox();
    private PictureBox tribblePicture55 = new PictureBox();

    private PictureBox[] tribblesArray;

    private Timer timer = new Timer(components);
    
    private int continueImage = 1;

    private EncounterResult _result = EncounterResult.CONTINUE;

    public FormEncounter() {
        initializeComponent();

        // Set up the Game encounter variables.
        game.encounterBegin();

        // Enable the control box (the X button) if cheats are enabled.
        if (game.isEasyEncounters()) {
            setControlBox(true);
        }

        buttons = new Button[]{attackButton, boardButton, bribeButton, drinkButton, fleeButton, ignoreButton, interruptButton, meetButton,
                plunderButton, submitButton, surrenderButton, tradeButton, yieldButton};

        updateShipInfo();
        updateTribbles();
        updateButtons();

        if (game.getEncounterImageIndex() >= 0) {
            encounterTypePicture.setImage(encounterTypeImageList.getImages()[game.getEncounterImageIndex()]);
        } else {
            encounterTypePicture.setVisible(false);
        }

        encounterLabelValue.setText(game.getEncounterTextInitial());
        actionLabelValue.setText(game.getEncounterActionInitial());
    }

    private void initializeComponent() {
        ResourceManager resources = new ResourceManager(FormEncounter.class);
        ReflectionUtils.setAllComponentNames(this);
        suspendLayout();

        setName("formEncounter");
        setText("Encounter");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setControlBox(false);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(new Size(5, 13));
        setClientSize(new Size(234, 271));

        encounterTypePicture.setLocation(new Point(220, 2));
        encounterTypePicture.setSize(new Size(12, 12));
        encounterTypePicture.setTabStop(false);

        youLabel.setAutoSize(true);
        youLabel.setFont(FontCollection.bold825);
        youLabel.setLocation(new Point(45, 8));
        youLabel.setSize(new Size(28, 16));
        youLabel.setText("You:");

        opponentLabel.setAutoSize(true);
        opponentLabel.setFont(FontCollection.bold825);
        opponentLabel.setLocation(new Point(141, 8));
        opponentLabel.setSize(new Size(59, 16));
        opponentLabel.setText("Opponent:");

        yourShipPicture.setBackground(Color.WHITE);
        yourShipPicture.setBorderStyle(BorderStyle.FIXED_SINGLE);
        yourShipPicture.setLocation(new Point(26, 24));
        yourShipPicture.setSize(new Size(70, 58));
        yourShipPicture.setTabStop(false);
        yourShipPicture.setPaint(new EventHandler<Object, PaintEventArgs>() {
            @Override
            public void handle(Object sender, PaintEventArgs e) {
                yourShipPicturePaint(e);
            }
        });

        opponentsShipPicture.setBackground(Color.WHITE);
        opponentsShipPicture.setBorderStyle(BorderStyle.FIXED_SINGLE);
        opponentsShipPicture.setLocation(new Point(138, 24));
        opponentsShipPicture.setSize(new Size(70, 58));
        opponentsShipPicture.setTabStop(false);
        opponentsShipPicture.setPaint(new EventHandler<Object, PaintEventArgs>() {
            @Override
            public void handle(Object sender, PaintEventArgs e) {
                opponentsShipPicturePaint(e);
            }
        });

        yourShipLabelValue.setLocation(new Point(26, 88));
        yourShipLabelValue.setSize(new Size(100, 13));
        yourShipLabelValue.setTabIndex(19);
        //yourShipLabelValue.setText("Grasshopper");

        opponentsShipLabelValue.setLocation(new Point(138, 88));
        opponentsShipLabelValue.setSize(new Size(80, 13));
        opponentsShipLabelValue.setTabIndex(18);
        //opponentsShipLabelValue.setText("Space Monster");

        yourHullLabelValue.setLocation(new Point(26, 104));
        yourHullLabelValue.setSize(new Size(68, 13));
        yourHullLabelValue.setTabIndex(20);
        //yourHullLabelValue.setText("Hull at 100%");

        opponentsHullLabelValue.setLocation(new Point(138, 104));
        opponentsHullLabelValue.setSize(new Size(68, 13));
        opponentsHullLabelValue.setTabIndex(22);
        //opponentsHullLabelValue.setText("Hull at 100%");

        yourShieldsLabelValue.setLocation(new Point(26, 120));
        yourShieldsLabelValue.setSize(new Size(86, 13));
        yourShieldsLabelValue.setTabIndex(21);
        //yourShieldsLabelValue.setText("Shields at 100%");

        opponentsShieldsLabelValue.setLocation(new Point(138, 120));
        opponentsShieldsLabelValue.setSize(new Size(86, 13));
        opponentsShieldsLabelValue.setTabIndex(23);
        //opponentsShieldsLabelValue.setText("Shields at 100%");

        encounterLabelValue.setLocation(new Point(8, 152));
        encounterLabelValue.setSize(new Size(232, 26));
        encounterLabelValue.setTabIndex(0);
        //encounterLabelValue.setText("At 20 clicks from Tarchannen, you encounter the famous Captain Ahab.");

        actionLabelValue.setLocation(new Point(8, 192));
        actionLabelValue.setSize(new Size(232, 39));
        actionLabelValue.setTabIndex(15);
        //actionLabelValue.setText("\"We know you removed illegal goods from the Marie Celeste. You must give them up at once!\"");

        attackButton.setFlatStyle(FlatStyle.FLAT);
        attackButton.setLocation(new Point(8, 240));
        attackButton.setSize(new Size(46, 22));
        attackButton.setTabIndex(24);
        attackButton.setText("Attack");
        attackButton.setVisible(false);
        attackButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                attackButtonClick();
            }
        });

        fleeButton.setFlatStyle(FlatStyle.FLAT);
        fleeButton.setLocation(new Point(62, 240));
        fleeButton.setSize(new Size(36, 22));
        fleeButton.setTabIndex(25);
        fleeButton.setText("Flee");
        fleeButton.setVisible(false);
        fleeButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                fleeButtonClick();
            }
        });

        submitButton.setFlatStyle(FlatStyle.FLAT);
        submitButton.setLocation(new Point(106, 240));
        submitButton.setSize(new Size(49, 22));
        submitButton.setTabIndex(26);
        submitButton.setText("Submit");
        submitButton.setVisible(false);
        submitButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                submitButtonClick();
            }
        });

        bribeButton.setFlatStyle(FlatStyle.FLAT);
        bribeButton.setLocation(new Point(163, 240));
        bribeButton.setSize(new Size(41, 22));
        bribeButton.setTabIndex(27);
        bribeButton.setText("Bribe");
        bribeButton.setVisible(false);
        bribeButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                bribeButtonClick();
            }
        });

        surrenderButton.setFlatStyle(FlatStyle.FLAT);
        surrenderButton.setLocation(new Point(106, 240));
        surrenderButton.setSize(new Size(65, 22));
        surrenderButton.setTabIndex(28);
        surrenderButton.setText("Surrender");
        surrenderButton.setVisible(false);
        surrenderButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                surrenderButtonClick();
            }
        });

        ignoreButton.setFlatStyle(FlatStyle.FLAT);
        ignoreButton.setLocation(new Point(62, 240));
        ignoreButton.setSize(new Size(46, 22));
        ignoreButton.setTabIndex(29);
        ignoreButton.setText("Ignore");
        ignoreButton.setVisible(false);
        ignoreButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                ignoreButtonClick();
            }
        });

        tradeButton.setFlatStyle(FlatStyle.FLAT);
        tradeButton.setLocation(new Point(116, 240));
        tradeButton.setSize(new Size(44, 22));
        tradeButton.setTabIndex(30);
        tradeButton.setText("Trade");
        tradeButton.setVisible(false);
        tradeButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                tradeButtonClick();
            }
        });

        plunderButton.setFlatStyle(FlatStyle.FLAT);
        plunderButton.setLocation(new Point(62, 240));
        plunderButton.setSize(new Size(53, 22));
        plunderButton.setTabIndex(31);
        plunderButton.setText("Plunder");
        plunderButton.setVisible(false);
        plunderButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                plunderButtonClick();
            }
        });

        boardButton.setFlatStyle(FlatStyle.FLAT);
        boardButton.setLocation(new Point(8, 240));
        boardButton.setSize(new Size(44, 22));
        boardButton.setTabIndex(32);
        boardButton.setText("Board");
        boardButton.setVisible(false);
        boardButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                boardButtonClick();
            }
        });

        meetButton.setFlatStyle(FlatStyle.FLAT);
        meetButton.setLocation(new Point(116, 240));
        meetButton.setSize(new Size(39, 22));
        meetButton.setTabIndex(34);
        meetButton.setText("Meet");
        meetButton.setVisible(false);
        meetButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                meetButtonClick();
            }
        });

        drinkButton.setFlatStyle(FlatStyle.FLAT);
        drinkButton.setLocation(new Point(8, 240));
        drinkButton.setSize(new Size(41, 22));
        drinkButton.setTabIndex(35);
        drinkButton.setText("Drink");
        drinkButton.setVisible(false);
        drinkButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                drinkButtonClick();
            }
        });

        interruptButton.setFlatStyle(FlatStyle.FLAT);
        interruptButton.setLocation(new Point(179, 240));
        interruptButton.setSize(new Size(30, 22));
        interruptButton.setTabIndex(36);
        interruptButton.setText("Int.");
        interruptButton.setVisible(false);
        interruptButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                interruptButtonClick();
            }
        });

        yieldButton.setFlatStyle(FlatStyle.FLAT);
        yieldButton.setLocation(new Point(106, 240));
        yieldButton.setSize(new Size(39, 22));
        yieldButton.setTabIndex(37);
        yieldButton.setText("Yield");
        yieldButton.setVisible(false);
        yieldButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                yieldButtonClick();
            }
        });

        continuousPicture.setLocation(new Point(214, 247));
        continuousPicture.setSize(new Size(9, 9));
        continuousPicture.setTabIndex(38);
        continuousPicture.setTabStop(false);
        continuousPicture.setVisible(false);

        tribblePicture00.setLocation(new Point(16, 16));
        tribblePicture01.setLocation(new Point(56, 8));
        tribblePicture02.setLocation(new Point(96, 16));
        tribblePicture03.setLocation(new Point(128, 8));
        tribblePicture04.setLocation(new Point(176, 8));
        tribblePicture05.setLocation(new Point(208, 16));
        tribblePicture10.setLocation(new Point(8, 56));
        tribblePicture11.setLocation(new Point(32, 80));
        tribblePicture12.setLocation(new Point(88, 56));
        tribblePicture13.setLocation(new Point(128, 40));
        tribblePicture14.setLocation(new Point(192, 72));
        tribblePicture15.setLocation(new Point(216, 48));
        tribblePicture20.setLocation(new Point(8, 96));
        tribblePicture21.setLocation(new Point(56, 96));
        tribblePicture22.setLocation(new Point(96, 80));
        tribblePicture23.setLocation(new Point(136, 88));
        tribblePicture24.setLocation(new Point(176, 104));
        tribblePicture25.setLocation(new Point(216, 96));
        tribblePicture30.setLocation(new Point(16, 136));
        tribblePicture31.setLocation(new Point(56, 128));
        tribblePicture32.setLocation(new Point(96, 120));
        tribblePicture33.setLocation(new Point(128, 128));
        tribblePicture34.setLocation(new Point(168, 144));
        tribblePicture35.setLocation(new Point(208, 128));
        tribblePicture40.setLocation(new Point(8, 184));
        tribblePicture41.setLocation(new Point(48, 176));
        tribblePicture42.setLocation(new Point(88, 168));
        tribblePicture43.setLocation(new Point(136, 176));
        tribblePicture44.setLocation(new Point(184, 184));
        tribblePicture45.setLocation(new Point(216, 176));
        tribblePicture50.setLocation(new Point(16, 224));
        tribblePicture51.setLocation(new Point(64, 216));
        tribblePicture52.setLocation(new Point(96, 224));
        tribblePicture53.setLocation(new Point(144, 216));
        tribblePicture54.setLocation(new Point(176, 224));
        tribblePicture55.setLocation(new Point(208, 216));

        tribblesArray = new PictureBox[]{
                tribblePicture00, tribblePicture01, tribblePicture02, tribblePicture03, tribblePicture04, tribblePicture05, 
                tribblePicture10, tribblePicture11, tribblePicture12, tribblePicture13, tribblePicture14, tribblePicture15,
                tribblePicture20, tribblePicture21, tribblePicture22, tribblePicture23, tribblePicture24, tribblePicture25, 
                tribblePicture30, tribblePicture31, tribblePicture32, tribblePicture33, tribblePicture34, tribblePicture35, 
                tribblePicture40, tribblePicture41, tribblePicture42, tribblePicture43, tribblePicture44, tribblePicture45, 
                tribblePicture50, tribblePicture51, tribblePicture52, tribblePicture53, tribblePicture54, tribblePicture55
        };

        Arrays.stream(tribblesArray).forEach(picture -> {
            picture.setBackground(SystemColors.CONTROL);
            picture.setSize(new Size(12, 12));
            picture.setTabStop(false);
            picture.setVisible(false);
            picture.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    tribblePictureClick();
                }
            });
        });

        timer.setInterval(1000);
        timer.setTick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                timerTick();
            }
        });

        encounterTypeImageList.setImageSize(new Size(12, 12));
        encounterTypeImageList.setImageStream(((ImageListStreamer) (resources.getObject("ilEncounterType.ImageStream"))));
        encounterTypeImageList.setTransparentColor(Color.WHITE);

        tribblesImageList.setImageSize(new Size(12, 12));
        tribblesImageList.setImageStream(((ImageListStreamer) (resources.getObject("ilTribbles.ImageStream"))));
        tribblesImageList.setTransparentColor(Color.WHITE);

        continuousImageList.setImageSize(new Size(9, 9));
        continuousImageList.setImageStream(((ImageListStreamer) (resources.getObject("ilContinuous.ImageStream"))));
        continuousImageList.setTransparentColor(Color.WHITE);

        controls.add(encounterTypePicture);
        controls.add(youLabel);
        controls.add(opponentLabel);
        controls.add(yourShipPicture);
        controls.add(opponentsShipPicture);
        controls.add(yourShipLabelValue);
        controls.add(opponentsShipLabelValue);
        controls.add(yourHullLabelValue);
        controls.add(opponentsHullLabelValue);
        controls.add(yourShieldsLabelValue);
        controls.add(opponentsShieldsLabelValue);
        controls.add(encounterLabelValue);
        controls.add(actionLabelValue);
        controls.add(attackButton);
        controls.add(fleeButton);
        controls.add(submitButton);
        controls.add(bribeButton);
        controls.add(surrenderButton);
        controls.add(ignoreButton);
        controls.add(tradeButton);
        controls.add(plunderButton);
        controls.add(boardButton);
        controls.add(meetButton);
        controls.add(drinkButton);
        controls.add(interruptButton);
        controls.add(yieldButton);
        controls.add(continuousPicture);
        controls.addAll(tribblesArray);
    }

    private void disableAuto() {
        timer.stop();

        game.setEncounterContinueFleeing(false);
        game.setEncounterContinueAttacking(false);
        interruptButton.setVisible(false);
        continuousPicture.setVisible(false);
    }

    private void executeAction() {
        if ((_result = game.getEncounterExecuteAction()) == EncounterResult.CONTINUE) {
            updateButtons();
            updateShipStats();

            encounterLabelValue.setText(game.getEncounterText());
            actionLabelValue.setText(game.getEncounterAction());

            if (game.getEncounterContinueFleeing() || game.getEncounterContinueAttacking()) {
                timer.start();
            }
        } else {
            close();
        }
    }

    private void exit(EncounterResult result) {
        _result = result;
        close();
    }

    private void updateButtons() {
        boolean[] visible = new boolean[buttons.length];

        switch (game.getEncounterType()) {
            case BOTTLE_GOOD:
            case BOTTLE_OLD:
                visible[DRINK] = true;
                visible[IGNORE] = true;
                //TODO scale
                ignoreButton.setLeft(drinkButton.getLeft() + drinkButton.getWidth() + 8);
                break;
            case CAPTAIN_AHAB:
            case CAPTAIN_CONRAD:
            case CAPTAIN_HUIE:
                visible[ATTACK] = true;
                visible[IGNORE] = true;
                visible[MEET] = true;
                break;
            case DRAGONFLY_ATTACK:
            case FAMOUS_CAPTAIN_ATTACK:
            case SCORPION_ATTACK:
            case SPACE_MONSTER_ATTACK:
            case TRADER_ATTACK:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                interruptButton.setLeft(fleeButton.getLeft() + fleeButton.getWidth() + 8);
                break;
            case DRAGONFLY_IGNORE:
            case FAMOUS_CAPT_DISABLED:
            case POLICE_DISABLED:
            case POLICE_FLEE:
            case POLICE_IGNORE:
            case PIRATE_FLEE:
            case PIRATE_IGNORE:
            case SCARAB_IGNORE:
            case SCORPION_IGNORE:
            case SPACE_MONSTER_IGNORE:
            case TRADER_FLEE:
            case TRADER_IGNORE:
                visible[ATTACK] = true;
                visible[IGNORE] = true;
                break;
            case MARIE_CELESTE:
                visible[BOARD] = true;
                visible[IGNORE] = true;
                ignoreButton.setLeft(boardButton.getLeft() + boardButton.getWidth() + 8);
                break;
            case MARIE_CELESTE_POLICE:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                visible[YIELD] = true;
                visible[BRIBE] = true;
                bribeButton.setLeft(yieldButton.getLeft() + yieldButton.getWidth() + 8);
                break;
            case PIRATE_ATTACK:
            case POLICE_ATTACK:
            case POLICE_SURRENDER:
            case SCARAB_ATTACK:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                visible[SURRENDER] = true;
                interruptButton.setLeft(surrenderButton.getLeft() + surrenderButton.getWidth() + 8);
                break;
            case PIRATE_DISABLED:
            case PIRATE_SURRENDER:
            case TRADER_DISABLED:
            case TRADER_SURRENDER:
                visible[ATTACK] = true;
                visible[PLUNDER] = true;
                break;
            case POLICE_INSPECT:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                visible[SUBMIT] = true;
                visible[BRIBE] = true;
                break;
            case TRADER_BUY:
            case TRADER_SELL:
                visible[ATTACK] = true;
                visible[IGNORE] = true;
                visible[TRADE] = true;
                break;
        }

        if (game.getEncounterContinueAttacking() || game.getEncounterContinueFleeing()) {
            visible[INT] = true;
        }

        for (int i = 0; i < visible.length; i++) {
            if (visible[i] != buttons[i].isVisible()) {
                buttons[i].setVisible(visible[i]);
            }
        }

        continuousPicture.setVisible(visible[INT]);

        if (continuousPicture.isVisible()) {
            continuousPicture.setImage(continuousImageList.getImages()[continueImage = (continueImage + 1) % 2]);
        }
    }

    private void updateShipInfo() {
        yourShipLabelValue.setText(commanderShip.getName());
        opponentsShipLabelValue.setText(opponent.getName());

        updateShipStats();
    }

    private void updateShipStats() {
        yourHullLabelValue.setText(commanderShip.getHullText());
        yourShieldsLabelValue.setText(commanderShip.getShieldText());
        opponentsHullLabelValue.setText(opponent.getHullText());
        opponentsShieldsLabelValue.setText(opponent.getShieldText());

        yourShipPicture.refresh();
        opponentsShipPicture.refresh();
    }

    private void updateTribbles() {
        int toShow = min(tribblesArray.length,
                (int) sqrt(commanderShip.getTribbles() / ceil(Consts.MaxTribbles / pow(tribblesArray.length + 1, 2))));

        for (int i = 0; i < toShow; i++) {
            int index = Functions.getRandom(tribblesArray.length);
            while (tribblesArray[index].isVisible()) {
                index = (index + 1) % tribblesArray.length;
            }
            tribblesArray[index].setImage(tribblesImageList.getImages()[Functions.getRandom(tribblesImageList.getImages().length)]);
            tribblesArray[index].setVisible(true);
        }
    }

    private void attackButtonClick() {
        disableAuto();

        if (game.isEncounterVerifyAttack()) {
            executeAction();
        }
    }

    private void boardButtonClick() {
        if (game.isEncounterVerifyBoard()) {
            exit(EncounterResult.NORMAL);
        }
    }

    private void bribeButtonClick() {
        if (game.isEncounterVerifyBribe()) {
            exit(EncounterResult.NORMAL);
        }
    }

    private void drinkButtonClick() {
        game.encounterDrink();

        exit(EncounterResult.NORMAL);
    }

    private void fleeButtonClick() {
        disableAuto();

        if (game.isEncounterVerifyFlee()) {
            executeAction();
        }
    }

    private void ignoreButtonClick() {
        disableAuto();

        exit(EncounterResult.NORMAL);
    }

    private void interruptButtonClick() {
        disableAuto();
    }

    private void meetButtonClick() {
        game.encounterMeet();

        exit(EncounterResult.NORMAL);
    }

    private void plunderButtonClick() {
        disableAuto();

        game.encounterPlunder();

        exit(EncounterResult.NORMAL);
    }

    private void submitButtonClick() {
        if (game.isEncounterVerifySubmit()) {
            exit(commanderShip.isIllegalSpecialCargo() ? EncounterResult.ARRESTED : EncounterResult.NORMAL);
        }
    }

    private void surrenderButtonClick() {
        disableAuto();

        if ((_result = game.getEncounterVerifySurrender()) != EncounterResult.CONTINUE) {
            close();
        }
    }

    private void tradeButtonClick() {
        game.encounterTrade();

        exit(EncounterResult.NORMAL);
    }

    private void yieldButtonClick() {
        if ((_result = game.getEncounterVerifyYield()) != EncounterResult.CONTINUE) {
            close();
        }
    }

    private void opponentsShipPicturePaint(PaintEventArgs e) {
        Functions.paintShipImage(opponent, e.getGraphics(), opponentsShipPicture.getBackground());
    }

    private void yourShipPicturePaint(PaintEventArgs e) {
        Functions.paintShipImage(commanderShip, e.getGraphics(), yourShipPicture.getBackground());
    }

    private void tribblePictureClick() {
        GuiFacade.alert(AlertType.TribblesSqueek);
    }

    private void timerTick() {
        disableAuto();

        executeAction();
    }

    public EncounterResult getResult() {
        return _result;
    }
}
