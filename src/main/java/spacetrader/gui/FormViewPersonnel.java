package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.*;
import spacetrader.game.CrewMember;
import spacetrader.game.Game;
import spacetrader.game.Strings;
import spacetrader.game.enums.AlertType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import java.util.List;

public class FormViewPersonnel extends SpaceTraderForm {

    private Game game = Game.getCurrentGame();

    private Button closeButton = new Button();
    private Panel mercenariesForHirePanel = new Panel();
    private Panel mercenaryInfoPanel = new Panel();
    private Panel currentCrewPanel = new Panel();
    private Button hireFireButton = new Button();
    private Label rateLabelValue = new Label();
    private Label nameLabelValue = new Label();
    private Label engineerLabelValue = new Label();
    private Label traderLabelValue = new Label();
    private Label fighterLabelValue = new Label();
    private Label pilotLabelValue = new Label();
    private Label engineerLabel = new Label();
    private Label traderLabel = new Label();
    private Label fighterLabel = new Label();
    private Label pilotLabel = new Label();
    private ListBox forHireListBox = new ListBox();
    private ListBox crewListBox = new ListBox();
    private Label crewNoQuartersLabel = new Label();
    private Label forHireNoneLabel = new Label();

    private CrewMember selectedCrewMember = null;
    private boolean handlingSelect = false;

