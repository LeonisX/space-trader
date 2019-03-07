package spacetrader.controls;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.GlobalAssets;
import spacetrader.util.ReflectionUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileDialog extends BaseComponent {

    private final JFileChooser chooser;

    private String name = "fileDialog";

    private String approveButtonText;
    private String title;

    FileDialog(Component swingComponent) {
        super(swingComponent);
        chooser = (JFileChooser) swingComponent;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setApproveButtonText(String approveButtonText) {
        this.approveButtonText = approveButtonText;
    }

    public DialogResult showDialog(WinformPane owner) {
        ReflectionUtils.loadControlsData(this);
        ReflectionUtils.loadControlsStrings(chooser, getName(), GlobalAssets.getStrings());

        chooser.setDialogTitle(title);
        chooser.setAcceptAllFileFilterUsed(false);
        JPanel panel = (JPanel) chooser.getComponent(0);
        panel.removeAll();
        panel.setMaximumSize(new Dimension(panel.getWidth(), 8));

        int returnVal = chooser.showDialog(owner.asSwingObject(), approveButtonText);

        switch (returnVal) {
            case JFileChooser.CANCEL_OPTION:
            case JFileChooser.ERROR_OPTION:
                return DialogResult.CANCEL;
            case JFileChooser.APPROVE_OPTION:
                return DialogResult.OK;
            default:
                throw new Error("JFileChooser returned unknown value " + returnVal);
        }
    }

    public void setInitialDirectory(String dir) {
        chooser.setCurrentDirectory(new File(dir));
    }

    public void setFilter(String filter) {
        String[] parts = filter.split("\\|");

        for (int i = 0; i < parts.length / 2; i++) {
            String desc = parts[i * 2];
            String[] extensions = parts[i * 2 + 1].split(";");
            // I assume the format is "*.bmp;*.txt;*.gif".
            for (int j = 0; j < extensions.length; j++) {
                String extension = extensions[j];
                extensions[j] = extension.substring(extension.lastIndexOf('.') + 1);
            }

            FileFilter filefilter = new FileNameExtensionFilter(desc, extensions);

            if (extensions.length == 1 && extensions[0].equals("*")) {
                chooser.addChoosableFileFilter(new AcceptAllFileFilter(desc));
            } else {
                chooser.addChoosableFileFilter(filefilter);
            }
        }
    }

    public String getFileName() {
        return chooser.getSelectedFile().getAbsolutePath();
    }

    public void setFileName(String fileName) {
        chooser.setSelectedFile(new File(fileName));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    private class AcceptAllFileFilter extends FileFilter {

        private final String description;

        AcceptAllFileFilter(String description) {
            this.description = description;
        }

        public boolean accept(File f) {
            return true;
        }

        public String getDescription() {
            return description;
        }
    }
}
