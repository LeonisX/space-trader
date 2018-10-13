/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/
package spacetrader.gui;

import jwinforms.*;
import jwinforms.Container;
import jwinforms.Image;
import spacetrader.*;
import spacetrader.enums.AlertType;
import spacetrader.enums.ShipType;
import spacetrader.enums.Size;
import spacetrader.guifacade.GuiEngine;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;
import spacetrader.stub.Directory;
import spacetrader.util.Hashtable;
import util.Path;

import java.awt.*;
import java.util.Arrays;

@SuppressWarnings({"synthetic-access", "unchecked"})
public class FormShipyard extends SpaceTraderForm {
    //#region Control Declarations

    private final Game game = Game.CurrentGame();
    private final Shipyard shipyard = Game.CurrentGame().Commander().getCurrentSystem().Shipyard();
    private final ShipType[] imgTypes = new ShipType[]{ShipType.Flea, ShipType.Gnat, ShipType.Firefly,
            ShipType.Mosquito, ShipType.Bumblebee, ShipType.Beetle, ShipType.Hornet, ShipType.Grasshopper,
            ShipType.Termite, ShipType.Wasp, ShipType.Custom};
    private IContainer components;
    private jwinforms.Label lblWelcome;
    private jwinforms.TextBox txtName;
    private jwinforms.Label lblName;
    private jwinforms.PictureBox picShip;
    private jwinforms.Label lblDesignFee;
    private jwinforms.Button btnConstruct;
    private jwinforms.Button btnCancel;
    private jwinforms.PictureBox picLogo;
    private jwinforms.GroupBox boxCosts;
    private jwinforms.GroupBox boxAllocation;
    private jwinforms.NumericUpDown numHullStrength;
    private jwinforms.Label lblHullStrenghLabel;
    private jwinforms.NumericUpDown numCargoBays;
    private jwinforms.NumericUpDown numCrewQuarters;
    private jwinforms.NumericUpDown numFuelTanks;
    private jwinforms.NumericUpDown numShieldSlots;
    private jwinforms.NumericUpDown numGadgetSlots;
    private jwinforms.NumericUpDown numWeaponSlots;
    private jwinforms.Label lblCargoBays;
    private jwinforms.Label lblFuelTanks;
    private jwinforms.Label lblCrewQuarters;
    private jwinforms.Label lblShieldSlots;
    private jwinforms.Label lblGadgetSlots;
    private jwinforms.Label lblWeaponsSlots;
    private jwinforms.Label lblShipCost;
    private jwinforms.Label lblTotalCost;
    private jwinforms.Label lblTotalCostLabel;
    private jwinforms.Label lblShipCostLabel;
    private jwinforms.Label lblDesignFeeLabel;
    private jwinforms.GroupBox boxWelcome;
    private jwinforms.GroupBox boxInfo;
    private jwinforms.Label lblSize;
    private jwinforms.ComboBox selSize;
    private jwinforms.Label lblTemplate;
    private jwinforms.ComboBox selTemplate;
    private jwinforms.Button btnSetCustomImage;
    private jwinforms.Label lblImageLabel;
    private jwinforms.Button btnNextImage;
    private jwinforms.Button btnPrevImage;
    private jwinforms.Label lblImage;
    private jwinforms.Label lblUnitsUsedLabel;
    private jwinforms.PictureBox picInfoLine;
    private jwinforms.Label lblPctLabel;
    private jwinforms.Label lblPct;
    private jwinforms.Label lblPenaltyLabel;
    private jwinforms.Label lblPenalty;
    private jwinforms.PictureBox picCostsLine;
    private jwinforms.Label lblSizeSpecialtyLabel;
    private jwinforms.Label lblSkillLabel;
    private jwinforms.Label lblSizeSpecialty;
    private jwinforms.Label lblSkill;
    private jwinforms.Label lblSkillDescription;
    private jwinforms.Label lblWarning;
    private jwinforms.ImageList ilShipyardLogos;
    private jwinforms.OpenFileDialog dlgOpen;
    private jwinforms.Button btnLoad;
    private jwinforms.Button btnSave;
    private jwinforms.Label lblTradeInLabel;
    private jwinforms.Label lblTradeIn;

    //#endregion

    //#region Member variables
    private jwinforms.Label lblUnitsUsed;
    private jwinforms.Label lblDisabledPct;
    private jwinforms.SaveFileDialog dlgSave;
    private boolean loading = false;
    private ArrayList<Size> sizes = null;
    private Image[] customImages = new Image[Consts.ImagesPerShip];
    private int imgIndex = 0;
    private jwinforms.Label lblDisabledName;

    //#endregion

    //#region Methods

    public FormShipyard() {
        initializeComponent();

        this.setText(Functions.StringVars(Strings.ShipyardTitle, shipyard.Name()));
        picLogo.setImage(ilShipyardLogos.getImages()[shipyard.Id().castToInt()]);
        lblWelcome.setText(Functions.StringVars(Strings.ShipyardWelcome, shipyard.Name(), shipyard.Engineer()));
        lblSizeSpecialty.setText(Strings.Sizes[shipyard.SpecialtySize().castToInt()]);
        lblSkill.setText(Strings.ShipyardSkills[shipyard.Skill().castToInt()]);
        lblSkillDescription.setText(Strings.ShipyardSkillDescriptions[shipyard.Skill().castToInt()]);
        lblWarning.setText(Functions.StringVars(Strings.ShipyardWarning, "" + Shipyard.PENALTY_FIRST_PCT, ""
                + Shipyard.PENALTY_SECOND_PCT));

        dlgOpen.setInitialDirectory(Consts.CustomImagesDirectory);
        dlgSave.setInitialDirectory(Consts.CustomTemplatesDirectory);
        lblDisabledName.setImage(GuiEngine.imageProvider.getDirectionImages()[Consts.DirectionDown]);
        lblDisabledPct.setImage(GuiEngine.imageProvider.getDirectionImages()[Consts.DirectionDown]);

        LoadSizes();
        LoadTemplateList();
        LoadSelectedTemplate();
    }