    public FormViewPersonnel() {
        initializeComponent();
        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formViewPersonnel");
        setText("Personnel");
        setClientSize(550, 140);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        currentCrewPanel.setLocation(8, 8);
        currentCrewPanel.setSize(155, 120);
        currentCrewPanel.setTabStop(false);
        currentCrewPanel.setText("Current Crew");

        currentCrewPanel.getControls().addAll(crewListBox, crewNoQuartersLabel);

        crewListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        crewListBox.setLocation(8, 26);
        crewListBox.setSize(137, 80);
        crewListBox.setTabIndex(6);
        crewListBox.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                hireFireClick();
            }
        });
        crewListBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        crewNoQuartersLabel.setAutoSize(true);
        crewNoQuartersLabel.setLocation(16, 26);
        //crewNoQuartersLabel.setSize(120, 16);
        crewNoQuartersLabel.setText("No quarters available");
        crewNoQuartersLabel.setVisible(false);

        mercenariesForHirePanel.setLocation(171, 8);
        mercenariesForHirePanel.setSize(165, 120);
        mercenariesForHirePanel.setTabStop(false);
        mercenariesForHirePanel.setText("Mercenaries For Hire");

        mercenariesForHirePanel.getControls().addAll(forHireListBox, forHireNoneLabel);

        forHireListBox.setBorderStyle(BorderStyle.FIXED_SINGLE);
        forHireListBox.setLocation(8, 26);
        forHireListBox.setSize(147, 80);
        forHireListBox.setTabIndex(5);
        forHireListBox.setDoubleClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                hireFireClick();
            }
        });
        forHireListBox.setSelectedIndexChanged(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                selectedIndexChanged(sender);
            }
        });

        forHireNoneLabel.setAutoSize(true);
        forHireNoneLabel.setLocation(16, 26);
        //forHireNoneLabel.setSize(120, 16);
        forHireNoneLabel.setText("No one for hire");
        forHireNoneLabel.setVisible(false);

        mercenaryInfoPanel.setLocation(344, 8);
        mercenaryInfoPanel.setSize(200, 120);
        mercenaryInfoPanel.setTabStop(false);
        mercenaryInfoPanel.setText("Mercenary Information");

        mercenaryInfoPanel.getControls().addAll(nameLabelValue, pilotLabel, pilotLabelValue,
                fighterLabel, fighterLabelValue, traderLabel, traderLabelValue, engineerLabel,
                engineerLabelValue, rateLabelValue, hireFireButton);

        nameLabelValue.setAutoSize(true);
        nameLabelValue.setFont(FontCollection.bold825);
        nameLabelValue.setLocation(12, 23);
        //nameLabelValue.setSize(72, 13);
        //nameLabelValue.setText("Alexey Leonov");

        pilotLabel.setAutoSize(true);
        pilotLabel.setLocation(12, 45);
        //pilotLabel.setSize(29, 16);
        pilotLabel.setText("Pilot:");

        pilotLabelValue.setAutoSize(true);
        pilotLabelValue.setLocation(67, 45);
        //pilotLabelValue.setSize(17, 13);
        //pilotLabelValue.setText("88");

        fighterLabel.setAutoSize(true);
        fighterLabel.setLocation(12, 61);
        //fighterLabel.setSize(43, 16);
        fighterLabel.setText("Fighter:");

        fighterLabelValue.setAutoSize(true);
        fighterLabelValue.setLocation(67, 61);
        //fighterLabelValue.setSize(17, 13);
        //fighterLabelValue.setText("88");

        traderLabel.setAutoSize(true);
        traderLabel.setLocation(12, 77);
        //traderLabel.setSize(41, 16);
        traderLabel.setText("Trader:");

        traderLabelValue.setAutoSize(true);
        traderLabelValue.setLocation(67, 77);
        //traderLabelValue.setSize(17, 13);
        //traderLabelValue.setText("88");

        engineerLabel.setAutoSize(true);
        engineerLabel.setLocation(12, 93);
        //engineerLabel.setSize(53, 16);
        engineerLabel.setText("Engineer:");

        engineerLabelValue.setAutoSize(true);
        engineerLabelValue.setLocation(67, 93);
        //engineerLabelValue.setSize(17, 13);
        //engineerLabelValue.setText("88");

        rateLabelValue.setAutoSize(true);
        rateLabelValue.setControlBinding(ControlBinding.RIGHT);
        rateLabelValue.setLocation(185, 45);
        //rateLabelValue.setSize(59, 13);
        rateLabelValue.setText("88 cr. daily");

        hireFireButton.setAutoWidth(true);
        hireFireButton.setControlBinding(ControlBinding.RIGHT);
        hireFireButton.setLocation(150, 85);
        hireFireButton.setSize(36, 22);
        hireFireButton.setTabIndex(4);
        hireFireButton.setText("Hire");
        hireFireButton.setClick(new EventHandler<Object, EventArgs>() {
            public void handle(Object sender, EventArgs e) {
                hireFireClick();
            }
        });

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(currentCrewPanel, mercenariesForHirePanel, mercenaryInfoPanel, closeButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void deselectAll() {
        forHireListBox.clearSelected();
        crewListBox.clearSelected();
    }

    private void updateAll() {
        selectedCrewMember = null;

        updateForHire();
        updateCurrentCrew();
        updateInfo();
    }

    private void updateCurrentCrew() {
        CrewMember[] crewMembers = game.getShip().getCrew();

        crewListBox.getItems().clear();
        for (CrewMember crewMember : crewMembers) {
            if (crewMember == null) {
                crewListBox.getItems().addElement(Strings.PersonnelVacancy);
            } else {
                crewListBox.getItems().addElement(crewMember);
            }
        }

        boolean isEntries = (crewListBox.getItems().size() > 0);

        crewListBox.setVisible(isEntries);
        crewNoQuartersLabel.setVisible(!isEntries);

        if (isEntries) {
            crewListBox.setHeight(crewListBox.getItemHeight() * Math.min(crewListBox.getItems().size(), 6) + 2);
        }
    }

    private void updateForHire() {
        List<CrewMember> mercs = game.getCommander().getCurrentSystem().getMercenariesForHire();

        forHireListBox.getItems().clear();
        forHireListBox.getItems().addAll(mercs);

        boolean isEntries = !mercs.isEmpty();

        forHireListBox.setVisible(isEntries);
        forHireNoneLabel.setVisible(!isEntries);

        if (isEntries) {
            forHireListBox.setHeight(forHireListBox.getItemHeight() * Math.min(forHireListBox.getItems().size(), 6) + 2);
        }
    }

    private void updateInfo() {
        boolean visible = false;
        boolean rateVisible = false;
        boolean hireFireVisible = false;

        if (selectedCrewMember != null) {
            visible = true;
            if (selectedCrewMember.isMercenary()) {
                rateVisible = true;
            }

            nameLabelValue.setText(selectedCrewMember.getName());
            rateLabelValue.setText(Functions.stringVars(Strings.MoneyRateSuffix, Functions.formatMoney(selectedCrewMember
                    .getRate())));
            pilotLabelValue.setText(selectedCrewMember.getPilot());
            fighterLabelValue.setText(selectedCrewMember.getFighter());
            traderLabelValue.setText(selectedCrewMember.getTrader());
            engineerLabelValue.setText(selectedCrewMember.getEngineer());

            hireFireButton.setText(game.getShip().hasCrew(selectedCrewMember.getId()) ? Strings.MercenaryFire
                    : Strings.MercenaryHire);
            hireFireVisible = rateVisible;
        }

        nameLabelValue.setVisible(visible);
        rateLabelValue.setVisible(rateVisible);
        pilotLabel.setVisible(visible);
        fighterLabel.setVisible(visible);
        traderLabel.setVisible(visible);
        engineerLabel.setVisible(visible);
        pilotLabelValue.setVisible(visible);
        fighterLabelValue.setVisible(visible);
        traderLabelValue.setVisible(visible);
        engineerLabelValue.setVisible(visible);
        hireFireButton.setVisible(hireFireVisible);
    }

    private void hireFireClick() {
        if (selectedCrewMember != null && hireFireButton.isVisible()) {
            if (game.getShip().hasCrew(selectedCrewMember.getId())) {
                if (GuiFacade.alert(AlertType.CrewFireMercenary, selectedCrewMember.getName()) == DialogResult.YES) {
                    game.getShip().fire(selectedCrewMember.getId());

                    updateAll();
                    game.getParentWindow().updateAll();
                }
            } else {
                if (game.getShip().getFreeCrewQuartersCount() == 0)
                    GuiFacade.alert(AlertType.CrewNoQuarters, selectedCrewMember.getName());
                else {
                    game.getShip().hire(selectedCrewMember);

                    updateAll();
                    game.getParentWindow().updateAll();
                }
            }
        }
    }

    private void selectedIndexChanged(Object sender) {
        if (!handlingSelect) {
            handlingSelect = true;

            Object obj = ((ListBox) sender).getSelectedItem();
            deselectAll();

            if (obj instanceof CrewMember) {
                ((ListBox) sender).setSelectedItem(obj);
                selectedCrewMember = (CrewMember) obj;
            } else {
                selectedCrewMember = null;
            }

            handlingSelect = false;
            updateInfo();
        }
    }
}
