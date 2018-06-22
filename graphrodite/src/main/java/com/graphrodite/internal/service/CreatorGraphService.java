package com.graphrodite.internal.service;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.IndexesContainsDuplicatesException;
import com.graphrodite.exception.NeighborAlreadyExistException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.model.Edge;
import com.graphrodite.model.Vertex;

import java.util.LinkedHashSet;
import java.util.Set;

public class CreatorGraphService<E> extends GraphService<E> {

    private FinderGraphService<E> finderGraphService;

    public CreatorGraphService(Set<Vertex<E>> vertices, Set<Edge<E>> edges) {
        super(vertices, edges);
        finderGraphService = new FinderGraphService<>(vertices, edges);
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
        if (finderGraphService.containsVertex(index)) {
            throw new VertexAlreadyExistException(index);
        }
        return createVertex(index);
    }


    public Edge<E> addEdge(E first, E second) throws EdgeAlreadyExistException {
        if (finderGraphService.containsEdge(first, second)) {
            throw new EdgeAlreadyExistException(first, second);
        }
        Vertex<E> firstVertex = finderGraphService.findVertex(first)
                .orElse(createVertex(first));
        Vertex<E> secondVertex = finderGraphService.findVertex(second)
                .orElse(createVertex(second));
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
        Vertex<E> firstVertex = finderGraphService.findVertex(first)
                .orElse(createVertex(first));
        for (E second : neighbors) {
            Vertex<E> secondVertex = finderGraphService.findVertex(second)
                    .orElse(createVertex(second));
            Edge<E> edge = addEdge(firstVertex.getIndex(), secondVertex.getIndex());
            edges.add(edge);
        }
        return edges;
    }

    private Vertex<E> createVertex(E index) {
        Vertex<E> vertex = Vertex.create(index);
        vertices.add(vertex);
        return vertex;
    }

    public Set<Vertex<E>> addCycle(E... indexes) throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        if (indexes.length < 3) {
            throw new IllegalArgumentException("Cannot create cycle from less than 3 indexes.");
        }
        Set<Vertex<E>> vertices = addSequentiallyEdges(indexes);
        addEdge(indexes[0], indexes[indexes.length - 1]);
        return vertices;
    }

    public Set<Vertex<E>> addPath(E... indexes) throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        if (indexes.length < 2) {
            throw new IllegalArgumentException("Cannot create path from less than 2 indexes.");
        }
        return addSequentiallyEdges(indexes);
    }

    private Set<Vertex<E>> addSequentiallyEdges(E... indexes) throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        Set<E> duplicatedIndexes = finderGraphService.findDuplicatedIndexes(indexes);
        if (!duplicatedIndexes.isEmpty()) {
            throw new IndexesContainsDuplicatesException(duplicatedIndexes);
        }
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
}