    //#region Windows Form Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void initializeComponent() {
        components = new Container();
        ResourceManager resources = new ResourceManager(FormShipyard.class);
        boxWelcome = new jwinforms.GroupBox();
        lblSkillDescription = new jwinforms.Label();
        lblSkill = new jwinforms.Label();
        lblSizeSpecialty = new jwinforms.Label();
        lblSkillLabel = new jwinforms.Label();
        lblSizeSpecialtyLabel = new jwinforms.Label();
        lblWelcome = new jwinforms.Label();
        lblWarning = new jwinforms.Label();
        picLogo = new jwinforms.PictureBox();
        boxInfo = new jwinforms.GroupBox();
        btnSave = new jwinforms.Button();
        btnLoad = new jwinforms.Button();
        picInfoLine = new jwinforms.PictureBox();
        btnPrevImage = new jwinforms.Button();
        btnNextImage = new jwinforms.Button();
        lblImage = new jwinforms.Label();
        lblImageLabel = new jwinforms.Label();
        selTemplate = new jwinforms.ComboBox();
        lblTemplate = new jwinforms.Label();
        selSize = new jwinforms.ComboBox();
        lblSize = new jwinforms.Label();
        btnSetCustomImage = new jwinforms.Button();
        picShip = new jwinforms.PictureBox();
        txtName = new jwinforms.TextBox();
        lblName = new jwinforms.Label();
        lblUnitsUsed = new jwinforms.Label();
        lblUnitsUsedLabel = new jwinforms.Label();
        boxCosts = new jwinforms.GroupBox();
        lblTradeIn = new jwinforms.Label();
        lblTradeInLabel = new jwinforms.Label();
        picCostsLine = new jwinforms.PictureBox();
        lblPenalty = new jwinforms.Label();
        lblPenaltyLabel = new jwinforms.Label();
        lblShipCost = new jwinforms.Label();
        lblTotalCost = new jwinforms.Label();
        lblTotalCostLabel = new jwinforms.Label();
        lblShipCostLabel = new jwinforms.Label();
        lblDesignFee = new jwinforms.Label();
        lblDesignFeeLabel = new jwinforms.Label();
        btnConstruct = new jwinforms.Button();
        btnCancel = new jwinforms.Button();
        boxAllocation = new jwinforms.GroupBox();
        lblPct = new jwinforms.Label();
        lblPctLabel = new jwinforms.Label();
        numHullStrength = new jwinforms.NumericUpDown();
        lblHullStrenghLabel = new jwinforms.Label();
        numCargoBays = new jwinforms.NumericUpDown();
        numCrewQuarters = new jwinforms.NumericUpDown();
        numFuelTanks = new jwinforms.NumericUpDown();
        numShieldSlots = new jwinforms.NumericUpDown();
        numGadgetSlots = new jwinforms.NumericUpDown();
        numWeaponSlots = new jwinforms.NumericUpDown();
        lblCargoBays = new jwinforms.Label();
        lblFuelTanks = new jwinforms.Label();
        lblCrewQuarters = new jwinforms.Label();
        lblShieldSlots = new jwinforms.Label();
        lblGadgetSlots = new jwinforms.Label();
        lblWeaponsSlots = new jwinforms.Label();
        ilShipyardLogos = new jwinforms.ImageList(components);
        dlgOpen = new jwinforms.OpenFileDialog();
        lblDisabledPct = new jwinforms.Label();
        dlgSave = new jwinforms.SaveFileDialog();
        lblDisabledName = new jwinforms.Label();
        boxWelcome.suspendLayout();
        boxInfo.suspendLayout();
        boxCosts.suspendLayout();
        boxAllocation.suspendLayout();
        ((ISupportInitialize) (numHullStrength)).beginInit();
        ((ISupportInitialize) (numCargoBays)).beginInit();
        ((ISupportInitialize) (numCrewQuarters)).beginInit();
        ((ISupportInitialize) (numFuelTanks)).beginInit();
        ((ISupportInitialize) (numShieldSlots)).beginInit();
        ((ISupportInitialize) (numGadgetSlots)).beginInit();
        ((ISupportInitialize) (numWeaponSlots)).beginInit();
        this.suspendLayout();
        //
        // boxWelcome
        //
        boxWelcome.controls.addAll((new WinformControl[]{lblSkillDescription, lblSkill, lblSizeSpecialty,
                lblSkillLabel, lblSizeSpecialtyLabel, lblWarning, picLogo, lblWelcome}));
        boxWelcome.setLocation(new java.awt.Point(8, 0));
        boxWelcome.setName("boxWelcome");
        boxWelcome.setSize(new jwinforms.Size(270, 204));
        boxWelcome.setTabIndex(1);
        boxWelcome.setTabStop(false);
        //
        // lblSkillDescription
        //
        lblSkillDescription.setLocation(new java.awt.Point(8, 98));
        lblSkillDescription.setName("lblSkillDescription");
        lblSkillDescription.setSize(new jwinforms.Size(258, 26));
        lblSkillDescription.setTabIndex(27);
        lblSkillDescription.setText("All ships finalructed at this shipyard use 2 fewer units per crew quarter.");
        //
        // lblSkill
        //
        lblSkill.setLocation(new java.awt.Point(180, 79));
        lblSkill.setName("lblSkill");
        lblSkill.setSize(new jwinforms.Size(87, 13));
        lblSkill.setTabIndex(26);
        lblSkill.setText("Crew Quartering");
        //
        // lblSizeSpecialty
        //
        lblSizeSpecialty.setLocation(new java.awt.Point(180, 65));
        lblSizeSpecialty.setName("lblSizeSpecialty");
        lblSizeSpecialty.setSize(new jwinforms.Size(64, 13));
        lblSizeSpecialty.setTabIndex(25);
        lblSizeSpecialty.setText("Gargantuan");
        //
        // lblSkillLabel
        //
        lblSkillLabel.setAutoSize(true);
        lblSkillLabel.setFont(FontCollection.bold825);
        lblSkillLabel.setLocation(new java.awt.Point(92, 79));
        lblSkillLabel.setName("lblSkillLabel");
        lblSkillLabel.setSize(new jwinforms.Size(72, 13));
        lblSkillLabel.setTabIndex(24);
        lblSkillLabel.setText("Special Skill:");
        //
        // lblSizeSpecialtyLabel
        //
        lblSizeSpecialtyLabel.setAutoSize(true);
        lblSizeSpecialtyLabel.setFont(FontCollection.bold825);
        lblSizeSpecialtyLabel.setLocation(new java.awt.Point(92, 65));
        lblSizeSpecialtyLabel.setName("lblSizeSpecialtyLabel");
        lblSizeSpecialtyLabel.setSize(new jwinforms.Size(82, 13));
        lblSizeSpecialtyLabel.setTabIndex(23);
        lblSizeSpecialtyLabel.setText("Size Specialty:");
        //
        // lblWelcome
        //
        lblWelcome.setLocation(new java.awt.Point(92, 12));
        lblWelcome.setName("lblWelcome");
        lblWelcome.setSize(new jwinforms.Size(176, 52));
        lblWelcome.setTabIndex(3);
        lblWelcome.setText("Welcome to Sorosuub Engineering Shipyards! Our best engineer, Obi-Wan, is at your"
                + " service.");
        //
        // lblWarning
        //
        lblWarning.setLocation(new java.awt.Point(8, 134));
        lblWarning.setName("lblWarning");
        lblWarning.setSize(new jwinforms.Size(258, 65));
        lblWarning.setTabIndex(5);
        lblWarning.setText("Bear in mind that getting too close to the maximum number of units will result in"
                + " a \"Crowding Penalty\" due to the engineering difficulty of squeezing everything "
                + "in.  There is a modest penalty at 80%, and a more severe one at 90%.");
        //
        // picLogo
        //
        picLogo.setBackColor(java.awt.Color.black);
        picLogo.setLocation(new java.awt.Point(8, 12));
        picLogo.setName("picLogo");
        picLogo.setSize(new jwinforms.Size(80, 80));
        picLogo.SizeMode = jwinforms.PictureBoxSizeMode.StretchImage;
        picLogo.setTabIndex(22);
        picLogo.setTabStop(false);
        //
        // boxInfo
        //
        boxInfo.controls.addAll((new WinformControl[]{btnSave, btnLoad, picInfoLine, btnPrevImage, btnNextImage,
                lblImage, lblImageLabel, selTemplate, lblTemplate, selSize, lblSize, btnSetCustomImage, picShip,
                txtName, lblName}));
        boxInfo.setLocation(new java.awt.Point(8, 208));
        boxInfo.setName("boxInfo");
        boxInfo.setSize(new jwinforms.Size(270, 160));
        boxInfo.setTabIndex(2);
        boxInfo.setTabStop(false);
        boxInfo.setText("Info");
        //
        // btnSave
        //
        btnSave.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSave.setLocation(new java.awt.Point(216, 40));
        btnSave.setName("btnSave");
        btnSave.setSize(new jwinforms.Size(44, 20));
        btnSave.setTabIndex(4);
        btnSave.setText("Save");
        btnSave.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnSave_Click(sender, e);
            }
        });
        btnSave.setMouseEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnSave_MouseEnter(sender, e);
            }
        });
        btnSave.setMouseLeave(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnSave_MouseLeave(sender, e);
            }
        });
        //
        // btnLoad
        //
        btnLoad.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnLoad.setLocation(new java.awt.Point(216, 16));
        btnLoad.setName("btnLoad");
        btnLoad.setSize(new jwinforms.Size(44, 20));
        btnLoad.setTabIndex(2);
        btnLoad.setText("Load");
        btnLoad.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnLoad_Click(sender, e);
            }
        });
        //
        // picInfoLine
        //
        picInfoLine.setBackColor(java.awt.Color.darkGray);
        picInfoLine.setLocation(new java.awt.Point(8, 89));
        picInfoLine.setName("picInfoLine");
        picInfoLine.setSize(new jwinforms.Size(254, 1));
        picInfoLine.setTabIndex(132);
        picInfoLine.setTabStop(false);
        //
        // btnPrevImage
        //
        btnPrevImage.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnPrevImage.setLocation(new java.awt.Point(154, 95));
        btnPrevImage.setName("btnPrevImage");
        btnPrevImage.setSize(new jwinforms.Size(18, 18));
        btnPrevImage.setTabIndex(6);
        btnPrevImage.setText("<");
        btnPrevImage.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnPrevImage_Click(sender, e);
            }
        });
        //
        // btnNextImage
        //
        btnNextImage.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnNextImage.setLocation(new java.awt.Point(242, 95));
        btnNextImage.setName("btnNextImage");
        btnNextImage.setSize(new jwinforms.Size(18, 18));
        btnNextImage.setTabIndex(7);
        btnNextImage.setText(">");
        btnNextImage.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnNextImage_Click(sender, e);
            }
        });
        //
        // lblImage
        //
        lblImage.setLocation(new java.awt.Point(174, 98));
        lblImage.setName("lblImage");
        lblImage.setSize(new jwinforms.Size(70, 13));
        lblImage.setTabIndex(61);
        lblImage.setText("Custom Ship");
        lblImage.TextAlign = ContentAlignment.TopCenter;
        //
        // lblImageLabel
        //
        lblImageLabel.setAutoSize(true);
        lblImageLabel.setLocation(new java.awt.Point(8, 95));
        lblImageLabel.setName("lblImageLabel");
        lblImageLabel.setSize(new jwinforms.Size(39, 13));
        lblImageLabel.setTabIndex(22);
        lblImageLabel.setText("Image:");
        //
        // selTemplate
        //
        selTemplate.DropDownStyle = jwinforms.ComboBoxStyle.DropDownList;
        selTemplate.setLocation(new java.awt.Point(80, 16));
        selTemplate.setName("selTemplate");
        selTemplate.setSize(new jwinforms.Size(132, 21));
        selTemplate.setTabIndex(1);
        //
        // lblTemplate
        //
        lblTemplate.setAutoSize(true);
        lblTemplate.setLocation(new java.awt.Point(8, 19));
        lblTemplate.setName("lblTemplate");
        lblTemplate.setSize(new jwinforms.Size(55, 13));
        lblTemplate.setTabIndex(20);
        lblTemplate.setText("Template:");
        //
        // selSize
        //
        selSize.DropDownStyle = jwinforms.ComboBoxStyle.DropDownList;
        selSize.setLocation(new java.awt.Point(80, 63));
        selSize.setName("selSize");
        selSize.setSize(new jwinforms.Size(180, 21));
        selSize.setTabIndex(5);
        selSize.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                selSize_SelectedIndexChanged(sender, e);
            }
        });
        //
        // lblSize
        //
        lblSize.setAutoSize(true);
        lblSize.setLocation(new java.awt.Point(8, 66));
        lblSize.setName("lblSize");
        lblSize.setSize(new jwinforms.Size(29, 13));
        lblSize.setTabIndex(18);
        lblSize.setText("Size:");
        //
        // btnSetCustomImage
        //
        btnSetCustomImage.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSetCustomImage.setLocation(new java.awt.Point(154, 121));
        btnSetCustomImage.setName("btnSetCustomImage");
        btnSetCustomImage.setSize(new jwinforms.Size(106, 22));
        btnSetCustomImage.setTabIndex(8);
        btnSetCustomImage.setText("Set Custom...");
        btnSetCustomImage.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnSetCustomImage_Click(sender, e);
            }
        });
        //
        // picShip
        //
        picShip.setBackColor(java.awt.Color.white);
        picShip.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        picShip.setLocation(new java.awt.Point(80, 95));
        picShip.setName("picShip");
        picShip.setSize(new jwinforms.Size(66, 54));
        picShip.setTabIndex(14);
        picShip.setTabStop(false);
        //
        // txtName
        //
        txtName.setLocation(new java.awt.Point(80, 40));
        txtName.setName("txtName");
        txtName.setSize(new jwinforms.Size(132, 20));
        txtName.setTabIndex(3);
        txtName.setText("");
        txtName.setTextChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                txtName_TextChanged(sender, e);
            }
        });
        //
        // lblName
        //
        lblName.setAutoSize(true);
        lblName.setLocation(new java.awt.Point(8, 44));
        lblName.setName("lblName");
        lblName.setSize(new jwinforms.Size(63, 13));
        lblName.setTabIndex(5);
        lblName.setText("Ship Name:");
        //
        // lblUnitsUsed
        //
        lblUnitsUsed.setLocation(new java.awt.Point(110, 186));
        lblUnitsUsed.setName("lblUnitsUsed");
        lblUnitsUsed.setSize(new jwinforms.Size(23, 13));
        lblUnitsUsed.setTabIndex(17);
        lblUnitsUsed.setText("888");
        lblUnitsUsed.TextAlign = ContentAlignment.TopRight;
        //
        // lblUnitsUsedLabel
        //
        lblUnitsUsedLabel.setAutoSize(true);
        lblUnitsUsedLabel.setLocation(new java.awt.Point(8, 186));
        lblUnitsUsedLabel.setName("lblUnitsUsedLabel");
        lblUnitsUsedLabel.setSize(new jwinforms.Size(63, 13));
        lblUnitsUsedLabel.setTabIndex(16);
        lblUnitsUsedLabel.setText("Units Used:");
        //
        // boxCosts
        //
        boxCosts.controls.addAll((new WinformControl[]{lblTradeIn, lblTradeInLabel, picCostsLine, lblPenalty,
                lblPenaltyLabel, lblShipCost, lblTotalCost, lblTotalCostLabel, lblShipCostLabel, lblDesignFee,
                lblDesignFeeLabel}));
        boxCosts.setLocation(new java.awt.Point(286, 230));
        boxCosts.setName("boxCosts");
        boxCosts.setSize(new jwinforms.Size(184, 106));
        boxCosts.setTabIndex(4);
        boxCosts.setTabStop(false);
        boxCosts.setText("Costs");
        //
        // lblTradeIn
        //
        lblTradeIn.setLocation(new java.awt.Point(106, 64));
        lblTradeIn.setName("lblTradeIn");
        lblTradeIn.setSize(new jwinforms.Size(75, 16));
        lblTradeIn.setTabIndex(135);
        lblTradeIn.setText("-8,888,888 cr.");
        lblTradeIn.TextAlign = ContentAlignment.TopRight;
        //
        // lblTradeInLabel
        //
        lblTradeInLabel.setAutoSize(true);
        lblTradeInLabel.setLocation(new java.awt.Point(8, 64));
        lblTradeInLabel.setName("lblTradeInLabel");
        lblTradeInLabel.setSize(new jwinforms.Size(77, 13));
        lblTradeInLabel.setTabIndex(134);
        lblTradeInLabel.setText("Less Trade-In:");
        //
        // picCostsLine
        //
        picCostsLine.setBackColor(java.awt.Color.darkGray);
        picCostsLine.setLocation(new java.awt.Point(8, 80));
        picCostsLine.setName("picCostsLine");
        picCostsLine.setSize(new jwinforms.Size(168, 1));
        picCostsLine.setTabIndex(133);
        picCostsLine.setTabStop(false);
        //
        // lblPenalty
        //
        lblPenalty.setLocation(new java.awt.Point(106, 32));
        lblPenalty.setName("lblPenalty");
        lblPenalty.setSize(new jwinforms.Size(74, 16));
        lblPenalty.setTabIndex(21);
        lblPenalty.setText("8,888,888 cr.");
        lblPenalty.TextAlign = ContentAlignment.TopRight;
        //
        // lblPenaltyLabel
        //
        lblPenaltyLabel.setAutoSize(true);
        lblPenaltyLabel.setLocation(new java.awt.Point(8, 32));
        lblPenaltyLabel.setName("lblPenaltyLabel");
        lblPenaltyLabel.setSize(new jwinforms.Size(96, 13));
        lblPenaltyLabel.setTabIndex(20);
        lblPenaltyLabel.setText("Crowding Penalty:");
        //
        // lblShipCost
        //
        lblShipCost.setLocation(new java.awt.Point(106, 16));
        lblShipCost.setName("lblShipCost");
        lblShipCost.setSize(new jwinforms.Size(74, 16));
        lblShipCost.setTabIndex(19);
        lblShipCost.setText("8,888,888 cr.");
        lblShipCost.TextAlign = ContentAlignment.TopRight;
        //
        // lblTotalCost
        //
        lblTotalCost.setLocation(new java.awt.Point(106, 84));
        lblTotalCost.setName("lblTotalCost");
        lblTotalCost.setSize(new jwinforms.Size(74, 16));
        lblTotalCost.setTabIndex(18);
        lblTotalCost.setText("8,888,888 cr.");
        lblTotalCost.TextAlign = ContentAlignment.TopRight;
        //
        // lblTotalCostLabel
        //
        lblTotalCostLabel.setAutoSize(true);
        lblTotalCostLabel.setLocation(new java.awt.Point(8, 84));
        lblTotalCostLabel.setName("lblTotalCostLabel");
        lblTotalCostLabel.setSize(new jwinforms.Size(59, 13));
        lblTotalCostLabel.setTabIndex(17);
        lblTotalCostLabel.setText("Total Cost:");
        //
        // lblShipCostLabel
        //
        lblShipCostLabel.setAutoSize(true);
        lblShipCostLabel.setLocation(new java.awt.Point(8, 16));
        lblShipCostLabel.setName("lblShipCostLabel");
        lblShipCostLabel.setSize(new jwinforms.Size(56, 13));
        lblShipCostLabel.setTabIndex(16);
        lblShipCostLabel.setText("Ship Cost:");
        //
        // lblDesignFee
        //
        lblDesignFee.setLocation(new java.awt.Point(106, 48));
        lblDesignFee.setName("lblDesignFee");
        lblDesignFee.setSize(new jwinforms.Size(74, 16));
        lblDesignFee.setTabIndex(15);
        lblDesignFee.setText("888,888 cr.");
        lblDesignFee.TextAlign = ContentAlignment.TopRight;
        //
        // lblDesignFeeLabel
        //
        lblDesignFeeLabel.setAutoSize(true);
        lblDesignFeeLabel.setLocation(new java.awt.Point(8, 48));
        lblDesignFeeLabel.setName("lblDesignFeeLabel");
        lblDesignFeeLabel.setSize(new jwinforms.Size(65, 13));
        lblDesignFeeLabel.setTabIndex(14);
        lblDesignFeeLabel.setText("Design Fee:");
        //
        // btnConstruct
        //
        btnConstruct.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnConstruct.setForeColor(SystemColors.ControlText);
        btnConstruct.setLocation(new java.awt.Point(382, 344));
        btnConstruct.setName("btnConstruct");
        btnConstruct.setSize(new jwinforms.Size(88, 22));
        btnConstruct.setTabIndex(6);
        btnConstruct.setText("Construct Ship");
        btnConstruct.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnConstruct_Click(sender, e);
            }
        });
        btnConstruct.setMouseEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnConstruct_MouseEnter(sender, e);
            }
        });
        btnConstruct.setMouseLeave(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnConstruct_MouseLeave(sender, e);
            }
        });
        //
        // btnCancel
        //
        btnCancel.setDialogResult(DialogResult.Cancel);
        btnCancel.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnCancel.setLocation(new java.awt.Point(286, 344));
        btnCancel.setName("btnCancel");
        btnCancel.setSize(new jwinforms.Size(88, 22));
        btnCancel.setTabIndex(5);
        btnCancel.setText("Cancel Design");
        //
        // boxAllocation
        //
        boxAllocation.controls.addAll((new WinformControl[]{lblPct, lblPctLabel, numHullStrength,
                lblHullStrenghLabel, numCargoBays, numCrewQuarters, numFuelTanks, numShieldSlots, numGadgetSlots,
                numWeaponSlots, lblCargoBays, lblFuelTanks, lblCrewQuarters, lblShieldSlots, lblGadgetSlots,
                lblWeaponsSlots, lblUnitsUsedLabel, lblUnitsUsed}));
        boxAllocation.setLocation(new java.awt.Point(286, 0));
        boxAllocation.setName("boxAllocation");
        boxAllocation.setSize(new jwinforms.Size(184, 226));
        boxAllocation.setTabIndex(3);
        boxAllocation.setTabStop(false);
        boxAllocation.setText("Space Allocation");
        //
        // lblPct
        //
        lblPct.setFont(FontCollection.bold825);
        lblPct.setForeColor(java.awt.Color.red);
        lblPct.setLocation(new java.awt.Point(110, 204));
        lblPct.setName("lblPct");
        lblPct.setSize(new jwinforms.Size(34, 13));
        lblPct.setTabIndex(19);
        lblPct.setText("888%");
        lblPct.TextAlign = ContentAlignment.TopRight;
        //
        // lblPctLabel
        //
        lblPctLabel.setAutoSize(true);
        lblPctLabel.setLocation(new java.awt.Point(8, 204));
        lblPctLabel.setName("lblPctLabel");
        lblPctLabel.setSize(new jwinforms.Size(54, 13));
        lblPctLabel.setTabIndex(18);
        lblPctLabel.setText("% of Max:");
        //
        // numHullStrength
        //
        numHullStrength.setBackColor(java.awt.Color.white);
        numHullStrength.setLocation(new java.awt.Point(110, 64));
        numHullStrength.setMaximum(9999);
        numHullStrength.setName("numHullStrength");
        numHullStrength.setReadOnly(true);
        numHullStrength.setSize(new jwinforms.Size(64, 20));
        numHullStrength.setTabIndex(1);
        numHullStrength.TextAlign = jwinforms.HorizontalAlignment.Right;
        numHullStrength.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        numHullStrength.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // lblHullStrenghLabel
        //
        lblHullStrenghLabel.setAutoSize(true);
        lblHullStrenghLabel.setLocation(new java.awt.Point(8, 66));
        lblHullStrenghLabel.setName("lblHullStrenghLabel");
        lblHullStrenghLabel.setSize(new jwinforms.Size(70, 13));
        lblHullStrenghLabel.setTabIndex(13);
        lblHullStrenghLabel.setText("Hull Strengh:");
        //
        // numCargoBays
        //
        numCargoBays.setBackColor(java.awt.Color.white);
        numCargoBays.setLocation(new java.awt.Point(110, 16));
        numCargoBays.setMaximum(999);
        numCargoBays.setName("numCargoBays");
        numCargoBays.setReadOnly(true);
        numCargoBays.setSize(new jwinforms.Size(64, 20));
        numCargoBays.setTabIndex(3);
        numCargoBays.TextAlign = jwinforms.HorizontalAlignment.Right;
        numCargoBays.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        numCargoBays.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // numCrewQuarters
        //
        numCrewQuarters.setBackColor(java.awt.Color.white);
        numCrewQuarters.setLocation(new java.awt.Point(110, 160));
        numCrewQuarters.setMinimum(1);
        numCrewQuarters.setName("numCrewQuarters");
        numCrewQuarters.setReadOnly(true);
        numCrewQuarters.setSize(new jwinforms.Size(64, 20));
        numCrewQuarters.setTabIndex(4);
        numCrewQuarters.TextAlign = jwinforms.HorizontalAlignment.Right;
        numCrewQuarters.setValue(1);
        numCrewQuarters.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        numCrewQuarters.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // numFuelTanks
        //
        numFuelTanks.setBackColor(java.awt.Color.white);
        numFuelTanks.setLocation(new java.awt.Point(110, 40));
        numFuelTanks.setName("numFuelTanks");
        numFuelTanks.setReadOnly(true);
        numFuelTanks.setSize(new jwinforms.Size(64, 20));
        numFuelTanks.setTabIndex(2);
        numFuelTanks.TextAlign = jwinforms.HorizontalAlignment.Right;
        numFuelTanks.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        numFuelTanks.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // numShieldSlots
        //
        numShieldSlots.setBackColor(java.awt.Color.white);
        numShieldSlots.setLocation(new java.awt.Point(110, 112));
        numShieldSlots.setName("numShieldSlots");
        numShieldSlots.setReadOnly(true);
        numShieldSlots.setSize(new jwinforms.Size(64, 20));
        numShieldSlots.setTabIndex(6);
        numShieldSlots.TextAlign = jwinforms.HorizontalAlignment.Right;
        numShieldSlots.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        numShieldSlots.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // numGadgetSlots
        //
        numGadgetSlots.setBackColor(java.awt.Color.white);
        numGadgetSlots.setLocation(new java.awt.Point(110, 136));
        numGadgetSlots.setName("numGadgetSlots");
        numGadgetSlots.setReadOnly(true);
        numGadgetSlots.setSize(new jwinforms.Size(64, 20));
        numGadgetSlots.setTabIndex(7);
        numGadgetSlots.TextAlign = jwinforms.HorizontalAlignment.Right;
        numGadgetSlots.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        numGadgetSlots.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // numWeaponSlots
        //
        numWeaponSlots.setBackColor(java.awt.Color.white);
        numWeaponSlots.setLocation(new java.awt.Point(110, 88));
        numWeaponSlots.setName("numWeaponSlots");
        numWeaponSlots.setReadOnly(true);
        numWeaponSlots.setSize(new jwinforms.Size(64, 20));
        numWeaponSlots.setTabIndex(5);
        numWeaponSlots.TextAlign = jwinforms.HorizontalAlignment.Right;
        numWeaponSlots.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueEnter(sender, e);
            }
        });
        numWeaponSlots.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                num_ValueChanged(sender, e);
            }
        });
        //
        // lblCargoBays
        //
        lblCargoBays.setAutoSize(true);
        lblCargoBays.setLocation(new java.awt.Point(8, 18));
        lblCargoBays.setName("lblCargoBays");
        lblCargoBays.setSize(new jwinforms.Size(66, 13));
        lblCargoBays.setTabIndex(5);
        lblCargoBays.setText("Cargo Bays:");
        //
        // lblFuelTanks
        //
        lblFuelTanks.setAutoSize(true);
        lblFuelTanks.setLocation(new java.awt.Point(8, 42));
        lblFuelTanks.setName("lblFuelTanks");
        lblFuelTanks.setSize(new jwinforms.Size(41, 13));
        lblFuelTanks.setTabIndex(4);
        lblFuelTanks.setText("Range:");
        //
        // lblCrewQuarters
        //
        lblCrewQuarters.setAutoSize(true);
        lblCrewQuarters.setLocation(new java.awt.Point(8, 162));
        lblCrewQuarters.setName("lblCrewQuarters");
        lblCrewQuarters.setSize(new jwinforms.Size(81, 13));
        lblCrewQuarters.setTabIndex(3);
        lblCrewQuarters.setText("Crew Quarters:");
        //
        // lblShieldSlots
        //
        lblShieldSlots.setAutoSize(true);
        lblShieldSlots.setLocation(new java.awt.Point(8, 114));
        lblShieldSlots.setName("lblShieldSlots");
        lblShieldSlots.setSize(new jwinforms.Size(67, 13));
        lblShieldSlots.setTabIndex(2);
        lblShieldSlots.setText("Shield Slots:");
        //
        // lblGadgetSlots
        //
        lblGadgetSlots.setAutoSize(true);
        lblGadgetSlots.setLocation(new java.awt.Point(8, 138));
        lblGadgetSlots.setName("lblGadgetSlots");
        lblGadgetSlots.setSize(new jwinforms.Size(73, 13));
        lblGadgetSlots.setTabIndex(1);
        lblGadgetSlots.setText("Gadget Slots:");
        //
        // lblWeaponsSlots
        //
        lblWeaponsSlots.setAutoSize(true);
        lblWeaponsSlots.setLocation(new java.awt.Point(8, 90));
        lblWeaponsSlots.setName("lblWeaponsSlots");
        lblWeaponsSlots.setSize(new jwinforms.Size(78, 13));
        lblWeaponsSlots.setTabIndex(0);
        lblWeaponsSlots.setText("Weapon Slots:");
        //
        // ilShipyardLogos
        //
        ilShipyardLogos.ColorDepth = jwinforms.ColorDepth.Depth24Bit;
        ilShipyardLogos.setImageSize(new jwinforms.Size(80, 80));
        ilShipyardLogos.setImageStream(((jwinforms.ImageListStreamer) (resources
                .getObject("ilShipyardLogos.ImageStream"))));
        ilShipyardLogos.setTransparentColor(java.awt.Color.black);
        //
        // dlgOpen
        //
        dlgOpen.setFilter("Windows Bitmaps (*.bmp)|*bmp");
        dlgOpen.setTitle("Open Ship Image");
        //
        // lblDisabledPct
        //
        lblDisabledPct.setBackColor(SystemColors.Info);
        lblDisabledPct.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        lblDisabledPct.ImageAlign = ContentAlignment.MiddleRight;
        lblDisabledPct.setLocation(new java.awt.Point(154, 182));
        lblDisabledPct.setName("lblDisabledPct");
        lblDisabledPct.setSize(new jwinforms.Size(276, 20));
        lblDisabledPct.setTabIndex(8);
        lblDisabledPct.setText("Your % of Max must be less than or equal to 100%.");
        lblDisabledPct.TextAlign = ContentAlignment.MiddleCenter;
        lblDisabledPct.setVisible(false);
        //
        // dlgSave
        //
        dlgSave.setDefaultExt("sst");
        dlgSave.setFileName("CustomShip.sst");
        dlgSave.setFilter("SpaceTrader Ship Template Files (*.sst)|*.sst");
        dlgSave.setTitle("Save Ship Template");
        //
        // lblDisabledName
        //
        lblDisabledName.setBackColor(SystemColors.Info);
        lblDisabledName.setBorderStyle(jwinforms.BorderStyle.FixedSingle);
        lblDisabledName.ImageAlign = ContentAlignment.MiddleLeft;
        lblDisabledName.setLocation(new java.awt.Point(96, 222));
        lblDisabledName.setName("lblDisabledName");
        lblDisabledName.setSize(new jwinforms.Size(170, 20));
        lblDisabledName.setTabIndex(7);
        lblDisabledName.setText("You must enter a Ship Name.");
        lblDisabledName.TextAlign = ContentAlignment.MiddleRight;
        lblDisabledName.setVisible(false);
        //
        // Form_Shipyard
        //
        this.setAcceptButton(btnConstruct);
        this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setCancelButton(btnCancel);
        this.setClientSize(new jwinforms.Size(478, 375));
        Controls.addAll(Arrays.asList(lblDisabledPct, boxWelcome, lblDisabledName, boxAllocation, boxCosts, boxInfo,
                btnCancel, btnConstruct));
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("Form_Shipyard");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Ship Design at XXXX Shipyards");
        ((ISupportInitialize) (numHullStrength)).endInit();
        ((ISupportInitialize) (numCargoBays)).endInit();
        ((ISupportInitialize) (numCrewQuarters)).endInit();
        ((ISupportInitialize) (numFuelTanks)).endInit();
        ((ISupportInitialize) (numShieldSlots)).endInit();
        ((ISupportInitialize) (numGadgetSlots)).endInit();
        ((ISupportInitialize) (numWeaponSlots)).endInit();
    }

    //#endregion

    private boolean ConstructButtonEnabled() {
        return (shipyard.PercentOfMaxUnits() <= 100 && txtName.getText().length() > 0);
    }

    private Bitmap GetImageFile(String fileName) {
        try {
            return new Bitmap(fileName);
        } catch (Exception ex) {
            GuiFacade.alert(AlertType.FileErrorOpen, fileName, ex.getMessage());
            return null;
        }
    }

    private void LoadSelectedTemplate() {
        if (selTemplate.getSelectedItem() instanceof ShipTemplate) {
            loading = true;

            ShipTemplate template = (ShipTemplate) selTemplate.getSelectedItem();

            if (template.Name() == Strings.ShipNameCurrentShip)
                txtName.setText(game.Commander().getShip().Name());
            else if (template.Name().endsWith(Strings.ShipNameTemplateSuffixDefault)
                    || template.Name().endsWith(Strings.ShipNameTemplateSuffixMinimum))
                txtName.setText("");
            else
                txtName.setText(template.Name());

            selSize.setSelectedIndex(Math.max(0, sizes.indexOf(template.Size())));
            imgIndex = template.ImageIndex() == ShipType.Custom.castToInt() ? imgTypes.length - 1 : template
                    .ImageIndex();

            if (template.Images() != null)
                customImages = template.Images();
            else
                customImages = GuiEngine.imageProvider.getCustomShipImages();

            numCargoBays.setValue(template.CargoBays());
            numFuelTanks.setValue(Math.min(Math.max(numFuelTanks.getMinimum(), template.FuelTanks()), numFuelTanks
                    .getMaximum()));
            numHullStrength.setValue(Math.min(Math.max(numHullStrength.getMinimum(), template.HullStrength()),
                    numHullStrength.getMaximum()));
            numWeaponSlots.setValue(template.WeaponSlots());
            numShieldSlots.setValue(template.ShieldSlots());
            numGadgetSlots.setValue(template.GadgetSlots());
            numCrewQuarters.setValue(Math.max(numCrewQuarters.getMinimum(), template.CrewQuarters()));

            UpdateShip();
            UpdateCalculatedFigures();

            if (selTemplate.Items.get(0).toString() == Strings.ShipNameModified)
                selTemplate.Items.remove(0);

            loading = false;
        }
    }

    private void LoadSizes() {
        sizes = new ArrayList<Size>(6);

//			foreach (Size size in shipyard.AvailableSizes)
        for (Size size : shipyard.AvailableSizes()) {
            sizes.add(size);
            selSize.Items.add(Functions.StringVars(Strings.ShipyardSizeItem, Strings.Sizes[size.castToInt()], Functions
                    .Multiples(Shipyard.MAX_UNITS[size.castToInt()], Strings.ShipyardUnit)));
        }
    }

    private void LoadTemplateList() {
        ShipTemplate currentShip = new ShipTemplate(game.Commander().getShip(), Strings.ShipNameCurrentShip);
        selTemplate.Items.add(currentShip);

        selTemplate.Items.add(Consts.ShipTemplateSeparator);

        // Add the minimal sizes templates.
        for (Size size : sizes)
            selTemplate.Items.add(new ShipTemplate(size, Strings.Sizes[size.castToInt()]
                    + Strings.ShipNameTemplateSuffixMinimum));

        selTemplate.Items.add(Consts.ShipTemplateSeparator);

        // Add the buyable ship spec templates.
        for (ShipSpec spec : Consts.ShipSpecs) {
            if (sizes.contains(spec.getSize()) && spec.Type().castToInt() <= Consts.MaxShip)
                selTemplate.Items.add(new ShipTemplate(spec, spec.Name() + Strings.ShipNameTemplateSuffixDefault));
        }

        selTemplate.Items.add(Consts.ShipTemplateSeparator);

        // Add the user-created templates.
        ArrayList userTemplates = new ArrayList();
        for (String fileName : Directory.GetFiles(Consts.CustomTemplatesDirectory, "*.sst")) {
            ShipTemplate template = new ShipTemplate((Hashtable) Functions.loadFile(fileName, true));
            if (sizes.contains(template.Size()))
                userTemplates.add(template);
        }
        userTemplates.Sort();
        selTemplate.Items.AddRange(userTemplates.toArray(new ShipTemplate[0]));

        selTemplate.setSelectedIndex(0);
    }

    private boolean SaveButtonEnabled() {
        return (txtName.getText().length() > 0);
    }

    private void SetTemplateModified() {
        if (!loading && selTemplate.Items.getSize() > 0) {
            if (selTemplate.Items.get(0).toString() != Strings.ShipNameModified)
                selTemplate.Items.Insert(0, Strings.ShipNameModified);

            selTemplate.setSelectedIndex(0);
        }
    }

    private void UpdateAllocation() {
        boolean fuelMinimum = numFuelTanks.getValue() == numFuelTanks.getMinimum();
        boolean hullMinimum = numHullStrength.getValue() == numHullStrength.getMinimum();

        numFuelTanks.setMinimum(shipyard.BaseFuel());
        numFuelTanks.setIncrement(shipyard.PerUnitFuel());
        numFuelTanks.setMaximum(Consts.MaxFuelTanks);
        if (fuelMinimum)
            numFuelTanks.setValue(numFuelTanks.getMinimum());

        numHullStrength.setMinimum(shipyard.BaseHull());
        numHullStrength.setIncrement(shipyard.PerUnitHull());
        if (hullMinimum)
            numHullStrength.setValue(numHullStrength.getMinimum());

        numWeaponSlots.setMaximum(Consts.MaxSlots);
        numShieldSlots.setMaximum(Consts.MaxSlots);
        numGadgetSlots.setMaximum(Consts.MaxSlots);
        numCrewQuarters.setMaximum(Consts.MaxSlots);
    }

    private void UpdateCalculatedFigures() {
        // Fix the fuel value to be a multiple of the per unit value less the super.
        int extraFuel = numFuelTanks.getValue() - shipyard.BaseFuel();
        if (extraFuel % shipyard.PerUnitFuel() > 0 && numFuelTanks.getValue() < numFuelTanks.getMaximum())
            numFuelTanks.setValue(Math.max(numFuelTanks.getMinimum(), Math.min(numFuelTanks.getMaximum(),
                    (extraFuel + shipyard.PerUnitFuel()) / shipyard.PerUnitFuel() * shipyard.PerUnitFuel()
                            + shipyard.BaseFuel())));

        // Fix the hull value to be a multiple of the unit value value less the super.
        int extraHull = numHullStrength.getValue() - shipyard.BaseHull();
        if (extraHull % shipyard.PerUnitHull() > 0)
            numHullStrength.setValue(Math.max(numHullStrength.getMinimum(), (extraHull + shipyard.PerUnitHull())
                    / shipyard.PerUnitHull() * shipyard.PerUnitHull() + shipyard.BaseHull()));

        shipyard.ShipSpec().CargoBays(numCargoBays.getValue());
        shipyard.ShipSpec().FuelTanks(numFuelTanks.getValue());
        shipyard.ShipSpec().HullStrength(numHullStrength.getValue());
        shipyard.ShipSpec().setWeaponSlots(numWeaponSlots.getValue());
        shipyard.ShipSpec().setShieldSlots(numShieldSlots.getValue());
        shipyard.ShipSpec().setGadgetSlots(numGadgetSlots.getValue());
        shipyard.ShipSpec().setCrewQuarters(numCrewQuarters.getValue());

        shipyard.CalculateDependantVariables();

        lblUnitsUsed.setText(shipyard.UnitsUsed() + "");
        lblPct.setText(Functions.FormatPercent(shipyard.PercentOfMaxUnits()));
        if (shipyard.PercentOfMaxUnits() >= Shipyard.PENALTY_FIRST_PCT)
            lblPct.setFont(lblSkillLabel.getFont());
        else
            lblPct.setFont(lblPctLabel.getFont());
        if (shipyard.UnitsUsed() > shipyard.MaxUnits())
            lblPct.setForeColor(Color.red);
        else if (shipyard.PercentOfMaxUnits() >= Shipyard.PENALTY_SECOND_PCT)
            lblPct.setForeColor(Color.orange);
        else if (shipyard.PercentOfMaxUnits() >= Shipyard.PENALTY_FIRST_PCT)
            lblPct.setForeColor(Color.yellow);
        else
            lblPct.setForeColor(lblPctLabel.getForeColor());

        lblShipCost.setText(Functions.formatMoney(shipyard.AdjustedPrice()));
        lblDesignFee.setText(Functions.formatMoney(shipyard.AdjustedDesignFee()));
        lblPenalty.setText(Functions.formatMoney(shipyard.AdjustedPenaltyCost()));
        lblTradeIn.setText(Functions.formatMoney(-shipyard.TradeIn()));
        lblTotalCost.setText(Functions.formatMoney(shipyard.TotalCost()));

        UpdateButtonEnabledState();
    }

    private void UpdateButtonEnabledState() {
        btnConstruct.setForeColor(ConstructButtonEnabled() ? Color.black : Color.gray);
        btnSave.setForeColor(SaveButtonEnabled() ? Color.black : Color.gray);
    }

    private void UpdateShip() {
        shipyard.ShipSpec().ImageIndex(imgTypes[imgIndex].castToInt());
        picShip.setImage((imgIndex > Consts.MaxShip ? customImages[0]
                : Consts.ShipSpecs[imgTypes[imgIndex].castToInt()].Image()));
        lblImage.setText((imgIndex > Consts.MaxShip ? Strings.ShipNameCustomShip : Consts.ShipSpecs[imgTypes[imgIndex]
                .castToInt()].Name()));
    }

    //#endregion

    //#region Event Handlers

    private void btnConstruct_Click(Object sender, EventArgs e) {
        if (ConstructButtonEnabled()) {
            if (game.Commander().TradeShip(shipyard.ShipSpec(), shipyard.TotalCost(), txtName.getText())) {
                Strings.ShipNames[ShipType.Custom.castToInt()] = txtName.getText();

                if (game.getQuestStatusScarab() == SpecialEvent.StatusScarabDone)
                    game.setQuestStatusScarab(SpecialEvent.StatusScarabNotStarted);

                // Replace the current custom images with the new ones.
                if (game.Commander().getShip().ImageIndex() == ShipType.Custom.castToInt()) {
                    GuiEngine.imageProvider.setCustomShipImages(customImages);

                    game.Commander().getShip().UpdateCustomImageOffsetConstants();
                }

                GuiFacade.alert(AlertType.ShipDesignThanks, shipyard.Name());
                Close();
            }
        }
    }

    private void btnConstruct_MouseEnter(Object sender, EventArgs e) {
        lblDisabledName.setVisible(txtName.getText().length() == 0);
        lblDisabledPct.setVisible(shipyard.PercentOfMaxUnits() > 100);
    }

    private void btnConstruct_MouseLeave(Object sender, EventArgs e) {
        lblDisabledName.setVisible(false);
        lblDisabledPct.setVisible(false);
    }

    private void btnLoad_Click(Object sender, EventArgs e) {
        LoadSelectedTemplate();
    }

    private void btnNextImage_Click(Object sender, EventArgs e) {
        SetTemplateModified();
        imgIndex = (imgIndex + 1) % imgTypes.length;
        UpdateShip();
    }

    private void btnPrevImage_Click(Object sender, EventArgs e) {
        SetTemplateModified();
        imgIndex = (imgIndex + imgTypes.length - 1) % imgTypes.length;
        UpdateShip();
    }

    private void btnSave_Click(Object sender, EventArgs e) {
        if (SaveButtonEnabled()) {
            if (dlgSave.ShowDialog(this) == DialogResult.OK) {
                ShipTemplate template = new ShipTemplate(shipyard.ShipSpec(), txtName.getText());

                if (imgIndex > Consts.MaxShip) {
                    template.ImageIndex(ShipType.Custom.castToInt());
                    template.Images(customImages);
                } else
                    template.ImageIndex(imgIndex);

                Functions.saveFile(dlgSave.getFileName(), template.Serialize());

                LoadTemplateList();
            }
        }
    }

    private void btnSave_MouseEnter(Object sender, EventArgs e) {
        lblDisabledName.setVisible(txtName.getText().length() == 0);
    }

    private void btnSave_MouseLeave(Object sender, EventArgs e) {
        lblDisabledName.setVisible(false);
    }

    private void btnSetCustomImage_Click(Object sender, EventArgs e) {
        if (dlgOpen.ShowDialog(this) == DialogResult.OK) {
            String baseFileName = Path.RemoveExtension(dlgOpen.getFileName());
            String ext = Path.GetExtension(dlgOpen.getFileName());

            Bitmap image = GetImageFile(baseFileName + ext);
            Bitmap imageDamaged = GetImageFile(baseFileName + "d" + ext);
            Bitmap imageShields = GetImageFile(baseFileName + "s" + ext);
            Bitmap imageShieldsDamaged = GetImageFile(baseFileName + "sd" + ext);

            if (image != null && imageDamaged != null && imageShields != null && imageShieldsDamaged != null) {
                customImages[Consts.ShipImgOffsetNormal] = image;
                customImages[Consts.ShipImgOffsetDamage] = imageDamaged;
                customImages[Consts.ShipImgOffsetShield] = imageShields;
                customImages[Consts.ShipImgOffsetSheildDamage] = imageShieldsDamaged;
            }

            imgIndex = imgTypes.length - 1;
            UpdateShip();
        }
    }

    private void num_ValueChanged(Object sender, EventArgs e) {
        SetTemplateModified();
        UpdateCalculatedFigures();
    }

    private void num_ValueEnter(Object sender, EventArgs e) {
        ((NumericUpDown) sender).Select(0, ("" + ((NumericUpDown) sender).getValue()).length());
    }

    private void selSize_SelectedIndexChanged(Object sender, EventArgs e) {
        SetTemplateModified();
        shipyard.ShipSpec().setSize(sizes.get(selSize.getSelectedIndex()));
        UpdateAllocation();
        UpdateCalculatedFigures();
    }

    private void txtName_TextChanged(Object sender, EventArgs e) {
        SetTemplateModified();
        UpdateButtonEnabledState();
    }

    //#endregion
}
