package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Font;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.game.*;

import java.awt.*;
import java.util.Arrays;

import static spacetrader.game.Strings.*;
import static spacetrader.util.Functions.formatMoney;
import static spacetrader.util.Functions.formatNumber;

class CargoPanel extends Panel {

    private static final Font BOLD_FONT = FontCollection.bold825;

    private static final String TARGET_PRICE_NA = "-----------";
    private static final String TARGET_DIFF_NA = "------------";
    private static final String TARGET_PERCENTAGE_NA = "--------";

    private GameController controller = null;

    private Button buyMaxButton0 = new Button();
    private Button buyButton0 = new Button();
    private Button sellButton0 = new Button();
    private Button sellAllButton0 = new Button();
    private Button buyMaxButton1 = new Button();
    private Button buyButton1 = new Button();
    private Button sellButton1 = new Button();
    private Button sellAllButton1 = new Button();
    private Button sellButton2 = new Button();
    private Button sellAllButton2 = new Button();
    private Button buyButton2 = new Button();
    private Button buyMaxButton2 = new Button();
    private Button sellButton3 = new Button();
    private Button sellAllButton3 = new Button();
    private Button buyButton3 = new Button();
    private Button buyMaxButton3 = new Button();
    private Button sellButton4 = new Button();
    private Button sellAllButton4 = new Button();
    private Button buyButton4 = new Button();
    private Button buyMaxButton4 = new Button();
    private Button sellButton5 = new Button();
    private Button sellAllButton5 = new Button();
    private Button buyButton5 = new Button();
    private Button buyMaxButton5 = new Button();
    private Button sellButton6 = new Button();
    private Button sellAllButton6 = new Button();
    private Button buyButton6 = new Button();
    private Button buyMaxButton6 = new Button();
    private Button sellButton7 = new Button();
    private Button sellAllButton7 = new Button();
    private Button buyButton7 = new Button();
    private Button buyMaxButton7 = new Button();
    private Button sellButton8 = new Button();
    private Button sellAllButton8 = new Button();
    private Button buyButton8 = new Button();
    private Button buyMaxButton8 = new Button();
    private Button sellButton9 = new Button();
    private Button sellAllButton9 = new Button();
    private Button buyButton9 = new Button();
    private Button buyMaxButton9 = new Button();

    private Label commodityLabel0 = new Label();
    private Label commodityLabel1 = new Label();
    private Label commodityLabel2 = new Label();
    private Label commodityLabel3 = new Label();
    private Label commodityLabel4 = new Label();
    private Label commodityLabel5 = new Label();
    private Label commodityLabel6 = new Label();
    private Label commodityLabel7 = new Label();
    private Label commodityLabel8 = new Label();
    private Label commodityLabel9 = new Label();

    private Label buyPriceLabel = new Label();
    private Label buyPriceLabelValue0 = new Label();
    private Label buyPriceLabelValue1 = new Label();
    private Label buyPriceLabelValue2 = new Label();
    private Label buyPriceLabelValue3 = new Label();
    private Label buyPriceLabelValue4 = new Label();
    private Label buyPriceLabelValue5 = new Label();
    private Label buyPriceLabelValue6 = new Label();
    private Label buyPriceLabelValue7 = new Label();
    private Label buyPriceLabelValue8 = new Label();
    private Label buyPriceLabelValue9 = new Label();

    private Label sellPriceLabel = new Label();
    private Label sellPriceLabelValue0 = new Label();
    private Label sellPriceLabelValue1 = new Label();
    private Label sellPriceLabelValue2 = new Label();
    private Label sellPriceLabelValue3 = new Label();
    private Label sellPriceLabelValue4 = new Label();
    private Label sellPriceLabelValue5 = new Label();
    private Label sellPriceLabelValue6 = new Label();
    private Label sellPriceLabelValue7 = new Label();
    private Label sellPriceLabelValue8 = new Label();
    private Label sellPriceLabelValue9 = new Label();

    private Label tradeTargetLabel = new Label();

    private Label targetPriceLabel = new Label();
    private Label targetPriceLabelValue0 = new Label();
    private Label targetPriceLabelValue1 = new Label();
    private Label targetPriceLabelValue2 = new Label();
    private Label targetPriceLabelValue3 = new Label();
    private Label targetPriceLabelValue4 = new Label();
    private Label targetPriceLabelValue5 = new Label();
    private Label targetPriceLabelValue6 = new Label();
    private Label targetPriceLabelValue7 = new Label();
    private Label targetPriceLabelValue8 = new Label();
    private Label targetPriceLabelValue9 = new Label();

    private Label targetDiffLabel = new Label();
    private Label targetDiffLabelValue0 = new Label();
    private Label targetDiffLabelValue1 = new Label();
    private Label targetDiffLabelValue2 = new Label();
    private Label targetDiffLabelValue3 = new Label();
    private Label targetDiffLabelValue4 = new Label();
    private Label targetDiffLabelValue5 = new Label();
    private Label targetDiffLabelValue6 = new Label();
    private Label targetDiffLabelValue7 = new Label();
    private Label targetDiffLabelValue8 = new Label();
    private Label targetDiffLabelValue9 = new Label();

