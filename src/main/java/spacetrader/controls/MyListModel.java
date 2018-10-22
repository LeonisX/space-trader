package spacetrader.controls;

import javax.swing.*;

public class MyListModel<Object> extends DefaultListModel<Object> {

    //TODO inline method, use super-class.
    public void add(Object obj) {
        this.addElement(obj);
    }

}
