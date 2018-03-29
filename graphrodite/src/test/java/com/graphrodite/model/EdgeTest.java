package com.graphrodite.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class EdgeTest {

    @Test
    public void givenExistingVertices_whenContainsVertices_thenReturnTrue() {
        // GIVEN
        Edge<Integer> edge = new Edge<>(new Vertex<>(1), new Vertex<>(2));
        // WHEN
        boolean result = edge.containsVertices(1, 2);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenNotExistingVertices_whenContainsVertices_thenReturnFalse() {
        // GIVEN
        Edge<Integer> edge = new Edge<>(new Vertex<>(1), new Vertex<>(2));
        // WHEN
        boolean result = edge.containsVertices(3, 3);
        // THEN
        assertThat(result).isFalse();
    }
}