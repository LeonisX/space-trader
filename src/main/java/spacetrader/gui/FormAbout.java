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

import java.awt.Point;

import javax.swing.UnsupportedLookAndFeelException;

import jwinforms.*;


public class FormAbout extends WinformForm
{
	// #region Control Declarations

	private Button btnClose;
	private Label lblTitle;
	private Label lblAbout;
	private PictureBox picLogo;

	// #endregion

	// #region Methods

	public FormAbout()
	{
		InitializeComponent();
	}

	// #region Windows Form Designer generated code
	// / <summary>
	// / Required method for Designer support - do not modify
	// / the contents of this method with the code editor.
	// / </summary>
	private void InitializeComponent()
	{
		ComponentResourceManager resources = new ComponentResourceManager(FormAbout.class);
		this.btnClose = new Button();
		this.lblTitle = new Label();
		this.lblAbout = new Label();
		this.picLogo = new PictureBox();
		((ISupportInitialize) (this.picLogo)).BeginInit();
		this.SuspendLayout();
		// 
		// btnClose
		// 
		this.btnClose.setDialogResult(DialogResult.Cancel);
		this.btnClose.setLocation(new Point(-32, -32));
		this.btnClose.setName("btnClose");
		this.btnClose.setSize(new Size(32, 32));
		this.btnClose.setTabIndex(32);
		this.btnClose.setTabStop(false);
		this.btnClose.setText("X");
		// 
		// lblTitle
		// 
		this.lblTitle.setAutoSize(true);
		this.lblTitle.setFont(FontCollection.bold825);
		this.lblTitle.setLocation(new Point(172, 8));
		this.lblTitle.setName("lblTitle");
		this.lblTitle.setSize(new Size(187, 13));
		this.lblTitle.setTabIndex(33);
		this.lblTitle.setText("Space Trader for Windows 2.01");
		// 
		// lblAbout
		// 
		this.lblAbout.setLocation(new Point(172, 32));
		this.lblAbout.setName("lblAbout");
		this.lblAbout.setSize(new Size(272, 160));
		this.lblAbout.setTabIndex(34);
		this.lblAbout.setText(resources.GetString("lblAbout.Text"));
		// 
		// picLogo
		// 
		this.picLogo.setImage(((Image) (resources.GetObject("picLogo.Image"))));
		this.picLogo.setLocation(new Point(8, 8));
		this.picLogo.setName("picLogo");
		this.picLogo.setSize(new Size(160, 160));
		this.picLogo.setTabIndex(35);
		this.picLogo.setTabStop(false);
		// 
		// FormAbout
		// 
		this.setAutoScaleBaseSize(new Size(5, 13));
		this.setCancelButton(this.btnClose);
		this.setClientSize(new Size(446, 191));
		this.Controls.add(this.picLogo);
		this.Controls.add(this.lblAbout);
		this.Controls.add(this.lblTitle);
		this.Controls.add(this.btnClose);
		this.setFormBorderStyle(FormBorderStyle.FixedDialog);
		this.setMaximizeBox(false);
		this.setMinimizeBox(false);
		this.setName("FormAbout");
		this.setShowInTaskbar(false);
		this.setStartPosition(FormStartPosition.CenterParent);
		this.setText("About Space Trader");
		((ISupportInitialize) (this.picLogo)).EndInit();
		this.PerformLayout();

	}
	// #endregion

	// #endregion
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		Launcher.runForm(new FormAbout());
	}
}
