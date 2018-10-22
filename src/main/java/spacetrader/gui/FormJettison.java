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
import spacetrader.game.Game;
import spacetrader.game.Ship;
import spacetrader.guifacade.Facaded;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.Arrays;

@Facaded
public class FormJettison extends SpaceTraderForm {

    private final Game game = Game.getCurrentGame();
    
    private Button btnJettisonAll9 = new Button();
    private Button btnJettisonQty9 = new Button();
    private Button btnJettisonAll8 = new Button();
    private Button btnJettisonQty8 = new Button();
    private Button btnJettisonAll7 = new Button();
    private Button btnJettisonQty7 = new Button();
    private Button btnJettisonAll6 = new Button();
    private Button btnJettisonQty6 = new Button();
    private Button btnJettisonAll5 = new Button();
    private Button btnJettisonQty5 = new Button();
    private Button btnJettisonAll4 = new Button();
    private Button btnJettisonQty4 = new Button();
    private Button btnJettisonAll3 = new Button();
    private Button btnJettisonQty3 = new Button();
    private Button btnJettisonAll2 = new Button();
    private Button btnJettisonQty2 = new Button();
    private Button btnJettisonAll1 = new Button();
    private Button btnJettisonQty1 = new Button();
    private Button btnJettisonAll0 = new Button();
    private Button btnJettisonQty0 = new Button();
    private Label lblTradeCmdty9 = new Label();
    private Label lblTradeCmdty8 = new Label();
    private Label lblTradeCmdty2 = new Label();
    private Label lblTradeCmdty0 = new Label();
    private Label lblTradeCmdty1 = new Label();
    private Label lblTradeCmdty6 = new Label();
    private Label lblTradeCmdty5 = new Label();
    private Label lblTradeCmdty4 = new Label();
    private Label lblTradeCmdty3 = new Label();
    private Label lblTradeCmdty7 = new Label();
    private Label lblBaysLabel = new Label();
    private Label lblBays = new Label();

    private Button btnDone = new Button();
    
    private final Button[] btnJettisonQty = new Button[]{btnJettisonQty0, btnJettisonQty1, btnJettisonQty2, btnJettisonQty3,
            btnJettisonQty4, btnJettisonQty5, btnJettisonQty6, btnJettisonQty7, btnJettisonQty8, btnJettisonQty9};

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
        setAcceptButton(btnDone);
        setCancelButton(btnDone);
        
        suspendLayout();

