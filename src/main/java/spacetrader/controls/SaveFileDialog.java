package spacetrader.controls;

import spacetrader.util.Path;

import javax.swing.*;

public class SaveFileDialog extends FileDialog {

    public SaveFileDialog() {
        super(new JFileChooser());
    }

    @Override
    public String getFileName() {
        return Path.defaultExtension(super.getFileName(), ".sav");
    }
}
