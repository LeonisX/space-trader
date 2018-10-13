package spacetrader.gui;

import jwinforms.DialogResult;
import jwinforms.WinformForm;

import javax.swing.*;


public class Launcher {

    public static void runForm(WinformForm form) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(form.asSwingObject());
        DialogResult res = form.showDialog(null);
        System.out.println("Dialog result: " + res);
    }

}
