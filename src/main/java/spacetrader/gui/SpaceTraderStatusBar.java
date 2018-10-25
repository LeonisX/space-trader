package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.game.Commander;
import spacetrader.game.Functions;

import java.util.Arrays;

import static spacetrader.game.Strings.*;

class SpaceTraderStatusBar extends StatusBar {

    private final SpaceTrader mainWindow;

    private StatusBarPanel statusBarPanelBays;
    private StatusBarPanel statusBarPanelCash;
    private StatusBarPanel statusBarPanelCosts;
    private StatusBarPanel statusBarPanelExtra;

    private Commander commander;

    SpaceTraderStatusBar(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(Commander commander) {
        this.commander = commander;
    }

    public void initializeComponent() {
        panels.addAll(statusBarPanelCash, statusBarPanelBays, statusBarPanelCosts, statusBarPanelExtra);
        showPanels = true;
        setSize(new Size(768, 24));
        sizingGrip = false;
        setTabIndex(2);

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

        endInit();
    }

    @Override
    public void beginInit() {
        statusBarPanelCash = new StatusBarPanel();
        statusBarPanelBays = new StatusBarPanel();
        statusBarPanelCosts = new StatusBarPanel();
        statusBarPanelExtra = new StatusBarPanel(StatusBarPanelAutoSize.SPRING);

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
