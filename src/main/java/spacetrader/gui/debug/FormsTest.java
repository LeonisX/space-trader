package spacetrader.gui.debug;

import spacetrader.controls.*;
import spacetrader.controls.Size;
import spacetrader.controls.enums.*;
import spacetrader.game.Consts;
import spacetrader.game.Game;
import spacetrader.game.GlobalAssets;
import spacetrader.game.SpecialEvent;
import spacetrader.game.enums.*;
import spacetrader.gui.*;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.ReflectionUtils;

//TODO untranslated, unrefactored, untested
public class FormsTest extends SpaceTraderForm {

    private Label lblAlertType;
    private Panel boxAlert;
    private Label lblValue2;
    private Label lblValue1;
    private Label lblValue3;
    private ComboBox<AlertType> selAlertType;
    private TextBox txtValue1;
    private TextBox txtValue2;
    private TextBox txtValue3;

    private Button btnTestAlert;
    private Button btnTestSpecialEvent;
    private ComboBox<SpecialEventType> selSpecialEvent;
    private Label lblSpecialEvent;

    private Panel languagesPanel = new Panel();
    private ComboBox<String> languagesComboBox = new ComboBox<>();

    private Panel mainPanel = new Panel();
    private Button formAboutButton = new Button();
    private Button formAlertsButton = new Button();
    private Button formBuyFuelButton = new Button();
    private Button formBuyRepairsButton = new Button();
    private Button formFormCargoBuyButton = new Button();
    private Button formFormCargoSellButton = new Button();

    public static void main(String[] args) {
        Launcher.runForm(new FormsTest());
    }

    public FormsTest() {
        GlobalAssets.loadStrings("english");

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
        setClientSize(370, 255);
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
                formFormCargoBuyButton, formFormCargoSellButton);

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

