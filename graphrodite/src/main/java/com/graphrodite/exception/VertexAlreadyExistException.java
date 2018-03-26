package com.graphrodite.exception;

public class VertexAlreadyExistException extends Exception {

    public <E> VertexAlreadyExistException(E index) {
        super("Vertex with " + index + " index already exist in graph.");
    }
}
