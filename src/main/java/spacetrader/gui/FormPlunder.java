package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.EventArgs;
import spacetrader.controls.EventHandler;
import spacetrader.controls.Label;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Game;
import spacetrader.game.Ship;
import spacetrader.game.Strings;
import spacetrader.guifacade.Facaded;
import spacetrader.util.ReflectionUtils;

import java.util.Arrays;

import static spacetrader.util.Functions.stringVars;

@Facaded
public class FormPlunder extends SpaceTraderForm {

    private final Game game = Game.getCurrentGame();
    private Button plunderAllButton9 = new Button();
    private Button plunderButton9 = new Button();
    private Button plunderAllButton8 = new Button();
    private Button plunderButton8 = new Button();
    private Button plunderAllButton7 = new Button();
    private Button plunderButton7 = new Button();
    private Button plunderAllButton6 = new Button();
    private Button plunderButton6 = new Button();
    private Button plunderAllButton5 = new Button();
    private Button plunderButton5 = new Button();
    private Button plunderAllButton4 = new Button();
    private Button plunderButton4 = new Button();
    private Button plunderAllButton3 = new Button();
    private Button plunderButton3 = new Button();
    private Button plunderAllButton2 = new Button();
    private Button plunderButton2 = new Button();
    private Button plunderAllButton1 = new Button();
    private Button plunderButton1 = new Button();
    private Button plunderAllButton0 = new Button();
    private Button plunderButton0 = new Button();
    private Label commodityLabel9 = new Label();
    private Label commodityLabel8 = new Label();
    private Label commodityLabel2 = new Label();
    private Label commodityLabel0 = new Label();
    private Label commodityLabel1 = new Label();
    private Label commodityLabel6 = new Label();
    private Label commodityLabel5 = new Label();
    private Label commodityLabel4 = new Label();
    private Label commodityLabel3 = new Label();
    private Label commodityLabel7 = new Label();
    private Label baysLabelValue = new Label();
    private Button doneButton = new Button();

    private Label[] commoditiesArray = new Label[]{commodityLabel0, commodityLabel1, commodityLabel2, commodityLabel3,
            commodityLabel4, commodityLabel5, commodityLabel6, commodityLabel7, commodityLabel8, commodityLabel9};

    private final Button[] plunderButtons = new Button[]{plunderButton0, plunderButton1, plunderButton2, plunderButton3,
            plunderButton4, plunderButton5, plunderButton6, plunderButton7, plunderButton8, plunderButton9};

    private final Button[] plunderAllButtons = new Button[]{plunderAllButton0, plunderAllButton1, plunderAllButton2,
            plunderAllButton3, plunderAllButton4, plunderAllButton5, plunderAllButton6, plunderAllButton7,
            plunderAllButton8, plunderAllButton9};


    private Button jettisonButton = new Button();

