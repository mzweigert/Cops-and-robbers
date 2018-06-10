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
        vertex.createNeighborhoodWith(vertexNeighbor);
        // WHEN
        assertThat(vertex.getOpenNeighborhood().size()).isEqualTo(1);
        assertThat(vertex.getOpenNeighborhood().contains(vertexNeighbor)).isTrue();
        assertThat(vertexNeighbor.getOpenNeighborhood().size()).isEqualTo(1);
        assertThat(vertexNeighbor.getOpenNeighborhood().contains(vertex)).isTrue();
    }

    @Test(expected = NeighborAlreadyExistException.class)
    public void givenDifferentVertices_whenInvokeCreateNeighbourhoodTwice_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> vertexNeighbor = Vertex.create(2);
        // THEN
        vertex.createNeighborhoodWith(vertexNeighbor);
        vertexNeighbor.createNeighborhoodWith(vertex);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenSameVertices_whenCreateNeighbourhood_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        // THEN
        vertex.createNeighborhoodWith(vertex);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenSameVerticesWithSameIndex_whenCreateNeighbourhood_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> vertexNeighbor = Vertex.create(1);

        // THEN
        vertex.createNeighborhoodWith(vertexNeighbor);
    }

    @Test
    public void givenExistingNeighbor_whenGetOpenNeighbourhood_thenSuccessGetOpenNeighbourhood() throws NeighborAlreadyExistException {

        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> vertexNeighbor = Vertex.create(2);
        vertex.createNeighborhoodWith(vertexNeighbor);
        // THEN
        Collection<Vertex<Integer>> neighbourhood = vertex.getOpenNeighborhood();
        // WHEN
        assertThat(neighbourhood.size()).isEqualTo(1);
        assertThat(neighbourhood.contains(vertexNeighbor)).isTrue();
    }

    @Test
    public void givenExistingNeighbor_whenGetClosedNeighbourhood_thenSuccessGetClosedNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> vertexNeighbor = Vertex.create(2);
        vertex.createNeighborhoodWith(vertexNeighbor);
        // THEN
        Collection<Vertex<Integer>> neighbourhood = vertex.getClosedNeighborhood();
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
        vertex.createNeighborhoodWith(vertexNeighbor);
        // THEN
        boolean result = vertex.removeFromNeighborhood(vertexNeighbor);
        // WHEN
        assertThat(result).isTrue();
        assertThat(vertex.getOpenNeighborhood().contains(vertexNeighbor)).isFalse();
        assertThat(vertexNeighbor.getOpenNeighborhood().contains(vertex)).isFalse();
    }

    @Test
    public void givenNotExistingNeighbor_whenRemoveNeighbourhood_thenReturnFalseAndSuccessRemove() {
        // GIVEN
        Vertex<Integer> vertex = Vertex.create(1);
        Vertex<Integer> notVertexNeighbor = Vertex.create(2);
        // THEN
        boolean result = vertex.removeFromNeighborhood(notVertexNeighbor);
        // WHEN
        assertThat(result).isFalse();
        assertThat(vertex.getOpenNeighborhood().contains(notVertexNeighbor)).isFalse();
        assertThat(notVertexNeighbor.getOpenNeighborhood().contains(vertex)).isFalse();
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