package com.graphrodite.model;


import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.Objects;

public class Edge<E> implements Serializable {

    private Vertex<E> first;
    private Vertex<E> second;

    private Edge(Vertex<E> first, Vertex<E> second) {
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
        return Objects.hash(getFirst(), getSecond()) * Objects.hash(getSecond(), getFirst());
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

    public static <E> Edge<E> create(Vertex<E> firstVertex, Vertex<E> secondVertex) {
        return new Edge<>(firstVertex, secondVertex);
    }
}
