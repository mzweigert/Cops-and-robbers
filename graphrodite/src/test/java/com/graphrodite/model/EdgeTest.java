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
        Edge<Integer> edge = Edge.create(Vertex.create(1), Vertex.create(2));
        // WHEN
        boolean result = edge.containsVertices(1, 2);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenNotExistingVertices_whenContainsVertices_thenReturnFalse() {
        // GIVEN
        Edge<Integer> edge = Edge.create(Vertex.create(1), Vertex.create(2));
        // WHEN
        boolean result = edge.containsVertices(3, 3);
        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenTwoVertices_whenCreate_thenSuccessCreateAndReturnEdge() {
        // GIVEN
        Vertex<Integer> firstVertex = Vertex.create(1);
        Vertex<Integer> secondVertex = Vertex.create(2);
        // WHEN
        Edge<Integer> edge = Edge.create(firstVertex, secondVertex);
        // THEN
        assertThat(edge).isNotNull();
        assertThat(edge.getFirst()).isEqualTo(firstVertex);
        assertThat(edge.getSecond()).isEqualTo(secondVertex);
    }
}