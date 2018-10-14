package spacetrader.controls;

public class StatusBarPanelClickEventArgs extends EventArgs {

    public final StatusBarPanel StatusBarPanel;

    StatusBarPanelClickEventArgs(StatusBarPanel statusBarPanel) {
        super();
        StatusBarPanel = statusBarPanel;
    }

}
