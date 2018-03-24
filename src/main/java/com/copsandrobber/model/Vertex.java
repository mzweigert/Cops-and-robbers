package com.copsandrobber.model;

import com.copsandrobber.exception.NeighborAlreadyExistException;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Vertex<E> implements Serializable {

    E index;
    List<Vertex<E>> neighbors;

    Vertex(E index) {
        this.index = index;
        this.neighbors = new ArrayList<>();
    }

    void addNeighbor(Vertex<E> neighbor) throws NeighborAlreadyExistException {
        if(neighbors.contains(neighbor)){
            throw new NeighborAlreadyExistException(neighbor.index, index);
        }
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


    public boolean removeFromNeighbourhood(Vertex<E> vertex) {
        return neighbors.remove(vertex);
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

    public Vertex<E> clone() throws CloneNotSupportedException {
        return (Vertex<E>) SerializationUtils.clone(this);
    }
}