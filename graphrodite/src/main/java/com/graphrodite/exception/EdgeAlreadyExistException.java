package com.graphrodite.exception;

/**
 * Exception thrown when edge exist in graph edges set {@link com.graphrodite.model.Graph}.
 * thrown in:
 * {@link com.graphrodite.model.Graph#addEdge(Object, Object)},
 * {@link com.graphrodite.model.Graph#addEdgesToVertex(Object, Object[])},
 * {@link com.graphrodite.model.Graph#addPath(Object[])}.
 */
public class EdgeAlreadyExistException extends Exception {

    public <E> EdgeAlreadyExistException(E first, E second) {
        super("Vertex " + first + " is already connected to " + second + " in graph.");
    }
}
