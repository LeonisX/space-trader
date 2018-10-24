package spacetrader.controls;

import javax.swing.*;
import java.util.List;

public class MyListModel<Object> extends DefaultListModel<Object> {

    //TODO inline method, use super-class.
    public void add(Object obj) {
        this.addElement(obj);
    }

    public void addAll(List<? extends Object> objectList) {
        objectList.forEach(this::addElement);
    }

}
