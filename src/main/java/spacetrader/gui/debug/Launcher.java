package spacetrader.gui.debug;

import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.WinformForm;

import javax.swing.*;


public class Launcher {

    public static void runForm(WinformForm form) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(form.asSwingObject());
            DialogResult res = form.showDialog(null);
            System.out.println("Dialog result: " + res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
