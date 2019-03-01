package spacetrader.gui.debug;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.*;
import spacetrader.game.enums.*;
import spacetrader.gui.*;
import spacetrader.gui.cheat.FormMonster;
import spacetrader.guifacade.GuiEngine;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.Arrays;

//TODO untranslated, unrefactored, untested
public class FormsTest extends SpaceTraderForm {

    private Panel languagesPanel = new Panel();
    private ComboBox<String> languagesComboBox = new ComboBox<>();

    private Panel mainPanel = new Panel();
    private Button formAboutButton = new Button();
    private Button formAlertsButton = new Button();
    private Button formBuyFuelButton = new Button();
    private Button formBuyRepairsButton = new Button();
    private Button formCargoBuyButton = new Button();
    private Button formCargoSellButton = new Button();
    private Button formCostsButton = new Button();

    private Panel encounterPanel = new Panel();
    private ComboBox<EncounterType> encounterComboBox = new ComboBox<>();
    private Button formEncounterButton = new Button();

    private Button formEquipmentButton = new Button();
    private Button formFindButton = new Button();
    private Button formGetLoanButton = new Button();
    private Button formJettisonButton = new Button();
    private Button formNewCommanderButton = new Button();
    private Button formOptionsButton = new Button();
    private Button formPayBackLoanButton = new Button();
    private Button formPlunderButton = new Button();
    private Button formShipListButton = new Button();
    private Button formShipyardButton = new Button();

    private Panel mainPanel2 = new Panel();
    private Button formViewBankButton = new Button();
    private Button formViewCommanderButton = new Button();
    private Button formViewHighScoresButton = new Button();
    private Button formViewPersonnelButton = new Button();
    private Button formViewQuestsButton = new Button();
    private Button formViewShipButton = new Button();

    private Button formMonsterButton = new Button();

    public FormsTest() {
        GlobalAssets.loadLanguageFromRegistry();
        GlobalAssets.loadVersion();
        GlobalAssets.loadStrings();
        GlobalAssets.initializeImages();
        GuiEngine.installImplementation(new OriginalGuiImplementationProvider());

        // Need to initialize pluralMap
        ReflectionUtils.loadStrings(GlobalAssets.getStrings());

        if (null == Game.getCurrentGame()) {
            Game game = new Game("name", Difficulty.BEGINNER, 8, 8, 8, 8, null);

            game.setParentWindow(new SpaceTrader(null));
            game.getShip().getCargo()[1] = 12;
            game.getCommander().setCash(65535);
            game.getShip().setCrewQuarters(5);
            game.getShip().setCrew(new CrewMember[5]);
            game.getShip().getCrew()[0] = Game.getCurrentGame().getMercenaries().get(0);
            game.getShip().setFuelTanks(24);
            game.getShip().setFuel(10);
            game.getShip().setHullStrength(100);
            game.getShip().setHull(50);
            game.getShip().setGadgetSlots(10);
            game.getShip().setShieldSlots(10);
            game.getShip().setWeaponSlots(10);
            game.getShip().setWeapons(new Weapon[game.getShip().getWeaponSlots()]);
            game.getShip().setShields(new Shield[game.getShip().getShieldSlots()]);
            game.getShip().setGadgets(new Gadget[game.getShip().getGadgetSlots()]);
            game.getCommander().setReputationScore(10);

            for (int i = 0; i < Consts.Weapons.length; i++) {
                game.getShip().addEquipment(Consts.Weapons[i]);
            }
            for (int i = 0; i < Consts.Shields.length; i++) {
                game.getShip().addEquipment(Consts.Shields[i]);
            }
            for (int i = 0; i < Consts.Gadgets.length; i++) {
                game.getShip().addEquipment(Consts.Gadgets[i]);
            }

            game.setSelectedSystemId(StarSystemId.Aldea);
            game.getSelectedSystem().setShipyardId(ShipyardId.INCOM);
            game.getSelectedSystem().setTechLevel(TechLevel.HI_TECH);
            game.warpDirect();
            game.getSelectedSystem().getMercenariesForHire().add(Game.getCurrentGame().getMercenaries().get(0));
            game.getCommander().getCurrentSystem().setShipyardId(ShipyardId.CORELLIAN);
        }

        initializeComponent();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formsTest");
        setText("Test dialogs");
        setClientSize(600, 500);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);

        languagesPanel.setLocation(4, 4);
        languagesPanel.setSize(200, 50);
        languagesPanel.setTabStop(false);
        languagesPanel.setText("Languages");