    public FormPlunder() {
        initializeComponent();
        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formPlunder");
        setText("Plunder Cargo");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(245, 293);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setAcceptButton(doneButton);
        setCancelButton(doneButton);

        commodityLabel0.setLocation(8, 8);
        //commodityLabel0.setSize(36, 13);
        //commodityLabel0.setText("Water");

        commodityLabel1.setLocation(8, 32);
        //commodityLabel1.setSize(27, 13);
        //commodityLabel1.setText("Furs");

        commodityLabel2.setLocation(8, 56);
        //commodityLabel2.setSize(31, 13);
        //commodityLabel2.setText("Food");

        commodityLabel3.setLocation(8, 80);
        //commodityLabel3.setSize(24, 13);
        //commodityLabel3.setText("Ore");

        commodityLabel4.setLocation(8, 104);
        //commodityLabel4.setSize(40, 13);
        //commodityLabel4.setText("Games");

        commodityLabel5.setLocation(8, 128);
        //commodityLabel5.setSize(46, 13);
        //commodityLabel5.setText("Firearms");

        commodityLabel6.setLocation(8, 152);
        //commodityLabel6.setSize(50, 13);
        //commodityLabel6.setText("Medicine");

        commodityLabel7.setLocation(8, 176);
        //commodityLabel7.setSize(53, 13);
        //commodityLabel7.setText("Machines");

        commodityLabel8.setLocation(8, 200);
        //commodityLabel8.setSize(52, 13);
        //commodityLabel8.setText("Narcotics");

        commodityLabel9.setLocation(8, 224);
        //commodityLabel9.setSize(41, 13);
        //commodityLabel9.setText("Robots");

        for (int i = 0; i < Strings.TradeItemNames.length; i++) {
            commoditiesArray[i].setAutoSize(true);
            commoditiesArray[i].setText(Strings.TradeItemNames[i]);
        }

        plunderAllButton9.setLocation(100, 220);
        plunderAllButton9.setTabIndex(141);

        plunderButton9.setLocation(68, 220);
        plunderButton9.setTabIndex(140);

        plunderAllButton8.setLocation(100, 196);
        plunderAllButton8.setTabIndex(139);

        plunderButton8.setLocation(68, 196);
        plunderButton8.setTabIndex(138);

        plunderAllButton7.setLocation(100, 172);
        plunderAllButton7.setTabIndex(137);

        plunderButton7.setLocation(68, 172);
        plunderButton7.setTabIndex(136);

        plunderAllButton6.setLocation(100, 148);
        plunderAllButton6.setTabIndex(135);

        plunderButton6.setLocation(68, 148);
        plunderButton6.setTabIndex(134);

        plunderAllButton5.setLocation(100, 124);
        plunderAllButton5.setTabIndex(133);

        plunderButton5.setLocation(68, 124);
        plunderButton5.setTabIndex(132);

        plunderAllButton4.setLocation(100, 100);
        plunderAllButton4.setTabIndex(131);

        plunderButton4.setLocation(68, 100);
        plunderButton4.setTabIndex(130);

        plunderAllButton3.setLocation(100, 76);
        plunderAllButton3.setTabIndex(129);

        plunderButton3.setLocation(68, 76);
        plunderButton3.setTabIndex(128);

        plunderAllButton2.setLocation(100, 52);
        plunderAllButton2.setTabIndex(127);

        plunderButton2.setLocation(68, 52);
        plunderButton2.setTabIndex(126);

        plunderAllButton1.setLocation(100, 28);
        plunderAllButton1.setTabIndex(125);

        plunderButton1.setLocation(68, 28);
        plunderButton1.setTabIndex(124);

        plunderAllButton0.setLocation(100, 4);
        plunderAllButton0.setTabIndex(123);

        plunderButton0.setLocation(68, 4);
        plunderButton0.setTabIndex(122);

        Arrays.stream(plunderButtons).forEach(button -> {
            button.setSize(28, 22);
            //button.setText("88");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    plunderButtonClick(sender);
                }
            });
        });

        Arrays.stream(plunderAllButtons).forEach(button -> {
            button.setAutoWidth(true);
            button.setSize(32, 22);
            button.setText(" " + Strings.JettisonAll + " ");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    plunderButtonClick(sender);
                }
            });
        });

        baysLabelValue.setAutoSize(true);
        baysLabelValue.setLocation(144, 8);
        //baysLabelValue.setSize(48, 13);
        //baysLabelValue.setText("Bays: 888/888");

        jettisonButton.setAutoWidth(true);
        jettisonButton.setLocation(142, 24);
        jettisonButton.setSize(53, 22);
        jettisonButton.setTabIndex(155);
        jettisonButton.setText("Jettison");
        jettisonButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                jettisonButtonClick();
            }
        });

        doneButton.setDialogResult(DialogResult.CANCEL);
        doneButton.setAutoWidth(true);
        doneButton.setControlBinding(ControlBinding.CENTER);
        doneButton.setLocation(92, 255);
        doneButton.setSize(44, 22);
        doneButton.setTabIndex(154);
        doneButton.setText("Done");

        controls.addAll(commoditiesArray);
        controls.addAll(plunderButtons);
        controls.addAll(plunderAllButtons);
        controls.addAll(baysLabelValue, jettisonButton, doneButton);

        performLayout();

        ReflectionUtils.loadControlsData(this);
    }

    private void plunder(int tradeItem, boolean all) {
        game.cargoPlunder(tradeItem, all);
        updateAll();
    }

    private void updateAll() {
        Ship ship = game.getShip();
        Ship opp = game.getOpponent();

        for (int i = 0; i < plunderButtons.length; i++) {
            plunderButtons[i].setText(opp.getCargo()[i]);
        }

        baysLabelValue
                .setText(stringVars(Strings.JettisonBays, ship.getFilledCargoBays() + "/" + ship.getCargoBays()));
    }

    private void jettisonButtonClick() {
        (new FormJettison()).showDialog(this);
    }

    private void plunderButtonClick(Object sender) {
        String name = ((Button) sender).getName();
        boolean all = name.contains("All");
        int index = Integer.parseInt(name.substring(name.length() - 1));

        plunder(index, all);
    }
}
