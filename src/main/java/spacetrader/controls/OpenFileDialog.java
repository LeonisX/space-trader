package spacetrader.controls;

import javax.swing.*;

public class OpenFileDialog extends FileDialog {

    public OpenFileDialog() {
        super(new JFileChooser());
    }
}
