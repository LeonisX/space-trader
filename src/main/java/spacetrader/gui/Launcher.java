package spacetrader.gui;

import spacetrader.controls.DialogResult;
import spacetrader.controls.WinformForm;

import javax.swing.*;


class Launcher {

    static void runForm(WinformForm form) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        //TODO detect OS type
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        SwingUtilities.updateComponentTreeUI(form.asSwingObject());
        DialogResult res = form.showDialog(null);
        System.out.println("Dialog result: " + res);
    }

}
