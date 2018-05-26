package com.graphrodite.model;


import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicatesException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.internal.service.GraphService;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Graph<E> implements Serializable {

    private Set<Edge<E>> edges;
    private Set<Vertex<E>> vertices;
    private GraphService<E> graphService;

    private Graph() {
        vertices = new LinkedHashSet<>();
        edges = new LinkedHashSet<>();
        graphService = new GraphService<>(vertices, edges);
    }

    public static <E> Graph<E> newInstance() {
        return new Graph<>();
    }

    public static Graph<Integer> petersen() {
        Graph<Integer> petersen = Graph.newInstance();
        try {
            petersen.addPath(0, 1, 2, 3, 4);
            petersen.addPath(0, 4);
            petersen.addEdgesToVertex(5, 0, 7, 8);
            petersen.addEdgesToVertex(6, 1, 8, 9);
            petersen.addEdgesToVertex(7, 2, 9);
            petersen.addEdgesToVertex(8, 3);
            petersen.addEdgesToVertex(9, 4);
        } catch (EdgeAlreadyExistException | PathContainsDuplicatesException e) {
            e.printStackTrace();
        }
        return petersen;
    }

    public static Graph<Integer> dodecahedron() {
        Graph<Integer> dodecahedron = Graph.newInstance();
        try {
            dodecahedron.addPath(0, 1, 2, 3, 4);
            dodecahedron.addPath(0, 4);

            dodecahedron.addPath(5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
            dodecahedron.addPath(5, 14);

            dodecahedron.addPath(15, 16, 17, 18, 19);
            dodecahedron.addPath(15, 19);

            dodecahedron.addEdge(0, 5);
            dodecahedron.addEdge(1, 7);
            dodecahedron.addEdge(2, 9);
            dodecahedron.addEdge(3, 11);
            dodecahedron.addEdge(4, 13);

            dodecahedron.addEdge(6, 16);
            dodecahedron.addEdge(8, 17);
            dodecahedron.addEdge(10, 18);
            dodecahedron.addEdge(12, 19);
            dodecahedron.addEdge(14, 15);

        } catch (EdgeAlreadyExistException | PathContainsDuplicatesException e) {
            e.printStackTrace();
        }
        return dodecahedron;
    }

    public Edge<E> addEdge(E first, E second) throws EdgeAlreadyExistException {
        return graphService.addEdge(first, second);
    }

    @SafeVarargs
    public final List<Edge<E>> addEdgesToVertex(E first, E... neighbors) throws EdgeAlreadyExistException {
        return graphService.addEdgesToVertex(first, neighbors);
    }

    public Vertex<E> addVertex(E index) throws VertexAlreadyExistException {
        return graphService.addVertex(index);
    }

    @SafeVarargs
    public final List<Vertex<E>> addVertices(E... indexes) throws VertexAlreadyExistException {
        return graphService.addVertices(indexes);
    }

    @SafeVarargs
    public final List<Vertex<E>> addPath(E... indexes) throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        return graphService.addPath(indexes);
    }

    public Optional<Edge<E>> findEdge(E first, E second) {
        if (containsEdge(first, second)) {
            return graphService.findEdge(e -> e.containsVertices(first, second));
        }
        return Optional.empty();
    }

    public boolean containsEdge(E first, E second) {
        return graphService.containsEdge(first, second);
    }

    public boolean containsVertex(E index) {
        return graphService.containsVertex(index);
    }

    public Optional<Vertex<E>> findVertex(E index) {
        return graphService.findVertex(v -> v.getIndex().equals(index));
    }

    public Set<Vertex<E>> getVertices() {
        return new LinkedHashSet<>(vertices);
    }

    public Set<Edge<E>> getEdges() {
        return new LinkedHashSet<>(edges);
    }

    public Graph<E> clone() {
        return (Graph<E>) SerializationUtils.clone(this);
    }
}
