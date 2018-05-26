package com.graphrodite.exception;


public class NeighborAlreadyExistException extends Exception {

    public <E> NeighborAlreadyExistException(E neighborIndex, E vertexIndex) {
        super("Neighbor : " + neighborIndex + " already exist in list of neighbors on vertex : " + vertexIndex);
    }

}
