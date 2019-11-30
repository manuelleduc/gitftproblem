package fr.mleduc.giftproblem.core.utils;

public class Pair<T, T1> {
    private final T key;
    private final T1 value;

    public Pair(T a, T1 b) {
        this.key = a;
        this.value = b;
    }

    public T getKey() {
        return key;
    }

    public T1 getValue() {
        return value;
    }
}
