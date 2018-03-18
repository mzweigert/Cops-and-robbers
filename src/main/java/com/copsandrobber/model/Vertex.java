package com.copsandrobber.model;

import com.copsandrobber.exception.NeighborAlreadyExistException;

import java.util.ArrayList;
import java.util.List;

class Vertex<E> {

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

    List<Vertex<E>> getNeighbors() {
        return this.neighbors;
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
}