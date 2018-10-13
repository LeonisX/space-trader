package jwinforms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer
{
	private final javax.swing.Timer timer = new javax.swing.Timer(0, new ActionListener()
	{
		public void actionPerformed(ActionEvent arg0)
		{
			Tick.handle(Timer.this, null);
			Stop();
		}
	});

	public Timer(IContainer components)
	{
	}

	public EventHandler<Object, EventArgs> Tick;

	public void Start()
	{
		timer.start();
	}

	public void Stop()
	{
		timer.stop();
	}

	public void setInterval(int interval)
	{
		timer.setDelay(interval);
		timer.setInitialDelay(interval);
	}
}