        btnJettisonAll9.setFlatStyle(FlatStyle.FLAT);
        btnJettisonAll9.setLocation(new Point(100, 220));
        btnJettisonAll9.setSize(new Size(32, 22));
        btnJettisonAll9.setTabIndex(141);
        btnJettisonAll9.setText("All");
        btnJettisonAll9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonQty9.setFlatStyle(FlatStyle.FLAT);
        btnJettisonQty9.setLocation(new Point(68, 220));
        btnJettisonQty9.setSize(new Size(28, 22));
        btnJettisonQty9.setTabIndex(140);
        btnJettisonQty9.setText("88");
        btnJettisonQty9.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonAll8.setFlatStyle(FlatStyle.FLAT);
        btnJettisonAll8.setLocation(new Point(100, 196));
        btnJettisonAll8.setSize(new Size(32, 22));
        btnJettisonAll8.setTabIndex(139);
        btnJettisonAll8.setText("All");
        btnJettisonAll8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonQty8.setFlatStyle(FlatStyle.FLAT);
        btnJettisonQty8.setLocation(new Point(68, 196));
        btnJettisonQty8.setSize(new Size(28, 22));
        btnJettisonQty8.setTabIndex(138);
        btnJettisonQty8.setText("88");
        btnJettisonQty8.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonAll7.setFlatStyle(FlatStyle.FLAT);
        btnJettisonAll7.setLocation(new Point(100, 172));
        btnJettisonAll7.setSize(new Size(32, 22));
        btnJettisonAll7.setTabIndex(137);
        btnJettisonAll7.setText("All");
        btnJettisonAll7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonQty7.setFlatStyle(FlatStyle.FLAT);
        btnJettisonQty7.setLocation(new Point(68, 172));
        btnJettisonQty7.setSize(new Size(28, 22));
        btnJettisonQty7.setTabIndex(136);
        btnJettisonQty7.setText("88");
        btnJettisonQty7.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonAll6.setFlatStyle(FlatStyle.FLAT);
        btnJettisonAll6.setLocation(new Point(100, 148));
        btnJettisonAll6.setSize(new Size(32, 22));
        btnJettisonAll6.setTabIndex(135);
        btnJettisonAll6.setText("All");
        btnJettisonAll6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonQty6.setFlatStyle(FlatStyle.FLAT);
        btnJettisonQty6.setLocation(new Point(68, 148));
        btnJettisonQty6.setSize(new Size(28, 22));
        btnJettisonQty6.setTabIndex(134);
        btnJettisonQty6.setText("88");
        btnJettisonQty6.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonAll5.setFlatStyle(FlatStyle.FLAT);
        btnJettisonAll5.setLocation(new Point(100, 124));
        btnJettisonAll5.setSize(new Size(32, 22));
        btnJettisonAll5.setTabIndex(133);
        btnJettisonAll5.setText("All");
        btnJettisonAll5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonQty5.setFlatStyle(FlatStyle.FLAT);
        btnJettisonQty5.setLocation(new Point(68, 124));
        btnJettisonQty5.setSize(new Size(28, 22));
        btnJettisonQty5.setTabIndex(132);
        btnJettisonQty5.setText("88");
        btnJettisonQty5.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonAll4.setFlatStyle(FlatStyle.FLAT);
        btnJettisonAll4.setLocation(new Point(100, 100));
        btnJettisonAll4.setSize(new Size(32, 22));
        btnJettisonAll4.setTabIndex(131);
        btnJettisonAll4.setText("All");
        btnJettisonAll4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonQty4.setFlatStyle(FlatStyle.FLAT);
        btnJettisonQty4.setLocation(new Point(68, 100));
        btnJettisonQty4.setSize(new Size(28, 22));
        btnJettisonQty4.setTabIndex(130);
        btnJettisonQty4.setText("88");
        btnJettisonQty4.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonAll3.setFlatStyle(FlatStyle.FLAT);
        btnJettisonAll3.setLocation(new Point(100, 76));
        btnJettisonAll3.setSize(new Size(32, 22));
        btnJettisonAll3.setTabIndex(129);
        btnJettisonAll3.setText("All");
        btnJettisonAll3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonQty3.setFlatStyle(FlatStyle.FLAT);
        btnJettisonQty3.setLocation(new Point(68, 76));
        btnJettisonQty3.setSize(new Size(28, 22));
        btnJettisonQty3.setTabIndex(128);
        btnJettisonQty3.setText("88");
        btnJettisonQty3.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonAll2.setFlatStyle(FlatStyle.FLAT);
        btnJettisonAll2.setLocation(new Point(100, 52));
        btnJettisonAll2.setSize(new Size(32, 22));
        btnJettisonAll2.setTabIndex(127);
        btnJettisonAll2.setText("All");
        btnJettisonAll2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonQty2.setFlatStyle(FlatStyle.FLAT);
        btnJettisonQty2.setLocation(new Point(68, 52));
        btnJettisonQty2.setSize(new Size(28, 22));
        btnJettisonQty2.setTabIndex(126);
        btnJettisonQty2.setText("88");
        btnJettisonQty2.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonAll1.setFlatStyle(FlatStyle.FLAT);
        btnJettisonAll1.setLocation(new Point(100, 28));
        btnJettisonAll1.setSize(new Size(32, 22));
        btnJettisonAll1.setTabIndex(125);
        btnJettisonAll1.setText("All");
        btnJettisonAll1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonQty1.setFlatStyle(FlatStyle.FLAT);
        btnJettisonQty1.setLocation(new Point(68, 28));
        btnJettisonQty1.setSize(new Size(28, 22));
        btnJettisonQty1.setTabIndex(124);
        btnJettisonQty1.setText("88");
        btnJettisonQty1.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonAll0.setFlatStyle(FlatStyle.FLAT);
        btnJettisonAll0.setLocation(new Point(100, 4));
        btnJettisonAll0.setSize(new Size(32, 22));
        btnJettisonAll0.setTabIndex(123);
        btnJettisonAll0.setText("All");
        btnJettisonAll0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        btnJettisonQty0.setFlatStyle(FlatStyle.FLAT);
        btnJettisonQty0.setLocation(new Point(68, 4));
        btnJettisonQty0.setSize(new Size(28, 22));
        btnJettisonQty0.setTabIndex(122);
        btnJettisonQty0.setText("88");
        btnJettisonQty0.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnJettison_Click(sender);
            }
        });

        lblTradeCmdty9.setAutoSize(true);
        lblTradeCmdty9.setLocation(new Point(8, 224));
        lblTradeCmdty9.setSize(new Size(40, 13));
        lblTradeCmdty9.setTabIndex(151);
        lblTradeCmdty9.setText("Robots");

        lblTradeCmdty8.setAutoSize(true);
        lblTradeCmdty8.setLocation(new Point(8, 200));
        lblTradeCmdty8.setSize(new Size(51, 13));
        lblTradeCmdty8.setTabIndex(150);
        lblTradeCmdty8.setText("Narcotics");

        lblTradeCmdty2.setAutoSize(true);
        lblTradeCmdty2.setLocation(new Point(8, 56));
        lblTradeCmdty2.setSize(new Size(30, 13));
        lblTradeCmdty2.setTabIndex(149);
        lblTradeCmdty2.setText("Food");

        lblTradeCmdty0.setAutoSize(true);
        lblTradeCmdty0.setLocation(new Point(8, 8));
        lblTradeCmdty0.setSize(new Size(34, 13));
        lblTradeCmdty0.setTabIndex(148);
        lblTradeCmdty0.setText("Water");

        lblTradeCmdty1.setAutoSize(true);
        lblTradeCmdty1.setLocation(new Point(8, 32));
        lblTradeCmdty1.setSize(new Size(27, 13));
        lblTradeCmdty1.setTabIndex(147);
        lblTradeCmdty1.setText("Furs");

        lblTradeCmdty6.setAutoSize(true);
        lblTradeCmdty6.setLocation(new Point(8, 152));
        lblTradeCmdty6.setSize(new Size(50, 13));
        lblTradeCmdty6.setTabIndex(146);
        lblTradeCmdty6.setText("Medicine");

        lblTradeCmdty5.setAutoSize(true);
        lblTradeCmdty5.setLocation(new Point(8, 128));
        lblTradeCmdty5.setSize(new Size(49, 13));
        lblTradeCmdty5.setTabIndex(145);
        lblTradeCmdty5.setText("Firearms");

        lblTradeCmdty4.setAutoSize(true);
        lblTradeCmdty4.setLocation(new Point(8, 104));
        lblTradeCmdty4.setSize(new Size(41, 13));
        lblTradeCmdty4.setTabIndex(144);
        lblTradeCmdty4.setText("Games");

        lblTradeCmdty3.setAutoSize(true);
        lblTradeCmdty3.setLocation(new Point(8, 80));
        lblTradeCmdty3.setSize(new Size(23, 13));
        lblTradeCmdty3.setTabIndex(143);
        lblTradeCmdty3.setText("Ore");

        lblTradeCmdty7.setAutoSize(true);
        lblTradeCmdty7.setLocation(new Point(8, 176));
        lblTradeCmdty7.setSize(new Size(53, 13));
        lblTradeCmdty7.setTabIndex(142);
        lblTradeCmdty7.setText("Machines");

        lblBaysLabel.setAutoSize(true);
        lblBaysLabel.setLocation(new Point(144, 8));
        lblBaysLabel.setSize(new Size(33, 13));
        lblBaysLabel.setTabIndex(152);

        lblBays.setLocation(new Point(176, 8));
        lblBays.setSize(new Size(33, 13));
        lblBays.setTabIndex(153);
        lblBays.setText("88/88");

        btnDone.setDialogResult(DialogResult.CANCEL);
        btnDone.setFlatStyle(FlatStyle.FLAT);
        btnDone.setLocation(new Point(87, 252));
        btnDone.setSize(new Size(44, 22));
        btnDone.setTabIndex(154);
        btnDone.setText("Done");

        controls.addAll(Arrays.asList(btnDone, lblBays, lblBaysLabel, lblTradeCmdty9, lblTradeCmdty8, lblTradeCmdty2,
                lblTradeCmdty0, lblTradeCmdty1, lblTradeCmdty6, lblTradeCmdty5, lblTradeCmdty4, lblTradeCmdty3,
                lblTradeCmdty7, btnJettisonAll9, btnJettisonQty9, btnJettisonAll8, btnJettisonQty8, btnJettisonAll7,
                btnJettisonQty7, btnJettisonAll6, btnJettisonQty6, btnJettisonAll5, btnJettisonQty5, btnJettisonAll4,
                btnJettisonQty4, btnJettisonAll3, btnJettisonQty3, btnJettisonAll2, btnJettisonQty2, btnJettisonAll1,
                btnJettisonQty1, btnJettisonAll0, btnJettisonQty0));
    }

    private void jettison(int tradeItem, boolean all) {
        game.cargoJettison(tradeItem, all);
        updateAll();
    }

    private void updateAll() {
        Ship ship = game.getCommander().getShip();

        for (int i = 0; i < btnJettisonQty.length; i++) {
            btnJettisonQty[i].setText(ship.getCargo()[i]);
        }

        lblBays.setText(ship.getFilledCargoBays() + "/" + ship.getCargoBays());
    }

    private void btnJettison_Click(Object sender) {
        String name = ((Button) sender).getName();
        //TODO
        boolean all = !name.contains("Qty");
        int index = Integer.parseInt(name.substring(name.length() - 1));

        jettison(index, all);
    }
}
