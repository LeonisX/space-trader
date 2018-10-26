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
import spacetrader.game.Strings;
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
    private Label commodityLabel0 = new Label();
    private Label commodityLabel1 = new Label();
    private Label commodityLabel2 = new Label();
    private Label commodityLabel3 = new Label();
    private Label commodityLabel4 = new Label();
    private Label commodityLabel5 = new Label();
    private Label commodityLabel6 = new Label();
    private Label commodityLabel7 = new Label();
    private Label commodityLabel8 = new Label();
    private Label commodityLabel9 = new Label();
    private Label baysLabel = new Label();
    private Label baysLabelValue = new Label();

    private Button doneButton = new Button();

    private Label[] commoditiesArray = new Label[]{commodityLabel0, commodityLabel1, commodityLabel2, commodityLabel3,
            commodityLabel4, commodityLabel5, commodityLabel6, commodityLabel7, commodityLabel8, commodityLabel9};

    private final Button[] jettisonButtons = new Button[]{jettisonButton0, jettisonButton1, jettisonButton2, jettisonButton3,
            jettisonButton4, jettisonButton5, jettisonButton6, jettisonButton7, jettisonButton8, jettisonButton9};

    private final Button[] jettisonAllButtons = new Button[]{jettisonAllButton0, jettisonAllButton1, jettisonAllButton2,
            jettisonAllButton3, jettisonAllButton4, jettisonAllButton5, jettisonAllButton6, jettisonAllButton7,
            jettisonAllButton8, jettisonAllButton9};

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
        setAutoScaleBaseSize(5, 13);
        setClientSize(218, 283);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(doneButton);
        setCancelButton(doneButton);
        
        suspendLayout();

        jettisonAllButton9.setLocation(100, 220);
        jettisonAllButton9.setSize(32, 22);
        jettisonAllButton9.setTabIndex(141);
        jettisonAllButton9.setText("All");

        jettisonButton9.setLocation(68, 220);
        jettisonButton9.setSize(28, 22);
        jettisonButton9.setTabIndex(140);

        jettisonAllButton8.setLocation(100, 196);
        jettisonAllButton8.setSize(32, 22);
        jettisonAllButton8.setTabIndex(139);

        jettisonButton8.setLocation(68, 196);
        jettisonButton8.setSize(28, 22);
        jettisonButton8.setTabIndex(138);

        jettisonAllButton7.setLocation(100, 172);
        jettisonAllButton7.setSize(32, 22);
        jettisonAllButton7.setTabIndex(137);

        jettisonButton7.setLocation(68, 172);
        jettisonButton7.setSize(28, 22);
        jettisonButton7.setTabIndex(136);

        jettisonAllButton6.setLocation(100, 148);
        jettisonAllButton6.setSize(32, 22);
        jettisonAllButton6.setTabIndex(135);

        jettisonButton6.setLocation(68, 148);
        jettisonButton6.setSize(28, 22);
        jettisonButton6.setTabIndex(134);

        jettisonAllButton5.setLocation(100, 124);
        jettisonAllButton5.setSize(32, 22);
        jettisonAllButton5.setTabIndex(133);

        jettisonButton5.setLocation(68, 124);
        jettisonButton5.setSize(28, 22);
        jettisonButton5.setTabIndex(132);

        jettisonAllButton4.setLocation(100, 100);
        jettisonAllButton4.setSize(32, 22);
        jettisonAllButton4.setTabIndex(131);

        jettisonButton4.setLocation(68, 100);
        jettisonButton4.setSize(28, 22);
        jettisonButton4.setTabIndex(130);

        jettisonAllButton3.setLocation(100, 76);
        jettisonAllButton3.setSize(32, 22);
        jettisonAllButton3.setTabIndex(129);

        jettisonButton3.setLocation(68, 76);
        jettisonButton3.setSize(28, 22);
        jettisonButton3.setTabIndex(128);

        jettisonAllButton2.setLocation(100, 52);
        jettisonAllButton2.setSize(32, 22);
        jettisonAllButton2.setTabIndex(127);

        jettisonButton2.setLocation(68, 52);
        jettisonButton2.setSize(28, 22);
        jettisonButton2.setTabIndex(126);

        jettisonAllButton1.setLocation(100, 28);
        jettisonAllButton1.setSize(32, 22);
        jettisonAllButton1.setTabIndex(125);

        jettisonButton1.setLocation(68, 28);
        jettisonButton1.setSize(28, 22);
        jettisonButton1.setTabIndex(124);

        jettisonAllButton0.setLocation(100, 4);
        jettisonAllButton0.setSize(32, 22);
        jettisonAllButton0.setTabIndex(123);

        jettisonButton0.setLocation(68, 4);
        jettisonButton0.setSize(28, 22);
        jettisonButton0.setTabIndex(122);

        Arrays.stream(jettisonButtons).forEach(button -> {
            button.setFlatStyle(FlatStyle.FLAT);
            //button.setText("88");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    jettisonButtonClick(((Button) sender).getName());
                }
            });
        });

        Arrays.stream(jettisonAllButtons).forEach(button -> {
            button.setFlatStyle(FlatStyle.FLAT);
            button.setText(Strings.JettisonAll);
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    jettisonButtonClick(((Button) sender).getName());
                }
            });
        });

        commodityLabel9.setLocation(8, 224);
        commodityLabel9.setSize(40, 13);
        //commodityLabel9.setText("Robots");

        commodityLabel8.setLocation(8, 200);
        commodityLabel8.setSize(51, 13);
        //commodityLabel8.setText("Narcotics");

        commodityLabel2.setLocation(8, 56);
        commodityLabel2.setSize(30, 13);
        //commodityLabel2.setText("Food");

        commodityLabel0.setLocation(8, 8);
        commodityLabel0.setSize(34, 13);
        //commodityLabel0.setText("Water");

        commodityLabel1.setLocation(8, 32);
        commodityLabel1.setSize(27, 13);
        //commodityLabel1.setText("Furs");

        commodityLabel6.setLocation(8, 152);
        commodityLabel6.setSize(50, 13);
        //commodityLabel6.setText("Medicine");

        commodityLabel5.setLocation(8, 128);
        commodityLabel5.setSize(49, 13);
        //commodityLabel5.setText("Firearms");

        commodityLabel4.setLocation(8, 104);
        commodityLabel4.setSize(41, 13);
        //commodityLabel4.setText("Games");

        commodityLabel3.setLocation(8, 80);
        commodityLabel3.setSize(23, 13);
        //commodityLabel3.setText("Ore");

        commodityLabel7.setLocation(8, 176);
        commodityLabel7.setSize(53, 13);
        //commodityLabel7.setText("Machines");

        for (int i = 0; i < Strings.TradeItemNames.length; i++) {
            commoditiesArray[i].setAutoSize(true);
            commoditiesArray[i].setTabIndex(142 + i);
            commoditiesArray[i].setText(Strings.TradeItemNames[i]);
        }

        baysLabel.setAutoSize(true);
        baysLabel.setLocation(144, 8);
        baysLabel.setSize(33, 13);
        baysLabel.setTabIndex(152);
        baysLabel.setText("Bays:");

        baysLabelValue.setLocation(176, 8);
        baysLabelValue.setSize(33, 13);
        baysLabelValue.setTabIndex(153);
        //baysLabelValue.setText("88/88");

        doneButton.setDialogResult(DialogResult.CANCEL);
        doneButton.setFlatStyle(FlatStyle.FLAT);
        doneButton.setLocation(87, 252);
        doneButton.setSize(44, 22);
        doneButton.setTabIndex(154);
        doneButton.setText("Done");

        controls.addAll(commoditiesArray);
        controls.addAll(jettisonButtons);
        controls.addAll(jettisonAllButtons);
        controls.addAll(baysLabel, baysLabelValue, doneButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void jettison(int tradeItem, boolean all) {
        game.cargoJettison(tradeItem, all);
        updateAll();
    }

    private void updateAll() {
        Ship ship = game.getCommander().getShip();

        for (int i = 0; i < jettisonButtons.length; i++) {
            jettisonButtons[i].setText(ship.getCargo()[i]);
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
