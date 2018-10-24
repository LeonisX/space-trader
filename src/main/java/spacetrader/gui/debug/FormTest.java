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
package spacetrader.gui.debug;

import spacetrader.controls.*;
import spacetrader.game.Consts;
import spacetrader.game.SpecialEvent;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.gui.FormAlert;
import spacetrader.gui.SpaceTraderForm;
import spacetrader.guifacade.GuiFacade;

import java.util.Arrays;

//TODO untranslated, unrefactored, untested
public class FormTest extends SpaceTraderForm {

    private spacetrader.controls.Label lblAlertType;
    private Panel boxAlert;
    private spacetrader.controls.Label lblValue2;
    private spacetrader.controls.Label lblValue1;
    private spacetrader.controls.Label lblValue3;
    private spacetrader.controls.ComboBox<AlertType> selAlertType;
    private spacetrader.controls.TextBox txtValue1;
    private spacetrader.controls.TextBox txtValue2;
    private spacetrader.controls.TextBox txtValue3;
    private Panel panel1;
    private spacetrader.controls.Button btnTestAlert;
    private spacetrader.controls.Button btnTestSpecialEvent;
    private spacetrader.controls.ComboBox<SpecialEventType> selSpecialEvent;
    private spacetrader.controls.Label lblSpecialEvent;

    public static void main(String[] args) {
        Launcher.runForm(new FormTest());
    }

    public FormTest() {
        initializeComponent();

        AlertType[] alerts = Arrays.copyOfRange(AlertType.values(), AlertType.Alert.ordinal(), AlertType.WildWontStayAboardReactor.ordinal());
//			for (AlertType type = AlertType.Alert; type.castToInt() <= AlertType.WildWontStayAboardReactor.castToInt(); type++)
        for (AlertType type : alerts) {
            selAlertType.getItems().add(type);
        }
        selAlertType.setSelectedIndex(0);

        SpecialEventType[] events = Arrays.copyOfRange(SpecialEventType.values(), SpecialEventType.Artifact.ordinal(), SpecialEventType.WildGetsOut.ordinal());
//			for (SpecialEventType type = SpecialEventType.Artifact; type < SpecialEventType.WildGetsOut; type++)
        for (SpecialEventType type : events) {
            selSpecialEvent.getItems().add(type);
        }
        selSpecialEvent.setSelectedIndex(0);
    }

