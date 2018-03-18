package com.copsandrobber.exception;

public class EdgeAlreadyExistException extends Exception{

    public <E> EdgeAlreadyExistException(E first, E second) {
        super("Vertex " + first + " is already connected to " + second + " in graph.");
    }
}
