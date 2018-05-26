package com.graphrodite.model;

import com.graphrodite.exception.NeighborAlreadyExistException;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Vertex<E> implements Serializable {

    private E index;
    private List<Vertex<E>> neighbors;

    public Vertex(E index) {
        this.index = index;
        this.neighbors = new ArrayList<>();
    }

    public static <E> Vertex<E> create(E index) {
        return new Vertex<>(index);
    }

    public void createNeighbourhood(Vertex<E> neighbor) throws NeighborAlreadyExistException {
        if (neighbors.contains(neighbor)) {
            throw new NeighborAlreadyExistException(neighbor.getIndex(), index);
        } else if (neighbor.equals(this)) {
            throw new IllegalArgumentException("Cannot add same vertex to his neighbourhood");
        }
        this.addToNeighbors(neighbor);
        neighbor.addToNeighbors(this);
    }

    public boolean removeNeighbourhood(Vertex<E> vertex) {
        return this.removeFromNeighbors(vertex) && vertex.removeFromNeighbors(this);
    }

    private boolean removeFromNeighbors(Vertex<E> neighbor) {
        return this.neighbors.remove(neighbor);
    }

    private void addToNeighbors(Vertex<E> neighbor) {
        this.neighbors.add(neighbor);
    }

    public Collection<Vertex<E>> getOpenNeighbourhood() {
        return new ArrayList<>(this.neighbors);
    }

    public Collection<Vertex<E>> getClosedNeighbourhood() {
        Collection<Vertex<E>> collection = getOpenNeighbourhood();
        collection.add(this);
        return collection;
    }

    public E getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex<?> vertex = (Vertex<?>) o;

        return index.equals(vertex.index);

    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }

    @Override
    public String toString() {
        return index.toString();
    }

    public Vertex<E> clone() {
        return (Vertex<E>) SerializationUtils.clone(this);
    }
}