package spacetrader.gui;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

import jwinforms.DialogResult;
import jwinforms.WinformForm;
import jwinforms.WinformPane;

abstract class SpaceTraderForm extends WinformForm
{
	protected SpaceTraderForm()
	{
		((Window)swingVersion).addWindowListener(new MyListener());
	}

	public DialogResult Show()
	{
		return super.ShowDialog(FormsOwnerTree.top());
	}

	class MyListener extends WindowAdapter
	{
		@Override
		public void windowClosed(WindowEvent e)
		{
			// make sure this won't be called more than once.
			// (sync not needed, because this runs on the swing's thread).
			((Window)swingVersion).removeWindowListener(this);
			FormsOwnerTree.pop(SpaceTraderForm.this);
		}

		@Override
		public void windowOpened(WindowEvent e)
		{
			FormsOwnerTree.add(SpaceTraderForm.this);
		}
	}
}

abstract class FormsOwnerTree
{
	static private final Stack<WinformPane> stack = new Stack<WinformPane>();

	static public void add(WinformPane e)
	{
		stack.push(e);
	}

	static WinformPane top()
	{
		return stack.empty() ? null : stack.peek();
	}

	static public void pop(WinformPane ob)
	{
		WinformPane top = stack.pop();
		if (top != ob)
			throw new Error("Stack order error; expected " + ob + ", found " + top);
	}
}
