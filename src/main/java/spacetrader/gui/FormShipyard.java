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

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Container;
import spacetrader.controls.Image;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.Difficulty;
import spacetrader.game.enums.ShipType;
import spacetrader.game.enums.ShipyardId;
import spacetrader.gui.debug.Launcher;
import spacetrader.guifacade.GuiEngine;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;
import spacetrader.stub.Directory;
import spacetrader.util.Hashtable;
import spacetrader.util.Path;
import spacetrader.util.ReflectionUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

class FormShipyard extends SpaceTraderForm {

    private final Game game = Game.getCurrentGame();
    private final Shipyard shipyard = Game.getCurrentGame().getCommander().getCurrentSystem().getShipyard();

    private final ShipType[] imgTypes = new ShipType[]{ShipType.FLEA, ShipType.GNAT, ShipType.FIREFLY,
            ShipType.MOSQUITO, ShipType.BUMBLEBEE, ShipType.BEETLE, ShipType.HORNET, ShipType.GRASSHOPPER,
            ShipType.TERMITE, ShipType.WASP, ShipType.CUSTOM};

    private IContainer components = new Container();

    private Label lblWelcome = new Label();
    private TextBox txtName = new TextBox();
    private Label lblName = new Label();
    private PictureBox picShip = new PictureBox();
    private Label lblDesignFee = new Label();
    private Button btnConstruct = new Button();
    private Button btnCancel = new Button();
    private PictureBox picLogo = new PictureBox();
    private Panel boxCosts = new Panel();
    private Panel boxAllocation = new Panel();
    private NumericUpDown numHullStrength = new NumericUpDown();
    private Label lblHullStrenghLabel = new Label();
    private NumericUpDown numCargoBays = new NumericUpDown();
    private NumericUpDown numCrewQuarters = new NumericUpDown();
    private NumericUpDown numFuelTanks = new NumericUpDown();
    private NumericUpDown numShieldSlots = new NumericUpDown();
    private NumericUpDown numGadgetSlots = new NumericUpDown();
    private NumericUpDown numWeaponSlots = new NumericUpDown();
    private Label lblCargoBays = new Label();
    private Label lblFuelTanks = new Label();
    private Label lblCrewQuarters = new Label();
    private Label lblShieldSlots = new Label();
    private Label lblGadgetSlots = new Label();
    private Label lblWeaponsSlots = new Label();
    private Label lblShipCost = new Label();
    private Label lblTotalCost = new Label();
    private Label lblTotalCostLabel = new Label();
    private Label lblShipCostLabel = new Label();
    private Label lblDesignFeeLabel = new Label();
    private Panel boxWelcome = new Panel();
    private Panel boxInfo = new Panel();
    private Label lblSize = new Label();
    private ComboBox<String> selSize = new ComboBox<>();
    private Label lblTemplate = new Label();
    private ComboBox<Object> selTemplate = new ComboBox<>();
    private Button btnSetCustomImage = new Button();
    private Label lblImageLabel = new Label();
    private Button btnNextImage = new Button();
    private Button btnPrevImage = new Button();
    private Label lblImage = new Label();
    private Label lblUnitsUsedLabel = new Label();
    private PictureBox picInfoLine = new PictureBox();
    private Label lblPctLabel = new Label();
    private Label lblPct = new Label();
    private Label lblPenaltyLabel = new Label();
    private Label lblPenalty = new Label();
    private PictureBox picCostsLine = new PictureBox();
    private Label lblSizeSpecialtyLabel = new Label();
    private Label lblSkillLabel = new Label();
    private Label lblSizeSpecialty = new Label();
    private Label lblSkill = new Label();
    private Label lblSkillDescription = new Label();
    private Label lblWarning = new Label();
    private Button btnLoad = new Button();
    private Button btnSave = new Button();
    private Label lblTradeInLabel = new Label();
    private Label lblTradeIn = new Label();
    private Label lblUnitsUsed = new Label();
    private Label lblDisabledPct = new Label();
    private Label lblDisabledName = new Label();

    private OpenFileDialog dlgOpen = new OpenFileDialog();
    private SaveFileDialog dlgSave = new SaveFileDialog();

    private Image[] customImages = new Image[Consts.ImagesPerShip];
    private ImageList ilShipyardLogos = new ImageList(components);

    private int imgIndex = 0;
    private boolean loading = false;
    private ArrayList<spacetrader.game.enums.Size> sizes = null;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        //SpaceTrader spaceTrader = new SpaceTrader(args.length > 0 ? args[0] : null);
        GlobalAssets.initializeImages();
        GuiEngine.installImplementation(new OriginalGuiImplementationProvider());
        new Game("name", Difficulty.BEGINNER, 8, 8, 8, 8, null);
        Game.getCurrentGame().getCommander().getCurrentSystem().setShipyardId(ShipyardId.CORELLIAN);

