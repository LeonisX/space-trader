package spacetrader.controls;

import spacetrader.util.Path;

public class SaveFileDialog extends FileDialog {

    public SaveFileDialog() {
    }

    @Override
    public void setApproveButtonText(String text) {
        super.setApproveButtonText(text);
    }

    @Override
    public String getFileName() {
        return Path.defaultExtension(super.getFileName(), ".sav");
    }
}
