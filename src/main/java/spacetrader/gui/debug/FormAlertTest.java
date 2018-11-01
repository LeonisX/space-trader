package spacetrader.gui.debug;

import spacetrader.controls.*;
import spacetrader.controls.enums.*;
import spacetrader.game.Consts;
import spacetrader.game.SpecialEvent;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.gui.FormAlert;
import spacetrader.gui.SpaceTraderForm;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.ReflectionUtils;

import java.util.Arrays;

//TODO untranslated, unrefactored, untested
public class FormAlertTest extends SpaceTraderForm {

    private Label lblAlertType = new Label();
    private Panel boxAlert = new Panel();
    private Label lblValue2 = new Label();
    private Label lblValue1 = new Label();
    private Label lblValue3 = new Label();
    private ComboBox<AlertType> selAlertType = new ComboBox<>();
    private TextBox txtValue1 = new TextBox();
    private TextBox txtValue2 = new TextBox();
    private TextBox txtValue3 = new TextBox();
    private Panel panel1 = new Panel();
    private Button btnTestAlert = new Button();
    private Button btnTestSpecialEvent = new Button();
    private ComboBox<SpecialEventType> selSpecialEvent = new ComboBox<>();
    private Label lblSpecialEvent = new Label();

    public static void main(String[] args) {
        Launcher.runForm(new FormAlertTest());
    }

    public FormAlertTest() {
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
        ReflectionUtils.setAllComponentNames(this);

        this.boxAlert.suspendLayout();
        this.panel1.suspendLayout();
        this.suspendLayout();
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
        this.boxAlert.setSize(new Size(300, 152));
        this.boxAlert.setTabIndex(1);
        this.boxAlert.setTabStop(false);
        this.boxAlert.setText("Test Alert");
        //
        // btnTestAlert
        //
        this.btnTestAlert.setFlatStyle(FlatStyle.FLAT);
        this.btnTestAlert.setLocation(new java.awt.Point(80, 120));
        this.btnTestAlert.setName("btnTestAlert");
        this.btnTestAlert.setSize(new Size(41, 22));
        this.btnTestAlert.setTabIndex(8);
        this.btnTestAlert.setText("Test");
        this.btnTestAlert.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                btnTestAlert_Click();
            }
        });
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
        this.selAlertType.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
        this.selAlertType.setLocation(new java.awt.Point(72, 16));
        this.selAlertType.setName("selAlertType");
        this.selAlertType.setSize(new Size(200, 21));
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
        // panel1
        //
        this.panel1.getControls().addAll((new BaseComponent[]{
                this.btnTestSpecialEvent,
                this.selSpecialEvent,
                this.lblSpecialEvent}));
        this.panel1.setLocation(new java.awt.Point(8, 168));
        this.panel1.setName("panel1");
        this.panel1.setSize(new Size(200, 80));
        this.panel1.setTabIndex(2);
        this.panel1.setTabStop(false);
        this.panel1.setText("Test Special Alert");
        //
        // btnTestSpecialEvent
        //
        this.btnTestSpecialEvent.setFlatStyle(FlatStyle.FLAT);
        this.btnTestSpecialEvent.setLocation(new java.awt.Point(80, 48));
        this.btnTestSpecialEvent.setName("btnTestSpecialEvent");
        this.btnTestSpecialEvent.setSize(new Size(41, 22));
        this.btnTestSpecialEvent.setTabIndex(8);
        this.btnTestSpecialEvent.setText("Test");
        this.btnTestSpecialEvent.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                btnTestSpecialEvent_Click();
            }
        });
        //
        // selSpecialEvent
        //
        this.selSpecialEvent.setDropDownStyle(ComboBoxStyle.DROP_DOWN_LIST);
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
        // FormAlertTest
        //
        this.setAutoScaleBaseSize(new Size(5, 13));
        this.setClientSize(new Size(370, 255));
        this.controls.addAll(Arrays.asList(
                this.panel1,
                this.boxAlert));
        this.setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormAlertTest");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setText("Test");

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
}