        languagesPanel.getControls().add(languagesComboBox);

        languagesComboBox.setLocation(8, 21);
        languagesComboBox.setSize(104, 21);
        languagesComboBox.setTabIndex(4);
        Arrays.stream(Language.values()).forEach(lang -> languagesComboBox.getItems().addElement(Functions.capitalize(lang.toString())));
        languagesComboBox.setSelectedIndex(GlobalAssets.getLanguage().ordinal());
        languagesComboBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                languageItemChanged();
            }
        });

        mainPanel.setLocation(4, 56);
        mainPanel.setSize(200, 440);
        mainPanel.setTabStop(false);
        mainPanel.setText("Simple dialogs");

        formAboutButton.setLocation(8, 23);
        formAboutButton.setSize(125, 22);
        formAboutButton.setText("FormAbout");
        formAboutButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormAbout().showDialog();
            }
        });

        formAlertsButton.setLocation(8, 46);
        formAlertsButton.setSize(125, 22);
        formAlertsButton.setText("FormAlert");
        formAlertsButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormAlertTest().showDialog();
            }
        });

        formBuyFuelButton.setLocation(8, 69);
        formBuyFuelButton.setSize(125, 22);
        formBuyFuelButton.setText("FormBuyFuel");
        formBuyFuelButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormBuyFuel().showDialog();
            }
        });

        formBuyRepairsButton.setLocation(8, 92);
        formBuyRepairsButton.setSize(125, 22);
        formBuyRepairsButton.setText("FormBuyRepairs");
        formBuyRepairsButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormBuyRepairs().showDialog();
            }
        });

        formCargoBuyButton.setLocation(8, 115);
        formCargoBuyButton.setSize(125, 22);
        formCargoBuyButton.setText("FormCargoBuy");
        formCargoBuyButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormCargoBuy(1, 1, CargoBuyOp.BUY_TRADER).showDialog();
            }
        });

        formCargoSellButton.setLocation(8, 138);
        formCargoSellButton.setSize(125, 22);
        formCargoSellButton.setText("FormCargoSell");
        formCargoSellButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormCargoSell(1, 1, CargoSellOp.JETTISON, 255).showDialog();
            }
        });

        formCostsButton.setLocation(8, 161);
        formCostsButton.setSize(125, 22);
        formCostsButton.setText("FormCosts");
        formCostsButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormCosts().showDialog();
            }
        });

        encounterPanel.setText("Encounter");
        encounterPanel.setControlBinding(ControlBinding.LEFT);
        encounterPanel.setForeground(Color.WHITE);
        encounterPanel.setLocation(220, 56);
        encounterPanel.setSize(180, 80);
        encounterPanel.setTabStop(false);

        encounterPanel.getControls().addAll(encounterComboBox, formEncounterButton);

        encounterComboBox.setLocation(8, 20);
        encounterComboBox.setSize(160, 21);
        encounterComboBox.setTabIndex(10);
        encounterComboBox.getItems().addAll(EncounterType.values());
        encounterComboBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                Game.getCurrentGame().getEncounter().isDetermineVeryRareEncounter();
                Game.getCurrentGame().getEncounter().setEncounterType((EncounterType) encounterComboBox.getSelectedItem());
                new FormEncounter().showDialog();
            }
        });

        formEncounterButton.setLocation(8, 44);
        formEncounterButton.setSize(90, 22);
        formEncounterButton.setText("FormEncounter");
        formEncounterButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Game.getCurrentGame().getEncounter().isDetermineEncounter();
                new FormEncounter().showDialog();
            }
        });


        formEquipmentButton.setLocation(8, 184);
        formEquipmentButton.setSize(125, 22);
        formEquipmentButton.setText("FormEquipment");
        formEquipmentButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormEquipment().showDialog();
            }
        });

        formFindButton.setLocation(8, 207);
        formFindButton.setSize(125, 22);
        formFindButton.setText("FormFind");
        formFindButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormFind("").showDialog();
            }
        });

        formGetLoanButton.setLocation(8, 230);
        formGetLoanButton.setSize(125, 22);
        formGetLoanButton.setText("FormGetLoan");
        formGetLoanButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormGetLoan(25000).showDialog();
            }
        });

        formJettisonButton.setLocation(8, 253);
        formJettisonButton.setSize(125, 22);
        formJettisonButton.setText("FormJettison");
        formJettisonButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormJettison().showDialog();
            }
        });

        formNewCommanderButton.setLocation(8, 276);
        formNewCommanderButton.setSize(125, 22);
        formNewCommanderButton.setText("FormNewCommander");
        formNewCommanderButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormNewCommander().showDialog();
            }
        });

        formOptionsButton.setLocation(8, 299);
        formOptionsButton.setSize(125, 22);
        formOptionsButton.setText("FormOptions");
        formOptionsButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormOptions().showDialog();
            }
        });

        formPayBackLoanButton.setLocation(8, 322);
        formPayBackLoanButton.setSize(125, 22);
        formPayBackLoanButton.setText("FormPayBackLoan");
        formPayBackLoanButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormPayBackLoan().showDialog();
            }
        });

        formPlunderButton.setLocation(8, 345);
        formPlunderButton.setSize(125, 22);
        formPlunderButton.setText("FormPlunder");
        formPlunderButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormPlunder().showDialog();
            }
        });

        formShipListButton.setLocation(8, 368);
        formShipListButton.setSize(125, 22);
        formShipListButton.setText("FormShipList");
        formShipListButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormShipList().showDialog();
            }
        });

        formShipyardButton.setLocation(8, 391);
        formShipyardButton.setSize(125, 22);
        formShipyardButton.setText("FormShipyard");
        formShipyardButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormShipyard().showDialog();
            }
        });

        mainPanel.getControls().addAll(formAboutButton, formAlertsButton, formBuyFuelButton, formBuyRepairsButton,
                formCargoBuyButton, formCargoSellButton, formCostsButton, formEquipmentButton, formFindButton,
                formGetLoanButton, formJettisonButton, formNewCommanderButton, formOptionsButton, formPayBackLoanButton,
                formPlunderButton, formShipListButton, formShipyardButton);

        mainPanel2.setLocation(220, 140);
        mainPanel2.setSize(200, 400);
        mainPanel2.setTabStop(false);
        mainPanel2.setText("Simple dialogs2");

        formViewBankButton.setLocation(8, 23);
        formViewBankButton.setSize(125, 22);
        formViewBankButton.setText("FormViewBank");
        formViewBankButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormViewBank().showDialog();
            }
        });

        formViewCommanderButton.setLocation(8, 46);
        formViewCommanderButton.setSize(125, 22);
        formViewCommanderButton.setText("FormViewCommander");
        formViewCommanderButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormViewCommander().showDialog();
            }
        });

        formViewHighScoresButton.setLocation(8, 69);
        formViewHighScoresButton.setSize(125, 22);
        formViewHighScoresButton.setText("FormViewHighScores");
        formViewHighScoresButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormViewHighScores().showDialog();
            }
        });

        formViewPersonnelButton.setLocation(8, 92);
        formViewPersonnelButton.setSize(125, 22);
        formViewPersonnelButton.setText("FormViewPersonnel");
        formViewPersonnelButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormViewPersonnel().showDialog();
            }
        });

        formViewQuestsButton.setLocation(8, 115);
        formViewQuestsButton.setSize(125, 22);
        formViewQuestsButton.setText("FormViewQuests");
        formViewQuestsButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormViewQuests().showDialog();
            }
        });

        formViewShipButton.setLocation(8, 138);
        formViewShipButton.setSize(125, 22);
        formViewShipButton.setText("FormViewShip");
        formViewShipButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormViewShip().showDialog();
            }
        });

        formMonsterButton.setLocation(8, 180);
        formMonsterButton.setSize(125, 22);
        formMonsterButton.setText("FormMonster");
        formMonsterButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                new FormMonster().showDialog();
            }
        });

        mainPanel2.getControls().addAll(formViewBankButton, formViewCommanderButton, formViewHighScoresButton,
                formViewPersonnelButton, formViewQuestsButton, formViewShipButton, formMonsterButton);

        controls.addAll(languagesPanel, mainPanel, encounterPanel, mainPanel2/*, boxAlert*/);

        ReflectionUtils.loadControlsData(this);
    }

    //TODO unify?
    private void languageItemChanged() {
        GlobalAssets.setLanguage(Language.values()[languagesComboBox.getSelectedIndex()]);
        GlobalAssets.loadStrings();

        ReflectionUtils.loadControlsStrings(asSwingObject(), getName(), GlobalAssets.getStrings());
        ReflectionUtils.loadStrings(GlobalAssets.getStrings());
    }
}
