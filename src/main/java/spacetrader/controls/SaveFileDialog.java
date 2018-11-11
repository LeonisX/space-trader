package spacetrader.controls;

import spacetrader.util.Path;

public class SaveFileDialog extends FileDialog {

    public SaveFileDialog() {
    }

    @Override
    public String getFileName() {
        return Path.defaultExtension(super.getFileName(), ".sav");
    }
}