        formFormCargoBuyButton.setLocation(8, 115);
        formFormCargoBuyButton.setSize(90, 22);
        formFormCargoBuyButton.setText("FormCargoBuy");
        formFormCargoBuyButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Launcher.runForm(new FormCargoBuy(1, 1, CargoBuyOp.BUY_TRADER));
            }
        });

        formFormCargoSellButton.setLocation(8, 138);
        formFormCargoSellButton.setSize(90, 22);
        formFormCargoSellButton.setText("FormCargoSell");
        formFormCargoSellButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                Launcher.runForm(new FormCargoSell(1, 1, CargoSellOp.JETTISON, 255));
            }
        });


        lblAlertType = new Label();
        boxAlert = new Panel();
        btnTestAlert = new Button();
        txtValue3 = new TextBox();
        txtValue2 = new TextBox();
        txtValue1 = new TextBox();
        selAlertType = new ComboBox<>();
        lblValue3 = new Label();
        lblValue1 = new Label();
        lblValue2 = new Label();
        btnTestSpecialEvent = new Button();
        selSpecialEvent = new ComboBox<>();
        lblSpecialEvent = new Label();
        //
        // lblAlertType
        //
        lblAlertType.setAutoSize(true);
        lblAlertType.setLocation(new java.awt.Point(8, 19));
        lblAlertType.setName("lblAlertType");
        lblAlertType.setSize(new Size(56, 13));
        lblAlertType.setTabIndex(0);
        lblAlertType.setText("Alert Type");
        //
        // boxAlert
        //
        boxAlert.getControls().addAll((new BaseComponent[]{
                btnTestAlert,
                txtValue3,
                txtValue2,
                txtValue1,
                selAlertType,
                lblValue3,
                lblValue1,
                lblValue2,
                lblAlertType}));
        boxAlert.setLocation(new java.awt.Point(8, 8));
        boxAlert.setName("boxAlert");
        boxAlert.setSize(new Size(200, 152));
        boxAlert.setTabIndex(1);
        boxAlert.setTabStop(false);
        boxAlert.setText("Test Alert");
        //
        // btnTestAlert
        //
        btnTestAlert.setFlatStyle(FlatStyle.FLAT);
        btnTestAlert.setLocation(new java.awt.Point(80, 120));
        btnTestAlert.setName("btnTestAlert");
        btnTestAlert.setSize(new Size(41, 22));
        btnTestAlert.setTabIndex(8);
        btnTestAlert.setText("Test");
        btnTestAlert.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                btnTestAlert_Click();
            }
        });
        //
        // txtValue3
        //
        txtValue3.setLocation(new java.awt.Point(72, 88));
        txtValue3.setName("txtValue3");
        txtValue3.setSize(new Size(120, 20));
        txtValue3.setTabIndex(7);
        txtValue3.setText("");
        //
        // txtValue2
        //
        txtValue2.setLocation(new java.awt.Point(72, 64));
        txtValue2.setName("txtValue2");
        txtValue2.setSize(new Size(120, 20));
        txtValue2.setTabIndex(6);
        txtValue2.setText("");
        //
        // txtValue1
        //
        txtValue1.setLocation(new java.awt.Point(72, 40));
        txtValue1.setName("txtValue1");
        txtValue1.setSize(new Size(120, 20));
        txtValue1.setTabIndex(5);
        txtValue1.setText("");
        //
        // selAlertType
        //
        selAlertType.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        selAlertType.setLocation(new java.awt.Point(72, 16));
        selAlertType.setName("selAlertType");
        selAlertType.setSize(new Size(120, 21));
        selAlertType.setTabIndex(4);
        //
        // lblValue3
        //
        lblValue3.setAutoSize(true);
        lblValue3.setLocation(new java.awt.Point(8, 91));
        lblValue3.setName("lblValue3");
        lblValue3.setSize(new Size(43, 13));
        lblValue3.setTabIndex(3);
        lblValue3.setText("Value 3");
        //
        // lblValue1
        //
        lblValue1.setAutoSize(true);
        lblValue1.setLocation(new java.awt.Point(8, 43));
        lblValue1.setName("lblValue1");
        lblValue1.setSize(new Size(43, 13));
        lblValue1.setTabIndex(2);
        lblValue1.setText("Value 1");
        //
        // lblValue2
        //
        lblValue2.setAutoSize(true);
        lblValue2.setLocation(new java.awt.Point(8, 67));
        lblValue2.setName("lblValue2");
        lblValue2.setSize(new Size(43, 13));
        lblValue2.setTabIndex(1);
        lblValue2.setText("Value 2");
        //
        // mainPanel
        //

        //
        // btnTestSpecialEvent
        //
        btnTestSpecialEvent.setFlatStyle(FlatStyle.FLAT);
        btnTestSpecialEvent.setLocation(new java.awt.Point(80, 48));
        btnTestSpecialEvent.setName("btnTestSpecialEvent");
        btnTestSpecialEvent.setSize(new Size(41, 22));
        btnTestSpecialEvent.setTabIndex(8);
        btnTestSpecialEvent.setText("Test");
        btnTestSpecialEvent.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                btnTestSpecialEvent_Click();
            }
        });
        //
        // selSpecialEvent
        //
        selSpecialEvent.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        selSpecialEvent.setLocation(new java.awt.Point(88, 16));
        selSpecialEvent.setName("selSpecialEvent");
        selSpecialEvent.setSize(new Size(104, 21));
        selSpecialEvent.setTabIndex(4);
        //
        // lblSpecialEvent
        //
        lblSpecialEvent.setAutoSize(true);
        lblSpecialEvent.setLocation(new java.awt.Point(8, 19));
        lblSpecialEvent.setName("lblSpecialEvent");
        lblSpecialEvent.setSize(new Size(73, 13));
        lblSpecialEvent.setTabIndex(0);
        lblSpecialEvent.setText("Special Event");

        controls.addAll(languagesPanel, mainPanel/*, boxAlert*/);

        ReflectionUtils.loadControlsData(this);
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

    //TODO unify?
    private void languageItemChanged() {

        String fileName = ((String) languagesComboBox.getSelectedItem()).toLowerCase();

        GlobalAssets.loadStrings(fileName);

        ReflectionUtils.loadControlsStrings(asSwingObject(), getName(), GlobalAssets.getStrings());
        ReflectionUtils.loadStrings(GlobalAssets.getStrings());
    }
}
