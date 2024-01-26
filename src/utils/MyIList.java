package utils;

import model.values.Value;

import java.util.List;

public interface MyIList<T> {
    void add(T e);
    void clear();

    List<T> getList();
}
