package spacetrader.controls;

import javax.swing.*;

public class MyComboBoxModel<T> extends DefaultComboBoxModel<T> {

    public void addAll(T... objects) {
        for (T object : objects) {
            addElement(object);
        }
    }

}
