package com.copsandrobber.model;


import com.copsandrobber.exception.EdgeAlreadyExistException;
import com.copsandrobber.exception.NeighborAlreadyExistException;
import com.copsandrobber.exception.VertexAlreadyExistException;

import java.util.*;

public class Graph<E> {

    List<Edge<E>> edges;
    List<Vertex<E>> vertices;
    private GraphUtils<E> graphUtils;

    private Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        graphUtils = new GraphUtils<>(this);
    }

    public static <E> Graph<E> newInstance() {
        return new Graph<>();
    }

    public void addEdge(E first, E second) throws NeighborAlreadyExistException, EdgeAlreadyExistException {
        Vertex<E> firstVertex = graphUtils.findOrCreateVertex(first);
        Vertex<E> secondVertex = graphUtils.findOrCreateVertex(second);
        graphUtils.createEdge(firstVertex, secondVertex);
    }

    public void addEdge(E first, E... neighbors) throws NeighborAlreadyExistException, EdgeAlreadyExistException {
        Vertex<E> firstVertex = graphUtils.findOrCreateVertex(first);
        for(E second : neighbors) {
            Vertex<E> secondVertex = graphUtils.findOrCreateVertex(second);
            graphUtils.createEdge(firstVertex, secondVertex);
        }
    }
    public Vertex<E> addVertex(E index) throws VertexAlreadyExistException {
        if(graphUtils.containsVertex(index)){
            throw new VertexAlreadyExistException(index);
        }
        return graphUtils.createVertex(index);
    }

    public List<Vertex<E>> addVertices(E... indexes) throws VertexAlreadyExistException {
        List<Vertex<E>> vertices = new ArrayList<>();
        for(E index : indexes){
            Vertex<E> vertex = addVertex(index);
            vertices.add(vertex);
        }
        return vertices;
    }

    public Optional<Vertex<E>> getVertex(E index) {
        return vertices.stream()
                .filter(v -> v.index.equals(index))
                .findFirst();
    }

    public Collection<Vertex<E>> getVertices() {
        return Collections.unmodifiableCollection(vertices) ;
    }

    public Collection<Edge<E>> getEdges() {
        return Collections.unmodifiableCollection(edges);
    }


}