    private Label targetPercentageLabel = new Label();
    private Label targetPercentageLabelValue0 = new Label();
    private Label targetPercentageLabelValue1 = new Label();
    private Label targetPercentageLabelValue2 = new Label();
    private Label targetPercentageLabelValue3 = new Label();
    private Label targetPercentageLabelValue4 = new Label();
    private Label targetPercentageLabelValue5 = new Label();
    private Label targetPercentageLabelValue6 = new Label();
    private Label targetPercentageLabelValue7 = new Label();
    private Label targetPercentageLabelValue8 = new Label();
    private Label targetPercentageLabelValue9 = new Label();

    private VerticalLine verticalLine0 = new VerticalLine();
    private VerticalLine verticalLine1 = new VerticalLine();
    private VerticalLine verticalLine2 = new VerticalLine();
    private HorizontalLine horizontalLine = new HorizontalLine();

    private ImageList ilChartImages = new ImageList();
    private ImageList ilDirectionImages = new ImageList();
    private ImageList ilEquipmentImages = new ImageList();
    private ImageList ilShipImages = new ImageList();

    private Label[] commoditiesArray = new Label[]{commodityLabel0, commodityLabel1, commodityLabel2, commodityLabel3, commodityLabel4,
            commodityLabel5, commodityLabel6, commodityLabel7, commodityLabel8, commodityLabel9};

    private Label[] sellPriceArray = new Label[]{sellPriceLabelValue0, sellPriceLabelValue1, sellPriceLabelValue2,
            sellPriceLabelValue3, sellPriceLabelValue4, sellPriceLabelValue5, sellPriceLabelValue6,
            sellPriceLabelValue7, sellPriceLabelValue8, sellPriceLabelValue9};

    private Label[] buyPriceArray = new Label[]{buyPriceLabelValue0, buyPriceLabelValue1, buyPriceLabelValue2,
            buyPriceLabelValue3, buyPriceLabelValue4, buyPriceLabelValue5, buyPriceLabelValue6,
            buyPriceLabelValue7, buyPriceLabelValue8, buyPriceLabelValue9};

    private Label[] targetPriceArray = new Label[]{targetPriceLabelValue0, targetPriceLabelValue1, targetPriceLabelValue2,
            targetPriceLabelValue3, targetPriceLabelValue4, targetPriceLabelValue5, targetPriceLabelValue6,
            targetPriceLabelValue7, targetPriceLabelValue8, targetPriceLabelValue9};

    private Label[] targetDiffArray = new Label[]{targetDiffLabelValue0, targetDiffLabelValue1, targetDiffLabelValue2,
            targetDiffLabelValue3, targetDiffLabelValue4, targetDiffLabelValue5, targetDiffLabelValue6,
            targetDiffLabelValue7, targetDiffLabelValue8, targetDiffLabelValue9};

    private Label[] targetPercentageArray = new Label[]{targetPercentageLabelValue0, targetPercentageLabelValue1,
            targetPercentageLabelValue2, targetPercentageLabelValue3, targetPercentageLabelValue4,
            targetPercentageLabelValue5, targetPercentageLabelValue6, targetPercentageLabelValue7,
            targetPercentageLabelValue8, targetPercentageLabelValue9};

    private Button[] sellButtonArray = new Button[]{sellButton0, sellButton1, sellButton2, sellButton3, sellButton4, sellButton5,
            sellButton6, sellButton7, sellButton8, sellButton9};

    private Button[] sellAllButtonArray = new Button[]{sellAllButton0, sellAllButton1, sellAllButton2, sellAllButton3,
            sellAllButton4, sellAllButton5, sellAllButton6, sellAllButton7, sellAllButton8, sellAllButton9};

    private Button[] buyButtonArray = new Button[]{buyButton0, buyButton1, buyButton2, buyButton3, buyButton4, buyButton5,
            buyButton6, buyButton7, buyButton8, buyButton9};

    private Button[] buyMaxButtonArray = new Button[]{buyMaxButton0, buyMaxButton1, buyMaxButton2, buyMaxButton3, buyMaxButton4,
            buyMaxButton5, buyMaxButton6, buyMaxButton7, buyMaxButton8, buyMaxButton9};

    CargoPanel() {
    }

    void setGame(GameController controller) {
        this.controller = controller;
    }

