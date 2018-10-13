package jwinforms;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class WinformJPanel extends JPanel {

    private final WinformPane form;
    Image backgroundImage = null;
    private Map<Component, Integer> tabOrderMap = new HashMap<>();

    WinformJPanel(WinformPane form) {
        super(null); // That's what winforms use.
        this.form = form;

        setFocusTraversalPolicy(new SortingFocusTraversalPolicy(Comparator.comparing(o -> tabOrderMap.get(o))) {
            @Override
            protected boolean accept(Component component) {
                return tabOrderMap.containsKey(component);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage.asSwingImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    private void setFocusOrder(Component component, int order) {
        if (order == -1) {
            tabOrderMap.remove(component);
        } else {
            tabOrderMap.put(component, order);
        }
    }

    public void add(final WinformControl wccont) {
        if (wccont instanceof Button) {
            handleDialogResult((Button) wccont);
        }
        Component ob = wccont.asSwingObject();
        add(ob);
        setFocusOrder(ob, wccont.getTabIndex());
        ob.addMouseListener(wccont.getMouseListener());
    }

    public void addAll(Collection<? extends WinformControl> coll) {
        for (WinformControl winformControl : coll) {
            this.add(winformControl);
        }
    }

    public void addAll(WinformControl... coll) {
        for (WinformControl winformControl : coll) {
            this.add(winformControl);
        }
    }

    private void handleDialogResult(final Button button) {
        if (button.DialogResult != null) {
            button.setClick(new ChainedEventHandler<Object, EventArgs>(button.click) {
                @Override
                public void instanceHandle(Object sender, EventArgs e) {
                    form.setResult(button.DialogResult);
                    form.dispose();
                }
            });
        }
    }
}
