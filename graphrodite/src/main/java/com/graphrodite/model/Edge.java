package com.graphrodite.model;


import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;

public class Edge<E> implements Serializable {

    private Vertex<E> first;
    private Vertex<E> second;

    public Edge(Vertex<E> first, Vertex<E> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge<?> edge = (Edge<?>) o;

        return (first.equals(edge.first) && second.equals(edge.second)) ||
                (first.equals(edge.second) && second.equals(edge.first));
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", first, second);
    }

    public boolean containsVertices(E first, E second) {
        return (this.first.getIndex().equals(first) && this.second.getIndex().equals(second)) ||
                (this.first.getIndex().equals(second) && this.second.getIndex().equals(first));
    }

    public Vertex<E> getFirst() {
        return first;
    }

    public Vertex<E> getSecond() {
        return second;
    }

    public Edge<E> clone() {
        return (Edge<E>) SerializationUtils.clone(this);
    }
}
