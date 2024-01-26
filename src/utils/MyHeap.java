package utils;

import model.values.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHeap<V> implements MyIHeap<V> {
    private Map<Integer, V> heap;
    private int freeAddress;
    private List<Integer> addressList;

    public MyHeap() {
        this.heap = new HashMap<>();
        this.freeAddress = 1;
        this.addressList = new ArrayList<>();
    }
    @Override
    public int allocate(V value) {
        heap.put(freeAddress, value);
        freeAddress++;
        return freeAddress - 1;
    }

    @Override
    public void deallocate(int address) {
        heap.remove(address);
    }

    @Override
    public void update(int address, V newValue) {
        heap.put(address, newValue);
    }

    @Override
    public V lookup(int address) {
        return heap.get(address);
    }

    @Override
    public boolean isDefined(int address) {
        return heap.containsKey(address);
    }

    @Override
    public int getFreeAddress() {
        return freeAddress;
    }

    @Override
    public void updateFreeAddress(int newAddress) {
        this.freeAddress = newAddress;
    }

    @Override
    public List<Integer> getAddressList() {
        return addressList;
    }

    @Override
    public void setContent(Map<Integer, V> content) {
        this.heap = content;
    }

    @Override
    public Map<Integer, V> getContent() {
        return heap;
    }


}
