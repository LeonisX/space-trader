package spacetrader.gui.debug;

import spacetrader.controls.*;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Game;
import spacetrader.game.enums.AlertType;
import spacetrader.game.quest.QuestDialog;
import spacetrader.game.quest.enums.MessageType;
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
    private ComboBox<QuestDialog> selSpecialEvent = new ComboBox<>();
    private Label lblSpecialEvent = new Label();

    public FormAlertTest() {
        initializeComponent();

        AlertType[] alerts = Arrays.copyOfRange(AlertType.values(), AlertType.Alert.ordinal(), AlertType.TravelUneventfulTrip.ordinal());
        for (AlertType type : alerts) {
            selAlertType.getItems().addElement(type);
        }
        selAlertType.setSelectedIndex(0);

        Game.getCurrentGame().getQuestSystem().getQuestDialogsStream().forEach(d -> selSpecialEvent.getItems().addElement(d));
        selSpecialEvent.setSelectedIndex(0);
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        //
        // lblAlertType
        //
        this.lblAlertType.setAutoSize(true);
        this.lblAlertType.setLocation(8, 19);
        this.lblAlertType.setName("lblAlertType");
        this.lblAlertType.setSize(56, 13);
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
        this.boxAlert.setLocation(8, 8);
        this.boxAlert.setName("boxAlert");
        this.boxAlert.setSize(300, 152);
        this.boxAlert.setTabIndex(1);
        this.boxAlert.setTabStop(false);
        this.boxAlert.setText("Test Alert");
        //
        // btnTestAlert
        //
        this.btnTestAlert.setLocation(80, 120);
        this.btnTestAlert.setName("btnTestAlert");
        this.btnTestAlert.setSize(41, 22);
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
        this.txtValue3.setLocation(72, 88);
        this.txtValue3.setName("txtValue3");
        this.txtValue3.setSize(120, 20);
        this.txtValue3.setTabIndex(7);
        this.txtValue3.setText("");
        //
        // txtValue2
        //
        this.txtValue2.setLocation(72, 64);
        this.txtValue2.setName("txtValue2");
        this.txtValue2.setSize(120, 20);
        this.txtValue2.setTabIndex(6);
        this.txtValue2.setText("");
        //
        // txtValue1
        //
        this.txtValue1.setLocation(72, 40);
        this.txtValue1.setName("txtValue1");
        this.txtValue1.setSize(120, 20);
        this.txtValue1.setTabIndex(5);
        this.txtValue1.setText("");
        //
        // selAlertType
        //
        this.selAlertType.setLocation(72, 16);
        this.selAlertType.setName("selAlertType");
        this.selAlertType.setSize(200, 21);
        this.selAlertType.setTabIndex(4);
        //
        // lblValue3
        //
        this.lblValue3.setAutoSize(true);
        this.lblValue3.setLocation(8, 91);
        this.lblValue3.setName("lblValue3");
        this.lblValue3.setSize(43, 13);
        this.lblValue3.setTabIndex(3);
        this.lblValue3.setText("Value 3");
        //
        // lblValue1
        //
        this.lblValue1.setAutoSize(true);
        this.lblValue1.setLocation(8, 43);
        this.lblValue1.setName("lblValue1");
        this.lblValue1.setSize(43, 13);
        this.lblValue1.setTabIndex(2);
        this.lblValue1.setText("Value 1");
        //
        // lblValue2
        //
        this.lblValue2.setAutoSize(true);
        this.lblValue2.setLocation(8, 67);
        this.lblValue2.setName("lblValue2");
        this.lblValue2.setSize(43, 13);
        this.lblValue2.setTabIndex(1);
        this.lblValue2.setText("Value 2");
        //
        // panel1
        //
        this.panel1.getControls().addAll((new BaseComponent[]{
                this.btnTestSpecialEvent,
                this.selSpecialEvent,
                this.lblSpecialEvent}));
        this.panel1.setLocation(8, 168);
        this.panel1.setName("panel1");
        this.panel1.setSize(300, 80);
        this.panel1.setTabIndex(2);
        this.panel1.setTabStop(false);
        this.panel1.setText("Test Special Alert");
        //
        // btnTestSpecialEvent
        //
        this.btnTestSpecialEvent.setLocation(80, 48);
        this.btnTestSpecialEvent.setName("btnTestSpecialEvent");
        this.btnTestSpecialEvent.setSize(41, 22);
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
        this.selSpecialEvent.setLocation(88, 16);
        this.selSpecialEvent.setName("selSpecialEvent");
        this.selSpecialEvent.setSize(204, 21);
        this.selSpecialEvent.setTabIndex(4);
        //
        // lblSpecialEvent
        //
        this.lblSpecialEvent.setAutoSize(true);
        this.lblSpecialEvent.setLocation(8, 19);
        this.lblSpecialEvent.setName("lblSpecialEvent");
        this.lblSpecialEvent.setSize(73, 13);
        this.lblSpecialEvent.setTabIndex(0);
        this.lblSpecialEvent.setText("Special Event");
        //
        // FormAlertTest
        //
        this.setClientSize(370, 255);
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
        DialogResult result = GuiFacade.alert(((AlertType) selAlertType.getSelectedItem()), txtValue1.getText(), txtValue2.getText(), txtValue3.getText());
        GuiFacade.alert(AlertType.Alert, "Result", ("The result was " + ((result == null) ? "null" : result.toString())));
    }

    private void btnTestSpecialEvent_Click() {
        QuestDialog dialog = (QuestDialog) selSpecialEvent.getSelectedItem();
        String btn1, btn2;
        DialogResult res1, res2;

        if (dialog.getMessageType() == MessageType.ALERT) {
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

        (new FormAlert(dialog.getTitle(), dialog.getMessage(), btn1, res1, btn2, res2, null)).showDialog(this);
    }
}
