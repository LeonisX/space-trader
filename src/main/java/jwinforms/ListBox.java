package jwinforms;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListBox extends WinformControl
{
	public final MyListModel Items = new MyListModel();

	@Override
	public void setBorderStyle(BorderStyle borderStyle)
	{
		if (borderStyle != BorderStyle.FixedSingle)
			throw new Error("Unknown border style");

		asJList().setBorder(BorderFactory.createLineBorder(Color.black, 1));
	}

	public ListBox()
	{
		super(new JList());
		asJList().setModel(Items);
	}

	public void clearSelected()
	{
		asJList().clearSelection();
	}

	public JList asJList()
	{
		return (JList)swingVersion;
	}

	public void setSelectedIndexChanged(final EventHandler<Object, EventArgs> handler)
	{
		asJList().addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				handler.handle(ListBox.this, null);
			}
		});
	}

	public int getSelectedIndex()
	{
		return asJList().getSelectedIndex();
	}

	public int getItemHeight()
	{
		return 15;
	}

	public void setSelectedItem(Object selectedItem)
	{
		asJList().setSelectedValue(selectedItem, true);
	}

	public Object getSelectedItem()
	{
		return asJList().getSelectedValue();
	}
}
