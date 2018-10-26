package spacetrader.controls;

public class CancelEventArgs extends EventArgs {

    private boolean cancel;

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}
