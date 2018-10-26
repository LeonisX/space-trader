/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron Original coding by Pieter Spronck, Sam Anderson,
 * Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
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
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Game;
import spacetrader.game.Ship;
import spacetrader.game.enums.Difficulty;
import spacetrader.gui.debug.Launcher;
import spacetrader.guifacade.Facaded;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.Arrays;

@Facaded
public class FormJettison extends SpaceTraderForm {

    private final Game game = Game.getCurrentGame();
    
    private Button jettisonAllButton9 = new Button();
    private Button jettisonButton9 = new Button();
    private Button jettisonAllButton8 = new Button();
    private Button jettisonButton8 = new Button();
    private Button jettisonAllButton7 = new Button();
    private Button jettisonButton7 = new Button();
    private Button jettisonAllButton6 = new Button();
    private Button jettisonButton6 = new Button();
    private Button jettisonAllButton5 = new Button();
    private Button jettisonButton5 = new Button();
    private Button jettisonAllButton4 = new Button();
    private Button jettisonButton4 = new Button();
    private Button jettisonAllButton3 = new Button();
    private Button jettisonButton3 = new Button();
    private Button jettisonAllButton2 = new Button();
    private Button jettisonButton2 = new Button();
    private Button jettisonAllButton1 = new Button();
    private Button jettisonButton1 = new Button();
    private Button jettisonAllButton0 = new Button();
    private Button jettisonButton0 = new Button();
    private Label commodityLabel9 = new Label();
    private Label commodityLabel8 = new Label();
    private Label commodityLabel2 = new Label();
    private Label commodityLabel0 = new Label();
    private Label commodityLabel1 = new Label();
    private Label commodityLabel6 = new Label();
    private Label commodityLabel5 = new Label();
    private Label commodityLabel4 = new Label();
    private Label commodityLabel3 = new Label();
    private Label commodityLabel7 = new Label();
    private Label baysLabel = new Label();
    private Label baysLabelValue = new Label();

    private Button doneButton = new Button();
    
    private final Button[] jettisonButton = new Button[]{jettisonButton0, jettisonButton1, jettisonButton2, jettisonButton3,
            jettisonButton4, jettisonButton5, jettisonButton6, jettisonButton7, jettisonButton8, jettisonButton9};

    public static void main(String[] args) {
        new Game("name", Difficulty.BEGINNER,8,8,8,8, null);
        Launcher.runForm(new FormJettison());
    }

