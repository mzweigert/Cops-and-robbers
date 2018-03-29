package com.graphrodite.model;

import com.graphrodite.exception.NeighborAlreadyExistException;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class VertexTest {

    @Test
    public void givenDifferentVertices_whenCreateNeighbourhood_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = new Vertex<>(1);
        Vertex<Integer> vertexNeighbor = new Vertex<>(2);
        // THEN
        vertex.createNeighbourhood(vertexNeighbor);
        // WHEN
        assertEquals(vertex.getOpenNeighbourhood().size(), 1);
        assertTrue(vertex.getOpenNeighbourhood().contains(vertexNeighbor));
        assertEquals(vertexNeighbor.getOpenNeighbourhood().size(), 1);
        assertTrue(vertexNeighbor.getOpenNeighbourhood().contains(vertex));
    }

    @Test(expected = NeighborAlreadyExistException.class)
    public void givenDifferentVertices_whenInvokeCreateNeighbourhoodTwice_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = new Vertex<>(1);
        Vertex<Integer> vertexNeighbor = new Vertex<>(2);
        // THEN
        vertex.createNeighbourhood(vertexNeighbor);
        vertexNeighbor.createNeighbourhood(vertex);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenSameVertices_whenCreateNeighbourhood_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = new Vertex<>(1);
        // THEN
        vertex.createNeighbourhood(vertex);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenSameVerticesWithSameIndex_whenCreateNeighbourhood_thenSuccessCreateNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = new Vertex<>(1);
        Vertex<Integer> vertexNeighbor = new Vertex<>(1);

        // THEN
        vertex.createNeighbourhood(vertexNeighbor);
    }

    @Test
    public void givenExistingNeighbor_whenGetOpenNeighbourhood_thenSuccessGetOpenNeighbourhood() throws NeighborAlreadyExistException {

        // GIVEN
        Vertex<Integer> vertex = new Vertex<>(1);
        Vertex<Integer> vertexNeighbor = new Vertex<>(2);
        vertex.createNeighbourhood(vertexNeighbor);
        // THEN
        Collection<Vertex<Integer>> neighbourhood = vertex.getOpenNeighbourhood();
        // WHEN
        assertEquals(neighbourhood.size(), 1);
        assertTrue(neighbourhood.contains(vertexNeighbor));
    }

    @Test
    public void givenExistingNeighbor_whenGetClosedNeighbourhood_thenSuccessGetClosedNeighbourhood() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = new Vertex<>(1);
        Vertex<Integer> vertexNeighbor = new Vertex<>(2);
        vertex.createNeighbourhood(vertexNeighbor);
        // THEN
        Collection<Vertex<Integer>> neighbourhood = vertex.getClosedNeighbourhood();
        // WHEN
        assertEquals(neighbourhood.size(), 2);
        assertTrue(neighbourhood.contains(vertex));
        assertTrue(neighbourhood.contains(vertexNeighbor));
    }

    @Test
    public void givenExistingNeighbor_whenRemoveNeighbourhood_thenReturnTrueAndSuccessRemove() throws NeighborAlreadyExistException {
        // GIVEN
        Vertex<Integer> vertex = new Vertex<>(1);
        Vertex<Integer> vertexNeighbor = new Vertex<>(2);
        vertex.createNeighbourhood(vertexNeighbor);
        // THEN
        boolean result = vertex.removeNeighbourhood(vertexNeighbor);
        // WHEN
        assertTrue(result);
        assertFalse(vertex.getOpenNeighbourhood().contains(vertexNeighbor));
        assertFalse(vertexNeighbor.getOpenNeighbourhood().contains(vertex));

    }

    @Test
    public void givenNotExistingNeighbor_whenRemoveNeighbourhood_thenReturnFalseAndSuccessRemove() {
        // GIVEN
        Vertex<Integer> vertex = new Vertex<>(1);
        Vertex<Integer> notVertexNeighbor = new Vertex<>(2);
        // THEN
        boolean result = vertex.removeNeighbourhood(notVertexNeighbor);
        // WHEN
        assertFalse(result);
        assertFalse(vertex.getOpenNeighbourhood().contains(notVertexNeighbor));
        assertFalse(notVertexNeighbor.getOpenNeighbourhood().contains(vertex));
    }
}