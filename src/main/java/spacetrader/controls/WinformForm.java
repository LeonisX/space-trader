package spacetrader.controls;

import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;

import javax.swing.*;
import java.awt.Dialog.ModalityType;

// TODO make Closing etc work.
public class WinformForm extends WinformPane {

    protected final WinformJPanel controls;
    private final JDialog jDialog;
    private final WinformJPanel panel;

    private DialogResult result;
    // Must encapsulate most of these.
    private boolean showInTaskbar;
    private boolean minimizeBox;
    private boolean maximizeBox;
    private FormStartPosition startPosition;
    private Button acceptButton;
    private Button cancelButton;
    private String title;
    private WinformPane parent;

    protected WinformForm() {
        // super(new WinformJPanel());
        super(new JDialog());
        jDialog = (JDialog) swingComponent;
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        panel = new WinformJPanel(this);
        jDialog.setContentPane(panel);
        controls = panel;
        jDialog.setResizable(false);
    }

    // ///////////// implementation ends here.
    protected Graphics createGraphics() {
        return new Graphics(jDialog.getGraphics());
    }

    public DialogResult showDialog() {
        return showDialog(null);
    }

    // This should be "modal", i.e. - parent is blocked.
    public DialogResult showDialog(WinformPane owner) {
        parent = owner;

        panel.addMouseListener(getMouseListener());
        jDialog.setModalityType(ModalityType.APPLICATION_MODAL);

        fixLocation();
        show();

        return result;
    }

    private void fixLocation() {
        if (startPosition == null) {
            return;
        }
        switch (startPosition) {
            case CENTER_PARENT:
                jDialog.setLocationRelativeTo(parent == null ? null : parent.asSwingObject());
                break;
            case MANUAL:
                break;
            default:
                throw new Error("Unknown startPosition kind: " + startPosition);
        }
    }

    public void close() {
        jDialog.setVisible(false);
        jDialog.dispose();
    }

    protected void performLayout() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Button getAcceptButton() {
        return acceptButton;
    }

    public void setAcceptButton(Button acceptButton) {
        jDialog.getRootPane().setDefaultButton(acceptButton.asJButton());
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    // TODO handle.
    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public void setStartPosition(FormStartPosition startPosition) {
        this.startPosition = startPosition;
    }

    public void setControlBox(boolean controlBox) {
        jDialog.setDefaultCloseOperation(controlBox ? JDialog.DISPOSE_ON_CLOSE : JDialog.DO_NOTHING_ON_CLOSE);
    }

    public boolean getShowInTaskbar() {
        return showInTaskbar;
    }

    public void setShowInTaskbar(boolean showInTaskbar) {
        this.showInTaskbar = showInTaskbar;
    }

    public boolean getMinimizeBox() {
        return minimizeBox;
    }

    public void setMinimizeBox(boolean minimizeBox) {
        this.minimizeBox = minimizeBox;
    }

    public boolean getMaximizeBox() {
        return maximizeBox;
    }

    public void setMaximizeBox(boolean maximizeBox) {
        this.maximizeBox = maximizeBox;
    }

    public void setFormBorderStyle(FormBorderStyle style) {
        switch (style) {
            case FIXED_DIALOG:
                jDialog.setUndecorated(false);
                break;
            case FIXED_SINGLE:
                jDialog.setUndecorated(false);
                break;
            case NONE:
                jDialog.setUndecorated(true);
                break;

            default:
                throw new Error("Unknown border style: " + style);
        }
    }

    public void setClientSize(int width, int height) {
        setSize(width, height);
        // bigger, cause decorations count in swing.
        //setSize(width + 10, height + 30));
    }

    public String getText() {
        return jDialog.getTitle();
    }

    public void setText(String text) {
        jDialog.setTitle(text);
    }

    @Override
    public void dispose() {
        jDialog.dispose();
    }

    @Override
    public void setResult(DialogResult dialogResult) {
        result = dialogResult;
    }

    protected Image getBackgroundImage() {
        return panel.backgroundImage;
    }

    protected void setBackgroundImage(Image backgroundImage) {
        panel.backgroundImage = backgroundImage;
    }
}
