package com.graphrodite.exception;

import java.util.Set;

/**
 * Exception thrown when path contains duplicates in given set vertices.
 * thrown in {@link com.graphrodite.model.Graph#addPath(Object[])}
 */
public class IndexesContainsDuplicatesException extends Exception {

    public <E> IndexesContainsDuplicatesException(Set<E> duplicatedIndexes) {
        super("Given indexes contains duplicates: " + duplicatedIndexes);
    }
}
