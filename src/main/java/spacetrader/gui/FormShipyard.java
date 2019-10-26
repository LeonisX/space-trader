package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Image;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.*;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.ShipType;
import spacetrader.game.enums.Size;
import spacetrader.guifacade.GuiEngine;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;
import spacetrader.util.IOUtils;
import spacetrader.util.Path;
import spacetrader.util.ReflectionUtils;

import java.awt.*;

public class FormShipyard extends SpaceTraderForm {

    private final Shipyard shipyard;

    private final ShipType[] imgTypes = new ShipType[]{ShipType.FLEA, ShipType.GNAT, ShipType.FIREFLY,
            ShipType.MOSQUITO, ShipType.BUMBLEBEE, ShipType.BEETLE, ShipType.HORNET, ShipType.GRASSHOPPER,
            ShipType.TERMITE, ShipType.WASP, ShipType.CUSTOM};

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

    private OpenFileDialog openDialog;
    private SaveFileDialog saveDialog;

    private Image[] customImages = new Image[Consts.ImagesPerShip];
    private ImageList shipyardLogosImageList = new ImageList();

    private int imgIndex = 0;
    private boolean loading = false;
    private ArrayList<Size> sizes = null;

    public FormShipyard() {
        shipyard = Game.getCurrentGame().getCommander().getCurrentSystem().getShipyard();
        initializeForm();
    }

    FormShipyard(int shipyardId) {
        shipyard = Consts.Shipyards[shipyardId];
        initializeForm();
    }

