package spacetrader.controls;

import javax.swing.*;

public class SubMenu extends MenuItem {

    public SubMenu(String name) {
        super(new JMenu());
        setName(name);
    }

    public void add(MenuItem item) {
        asJMenu().add(item.asJMenuItem());
    }


    public void addAll(MenuItem... items) {
        for (MenuItem item : items)
            this.add(item);
    }

    private JMenu asJMenu() {
        return (JMenu) swingVersion;
    }
}
