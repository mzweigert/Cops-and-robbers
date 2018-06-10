package com.graphrodite.model;


import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class representing edge in graph.
 * @param <E> type of vertices in edge.
 */
public class Edge<E> implements Serializable {

    private Vertex<E> first;
    private Vertex<E> second;

    private Edge(Vertex<E> first, Vertex<E> second) {
        this.first = first;
        this.second = second;
    }

    public static <E> Edge<E> create(Vertex<E> firstVertex, Vertex<E> secondVertex) {
        return new Edge<>(firstVertex, secondVertex);
    }

    /**
     * Method check if edges are equals.
     * Note, that method doesn't takes into account the order of indexes.
     * I. e. edge with fields values first=1 and second=2 is the same as edge with first=2 and second=1.
     * @param edge edge to compare.
     * @return true if given edge is equal, false otherwise.
     */
    @Override
    public boolean equals(Object edge) {
        if (this == edge) return true;
        if (!(edge instanceof Edge)) return false;

        Edge<?> toCompare = (Edge<?>) edge;

        return (first.equals(toCompare.first) && second.equals(toCompare.second)) ||
                (first.equals(toCompare.second) && second.equals(toCompare.first));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond()) * Objects.hash(getSecond(), getFirst());
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", first, second);
    }

    /**
     * Method check if edge is consists of two vertices with given indexes.
     * Method use {@link Edge#equals(Object)} method,
     * and method return true, even if edge has reversed order of given indexes.
     * @param first first vertex index.
     * @param second second vertex index.
     * @return true if edge is consists of given indexes, false otherwise.
     */
    public boolean containsVertices(E first, E second) {
        return (this.first.getIndex().equals(first) && this.second.getIndex().equals(second)) ||
                (this.first.getIndex().equals(second) && this.second.getIndex().equals(first));
    }

    /**
     * Method return first edge vertex.
     * @return Vertex&lt;E&gt; first vertex
     */
    public Vertex<E> getFirst() {
        return first;
    }

    /**
     * Method return second edge vertex.
     * @return Vertex&lt;E&gt; second vertex
     */
    public Vertex<E> getSecond() {
        return second;
    }

    /**
     * Method clone edge.
     * @return Edge&lt;E&gt; cloned edge.
     */
    public Edge<E> clone() {
        return (Edge<E>) SerializationUtils.clone(this);
    }
}
