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

public class FormShipyard extends SpaceTraderForm {

    private final Game game = Game.getCurrentGame();
    private final Shipyard shipyard = Game.getCurrentGame().getCommander().getCurrentSystem().getShipyard();

    private final ShipType[] imgTypes = new ShipType[]{ShipType.FLEA, ShipType.GNAT, ShipType.FIREFLY,
            ShipType.MOSQUITO, ShipType.BUMBLEBEE, ShipType.BEETLE, ShipType.HORNET, ShipType.GRASSHOPPER,
            ShipType.TERMITE, ShipType.WASP, ShipType.CUSTOM};

    private IContainer components = new Container();

    private PictureBox logoPictureBox = new PictureBox();
    private Label welcomeLabelValue = new Label();
    private TextBox shipNameTextBox = new TextBox();
    private Label shipNameLabel = new Label();
    private PictureBox shipPictureBox = new PictureBox();
    private Label designFeeLabelValue = new Label();
    private Button constructButton = new Button();
    private Button cancelButton = new Button();

    private Panel costsPanel = new Panel();
    private Panel allocationPanel = new Panel();
    private NumericUpDown hullStrengthNum = new NumericUpDown();
    private Label hullStrengthLabel = new Label();
    private NumericUpDown cargoBaysNum = new NumericUpDown();
    private NumericUpDown crewQuartersNum = new NumericUpDown();
    private NumericUpDown fuelTanksNum = new NumericUpDown();
    private NumericUpDown shieldSlotsNum = new NumericUpDown();
    private NumericUpDown gadgetSlotsNum = new NumericUpDown();
    private NumericUpDown weaponsSlotsNum = new NumericUpDown();
    private Label cargoBaysLabel = new Label();
    private Label fuelTanksLabel = new Label();
    private Label crewQuartersLabel = new Label();
    private Label shieldSlotsLabel = new Label();
    private Label gadgetSlotsLabel = new Label();
    private Label weaponsSlotsLabel = new Label();
    private Label shipCostLabelValue = new Label();
    private Label totalCostLabelValue = new Label();
    private Label totalCostLabel = new Label();
    private Label shipCostLabel = new Label();
    private Label designFeeLabel = new Label();
    private Panel welcomePanel = new Panel();
    private Panel infoPanel = new Panel();
    private Label sizeLabel = new Label();
    private ComboBox<String> sizeComboBox = new ComboBox<>();
    private Label templateLabel = new Label();
    private ComboBox<Object> templateComboBox = new ComboBox<>();
    private Button setCustomImageButton = new Button();
    private Label imageLabel = new Label();
    private Button nextImageButton = new Button();
    private Button prevImageButton = new Button();
    private Label imageLabelValue = new Label();
    private Label unitsUsedLabel = new Label();
    private HorizontalLine infoHorizontalLine = new HorizontalLine();
    private Label pctOfMaxLabel = new Label();
    private Label pctOfMaxLabelValue = new Label();
    private Label penaltyLabel = new Label();
    private Label penaltyLabelValue = new Label();
    private HorizontalLine costsHorizontalLine = new HorizontalLine();
    private Label sizeSpecialtyLabel = new Label();
    private Label skillLabel = new Label();
    private Label sizeSpecialtyLabelValue = new Label();
    private Label skillLabelValue = new Label();
    private Label skillDescriptionLabelValue = new Label();
    private Label warningLabelValue = new Label();
    private Button loadButton = new Button();
    private Button saveButton = new Button();
    private Label tradeInLabel = new Label();
    private Label tradeInLabelValue = new Label();
    private Label unitsUsedLabelValue = new Label();
    private Label disabledPctTipLabel = new Label();
    private Label disabledNameTipLabel = new Label();

    private OpenFileDialog openDialog = new OpenFileDialog();
    private SaveFileDialog saveDialog = new SaveFileDialog();

    private Image[] customImages = new Image[Consts.ImagesPerShip];
    private ImageList shipyardLogosImageList = new ImageList(components);

    private int imgIndex = 0;
    private boolean loading = false;
    private ArrayList<spacetrader.game.enums.Size> sizes = null;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        GlobalAssets.initializeImages();
        GuiEngine.installImplementation(new OriginalGuiImplementationProvider());
        new Game("name", Difficulty.BEGINNER, 8, 8, 8, 8, null);
        Game.getCurrentGame().getCommander().getCurrentSystem().setShipyardId(ShipyardId.CORELLIAN);

