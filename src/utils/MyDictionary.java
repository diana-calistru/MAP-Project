package utils;

import java.util.*;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {

    private Map<K, V> map;
    private List<K> keySet;

    public MyDictionary() {
        map = new HashMap<>();
        keySet = new ArrayList<>();
    }
    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return map.get(key) != null;
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
        keySet.add(key);
    }

    @Override
    public void update(K key, V value) {
        map.put(key, value);
    }

    @Override
    public List<K> getKeySet() {
        return keySet;
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public String toString() {
        return map.toString();
    }
    @Override
    public Map<K, V> getContent() {return map;}

    public MyIDictionary<K, V> deepcopy() {
        MyDictionary<K, V> copy = new MyDictionary<>();
        map.forEach((K, V) -> copy.put(K, V));
        return copy;
    }
}
