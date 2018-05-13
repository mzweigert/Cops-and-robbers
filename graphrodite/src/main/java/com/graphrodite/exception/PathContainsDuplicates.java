package com.graphrodite.exception;

import java.util.Set;

public class PathContainsDuplicates extends Throwable {

    public <E> PathContainsDuplicates(Set<E> duplicatedIndexes) {
        super("Path has duplicated values: " + duplicatedIndexes);
    }
}
