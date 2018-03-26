package com.graphrodite.model;


import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.NeighborAlreadyExistException;
import com.graphrodite.exception.VertexAlreadyExistException;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Graph<E> implements Serializable {

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

    public Graph<E> addEdge(E first, E second) throws NeighborAlreadyExistException, EdgeAlreadyExistException {
        Vertex<E> firstVertex = graphUtils.findOrCreateVertex(first);
        Vertex<E> secondVertex = graphUtils.findOrCreateVertex(second);
        graphUtils.createEdge(firstVertex, secondVertex);
        return this;
    }

    public Graph<E>  addEdge(E first, E... neighbors) throws NeighborAlreadyExistException, EdgeAlreadyExistException {
        Vertex<E> firstVertex = graphUtils.findOrCreateVertex(first);
        for(E second : neighbors) {
            Vertex<E> secondVertex = graphUtils.findOrCreateVertex(second);
            graphUtils.createEdge(firstVertex, secondVertex);
        }
        return this;
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

    public List<Vertex<E>> getVertices() {
        return new ArrayList<>(vertices);
    }

    public Collection<Edge<E>> getEdges() {
        return new ArrayList<>(edges);
    }

    public Graph<E> clone() throws CloneNotSupportedException {
        return (Graph<E>)SerializationUtils.clone(this);
    }

}
