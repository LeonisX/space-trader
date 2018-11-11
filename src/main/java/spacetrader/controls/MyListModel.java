package spacetrader.controls;

import javax.swing.*;
import java.util.List;

public class MyListModel<Object> extends DefaultListModel<Object> {

    public void addAll(List<? extends Object> objectList) {
        objectList.forEach(this::addElement);
    }

}