    private void initializeComponent() {
        this.lblAlertType = new spacetrader.controls.Label();
        this.boxAlert = new Panel();
        this.btnTestAlert = new spacetrader.controls.Button();
        this.txtValue3 = new spacetrader.controls.TextBox();
        this.txtValue2 = new spacetrader.controls.TextBox();
        this.txtValue1 = new spacetrader.controls.TextBox();
        this.selAlertType = new spacetrader.controls.ComboBox<>();
        this.lblValue3 = new spacetrader.controls.Label();
        this.lblValue1 = new spacetrader.controls.Label();
        this.lblValue2 = new spacetrader.controls.Label();
        this.panel1 = new Panel();
        this.btnTestSpecialEvent = new spacetrader.controls.Button();
        this.selSpecialEvent = new spacetrader.controls.ComboBox<>();
        this.lblSpecialEvent = new spacetrader.controls.Label();
        this.boxAlert.suspendLayout();
        this.panel1.suspendLayout();
        this.suspendLayout();
        //
        // lblAlertType
        //
        this.lblAlertType.setAutoSize(true);
        this.lblAlertType.setLocation(new java.awt.Point(8, 19));
        this.lblAlertType.setName("lblAlertType");
        this.lblAlertType.setSize(new spacetrader.controls.Size(56, 13));
        this.lblAlertType.setTabIndex(0);
        this.lblAlertType.setText("Alert Type");
        //
        // boxAlert
        //
        this.boxAlert.getControls().addAll((new BaseComponent[]{
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
        this.boxAlert.setSize(new spacetrader.controls.Size(200, 152));
        this.boxAlert.setTabIndex(1);
        this.boxAlert.setTabStop(false);
        this.boxAlert.setText("Test Alert");
        //
        // btnTestAlert
        //
        this.btnTestAlert.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        this.btnTestAlert.setLocation(new java.awt.Point(80, 120));
        this.btnTestAlert.setName("btnTestAlert");
        this.btnTestAlert.setSize(new spacetrader.controls.Size(41, 22));
        this.btnTestAlert.setTabIndex(8);
        this.btnTestAlert.setText("Test");
        this.btnTestAlert.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnTestAlert_Click();
            }
        });
        //
        // txtValue3
        //
        this.txtValue3.setLocation(new java.awt.Point(72, 88));
        this.txtValue3.setName("txtValue3");
        this.txtValue3.setSize(new spacetrader.controls.Size(120, 20));
        this.txtValue3.setTabIndex(7);
        this.txtValue3.setText("");
        //
        // txtValue2
        //
        this.txtValue2.setLocation(new java.awt.Point(72, 64));
        this.txtValue2.setName("txtValue2");
        this.txtValue2.setSize(new spacetrader.controls.Size(120, 20));
        this.txtValue2.setTabIndex(6);
        this.txtValue2.setText("");
        //
        // txtValue1
        //
        this.txtValue1.setLocation(new java.awt.Point(72, 40));
        this.txtValue1.setName("txtValue1");
        this.txtValue1.setSize(new spacetrader.controls.Size(120, 20));
        this.txtValue1.setTabIndex(5);
        this.txtValue1.setText("");
        //
        // selAlertType
        //
        this.selAlertType.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        this.selAlertType.setLocation(new java.awt.Point(72, 16));
        this.selAlertType.setName("selAlertType");
        this.selAlertType.setSize(new spacetrader.controls.Size(120, 21));
        this.selAlertType.setTabIndex(4);
        //
        // lblValue3
        //
        this.lblValue3.setAutoSize(true);
        this.lblValue3.setLocation(new java.awt.Point(8, 91));
        this.lblValue3.setName("lblValue3");
        this.lblValue3.setSize(new spacetrader.controls.Size(43, 13));
        this.lblValue3.setTabIndex(3);
        this.lblValue3.setText("Value 3");
        //
        // lblValue1
        //
        this.lblValue1.setAutoSize(true);
        this.lblValue1.setLocation(new java.awt.Point(8, 43));
        this.lblValue1.setName("lblValue1");
        this.lblValue1.setSize(new spacetrader.controls.Size(43, 13));
        this.lblValue1.setTabIndex(2);
        this.lblValue1.setText("Value 1");
        //
        // lblValue2
        //
        this.lblValue2.setAutoSize(true);
        this.lblValue2.setLocation(new java.awt.Point(8, 67));
        this.lblValue2.setName("lblValue2");
        this.lblValue2.setSize(new spacetrader.controls.Size(43, 13));
        this.lblValue2.setTabIndex(1);
        this.lblValue2.setText("Value 2");
        //
        // panel1
        //
        this.panel1.getControls().addAll((new BaseComponent[]{
                this.btnTestSpecialEvent,
                this.selSpecialEvent,
                this.lblSpecialEvent}));
        this.panel1.setLocation(new java.awt.Point(8, 168));
        this.panel1.setName("panel1");
        this.panel1.setSize(new spacetrader.controls.Size(200, 80));
        this.panel1.setTabIndex(2);
        this.panel1.setTabStop(false);
        this.panel1.setText("Test Special Alert");
        //
        // btnTestSpecialEvent
        //
        this.btnTestSpecialEvent.setFlatStyle(spacetrader.controls.FlatStyle.FLAT);
        this.btnTestSpecialEvent.setLocation(new java.awt.Point(80, 48));
        this.btnTestSpecialEvent.setName("btnTestSpecialEvent");
        this.btnTestSpecialEvent.setSize(new spacetrader.controls.Size(41, 22));
        this.btnTestSpecialEvent.setTabIndex(8);
        this.btnTestSpecialEvent.setText("Test");
        this.btnTestSpecialEvent.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnTestSpecialEvent_Click();
            }
        });
        //
        // selSpecialEvent
        //
        this.selSpecialEvent.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        this.selSpecialEvent.setLocation(new java.awt.Point(88, 16));
        this.selSpecialEvent.setName("selSpecialEvent");
        this.selSpecialEvent.setSize(new spacetrader.controls.Size(104, 21));
        this.selSpecialEvent.setTabIndex(4);
        //
        // lblSpecialEvent
        //
        this.lblSpecialEvent.setAutoSize(true);
        this.lblSpecialEvent.setLocation(new java.awt.Point(8, 19));
        this.lblSpecialEvent.setName("lblSpecialEvent");
        this.lblSpecialEvent.setSize(new spacetrader.controls.Size(73, 13));
        this.lblSpecialEvent.setTabIndex(0);
        this.lblSpecialEvent.setText("Special Event");
        //
        // FormTest
        //
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setClientSize(new spacetrader.controls.Size(370, 255));
        this.controls.addAll(Arrays.asList(
                this.panel1,
                this.boxAlert));
        this.setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormTest");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setText("Test");
    }

    private void btnTestAlert_Click() {
        GuiFacade.alert(AlertType.Alert, "Result", ("The result was " +
                GuiFacade.alert(((AlertType) selAlertType.getSelectedItem()), txtValue1.getText(), txtValue2.getText(), txtValue3.getText()).toString()));
    }

    private void btnTestSpecialEvent_Click() {
        SpecialEvent specEvent = Consts.SpecialEvents[((SpecialEventType) selSpecialEvent.getSelectedItem()).castToInt()];
        String btn1, btn2;
        DialogResult res1, res2;

        if (specEvent.isMessageOnly()) {
            btn1 = "Ok";
            btn2 = null;
            res1 = DialogResult.OK;
            res2 = DialogResult.NONE;
        } else {
            btn1 = "Yes";
            btn2 = "No";
            res1 = DialogResult.YES;
            res2 = DialogResult.NO;
        }

        (new FormAlert(specEvent.getTitle(), specEvent.getString(), btn1, res1, btn2, res2, null)).showDialog(this);
    }
}
