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

import jwinforms.Container;
import jwinforms.ContentAlignment;
import jwinforms.DialogResult;
import jwinforms.FormStartPosition;
import spacetrader.Functions;
import spacetrader.Game;

public class FormCosts extends SpaceTraderForm {
    // #region Control Declarations

    private jwinforms.Button btnClose;
    private jwinforms.Label lblMerc;
    private jwinforms.Label lblIns;
    private jwinforms.Label lblInt;
    private jwinforms.Label lblTax;
    private jwinforms.Label lblTotal;
    private jwinforms.Label lblTotalLabel;
    private jwinforms.Label lblTaxLabel;
    private jwinforms.Label lblIntLabel;
    private jwinforms.Label lblMercLabel;
    private jwinforms.Label lblInsLabel;
    private jwinforms.PictureBox picLine;
    private Container components = null;

    // #endregion

    // #region Member Declarations

    private Game game = Game.CurrentGame();

    // #endregion

    // #region Methods

    public FormCosts() {
        InitializeComponent();

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
    private void InitializeComponent() {
        this.btnClose = new jwinforms.Button();
        this.lblMerc = new jwinforms.Label();
        this.lblIns = new jwinforms.Label();
        this.lblInt = new jwinforms.Label();
        this.lblTax = new jwinforms.Label();
        this.lblTotal = new jwinforms.Label();
        this.lblTotalLabel = new jwinforms.Label();
        this.lblTaxLabel = new jwinforms.Label();
        this.lblIntLabel = new jwinforms.Label();
        this.lblMercLabel = new jwinforms.Label();
        this.lblInsLabel = new jwinforms.Label();
        this.picLine = new jwinforms.PictureBox();
        this.SuspendLayout();
        //
        // btnClose
        //
        this.btnClose.setDialogResult(DialogResult.Cancel);
        this.btnClose.setLocation(new java.awt.Point(-32, -32));
        this.btnClose.setName("btnClose");
        this.btnClose.setSize(new jwinforms.Size(32, 32));
        this.btnClose.setTabIndex(32);
        this.btnClose.setTabStop(false);
        this.btnClose.setText("X");
        //
        // lblMerc
        //
        this.lblMerc.setLocation(new java.awt.Point(104, 8));
        this.lblMerc.setName("lblMerc");
        this.lblMerc.setSize(new jwinforms.Size(39, 13));
        this.lblMerc.setTabIndex(36);
        this.lblMerc.setText("888 cr.");
        this.lblMerc.TextAlign = ContentAlignment.TopRight;
        //
        // lblIns
        //
        this.lblIns.setLocation(new java.awt.Point(104, 24));
        this.lblIns.setName("lblIns");
        this.lblIns.setSize(new jwinforms.Size(39, 13));
        this.lblIns.setTabIndex(40);
        this.lblIns.setText("888 cr.");
        this.lblIns.TextAlign = ContentAlignment.TopRight;
        //
        // lblInt
        //
        this.lblInt.setLocation(new java.awt.Point(104, 40));
        this.lblInt.setName("lblInt");
        this.lblInt.setSize(new jwinforms.Size(39, 13));
        this.lblInt.setTabIndex(44);
        this.lblInt.setText("888 cr.");
        this.lblInt.TextAlign = ContentAlignment.TopRight;
        //
        // lblTax
        //
        this.lblTax.setLocation(new java.awt.Point(104, 56));
        this.lblTax.setName("lblTax");
        this.lblTax.setSize(new jwinforms.Size(39, 13));
        this.lblTax.setTabIndex(48);
        this.lblTax.setText("888 cr.");
        this.lblTax.TextAlign = ContentAlignment.TopRight;
        //
        // lblTotal
        //
        this.lblTotal.setLocation(new java.awt.Point(104, 79));
        this.lblTotal.setName("lblTotal");
        this.lblTotal.setSize(new jwinforms.Size(39, 13));
        this.lblTotal.setTabIndex(52);
        this.lblTotal.setText("888 cr.");
        this.lblTotal.TextAlign = ContentAlignment.TopRight;
        //
        // lblTotalLabel
        //
        this.lblTotalLabel.setAutoSize(true);
        this.lblTotalLabel.setFont(FontCollection.bold825);
        this.lblTotalLabel.setLocation(new java.awt.Point(8, 79));
        this.lblTotalLabel.setName("lblTotalLabel");
        this.lblTotalLabel.setSize(new jwinforms.Size(34, 13));
        this.lblTotalLabel.setTabIndex(7);
        this.lblTotalLabel.setText("Total:");
        //
        // lblTaxLabel
        //
        this.lblTaxLabel.setAutoSize(true);
        this.lblTaxLabel.setFont(FontCollection.bold825);
        this.lblTaxLabel.setLocation(new java.awt.Point(8, 56));
        this.lblTaxLabel.setName("lblTaxLabel");
        this.lblTaxLabel.setSize(new jwinforms.Size(84, 13));
        this.lblTaxLabel.setTabIndex(6);
        this.lblTaxLabel.setText("Wormhole Tax:");
        //
        // lblIntLabel
        //
        this.lblIntLabel.setAutoSize(true);
        this.lblIntLabel.setFont(FontCollection.bold825);
        this.lblIntLabel.setLocation(new java.awt.Point(8, 40));
        this.lblIntLabel.setName("lblIntLabel");
        this.lblIntLabel.setSize(new jwinforms.Size(47, 13));
        this.lblIntLabel.setTabIndex(5);
        this.lblIntLabel.setText("Interest:");
        //
        // lblMercLabel
        //
        this.lblMercLabel.setAutoSize(true);
        this.lblMercLabel.setFont(FontCollection.bold825);
        this.lblMercLabel.setLocation(new java.awt.Point(8, 8));
        this.lblMercLabel.setName("lblMercLabel");
        this.lblMercLabel.setSize(new jwinforms.Size(72, 13));
        this.lblMercLabel.setTabIndex(4);
        this.lblMercLabel.setText("Mercenaries:");
        //
        // lblInsLabel
        //
        this.lblInsLabel.setAutoSize(true);
        this.lblInsLabel.setFont(FontCollection.bold825);
        this.lblInsLabel.setLocation(new java.awt.Point(8, 24));
        this.lblInsLabel.setName("lblInsLabel");
        this.lblInsLabel.setSize(new jwinforms.Size(59, 13));
        this.lblInsLabel.setTabIndex(3);
        this.lblInsLabel.setText("Insurance:");
        //
        // picLine
        //
        this.picLine.setBackColor(java.awt.Color.darkGray);
        this.picLine.setLocation(new java.awt.Point(6, 73));
        this.picLine.setName("picLine");
        this.picLine.setSize(new jwinforms.Size(138, 1));
        this.picLine.setTabIndex(134);
        this.picLine.setTabStop(false);
        //
        // FormCosts
        //
        this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setCancelButton(this.btnClose);
        this.setClientSize(new jwinforms.Size(148, 99));
        this.Controls.addAll(this.picLine, this.lblTotal,
                this.lblTax, this.lblInt, this.lblIns, this.lblMerc,
                this.btnClose, this.lblInsLabel, this.lblTotalLabel,
                this.lblTaxLabel, this.lblIntLabel, this.lblMercLabel);
        this.setFormBorderStyle(jwinforms.FormBorderStyle.FixedDialog);
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
