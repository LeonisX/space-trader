package jwinforms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Button extends WinformControl
{
	public Button()
	{
		super(new JButton());
	}

	DialogResult DialogResult;
	Action userAction;

	public void setText(String text)
	{
		asJButtton().setText(text);
	}

	public String getText()
	{
		return asJButtton().getText();
	}

	@Override
	public void setSize(Dimension size)
	{
		// width should be bigger because font is bigger(?).
		super.setSize(new Dimension(size.width, size.height));
	}

	public JButton asJButtton()
	{
		return (JButton)swingVersion;
	}

	public void setDialogResult(DialogResult dialogResult)
	{
		DialogResult = dialogResult;
	}

//	@SuppressWarnings("serial")
	@Override
	public void setClick(final EventHandler<Object, EventArgs> click)
	{
		userAction = new AbstractAction()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				click.handle(Button.this, null);
			}
		};
		userAction.putValue(AbstractAction.NAME, getText());
		asJButtton().setAction(userAction);
		super.setClick(click);
	}

		public void setFlatStyle(FlatStyle flatStyle)
	{
		switch (flatStyle)
		{
		case Flat:
			asJButtton().setBorder(BorderFactory.createLineBorder(Color.black));
			return;

		default:
			throw new Error("Unknown FlatStyle");
		}
		// I think this is default.
		//asJButtton().setBorder(BorderFactory.createRaisedBevelBorder());
	}
}