    public FormJettison() {
        initializeComponent();
        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formJettison");
        setText("Jettison Cargo");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(new Size(5, 13));
        setClientSize(new Size(218, 283));
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(doneButton);
        setCancelButton(doneButton);
        
        suspendLayout();

        jettisonAllButton9.setFlatStyle(FlatStyle.FLAT);
        jettisonAllButton9.setLocation(new Point(100, 220));
        jettisonAllButton9.setSize(new Size(32, 22));
        jettisonAllButton9.setTabIndex(141);
        jettisonAllButton9.setText("All");
        jettisonAllButton9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonButton9.setFlatStyle(FlatStyle.FLAT);
        jettisonButton9.setLocation(new Point(68, 220));
        jettisonButton9.setSize(new Size(28, 22));
        jettisonButton9.setTabIndex(140);
        //jettisonButton9.setText("88");
        jettisonButton9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonAllButton8.setFlatStyle(FlatStyle.FLAT);
        jettisonAllButton8.setLocation(new Point(100, 196));
        jettisonAllButton8.setSize(new Size(32, 22));
        jettisonAllButton8.setTabIndex(139);
        jettisonAllButton8.setText("All");
        jettisonAllButton8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonButton8.setFlatStyle(FlatStyle.FLAT);
        jettisonButton8.setLocation(new Point(68, 196));
        jettisonButton8.setSize(new Size(28, 22));
        jettisonButton8.setTabIndex(138);
        //jettisonButton8.setText("88");
        jettisonButton8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonAllButton7.setFlatStyle(FlatStyle.FLAT);
        jettisonAllButton7.setLocation(new Point(100, 172));
        jettisonAllButton7.setSize(new Size(32, 22));
        jettisonAllButton7.setTabIndex(137);
        jettisonAllButton7.setText("All");
        jettisonAllButton7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonButton7.setFlatStyle(FlatStyle.FLAT);
        jettisonButton7.setLocation(new Point(68, 172));
        jettisonButton7.setSize(new Size(28, 22));
        jettisonButton7.setTabIndex(136);
        //jettisonButton7.setText("88");
        jettisonButton7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonAllButton6.setFlatStyle(FlatStyle.FLAT);
        jettisonAllButton6.setLocation(new Point(100, 148));
        jettisonAllButton6.setSize(new Size(32, 22));
        jettisonAllButton6.setTabIndex(135);
        jettisonAllButton6.setText("All");
        jettisonAllButton6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonButton6.setFlatStyle(FlatStyle.FLAT);
        jettisonButton6.setLocation(new Point(68, 148));
        jettisonButton6.setSize(new Size(28, 22));
        jettisonButton6.setTabIndex(134);
        //jettisonButton6.setText("88");
        jettisonButton6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonAllButton5.setFlatStyle(FlatStyle.FLAT);
        jettisonAllButton5.setLocation(new Point(100, 124));
        jettisonAllButton5.setSize(new Size(32, 22));
        jettisonAllButton5.setTabIndex(133);
        jettisonAllButton5.setText("All");
        jettisonAllButton5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonButton5.setFlatStyle(FlatStyle.FLAT);
        jettisonButton5.setLocation(new Point(68, 124));
        jettisonButton5.setSize(new Size(28, 22));
        jettisonButton5.setTabIndex(132);
        //jettisonButton5.setText("88");
        jettisonButton5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonAllButton4.setFlatStyle(FlatStyle.FLAT);
        jettisonAllButton4.setLocation(new Point(100, 100));
        jettisonAllButton4.setSize(new Size(32, 22));
        jettisonAllButton4.setTabIndex(131);
        jettisonAllButton4.setText("All");
        jettisonAllButton4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonButton4.setFlatStyle(FlatStyle.FLAT);
        jettisonButton4.setLocation(new Point(68, 100));
        jettisonButton4.setSize(new Size(28, 22));
        jettisonButton4.setTabIndex(130);
        //jettisonButton4.setText("88");
        jettisonButton4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonAllButton3.setFlatStyle(FlatStyle.FLAT);
        jettisonAllButton3.setLocation(new Point(100, 76));
        jettisonAllButton3.setSize(new Size(32, 22));
        jettisonAllButton3.setTabIndex(129);
        jettisonAllButton3.setText("All");
        jettisonAllButton3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonButton3.setFlatStyle(FlatStyle.FLAT);
        jettisonButton3.setLocation(new Point(68, 76));
        jettisonButton3.setSize(new Size(28, 22));
        jettisonButton3.setTabIndex(128);
        //jettisonButton3.setText("88");
        jettisonButton3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonAllButton2.setFlatStyle(FlatStyle.FLAT);
        jettisonAllButton2.setLocation(new Point(100, 52));
        jettisonAllButton2.setSize(new Size(32, 22));
        jettisonAllButton2.setTabIndex(127);
        jettisonAllButton2.setText("All");
        jettisonAllButton2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonButton2.setFlatStyle(FlatStyle.FLAT);
        jettisonButton2.setLocation(new Point(68, 52));
        jettisonButton2.setSize(new Size(28, 22));
        jettisonButton2.setTabIndex(126);
        //jettisonButton2.setText("88");
        jettisonButton2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonAllButton1.setFlatStyle(FlatStyle.FLAT);
        jettisonAllButton1.setLocation(new Point(100, 28));
        jettisonAllButton1.setSize(new Size(32, 22));
        jettisonAllButton1.setTabIndex(125);
        jettisonAllButton1.setText("All");
        jettisonAllButton1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonButton1.setFlatStyle(FlatStyle.FLAT);
        jettisonButton1.setLocation(new Point(68, 28));
        jettisonButton1.setSize(new Size(28, 22));
        jettisonButton1.setTabIndex(124);
        //jettisonButton1.setText("88");
        jettisonButton1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonAllButton0.setFlatStyle(FlatStyle.FLAT);
        jettisonAllButton0.setLocation(new Point(100, 4));
        jettisonAllButton0.setSize(new Size(32, 22));
        jettisonAllButton0.setTabIndex(123);
        jettisonAllButton0.setText("All");
        jettisonAllButton0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        jettisonButton0.setFlatStyle(FlatStyle.FLAT);
        jettisonButton0.setLocation(new Point(68, 4));
        jettisonButton0.setSize(new Size(28, 22));
        jettisonButton0.setTabIndex(122);
        //jettisonButton0.setText("88");
        jettisonButton0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick(sender);
            }
        });

        commodityLabel9.setAutoSize(true);
        commodityLabel9.setLocation(new Point(8, 224));
        commodityLabel9.setSize(new Size(40, 13));
        commodityLabel9.setTabIndex(151);
        commodityLabel9.setText("Robots");

        commodityLabel8.setAutoSize(true);
        commodityLabel8.setLocation(new Point(8, 200));
        commodityLabel8.setSize(new Size(51, 13));
        commodityLabel8.setTabIndex(150);
        commodityLabel8.setText("Narcotics");

        commodityLabel2.setAutoSize(true);
        commodityLabel2.setLocation(new Point(8, 56));
        commodityLabel2.setSize(new Size(30, 13));
        commodityLabel2.setTabIndex(149);
        commodityLabel2.setText("Food");

        commodityLabel0.setAutoSize(true);
        commodityLabel0.setLocation(new Point(8, 8));
        commodityLabel0.setSize(new Size(34, 13));
        commodityLabel0.setTabIndex(148);
        commodityLabel0.setText("Water");

        commodityLabel1.setAutoSize(true);
        commodityLabel1.setLocation(new Point(8, 32));
        commodityLabel1.setSize(new Size(27, 13));
        commodityLabel1.setTabIndex(147);
        commodityLabel1.setText("Furs");

        commodityLabel6.setAutoSize(true);
        commodityLabel6.setLocation(new Point(8, 152));
        commodityLabel6.setSize(new Size(50, 13));
        commodityLabel6.setTabIndex(146);
        commodityLabel6.setText("Medicine");

        commodityLabel5.setAutoSize(true);
        commodityLabel5.setLocation(new Point(8, 128));
        commodityLabel5.setSize(new Size(49, 13));
        commodityLabel5.setTabIndex(145);
        commodityLabel5.setText("Firearms");

        commodityLabel4.setAutoSize(true);
        commodityLabel4.setLocation(new Point(8, 104));
        commodityLabel4.setSize(new Size(41, 13));
        commodityLabel4.setTabIndex(144);
        commodityLabel4.setText("Games");

        commodityLabel3.setAutoSize(true);
        commodityLabel3.setLocation(new Point(8, 80));
        commodityLabel3.setSize(new Size(23, 13));
        commodityLabel3.setTabIndex(143);
        commodityLabel3.setText("Ore");

        commodityLabel7.setAutoSize(true);
        commodityLabel7.setLocation(new Point(8, 176));
        commodityLabel7.setSize(new Size(53, 13));
        commodityLabel7.setTabIndex(142);
        commodityLabel7.setText("Machines");

        baysLabel.setAutoSize(true);
        baysLabel.setLocation(new Point(144, 8));
        baysLabel.setSize(new Size(33, 13));
        baysLabel.setTabIndex(152);
        baysLabel.setText("Bays:");

        baysLabelValue.setLocation(new Point(176, 8));
        baysLabelValue.setSize(new Size(33, 13));
        baysLabelValue.setTabIndex(153);
        //baysLabelValue.setText("88/88");

        doneButton.setDialogResult(DialogResult.CANCEL);
        doneButton.setFlatStyle(FlatStyle.FLAT);
        doneButton.setLocation(new Point(87, 252));
        doneButton.setSize(new Size(44, 22));
        doneButton.setTabIndex(154);
        doneButton.setText("Done");

        controls.addAll(Arrays.asList(baysLabel, baysLabelValue, commodityLabel0, commodityLabel1, commodityLabel2,
                commodityLabel3, commodityLabel4, commodityLabel5, commodityLabel6, commodityLabel7, commodityLabel8,
                commodityLabel9, jettisonButton0, jettisonButton1, jettisonButton2, jettisonButton3, jettisonButton4,
                jettisonButton5, jettisonButton6, jettisonButton7, jettisonButton8, jettisonButton9, jettisonAllButton0,
                jettisonAllButton1, jettisonAllButton2, jettisonAllButton3, jettisonAllButton4, jettisonAllButton5,
                jettisonAllButton6, jettisonAllButton7, jettisonAllButton8, jettisonAllButton9, doneButton));
    }

    private void jettison(int tradeItem, boolean all) {
        game.cargoJettison(tradeItem, all);
        updateAll();
    }

    private void updateAll() {
        Ship ship = game.getCommander().getShip();

        for (int i = 0; i < jettisonButton.length; i++) {
            jettisonButton[i].setText(ship.getCargo()[i]);
        }

        baysLabelValue.setText(ship.getFilledCargoBays() + "/" + ship.getCargoBays());
    }

    private void jettisonButtonClick(Object sender) {
        String name = ((Button) sender).getName();
        boolean all = name.contains("AllButton");
        int index = Integer.parseInt(name.substring(name.length() - 1));

        jettison(index, all);
    }
}
