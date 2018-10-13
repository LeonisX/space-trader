package spacetrader.gui;

import jwinforms.StatusBar;
import jwinforms.StatusBarPanelClickEventArgs;
import spacetrader.Commander;
import spacetrader.Functions;

import java.util.Arrays;

class SpaceTraderStatusBar extends StatusBar {
    private final SpaceTrader mainWindow;
    private jwinforms.StatusBarPanel statusBarPanelBays;
    private jwinforms.StatusBarPanel statusBarPanelCash;
    private jwinforms.StatusBarPanel statusBarPanelCosts;
    private jwinforms.StatusBarPanel statusBarPanelExtra;
    private Commander commander;

    public SpaceTraderStatusBar(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    public void initializeComponent() {
        this.setName("statusBar");
        Panels.addAll(Arrays.asList(new jwinforms.StatusBarPanel[]{statusBarPanelCash, statusBarPanelBays,
                statusBarPanelCosts, statusBarPanelExtra}));
        ShowPanels = true;
        this.setSize(new jwinforms.Size(768, 24));
        SizingGrip = false;
        this.setTabIndex(2);
        PanelClick = new jwinforms.EventHandler<Object, StatusBarPanelClickEventArgs>() {
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
        statusBarPanelCash = new jwinforms.StatusBarPanel();
        statusBarPanelBays = new jwinforms.StatusBarPanel();
        statusBarPanelCosts = new jwinforms.StatusBarPanel();
        statusBarPanelExtra = new jwinforms.StatusBarPanel(jwinforms.StatusBarPanelAutoSize.Spring);

        ((jwinforms.ISupportInitialize) (statusBarPanelCash)).beginInit();
        ((jwinforms.ISupportInitialize) (statusBarPanelBays)).beginInit();
        ((jwinforms.ISupportInitialize) (statusBarPanelCosts)).beginInit();
        ((jwinforms.ISupportInitialize) (statusBarPanelExtra)).beginInit();
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

    private void statusBar_PanelClick(Object sender, jwinforms.StatusBarPanelClickEventArgs e) {
        if (commander != null) {
            if (e.StatusBarPanel == statusBarPanelCash)
                mainWindow.showBank();
            else if (e.StatusBarPanel == statusBarPanelCosts)
                (new FormCosts()).showDialog(mainWindow);
        }
    }
}
