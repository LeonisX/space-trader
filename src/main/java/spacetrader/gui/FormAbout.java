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

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Image;
import spacetrader.controls.Label;
import spacetrader.gui.debug.Launcher;
import spacetrader.util.ReflectionUtils;

import javax.swing.*;
import java.awt.*;

public class FormAbout extends WinformForm {

    private PictureBox logoPicture;
    private Label titleLabel;
    private Label aboutLabel;
    private Button closeButton;

    public FormAbout() {
        initializeComponent();
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        Launcher.runForm(new FormAbout());
    }

    private void initializeComponent() {
        closeButton = new Button();
        titleLabel = new Label();
        aboutLabel = new Label();
        logoPicture = new PictureBox();
        suspendLayout();

        closeButton.setDialogResult(DialogResult.CANCEL);
        //TODO delete all sizes
        closeButton.setLocation(new Point(-32, -32));
        closeButton.setSize(new Size(32, 32));
        closeButton.setTabIndex(32);
        closeButton.setTabStop(false);

        titleLabel.setAutoSize(true);
        titleLabel.setFont(FontCollection.bold825);
        titleLabel.setLocation(new Point(172, 8));
        titleLabel.setSize(new Size(187, 13));
        titleLabel.setTabIndex(33);
        titleLabel.setText("Space Trader for Windows 2.01");

        aboutLabel.setLocation(new Point(172, 32));
        aboutLabel.setSize(new Size(272, 160));
        aboutLabel.setTabIndex(34);
        aboutLabel.setText("Copyright © 2005 French<BR>spacetrader@frenchfryz.com<BR><BR>" +
                "Palm version copyright © 2000-2002 by Peter Spronk<BR>" +
                "space_trader@hotmail.com<BR><BR>Pictures copyright © 2000 by Alexander Lawrence<BR><BR>" +
                "This game is freeware under a GNU General Public License.<BR>" +
                "http://spacetraderwin.sourceforge.net/");

        logoPicture.setImage(((Image) (ResourceManager.getImage("images/splash.jpg"))));
        logoPicture.setLocation(new Point(8, 8));
        logoPicture.setSize(new Size(160, 160));
        logoPicture.setTabIndex(35);
        logoPicture.setTabStop(false);

        setAutoScaleBaseSize(new Size(5, 13));
        setCancelButton(closeButton);
        setClientSize(new Size(446, 191));

        controls.add(logoPicture);
        controls.add(aboutLabel);
        controls.add(titleLabel);
        controls.add(closeButton);

        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setName("formAbout");
        ReflectionUtils.setAllComponentNames(this);
        setShowInTaskbar(false);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setText("About Space Trader");
        performLayout();
    }
}
