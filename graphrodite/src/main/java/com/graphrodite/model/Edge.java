package com.graphrodite.model;


public class Edge<E> {

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

        return first.equals(edge.first) && second.equals(edge.second);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

    public boolean containsVertices(E first, E second) {
        return (this.first.getIndex().equals(first) && this.second.getIndex().equals(second)) ||
                (this.first.getIndex().equals(second) && this.second.getIndex().equals(first));
    }
}
