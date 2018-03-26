package com.graphrodite.model;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.NeighborAlreadyExistException;

import java.util.Optional;
import java.util.function.Predicate;

class GraphUtils<E> {

    private Graph<E> graph;

    public GraphUtils(Graph<E> graph) {
        this.graph = graph;
    }

    boolean containsVertex(E index) {
        return findVertex(v -> v.index.equals(index)).isPresent();
    }

    Vertex<E> findOrCreateVertex(E index) {
        Optional<Vertex<E>> optional = findVertex(v -> v.index.equals(index));
        if (!optional.isPresent()) {
            return createVertex(index);
        }
        return optional.get();
    }

    Vertex<E> createVertex(E index) {
        Vertex<E> vertex = new Vertex<>(index);
        graph.vertices.add(vertex);
        return vertex;
    }

    Optional<Vertex<E>> findVertex(Predicate<? super Vertex> predicate) {
        return graph.vertices.stream()
                .filter(predicate)
                .findFirst();
    }


    void createEdge(Vertex<E> firstVertex, Vertex<E> secondVertex) throws EdgeAlreadyExistException, NeighborAlreadyExistException {
        Edge<E> edge = new Edge<>(firstVertex, secondVertex);
        if (graph.edges.contains(edge)) {
            throw new EdgeAlreadyExistException(firstVertex.index, secondVertex.index);
        }
        graph.edges.add(edge);
        createVerticesNeighborhood(firstVertex, secondVertex);
    }

    private void createVerticesNeighborhood(Vertex<E> firstVertex, Vertex<E> secondVertex) throws NeighborAlreadyExistException {
        firstVertex.addNeighbor(secondVertex);
        if (!firstVertex.equals(secondVertex)) {
            secondVertex.addNeighbor(firstVertex);
        }
    }
}
