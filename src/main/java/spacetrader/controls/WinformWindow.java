package spacetrader.controls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;

public class WinformWindow extends WinformPane {

    protected final WinformJPanel controls;
    private final JFrame frame;
    private final WinformJPanel panel;
    protected Integer left, top;
    protected FormWindowState windowState;
    private DialogResult result;
    private EventHandler<Object, CancelEventArgs> onClosing;
    private EventHandler<Object, EventArgs> onClosed;
    private FormBorderStyle formBorderStyle;
    private boolean controlBox;
    private boolean minimizeBox;
    private boolean maximizeBox;
    private String title;

    protected WinformWindow() {
        super(new JFrame());
        frame = (JFrame) swingComponent;
        frame.addWindowListener(new WindowListener());

        panel = new WinformJPanel(this);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        controls = panel;
        frame.setResizable(false);
        //TODO
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    protected void setClosing(EventHandler<Object, CancelEventArgs> closing) {
        onClosing = closing;
    }

    protected void setClosed(EventHandler<Object, EventArgs> closed) {
        onClosed = closed;
    }

    public void showWindow() {
        fixLocation();
        show();
    }

    private void fixLocation() {
        frame.setLocationRelativeTo(null);
    }

    protected void close() {
        WindowEvent wev = new WindowEvent((Window) swingComponent, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartPosition(FormStartPosition startPosition) {
        //TODO implement method.
    }

    protected void setMenu(MainMenu menu) {
        frame.getContentPane().add(menu.asSwingObject(), BorderLayout.PAGE_START);
    }

    public boolean getControlBox() {
        return controlBox;
    }

    public void setControlBox(boolean controlBox) {
        this.controlBox = controlBox;
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

    protected void setIcon(Icon icon) {
        frame.setIconImage(icon.asSwingImage());
    }

    public FormBorderStyle getFormBorderStyle() {
        return formBorderStyle;
    }

    public void setFormBorderStyle(FormBorderStyle formBorderStyle) {
        this.formBorderStyle = formBorderStyle;
    }

    public void setClientSize(int width, int height) {
        setClientSize(new Dimension(width, height));
    }

    public void setClientSize(Dimension clientSize) {
        // heigher, cause decorations count in swing.
        frame.setSize(new Dimension(clientSize.width, clientSize.height + 45));
    }

    public String getText() {
        return frame.getTitle();
    }

    public void setText(String text) {
        frame.setTitle(text);
    }

    protected void setStatusBar(StatusBar statusBar) {
        frame.getContentPane().add(statusBar.asSwingObject(), BorderLayout.PAGE_END);
    }

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void dispose() {
        frame.dispose();
    }

    @Override
    public void setResult(DialogResult dialogResult) {
        result = dialogResult;
    }

    // ///////////// implementation ends here.
    protected enum FormWindowState {
        NORMAL
    }

    private class WindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            CancelEventArgs args = new CancelEventArgs();
            if (onClosing != null) {
                onClosing.handle(WinformWindow.this, args);
            }
            if (!args.isCancel()) {
                frame.dispose();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {
            if (onClosed != null) {
                onClosed.handle(WinformWindow.this, new EventArgs());
            }
            super.windowClosed(e);
        }
    }
}
