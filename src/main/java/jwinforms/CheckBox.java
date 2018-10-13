package jwinforms;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CheckBox extends WinformControl
{
	public CheckBox()
	{
		super(new JCheckBox());
	}

	public void setText(String text)
	{
		asJCheckBox().setText(text);
	}

	public String getText()
	{
		return asJCheckBox().getText();
	}

	public void setChecked(boolean checked)
	{
		asJCheckBox().setSelected(checked);
	}

	public boolean isChecked()
	{
		return asJCheckBox().isSelected();
	}

	public JCheckBox asJCheckBox()
	{
		return ((JCheckBox)swingVersion);
	}

	public void setCheckedChanged(final EventHandler<Object, EventArgs> handler)
	{
		asJCheckBox().addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				handler.handle(CheckBox.this, null);
			}
		});
	}
}
