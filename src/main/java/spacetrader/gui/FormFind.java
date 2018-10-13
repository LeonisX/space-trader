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
import jwinforms.DialogResult;
import jwinforms.FormStartPosition;
import jwinforms.WinformForm;

public class FormFind extends SpaceTraderForm
{
	// #region Control Declarations

	private jwinforms.Label lblText;
	private jwinforms.Button btnOk;
	private jwinforms.Button btnCancel;
	private jwinforms.TextBox txtSystem;
	private jwinforms.CheckBox chkTrack;

	// #endregion

	// #region Methods

	public FormFind()
	{
		InitializeComponent();

		txtSystem.setText("");
		chkTrack.setChecked(false);
	}

	// #region Windows Form Designer generated code
	// / <summary>
	// / Required method for Designer support - do not modify
	// / the contents of this method with the code editor.
	// / </summary>
	private void InitializeComponent()
	{
		lblText = new jwinforms.Label();
		btnOk = new jwinforms.Button();
		btnCancel = new jwinforms.Button();
		txtSystem = new jwinforms.TextBox();
		chkTrack = new jwinforms.CheckBox();
		this.SuspendLayout();
		//
		// lblText
		//
		lblText.setAutoSize(true);
		lblText.setLocation(new java.awt.Point(8, 8));
		lblText.setName("lblText");
		lblText.setSize(new jwinforms.Size(177, 13));
		lblText.setTabIndex(3);
		lblText.setText("Which system are you looking for?");
		//
		// btnOk
		//
		btnOk.setDialogResult(DialogResult.OK);
		btnOk.setFlatStyle(jwinforms.FlatStyle.Flat);
		btnOk.setLocation(new java.awt.Point(43, 68));
		btnOk.setName("btnOk");
		btnOk.setSize(new jwinforms.Size(40, 22));
		btnOk.setTabIndex(3);
		btnOk.setText("Ok");
		//
		// btnCancel
		//
		btnCancel.setDialogResult(DialogResult.Cancel);
		btnCancel.setFlatStyle(jwinforms.FlatStyle.Flat);
		btnCancel.setLocation(new java.awt.Point(91, 68));
		btnCancel.setName("btnCancel");
		btnCancel.setSize(new jwinforms.Size(50, 22));
		btnCancel.setTabIndex(4);
		btnCancel.setText("Cancel");
		//
		// txtSystem
		//
		txtSystem.setLocation(new java.awt.Point(8, 24));
		txtSystem.setName("txtSystem");
		txtSystem.setSize(new jwinforms.Size(168, 20));
		txtSystem.setTabIndex(1);
		txtSystem.setText("");
		//
		// chkTrack
		//
		chkTrack.setLocation(new java.awt.Point(8, 48));
		chkTrack.setName("chkTrack");
		chkTrack.setSize(new jwinforms.Size(112, 16));
		chkTrack.setTabIndex(2);
		chkTrack.setText("Track this system");
		//
		// FormFind
		//
		this.setAcceptButton(btnOk);
		this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
		this.setCancelButton(btnCancel);
		this.setClientSize(new jwinforms.Size(184, 97));
		this.setControlBox(false);
		Controls.addAll(chkTrack, txtSystem,
				btnCancel, btnOk, lblText);
		this.setFormBorderStyle(jwinforms.FormBorderStyle.FixedDialog);
		this.setName("FormFind");
		this.setShowInTaskbar(false);
		this.setStartPosition(FormStartPosition.CenterParent);
		this.setText("Find System");
	}

	public String SystemName()
	{
		return txtSystem.getText();
	}

	public boolean TrackSystem()
	{
		return chkTrack.isChecked();
	}
}
