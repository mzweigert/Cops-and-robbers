package com.graphrodite.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EdgeTest {

    @Test
    public void givenExistingVertices_whenContainsVertices_thenReturnTrue() {
        // GIVEN
        Edge<Integer> edge = new Edge<>(new Vertex<>(1), new Vertex<>(2));
        // WHEN
        boolean result = edge.containsVertices(1, 2);
        // THEN
        assertTrue(result);
    }

    @Test
    public void givenNotExistingVertices_whenContainsVertices_thenReturnTrue() {
        // GIVEN
        Edge<Integer> edge = new Edge<>(new Vertex<>(1), new Vertex<>(2));
        // WHEN
        boolean result = edge.containsVertices(3, 3);
        // THEN
        assertFalse(result);
    }
}