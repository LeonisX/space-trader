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
//using System;
//using System.Drawing;
//using System.Collections;
//using System.ComponentModel;
//using System.Windows.Forms;
package spacetrader.gui;

import spacetrader.controls.Container;
import spacetrader.controls.ContentAlignment;
import spacetrader.controls.DialogResult;
import spacetrader.controls.FormStartPosition;
import spacetrader.game.Functions;
import spacetrader.game.Game;

public class FormCosts extends SpaceTraderForm {
    // #region Control Declarations

    private spacetrader.controls.Button btnClose;
    private spacetrader.controls.Label lblMerc;
    private spacetrader.controls.Label lblIns;
    private spacetrader.controls.Label lblInt;
    private spacetrader.controls.Label lblTax;
    private spacetrader.controls.Label lblTotal;
    private spacetrader.controls.Label lblTotalLabel;
    private spacetrader.controls.Label lblTaxLabel;
    private spacetrader.controls.Label lblIntLabel;
    private spacetrader.controls.Label lblMercLabel;
    private spacetrader.controls.Label lblInsLabel;
    private spacetrader.controls.PictureBox picLine;
    private Container components = null;

    // #endregion

    // #region Member Declarations

    private Game game = Game.getCurrentGame();

    // #endregion

    // #region Methods

