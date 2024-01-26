package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Collections;

public class MyStack<T> implements MyIStack<T> {

    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }
    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<T> reverse() {
        List<T> items;
        items = Arrays.asList((T[])stack.toArray());

        Collections.reverse(items);
        return items;
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
