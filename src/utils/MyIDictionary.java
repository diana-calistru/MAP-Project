package utils;

import java.util.List;
import java.util.Map;

public interface MyIDictionary<K, V> {
    V lookup(K key);
    boolean isDefined(K key);
    void put(K key, V value);
    void update(K key, V value);

    Map<K, V> getContent();
    List<K> getKeySet();

    void remove(K key);

    MyIDictionary<K, V> deepcopy();
}
