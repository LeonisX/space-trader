package spacetrader.gui.debug;

import spacetrader.controls.DialogResult;
import spacetrader.controls.WinformForm;

import javax.swing.*;


public class Launcher {

    public static void runForm(WinformForm form) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.updateComponentTreeUI(form.asSwingObject());
        DialogResult res = form.showDialog(null);
        System.out.println("Dialog result: " + res);
    }

}
