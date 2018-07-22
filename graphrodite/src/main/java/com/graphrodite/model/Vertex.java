package com.graphrodite.model;

import com.graphrodite.exception.NeighborAlreadyExistException;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing vertex in graph.
 *
 * @param <E> type of vertex.
 */
public class Vertex<E> {

    private E index;
    private List<Vertex<E>> neighbors;

    private Vertex(E index) {
        this(index, new ArrayList<>());
    }

    private Vertex(E index, List<Vertex<E>> neighbors) {
        this.index = index;
        this.neighbors = neighbors;
    }

    /**
     * Method create and return vertex with given index.
     *
     * @param index vertex id.
     * @return Vertex&lt;E&gt; vertex object of generic E type.
     */
    public static <E> Vertex<E> create(E index) {
        return new Vertex<>(index);
    }

    /**
     * Method create and return vertex with given index and neighbors.
     *
     * @param index vertex id.
     * @return Vertex&lt;E&gt; vertex object of generic E type.
     */
    static <E> Vertex<E> create(E index, List<Vertex<E>> neighbors) {
        return new Vertex<>(index, neighbors);
    }

    /**
     * Method create neighborhood with given as param vertex.
     *
     * @param neighbor new vertex neighbor.
     * @throws NeighborAlreadyExistException throw when neighbor exist in neighbors set.
     */
    public void createNeighborhoodWith(Vertex<E> neighbor) throws NeighborAlreadyExistException {
        if (neighbors.contains(neighbor)) {
            throw new NeighborAlreadyExistException(neighbor.getIndex(), index);
        } else if (neighbor.equals(this)) {
            throw new IllegalArgumentException("Cannot add same vertex to his neighbourhood");
        }
        this.addToNeighbors(neighbor);
        neighbor.addToNeighbors(this);
    }

    /**
     * Method remove neighborhood with given as param vertex.
     *
     * @param neighbor vertex which is in neighbors set.
     * @return true if neighborhood has been removed successfully, false otherwise.
     */
    public boolean removeFromNeighborhood(Vertex<E> neighbor) {
        return this.removeFromNeighbors(neighbor) && neighbor.removeFromNeighbors(this);
    }

    private boolean removeFromNeighbors(Vertex<E> neighbor) {
        return this.neighbors.remove(neighbor);
    }

    private void addToNeighbors(Vertex<E> neighbor) {
        this.neighbors.add(neighbor);
    }

    /**
     * Method return copy of neighbors collection given vertex.
     *
     * @return Collection&lt;Vertex&lt;E&gt;&gt; collection of open neighborhood.
     */
    public List<Vertex<E>> getOpenNeighborhood() {
        return new ArrayList<>(this.neighbors);
    }

    /**
     * Method return open copy of neighbors collection given vertex together with him.
     *
     * @return Collection&lt;Vertex&lt;E&gt;&gt; collection of closed neighborhood.
     */
    public Collection<Vertex<E>> getClosedNeighborhood() {
        Collection<Vertex<E>> collection = getOpenNeighborhood();
        collection.add(this);
        return collection;
    }

    /**
     * Method return index of vertex.
     *
     * @return E vertex index.
     */
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

}