        Launcher.runForm(new FormShipyard());
    }

    public FormShipyard() {
        initializeComponent();

        this.setText(Functions.stringVars(Strings.ShipyardTitle, shipyard.getName()));
        logoPictureBox.setImage(shipyardLogosImageList.getImages()[shipyard.getId().castToInt()]);
        welcomeLabelValue
                .setText(Functions.stringVars(Strings.ShipyardWelcome, shipyard.getName(), shipyard.getEngineer()));
        sizeSpecialtyLabelValue.setText(Strings.Sizes[shipyard.getSpecialtySize().castToInt()]);
        skillLabelValue.setText(Strings.ShipyardSkills[shipyard.getSkill().castToInt()]);
        skillDescriptionLabelValue.setText(Strings.ShipyardSkillDescriptions[shipyard.getSkill().castToInt()]);
        warningLabelValue
                .setText(Functions.stringVars(Strings.ShipyardWarning, Integer.toString(Shipyard.PENALTY_FIRST_PCT),
                Integer.toString(Shipyard.PENALTY_SECOND_PCT)));

        openDialog.setInitialDirectory(Consts.CustomImagesDirectory);
        saveDialog.setInitialDirectory(Consts.CustomTemplatesDirectory);
        disabledNameTipLabel.setImage(GuiEngine.getImageProvider().getDirectionImages()[Consts.DirectionDown]);
        disabledPctTipLabel.setImage(GuiEngine.getImageProvider().getDirectionImages()[Consts.DirectionDown]);

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
        setAutoScaleBaseSize(5, 13);
        setClientSize(478, 375);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(constructButton);
        setCancelButton(cancelButton);

        ResourceManager resources = new ResourceManager(FormShipyard.class);

        welcomePanel.suspendLayout();
        infoPanel.suspendLayout();
        costsPanel.suspendLayout();
        allocationPanel.suspendLayout();
        hullStrengthNum.beginInit();
        cargoBaysNum.beginInit();
        crewQuartersNum.beginInit();
        fuelTanksNum.beginInit();
        shieldSlotsNum.beginInit();
        gadgetSlotsNum.beginInit();
        weaponsSlotsNum.beginInit();
        this.suspendLayout();

        welcomePanel.setLocation(8, 0);
        welcomePanel.setSize(270, 204);
        welcomePanel.setTabStop(false);
        welcomePanel.getControls().addAll(logoPictureBox, welcomeLabelValue, sizeSpecialtyLabel,
                sizeSpecialtyLabelValue, skillLabel, skillLabelValue, skillDescriptionLabelValue, warningLabelValue);

        logoPictureBox.setBackground(Color.BLACK);
        logoPictureBox.setLocation(8, 12);
        logoPictureBox.setSize(80, 80);
        logoPictureBox.sizeMode = PictureBoxSizeMode.StretchImage;
        logoPictureBox.setTabStop(false);

        welcomeLabelValue.setLocation(92, 12);
        welcomeLabelValue.setSize(176, 52);
        welcomeLabelValue.setTabIndex(3);
        welcomeLabelValue
                .setText("Welcome to Sorosuub Engineering Shipyards! Our best engineer, Obi-Wan, is at your service.");

        sizeSpecialtyLabel.setAutoSize(true);
        sizeSpecialtyLabel.setFont(FontCollection.bold825);
        sizeSpecialtyLabel.setLocation(92, 65);
        sizeSpecialtyLabel.setSize(82, 13);
        sizeSpecialtyLabel.setTabIndex(23);
        sizeSpecialtyLabel.setText("Size Specialty:");

        sizeSpecialtyLabelValue.setLocation(180, 65);
        sizeSpecialtyLabelValue.setSize(64, 13);
        sizeSpecialtyLabelValue.setTabIndex(25);
        sizeSpecialtyLabelValue.setText("Gargantuan");

        skillLabel.setAutoSize(true);
        skillLabel.setFont(FontCollection.bold825);
        skillLabel.setLocation(92, 79);
        skillLabel.setSize(72, 13);
        skillLabel.setTabIndex(24);
        skillLabel.setText("Special Skill:");

        skillLabelValue.setLocation(180, 79);
        skillLabelValue.setSize(87, 13);
        skillLabelValue.setTabIndex(26);
        skillLabelValue.setText("Crew Quartering");

        skillDescriptionLabelValue.setLocation(8, 98);
        skillDescriptionLabelValue.setSize(258, 26);
        skillDescriptionLabelValue.setTabIndex(27);
        skillDescriptionLabelValue.setText("All ships constructed at this shipyard use 2 fewer units per crew quarter.");

        warningLabelValue.setLocation(8, 134);
        warningLabelValue.setSize(258, 65);
        warningLabelValue.setTabIndex(5);
        warningLabelValue.setText("Bear in mind that getting too close to the maximum number of units will result in"
                + " a \"Crowding Penalty\" due to the engineering difficulty of squeezing everything "
                + "in.  There is a modest penalty at 80%, and a more severe one at 90%.");

        
        infoPanel.setLocation(8, 208);
        infoPanel.setSize(270, 160);
        infoPanel.setTabStop(false);
        infoPanel.setText("Info");
        
        infoPanel.getControls().addAll(templateLabel, templateComboBox, loadButton, shipNameLabel,
                shipNameTextBox, saveButton, sizeLabel, sizeComboBox, infoHorizontalLine, imageLabel,
                shipPictureBox, prevImageButton, imageLabelValue, nextImageButton, setCustomImageButton);

        templateLabel.setAutoSize(true);
        templateLabel.setLocation(8, 19);
        templateLabel.setSize(55, 13);
        templateLabel.setTabIndex(20);
        templateLabel.setText("Template:");

        templateComboBox.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        templateComboBox.setLocation(80, 16);
        templateComboBox.setSize(132, 21);
        templateComboBox.setTabIndex(1);

        loadButton.setFlatStyle(FlatStyle.FLAT);
        loadButton.setLocation(216, 16);
        loadButton.setSize(44, 20);
        loadButton.setTabIndex(2);
        loadButton.setText("Load");
        loadButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                loadButtonClick();
            }
        });

        shipNameLabel.setAutoSize(true);
        shipNameLabel.setLocation(8, 44);
        shipNameLabel.setSize(63, 13);
        shipNameLabel.setTabIndex(5);
        shipNameLabel.setText("Ship Name:");

        shipNameTextBox.setLocation(80, 40);
        shipNameTextBox.setSize(132, 20);
        shipNameTextBox.setTabIndex(3);
        shipNameTextBox.setTextChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                shipNameTextBoxTextChanged();
            }
        });

        saveButton.setFlatStyle(FlatStyle.FLAT);
        saveButton.setLocation(216, 40);
        saveButton.setSize(44, 20);
        saveButton.setTabIndex(4);
        saveButton.setText("Save");
        saveButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                saveButtonClick();
            }
        });
        saveButton.setMouseEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                saveButtonMouseEnter();
            }
        });
        saveButton.setMouseLeave(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                saveButtonMouseLeave();
            }
        });

        sizeLabel.setAutoSize(true);
        sizeLabel.setLocation(8, 66);
        sizeLabel.setSize(29, 13);
        sizeLabel.setTabIndex(18);
        sizeLabel.setText("Size:");

        sizeComboBox.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        sizeComboBox.setLocation(80, 63);
        sizeComboBox.setSize(180, 21);
        sizeComboBox.setTabIndex(5);
        sizeComboBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                sizeComboBoxSelectedIndexChanged();
            }
        });

        infoHorizontalLine.setLocation(8, 89);
        infoHorizontalLine.setWidth(254);
        infoHorizontalLine.setTabStop(false);

        imageLabel.setAutoSize(true);
        imageLabel.setLocation(8, 95);
        imageLabel.setSize(39, 13);
        imageLabel.setTabIndex(22);
        imageLabel.setText("Image:");

        shipPictureBox.setBackground(Color.WHITE);
        shipPictureBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        shipPictureBox.setLocation(80, 95);
        shipPictureBox.setSize(66, 54);
        shipPictureBox.setTabIndex(14);
        shipPictureBox.setTabStop(false);

        prevImageButton.setFlatStyle(FlatStyle.FLAT);
        prevImageButton.setLocation(154, 95);
        prevImageButton.setSize(20, 18);
        prevImageButton.setTabIndex(6);
        prevImageButton.setText("<");
        prevImageButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                prevImageButtonClick();
            }
        });

        imageLabelValue.setLocation(174, 98);
        imageLabelValue.setSize(70, 13);
        imageLabelValue.setTabIndex(61);
        imageLabelValue.setText("Custom Ship");
        imageLabelValue.setTextAlign(ContentAlignment.TOP_CENTER);

        nextImageButton.setFlatStyle(FlatStyle.FLAT);
        nextImageButton.setLocation(240, 95);
        nextImageButton.setSize(20, 18);
        nextImageButton.setTabIndex(7);
        nextImageButton.setText(">");
        nextImageButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                nextImageButtonClick();
            }
        });

        setCustomImageButton.setFlatStyle(FlatStyle.FLAT);
        setCustomImageButton.setLocation(158, 121);
        setCustomImageButton.setSize(106, 22);
        setCustomImageButton.setTabIndex(8);
        setCustomImageButton.setText("Set Custom...");
        setCustomImageButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                setCustomImageButtonClick();
            }
        });

        allocationPanel.setLocation(286, 0);
        allocationPanel.setSize(184, 226);
        allocationPanel.setTabStop(false);
        allocationPanel.setText("Space Allocation");

        allocationPanel.getControls().addAll(cargoBaysLabel, cargoBaysNum, fuelTanksLabel, fuelTanksNum,
                hullStrengthLabel, hullStrengthNum, weaponsSlotsLabel, weaponsSlotsNum, shieldSlotsLabel,
                shieldSlotsNum, gadgetSlotsLabel, gadgetSlotsNum, crewQuartersLabel, crewQuartersNum, unitsUsedLabel,
                unitsUsedLabelValue, pctOfMaxLabel, pctOfMaxLabelValue);

        cargoBaysLabel.setAutoSize(true);
        cargoBaysLabel.setLocation(8, 18);
        cargoBaysLabel.setSize(66, 13);
        cargoBaysLabel.setTabIndex(5);
        cargoBaysLabel.setText("Cargo Bays:");

        cargoBaysNum.setBackground(Color.WHITE);
        cargoBaysNum.setLocation(110, 16);
        cargoBaysNum.setMaximum(999);
        cargoBaysNum.setReadOnly(true);
        cargoBaysNum.setSize(64, 20);
        cargoBaysNum.setTabIndex(3);
        cargoBaysNum.setTextAlign(HorizontalAlignment.RIGHT);
        cargoBaysNum.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        cargoBaysNum.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        fuelTanksLabel.setAutoSize(true);
        fuelTanksLabel.setLocation(8, 42);
        fuelTanksLabel.setSize(41, 13);
        fuelTanksLabel.setTabIndex(4);
        fuelTanksLabel.setText("Range:");

        fuelTanksNum.setBackground(Color.WHITE);
        fuelTanksNum.setLocation(110, 40);
        fuelTanksNum.setReadOnly(true);
        fuelTanksNum.setSize(64, 20);
        fuelTanksNum.setTabIndex(2);
        fuelTanksNum.setTextAlign(HorizontalAlignment.RIGHT);
        fuelTanksNum.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        fuelTanksNum.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        hullStrengthLabel.setAutoSize(true);
        hullStrengthLabel.setLocation(8, 66);
        hullStrengthLabel.setSize(70, 13);
        hullStrengthLabel.setTabIndex(13);
        hullStrengthLabel.setText("Hull Strength:");

        hullStrengthNum.setBackground(Color.WHITE);
        hullStrengthNum.setLocation(110, 64);
        hullStrengthNum.setMaximum(9999);
        hullStrengthNum.setReadOnly(true);
        hullStrengthNum.setSize(64, 20);
        hullStrengthNum.setTabIndex(1);
        hullStrengthNum.setTextAlign(HorizontalAlignment.RIGHT);
        hullStrengthNum.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        hullStrengthNum.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        weaponsSlotsLabel.setAutoSize(true);
        weaponsSlotsLabel.setLocation(8, 90);
        weaponsSlotsLabel.setSize(78, 13);
        weaponsSlotsLabel.setTabIndex(0);
        weaponsSlotsLabel.setText("Weapon Slots:");

        weaponsSlotsNum.setBackground(Color.WHITE);
        weaponsSlotsNum.setLocation(110, 88);
        weaponsSlotsNum.setReadOnly(true);
        weaponsSlotsNum.setSize(64, 20);
        weaponsSlotsNum.setTabIndex(5);
        weaponsSlotsNum.setTextAlign(HorizontalAlignment.RIGHT);
        weaponsSlotsNum.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        weaponsSlotsNum.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        shieldSlotsLabel.setAutoSize(true);
        shieldSlotsLabel.setLocation(8, 114);
        shieldSlotsLabel.setSize(67, 13);
        shieldSlotsLabel.setTabIndex(2);
        shieldSlotsLabel.setText("Shield Slots:");

        shieldSlotsNum.setBackground(Color.WHITE);
        shieldSlotsNum.setLocation(110, 112);
        shieldSlotsNum.setReadOnly(true);
        shieldSlotsNum.setSize(64, 20);
        shieldSlotsNum.setTabIndex(6);
        shieldSlotsNum.setTextAlign(HorizontalAlignment.RIGHT);
        shieldSlotsNum.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        shieldSlotsNum.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        gadgetSlotsLabel.setAutoSize(true);
        gadgetSlotsLabel.setLocation(8, 138);
        gadgetSlotsLabel.setSize(73, 13);
        gadgetSlotsLabel.setTabIndex(1);
        gadgetSlotsLabel.setText("Gadget Slots:");

        gadgetSlotsNum.setBackground(Color.WHITE);
        gadgetSlotsNum.setLocation(110, 136);
        gadgetSlotsNum.setReadOnly(true);
        gadgetSlotsNum.setSize(64, 20);
        gadgetSlotsNum.setTabIndex(7);
        gadgetSlotsNum.setTextAlign(HorizontalAlignment.RIGHT);
        gadgetSlotsNum.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        gadgetSlotsNum.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        crewQuartersLabel.setAutoSize(true);
        crewQuartersLabel.setLocation(8, 162);
        crewQuartersLabel.setSize(81, 13);
        crewQuartersLabel.setTabIndex(3);
        crewQuartersLabel.setText("Crew Quarters:");

        crewQuartersNum.setBackground(Color.WHITE);
        crewQuartersNum.setLocation(110, 160);
        crewQuartersNum.setMinimum(1);
        crewQuartersNum.setReadOnly(true);
        crewQuartersNum.setSize(64, 20);
        crewQuartersNum.setTabIndex(4);
        crewQuartersNum.setTextAlign(HorizontalAlignment.RIGHT);
        crewQuartersNum.setValue(1);
        crewQuartersNum.setEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueEnter(sender);
            }
        });
        crewQuartersNum.setValueChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                numValueChanged();
            }
        });

        unitsUsedLabel.setAutoSize(true);
        unitsUsedLabel.setLocation(8, 186);
        unitsUsedLabel.setSize(63, 13);
        unitsUsedLabel.setTabIndex(16);
        unitsUsedLabel.setText("Units Used:");

        unitsUsedLabelValue.setLocation(110, 186);
        unitsUsedLabelValue.setSize(34, 13);
        unitsUsedLabelValue.setTabIndex(17);
        unitsUsedLabelValue.setText("888");
        unitsUsedLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        pctOfMaxLabel.setAutoSize(true);
        pctOfMaxLabel.setLocation(8, 204);
        pctOfMaxLabel.setSize(60, 13);
        pctOfMaxLabel.setTabIndex(18);
        pctOfMaxLabel.setText("% of Max:");

        pctOfMaxLabelValue.setFont(FontCollection.bold825);
        pctOfMaxLabelValue.setForeground(Color.red);
        pctOfMaxLabelValue.setLocation(110, 204);
        pctOfMaxLabelValue.setSize(34, 13);
        pctOfMaxLabelValue.setTabIndex(19);
        pctOfMaxLabelValue.setText("888%");
        pctOfMaxLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        costsPanel.setLocation(286, 230);
        costsPanel.setSize(184, 106);
        costsPanel.setTabStop(false);
        costsPanel.setText("Costs");

        costsPanel.getControls().addAll(shipCostLabel, shipCostLabelValue, penaltyLabel,
                penaltyLabelValue, designFeeLabel, designFeeLabelValue, tradeInLabel,
                tradeInLabelValue, costsHorizontalLine, totalCostLabel, totalCostLabelValue);

        shipCostLabel.setAutoSize(true);
        shipCostLabel.setLocation(8, 16);
        shipCostLabel.setSize(56, 13);
        shipCostLabel.setTabIndex(16);
        shipCostLabel.setText("Ship Cost:");

        shipCostLabelValue.setLocation(106, 16);
        shipCostLabelValue.setSize(74, 16);
        shipCostLabelValue.setTabIndex(19);
        shipCostLabelValue.setText("8,888,888 cr.");
        shipCostLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        penaltyLabel.setAutoSize(true);
        penaltyLabel.setLocation(8, 32);
        penaltyLabel.setSize(96, 13);
        penaltyLabel.setTabIndex(20);
        penaltyLabel.setText("Crowding Penalty:");

        penaltyLabelValue.setLocation(106, 32);
        penaltyLabelValue.setSize(74, 16);
        penaltyLabelValue.setTabIndex(21);
        penaltyLabelValue.setText("8,888,888 cr.");
        penaltyLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        designFeeLabel.setAutoSize(true);
        designFeeLabel.setLocation(8, 48);
        designFeeLabel.setSize(65, 13);
        designFeeLabel.setTabIndex(14);
        designFeeLabel.setText("Design Fee:");

        designFeeLabelValue.setLocation(106, 48);
        designFeeLabelValue.setSize(74, 16);
        designFeeLabelValue.setTabIndex(15);
        designFeeLabelValue.setText("888,888 cr.");
        designFeeLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        tradeInLabel.setAutoSize(true);
        tradeInLabel.setLocation(8, 64);
        tradeInLabel.setSize(77, 13);
        tradeInLabel.setTabIndex(134);
        tradeInLabel.setText("Less Trade-In:");

        tradeInLabelValue.setLocation(106, 64);
        tradeInLabelValue.setSize(75, 16);
        tradeInLabelValue.setTabIndex(135);
        tradeInLabelValue.setText("-8,888,888 cr.");
        tradeInLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        costsHorizontalLine.setLocation(8, 80);
        costsHorizontalLine.setWidth(168);
        costsHorizontalLine.setTabStop(false);

        totalCostLabel.setAutoSize(true);
        totalCostLabel.setLocation(8, 84);
        totalCostLabel.setSize(59, 13);
        totalCostLabel.setTabIndex(17);
        totalCostLabel.setText("Total Cost:");

        totalCostLabelValue.setLocation(106, 84);
        totalCostLabelValue.setSize(74, 16);
        totalCostLabelValue.setTabIndex(18);
        totalCostLabelValue.setText("8,888,888 cr.");
        totalCostLabelValue.setTextAlign(ContentAlignment.TOP_RIGHT);

        cancelButton.setDialogResult(DialogResult.CANCEL);
        cancelButton.setFlatStyle(FlatStyle.FLAT);
        cancelButton.setLocation(286, 344);
        cancelButton.setSize(88, 22);
        cancelButton.setTabIndex(5);
        cancelButton.setText("Cancel Design");

        constructButton.setFlatStyle(FlatStyle.FLAT);
        constructButton.setForeground(SystemColors.CONTROL_TEXT);
        constructButton.setLocation(382, 344);
        constructButton.setSize(88, 22);
        constructButton.setTabIndex(6);
        constructButton.setText("Construct Ship");
        constructButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                constructButtonClick();
            }
        });
        constructButton.setMouseEnter(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                constructButtonMouseEnter();
            }
        });
        constructButton.setMouseLeave(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                constructButtonMouseLeave();
            }
        });

        shipyardLogosImageList.setColorDepth(ColorDepth.DEPTH_24_BIT);
        shipyardLogosImageList.setImageSize(80, 80);
        shipyardLogosImageList.setImageStream(((ImageListStreamer) (resources
                .getObject("ilShipyardLogos.ImageStream"))));
        shipyardLogosImageList.setTransparentColor(Color.BLACK);

        if (!GlobalAssets.getStrings().isEmpty()) {
            openDialog.setFilter(GlobalAssets.getStrings().get("formShipyard.openDialog.filter"));
            openDialog.setTitle(GlobalAssets.getStrings().getTitle("formShipyard.openDialog.title"));

            saveDialog.setDefaultExt(GlobalAssets.getStrings().get("formShipyard.saveDialog.defaultExt"));
            saveDialog.setFileName(GlobalAssets.getStrings().get("formShipyard.saveDialog.fileName"));
            saveDialog.setFilter(GlobalAssets.getStrings().get("formShipyard.saveDialog.filter"));
            saveDialog.setTitle(GlobalAssets.getStrings().getTitle("formShipyard.saveDialog.title"));
        }

        disabledNameTipLabel.setBackground(SystemColors.INFO);
        disabledNameTipLabel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        disabledNameTipLabel.setTextAlign(ContentAlignment.MIDDLE_LEFT);
        disabledNameTipLabel.setLocation(96, 222);
        disabledNameTipLabel.setSize(170, 20);
        disabledNameTipLabel.setTabIndex(7);
        disabledNameTipLabel.setText("You must enter a Ship Name.");
        disabledNameTipLabel.setTextAlign(ContentAlignment.MIDDLE_RIGHT);
        disabledNameTipLabel.setVisible(false);

        disabledPctTipLabel.setBackground(SystemColors.INFO);
        disabledPctTipLabel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        disabledPctTipLabel.setTextAlign(ContentAlignment.MIDDLE_RIGHT);
        disabledPctTipLabel.setLocation(154, 182);
        disabledPctTipLabel.setSize(276, 20);
        disabledPctTipLabel.setTabIndex(8);
        disabledPctTipLabel.setText("Your % of Max must be less than or equal to 100%.");
        disabledPctTipLabel.setTextAlign(ContentAlignment.MIDDLE_CENTER);
        disabledPctTipLabel.setVisible(false);

        controls.addAll(welcomePanel, infoPanel, allocationPanel, costsPanel, cancelButton, constructButton,
                disabledNameTipLabel, disabledPctTipLabel);

        hullStrengthNum.endInit();
        cargoBaysNum.endInit();
        crewQuartersNum.endInit();
        fuelTanksNum.endInit();
        shieldSlotsNum.endInit();
        gadgetSlotsNum.endInit();
        weaponsSlotsNum.endInit();

        ReflectionUtils.loadControlsDimensions(this.asSwingObject(), this.getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(this.asSwingObject(), this.getName(), GlobalAssets.getStrings());
    }

    private boolean constructButtonEnabled() {
        return (shipyard.getPercentOfMaxUnits() <= 100 && shipNameTextBox.getText().length() > 0);
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
        if (templateComboBox.getSelectedItem() instanceof ShipTemplate) {
            loading = true;

            ShipTemplate template = (ShipTemplate) templateComboBox.getSelectedItem();

            if (template.getName().equals(Strings.ShipNameCurrentShip)) {
                shipNameTextBox.setText(game.getCommander().getShip().getName());
            } else if (template.getName().endsWith(Strings.ShipNameTemplateSuffixDefault)
                    || template.getName().endsWith(Strings.ShipNameTemplateSuffixMinimum)) {
                shipNameTextBox.setText("");
            } else {
                shipNameTextBox.setText(template.getName());
            }

            sizeComboBox.setSelectedIndex(Math.max(0, sizes.indexOf(template.getSize())));
            imgIndex = (template.getImageIndex() == ShipType.CUSTOM.castToInt())
                    ? imgTypes.length - 1 : template.getImageIndex();

            if (template.getImages() != null) {
                customImages = template.getImages();
            } else {
                customImages = GuiEngine.getImageProvider().getCustomShipImages();
            }

            cargoBaysNum.setValue(template.getCargoBays());
            fuelTanksNum.setValue(Math.min(Math.max(fuelTanksNum.getMinimum(), template.getFuelTanks()), fuelTanksNum
                    .getMaximum()));
            hullStrengthNum.setValue(Math.min(Math.max(hullStrengthNum.getMinimum(), template.getHullStrength()),
                    hullStrengthNum.getMaximum()));
            weaponsSlotsNum.setValue(template.getWeaponSlots());
            shieldSlotsNum.setValue(template.getShieldSlots());
            gadgetSlotsNum.setValue(template.getGadgetSlots());
            crewQuartersNum.setValue(Math.max(crewQuartersNum.getMinimum(), template.getCrewQuarters()));

            updateShip();
            updateCalculatedFigures();

            if (templateComboBox.getItems().get(0).toString().equals(Strings.ShipNameModified)) {
                templateComboBox.getItems().remove(0);
            }

            loading = false;
        }
    }

    private void loadSizes() {
        sizes = new ArrayList<>(6);

        for (spacetrader.game.enums.Size size : shipyard.getAvailableSizes()) {
            sizes.add(size);
            sizeComboBox.getItems().add(Functions.stringVars(Strings.ShipyardSizeItem, Strings.Sizes[size.castToInt()],
                    Functions.multiples(Shipyard.MAX_UNITS[size.castToInt()], Strings.ShipyardUnit)));
        }
    }

    private void loadTemplateList() {
        ShipTemplate currentShip = new ShipTemplate(game.getCommander().getShip(), Strings.ShipNameCurrentShip);
        templateComboBox.getItems().add(currentShip);

        templateComboBox.getItems().add(Consts.ShipTemplateSeparator);

        // Add the minimal sizes templates.
        for (spacetrader.game.enums.Size size : sizes)
            templateComboBox.getItems().add(new ShipTemplate(size, Strings.Sizes[size.castToInt()]
                    + Strings.ShipNameTemplateSuffixMinimum));

        templateComboBox.getItems().add(Consts.ShipTemplateSeparator);

        // Add the buyable ship spec templates.
        for (ShipSpec spec : Consts.ShipSpecs) {
            if (sizes.contains(spec.getSize()) && spec.getType().castToInt() <= Consts.MaxShip) {
                templateComboBox
                        .getItems().add(new ShipTemplate(spec, spec.getName() + Strings.ShipNameTemplateSuffixDefault));
            }
        }

        templateComboBox.getItems().add(Consts.ShipTemplateSeparator);

        // Add the user-created templates.
        ArrayList<ShipTemplate> userTemplates = new ArrayList<>();
        for (String fileName : Directory.getFiles(Consts.CustomTemplatesDirectory, "*.sst")) {
            ShipTemplate template = new ShipTemplate((Hashtable) Functions.loadFile(fileName, true));
            if (sizes.contains(template.getSize())) {
                userTemplates.add(template);
            }
        }
        userTemplates.sort();
        templateComboBox.getItems().addRange(userTemplates.toArray(new ShipTemplate[0]));

        templateComboBox.setSelectedIndex(0);
    }

    private boolean isSaveButtonEnabled() {
        return shipNameTextBox.getText().length() > 0;
    }

    private void setTemplateModified() {
        if (!loading && templateComboBox.getItems().getSize() > 0) {
            if (!templateComboBox.getItems().get(0).toString().equals(Strings.ShipNameModified)) {
                templateComboBox.getItems().insert(0, Strings.ShipNameModified);
            }
            templateComboBox.setSelectedIndex(0);
        }
    }

    private void updateAllocation() {
        boolean fuelMinimum = (fuelTanksNum.getValue() == fuelTanksNum.getMinimum());
        boolean hullMinimum = (hullStrengthNum.getValue() == hullStrengthNum.getMinimum());

        fuelTanksNum.setMinimum(shipyard.getBaseFuel());
        fuelTanksNum.setIncrement(shipyard.getPerUnitFuel());
        fuelTanksNum.setMaximum(Consts.MaxFuelTanks);
        if (fuelMinimum) {
            fuelTanksNum.setValue(fuelTanksNum.getMinimum());
        }

        hullStrengthNum.setMinimum(shipyard.getBaseHull());
        hullStrengthNum.setIncrement(shipyard.getPerUnitHull());
        if (hullMinimum) {
            hullStrengthNum.setValue(hullStrengthNum.getMinimum());
        }

        weaponsSlotsNum.setMaximum(Consts.MaxSlots);
        shieldSlotsNum.setMaximum(Consts.MaxSlots);
        gadgetSlotsNum.setMaximum(Consts.MaxSlots);
        crewQuartersNum.setMaximum(Consts.MaxSlots);
    }

    private void updateCalculatedFigures() {
        // Fix the fuel value to be a multiple of the per unit value less the super.
        int extraFuel = fuelTanksNum.getValue() - shipyard.getBaseFuel();
        if (extraFuel % shipyard.getPerUnitFuel() > 0 && fuelTanksNum.getValue() < fuelTanksNum.getMaximum()) {
            fuelTanksNum.setValue(Math.max(fuelTanksNum.getMinimum(), Math.min(fuelTanksNum.getMaximum(),
                    (extraFuel + shipyard.getPerUnitFuel()) / shipyard.getPerUnitFuel() * shipyard.getPerUnitFuel()
                            + shipyard.getBaseFuel())));
        }

        // Fix the hull value to be a multiple of the unit value less the super.
        int extraHull = hullStrengthNum.getValue() - shipyard.getBaseHull();
        if (extraHull % shipyard.getPerUnitHull() > 0) {
            hullStrengthNum.setValue(Math.max(hullStrengthNum.getMinimum(), (extraHull + shipyard.getPerUnitHull())
                    / shipyard.getPerUnitHull() * shipyard.getPerUnitHull() + shipyard.getBaseHull()));
        }

        shipyard.getShipSpec().setCargoBays(cargoBaysNum.getValue());
        shipyard.getShipSpec().setFuelTanks(fuelTanksNum.getValue());
        shipyard.getShipSpec().setHullStrength(hullStrengthNum.getValue());
        shipyard.getShipSpec().setWeaponSlots(weaponsSlotsNum.getValue());
        shipyard.getShipSpec().setShieldSlots(shieldSlotsNum.getValue());
        shipyard.getShipSpec().setGadgetSlots(gadgetSlotsNum.getValue());
        shipyard.getShipSpec().setCrewQuarters(crewQuartersNum.getValue());

        shipyard.calculateDependantVariables();

        unitsUsedLabelValue.setText(shipyard.getUnitsUsed());
        pctOfMaxLabelValue.setText(Functions.formatPercent(shipyard.getPercentOfMaxUnits()));
        if (shipyard.getPercentOfMaxUnits() >= Shipyard.PENALTY_FIRST_PCT) {
            pctOfMaxLabelValue.setFont(skillLabel.getFont());
        } else {
            pctOfMaxLabelValue.setFont(pctOfMaxLabel.getFont());
        }
        if (shipyard.getUnitsUsed() > shipyard.getMaxUnits()) {
            pctOfMaxLabelValue.setForeground(Color.RED);
        } else if (shipyard.getPercentOfMaxUnits() >= Shipyard.PENALTY_SECOND_PCT) {
            pctOfMaxLabelValue.setForeground(Color.ORANGE);
        } else if (shipyard.getPercentOfMaxUnits() >= Shipyard.PENALTY_FIRST_PCT) {
            pctOfMaxLabelValue.setForeground(Color.YELLOW);
        } else {
            pctOfMaxLabelValue.setForeground(pctOfMaxLabel.getForeground());
        }

        shipCostLabelValue.setText(Functions.formatMoney(shipyard.getAdjustedPrice()));
        designFeeLabelValue.setText(Functions.formatMoney(shipyard.getAdjustedDesignFee()));
        penaltyLabelValue.setText(Functions.formatMoney(shipyard.getAdjustedPenaltyCost()));
        tradeInLabelValue.setText(Functions.formatMoney(-shipyard.getTradeIn()));
        totalCostLabelValue.setText(Functions.formatMoney(shipyard.getTotalCost()));

        updateButtonEnabledState();
    }

    private void updateButtonEnabledState() {
        constructButton.setForeground(constructButtonEnabled() ? Color.BLACK : Color.GRAY);
        saveButton.setForeground(isSaveButtonEnabled() ? Color.BLACK : Color.GRAY);
    }

    private void updateShip() {
        shipyard.getShipSpec().setImageIndex(imgTypes[imgIndex].castToInt());
        shipPictureBox.setImage((imgIndex > Consts.MaxShip
                ? customImages[0] : Consts.ShipSpecs[imgTypes[imgIndex].castToInt()].getImage()));
        imageLabelValue.setText((imgIndex > Consts.MaxShip
                ? Strings.ShipNameCustomShip : Consts.ShipSpecs[imgTypes[imgIndex].castToInt()].getName()));
    }

    private void constructButtonClick() {
        if (constructButtonEnabled()) {
            if (game.getCommander().isTradeShip(shipyard.getShipSpec(), shipyard.getTotalCost(), shipNameTextBox
                    .getText())) {
                Strings.ShipNames[ShipType.CUSTOM.castToInt()] = shipNameTextBox.getText();

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

    private void constructButtonMouseEnter() {
        disabledNameTipLabel.setVisible(shipNameTextBox.getText().length() == 0);
        disabledPctTipLabel.setVisible(shipyard.getPercentOfMaxUnits() > 100);
    }

    private void constructButtonMouseLeave() {
        disabledNameTipLabel.setVisible(false);
        disabledPctTipLabel.setVisible(false);
    }

    private void loadButtonClick() {
        loadSelectedTemplate();
    }

    private void nextImageButtonClick() {
        setTemplateModified();
        imgIndex = (imgIndex + 1) % imgTypes.length;
        updateShip();
    }

    private void prevImageButtonClick() {
        setTemplateModified();
        imgIndex = (imgIndex + imgTypes.length - 1) % imgTypes.length;
        updateShip();
    }

    private void saveButtonClick() {
        if (isSaveButtonEnabled()) {
            if (saveDialog.showDialog(this) == DialogResult.OK) {
                ShipTemplate template = new ShipTemplate(shipyard.getShipSpec(), shipNameTextBox.getText());

                if (imgIndex > Consts.MaxShip) {
                    template.setImageIndex(ShipType.CUSTOM.castToInt());
                    template.setImages(customImages);
                } else {
                    template.setImageIndex(imgIndex);
                }

                Functions.saveFile(saveDialog.getFileName(), template.serialize());

                loadTemplateList();
            }
        }
    }

    private void saveButtonMouseEnter() {
        disabledNameTipLabel.setVisible(shipNameTextBox.getText().length() == 0);
    }

    private void saveButtonMouseLeave() {
        disabledNameTipLabel.setVisible(false);
    }

    private void setCustomImageButtonClick() {
        if (openDialog.showDialog(this) == DialogResult.OK) {
            String baseFileName = Path.removeExtension(openDialog.getFileName());
            String ext = Path.getExtension(openDialog.getFileName());

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

    private void numValueChanged() {
        setTemplateModified();
        updateCalculatedFigures();
    }

    private void numValueEnter(Object sender) {
        ((NumericUpDown) sender).select(0, Integer.toString(((NumericUpDown) sender).getValue()).length());
    }

    private void sizeComboBoxSelectedIndexChanged() {
        setTemplateModified();
        shipyard.getShipSpec().setSize(sizes.get(sizeComboBox.getSelectedIndex()));
        updateAllocation();
        updateCalculatedFigures();
    }

    private void shipNameTextBoxTextChanged() {
        setTemplateModified();
        updateButtonEnabledState();
    }
}
