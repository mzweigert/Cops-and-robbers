package com.graphrodite.model;


import java.io.Serializable;
import java.util.Objects;

/**
 * Graph representing vertex type in graph product.
 *
 * @param <A> type of first graph product factor
 * @param <B> type of second graph product factor
 */
public class Pair<A, B> implements Serializable {
    private A first;
    private B second;

    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(getFirst(), pair.getFirst()) &&
                Objects.equals(getSecond(), pair.getSecond());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond());
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    /**
     * @return A first index of graph product vertex.
     */
    public A getFirst() {
        return first;
    }

    /**
     * @return B second index of graph product vertex.
     */
    public B getSecond() {
        return second;
    }

    public void set(A copNeighbour, B robberNeighbour) {
        this.first = copNeighbour;
        this.second = robberNeighbour;
    }
}