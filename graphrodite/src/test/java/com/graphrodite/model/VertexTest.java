package com.graphrodite.model;

import com.graphrodite.exception.NeighborAlreadyExistException;
import org.junit.Test;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class VertexTest {

    @Test
    public void givenDifferentVertices_whenCreateNeighbourhood_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> vertexNeighbor = Vertex.create(2);
        // THEN
        vertex.createNeighbourhood(vertexNeighbor);
        // WHEN
        assertThat(vertex.getOpenNeighbourhood().size()).isEqualTo(1);
        assertThat(vertex.getOpenNeighbourhood().contains(vertexNeighbor)).isTrue();
        assertThat(vertexNeighbor.getOpenNeighbourhood().size()).isEqualTo(1);
        assertThat(vertexNeighbor.getOpenNeighbourhood().contains(vertex)).isTrue();
    }

    @Test(expected = NeighborAlreadyExistException.class)
    public void givenDifferentVertices_whenInvokeCreateNeighbourhoodTwice_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> vertexNeighbor = Vertex.create(2);
        // THEN
        vertex.createNeighbourhood(vertexNeighbor);
        vertexNeighbor.createNeighbourhood(vertex);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenSameVertices_whenCreateNeighbourhood_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        // THEN
        vertex.createNeighbourhood(vertex);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenSameVerticesWithSameIndex_whenCreateNeighbourhood_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> vertexNeighbor = Vertex.create(1);

        // THEN
        vertex.createNeighbourhood(vertexNeighbor);
    }

    @Test
    public void givenExistingNeighbor_whenGetOpenNeighbourhood_thenSuccessGetOpenNeighbourhood() throws NeighborAlreadyExistException {

        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> vertexNeighbor = Vertex.create(2);
        vertex.createNeighbourhood(vertexNeighbor);
        // THEN
        Collection<Vertex<Integer>> neighbourhood = vertex.getOpenNeighbourhood();
        // WHEN
        assertThat(neighbourhood.size()).isEqualTo(1);
        assertThat(neighbourhood.contains(vertexNeighbor)).isTrue();
    }

    @Test
    public void givenExistingNeighbor_whenGetClosedNeighbourhood_thenSuccessGetClosedNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> vertexNeighbor = Vertex.create(2);
        vertex.createNeighbourhood(vertexNeighbor);
        // THEN
        Collection<Vertex<Integer>> neighbourhood = vertex.getClosedNeighbourhood();
        // WHEN
        assertThat(neighbourhood.size()).isEqualTo(2);
        assertThat(neighbourhood.contains(vertex)).isTrue();
        assertThat(neighbourhood.contains(vertexNeighbor)).isTrue();
    }

    @Test
    public void givenExistingNeighbor_whenRemoveNeighbourhood_thenReturnTrueAndSuccessRemove() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> vertexNeighbor = Vertex.create(2);
        vertex.createNeighbourhood(vertexNeighbor);
        // THEN
        boolean result = vertex.removeNeighbourhood(vertexNeighbor);
        // WHEN
        assertThat(result).isTrue();
        assertThat(vertex.getOpenNeighbourhood().contains(vertexNeighbor)).isFalse();
        assertThat(vertexNeighbor.getOpenNeighbourhood().contains(vertex)).isFalse();
    }

    @Test
    public void givenNotExistingNeighbor_whenRemoveNeighbourhood_thenReturnFalseAndSuccessRemove() {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> notVertexNeighbor = Vertex.create(2);
        // THEN
        boolean result = vertex.removeNeighbourhood(notVertexNeighbor);
        // WHEN
        assertThat(result).isFalse();
        assertThat(vertex.getOpenNeighbourhood().contains(notVertexNeighbor)).isFalse();
        assertThat(notVertexNeighbor.getOpenNeighbourhood().contains(vertex)).isFalse();
    }

    @Test
    public void givenIndex_whenCreate_thenSuccessCreateAndReturnVertex() {
        // GIVEN
        Integer index = 1;
        // WHEN
        Vertex<Integer> vertex = Vertex.create(index);
        // THEN
        assertThat(vertex).isNotNull();
        assertThat(vertex.getIndex()).isEqualTo(index);
    }
}