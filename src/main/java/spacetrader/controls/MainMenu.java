package spacetrader.controls;

import javax.swing.*;

public class MainMenu extends BaseComponent {

    public MainMenu(String name) {
        super(new JMenuBar());
        setName(name);
    }

    public void add(MenuItem item) {
        asJMenuBar().add(item.asJMenuItem());
    }

    public void addAll(MenuItem... items) {
        for (MenuItem item : items) {
            this.add(item);
        }
    }

    private JMenuBar asJMenuBar() {
        return (JMenuBar) swingComponent;
    }
}
