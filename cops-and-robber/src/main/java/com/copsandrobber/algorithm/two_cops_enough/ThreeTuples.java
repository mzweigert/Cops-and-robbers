package com.copsandrobber.algorithm.two_cops_enough;

import com.graphrodite.model.Pair;

import java.io.Serializable;
import java.util.Objects;

class ThreeTuples<E> implements Serializable {

    private final Pair<E, E> pair;
    private final E third;

    public ThreeTuples(E first, E second, E third) {
        super();
        this.pair = new Pair<>(first, second);
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThreeTuples)) return false;
        ThreeTuples<?> that = (ThreeTuples<?>) o;
        return Objects.equals(pair, that.pair) &&
                Objects.equals(third, that.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pair, third);
    }

    @Override
    public String toString() {
        return "(" + pair.getFirst() + ", " + pair.getSecond() + ", " + third + ")";


    }

    public E getFirst() {
        return pair.getFirst();
    }

    public E getSecond() {
        return pair.getSecond();
    }

    public E getThird() {
        return third;
    }
}
