package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.StatusBarPanelAutoSize;
import spacetrader.game.Commander;
import spacetrader.util.Functions;

import static spacetrader.game.Strings.*;

class SpaceTraderStatusBar extends StatusBar {

    private final SpaceTrader mainWindow;

    private StatusBarPanel statusBarPanelBays = new StatusBarPanel();
    private StatusBarPanel statusBarPanelCash = new StatusBarPanel();
    private StatusBarPanel statusBarPanelCosts = new StatusBarPanel();
    private StatusBarPanel statusBarPanelExtra = new StatusBarPanel(StatusBarPanelAutoSize.SPRING);

    private Commander commander;

    SpaceTraderStatusBar(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    public void initializeComponent() {
        setSize(768, 24);
        setTabIndex(2);
        showPanels = true;
        sizingGrip = false;

        panelClick = new EventHandler<Object, StatusBarPanelClickEventArgs>() {
            @Override
            public void handle(Object sender, StatusBarPanelClickEventArgs e) {
                statusBarPanelClick(e);
            }
        };

        statusBarPanelCash.setMinWidth(112);
        //statusBarPanelCash.setText(" Cash: 88,888,888 cr.");
        statusBarPanelCash.setWidth(112);

        statusBarPanelBays.setMinWidth(80);
        //statusBarPanelBays.setText(" Bays: 88/88");
        statusBarPanelBays.setWidth(80);

        statusBarPanelCosts.setMinWidth(120);
        //statusBarPanelCosts.setText(" Current Costs: 888 cr.");
        statusBarPanelCosts.setWidth(120);

        panels.addAll(statusBarPanelCash, statusBarPanelBays, statusBarPanelCosts, statusBarPanelExtra);
        
        endInit();
    }

    @Override
    public void beginInit() {
        //TODO need?
        statusBarPanelCash.beginInit();
        statusBarPanelBays.beginInit();
        statusBarPanelCosts.beginInit();
        statusBarPanelExtra.beginInit();
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
            statusBarPanelExtra.setText(StatusBarNoGameLoaded);
        } else {
            statusBarPanelCash.setText(StatusBarCash + " " + Functions.formatMoney(commander.getCash()));
            statusBarPanelBays.setText(StatusBarBays + " " + commander.getShip().getFilledCargoBays() + "/"
                    + commander.getShip().getCargoBays());
            statusBarPanelCosts.setText(StatusBarCurrentCosts + " " + Functions.formatMoney(commander.getCurrentCosts()));
            statusBarPanelExtra.setText("");
        }
    }

    private void statusBarPanelClick(StatusBarPanelClickEventArgs e) {
        if (commander != null) {
            if (e.getStatusBarPanel() == statusBarPanelCash) {
                mainWindow.showBank();
            } else if (e.getStatusBarPanel() == statusBarPanelCosts) {
                (new FormCosts()).showDialog(mainWindow);
            }
        }
    }
}
