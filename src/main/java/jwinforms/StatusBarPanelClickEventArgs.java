package jwinforms;

public class StatusBarPanelClickEventArgs extends EventArgs {

    public final StatusBarPanel StatusBarPanel;

    StatusBarPanelClickEventArgs(StatusBarPanel statusBarPanel) {
        super();
        StatusBarPanel = statusBarPanel;
    }

}