    public FormCosts() {
        initializeComponent();

        lblMerc.setText(Functions.formatMoney(game.MercenaryCosts()));
        lblIns.setText(Functions.formatMoney(game.InsuranceCosts()));
        lblInt.setText(Functions.formatMoney(game.InterestCosts()));
        lblTax.setText(Functions.formatMoney(game.WormholeCosts()));
        lblTotal.setText(Functions.formatMoney(game.CurrentCosts()));
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        this.btnClose = new spacetrader.controls.Button();
        this.lblMerc = new spacetrader.controls.Label();
        this.lblIns = new spacetrader.controls.Label();
        this.lblInt = new spacetrader.controls.Label();
        this.lblTax = new spacetrader.controls.Label();
        this.lblTotal = new spacetrader.controls.Label();
        this.lblTotalLabel = new spacetrader.controls.Label();
        this.lblTaxLabel = new spacetrader.controls.Label();
        this.lblIntLabel = new spacetrader.controls.Label();
        this.lblMercLabel = new spacetrader.controls.Label();
        this.lblInsLabel = new spacetrader.controls.Label();
        this.picLine = new spacetrader.controls.PictureBox();
        this.suspendLayout();
        //
        // btnClose
        //
        this.btnClose.setDialogResult(DialogResult.CANCEL);
        this.btnClose.setLocation(new java.awt.Point(-32, -32));
        this.btnClose.setName("btnClose");
        this.btnClose.setSize(new spacetrader.controls.Size(32, 32));
        this.btnClose.setTabIndex(32);
        this.btnClose.setTabStop(false);
        this.btnClose.setText("X");
        //
        // lblMerc
        //
        this.lblMerc.setLocation(new java.awt.Point(104, 8));
        this.lblMerc.setName("lblMerc");
        this.lblMerc.setSize(new spacetrader.controls.Size(39, 13));
        this.lblMerc.setTabIndex(36);
        this.lblMerc.setText("888 cr.");
        this.lblMerc.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblIns
        //
        this.lblIns.setLocation(new java.awt.Point(104, 24));
        this.lblIns.setName("lblIns");
        this.lblIns.setSize(new spacetrader.controls.Size(39, 13));
        this.lblIns.setTabIndex(40);
        this.lblIns.setText("888 cr.");
        this.lblIns.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblInt
        //
        this.lblInt.setLocation(new java.awt.Point(104, 40));
        this.lblInt.setName("lblInt");
        this.lblInt.setSize(new spacetrader.controls.Size(39, 13));
        this.lblInt.setTabIndex(44);
        this.lblInt.setText("888 cr.");
        this.lblInt.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblTax
        //
        this.lblTax.setLocation(new java.awt.Point(104, 56));
        this.lblTax.setName("lblTax");
        this.lblTax.setSize(new spacetrader.controls.Size(39, 13));
        this.lblTax.setTabIndex(48);
        this.lblTax.setText("888 cr.");
        this.lblTax.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblTotal
        //
        this.lblTotal.setLocation(new java.awt.Point(104, 79));
        this.lblTotal.setName("lblTotal");
        this.lblTotal.setSize(new spacetrader.controls.Size(39, 13));
        this.lblTotal.setTabIndex(52);
        this.lblTotal.setText("888 cr.");
        this.lblTotal.textAlign = ContentAlignment.TOP_RIGHT;
        //
        // lblTotalLabel
        //
        this.lblTotalLabel.setAutoSize(true);
        this.lblTotalLabel.setFont(FontCollection.bold825);
        this.lblTotalLabel.setLocation(new java.awt.Point(8, 79));
        this.lblTotalLabel.setName("lblTotalLabel");
        this.lblTotalLabel.setSize(new spacetrader.controls.Size(34, 13));
        this.lblTotalLabel.setTabIndex(7);
        this.lblTotalLabel.setText("Total:");
        //
        // lblTaxLabel
        //
        this.lblTaxLabel.setAutoSize(true);
        this.lblTaxLabel.setFont(FontCollection.bold825);
        this.lblTaxLabel.setLocation(new java.awt.Point(8, 56));
        this.lblTaxLabel.setName("lblTaxLabel");
        this.lblTaxLabel.setSize(new spacetrader.controls.Size(84, 13));
        this.lblTaxLabel.setTabIndex(6);
        this.lblTaxLabel.setText("Wormhole Tax:");
        //
        // lblIntLabel
        //
        this.lblIntLabel.setAutoSize(true);
        this.lblIntLabel.setFont(FontCollection.bold825);
        this.lblIntLabel.setLocation(new java.awt.Point(8, 40));
        this.lblIntLabel.setName("lblIntLabel");
        this.lblIntLabel.setSize(new spacetrader.controls.Size(47, 13));
        this.lblIntLabel.setTabIndex(5);
        this.lblIntLabel.setText("Interest:");
        //
        // lblMercLabel
        //
        this.lblMercLabel.setAutoSize(true);
        this.lblMercLabel.setFont(FontCollection.bold825);
        this.lblMercLabel.setLocation(new java.awt.Point(8, 8));
        this.lblMercLabel.setName("lblMercLabel");
        this.lblMercLabel.setSize(new spacetrader.controls.Size(72, 13));
        this.lblMercLabel.setTabIndex(4);
        this.lblMercLabel.setText("Mercenaries:");
        //
        // lblInsLabel
        //
        this.lblInsLabel.setAutoSize(true);
        this.lblInsLabel.setFont(FontCollection.bold825);
        this.lblInsLabel.setLocation(new java.awt.Point(8, 24));
        this.lblInsLabel.setName("lblInsLabel");
        this.lblInsLabel.setSize(new spacetrader.controls.Size(59, 13));
        this.lblInsLabel.setTabIndex(3);
        this.lblInsLabel.setText("Insurance:");
        //
        // picLine
        //
        this.picLine.setBackground(java.awt.Color.darkGray);
        this.picLine.setLocation(new java.awt.Point(6, 73));
        this.picLine.setName("picLine");
        this.picLine.setSize(new spacetrader.controls.Size(138, 1));
        this.picLine.setTabIndex(134);
        this.picLine.setTabStop(false);
        //
        // FormCosts
        //
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(this.btnClose);
        this.setClientSize(new spacetrader.controls.Size(148, 99));
        this.Controls.addAll(this.picLine, this.lblTotal,
                this.lblTax, this.lblInt, this.lblIns, this.lblMerc,
                this.btnClose, this.lblInsLabel, this.lblTotalLabel,
                this.lblTaxLabel, this.lblIntLabel, this.lblMercLabel);
        this.setFormBorderStyle(spacetrader.controls.FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormCosts");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Cost Specification");
    }
    // #endregion

    // #endregion
}
