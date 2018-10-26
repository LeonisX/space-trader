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

import java.awt.Point;
import java.util.Arrays;
import spacetrader.controls.Button;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.EventArgs;
import spacetrader.controls.EventHandler;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.controls.Label;
import spacetrader.controls.Size;
import spacetrader.game.Game;
import spacetrader.game.Ship;
import spacetrader.game.Strings;
import spacetrader.game.enums.Difficulty;
import spacetrader.gui.debug.Launcher;
import spacetrader.guifacade.Facaded;
import spacetrader.util.ReflectionUtils;

@Facaded
public class FormPlunder extends SpaceTraderForm {

    private final Game game = Game.getCurrentGame();
    private Button plunderAllButton9 = new Button();
    private Button plunderButton9 = new Button();
    private Button plunderAllButton8 = new Button();
    private Button plunderButton8 = new Button();
    private Button plunderAllButton7 = new Button();
    private Button plunderButton7 = new Button();
    private Button plunderAllButton6 = new Button();
    private Button plunderButton6 = new Button();
    private Button plunderAllButton5 = new Button();
    private Button plunderButton5 = new Button();
    private Button plunderAllButton4 = new Button();
    private Button plunderButton4 = new Button();
    private Button plunderAllButton3 = new Button();
    private Button plunderButton3 = new Button();
    private Button plunderAllButton2 = new Button();
    private Button plunderButton2 = new Button();
    private Button plunderAllButton1 = new Button();
    private Button plunderButton1 = new Button();
    private Button plunderAllButton0 = new Button();
    private Button plunderButton0 = new Button();
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

    private Label[] commoditiesArray = new Label[]{commodityLabel0, commodityLabel1, commodityLabel2, commodityLabel3,
            commodityLabel4, commodityLabel5, commodityLabel6, commodityLabel7, commodityLabel8, commodityLabel9};

    private final Button[] plunderButtons = new Button[]{plunderButton0, plunderButton1, plunderButton2, plunderButton3,
            plunderButton4, plunderButton5, plunderButton6, plunderButton7, plunderButton8, plunderButton9};

    private final Button[] plunderAllButtons = new Button[]{plunderAllButton0, plunderAllButton1, plunderAllButton2,
            plunderAllButton3, plunderAllButton4, plunderAllButton5, plunderAllButton6, plunderAllButton7,
            plunderAllButton8, plunderAllButton9};


    private Button btnJettison = new Button();

    public static void main(String[] args) {
        new Game("name", Difficulty.BEGINNER,8,8,8,8, null);
        FormPlunder form = new FormPlunder();
        Launcher.runForm(form);
    }

