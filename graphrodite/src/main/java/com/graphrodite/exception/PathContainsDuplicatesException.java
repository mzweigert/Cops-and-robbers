package com.graphrodite.exception;

import java.util.Set;

/**
 * Exception thrown when path contains duplicates in given set vertices.
 * thrown in {@link com.graphrodite.model.Graph#addPath(Object[])}
 */
public class PathContainsDuplicatesException extends Exception {

    public <E> PathContainsDuplicatesException(Set<E> duplicatedIndexes) {
        super("Path has duplicated values: " + duplicatedIndexes);
    }
}
