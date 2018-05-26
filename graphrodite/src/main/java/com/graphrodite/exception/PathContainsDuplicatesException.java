package com.graphrodite.exception;

import java.util.Set;

public class PathContainsDuplicatesException extends Throwable {

    public <E> PathContainsDuplicatesException(Set<E> duplicatedIndexes) {
        super("Path has duplicated values: " + duplicatedIndexes);
    }
}