    void initializeComponent() {
        ResourceManager resources = new ResourceManager(SpaceTrader.class);

        setText("Cargo");
        setSize(535, 305);
        setTabStop(false);

        horizontalLine.setLocation(8, 52);
        horizontalLine.setWidth(519);
        horizontalLine.setTabStop(false);

        verticalLine0.setLocation(71, 32);
        verticalLine0.setHeight(262);
        verticalLine0.setTabStop(false);

        verticalLine1.setLocation(223, 32);
        verticalLine1.setHeight(262);
        verticalLine1.setTabStop(false);

        verticalLine2.setLocation(371, 32);
        verticalLine2.setHeight(262);
        verticalLine2.setTabStop(false);

        sellPriceLabel.setAutoSize(true);
        sellPriceLabel.setControlBinding(ControlBinding.CENTER);
        sellPriceLabel.setFont(FontCollection.regular825);
        sellPriceLabel.setLocation(135, 34);
        sellPriceLabel.setSize(23, 16);
        sellPriceLabel.setText("Sell");

        buyPriceLabel.setAutoSize(true);
        buyPriceLabel.setControlBinding(ControlBinding.CENTER);
        buyPriceLabel.setLocation(286, 34);
        buyPriceLabel.setSize(24, 16);
        buyPriceLabel.setText("Buy");

        tradeTargetLabel.setAutoSize(true);
        tradeTargetLabel.setControlBinding(ControlBinding.CENTER);
        tradeTargetLabel.setLocation(414, 16);
        tradeTargetLabel.setSize(78, 16);
        tradeTargetLabel.setText("Target System");

        targetPriceLabel.setAutoSize(true);
        targetPriceLabel.setLocation(378, 34);
        targetPriceLabel.setSize(30, 16);
        targetPriceLabel.setText("Price");

        targetDiffLabel.setAutoSize(true);
        targetDiffLabel.setLocation(443, 34);
        targetDiffLabel.setSize(18, 16);
        targetDiffLabel.setText("+/-");

        targetPercentageLabel.setAutoSize(true);
        targetPercentageLabel.setLocation(498, 34);
        targetPercentageLabel.setSize(14, 16);
        targetPercentageLabel.setText("%");

        commodityLabel0.setLocation(8, 60);
        //commodityLabel0.setSize(34, 16);
        //commodityLabel0.setText("Water");

        commodityLabel1.setLocation(8, 84);
        //commodityLabel1.setSize(27, 16);
        //commodityLabel1.setText("Furs");

        commodityLabel2.setLocation(8, 108);
        //commodityLabel2.setSize(30, 16);
        //commodityLabel2.setText("Food");

        commodityLabel3.setLocation(8, 132);
        //commodityLabel3.setSize(23, 16);
        //commodityLabel3.setText("Ore");

        commodityLabel4.setLocation(8, 156);
        //commodityLabel4.setSize(41, 16);
        //commodityLabel4.setText("Games");

        commodityLabel5.setLocation(8, 180);
        //commodityLabel5.setSize(49, 16);
        //commodityLabel5.setText("Firearms");

        commodityLabel6.setLocation(8, 204);
        //commodityLabel6.setSize(50, 16);
        //commodityLabel6.setText("Medicine");

        commodityLabel7.setLocation(8, 228);
        //commodityLabel7.setSize(53, 16);
        //commodityLabel7.setText("Machines");

        commodityLabel8.setLocation(8, 252);
        //commodityLabel8.setSize(51, 16);
        //commodityLabel8.setText("Narcotics");

        commodityLabel9.setLocation(8, 276);
        //commodityLabel9.setSize(40, 16);
        //commodityLabel9.setText("Robots");

        for (int i = 0; i < Strings.TradeItemNames.length; i++) {
            commoditiesArray[i].setAutoSize(true);
            //commoditiesArray[i].setTabIndex(16 + i);
            //commoditiesArray[i].setText(Strings.TradeItemNames[i]);
        }

        sellButton0.setLocation(80, 56);
        sellButton0.setSize(28, 22);
        sellButton0.setTabIndex(12);

        sellButton1.setLocation(80, 80);
        sellButton1.setSize(28, 22);
        sellButton1.setTabIndex(16);

        sellButton2.setLocation(80, 104);
        sellButton2.setSize(28, 22);
        sellButton2.setTabIndex(20);

        sellButton3.setLocation(80, 128);
        sellButton3.setSize(28, 22);
        sellButton3.setTabIndex(24);

        sellButton4.setLocation(80, 152);
        sellButton4.setSize(28, 22);
        sellButton4.setTabIndex(28);

        sellButton5.setLocation(80, 176);
        sellButton5.setSize(28, 22);
        sellButton5.setTabIndex(32);

        sellButton6.setLocation(80, 200);
        sellButton6.setSize(28, 22);
        sellButton6.setTabIndex(36);

        sellButton7.setLocation(80, 224);
        sellButton7.setSize(28, 22);
        sellButton7.setTabIndex(40);

        sellButton8.setLocation(80, 248);
        sellButton8.setSize(28, 22);
        sellButton8.setTabIndex(44);

        sellButton9.setLocation(80, 272);
        sellButton9.setSize(28, 22);
        sellButton9.setTabIndex(48);

        sellAllButton0.setLocation(114, 56);
        sellAllButton0.setSize(44, 22);
        sellAllButton0.setTabIndex(13);

        sellAllButton1.setLocation(114, 80);
        sellAllButton1.setSize(44, 22);
        sellAllButton1.setTabIndex(17);

        sellAllButton2.setLocation(114, 104);
        sellAllButton2.setSize(44, 22);
        sellAllButton2.setTabIndex(21);

        sellAllButton3.setLocation(114, 128);
        sellAllButton3.setSize(44, 22);
        sellAllButton3.setTabIndex(25);

        sellAllButton4.setLocation(114, 152);
        sellAllButton4.setSize(44, 22);
        sellAllButton4.setTabIndex(29);

        sellAllButton5.setLocation(114, 176);
        sellAllButton5.setSize(44, 22);
        sellAllButton5.setTabIndex(33);

        sellAllButton6.setLocation(114, 200);
        sellAllButton6.setSize(44, 22);
        sellAllButton6.setTabIndex(37);

        sellAllButton7.setLocation(114, 224);
        sellAllButton7.setSize(44, 22);
        sellAllButton7.setTabIndex(41);

        sellAllButton8.setLocation(114, 248);
        sellAllButton8.setSize(44, 22);
        sellAllButton8.setTabIndex(45);

        sellAllButton9.setLocation(114, 272);
        sellAllButton9.setSize(44, 22);
        sellAllButton9.setTabIndex(49);

        sellPriceLabelValue0.setAutoSize(true);
        sellPriceLabelValue0.setLocation(163, 60);
        sellPriceLabelValue0.setSize(48, 13);
        sellPriceLabelValue0.setTabIndex(35);
        // sellPriceLabelValue0.setText("8,888 cr.");

        sellPriceLabelValue1.setAutoSize(true);
        sellPriceLabelValue1.setLocation(163, 84);
        sellPriceLabelValue1.setSize(48, 13);
        sellPriceLabelValue1.setTabIndex(38);
        // sellPriceLabelValue1.setText("8,888 cr.");

        sellPriceLabelValue2.setAutoSize(true);
        sellPriceLabelValue2.setLocation(163, 108);
        sellPriceLabelValue2.setSize(48, 13);
        sellPriceLabelValue2.setTabIndex(56);
        // sellPriceLabelValue2.setText("8,888 cr.");

        sellPriceLabelValue3.setAutoSize(true);
        sellPriceLabelValue3.setLocation(163, 132);
        sellPriceLabelValue3.setSize(48, 13);
        sellPriceLabelValue3.setTabIndex(65);
        // sellPriceLabelValue3.setText("8,888 cr.");

        sellPriceLabelValue4.setAutoSize(true);
        sellPriceLabelValue4.setLocation(163, 156);
        sellPriceLabelValue4.setSize(48, 13);
        sellPriceLabelValue4.setTabIndex(74);
        // sellPriceLabelValue4.setText("8,888 cr.");

        sellPriceLabelValue5.setAutoSize(true);
        sellPriceLabelValue5.setLocation(163, 180);
        sellPriceLabelValue5.setSize(48, 13);
        sellPriceLabelValue5.setTabIndex(83);
        // sellPriceLabelValue5.setText("8,888 cr.");

        sellPriceLabelValue6.setAutoSize(true);
        sellPriceLabelValue6.setLocation(163, 204);
        sellPriceLabelValue6.setSize(48, 13);
        sellPriceLabelValue6.setTabIndex(92);
        // sellPriceLabelValue6.setText("8,888 cr.");

        sellPriceLabelValue7.setAutoSize(true);
        sellPriceLabelValue7.setLocation(163, 228);
        sellPriceLabelValue7.setSize(48, 13);
        sellPriceLabelValue7.setTabIndex(101);
        // sellPriceLabelValue7.setText("8,888 cr.");

        sellPriceLabelValue8.setAutoSize(true);
        sellPriceLabelValue8.setLocation(163, 252);
        sellPriceLabelValue8.setSize(48, 13);
        sellPriceLabelValue8.setTabIndex(110);
        // sellPriceLabelValue8.setText("8,888 cr.");

        sellPriceLabelValue9.setAutoSize(true);
        sellPriceLabelValue9.setLocation(163, 276);
        sellPriceLabelValue9.setSize(48, 13);
        sellPriceLabelValue9.setTabIndex(119);
        // sellPriceLabelValue9.setText("no trade");

        buyButton0.setLocation(232, 56);
        buyButton0.setSize(28, 22);
        buyButton0.setTabIndex(14);

        buyButton1.setLocation(232, 80);
        buyButton1.setSize(28, 22);
        buyButton1.setTabIndex(18);

        buyButton2.setLocation(232, 104);
        buyButton2.setSize(28, 22);
        buyButton2.setTabIndex(22);

        buyButton3.setLocation(232, 128);
        buyButton3.setSize(28, 22);
        buyButton3.setTabIndex(26);

        buyButton4.setLocation(232, 152);
        buyButton4.setSize(28, 22);
        buyButton4.setTabIndex(30);

        buyButton5.setLocation(232, 176);
        buyButton5.setSize(28, 22);
        buyButton5.setTabIndex(34);

        buyButton6.setLocation(232, 200);
        buyButton6.setSize(28, 22);
        buyButton6.setTabIndex(38);

        buyButton7.setLocation(232, 224);
        buyButton7.setSize(28, 22);
        buyButton7.setTabIndex(42);

        buyButton8.setLocation(232, 248);
        buyButton8.setSize(28, 22);
        buyButton8.setTabIndex(46);

        buyButton9.setLocation(232, 272);
        buyButton9.setSize(28, 22);
        buyButton9.setTabIndex(50);

        buyMaxButton0.setLocation(265, 56);
        buyMaxButton0.setSize(41, 22);
        buyMaxButton0.setTabIndex(15);

        buyMaxButton1.setLocation(265, 80);
        buyMaxButton1.setSize(41, 22);
        buyMaxButton1.setTabIndex(19);

        buyMaxButton2.setLocation(265, 104);
        buyMaxButton2.setSize(41, 22);
        buyMaxButton2.setTabIndex(23);

        buyMaxButton3.setLocation(265, 128);
        buyMaxButton3.setSize(41, 22);
        buyMaxButton3.setTabIndex(27);

        buyMaxButton4.setLocation(265, 152);
        buyMaxButton4.setSize(41, 22);
        buyMaxButton4.setTabIndex(31);

        buyMaxButton5.setLocation(265, 176);
        buyMaxButton5.setSize(41, 22);
        buyMaxButton5.setTabIndex(35);

        buyMaxButton6.setLocation(265, 200);
        buyMaxButton6.setSize(41, 22);
        buyMaxButton6.setTabIndex(39);

        buyMaxButton7.setLocation(265, 224);
        buyMaxButton7.setSize(41, 22);
        buyMaxButton7.setTabIndex(43);

        buyMaxButton8.setLocation(265, 248);
        buyMaxButton8.setSize(41, 22);
        buyMaxButton8.setTabIndex(47);

        buyMaxButton9.setLocation(265, 272);
        buyMaxButton9.setSize(41, 22);
        buyMaxButton9.setTabIndex(51);

        buyPriceLabelValue0.setAutoSize(true);
        buyPriceLabelValue0.setLocation(311, 60);
        buyPriceLabelValue0.setSize(48, 13);
        // buyPriceLabelValue0.setText("8,888 cr.");

        buyPriceLabelValue1.setAutoSize(true);
        buyPriceLabelValue1.setLocation(311, 84);
        buyPriceLabelValue1.setSize(48, 13);
        // buyPriceLabelValue1.setText("8,888 cr.");

        buyPriceLabelValue2.setAutoSize(true);
        buyPriceLabelValue2.setLocation(311, 108);
        buyPriceLabelValue2.setSize(48, 13);
        // buyPriceLabelValue2.setText("8,888 cr.");

        buyPriceLabelValue3.setAutoSize(true);
        buyPriceLabelValue3.setLocation(311, 132);
        buyPriceLabelValue3.setSize(48, 13);
        // buyPriceLabelValue3.setText("8,888 cr.");

        buyPriceLabelValue4.setAutoSize(true);
        buyPriceLabelValue4.setLocation(311, 156);
        buyPriceLabelValue4.setSize(48, 13);
        // buyPriceLabelValue4.setText("8,888 cr.");

        buyPriceLabelValue5.setAutoSize(true);
        buyPriceLabelValue5.setLocation(311, 180);
        buyPriceLabelValue5.setSize(48, 13);
        // buyPriceLabelValue5.setText("8,888 cr.");

        buyPriceLabelValue6.setAutoSize(true);
        buyPriceLabelValue6.setLocation(311, 204);
        buyPriceLabelValue6.setSize(48, 13);
        // buyPriceLabelValue6.setText("8,888 cr.");

        buyPriceLabelValue7.setAutoSize(true);
        buyPriceLabelValue7.setLocation(311, 228);
        buyPriceLabelValue7.setSize(48, 13);
        // buyPriceLabelValue7.setText("8,888 cr.");

        buyPriceLabelValue8.setAutoSize(true);
        buyPriceLabelValue8.setLocation(311, 252);
        buyPriceLabelValue8.setSize(48, 13);
        // buyPriceLabelValue8.setText("8,888 cr.");

        buyPriceLabelValue9.setAutoSize(true);
        buyPriceLabelValue9.setLocation(311, 276);
        buyPriceLabelValue9.setSize(48, 13);
        // buyPriceLabelValue9.setText("not sold");

        targetPriceLabelValue0.setAutoSize(true);
        targetPriceLabelValue0.setLocation(377, 60);
        targetPriceLabelValue0.setSize(48, 13);
        targetPriceLabelValue0.setTabIndex(44);
        // targetPriceLabelValue0.setText("8,888 cr.");

        targetPriceLabelValue1.setAutoSize(true);
        targetPriceLabelValue1.setLocation(377, 84);
        targetPriceLabelValue1.setSize(48, 13);
        targetPriceLabelValue1.setTabIndex(53);
        // targetPriceLabelValue1.setText("8,888 cr.");

        targetPriceLabelValue2.setAutoSize(true);
        targetPriceLabelValue2.setLocation(377, 108);
        targetPriceLabelValue2.setSize(48, 13);
        targetPriceLabelValue2.setTabIndex(62);
        // targetPriceLabelValue2.setText("8,888 cr.");

        targetPriceLabelValue3.setAutoSize(true);
        targetPriceLabelValue3.setLocation(377, 132);
        targetPriceLabelValue3.setSize(48, 13);
        targetPriceLabelValue3.setTabIndex(71);
        // targetPriceLabelValue3.setText("8,888 cr.");

        targetPriceLabelValue4.setAutoSize(true);
        targetPriceLabelValue4.setLocation(377, 156);
        targetPriceLabelValue4.setSize(48, 13);
        targetPriceLabelValue4.setTabIndex(80);
        // targetPriceLabelValue4.setText("8,888 cr.");

        targetPriceLabelValue5.setAutoSize(true);
        targetPriceLabelValue5.setLocation(377, 180);
        targetPriceLabelValue5.setSize(48, 13);
        targetPriceLabelValue5.setTabIndex(89);
        // targetPriceLabelValue5.setText("8,888 cr.");

        targetPriceLabelValue6.setAutoSize(true);
        targetPriceLabelValue6.setLocation(377, 204);
        targetPriceLabelValue6.setSize(48, 13);
        targetPriceLabelValue6.setTabIndex(98);
        // targetPriceLabelValue6.setText("8,888 cr.");

        targetPriceLabelValue7.setAutoSize(true);
        targetPriceLabelValue7.setLocation(377, 228);
        targetPriceLabelValue7.setSize(48, 13);
        targetPriceLabelValue7.setTabIndex(107);
        // targetPriceLabelValue7.setText("8,888 cr.");

        targetPriceLabelValue8.setAutoSize(true);
        targetPriceLabelValue8.setLocation(377, 252);
        targetPriceLabelValue8.setSize(48, 13);
        targetPriceLabelValue8.setTabIndex(116);
        // targetPriceLabelValue8.setText("8,888 cr.");

        targetPriceLabelValue9.setAutoSize(true);
        targetPriceLabelValue9.setLocation(377, 276);
        targetPriceLabelValue9.setSize(48, 13);
        targetPriceLabelValue9.setTabIndex(125);
        targetPriceLabelValue9.setText(TARGET_PRICE_NA);

        targetDiffLabelValue0.setAutoSize(true);
        targetDiffLabelValue0.setLocation(430, 60);
        targetDiffLabelValue0.setSize(52, 13);
        targetDiffLabelValue0.setTabIndex(45);
        // targetDiffLabelValue0.setText("-8,888 cr.");

        targetDiffLabelValue1.setAutoSize(true);
        targetDiffLabelValue1.setLocation(430, 84);
        targetDiffLabelValue1.setSize(52, 13);
        targetDiffLabelValue1.setTabIndex(54);
        // targetDiffLabelValue1.setText("-8,888 cr.");

        targetDiffLabelValue2.setAutoSize(true);
        targetDiffLabelValue2.setLocation(430, 108);
        targetDiffLabelValue2.setSize(52, 13);
        targetDiffLabelValue2.setTabIndex(63);
        // targetDiffLabelValue2.setText("-8,888 cr.");

        targetDiffLabelValue3.setAutoSize(true);
        targetDiffLabelValue3.setLocation(430, 132);
        targetDiffLabelValue3.setSize(52, 13);
        targetDiffLabelValue3.setTabIndex(72);
        // targetDiffLabelValue3.setText("-8,888 cr.");

        targetDiffLabelValue4.setAutoSize(true);
        targetDiffLabelValue4.setLocation(430, 156);
        targetDiffLabelValue4.setSize(52, 13);
        targetDiffLabelValue4.setTabIndex(81);
        // targetDiffLabelValue4.setText("-8,888 cr.");

        targetDiffLabelValue5.setAutoSize(true);
        targetDiffLabelValue5.setLocation(430, 180);
        targetDiffLabelValue5.setSize(52, 13);
        targetDiffLabelValue5.setTabIndex(90);
        // targetDiffLabelValue5.setText("-8,888 cr.");

        targetDiffLabelValue6.setAutoSize(true);
        targetDiffLabelValue6.setLocation(430, 204);
        targetDiffLabelValue6.setSize(52, 13);
        targetDiffLabelValue6.setTabIndex(99);
        // targetDiffLabelValue6.setText("-8,888 cr.");

        targetDiffLabelValue7.setAutoSize(true);
        targetDiffLabelValue7.setLocation(430, 228);
        targetDiffLabelValue7.setSize(52, 13);
        targetDiffLabelValue7.setTabIndex(108);
        // targetDiffLabelValue7.setText("-8,888 cr.");

        targetDiffLabelValue8.setAutoSize(true);
        targetDiffLabelValue8.setLocation(430, 252);
        targetDiffLabelValue8.setSize(52, 13);
        targetDiffLabelValue8.setTabIndex(117);
        // targetDiffLabelValue8.setText("-8,888 cr.");

        targetDiffLabelValue9.setAutoSize(true);
        targetDiffLabelValue9.setLocation(430, 276);
        targetDiffLabelValue9.setSize(52, 13);
        targetDiffLabelValue9.setTabIndex(126);
        targetDiffLabelValue9.setText(TARGET_DIFF_NA);

        targetPercentageLabelValue0.setAutoSize(true);
        targetPercentageLabelValue0.setLocation(489, 60);
        targetPercentageLabelValue0.setSize(37, 13);
        targetPercentageLabelValue0.setTabIndex(46);
        // targetPercentageLabelValue0.setText("-888%");

        targetPercentageLabelValue1.setAutoSize(true);
        targetPercentageLabelValue1.setLocation(489, 84);
        targetPercentageLabelValue1.setSize(37, 13);
        targetPercentageLabelValue1.setTabIndex(55);
        // targetPercentageLabelValue1.setText("-888%");

        targetPercentageLabelValue2.setAutoSize(true);
        targetPercentageLabelValue2.setLocation(489, 108);
        targetPercentageLabelValue2.setSize(37, 13);
        targetPercentageLabelValue2.setTabIndex(64);
        // targetPercentageLabelValue2.setText("-888%");

        targetPercentageLabelValue3.setAutoSize(true);
        targetPercentageLabelValue3.setLocation(489, 132);
        targetPercentageLabelValue3.setSize(37, 13);
        targetPercentageLabelValue3.setTabIndex(73);
        // targetPercentageLabelValue3.setText("-888%");

        targetPercentageLabelValue4.setAutoSize(true);
        targetPercentageLabelValue4.setLocation(489, 156);
        targetPercentageLabelValue4.setSize(37, 13);
        targetPercentageLabelValue4.setTabIndex(82);
        // targetPercentageLabelValue4.setText("-888%");

        targetPercentageLabelValue5.setAutoSize(true);
        targetPercentageLabelValue5.setLocation(489, 180);
        targetPercentageLabelValue5.setSize(37, 13);
        targetPercentageLabelValue5.setTabIndex(91);
        // targetPercentageLabelValue5.setText("-888%");

        targetPercentageLabelValue6.setAutoSize(true);
        targetPercentageLabelValue6.setLocation(489, 204);
        targetPercentageLabelValue6.setSize(37, 13);
        targetPercentageLabelValue6.setTabIndex(100);
        // targetPercentageLabelValue6.setText("-888%");

        targetPercentageLabelValue7.setAutoSize(true);
        targetPercentageLabelValue7.setLocation(489, 228);
        targetPercentageLabelValue7.setSize(37, 13);
        targetPercentageLabelValue7.setTabIndex(109);
        // targetPercentageLabelValue7.setText("-888%");

        targetPercentageLabelValue8.setAutoSize(true);
        targetPercentageLabelValue8.setLocation(489, 252);
        targetPercentageLabelValue8.setSize(37, 13);
        targetPercentageLabelValue8.setTabIndex(118);
        // targetPercentageLabelValue8.setText("-888%");

        targetPercentageLabelValue9.setAutoSize(true);
        targetPercentageLabelValue9.setLocation(489, 276);
        targetPercentageLabelValue9.setSize(37, 13);
        targetPercentageLabelValue9.setTabIndex(127);
        //targetPercentageLabelValue9.setText(TARGET_PERCENTAGE_NA);

        Arrays.stream(sellButtonArray).forEach(button -> {
            //button.setFlatStyle(FlatStyle.FLAT);
            //button.setText("88");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    buySellButtonClick(((Button) sender).getName());
                }
            });
        });

        Arrays.stream(sellAllButtonArray).forEach(button -> {
            //button.setFlatStyle(FlatStyle.FLAT);
            //button.setText(Strings.CargoAll);
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    buySellButtonClick(((Button) sender).getName());
                }
            });
        });

        Arrays.stream(buyButtonArray).forEach(button -> {
            //button.setFlatStyle(FlatStyle.FLAT);
            //button.setText("88");
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    buySellButtonClick(((Button) sender).getName());
                }
            });
        });

        Arrays.stream(buyMaxButtonArray).forEach(button -> {
            //button.setFlatStyle(FlatStyle.FLAT);
            //button.setText(Strings.CargoMax);
            button.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    buySellButtonClick(((Button) sender).getName());
                }
            });
        });

        ilChartImages.setImageSize(7, 7);
        ilChartImages.setImageStream(((ImageListStreamer) (resources.getObject("ilChartImages.ImageStream"))));
        ilChartImages.setTransparentColor(Color.WHITE);

        ilShipImages.setImageSize(64, 52);
        ilShipImages.setImageStream(((ImageListStreamer) (resources.getObject("ilShipImages.ImageStream"))));
        ilShipImages.setTransparentColor(Color.WHITE);

        ilDirectionImages.setImageSize(13, 13);
        ilDirectionImages.setImageStream(((ImageListStreamer) (resources
                .getObject("ilDirectionImages.ImageStream"))));
        ilDirectionImages.setTransparentColor(Color.WHITE);

        ilEquipmentImages.setImageSize(64, 52);
        ilEquipmentImages.setImageStream(((ImageListStreamer) (resources
                .getObject("ilEquipmentImages.ImageStream"))));
        ilEquipmentImages.setTransparentColor(Color.WHITE);


        getControls().addAll(verticalLine0, verticalLine1, verticalLine2, horizontalLine);

        getControls().addAll(commoditiesArray);

        getControls().addAll(buyPriceLabel, sellPriceLabel, tradeTargetLabel, targetPriceLabel, targetDiffLabel,
                targetPercentageLabel);

        getControls().addAll(sellPriceArray);
        getControls().addAll(buyPriceArray);
        getControls().addAll(targetPriceArray);
        getControls().addAll(targetDiffArray);
        getControls().addAll(targetPercentageArray);

        getControls().addAll(sellButtonArray);
        getControls().addAll(sellAllButtonArray);
        getControls().addAll(buyButtonArray);
        getControls().addAll(buyMaxButtonArray);
    }

    void update() {
        for (int i = 0; i < Strings.TradeItemNames.length; i++) {
            commoditiesArray[i].setText(Strings.TradeItemNames[i]);
        }
        Arrays.stream(sellAllButtonArray).forEach(button -> button.setText(Strings.CargoAll));
        Arrays.stream(buyMaxButtonArray).forEach(button -> button.setText(Strings.CargoMax));

        Game game = Game.getCurrentGame();

        if (game == null || game.getCommander().getCurrentSystem() == null) {
            for (int i = 0; i < sellPriceArray.length; i++) {
                sellPriceArray[i].setText("");
                buyPriceArray[i].setText("");
                targetPriceArray[i].setText("");
                targetDiffArray[i].setText("");
                targetPercentageArray[i].setText("");
                sellButtonArray[i].setVisible(false);
                sellAllButtonArray[i].setVisible(false);
                buyButtonArray[i].setVisible(false);
                buyMaxButtonArray[i].setVisible(false);
            }
            return;
        }
        int[] buy = game.getPriceCargoBuy();
        int[] sell = game.getPriceCargoSell();
        Commander commander = game.getCommander();
        StarSystem warpSystem = game.getWarpSystem();

        for (int i = 0; i < sellPriceArray.length; i++) {
            int price = (warpSystem == null) ? 0 : Consts.TradeItems[i].standardPrice(warpSystem);

            sellPriceArray[i].setText(sell[i] > 0 ? formatMoney(sell[i]) : CargoSellNA);
            sellButtonArray[i].setText(commander.getShip().getCargo()[i]);
            sellButtonArray[i].setVisible(true);
            
            sellAllButtonArray[i].setText(sell[i] > 0 ? CargoAll : CargoDump);
            sellAllButtonArray[i].setVisible(true);
            
            buyPriceArray[i].setText(buy[i] > 0 ? formatMoney(buy[i]) : CargoBuyNA);
            
            buyButtonArray[i].setText(commander.getCurrentSystem().getTradeItems()[i]);
            buyButtonArray[i].setVisible(buy[i] > 0);
            
            buyMaxButtonArray[i].setVisible(buy[i] > 0);

            if (sell[i] * commander.getShip().getCargo()[i] > commander.getPriceCargo()[i]) {
                sellPriceArray[i].setFont(BOLD_FONT);
            } else {
                sellPriceArray[i].setFont(sellPriceLabel.getFont());
            }

            if (warpSystem != null && warpSystem.destIsOk() && price > 0) {
                targetPriceArray[i].setText(formatMoney(price));
            } else {
                targetPriceArray[i].setText(TARGET_PRICE_NA);
            }

            if (warpSystem != null && warpSystem.destIsOk() && price > 0 && buy[i] > 0) {
                int diff = price - buy[i];
                targetDiffArray[i].setText((diff > 0 ? "+" : "")  + formatMoney(diff));
                targetPercentageArray[i].setText((diff > 0 ? "+" : "") + formatNumber(100 * diff / buy[i]) + "%");
                buyPriceArray[i].setFont((diff > 0 && commander.getCurrentSystem().getTradeItems()[i] > 0) 
                        ? BOLD_FONT : buyPriceLabel.getFont());
            } else {
                targetDiffArray[i].setText(TARGET_DIFF_NA);
                targetPercentageArray[i].setText(TARGET_PERCENTAGE_NA);
                buyPriceArray[i].setFont(buyPriceLabel.getFont());
            }

            targetPriceArray[i].setFont(buyPriceArray[i].getFont());
            targetDiffArray[i].setFont(buyPriceArray[i].getFont());
            targetPercentageArray[i].setFont(buyPriceArray[i].getFont());
        }
    }

    private void buySellButtonClick(String buttonName) {
        boolean all = buttonName.contains("AllButton") || buttonName.contains("MaxButton");
        int index = Integer.parseInt(buttonName.substring(buttonName.length() - 1));

        if (!buttonName.startsWith("buy")) {
            controller.cargoSell(index, all);
        } else {
            controller.cargoBuy(index, all);
        }
    }

}
