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

package spacetrader.gui;import java.util.*;

import jwinforms.*;
import spacetrader.*;import spacetrader.enums.*;
import spacetrader.guifacade.GuiFacade;
	public class FormTest extends SpaceTraderForm
	{
		//#region Control Declarations

		private Label lblAlertType;
		private GroupBox boxAlert;
		private Label lblValue2;
		private Label lblValue1;
		private Label lblValue3;
		private ComboBox selAlertType;
		private TextBox txtValue1;
		private TextBox txtValue2;
		private TextBox txtValue3;
		private GroupBox groupBox1;
		private Button btnTestAlert;
		private Button btnTestSpecialEvent;
		private ComboBox selSpecialEvent;
		private Label lblSpecialEvent;
		private Container components = null;

		//#endregion

		//#region Methods

		public FormTest()
		{
			InitializeComponent();

			AlertType[] alerts = Arrays.copyOfRange(AlertType.values(), AlertType.Alert.ordinal(), AlertType.WildWontStayAboardReactor.ordinal());
//			for (AlertType type = AlertType.Alert; type.CastToInt() <= AlertType.WildWontStayAboardReactor.CastToInt(); type++)
			for (AlertType type : alerts)		
				selAlertType.Items.add(type);
			selAlertType.setSelectedIndex(0);

			SpecialEventType[] events = Arrays.copyOfRange(SpecialEventType.values(), SpecialEventType.Artifact.ordinal(), SpecialEventType.WildGetsOut.ordinal());
//			for (SpecialEventType type = SpecialEventType.Artifact; type < SpecialEventType.WildGetsOut; type++)
				for (SpecialEventType type : events)
				selSpecialEvent.Items.add(type);
			selSpecialEvent.setSelectedIndex(0);
		}

		

		//#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.lblAlertType = new Label();
			this.boxAlert = new GroupBox();
			this.btnTestAlert = new Button();
			this.txtValue3 = new TextBox();
			this.txtValue2 = new TextBox();
			this.txtValue1 = new TextBox();
			this.selAlertType = new ComboBox();
			this.lblValue3 = new Label();
			this.lblValue1 = new Label();
			this.lblValue2 = new Label();
			this.groupBox1 = new GroupBox();
			this.btnTestSpecialEvent = new Button();
			this.selSpecialEvent = new ComboBox();
			this.lblSpecialEvent = new Label();
			this.boxAlert.SuspendLayout();
			this.groupBox1.SuspendLayout();
			this.SuspendLayout();
			//
			// lblAlertType
			//
			this.lblAlertType.setAutoSize(true);
			this.lblAlertType.setLocation(new java.awt.Point(8, 19));
			this.lblAlertType.setName("lblAlertType");
			this.lblAlertType.setSize(new Size(56, 13));
			this.lblAlertType.setTabIndex(0);
			this.lblAlertType.setText("Alert Type");
			//
			// boxAlert
			//
			this.boxAlert.Controls.addAll((new WinformControl[] {
			 this.btnTestAlert,
			 this.txtValue3,
			 this.txtValue2,
			 this.txtValue1,
			 this.selAlertType,
			 this.lblValue3,
			 this.lblValue1,
			 this.lblValue2,
			 this.lblAlertType}));
			this.boxAlert.setLocation(new java.awt.Point(8, 8));
			this.boxAlert.setName("boxAlert");
			this.boxAlert.setSize(new Size(200, 152));
			this.boxAlert.setTabIndex(1);
			this.boxAlert.setTabStop(false);
			this.boxAlert.setText("Test Alert");
			//
			// btnTestAlert
			//
			this.btnTestAlert.setFlatStyle(FlatStyle.Flat);
			this.btnTestAlert.setLocation(new java.awt.Point(80, 120));
			this.btnTestAlert.setName("btnTestAlert");
			this.btnTestAlert.setSize(new Size(41, 22));
			this.btnTestAlert.setTabIndex(8);
			this.btnTestAlert.setText("Test");
			this.btnTestAlert.setClick(new EventHandler<Object, EventArgs>()
{
public void handle(Object sender, EventArgs e)
	{
btnTestAlert_Click(sender, e);}});
			//
			// txtValue3
			//
			this.txtValue3.setLocation(new java.awt.Point(72, 88));
			this.txtValue3.setName("txtValue3");
			this.txtValue3.setSize(new Size(120, 20));
			this.txtValue3.setTabIndex(7);
			this.txtValue3.setText("");
			//
			// txtValue2
			//
			this.txtValue2.setLocation(new java.awt.Point(72, 64));
			this.txtValue2.setName("txtValue2");
			this.txtValue2.setSize(new Size(120, 20));
			this.txtValue2.setTabIndex(6);
			this.txtValue2.setText("");
			//
			// txtValue1
			//
			this.txtValue1.setLocation(new java.awt.Point(72, 40));
			this.txtValue1.setName("txtValue1");
			this.txtValue1.setSize(new Size(120, 20));
			this.txtValue1.setTabIndex(5);
			this.txtValue1.setText("");
			//
			// selAlertType
			//
			this.selAlertType.DropDownStyle = ComboBoxStyle.DropDownList;
			this.selAlertType.setLocation(new java.awt.Point(72, 16));
			this.selAlertType.setName("selAlertType");
			this.selAlertType.setSize(new Size(120, 21));
			this.selAlertType.setTabIndex(4);
			//
			// lblValue3
			//
			this.lblValue3.setAutoSize(true);
			this.lblValue3.setLocation(new java.awt.Point(8, 91));
			this.lblValue3.setName("lblValue3");
			this.lblValue3.setSize(new Size(43, 13));
			this.lblValue3.setTabIndex(3);
			this.lblValue3.setText("Value 3");
			//
			// lblValue1
			//
			this.lblValue1.setAutoSize(true);
			this.lblValue1.setLocation(new java.awt.Point(8, 43));
			this.lblValue1.setName("lblValue1");
			this.lblValue1.setSize(new Size(43, 13));
			this.lblValue1.setTabIndex(2);
			this.lblValue1.setText("Value 1");
			//
			// lblValue2
			//
			this.lblValue2.setAutoSize(true);
			this.lblValue2.setLocation(new java.awt.Point(8, 67));
			this.lblValue2.setName("lblValue2");
			this.lblValue2.setSize(new Size(43, 13));
			this.lblValue2.setTabIndex(1);
			this.lblValue2.setText("Value 2");
			//
			// groupBox1
			//
			this.groupBox1.Controls.addAll((new WinformControl[] {
			this.btnTestSpecialEvent,
			this.selSpecialEvent,
			this.lblSpecialEvent}));
			this.groupBox1.setLocation(new java.awt.Point(8, 168));
			this.groupBox1.setName("groupBox1");
			this.groupBox1.setSize(new Size(200, 80));
			this.groupBox1.setTabIndex(2);
			this.groupBox1.setTabStop(false);
			this.groupBox1.setText("Test Special Alert");
			//
			// btnTestSpecialEvent
			//
			this.btnTestSpecialEvent.setFlatStyle(FlatStyle.Flat);
			this.btnTestSpecialEvent.setLocation(new java.awt.Point(80, 48));
			this.btnTestSpecialEvent.setName("btnTestSpecialEvent");
			this.btnTestSpecialEvent.setSize(new Size(41, 22));
			this.btnTestSpecialEvent.setTabIndex(8);
			this.btnTestSpecialEvent.setText("Test");
			this.btnTestSpecialEvent.setClick(new EventHandler<Object, EventArgs>()
{
public void handle(Object sender, EventArgs e)
	{
btnTestSpecialEvent_Click(sender, e);}});
			//
			// selSpecialEvent
			//
			this.selSpecialEvent.DropDownStyle = ComboBoxStyle.DropDownList;
			this.selSpecialEvent.setLocation(new java.awt.Point(88, 16));
			this.selSpecialEvent.setName("selSpecialEvent");
			this.selSpecialEvent.setSize(new Size(104, 21));
			this.selSpecialEvent.setTabIndex(4);
			//
			// lblSpecialEvent
			//
			this.lblSpecialEvent.setAutoSize(true);
			this.lblSpecialEvent.setLocation(new java.awt.Point(8, 19));
			this.lblSpecialEvent.setName("lblSpecialEvent");
			this.lblSpecialEvent.setSize(new Size(73, 13));
			this.lblSpecialEvent.setTabIndex(0);
			this.lblSpecialEvent.setText("Special Event");
			//
			// FormTest
			//
			this.setAutoScaleBaseSize(new Size(5, 13));
			this.setClientSize(new Size(370, 255));
			this.Controls.addAll(Arrays.asList(
																																	this.groupBox1,
																																	this.boxAlert));
			this.setFormBorderStyle(FormBorderStyle.FixedDialog);
			this.setMaximizeBox(false);
			this.setMinimizeBox(false);
			this.setName("FormTest");
			this.setShowInTaskbar(false);
			this.setStartPosition(FormStartPosition.CenterParent);
			this.setText("Test");
		}
		//#endregion

		//#endregion

		//#region Event Handlers

		private void btnTestAlert_Click(Object sender, EventArgs e)
		{
			GuiFacade.alert(AlertType.Alert, "Result", ("The result was " +
			GuiFacade.alert(((AlertType)selAlertType.getSelectedItem()), txtValue1.getText(), txtValue2.getText(), txtValue3.getText()).toString()));
		}

		private void btnTestSpecialEvent_Click(Object sender, EventArgs e)
		{
			SpecialEvent	specEvent	= Consts.SpecialEvents[((SpecialEventType)selSpecialEvent.getSelectedItem()).CastToInt()];
			String				btn1, btn2;
			DialogResult	res1, res2;

			if (specEvent.MessageOnly())
			{
				btn1	= "Ok";
				btn2	= null;
				res1	= DialogResult.OK;
				res2	= DialogResult.None;
			}
			else
			{
				btn1	= "Yes";
				btn2	= "No";
				res1	= DialogResult.Yes;
				res2	= DialogResult.No;
			}

			(new FormAlert(specEvent.Title(), specEvent.String(), btn1, res1, btn2, res2, null)).ShowDialog(this);
		}

		//#endregion
	}
