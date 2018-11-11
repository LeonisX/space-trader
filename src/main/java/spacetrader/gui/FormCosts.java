package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.HorizontalLine;
import spacetrader.controls.Label;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.util.Functions;
import spacetrader.game.Game;
import spacetrader.util.ReflectionUtils;

public class FormCosts extends SpaceTraderForm {

    private Button closeButton = new Button();
    private Label mercenariesLabelValue = new Label();
    private Label insuranceLabelValue = new Label();
    private Label interestLabelValue = new Label();
    private Label wormholeTaxLabelValue = new Label();
    private Label totalLabelValue = new Label();
    private Label totalLabel = new Label();
    private Label wormholeTaxLabel = new Label();
    private Label interestLabel = new Label();
    private Label mercenariesLabel = new Label();
    private Label insuranceLabel = new Label();
    private HorizontalLine horizontalLine = new HorizontalLine();

    public FormCosts() {
        initializeComponent();

        Game game = Game.getCurrentGame();
        mercenariesLabelValue.setText(Functions.formatMoney(game.getMercenaryCosts()));
        insuranceLabelValue.setText(Functions.formatMoney(game.getInsuranceCosts()));
        interestLabelValue.setText(Functions.formatMoney(game.getInterestCosts()));
        wormholeTaxLabelValue.setText(Functions.formatMoney(game.getWormholeCosts()));
        totalLabelValue.setText(Functions.formatMoney(game.getCurrentCosts()));
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);
        
        setName("formCosts");
        setText("Cost Specification");
        setClientSize(175, 110);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        mercenariesLabel.setAutoSize(true);
        mercenariesLabel.setFont(FontCollection.bold825);
        mercenariesLabel.setLocation(8, 8);
        //mercenariesLabel.setSize(72, 13);
        mercenariesLabel.setText("Mercenaries:");

        mercenariesLabelValue.setAutoSize(true);
        mercenariesLabelValue.setLocation(114, 9);
        //mercenariesLabelValue.setSize(39, 13);
        //mercenariesLabelValue.setText("888 cr.");

        insuranceLabel.setAutoSize(true);
        insuranceLabel.setFont(FontCollection.bold825);
        insuranceLabel.setLocation(8, 25);
        //insuranceLabel.setSize(59, 13);
        insuranceLabel.setText("Insurance:");

        insuranceLabelValue.setAutoSize(true);
        insuranceLabelValue.setLocation(114, 24);
        //insuranceLabelValue.setSize(39, 13);
        //insuranceLabelValue.setText("888 cr.");

        interestLabel.setAutoSize(true);
        interestLabel.setFont(FontCollection.bold825);
        interestLabel.setLocation(8, 41);
        //interestLabel.setSize(47, 13);
        interestLabel.setText("Interest:");

        interestLabelValue.setAutoSize(true);
        interestLabelValue.setLocation(114, 40);
        //interestLabelValue.setSize(39, 13);
        //interestLabelValue.setText("888 cr.");

        wormholeTaxLabel.setAutoSize(true);
        wormholeTaxLabel.setFont(FontCollection.bold825);
        wormholeTaxLabel.setLocation(8, 57);
        //wormholeTaxLabel.setSize(84, 13);
        wormholeTaxLabel.setText("Wormhole Tax:");

        wormholeTaxLabelValue.setAutoSize(true);
        wormholeTaxLabelValue.setLocation(114, 56);
        //wormholeTaxLabelValue.setSize(39, 13);
        //wormholeTaxLabelValue.setText("888 cr.");

        horizontalLine.setLocation(6, 73);
        horizontalLine.setWidth(160);
        horizontalLine.setTabStop(false);

        totalLabel.setAutoSize(true);
        totalLabel.setFont(FontCollection.bold825);
        totalLabel.setLocation(8, 80);
        //totalLabel.setSize(34, 13);
        totalLabel.setTabIndex(7);
        totalLabel.setText("Total:");

        totalLabelValue.setAutoSize(true);
        totalLabelValue.setLocation(114, 79);
        //totalLabelValue.setSize(39, 13);
        //totalLabelValue.setText("888 cr.");

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setVisible(false);
        closeButton.setTabStop(false);

        controls.addAll(mercenariesLabel, mercenariesLabelValue, insuranceLabel, insuranceLabelValue, 
                interestLabel, interestLabelValue, wormholeTaxLabel, wormholeTaxLabelValue, horizontalLine, 
                totalLabel, totalLabelValue, closeButton);

        ReflectionUtils.loadControlsData(this);
    }
}
