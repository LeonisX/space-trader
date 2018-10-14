package spacetrader.gui;

import spacetrader.controls.StatusBar;
import spacetrader.controls.StatusBarPanelClickEventArgs;
import spacetrader.game.Commander;
import spacetrader.game.Functions;

import java.util.Arrays;

class SpaceTraderStatusBar extends StatusBar {
    private final SpaceTrader mainWindow;
    private spacetrader.controls.StatusBarPanel statusBarPanelBays;
    private spacetrader.controls.StatusBarPanel statusBarPanelCash;
    private spacetrader.controls.StatusBarPanel statusBarPanelCosts;
    private spacetrader.controls.StatusBarPanel statusBarPanelExtra;
    private Commander commander;

    public SpaceTraderStatusBar(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    public void initializeComponent() {
        this.setName("statusBar");
        Panels.addAll(Arrays.asList(new spacetrader.controls.StatusBarPanel[]{statusBarPanelCash, statusBarPanelBays,
                statusBarPanelCosts, statusBarPanelExtra}));
        ShowPanels = true;
        this.setSize(new spacetrader.controls.Size(768, 24));
        SizingGrip = false;
        this.setTabIndex(2);
        PanelClick = new spacetrader.controls.EventHandler<Object, StatusBarPanelClickEventArgs>() {
            @Override
            public void handle(Object sender, StatusBarPanelClickEventArgs e) {
                statusBar_PanelClick(sender, e);
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
        statusBarPanelCash = new spacetrader.controls.StatusBarPanel();
        statusBarPanelBays = new spacetrader.controls.StatusBarPanel();
        statusBarPanelCosts = new spacetrader.controls.StatusBarPanel();
        statusBarPanelExtra = new spacetrader.controls.StatusBarPanel(spacetrader.controls.StatusBarPanelAutoSize.Spring);

        ((spacetrader.controls.ISupportInitialize) (statusBarPanelCash)).beginInit();
        ((spacetrader.controls.ISupportInitialize) (statusBarPanelBays)).beginInit();
        ((spacetrader.controls.ISupportInitialize) (statusBarPanelCosts)).beginInit();
        ((spacetrader.controls.ISupportInitialize) (statusBarPanelExtra)).beginInit();
    }

    @Override
    public void endInit() {
        super.endInit();
        statusBarPanelCash.endInit();
        statusBarPanelBays.endInit();
        statusBarPanelCosts.endInit();
        statusBarPanelExtra.endInit();
    }

    public void Update() {
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

    private void statusBar_PanelClick(Object sender, spacetrader.controls.StatusBarPanelClickEventArgs e) {
        if (commander != null) {
            if (e.StatusBarPanel == statusBarPanelCash)
                mainWindow.showBank();
            else if (e.StatusBarPanel == statusBarPanelCosts)
                (new FormCosts()).showDialog(mainWindow);
        }
    }
}
