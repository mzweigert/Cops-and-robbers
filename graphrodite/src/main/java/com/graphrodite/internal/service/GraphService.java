package com.graphrodite.internal.service;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.NeighborAlreadyExistException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicates;
import com.graphrodite.model.Edge;
import com.graphrodite.model.Vertex;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GraphService<E> implements Serializable {

    private List<Edge<E>> edges;
    private List<Vertex<E>> vertices;
    private List<E> duplicates;

    public GraphService(List<Vertex<E>> vertices, List<Edge<E>> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public boolean containsVertex(E index) {
        return findVertex(v -> v.getIndex().equals(index)).isPresent();
    }

    public boolean containsEdge(E first, E second) {
        return findEdge(e -> e.containsVertices(first, second)).isPresent();
    }

    public Vertex<E> findOrCreateVertex(E index) {
        Optional<Vertex<E>> optional = findVertex(v -> v.getIndex().equals(index));
        return optional.orElseGet(() -> createVertex(index));
    }

    public List<Vertex<E>> addVertices(E... indexes) throws VertexAlreadyExistException {
        List<Vertex<E>> vertices = new ArrayList<>();
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

    private Vertex<E> createVertex(E index) {
        Vertex<E> vertex = new Vertex<>(index);
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
        Vertex<E> firstVertex = findOrCreateVertex(first);
        Vertex<E> secondVertex = findOrCreateVertex(second);
        Edge<E> edge = new Edge<>(firstVertex, secondVertex);
        if (edges.contains(edge)) {
            throw new EdgeAlreadyExistException(firstVertex.getIndex(), secondVertex.getIndex());
        }
        edges.add(edge);
        try {
            if (!firstVertex.equals(secondVertex)) {
                firstVertex.createNeighbourhood(secondVertex);
            }
        } catch (NeighborAlreadyExistException e) {
            e.printStackTrace();
        }
        return edge;
    }


    public List<Edge<E>> addEdgesToVertex(E first, E... neighbors) throws EdgeAlreadyExistException {
        List<Edge<E>> edges = new ArrayList<>();
        Vertex<E> firstVertex = findOrCreateVertex(first);
        for (E second : neighbors) {
            Vertex<E> secondVertex = findOrCreateVertex(second);
            Edge<E> edge = addEdge(firstVertex.getIndex(), secondVertex.getIndex());
            edges.add(edge);
        }
        return edges;
    }

    public List<Vertex<E>> addPath(E... indexes) throws EdgeAlreadyExistException, PathContainsDuplicates {
        List<E> copyIndexes = Arrays.stream(indexes).collect(Collectors.toList());
        HashSet<E> withoutDuplicates = new HashSet<>(copyIndexes);
        boolean containsDuplicates = withoutDuplicates.size() != indexes.length;
        if(containsDuplicates) {
            Set<E> duplicates = copyIndexes.stream()
                    .filter(i -> Collections.frequency(copyIndexes, i) > 1)
                    .collect(Collectors.toSet());
            throw new PathContainsDuplicates(duplicates);
        }
        return addSequentiallyEdges(indexes);
    }

    private List<Vertex<E>> addSequentiallyEdges(E... indexes) throws EdgeAlreadyExistException {
        Vertex<E> previousVertex = null;
        List<Vertex<E>> vertices = new ArrayList<>();
        for (E index : indexes) {
            if (previousVertex != null) {
                addEdge(previousVertex.getIndex(), index);
            }
            previousVertex = new Vertex<>(index);
            vertices.add(previousVertex);
        }
        return vertices;
    }
}
