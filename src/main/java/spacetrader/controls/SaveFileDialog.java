package spacetrader.controls;

import spacetrader.game.GlobalAssets;
import spacetrader.util.Path;

import javax.swing.*;

public class SaveFileDialog extends FileDialog {

    private static final String NAME = "saveFileDialog";

    public SaveFileDialog(String name) {
        super(new JFileChooser());
        if (!GlobalAssets.getStrings().isEmpty()) {
            setFileName(GlobalAssets.getStrings().get(name, NAME, "fileName"));
            setFilter(GlobalAssets.getStrings().get(name, NAME, "filter"));
            setTitle(GlobalAssets.getStrings().getTitle(name, NAME));
            setApproveButtonText(GlobalAssets.getStrings().getText(name, NAME, "approveButton"));
        }
    }

    @Override
    public String getFileName() {
        return Path.defaultExtension(super.getFileName(), ".sav");
    }
}
