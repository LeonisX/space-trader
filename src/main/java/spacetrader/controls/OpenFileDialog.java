package spacetrader.controls;

import spacetrader.game.GlobalAssets;

import javax.swing.*;

public class OpenFileDialog extends FileDialog {

    private static final String NAME = "openFileDialog";

    public OpenFileDialog(String name) {
        super(new JFileChooser());
        if (!GlobalAssets.getStrings().isEmpty()) {
            setFilter(GlobalAssets.getStrings().get(name, NAME, "filter"));
            setTitle(GlobalAssets.getStrings().getTitle(name, NAME));
            setApproveButtonText(GlobalAssets.getStrings().getText(name, NAME, "approveButton"));
        }
    }
}