    private void initializeForm() {
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
        //setText("Ship Design at XXXX Shipyards");

        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(525, 385);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(constructButton);
        setCancelButton(cancelButton);

        openDialog = new OpenFileDialog(getName());
        saveDialog = new SaveFileDialog(getName());

        ResourceManager resources = new ResourceManager(FormShipyard.class);

        welcomePanel.setLocation(8, 0);
        welcomePanel.setSize(310, 204);
        welcomePanel.setTabStop(false);

        welcomePanel.getControls().addAll(logoPictureBox, welcomeLabelValue, sizeSpecialtyLabel,
                sizeSpecialtyLabelValue, skillLabel, skillLabelValue, skillDescriptionLabelValue, warningLabelValue);

        logoPictureBox.setBackground(Color.BLACK);
        logoPictureBox.setLocation(8, 10);
        logoPictureBox.setSize(80, 80);
        logoPictureBox.sizeMode = PictureBoxSizeMode.STRETCH_IMAGE;
        logoPictureBox.setTabStop(false);

        welcomeLabelValue.setLocation(92, 10);
        welcomeLabelValue.setSize(210, 42);
        /*welcomeLabelValue
                .setText("Welcome to Sorosuub Engineering Shipyards! Our best engineer, Obi-Wan, is at your service.");*/

        sizeSpecialtyLabel.setAutoSize(true);
        sizeSpecialtyLabel.setFont(FontCollection.bold825);
        sizeSpecialtyLabel.setLocation(92, 60);
        //sizeSpecialtyLabel.setSize(82, 13);
        sizeSpecialtyLabel.setText("Size Specialty:");

        sizeSpecialtyLabelValue.setAutoSize(true);
        sizeSpecialtyLabelValue.setLocation(190, 59);
        //sizeSpecialtyLabelValue.setSize(64, 13);
        sizeSpecialtyLabelValue.setTabIndex(25);
        //sizeSpecialtyLabelValue.setText("Gargantuan");

        skillLabel.setAutoSize(true);
        skillLabel.setFont(FontCollection.bold825);
        skillLabel.setLocation(92, 75);
        //skillLabel.setSize(72, 13);
        skillLabel.setText("Special Skill:");

        skillLabelValue.setAutoSize(true);
        skillLabelValue.setLocation(190, 74);
        //skillLabelValue.setSize(87, 13);
        //skillLabelValue.setText("Crew Quartering");

        skillDescriptionLabelValue.setLocation(8, 96);
        skillDescriptionLabelValue.setSize(305, 26);
        //skillDescriptionLabelValue.setText("All ships constructed at this shipyard use 2 fewer units per crew quarter.");

        warningLabelValue.setLocation(8, 130);
        warningLabelValue.setSize(300, 70);
        /*warningLabelValue.setText("Bear in mind that getting too close to the maximum number of units will result in"
                + " a \"Crowding Penalty\" due to the engineering difficulty of squeezing everything "
                + "in.  There is a modest penalty at 80%, and a more severe one at 90%.");*/

        
        infoPanel.setLocation(6, 208);
        infoPanel.setSize(314, 165);
        infoPanel.setTabStop(false);
        infoPanel.setText("Info");
        
        infoPanel.getControls().addAll(templateLabel, templateComboBox, loadButton, shipNameLabel,
                shipNameTextBox, saveButton, sizeLabel, sizeComboBox, infoHorizontalLine, imageLabel,
                shipPictureBox, prevImageButton, imageLabelValue, nextImageButton, setCustomImageButton);

        templateLabel.setAutoSize(true);
        templateLabel.setLocation(8, 24);
        templateLabel.setSize(55, 13);
        templateLabel.setTabIndex(20);
        templateLabel.setText("Template:");

        templateComboBox.setLocation(80, 23);
        templateComboBox.setSize(152, 21);
        templateComboBox.setTabIndex(1);

        loadButton.setLocation(236, 24);
        loadButton.setSize(67, 20);
        loadButton.setTabIndex(2);
        loadButton.setText("Load");
        loadButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                loadButtonClick();
            }
        });

        shipNameLabel.setAutoSize(true);
        shipNameLabel.setLocation(8, 48);
        shipNameLabel.setSize(63, 13);
        shipNameLabel.setTabIndex(5);
        shipNameLabel.setText("Ship Name:");

        shipNameTextBox.setLocation(80, 45);
        shipNameTextBox.setSize(152, 20);
        shipNameTextBox.setTabIndex(3);
        shipNameTextBox.setTextChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                shipNameTextBoxTextChanged();
            }
        });

        saveButton.setLocation(236, 45);
        saveButton.setSize(67, 20);
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
        sizeLabel.setLocation(8, 71);
        sizeLabel.setSize(29, 13);
        sizeLabel.setTabIndex(18);
        sizeLabel.setText("Size:");

        sizeComboBox.setLocation(80, 67);
        sizeComboBox.setSize(222, 21);
        sizeComboBox.setTabIndex(5);
        sizeComboBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                sizeComboBoxSelectedIndexChanged();
            }
        });

        infoHorizontalLine.setLocation(8, 94);
        infoHorizontalLine.setWidth(298);
        infoHorizontalLine.setTabStop(false);

        imageLabel.setAutoSize(true);
        imageLabel.setLocation(8, 100);
        //imageLabel.setSize(39, 13);
        imageLabel.setText("Image:");

        shipPictureBox.setBackground(Color.WHITE);
        shipPictureBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        shipPictureBox.setLocation(85, 100);
        shipPictureBox.setSize(66, 54);
        shipPictureBox.setTabStop(false);

        prevImageButton.setAutoWidth(true);
        prevImageButton.setLocation(159, 100);
        prevImageButton.setSize(20, 18);
        prevImageButton.setTabIndex(6);
        prevImageButton.setText("<");
        prevImageButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                prevImageButtonClick();
            }
        });

        imageLabelValue.setAutoSize(true);
        imageLabelValue.setControlBinding(ControlBinding.CENTER);
        imageLabelValue.setLocation(192, 102);
        imageLabelValue.setSize(70, 13);
        imageLabelValue.setTabIndex(61);
        imageLabelValue.setText("Custom Ship");

        nextImageButton.setAutoWidth(true);
        nextImageButton.setControlBinding(ControlBinding.RIGHT);
        nextImageButton.setLocation(283, 100);
        nextImageButton.setSize(20, 18);
        nextImageButton.setTabIndex(7);
        nextImageButton.setText(">");
        nextImageButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                nextImageButtonClick();
            }
        });

        setCustomImageButton.setAutoWidth(true);
        setCustomImageButton.setControlBinding(ControlBinding.CENTER);
        setCustomImageButton.setLocation(178, 126);
        setCustomImageButton.setSize(106, 22);
        setCustomImageButton.setTabIndex(8);
        setCustomImageButton.setText("Set Custom...");
        setCustomImageButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                setCustomImageButtonClick();
            }
        });

        allocationPanel.setLocation(325, 0);
        allocationPanel.setSize(195, 231);
        allocationPanel.setTabStop(false);
        allocationPanel.setText("Space Allocation");

        allocationPanel.getControls().addAll(cargoBaysLabel, cargoBaysNum, fuelTanksLabel, fuelTanksNum,
                hullStrengthLabel, hullStrengthNum, weaponsSlotsLabel, weaponsSlotsNum, shieldSlotsLabel,
                shieldSlotsNum, gadgetSlotsLabel, gadgetSlotsNum, crewQuartersLabel, crewQuartersNum, unitsUsedLabel,
                unitsUsedLabelValue, pctOfMaxLabel, pctOfMaxLabelValue);

        cargoBaysLabel.setAutoSize(true);
        cargoBaysLabel.setLocation(8, 23);
        //cargoBaysLabel.setSize(66, 13);
        cargoBaysLabel.setText("Cargo Bays:");

        cargoBaysNum.setBackground(Color.WHITE);
        cargoBaysNum.setLocation(120, 21);
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
        cargoBaysNum.setMinimum(0);

        fuelTanksLabel.setAutoSize(true);
        fuelTanksLabel.setLocation(8, 47);
        //fuelTanksLabel.setSize(41, 13);
        fuelTanksLabel.setText("Range:");

        fuelTanksNum.setBackground(Color.WHITE);
        fuelTanksNum.setLocation(120, 45);
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
        fuelTanksNum.setMinimum(0);

        hullStrengthLabel.setAutoSize(true);
        hullStrengthLabel.setLocation(8, 71);
        //hullStrengthLabel.setSize(70, 13);
        hullStrengthLabel.setText("Hull Strength:");

        hullStrengthNum.setBackground(Color.WHITE);
        hullStrengthNum.setLocation(120, 69);
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
        hullStrengthNum.setMinimum(0);

        weaponsSlotsLabel.setAutoSize(true);
        weaponsSlotsLabel.setLocation(8, 95);
        //weaponsSlotsLabel.setSize(78, 13);
        weaponsSlotsLabel.setText("Weapon Slots:");

        weaponsSlotsNum.setBackground(Color.WHITE);
        weaponsSlotsNum.setLocation(120, 93);
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
        weaponsSlotsNum.setMinimum(0);

        shieldSlotsLabel.setAutoSize(true);
        shieldSlotsLabel.setLocation(8, 119);
        //shieldSlotsLabel.setSize(67, 13);
        shieldSlotsLabel.setText("Shield Slots:");

        shieldSlotsNum.setBackground(Color.WHITE);
        shieldSlotsNum.setLocation(120, 117);
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
        shieldSlotsNum.setMinimum(0);

        gadgetSlotsLabel.setAutoSize(true);
        gadgetSlotsLabel.setLocation(8, 143);
        //gadgetSlotsLabel.setSize(73, 13);
        gadgetSlotsLabel.setText("Gadget Slots:");

        gadgetSlotsNum.setBackground(Color.WHITE);
        gadgetSlotsNum.setLocation(120, 141);
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
        gadgetSlotsNum.setMinimum(0);

        crewQuartersLabel.setAutoSize(true);
        crewQuartersLabel.setLocation(8, 167);
        //crewQuartersLabel.setSize(81, 13);
        crewQuartersLabel.setText("Crew Quarters:");

        crewQuartersNum.setBackground(Color.WHITE);
        crewQuartersNum.setLocation(120, 165);
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
        crewQuartersNum.setMinimum(0);

        unitsUsedLabel.setAutoSize(true);
        unitsUsedLabel.setLocation(8, 191);
        unitsUsedLabel.setSize(63, 13);
        unitsUsedLabel.setText("Units Used:");

        unitsUsedLabelValue.setAutoSize(true);
        unitsUsedLabelValue.setLocation(120, 191);
        unitsUsedLabelValue.setSize(34, 13);
        //unitsUsedLabelValue.setText("888");

        pctOfMaxLabel.setAutoSize(true);
        pctOfMaxLabel.setLocation(8, 209);
        //pctOfMaxLabel.setSize(60, 13);
        pctOfMaxLabel.setText("% of Max:");

        pctOfMaxLabelValue.setAutoSize(true);
        pctOfMaxLabelValue.setFont(FontCollection.bold825);
        pctOfMaxLabelValue.setForeground(Color.RED);
        pctOfMaxLabelValue.setLocation(121, 212);
        //pctOfMaxLabelValue.setSize(34, 13);
        //pctOfMaxLabelValue.setText("888%");

        costsPanel.setLocation(325, 235);
        costsPanel.setSize(195, 108);
        costsPanel.setTabStop(false);
        costsPanel.setText("Costs");

        costsPanel.getControls().addAll(shipCostLabel, shipCostLabelValue, penaltyLabel,
                penaltyLabelValue, designFeeLabel, designFeeLabelValue, tradeInLabel,
                tradeInLabelValue, costsHorizontalLine, totalCostLabel, totalCostLabelValue);

        shipCostLabel.setAutoSize(true);
        shipCostLabel.setLocation(8, 19);
        //shipCostLabel.setSize(56, 13);
        shipCostLabel.setText("Ship Cost:");

        shipCostLabelValue.setAutoSize(true);
        shipCostLabelValue.setLocation(120, 19);
        //shipCostLabelValue.setSize(74, 16);
        //shipCostLabelValue.setText("8,888,888 cr.");

        penaltyLabel.setAutoSize(true);
        penaltyLabel.setLocation(8, 35);
        //penaltyLabel.setSize(96, 13);
        penaltyLabel.setText("Crowding Penalty:");

        penaltyLabelValue.setAutoSize(true);
        penaltyLabelValue.setLocation(120, 35);
        //penaltyLabelValue.setSize(74, 16);
        //penaltyLabelValue.setText("8,888,888 cr.");

        designFeeLabel.setAutoSize(true);
        designFeeLabel.setLocation(8, 51);
        //designFeeLabel.setSize(65, 13);
        designFeeLabel.setText("Design Fee:");

        designFeeLabelValue.setAutoSize(true);
        designFeeLabelValue.setLocation(120, 51);
        //designFeeLabelValue.setSize(74, 16);
        //designFeeLabelValue.setText("888,888 cr.");

        tradeInLabel.setAutoSize(true);
        tradeInLabel.setLocation(8, 67);
        tradeInLabel.setSize(77, 13);
        tradeInLabel.setText("Less Trade-In:");

        tradeInLabelValue.setAutoSize(true);
        tradeInLabelValue.setLocation(120, 67);
        //tradeInLabelValue.setSize(75, 16);
        //tradeInLabelValue.setText("-8,888,888 cr.");

        costsHorizontalLine.setLocation(8, 83);
        costsHorizontalLine.setWidth(179);
        costsHorizontalLine.setTabStop(false);

        totalCostLabel.setAutoSize(true);
        totalCostLabel.setLocation(8, 87);
        //totalCostLabel.setSize(59, 13);
        totalCostLabel.setText("Total Cost:");

        totalCostLabelValue.setAutoSize(true);
        totalCostLabelValue.setLocation(120, 87);
        //totalCostLabelValue.setSize(74, 16);
        //totalCostLabelValue.setText("8,888,888 cr.");

        cancelButton.setDialogResult(DialogResult.CANCEL);
        cancelButton.setAutoWidth(true);
        cancelButton.setControlBinding(ControlBinding.LEFT);
        cancelButton.setLocation(325, 349);
        cancelButton.setSize(88, 22);
        cancelButton.setTabIndex(5);
        cancelButton.setText("Cancel Design");

        constructButton.setAutoWidth(true);
        constructButton.setControlBinding(ControlBinding.RIGHT);
        constructButton.setForeground(SystemColors.CONTROL_TEXT);
        constructButton.setLocation(430, 349);
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

        disabledNameTipLabel.setBackground(SystemColors.INFO);
        disabledNameTipLabel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        //disabledNameTipLabel.setAutoSize(true);
        disabledNameTipLabel.setLocation(87, 232);
        disabledNameTipLabel.setSize(195, 20);
        disabledNameTipLabel.setText("You must enter a Ship Name.");
        disabledNameTipLabel.setVisible(false);

        disabledPctTipLabel.setBackground(SystemColors.INFO);
        disabledPctTipLabel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        //disabledPctTipLabel.setAutoSize(true);
        disabledPctTipLabel.setControlBinding(ControlBinding.RIGHT);
        disabledPctTipLabel.setLocation(215, 189);
        disabledPctTipLabel.setSize(305, 20);
        disabledPctTipLabel.setText("Your % of Max must be less than or equal to 100%.");
        disabledPctTipLabel.setVisible(false);

        controls.addAll(disabledPctTipLabel, welcomePanel, disabledNameTipLabel, infoPanel, allocationPanel,
                costsPanel, cancelButton, constructButton);

        ReflectionUtils.loadControlsData(this);
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
                shipNameTextBox.setText(Game.getCurrentGame().getShip().getName());
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

            if (templateComboBox.getItems().getElementAt(0).toString().equals(Strings.ShipNameModified)) {
                templateComboBox.getItems().removeElementAt(0);
            }

            loading = false;
        }
    }

    private void loadSizes() {
        sizes = new ArrayList<>(6);

        for (Size size : shipyard.getAvailableSizes()) {
            sizes.add(size);
            sizeComboBox.getItems().addElement(Functions.stringVars(Strings.ShipyardSizeItem, Strings.Sizes[size.castToInt()],
                    Shipyard.MAX_UNITS[size.castToInt()] + " " + Strings.ShipyardUnit));
        }
    }

    private void loadTemplateList() {
        ShipTemplate currentShip = new ShipTemplate(Game.getCurrentGame().getShip(), Strings.ShipNameCurrentShip);
        templateComboBox.getItems().addElement(currentShip);

        templateComboBox.getItems().addElement(Consts.ShipTemplateSeparator);

        // Add the minimal sizes templates.
        for (Size size : sizes)
            templateComboBox.getItems().addElement(new ShipTemplate(size, Strings.Sizes[size.castToInt()]
                    + " " + Strings.ShipNameTemplateSuffixMinimum));

        templateComboBox.getItems().addElement(Consts.ShipTemplateSeparator);

        // Add the buyable ship spec templates.
        for (ShipSpec spec : Consts.ShipSpecs) {
            if (sizes.contains(spec.getSize()) && spec.getType().castToInt() <= Consts.MaxShip) {
                templateComboBox
                        .getItems().addElement(new ShipTemplate(spec, spec.getName() + " " + Strings.ShipNameTemplateSuffixDefault));
            }
        }

        templateComboBox.getItems().addElement(Consts.ShipTemplateSeparator);

        // Add the user-created templates.
        ArrayList<ShipTemplate> userTemplates = new ArrayList<>();
        for (String fileName : IOUtils.getFiles(Consts.CustomTemplatesDirectory, "*.sst")) {
            IOUtils.readObjectFromFile(fileName, true).map(t -> {
                ShipTemplate template = (ShipTemplate) t;
                if (sizes.contains(template.getSize())) {
                    userTemplates.add(template);
                }
                return null;
            });
        }
        userTemplates.sort();
        templateComboBox.getItems().addAll(userTemplates);

        templateComboBox.setSelectedIndex(0);
    }

    private boolean isSaveButtonEnabled() {
        return shipNameTextBox.getText().length() > 0;
    }

    private void setTemplateModified() {
        if (!loading && templateComboBox.getItems().getSize() > 0) {
            if (!templateComboBox.getItems().getElementAt(0).toString().equals(Strings.ShipNameModified)) {
                templateComboBox.getItems().insertElementAt(Strings.ShipNameModified, 0);
            }
            templateComboBox.setSelectedIndex(0);
        }
    }

    private void updateAllocation() {
        boolean fuelMinimum = (fuelTanksNum.getValue() <= fuelTanksNum.getMinimum());
        boolean hullMinimum = (hullStrengthNum.getValue() <= hullStrengthNum.getMinimum());

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
            if (Game.getCurrentGame().getCommander()
                    .isTradeShip(shipyard.getShipSpec(), shipyard.getTotalCost(), shipNameTextBox.getText())) {
                Strings.ShipNames[ShipType.CUSTOM.castToInt()] = shipNameTextBox.getText();

                // Replace the current custom images with the new ones.
                if (Game.getCurrentGame().getShip().getImageIndex() == ShipType.CUSTOM.castToInt()) {
                    GuiEngine.getImageProvider().setCustomShipImages(customImages);

                    Game.getCurrentGame().getShip().updateCustomImageOffsetConstants();
                }

                GuiFacade.alert(AlertType.ShipDesignThanks, this, shipyard.getName());
                Game.getCurrentGame().getParentWindow().updateAll();
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

                IOUtils.writeObjectToFile(saveDialog.getFileName(), template);

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
                customImages[Consts.ShipImgOffsetShieldDamage] = imageShieldsDamaged;
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
