package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.game.Commander;
import spacetrader.game.Functions;

import java.util.Arrays;

class SpaceTraderStatusBar extends StatusBar {

    private final SpaceTrader mainWindow;
    private StatusBarPanel statusBarPanelBays;
    private StatusBarPanel statusBarPanelCash;
    private StatusBarPanel statusBarPanelCosts;
    private StatusBarPanel statusBarPanelExtra;
    private Commander commander;

    SpaceTraderStatusBar(SpaceTrader mainWindow, String name) {
        this.mainWindow = mainWindow;
        setName(name);
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    public void initializeComponent() {
        panels.addAll(Arrays.asList(statusBarPanelCash, statusBarPanelBays, statusBarPanelCosts, statusBarPanelExtra));
        showPanels = true;
        setSize(new spacetrader.controls.Size(768, 24));
        sizingGrip = false;
        setTabIndex(2);
        panelClick = new spacetrader.controls.EventHandler<Object, StatusBarPanelClickEventArgs>() {
            @Override
            public void handle(Object sender, StatusBarPanelClickEventArgs e) {
                statusBar_PanelClick(e);
            }
        };
        //
        // statusBarPanelCash
        //
        statusBarPanelCash.setMinWidth(112);
        statusBarPanelCash.setText(" Cash: 88,888,888 cr.");
        statusBarPanelCash.setWidth(112);
        //
        // statusBarPanelBays
        //
        statusBarPanelBays.setMinWidth(80);
        statusBarPanelBays.setText(" Bays: 88/88");
        statusBarPanelBays.setWidth(80);
        //
        // statusBarPanelCosts
        //
        statusBarPanelCosts.setMinWidth(120);
        statusBarPanelCosts.setText(" Current Costs: 888 cr.");
        statusBarPanelCosts.setWidth(120);

        endInit();
    }

    @Override
    public void beginInit() {
        statusBarPanelCash = new StatusBarPanel();
        statusBarPanelBays = new StatusBarPanel();
        statusBarPanelCosts = new StatusBarPanel();
        statusBarPanelExtra = new StatusBarPanel(StatusBarPanelAutoSize.SPRING);

        ((ISupportInitialize) (statusBarPanelCash)).beginInit();
        ((ISupportInitialize) (statusBarPanelBays)).beginInit();
        ((ISupportInitialize) (statusBarPanelCosts)).beginInit();
        ((ISupportInitialize) (statusBarPanelExtra)).beginInit();
    }

    @Override
    public void endInit() {
        super.endInit();
        statusBarPanelCash.endInit();
        statusBarPanelBays.endInit();
        statusBarPanelCosts.endInit();
        statusBarPanelExtra.endInit();
    }

    void update() {
        if (commander == null) {
            statusBarPanelCash.setText("");
            statusBarPanelBays.setText("");
            statusBarPanelCosts.setText("");
            statusBarPanelExtra.setText("No Game Loaded.");
        } else {
            statusBarPanelCash.setText("Cash: " + Functions.formatMoney(commander.getCash()));
            statusBarPanelBays.setText("Bays: " + commander.getShip().FilledCargoBays() + "/"
                    + commander.getShip().CargoBays());
            statusBarPanelCosts.setText("Current Costs: " + Functions.formatMoney(commander.CurrentCosts()));
            statusBarPanelExtra.setText("");
        }
    }

    private void statusBar_PanelClick(spacetrader.controls.StatusBarPanelClickEventArgs e) {
        if (commander != null) {
            if (e.StatusBarPanel == statusBarPanelCash) {
                mainWindow.showBank();
            } else if (e.StatusBarPanel == statusBarPanelCosts) {
                (new FormCosts()).showDialog(mainWindow);
            }
        }
    }
}
