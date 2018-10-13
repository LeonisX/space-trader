package jwinforms;

import java.awt.*;

/**
 * base class for windows / forms.
 *
 * @author Aviv
 */
public abstract class WinformPane extends WinformControl {

    private EventHandler<Object, EventArgs> load;

    WinformPane(Window swingVersion) {
        super(swingVersion);
    }

    protected void show() {
        EventHandler<Object, EventArgs> loadHandler = load;
        raise(loadHandler, this, null);
        swingVersion.setVisible(true);
    }

    private <O, E> void raise(EventHandler<O, E> handler, O sender, E args) {
        if (handler != null)
            handler.handle(sender, args);
    }

    protected void setLoad(EventHandler<Object, EventArgs> load) {
        this.load = load;
    }

    public abstract void setResult(DialogResult dialogResult);

    public abstract void dispose();
}
