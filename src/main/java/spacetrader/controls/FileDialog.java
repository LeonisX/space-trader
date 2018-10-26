package spacetrader.controls;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import spacetrader.controls.enums.DialogResult;

public class FileDialog {

    private final JFileChooser chooser = new JFileChooser();

    private String approveButtonText;
    //TODO
    private String string;
    private String defaultExt;
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    void setApproveButtonText(String approveButtonText) {
        this.approveButtonText = approveButtonText;
    }

    public DialogResult showDialog(WinformPane owner) {
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
        //setFilter("Windows Bitmaps (*.bmp)|*bmp")
        String[] parts = filter.split("\\|");

        String desc = parts[0];
        String[] extensions = parts[1].split(";");
        // I assume the format is "*.bmp;*.txt;*.gif".
        for (int i = 0; i < extensions.length; i++) {
            String extension = extensions[i];
            extensions[i] = extension.substring(extension.lastIndexOf('.') + 1);
        }

        FileFilter filefilter = new FileNameExtensionFilter(desc, extensions);
        chooser.setFileFilter(filefilter);
    }

    public void setDefaultExt(String defaultExt) {
        this.defaultExt = defaultExt;
    }

    public String getFileName() {
        return chooser.getSelectedFile().getAbsolutePath();
    }

    public void setFileName(String fileName) {
        chooser.setSelectedFile(new File(fileName));
    }
}
