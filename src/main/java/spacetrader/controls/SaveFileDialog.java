package spacetrader.controls;

import spacetrader.util.Path;

//TODO may be remove this class
public class SaveFileDialog extends FileDialog {

    public SaveFileDialog() {
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
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
