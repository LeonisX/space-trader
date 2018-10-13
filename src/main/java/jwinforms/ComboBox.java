package jwinforms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class ComboBox extends WinformControl
{
	public ComboBox()
	{
		super(new JComboBox());
		asJComboBox().setModel(Items);
	}

	public JComboBox asJComboBox()
	{
		return (JComboBox)swingVersion;
	}

	public int getSelectedIndex()
	{
		return asJComboBox().getSelectedIndex();
	}

	public void setSelectedIndex(int index)
	{
		asJComboBox().setSelectedIndex(index);
	}

	public void setSelectedIndexChanged(final EventHandler<Object, EventArgs> handler)
	{
		asJComboBox().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				handler.handle(ComboBox.this, null);
			}
		});
	}

	public Object getSelectedItem()
	{
		return asJComboBox().getSelectedItem();
	}

	public final MyComboBoxModel Items = new MyComboBoxModel();

	// probably don't care.
	public ComboBoxStyle DropDownStyle;
}
