package utils;

import java.util.HashMap;

public interface MyILatchTable {
    void put(int key, int value);
    int get(int key);
    boolean exists(int key);
    int getFreeAddress();
    void update(int key, int value);
    void setFreeAddress(int freeAddress);
    HashMap<Integer, Integer> getLatchTable();
    void setLatchTable(HashMap<Integer, Integer> newLatchTable);
}
