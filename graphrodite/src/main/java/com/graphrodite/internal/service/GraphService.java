package com.graphrodite.internal.service;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.IndexesContainsDuplicatesException;
import com.graphrodite.exception.NeighborAlreadyExistException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.internal.wrapper.FindCycleDFSWrapper;
import com.graphrodite.model.Edge;
import com.graphrodite.model.Vertex;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GraphService<E> implements Serializable {

    private Set<Edge<E>> edges;
    private Set<Vertex<E>> vertices;

    public GraphService(Set<Vertex<E>> vertices, Set<Edge<E>> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public Vertex<E> findOrCreateVertex(E index) {
        if (containsVertex(index)) {
            return findVertex(v -> v.getIndex().equals(index)).get();
        }
        return createVertex(index);
    }

    public Set<Vertex<E>> addVertices(E... indexes) throws VertexAlreadyExistException {
        Set<Vertex<E>> vertices = new LinkedHashSet<>();
        for (E index : indexes) {
            Vertex<E> vertex = addVertex(index);
            vertices.add(vertex);
        }
        return vertices;
    }

    public Vertex<E> addVertex(E index) throws VertexAlreadyExistException {
        if (containsVertex(index)) {
            throw new VertexAlreadyExistException(index);
        }
        return createVertex(index);
    }

    public boolean containsVertex(E index) {
        return vertices.contains(Vertex.create(index));
    }


    public boolean containsEdge(E first, E second) {
        Edge<E> edge = Edge.create(Vertex.create(first), Vertex.create(second));
        return edges.contains(edge);
    }

    private Vertex<E> createVertex(E index) {
        Vertex<E> vertex = Vertex.create(index);
        vertices.add(vertex);
        return vertex;
    }

    public Optional<Vertex<E>> findVertex(Predicate<? super Vertex<E>> predicate) {
        return vertices.stream()
                .filter(predicate)
                .findFirst();
    }

    public Optional<Edge<E>> findEdge(Predicate<? super Edge<E>> predicate) {
        return edges.stream()
                .filter(predicate)
                .findFirst();
    }

    public Edge<E> addEdge(E first, E second) throws EdgeAlreadyExistException {
        if (containsEdge(first, second)) {
            throw new EdgeAlreadyExistException(first, second);
        }
        Vertex<E> firstVertex = findOrCreateVertex(first);
        Vertex<E> secondVertex = findOrCreateVertex(second);
        Edge<E> edge = Edge.create(firstVertex, secondVertex);
        edges.add(edge);
        try {
            if (!firstVertex.equals(secondVertex)) {
                firstVertex.createNeighborhoodWith(secondVertex);
            }
        } catch (NeighborAlreadyExistException e) {
            e.printStackTrace();
        }
        return edge;
    }


    public Set<Edge<E>> addEdgesToVertex(E first, E... neighbors) throws EdgeAlreadyExistException {
        Set<Edge<E>> edges = new LinkedHashSet<>();
        Vertex<E> firstVertex = findOrCreateVertex(first);
        for (E second : neighbors) {
            Vertex<E> secondVertex = findOrCreateVertex(second);
            Edge<E> edge = addEdge(firstVertex.getIndex(), secondVertex.getIndex());
            edges.add(edge);
        }
        return edges;
    }


    public Set<Vertex<E>> addCycle(E... indexes) throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        if (indexes.length < 3) {
            throw new IllegalArgumentException("Cannot create cycle from less than 3 indexes.");
        }
        checkIfGivenIndexesContainsDuplicates(indexes);
        Set<Vertex<E>> vertices = addSequentiallyEdges(indexes);
        addEdge(indexes[0], indexes[indexes.length - 1]);
        return vertices;
    }

    public Set<Vertex<E>> addPath(E... indexes) throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        if (indexes.length < 2) {
            throw new IllegalArgumentException("Cannot create path from less than 2 indexes.");
        }
        checkIfGivenIndexesContainsDuplicates(indexes);
        return addSequentiallyEdges(indexes);
    }

    private void checkIfGivenIndexesContainsDuplicates(E... indexes) throws IndexesContainsDuplicatesException {
        Set<E> indexesList = Set.of(indexes);
        Set<E> duplicates = indexesList.stream()
                .filter(i -> Collections.frequency(indexesList, i) > 1)
                .collect(Collectors.toSet());
        if (duplicates.size() > 1) {
            throw new IndexesContainsDuplicatesException(duplicates);
        }
    }

    private Set<Vertex<E>> addSequentiallyEdges(E... indexes) throws EdgeAlreadyExistException {
        Vertex<E> previousVertex = null;
        Set<Vertex<E>> vertices = new LinkedHashSet<>();
        for (E index : indexes) {
            if (previousVertex != null) {
                addEdge(previousVertex.getIndex(), index);
            }
            previousVertex = Vertex.create(index);
            vertices.add(previousVertex);
        }
        return vertices;
    }

    public boolean containsCycle(FindCycleDFSWrapper<E> wrapper, Vertex<E> current) {
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