    public FormPlunder() {
        initializeComponent();
        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formPlunder");
        setText("Plunder Cargo");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(5, 13);
        setClientSize(230, 283);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(doneButton);
        setCancelButton(doneButton);
        
        suspendLayout();

        commodityLabel0.setLocation(8, 8);
        commodityLabel0.setSize(36, 13);
        //commodityLabel0.setText("Water");

        commodityLabel1.setLocation(8, 32);
        commodityLabel1.setSize(27, 13);
        //commodityLabel1.setText("Furs");

        commodityLabel2.setLocation(8, 56);
        commodityLabel2.setSize(31, 13);
        //commodityLabel2.setText("Food");

        commodityLabel3.setLocation(8, 80);
        commodityLabel3.setSize(24, 13);
        //commodityLabel3.setText("Ore");

        commodityLabel4.setLocation(8, 104);
        commodityLabel4.setSize(40, 13);
        //commodityLabel4.setText("Games");

        commodityLabel5.setLocation(8, 128);
        commodityLabel5.setSize(46, 13);
        //commodityLabel5.setText("Firearms");

        commodityLabel6.setLocation(8, 152);
        commodityLabel6.setSize(50, 13);
        //commodityLabel6.setText("Medicine");

        commodityLabel7.setLocation(8, 176);
        commodityLabel7.setSize(53, 13);
        //commodityLabel7.setText("Machines");

        commodityLabel8.setLocation(8, 200);
        commodityLabel8.setSize(52, 13);
        //commodityLabel8.setText("Narcotics");

        commodityLabel9.setLocation(8, 224);
        commodityLabel9.setSize(41, 13);
        //commodityLabel9.setText("Robots");

        for (int i = 0; i < Strings.TradeItemNames.length; i++) {
            commoditiesArray[i].setAutoSize(true);
            commoditiesArray[i].setTabIndex(142 + i);
            commoditiesArray[i].setText(Strings.TradeItemNames[i]);
        }

        plunderAllButton9.setLocation(100, 220);
        plunderAllButton9.setTabIndex(141);

        plunderButton9.setLocation(68, 220);
        plunderButton9.setTabIndex(140);

        plunderAllButton8.setLocation(100, 196);
        plunderAllButton8.setTabIndex(139);

        plunderButton8.setLocation(68, 196);
        plunderButton8.setTabIndex(138);

        plunderAllButton7.setLocation(100, 172);
        plunderAllButton7.setTabIndex(137);

        plunderButton7.setLocation(68, 172);
        plunderButton7.setTabIndex(136);

        plunderAllButton6.setLocation(100, 148);
        plunderAllButton6.setTabIndex(135);

        plunderButton6.setLocation(68, 148);
        plunderButton6.setTabIndex(134);

        plunderAllButton5.setLocation(100, 124);
        plunderAllButton5.setTabIndex(133);

        plunderButton5.setLocation(68, 124);
        plunderButton5.setTabIndex(132);

        plunderAllButton4.setLocation(100, 100);
        plunderAllButton4.setTabIndex(131);

        plunderButton4.setLocation(68, 100);
        plunderButton4.setTabIndex(130);

        plunderAllButton3.setLocation(100, 76);
        plunderAllButton3.setTabIndex(129);

        plunderButton3.setLocation(68, 76);
        plunderButton3.setTabIndex(128);

        plunderAllButton2.setLocation(100, 52);
        plunderAllButton2.setTabIndex(127);

        plunderButton2.setLocation(68, 52);
        plunderButton2.setTabIndex(126);

        plunderAllButton1.setLocation(100, 28);
        plunderAllButton1.setTabIndex(125);

        plunderButton1.setLocation(68, 28);
        plunderButton1.setTabIndex(124);

        plunderAllButton0.setLocation(100, 4);
        plunderAllButton0.setTabIndex(123);

        plunderButton0.setLocation(68, 4);
        plunderButton0.setTabIndex(122);

        Arrays.stream(plunderButtons).forEach(button -> {
            button.setFlatStyle(FlatStyle.FLAT);
            button.setSize(28, 22);
            //button.setText("88");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    plunderButtonClick(sender);
                }
            });
        });

        Arrays.stream(plunderAllButtons).forEach(button -> {
            button.setFlatStyle(FlatStyle.FLAT);
            button.setSize(32, 22);
            button.setText("All");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    plunderButtonClick(sender);
                }
            });
        });

        baysLabel.setAutoSize(true);
        baysLabel.setLocation(144, 8);
        baysLabel.setSize(33, 13);
        baysLabel.setTabIndex(152);
        baysLabel.setText("Bays:");

        baysLabelValue.setLocation(176, 8);
        baysLabelValue.setSize(48, 13);
        baysLabelValue.setTabIndex(153);
        //baysLabelValue.setText("888/888");

        btnJettison.setFlatStyle(FlatStyle.FLAT);
        btnJettison.setLocation(150, 24);
        btnJettison.setSize(53, 22);
        btnJettison.setTabIndex(155);
        btnJettison.setText("Jettison");
        btnJettison.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick();
            }
        });

        doneButton.setDialogResult(DialogResult.CANCEL);
        doneButton.setFlatStyle(FlatStyle.FLAT);
        doneButton.setLocation(87, 252);
        doneButton.setSize(44, 22);
        doneButton.setTabIndex(154);
        doneButton.setText("Done");

        controls.addAll(commoditiesArray);
        controls.addAll(plunderButtons);
        controls.addAll(plunderAllButtons);

        performLayout();

        ReflectionUtils.loadControlsData(this);
    }

    private void plunder(int tradeItem, boolean all) {
        game.cargoPlunder(tradeItem, all);
        updateAll();
    }

    private void updateAll() {
        Ship ship = game.getCommander().getShip();
        Ship opp = game.getOpponent();

        for (int i = 0; i < plunderButtons.length; i++) {
            plunderButtons[i].setText(opp.getCargo()[i]);
        }

        baysLabelValue.setText(ship.getFilledCargoBays() + "/" + ship.getCargoBays());
    }

    private void jettisonButtonClick() {
        (new FormJettison()).showDialog(this);
    }

    private void plunderButtonClick(Object sender) {
        String name = ((Button) sender).getName();
        boolean all = name.contains("All");
        int index = Integer.parseInt(name.substring(name.length() - 1));

        plunder(index, all);
    }
}
