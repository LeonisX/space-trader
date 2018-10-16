package spacetrader.controls;

public class StatusBarPanelClickEventArgs extends EventArgs {

    private final StatusBarPanel statusBarPanel;

    StatusBarPanelClickEventArgs(StatusBarPanel statusBarPanel) {
        super();
        this.statusBarPanel = statusBarPanel;
    }

    public StatusBarPanel getStatusBarPanel() {
        return statusBarPanel;
    }
}
