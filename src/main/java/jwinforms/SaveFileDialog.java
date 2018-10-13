package jwinforms;

import util.Path;

public class SaveFileDialog extends FileDialog {

    public SaveFileDialog() {
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public void setButtonText(String text) {
        super.setButtonText(text);
    }

    @Override
    public String getFileName() {
        return Path.DefaultExtension(super.getFileName(), ".sav");
    }
}
