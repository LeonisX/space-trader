package spacetrader.controls;

public abstract class ChainedEventHandler<Sender, Args> extends EventHandler<Sender, Args> {

    private final EventHandler<Sender, Args> chain;

    ChainedEventHandler(EventHandler<Sender, Args> chain) {
        super();
        this.chain = chain;
    }

    @Override
    public final void handle(Sender sender, Args e) {
        if (chain != null) {
            chain.handle(sender, e);
        }
        this.instanceHandle(sender, e);
    }

    protected abstract void instanceHandle(Sender sender, Args e);
}