        Launcher.runForm(new FormShipyard());
    }

    FormShipyard() {
        initializeComponent();

        this.setText(Functions.stringVars(Strings.ShipyardTitle, shipyard.getName()));
        picLogo.setImage(ilShipyardLogos.getImages()[shipyard.getId().castToInt()]);
        lblWelcome.setText(Functions.stringVars(Strings.ShipyardWelcome, shipyard.getName(), shipyard.getEngineer()));
        lblSizeSpecialty.setText(Strings.Sizes[shipyard.getSpecialtySize().castToInt()]);
        lblSkill.setText(Strings.ShipyardSkills[shipyard.getSkill().castToInt()]);
        lblSkillDescription.setText(Strings.ShipyardSkillDescriptions[shipyard.getSkill().castToInt()]);
        lblWarning.setText(Functions.stringVars(Strings.ShipyardWarning, Integer.toString(Shipyard.PENALTY_FIRST_PCT),
                Integer.toString(Shipyard.PENALTY_SECOND_PCT)));

        dlgOpen.setInitialDirectory(Consts.CustomImagesDirectory);
        dlgSave.setInitialDirectory(Consts.CustomTemplatesDirectory);
        lblDisabledName.setImage(GuiEngine.getImageProvider().getDirectionImages()[Consts.DirectionDown]);
        lblDisabledPct.setImage(GuiEngine.getImageProvider().getDirectionImages()[Consts.DirectionDown]);

        loadSizes();
        loadTemplateList();
        loadSelectedTemplate();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formShipyard");
        setText("Ship Design at XXXX Shipyards");

        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setAutoScaleBaseSize(new Size(5, 13));
        setClientSize(new Size(478, 375));
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(btnConstruct);
        setCancelButton(btnCancel);

        ResourceManager resources = new ResourceManager(FormShipyard.class);

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
        boxWelcome.getControls().addAll(lblSkillDescription, lblSkill, lblSizeSpecialty,
                lblSkillLabel, lblSizeSpecialtyLabel, lblWarning, picLogo, lblWelcome);
        boxWelcome.setLocation(new Point(8, 0));
        boxWelcome.setSize(new Size(270, 204));
        boxWelcome.setTabIndex(1);
        boxWelcome.setTabStop(false);
        //
        // lblSkillDescription
        //
        lblSkillDescription.setLocation(new Point(8, 98));
        lblSkillDescription.setSize(new Size(258, 26));
        lblSkillDescription.setTabIndex(27);
        lblSkillDescription.setText("All ships finalructed at this shipyard use 2 fewer units per crew quarter.");
        //
        // lblSkill
        //
        lblSkill.setLocation(new Point(180, 79));
        lblSkill.setSize(new Size(87, 13));
        lblSkill.setTabIndex(26);
        lblSkill.setText("Crew Quartering");
        //
        // lblSizeSpecialty
        //
        lblSizeSpecialty.setLocation(new Point(180, 65));
        lblSizeSpecialty.setSize(new Size(64, 13));
        lblSizeSpecialty.setTabIndex(25);
        lblSizeSpecialty.setText("Gargantuan");
        //
        // lblSkillLabel
        //
        lblSkillLabel.setAutoSize(true);
        lblSkillLabel.setFont(FontCollection.bold825);
        lblSkillLabel.setLocation(new Point(92, 79));
        lblSkillLabel.setSize(new Size(72, 13));
        lblSkillLabel.setTabIndex(24);
        lblSkillLabel.setText("Special Skill:");
        //
        // lblSizeSpecialtyLabel
        //
        lblSizeSpecialtyLabel.setAutoSize(true);
        lblSizeSpecialtyLabel.setFont(FontCollection.bold825);
        lblSizeSpecialtyLabel.setLocation(new Point(92, 65));
        lblSizeSpecialtyLabel.setSize(new Size(82, 13));
        lblSizeSpecialtyLabel.setTabIndex(23);
        lblSizeSpecialtyLabel.setText("Size Specialty:");
        //
        // lblWelcome
        //
        lblWelcome.setLocation(new Point(92, 12));
        lblWelcome.setSize(new Size(176, 52));
        lblWelcome.setTabIndex(3);
        lblWelcome.setText("Welcome to Sorosuub Engineering Shipyards! Our best engineer, Obi-Wan, is at your"
                + " service.");
        //
        // lblWarning
        //
        lblWarning.setLocation(new Point(8, 134));
        lblWarning.setSize(new Size(258, 65));
        lblWarning.setTabIndex(5);
        lblWarning.setText("Bear in mind that getting too close to the maximum number of units will result in"
                + " a \"Crowding Penalty\" due to the engineering difficulty of squeezing everything "
                + "in.  There is a modest penalty at 80%, and a more severe one at 90%.");
        //
        // picLogo
        //
        picLogo.setBackground(Color.BLACK);
        picLogo.setLocation(new Point(8, 12));
        picLogo.setSize(new Size(80, 80));
        picLogo.sizeMode = PictureBoxSizeMode.StretchImage;
        picLogo.setTabIndex(22);
        picLogo.setTabStop(false);
        //
        // boxInfo
        //
        boxInfo.getControls().addAll((new BaseComponent[]{btnSave, btnLoad, picInfoLine, btnPrevImage, btnNextImage,
                lblImage, lblImageLabel, selTemplate, lblTemplate, selSize, lblSize, btnSetCustomImage, picShip,
                txtName, lblName}));
        boxInfo.setLocation(new Point(8, 208));
        boxInfo.setSize(new Size(270, 160));
        boxInfo.setTabIndex(2);
        boxInfo.setTabStop(false);
        boxInfo.setText("Info");
        //
        // btnSave
        //
        btnSave.setFlatStyle(FlatStyle.FLAT);
        btnSave.setLocation(new Point(216, 40));
        btnSave.setSize(new Size(44, 20));
        btnSave.setTabIndex(4);
        btnSave.setText("Save");
        btnSave.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnSave_Click();
            }
        });
        btnSave.setMouseEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnSave_MouseEnter();
            }
        });
        btnSave.setMouseLeave(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnSave_MouseLeave();
            }
        });
        //
        // btnLoad
        //
        btnLoad.setFlatStyle(FlatStyle.FLAT);
        btnLoad.setLocation(new Point(216, 16));
        btnLoad.setSize(new Size(44, 20));
        btnLoad.setTabIndex(2);
        btnLoad.setText("Load");
        btnLoad.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnLoad_Click();
            }
        });
        //
        // picInfoLine
        //
        picInfoLine.setBackground(Color.darkGray);
        picInfoLine.setLocation(new Point(8, 89));
        picInfoLine.setSize(new Size(254, 1));
        picInfoLine.setTabIndex(132);
        picInfoLine.setTabStop(false);
        //
        // btnPrevImage
        //
        btnPrevImage.setFlatStyle(FlatStyle.FLAT);
        btnPrevImage.setLocation(new Point(154, 95));
        btnPrevImage.setSize(new Size(18, 18));
        btnPrevImage.setTabIndex(6);
        btnPrevImage.setText("<");
        btnPrevImage.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnPrevImage_Click();
            }
        });
        //
        // btnNextImage
        //
        btnNextImage.setFlatStyle(FlatStyle.FLAT);
        btnNextImage.setLocation(new Point(242, 95));
        btnNextImage.setSize(new Size(18, 18));
        btnNextImage.setTabIndex(7);
        btnNextImage.setText(">");
        btnNextImage.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnNextImage_Click();
            }
        });
        //
        // lblImage
        //
        lblImage.setLocation(new Point(174, 98));
        lblImage.setSize(new Size(70, 13));
        lblImage.setTabIndex(61);
        lblImage.setText("Custom Ship");
        lblImage.setTextAlign(ContentAlignment.TOP_CENTER);
        //
        // lblImageLabel
        //
        lblImageLabel.setAutoSize(true);
        lblImageLabel.setLocation(new Point(8, 95));
        lblImageLabel.setSize(new Size(39, 13));
        lblImageLabel.setTabIndex(22);
        lblImageLabel.setText("Image:");
        //
        // selTemplate
        //
        selTemplate.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        selTemplate.setLocation(new Point(80, 16));
        selTemplate.setSize(new Size(132, 21));
        selTemplate.setTabIndex(1);
        //
        // lblTemplate
        //
        lblTemplate.setAutoSize(true);
        lblTemplate.setLocation(new Point(8, 19));
        lblTemplate.setSize(new Size(55, 13));
        lblTemplate.setTabIndex(20);
        lblTemplate.setText("Template:");
        //
        // selSize
        //
        selSize.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        selSize.setLocation(new Point(80, 63));
        selSize.setSize(new Size(180, 21));
        selSize.setTabIndex(5);
        selSize.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                selSize_SelectedIndexChanged();
            }
        });
        //
        // lblSize
        //
        lblSize.setAutoSize(true);
        lblSize.setLocation(new Point(8, 66));
        lblSize.setSize(new Size(29, 13));
        lblSize.setTabIndex(18);
        lblSize.setText("Size:");
        //
        // btnSetCustomImage
        //
        btnSetCustomImage.setFlatStyle(FlatStyle.FLAT);
        btnSetCustomImage.setLocation(new Point(154, 121));
        btnSetCustomImage.setSize(new Size(106, 22));
        btnSetCustomImage.setTabIndex(8);
        btnSetCustomImage.setText("Set Custom...");
        btnSetCustomImage.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnSetCustomImage_Click();
            }
        });
        //
        // picShip
        //
        picShip.setBackground(Color.white);
        picShip.setBorderStyle(BorderStyle.FIXED_SINGLE);
        picShip.setLocation(new Point(80, 95));
        picShip.setSize(new Size(66, 54));
        picShip.setTabIndex(14);
        picShip.setTabStop(false);
        //
        // txtName
        //
        txtName.setLocation(new Point(80, 40));
        txtName.setSize(new Size(132, 20));
        txtName.setTabIndex(3);
        txtName.setText("");
        txtName.setTextChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                txtName_TextChanged();
            }
        });
        //
        // lblName
        //
        lblName.setAutoSize(true);
        lblName.setLocation(new Point(8, 44));
        lblName.setSize(new Size(63, 13));
        lblName.setTabIndex(5);
        lblName.setText("Ship Name:");
        //
        // lblUnitsUsed
        //
        lblUnitsUsed.setLocation(new Point(110, 186));
        lblUnitsUsed.setSize(new Size(23, 13));
        lblUnitsUsed.setTabIndex(17);
        lblUnitsUsed.setText("888");
        lblUnitsUsed.setTextAlign(ContentAlignment.TOP_RIGHT);
        //
        // lblUnitsUsedLabel
        //
        lblUnitsUsedLabel.setAutoSize(true);
        lblUnitsUsedLabel.setLocation(new Point(8, 186));
        lblUnitsUsedLabel.setSize(new Size(63, 13));
        lblUnitsUsedLabel.setTabIndex(16);
        lblUnitsUsedLabel.setText("Units Used:");
        //
        // boxCosts
        //
        boxCosts.getControls().addAll((new BaseComponent[]{lblTradeIn, lblTradeInLabel, picCostsLine, lblPenalty,
                lblPenaltyLabel, lblShipCost, lblTotalCost, lblTotalCostLabel, lblShipCostLabel, lblDesignFee,
                lblDesignFeeLabel}));
        boxCosts.setLocation(new Point(286, 230));
        boxCosts.setSize(new Size(184, 106));
        boxCosts.setTabIndex(4);
        boxCosts.setTabStop(false);
        boxCosts.setText("Costs");
        //
        // lblTradeIn
        //
        lblTradeIn.setLocation(new Point(106, 64));
        lblTradeIn.setSize(new Size(75, 16));
        lblTradeIn.setTabIndex(135);
        lblTradeIn.setText("-8,888,888 cr.");
        lblTradeIn.setTextAlign(ContentAlignment.TOP_RIGHT);
        //
        // lblTradeInLabel
        //
        lblTradeInLabel.setAutoSize(true);
        lblTradeInLabel.setLocation(new Point(8, 64));
        lblTradeInLabel.setSize(new Size(77, 13));
        lblTradeInLabel.setTabIndex(134);
        lblTradeInLabel.setText("Less Trade-In:");
        //
        // picCostsLine
        //
        picCostsLine.setBackground(Color.darkGray);
        picCostsLine.setLocation(new Point(8, 80));
        picCostsLine.setSize(new Size(168, 1));
        picCostsLine.setTabIndex(133);
        picCostsLine.setTabStop(false);
        //
        // lblPenalty
        //
        lblPenalty.setLocation(new Point(106, 32));
        lblPenalty.setSize(new Size(74, 16));
        lblPenalty.setTabIndex(21);
        lblPenalty.setText("8,888,888 cr.");
        lblPenalty.setTextAlign(ContentAlignment.TOP_RIGHT);
        //
        // lblPenaltyLabel
        //
        lblPenaltyLabel.setAutoSize(true);
        lblPenaltyLabel.setLocation(new Point(8, 32));
        lblPenaltyLabel.setSize(new Size(96, 13));
        lblPenaltyLabel.setTabIndex(20);
        lblPenaltyLabel.setText("Crowding Penalty:");
        //
        // lblShipCost
        //
        lblShipCost.setLocation(new Point(106, 16));
        lblShipCost.setSize(new Size(74, 16));
        lblShipCost.setTabIndex(19);
        lblShipCost.setText("8,888,888 cr.");
        lblShipCost.setTextAlign(ContentAlignment.TOP_RIGHT);
        //
        // lblTotalCost
        //
        lblTotalCost.setLocation(new Point(106, 84));
        lblTotalCost.setSize(new Size(74, 16));
        lblTotalCost.setTabIndex(18);
        lblTotalCost.setText("8,888,888 cr.");
        lblTotalCost.setTextAlign(ContentAlignment.TOP_RIGHT);
        //
        // lblTotalCostLabel
        //
        lblTotalCostLabel.setAutoSize(true);
        lblTotalCostLabel.setLocation(new Point(8, 84));
        lblTotalCostLabel.setSize(new Size(59, 13));
        lblTotalCostLabel.setTabIndex(17);
        lblTotalCostLabel.setText("Total Cost:");
        //
        // lblShipCostLabel
        //
        lblShipCostLabel.setAutoSize(true);
        lblShipCostLabel.setLocation(new Point(8, 16));
        lblShipCostLabel.setSize(new Size(56, 13));
        lblShipCostLabel.setTabIndex(16);
        lblShipCostLabel.setText("Ship Cost:");
        //
        // lblDesignFee
        //
        lblDesignFee.setLocation(new Point(106, 48));
        lblDesignFee.setSize(new Size(74, 16));
        lblDesignFee.setTabIndex(15);
        lblDesignFee.setText("888,888 cr.");
        lblDesignFee.setTextAlign(ContentAlignment.TOP_RIGHT);
        //
        // lblDesignFeeLabel
        //
        lblDesignFeeLabel.setAutoSize(true);
        lblDesignFeeLabel.setLocation(new Point(8, 48));
        lblDesignFeeLabel.setSize(new Size(65, 13));
        lblDesignFeeLabel.setTabIndex(14);
        lblDesignFeeLabel.setText("Design Fee:");
        //
        // btnConstruct
        //
        btnConstruct.setFlatStyle(FlatStyle.FLAT);
        btnConstruct.setForeground(SystemColors.CONTROL_TEXT);
        btnConstruct.setLocation(new Point(382, 344));
        btnConstruct.setSize(new Size(88, 22));
        btnConstruct.setTabIndex(6);
        btnConstruct.setText("Construct Ship");
        btnConstruct.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnConstruct_Click();
            }
        });
        btnConstruct.setMouseEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnConstruct_MouseEnter();
            }
        });
        btnConstruct.setMouseLeave(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnConstruct_MouseLeave();
            }
        });
        //
        // btnCancel
        //
        btnCancel.setDialogResult(DialogResult.CANCEL);
        btnCancel.setFlatStyle(FlatStyle.FLAT);
        btnCancel.setLocation(new Point(286, 344));
        btnCancel.setSize(new Size(88, 22));
        btnCancel.setTabIndex(5);
        btnCancel.setText("Cancel Design");
        //
        // boxAllocation
        //
        boxAllocation.getControls().addAll((new BaseComponent[]{lblPct, lblPctLabel, numHullStrength,
                lblHullStrenghLabel, numCargoBays, numCrewQuarters, numFuelTanks, numShieldSlots, numGadgetSlots,
                numWeaponSlots, lblCargoBays, lblFuelTanks, lblCrewQuarters, lblShieldSlots, lblGadgetSlots,
                lblWeaponsSlots, lblUnitsUsedLabel, lblUnitsUsed}));
        boxAllocation.setLocation(new Point(286, 0));
        boxAllocation.setSize(new Size(184, 226));
        boxAllocation.setTabIndex(3);
        boxAllocation.setTabStop(false);
        boxAllocation.setText("Space Allocation");
        //
        // lblPct
        //
        lblPct.setFont(FontCollection.bold825);
        lblPct.setForeground(Color.red);
        lblPct.setLocation(new Point(110, 204));
        lblPct.setSize(new Size(34, 13));
        lblPct.setTabIndex(19);
        lblPct.setText("888%");
        lblPct.setTextAlign(ContentAlignment.TOP_RIGHT);
        //
        // lblPctLabel
        //
        lblPctLabel.setAutoSize(true);
        lblPctLabel.setLocation(new Point(8, 204));
        lblPctLabel.setSize(new Size(54, 13));
        lblPctLabel.setTabIndex(18);
        lblPctLabel.setText("% of Max:");
        //
        // numHullStrength
        //
        numHullStrength.setBackground(Color.white);
        numHullStrength.setLocation(new Point(110, 64));
        numHullStrength.setMaximum(9999);
        numHullStrength.setReadOnly(true);
        numHullStrength.setSize(new Size(64, 20));
        numHullStrength.setTabIndex(1);
        numHullStrength.setTextAlign(HorizontalAlignment.RIGHT);
        numHullStrength.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueEnter(sender);
            }
        });
        numHullStrength.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueChanged();
            }
        });
        //
        // lblHullStrenghLabel
        //
        lblHullStrenghLabel.setAutoSize(true);
        lblHullStrenghLabel.setLocation(new Point(8, 66));
        lblHullStrenghLabel.setSize(new Size(70, 13));
        lblHullStrenghLabel.setTabIndex(13);
        lblHullStrenghLabel.setText("Hull Strengh:");
        //
        // numCargoBays
        //
        numCargoBays.setBackground(Color.white);
        numCargoBays.setLocation(new Point(110, 16));
        numCargoBays.setMaximum(999);
        numCargoBays.setReadOnly(true);
        numCargoBays.setSize(new Size(64, 20));
        numCargoBays.setTabIndex(3);
        numCargoBays.setTextAlign(HorizontalAlignment.RIGHT);
        numCargoBays.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueEnter(sender);
            }
        });
        numCargoBays.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueChanged();
            }
        });
        //
        // numCrewQuarters
        //
        numCrewQuarters.setBackground(Color.white);
        numCrewQuarters.setLocation(new Point(110, 160));
        numCrewQuarters.setMinimum(1);
        numCrewQuarters.setReadOnly(true);
        numCrewQuarters.setSize(new Size(64, 20));
        numCrewQuarters.setTabIndex(4);
        numCrewQuarters.setTextAlign(HorizontalAlignment.RIGHT);
        numCrewQuarters.setValue(1);
        numCrewQuarters.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueEnter(sender);
            }
        });
        numCrewQuarters.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueChanged();
            }
        });
        //
        // numFuelTanks
        //
        numFuelTanks.setBackground(Color.white);
        numFuelTanks.setLocation(new Point(110, 40));
        numFuelTanks.setReadOnly(true);
        numFuelTanks.setSize(new Size(64, 20));
        numFuelTanks.setTabIndex(2);
        numFuelTanks.setTextAlign(HorizontalAlignment.RIGHT);
        numFuelTanks.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueEnter(sender);
            }
        });
        numFuelTanks.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueChanged();
            }
        });
        //
        // numShieldSlots
        //
        numShieldSlots.setBackground(Color.white);
        numShieldSlots.setLocation(new Point(110, 112));
        numShieldSlots.setReadOnly(true);
        numShieldSlots.setSize(new Size(64, 20));
        numShieldSlots.setTabIndex(6);
        numShieldSlots.setTextAlign(HorizontalAlignment.RIGHT);
        numShieldSlots.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueEnter(sender);
            }
        });
        numShieldSlots.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueChanged();
            }
        });
        //
        // numGadgetSlots
        //
        numGadgetSlots.setBackground(Color.white);
        numGadgetSlots.setLocation(new Point(110, 136));
        numGadgetSlots.setReadOnly(true);
        numGadgetSlots.setSize(new Size(64, 20));
        numGadgetSlots.setTabIndex(7);
        numGadgetSlots.setTextAlign(HorizontalAlignment.RIGHT);
        numGadgetSlots.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueEnter(sender);
            }
        });
        numGadgetSlots.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueChanged();
            }
        });
        //
        // numWeaponSlots
        //
        numWeaponSlots.setBackground(Color.white);
        numWeaponSlots.setLocation(new Point(110, 88));
        numWeaponSlots.setReadOnly(true);
        numWeaponSlots.setSize(new Size(64, 20));
        numWeaponSlots.setTabIndex(5);
        numWeaponSlots.setTextAlign(HorizontalAlignment.RIGHT);
        numWeaponSlots.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueEnter(sender);
            }
        });
        numWeaponSlots.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                num_ValueChanged();
            }
        });
        //
        // lblCargoBays
        //
        lblCargoBays.setAutoSize(true);
        lblCargoBays.setLocation(new Point(8, 18));
        lblCargoBays.setSize(new Size(66, 13));
        lblCargoBays.setTabIndex(5);
        lblCargoBays.setText("Cargo Bays:");
        //
        // lblFuelTanks
        //
        lblFuelTanks.setAutoSize(true);
        lblFuelTanks.setLocation(new Point(8, 42));
        lblFuelTanks.setSize(new Size(41, 13));
        lblFuelTanks.setTabIndex(4);
        lblFuelTanks.setText("Range:");
        //
        // lblCrewQuarters
        //
        lblCrewQuarters.setAutoSize(true);
        lblCrewQuarters.setLocation(new Point(8, 162));
        lblCrewQuarters.setSize(new Size(81, 13));
        lblCrewQuarters.setTabIndex(3);
        lblCrewQuarters.setText("Crew Quarters:");
        //
        // lblShieldSlots
        //
        lblShieldSlots.setAutoSize(true);
        lblShieldSlots.setLocation(new Point(8, 114));
        lblShieldSlots.setSize(new Size(67, 13));
        lblShieldSlots.setTabIndex(2);
        lblShieldSlots.setText("Shield Slots:");
        //
        // lblGadgetSlots
        //
        lblGadgetSlots.setAutoSize(true);
        lblGadgetSlots.setLocation(new Point(8, 138));
        lblGadgetSlots.setSize(new Size(73, 13));
        lblGadgetSlots.setTabIndex(1);
        lblGadgetSlots.setText("Gadget Slots:");
        //
        // lblWeaponsSlots
        //
        lblWeaponsSlots.setAutoSize(true);
        lblWeaponsSlots.setLocation(new Point(8, 90));
        lblWeaponsSlots.setSize(new Size(78, 13));
        lblWeaponsSlots.setTabIndex(0);
        lblWeaponsSlots.setText("Weapon Slots:");
        //
        // ilShipyardLogos
        //
        ilShipyardLogos.setColorDepth(ColorDepth.Depth24Bit);
        ilShipyardLogos.setImageSize(new Size(80, 80));
        ilShipyardLogos.setImageStream(((ImageListStreamer) (resources
                .getObject("ilShipyardLogos.ImageStream"))));
        ilShipyardLogos.setTransparentColor(Color.black);
        //
        // dlgOpen
        //
        dlgOpen.setFilter("Windows Bitmaps (*.bmp)|*bmp");
        dlgOpen.setTitle("Open Ship Image");
        //
        // lblDisabledPct
        //
        lblDisabledPct.setBackground(SystemColors.INFO);
        lblDisabledPct.setBorderStyle(BorderStyle.FIXED_SINGLE);
        lblDisabledPct.setTextAlign(ContentAlignment.MIDDLE_RIGHT);
        lblDisabledPct.setLocation(new Point(154, 182));
        lblDisabledPct.setSize(new Size(276, 20));
        lblDisabledPct.setTabIndex(8);
        lblDisabledPct.setText("Your % of Max must be less than or equal to 100%.");
        lblDisabledPct.setTextAlign(ContentAlignment.MIDDLE_CENTER);
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
        lblDisabledName.setBackground(SystemColors.INFO);
        lblDisabledName.setBorderStyle(BorderStyle.FIXED_SINGLE);
        lblDisabledName.setTextAlign(ContentAlignment.MIDDLE_LEFT);
        lblDisabledName.setLocation(new Point(96, 222));
        lblDisabledName.setSize(new Size(170, 20));
        lblDisabledName.setTabIndex(7);
        lblDisabledName.setText("You must enter a Ship Name.");
        lblDisabledName.setTextAlign(ContentAlignment.MIDDLE_RIGHT);
        lblDisabledName.setVisible(false);

        controls.addAll(Arrays.asList(lblDisabledPct, boxWelcome, lblDisabledName, boxAllocation, boxCosts, boxInfo,
                btnCancel, btnConstruct));

        ((ISupportInitialize) (numHullStrength)).endInit();
        ((ISupportInitialize) (numCargoBays)).endInit();
        ((ISupportInitialize) (numCrewQuarters)).endInit();
        ((ISupportInitialize) (numFuelTanks)).endInit();
        ((ISupportInitialize) (numShieldSlots)).endInit();
        ((ISupportInitialize) (numGadgetSlots)).endInit();
        ((ISupportInitialize) (numWeaponSlots)).endInit();

        ReflectionUtils.loadControlsDimensions(this.asSwingObject(), this.getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(this.asSwingObject(), this.getName(), GlobalAssets.getStrings());
    }

    private boolean constructButtonEnabled() {
        return (shipyard.getPercentOfMaxUnits() <= 100 && txtName.getText().length() > 0);
    }

    private Bitmap getImageFile(String fileName) {
        try {
            return new Bitmap(fileName);
        } catch (Exception ex) {
            GuiFacade.alert(AlertType.FileErrorOpen, fileName, ex.getMessage());
            return null;
        }
    }

    private void loadSelectedTemplate() {
        if (selTemplate.getSelectedItem() instanceof ShipTemplate) {
            loading = true;

            ShipTemplate template = (ShipTemplate) selTemplate.getSelectedItem();

            if (template.getName().equals(Strings.ShipNameCurrentShip)) {
                txtName.setText(game.getCommander().getShip().getName());
            } else if (template.getName().endsWith(Strings.ShipNameTemplateSuffixDefault)
                    || template.getName().endsWith(Strings.ShipNameTemplateSuffixMinimum)) {
                txtName.setText("");
            } else {
                txtName.setText(template.getName());
            }

            selSize.setSelectedIndex(Math.max(0, sizes.indexOf(template.getSize())));
            imgIndex = (template.getImageIndex() == ShipType.CUSTOM.castToInt())
                    ? imgTypes.length - 1 : template.getImageIndex();

            if (template.getImages() != null) {
                customImages = template.getImages();
            } else {
                customImages = GuiEngine.getImageProvider().getCustomShipImages();
            }

            numCargoBays.setValue(template.getCargoBays());
            numFuelTanks.setValue(Math.min(Math.max(numFuelTanks.getMinimum(), template.getFuelTanks()), numFuelTanks
                    .getMaximum()));
            numHullStrength.setValue(Math.min(Math.max(numHullStrength.getMinimum(), template.getHullStrength()),
                    numHullStrength.getMaximum()));
            numWeaponSlots.setValue(template.getWeaponSlots());
            numShieldSlots.setValue(template.getShieldSlots());
            numGadgetSlots.setValue(template.getGadgetSlots());
            numCrewQuarters.setValue(Math.max(numCrewQuarters.getMinimum(), template.getCrewQuarters()));

            updateShip();
            updateCalculatedFigures();

            if (selTemplate.getItems().get(0).toString().equals(Strings.ShipNameModified)) {
                selTemplate.getItems().remove(0);
            }

            loading = false;
        }
    }

    private void loadSizes() {
        sizes = new ArrayList<>(6);

        for (spacetrader.game.enums.Size size : shipyard.getAvailableSizes()) {
            sizes.add(size);
            selSize.getItems().add(Functions.stringVars(Strings.ShipyardSizeItem, Strings.Sizes[size.castToInt()],
                    Functions.multiples(Shipyard.MAX_UNITS[size.castToInt()], Strings.ShipyardUnit)));
        }
    }

    private void loadTemplateList() {
        ShipTemplate currentShip = new ShipTemplate(game.getCommander().getShip(), Strings.ShipNameCurrentShip);
        selTemplate.getItems().add(currentShip);

        selTemplate.getItems().add(Consts.ShipTemplateSeparator);

        // Add the minimal sizes templates.
        for (spacetrader.game.enums.Size size : sizes)
            selTemplate.getItems().add(new ShipTemplate(size, Strings.Sizes[size.castToInt()]
                    + Strings.ShipNameTemplateSuffixMinimum));

        selTemplate.getItems().add(Consts.ShipTemplateSeparator);

        // Add the buyable ship spec templates.
        for (ShipSpec spec : Consts.ShipSpecs) {
            if (sizes.contains(spec.getSize()) && spec.getType().castToInt() <= Consts.MaxShip) {
                selTemplate.getItems().add(new ShipTemplate(spec, spec.getName() + Strings.ShipNameTemplateSuffixDefault));
            }
        }

        selTemplate.getItems().add(Consts.ShipTemplateSeparator);

        // Add the user-created templates.
        ArrayList<ShipTemplate> userTemplates = new ArrayList<>();
        for (String fileName : Directory.getFiles(Consts.CustomTemplatesDirectory, "*.sst")) {
            ShipTemplate template = new ShipTemplate((Hashtable) Functions.loadFile(fileName, true));
            if (sizes.contains(template.getSize())) {
                userTemplates.add(template);
            }
        }
        userTemplates.sort();
        selTemplate.getItems().addRange(userTemplates.toArray(new ShipTemplate[0]));

        selTemplate.setSelectedIndex(0);
    }

    private boolean isSaveButtonEnabled() {
        return txtName.getText().length() > 0;
    }

    private void setTemplateModified() {
        if (!loading && selTemplate.getItems().getSize() > 0) {
            if (!selTemplate.getItems().get(0).toString().equals(Strings.ShipNameModified)) {
                selTemplate.getItems().insert(0, Strings.ShipNameModified);
            }
            selTemplate.setSelectedIndex(0);
        }
    }

    private void updateAllocation() {
        boolean fuelMinimum = numFuelTanks.getValue() == numFuelTanks.getMinimum();
        boolean hullMinimum = numHullStrength.getValue() == numHullStrength.getMinimum();

        numFuelTanks.setMinimum(shipyard.getBaseFuel());
        numFuelTanks.setIncrement(shipyard.getPerUnitFuel());
        numFuelTanks.setMaximum(Consts.MaxFuelTanks);
        if (fuelMinimum) {
            numFuelTanks.setValue(numFuelTanks.getMinimum());
        }

        numHullStrength.setMinimum(shipyard.getBaseHull());
        numHullStrength.setIncrement(shipyard.getPerUnitHull());
        if (hullMinimum) {
            numHullStrength.setValue(numHullStrength.getMinimum());
        }

        numWeaponSlots.setMaximum(Consts.MaxSlots);
        numShieldSlots.setMaximum(Consts.MaxSlots);
        numGadgetSlots.setMaximum(Consts.MaxSlots);
        numCrewQuarters.setMaximum(Consts.MaxSlots);
    }

    private void updateCalculatedFigures() {
        // Fix the fuel value to be a multiple of the per unit value less the super.
        int extraFuel = numFuelTanks.getValue() - shipyard.getBaseFuel();
        if (extraFuel % shipyard.getPerUnitFuel() > 0 && numFuelTanks.getValue() < numFuelTanks.getMaximum()) {
            numFuelTanks.setValue(Math.max(numFuelTanks.getMinimum(), Math.min(numFuelTanks.getMaximum(),
                    (extraFuel + shipyard.getPerUnitFuel()) / shipyard.getPerUnitFuel() * shipyard.getPerUnitFuel()
                            + shipyard.getBaseFuel())));
        }

        // Fix the hull value to be a multiple of the unit value value less the super.
        int extraHull = numHullStrength.getValue() - shipyard.getBaseHull();
        if (extraHull % shipyard.getPerUnitHull() > 0) {
            numHullStrength.setValue(Math.max(numHullStrength.getMinimum(), (extraHull + shipyard.getPerUnitHull())
                    / shipyard.getPerUnitHull() * shipyard.getPerUnitHull() + shipyard.getBaseHull()));
        }

        shipyard.getShipSpec().setCargoBays(numCargoBays.getValue());
        shipyard.getShipSpec().setFuelTanks(numFuelTanks.getValue());
        shipyard.getShipSpec().setHullStrength(numHullStrength.getValue());
        shipyard.getShipSpec().setWeaponSlots(numWeaponSlots.getValue());
        shipyard.getShipSpec().setShieldSlots(numShieldSlots.getValue());
        shipyard.getShipSpec().setGadgetSlots(numGadgetSlots.getValue());
        shipyard.getShipSpec().setCrewQuarters(numCrewQuarters.getValue());

        shipyard.calculateDependantVariables();

        lblUnitsUsed.setText(shipyard.getUnitsUsed());
        lblPct.setText(Functions.formatPercent(shipyard.getPercentOfMaxUnits()));
        if (shipyard.getPercentOfMaxUnits() >= Shipyard.PENALTY_FIRST_PCT) {
            lblPct.setFont(lblSkillLabel.getFont());
        } else {
            lblPct.setFont(lblPctLabel.getFont());
        }
        if (shipyard.getUnitsUsed() > shipyard.getMaxUnits()) {
            lblPct.setForeground(Color.RED);
        } else if (shipyard.getPercentOfMaxUnits() >= Shipyard.PENALTY_SECOND_PCT) {
            lblPct.setForeground(Color.ORANGE);
        } else if (shipyard.getPercentOfMaxUnits() >= Shipyard.PENALTY_FIRST_PCT) {
            lblPct.setForeground(Color.YELLOW);
        } else {
            lblPct.setForeground(lblPctLabel.getForeground());
        }

        lblShipCost.setText(Functions.formatMoney(shipyard.getAdjustedPrice()));
        lblDesignFee.setText(Functions.formatMoney(shipyard.getAdjustedDesignFee()));
        lblPenalty.setText(Functions.formatMoney(shipyard.getAdjustedPenaltyCost()));
        lblTradeIn.setText(Functions.formatMoney(-shipyard.getTradeIn()));
        lblTotalCost.setText(Functions.formatMoney(shipyard.getTotalCost()));

        updateButtonEnabledState();
    }

    private void updateButtonEnabledState() {
        btnConstruct.setForeground(constructButtonEnabled() ? Color.BLACK : Color.GRAY);
        btnSave.setForeground(isSaveButtonEnabled() ? Color.BLACK : Color.GRAY);
    }

    private void updateShip() {
        shipyard.getShipSpec().setImageIndex(imgTypes[imgIndex].castToInt());
        picShip.setImage((imgIndex > Consts.MaxShip
                ? customImages[0] : Consts.ShipSpecs[imgTypes[imgIndex].castToInt()].getImage()));
        lblImage.setText((imgIndex > Consts.MaxShip
                ? Strings.ShipNameCustomShip : Consts.ShipSpecs[imgTypes[imgIndex].castToInt()].getName()));
    }

    private void btnConstruct_Click() {
        if (constructButtonEnabled()) {
            if (game.getCommander().isTradeShip(shipyard.getShipSpec(), shipyard.getTotalCost(), txtName.getText())) {
                Strings.ShipNames[ShipType.CUSTOM.castToInt()] = txtName.getText();

                if (game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_DONE) {
                    game.setQuestStatusScarab(SpecialEvent.STATUS_SCARAB_NOT_STARTED);
                }

                // Replace the current custom images with the new ones.
                if (game.getCommander().getShip().getImageIndex() == ShipType.CUSTOM.castToInt()) {
                    GuiEngine.getImageProvider().setCustomShipImages(customImages);

                    game.getCommander().getShip().updateCustomImageOffsetConstants();
                }

                GuiFacade.alert(AlertType.ShipDesignThanks, shipyard.getName());
                close();
            }
        }
    }

    private void btnConstruct_MouseEnter() {
        lblDisabledName.setVisible(txtName.getText().length() == 0);
        lblDisabledPct.setVisible(shipyard.getPercentOfMaxUnits() > 100);
    }

    private void btnConstruct_MouseLeave() {
        lblDisabledName.setVisible(false);
        lblDisabledPct.setVisible(false);
    }

    private void btnLoad_Click() {
        loadSelectedTemplate();
    }

    private void btnNextImage_Click() {
        setTemplateModified();
        imgIndex = (imgIndex + 1) % imgTypes.length;
        updateShip();
    }

    private void btnPrevImage_Click() {
        setTemplateModified();
        imgIndex = (imgIndex + imgTypes.length - 1) % imgTypes.length;
        updateShip();
    }

    private void btnSave_Click() {
        if (isSaveButtonEnabled()) {
            if (dlgSave.showDialog(this) == DialogResult.OK) {
                ShipTemplate template = new ShipTemplate(shipyard.getShipSpec(), txtName.getText());

                if (imgIndex > Consts.MaxShip) {
                    template.setImageIndex(ShipType.CUSTOM.castToInt());
                    template.setImages(customImages);
                } else {
                    template.setImageIndex(imgIndex);
                }

                Functions.saveFile(dlgSave.getFileName(), template.serialize());

                loadTemplateList();
            }
        }
    }

    private void btnSave_MouseEnter() {
        lblDisabledName.setVisible(txtName.getText().length() == 0);
    }

    private void btnSave_MouseLeave() {
        lblDisabledName.setVisible(false);
    }

    private void btnSetCustomImage_Click() {
        if (dlgOpen.showDialog(this) == DialogResult.OK) {
            String baseFileName = Path.removeExtension(dlgOpen.getFileName());
            String ext = Path.getExtension(dlgOpen.getFileName());

            Bitmap image = getImageFile(baseFileName + ext);
            Bitmap imageDamaged = getImageFile(baseFileName + "d" + ext);
            Bitmap imageShields = getImageFile(baseFileName + "s" + ext);
            Bitmap imageShieldsDamaged = getImageFile(baseFileName + "sd" + ext);

            if (image != null && imageDamaged != null && imageShields != null && imageShieldsDamaged != null) {
                customImages[Consts.ShipImgOffsetNormal] = image;
                customImages[Consts.ShipImgOffsetDamage] = imageDamaged;
                customImages[Consts.ShipImgOffsetShield] = imageShields;
                customImages[Consts.ShipImgOffsetSheildDamage] = imageShieldsDamaged;
            }

            imgIndex = imgTypes.length - 1;
            updateShip();
        }
    }

    private void num_ValueChanged() {
        setTemplateModified();
        updateCalculatedFigures();
    }

    private void num_ValueEnter(Object sender) {
        ((NumericUpDown) sender).select(0, ("" + ((NumericUpDown) sender).getValue()).length());
    }

    private void selSize_SelectedIndexChanged() {
        setTemplateModified();
        shipyard.getShipSpec().setSize(sizes.get(selSize.getSelectedIndex()));
        updateAllocation();
        updateCalculatedFigures();
    }

    private void txtName_TextChanged() {
        setTemplateModified();
        updateButtonEnabledState();
    }
}
