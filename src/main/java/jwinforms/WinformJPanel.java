package jwinforms;

import java.awt.Component;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SortingFocusTraversalPolicy;

public class WinformJPanel extends JPanel
{
	Map<Component, Integer> tabOrderMap = new HashMap<Component, Integer>();

	public WinformJPanel(WinformPane form)
	{
		super(null); // That's what winforms use.
		this.form = form;

		setFocusTraversalPolicy(new SortingFocusTraversalPolicy(new Comparator<Component>()
		{
			public int compare(Component o1, Component o2)
			{
				return tabOrderMap.get(o1).compareTo(tabOrderMap.get(o2));
			}
		})
		{
			@Override
			protected boolean accept(Component component)
			{
				return tabOrderMap.containsKey(component);
			}
		});
	}

	Image BackgroundImage = null;

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (BackgroundImage != null)
		{
			g.drawImage(BackgroundImage.asSwingImage(), 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

	private final WinformPane form;

	public void setFocusOrder(Component component, int order)
	{
		if (order == -1)
			tabOrderMap.remove(component);
		else
			tabOrderMap.put(component, order);
	}

	public void add(final WinformControl wccont)
	{
		if (wccont instanceof Button)
			handleDialogResult((Button)wccont);
		Component ob = wccont.asSwingObject();
		add(ob);
		setFocusOrder(ob, wccont.getTabIndex());
		ob.addMouseListener(wccont.getMouseListener());
	}

	public void addAll(Collection<? extends WinformControl> coll)
	{
		for (WinformControl winformControl : coll)
			this.add(winformControl);
	}

	public void addAll(WinformControl... coll)
	{
		for (WinformControl winformControl : coll)
			this.add(winformControl);
	}

	void handleDialogResult(final Button button)
	{
		if (button.DialogResult != null)
			button.setClick(new ChainedEventHandler<Object, EventArgs>(button.click)
			{
				@Override
				public void instanceHandle(Object sender, EventArgs e)
				{
					form.setResult(button.DialogResult);
					form.dispose();
				}
			});
	}
}
