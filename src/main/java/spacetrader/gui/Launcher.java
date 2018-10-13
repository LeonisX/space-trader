package spacetrader.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jwinforms.*;


public class Launcher
{

	public static void runForm(WinformForm form) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		SwingUtilities.updateComponentTreeUI(form.asSwingObject());
		DialogResult res = form.ShowDialog(null);
		System.out.println("Dialog result: "+res);
	}

}
