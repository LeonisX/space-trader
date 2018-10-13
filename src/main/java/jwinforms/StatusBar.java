package jwinforms;

import swingextra.JStatusBar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StatusBar extends WinformControl {

    protected StatusBar Panels = this;
    protected EventHandler<Object, StatusBarPanelClickEventArgs> PanelClick;
    protected boolean ShowPanels;
    protected boolean SizingGrip;

    protected StatusBar() {
        super(new JStatusBar());
    }

    public void addAll(Iterable<StatusBarPanel> asList) {
        for (StatusBarPanel panel : asList)
            add(panel);
    }

    private void add(StatusBarPanel panel) {
        asJStatusBar().addSection(panel.asJStatusBarSection(), panel.AutoSize == StatusBarPanelAutoSize.Spring);
        panel.asJStatusBarSection().addMouseListener(new MouseAdapterExtension(panel));
    }

    private JStatusBar asJStatusBar() {
        return (JStatusBar) swingVersion;
    }

    private final class MouseAdapterExtension extends MouseAdapter {
        private final StatusBarPanel source;

        MouseAdapterExtension(StatusBarPanel source) {
            super();
            this.source = source;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (PanelClick != null)
                PanelClick.handle(source, new StatusBarPanelClickEventArgs(source));
        }
    }
}
