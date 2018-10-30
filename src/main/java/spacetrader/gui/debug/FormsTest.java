package spacetrader.gui.debug;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.*;
import spacetrader.game.Game;
import spacetrader.game.GlobalAssets;
import spacetrader.game.enums.*;
import spacetrader.gui.*;
import spacetrader.guifacade.GuiEngine;
import spacetrader.util.ReflectionUtils;

import java.awt.*;

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

    public static void main(String[] args) {
        Launcher.runForm(new FormsTest());
    }

    public FormsTest() {
        GlobalAssets.loadStrings("english");
        GlobalAssets.initializeImages();
        GuiEngine.installImplementation(new OriginalGuiImplementationProvider());

        Game game = new Game("name", Difficulty.BEGINNER, 8, 8, 8, 8, null);
        game.getCommander().getShip().getCargo()[1] = 12;
        game.getCommander().setCash(65535);
        game.getCommander().getShip().setFuelTanks(24);
        game.getCommander().getShip().setFuel(10);
        game.getCommander().getShip().setHullStrength(100);
        game.getCommander().getShip().setHull(50);

        game.setSelectedSystemId(StarSystemId.Aldea);
        game.warpDirect();
        Game.getCurrentGame().getCommander().getCurrentSystem().setShipyardId(ShipyardId.CORELLIAN);

        initializeComponent();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formsTest");
        setText("Test dialogs");
        setClientSize(600, 255);
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

        languagesComboBox.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        languagesComboBox.setLocation(8, 21);
        languagesComboBox.setSize(104, 21);
        languagesComboBox.setTabIndex(4);
        languagesComboBox.getItems().addAll("English", "Russian");
        languagesComboBox.setSelectedIndex(0);
        languagesComboBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                languageItemChanged();
            }
        });

        mainPanel.setLocation(4, 56);
        mainPanel.setSize(200, 300);
        mainPanel.setTabStop(false);
        mainPanel.setText("Simple dialogs");

        mainPanel.getControls().addAll(formAboutButton, formAlertsButton, formBuyFuelButton, formBuyRepairsButton,
                formCargoBuyButton, formCargoSellButton, formCostsButton, formEncounterButton);

        formAboutButton.setLocation(8, 23);
        formAboutButton.setSize(90, 22);
        formAboutButton.setText("FormAbout");
        formAboutButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Launcher.runForm(new FormAbout());
            }
        });

        formAlertsButton.setLocation(8, 46);
        formAlertsButton.setSize(90, 22);
        formAlertsButton.setText("FormAlert");
        formAlertsButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Launcher.runForm(new FormAlertTest());
            }
        });

        formBuyFuelButton.setLocation(8, 69);
        formBuyFuelButton.setSize(90, 22);
        formBuyFuelButton.setText("FormBuyFuel");
        formBuyFuelButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Launcher.runForm(new FormBuyFuel());
            }
        });

        formBuyRepairsButton.setLocation(8, 92);
        formBuyRepairsButton.setSize(90, 22);
        formBuyRepairsButton.setText("FormBuyRepairs");
        formBuyRepairsButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Launcher.runForm(new FormBuyRepairs());
            }
        });

        formCargoBuyButton.setLocation(8, 115);
        formCargoBuyButton.setSize(90, 22);
        formCargoBuyButton.setText("FormCargoBuy");
        formCargoBuyButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Launcher.runForm(new FormCargoBuy(1, 1, CargoBuyOp.BUY_TRADER));
            }
        });

        formCargoSellButton.setLocation(8, 138);
        formCargoSellButton.setSize(90, 22);
        formCargoSellButton.setText("FormCargoSell");
        formCargoSellButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Launcher.runForm(new FormCargoSell(1, 1, CargoSellOp.JETTISON, 255));
            }
        });

        formCostsButton.setLocation(8, 161);
        formCostsButton.setSize(90, 22);
        formCostsButton.setText("FormCosts");
        formCostsButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Launcher.runForm(new FormCosts());
            }
        });

        encounterPanel.setText("Encounter");
        encounterPanel.setControlBinding(ControlBinding.LEFT);
        encounterPanel.setForeground(Color.WHITE);
        encounterPanel.setLocation(220, 56);
        encounterPanel.setSize(180, 80);
        encounterPanel.setTabStop(false);

        encounterPanel.getControls().addAll(encounterComboBox, formEncounterButton);

        encounterComboBox.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        encounterComboBox.setLocation(8, 20);
        encounterComboBox.setSize(160, 21);
        encounterComboBox.setTabIndex(10);
        encounterComboBox.getItems().addAll(EncounterType.values());
        encounterComboBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                Game.getCurrentGame().isDetermineVeryRareEncounter();
                Game.getCurrentGame().setEncounterType((EncounterType) encounterComboBox.getSelectedItem());
                Launcher.runForm(new FormEncounter());
            }
        });

        formEncounterButton.setLocation(8, 44);
        formEncounterButton.setSize(90, 22);
        formEncounterButton.setText("FormEncounter");
        formEncounterButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Game.getCurrentGame().isDetermineEncounter();
                Launcher.runForm(new FormEncounter());
            }
        });

        controls.addAll(languagesPanel, mainPanel, encounterPanel/*, boxAlert*/);

        ReflectionUtils.loadControlsData(this);
    }

    //TODO unify?
    private void languageItemChanged() {

        String fileName = ((String) languagesComboBox.getSelectedItem()).toLowerCase();

        GlobalAssets.loadStrings(fileName);

        ReflectionUtils.loadControlsStrings(asSwingObject(), getName(), GlobalAssets.getStrings());
        ReflectionUtils.loadStrings(GlobalAssets.getStrings());
    }
}
