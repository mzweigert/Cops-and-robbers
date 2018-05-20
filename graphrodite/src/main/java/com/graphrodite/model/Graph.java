package com.graphrodite.model;


import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicates;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.internal.service.GraphService;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.*;

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
    public final List<Vertex<E>> addPath(E... indexes) throws EdgeAlreadyExistException, PathContainsDuplicates {
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

    public static Graph<Integer> petersen(){
        Graph<Integer> petersenGraph = Graph.newInstance();
        try {
            petersenGraph.addPath(0, 1, 2, 3, 4);
            petersenGraph.addPath(0, 4);
            petersenGraph.addEdgesToVertex(5, 0, 7, 8);
            petersenGraph.addEdgesToVertex(6, 1, 8, 9);
            petersenGraph.addEdgesToVertex(7, 2, 9);
            petersenGraph.addEdgesToVertex(8, 3);
            petersenGraph.addEdgesToVertex(9, 4);
        } catch (EdgeAlreadyExistException | PathContainsDuplicates e) {
            e.printStackTrace();
        }
        return petersenGraph;
    }

}
