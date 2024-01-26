package utils;

import model.values.Value;

import java.util.List;
import java.util.ArrayList;
public class MyList<T> implements MyIList<T> {

    private List<T> list;

    public MyList() {
        list = new ArrayList<>();
    }
    @Override
    public void add(T e) {
        list.add(e);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
