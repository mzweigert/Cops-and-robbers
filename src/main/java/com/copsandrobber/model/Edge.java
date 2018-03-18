package com.copsandrobber.model;

class Edge<E> {

    Vertex<E> first;
    Vertex<E> second;

    public Edge(Vertex<E> first, Vertex<E> second) {
        this.first = first;
        this.second = second;
    }

    public Edge(E first, E second) {
        this.first = new Vertex<>(first);
        this.second = new Vertex<>(second);
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

}
