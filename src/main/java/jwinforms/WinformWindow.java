package jwinforms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WinformWindow extends WinformPane {

    protected final WinformJPanel Controls;
    private final JFrame frame;
    private final WinformJPanel panel;
    protected Integer Left, Top;
    protected FormWindowState WindowState;
    DialogResult result;
    private EventHandler<Object, CancelEventArgs> onClosing;
    private EventHandler<Object, EventArgs> onClosed;
    // Must encapsulate most of these.
    private Size AutoScaleBaseSize;
    private FormBorderStyle FormBorderStyle;
    private boolean ControlBox;
    private boolean MinimizeBox;
    private boolean MaximizeBox;
    private String Title;

    protected WinformWindow() {
        super(new JFrame());
        frame = (JFrame) swingVersion;
        frame.addWindowListener(new WindowListener());

        panel = new WinformJPanel(this);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        Controls = panel;
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
        WindowEvent wev = new WindowEvent((Window) swingVersion, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }

    public Size getAutoScaleBaseSize() {
        return AutoScaleBaseSize;
    }

    public void setAutoScaleBaseSize(Size autoScaleBaseSize) {
        AutoScaleBaseSize = autoScaleBaseSize;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setStartPosition(FormStartPosition startPosition) {
        //TODO implement method.
    }

    protected void setMenu(MainMenu menu) {
        frame.getContentPane().add(menu.asSwingObject(), BorderLayout.PAGE_START);
    }

    public boolean getControlBox() {
        return ControlBox;
    }

    public void setControlBox(boolean controlBox) {
        ControlBox = controlBox;
    }

    public boolean getMinimizeBox() {
        return MinimizeBox;
    }

    public void setMinimizeBox(boolean minimizeBox) {
        MinimizeBox = minimizeBox;
    }

    public boolean getMaximizeBox() {
        return MaximizeBox;
    }

    public void setMaximizeBox(boolean maximizeBox) {
        MaximizeBox = maximizeBox;
    }

    protected void setIcon(Icon icon) {
        frame.setIconImage(icon.asSwingImage());
    }

    public FormBorderStyle getFormBorderStyle() {
        return FormBorderStyle;
    }

    public void setFormBorderStyle(FormBorderStyle formBorderStyle) {
        FormBorderStyle = formBorderStyle;
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
        Normal
    }

    private class WindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            CancelEventArgs args = new CancelEventArgs();
            if (onClosing != null)
                onClosing.handle(WinformWindow.this, args);
            if (!args.Cancel)
                frame.dispose();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            if (onClosed != null)
                onClosed.handle(WinformWindow.this, new EventArgs());
            super.windowClosed(e);
        }
    }
}
