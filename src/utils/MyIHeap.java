package utils;

import model.values.Value;

import java.util.List;
import java.util.Map;

public interface MyIHeap<V> {
    int allocate(V value); // allocates a new memory location, stores the given value and returns the address
    void deallocate(int address); // deallocates the memory at the given address
    void update(int address, V newValue); // updates the value at the given address with the new value
    V lookup(int address); // retrieves the value from the given address
    boolean isDefined(int address); //checks if the address is allocated in the heap
    int getFreeAddress(); // returns the next available memory address
    void updateFreeAddress(int newAddress); // updates the free address accordingly
    List<Integer> getAddressList(); // returns the content of the heap
    void setContent(Map<Integer, V> content);

    Map<Integer, V> getContent();
}
