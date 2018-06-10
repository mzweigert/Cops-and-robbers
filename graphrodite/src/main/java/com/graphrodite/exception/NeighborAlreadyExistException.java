package com.graphrodite.exception;

import com.graphrodite.model.Vertex;

/**
 * Exception thrown when edges exist in vertex {@link com.graphrodite.model.Vertex}.
 * thrown in {@link com.graphrodite.model.Vertex#addToNeighbors(Vertex) }.
 */
public class NeighborAlreadyExistException extends Exception {

    public <E> NeighborAlreadyExistException(E neighborIndex, E vertexIndex) {
        super("Neighbor : " + neighborIndex + " already exist in list of neighbors on vertex : " + vertexIndex);
    }

}
