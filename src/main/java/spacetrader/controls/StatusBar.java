package spacetrader.controls;

import spacetrader.controls.enums.StatusBarPanelAutoSize;
import spacetrader.controls.swingextra.JStatusBar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StatusBar extends BaseComponent {

    protected StatusBar panels = this;

    protected boolean showPanels;
    protected boolean sizingGrip;

    protected EventHandler<Object, StatusBarPanelClickEventArgs> panelClick;

    protected StatusBar() {
        super(new JStatusBar());
    }

    public void addAll(StatusBarPanel... panels) {
        for (StatusBarPanel panel : panels) {
            add(panel);
        }
    }

    private void add(StatusBarPanel panel) {
        asJStatusBar().addSection(panel.asJStatusBarSection(), panel.getAutoSize() == StatusBarPanelAutoSize.SPRING);
        panel.asJStatusBarSection().addMouseListener(new MouseAdapterExtension(panel));
    }

    private JStatusBar asJStatusBar() {
        return (JStatusBar) swingComponent;
    }

    private final class MouseAdapterExtension extends MouseAdapter {
        private final StatusBarPanel source;

        MouseAdapterExtension(StatusBarPanel source) {
            super();
            this.source = source;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (panelClick != null)
                panelClick.handle(source, new StatusBarPanelClickEventArgs(source));
        }
    }
}
