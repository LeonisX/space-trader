package jwinforms;

abstract public class EventHandler<Sender, Args>
{
	public abstract void handle(Sender sender, Args e);
}

