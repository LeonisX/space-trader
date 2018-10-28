package spacetrader.controls;

import javax.swing.*;

public class MyComboBoxModel<T> extends DefaultComboBoxModel<T> {

    // TODO inline methods, use super-class.
    public void add(T obj) {
        this.addElement(obj);
    }

    public void addAll(T... objects) {
        for (T object : objects) {
            add(object);
        }
    }

    public T get(int index) {
        return getElementAt(index);
    }

    public void remove(int index) {
        removeElementAt(index);
    }

    public void insert(int index, T object) {
        insertElementAt(object, index);
    }


}
