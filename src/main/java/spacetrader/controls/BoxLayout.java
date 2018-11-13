package spacetrader.controls;

import java.awt.Container;

public class BoxLayout implements Layout {

    private javax.swing.BoxLayout layout;

    public BoxLayout(Container target, int axis) {
        layout = new javax.swing.BoxLayout(target, axis);
    }

    public java.awt.LayoutManager getLayout() {
        return layout;
    }

}
