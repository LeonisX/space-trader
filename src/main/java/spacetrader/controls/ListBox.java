package spacetrader.controls;

import javax.swing.*;
import java.awt.*;

public class ListBox extends BaseComponent {

    private final MyListModel items = new MyListModel();

    public ListBox() {
        super(new JList());
        asJList().setModel(items);
    }

    public MyListModel getItems() {
        return items;
    }

    @Override
    public void setBorderStyle(BorderStyle borderStyle) {
        if (borderStyle != BorderStyle.FIXED_SINGLE)
            throw new Error("Unknown border style");

        asJList().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public void clearSelected() {
        asJList().clearSelection();
    }

    private JList asJList() {
        return (JList) swingComponent;
    }

    public void setSelectedIndexChanged(final EventHandler<Object, EventArgs> handler) {
        asJList().addListSelectionListener(e -> handler.handle(ListBox.this, null));
    }

    public int getSelectedIndex() {
        return asJList().getSelectedIndex();
    }

    public int getItemHeight() {
        return 15;
    }

    public Object getSelectedItem() {
        return asJList().getSelectedValue();
    }

    public void setSelectedItem(Object selectedItem) {
        asJList().setSelectedValue(selectedItem, true);
    }
}
