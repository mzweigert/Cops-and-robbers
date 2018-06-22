package com.graphrodite.internal.service;

import com.graphrodite.internal.wrapper.FindCycleDFSWrapper;
import com.graphrodite.model.Edge;
import com.graphrodite.model.Vertex;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FinderGraphService<E> extends GraphService<E> {

    public FinderGraphService(Set<Vertex<E>> vertices, Set<Edge<E>> edges) {
        super(vertices, edges);
    }

    public Optional<Vertex<E>> findVertex(E index) {
        if (containsVertex(index)) {
            return findVertex(v -> v.getIndex().equals(index));
        }
        return Optional.empty();
    }

    public Optional<Vertex<E>> findVertex(Predicate<? super Vertex<E>> predicate) {
        return vertices.stream()
                .filter(predicate)
                .findFirst();
    }

    public boolean containsVertex(E index) {
        return vertices.contains(Vertex.create(index));
    }

    public Optional<Edge<E>> findEdge(Predicate<? super Edge<E>> predicate) {
        return edges.stream()
                .filter(predicate)
                .findFirst();
    }

    public boolean containsEdge(E first, E second) {
        Edge<E> edge = Edge.create(Vertex.create(first), Vertex.create(second));
        return edges.contains(edge);
    }

    public Set<E> findDuplicatedIndexes(E... indexes) {
        List<E> indexesList = List.of(indexes);
        return indexesList.stream()
                .filter(i -> Collections.frequency(indexesList, i) > 1)
                .collect(Collectors.toSet());
    }

    public boolean containsCycle() {
        Optional<Vertex<E>> first = vertices.stream().findFirst();
        if(first.isPresent()){
            FindCycleDFSWrapper<E> findCycleDFSWrapper = new FindCycleDFSWrapper<>(first.get());
            return containsCycle(findCycleDFSWrapper, first.get());
        }
        return false;
    }

     boolean containsCycle(FindCycleDFSWrapper<E> wrapper, Vertex<E> current) {
        wrapper.addToVisited(current);
        for (Vertex<E> neighbor : current.getOpenNeighborhood()) {
            Optional<Vertex<E>> firstFromCycle = wrapper.getLastFromCycle();
            if (firstFromCycle.isPresent() && neighbor.equals(firstFromCycle.get())) {
                continue;
            }
            wrapper.addToCycle(current);
            if (neighbor.equals(wrapper.getStartVertex()) ||
                    (!wrapper.IsNotInVisited(neighbor) &&
                            containsCycle(wrapper, neighbor))) {
                return true;
            }
            wrapper.removeFromCycle();
        }
        return false;
    }
}
