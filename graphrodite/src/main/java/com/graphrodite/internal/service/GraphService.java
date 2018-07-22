package com.graphrodite.internal.service;

import com.graphrodite.model.Edge;
import com.graphrodite.model.Vertex;

import java.io.Serializable;
import java.util.Set;

public abstract class GraphService<E> {

    protected final Set<Vertex<E>> vertices;
    protected final Set<Edge<E>> edges;

    GraphService(Set<Vertex<E>> vertices, Set<Edge<E>> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }
}
