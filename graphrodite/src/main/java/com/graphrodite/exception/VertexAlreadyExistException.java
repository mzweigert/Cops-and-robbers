package com.graphrodite.exception;

/**
 * Exception thrown when vertex exist in graph vertices set {@link com.graphrodite.model.Graph}.
 * thrown in :
 * {@link com.graphrodite.model.Graph#addVertex(Object)},
 * {@link com.graphrodite.model.Graph#addVertices(Object[])}.
 */
public class VertexAlreadyExistException extends Exception {

    public <E> VertexAlreadyExistException(E index) {
        super("Vertex with " + index + " index already exist in graph.");
    }
}
