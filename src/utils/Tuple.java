package utils;

public class Tuple<T1, T2, T3> {
    private final T1 first;
    private final T2 second;
    private final T3 third;
    public Tuple(T1 fst, T2 snd, T3 thrd) {
        this.first = fst;
        this.second = snd;
        this.third = thrd;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    public T3 getThird() {
        return third;
    }
}
