package com.copsandrobber.model;

import com.copsandrobber.exception.EdgeAlreadyExistException;
import com.copsandrobber.exception.NeighborAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class GraphUtilsTest {

    private GraphUtils<Integer> graphUtils;

    @Before
    public void setUp(){
        graphUtils = new GraphUtils<>(Graph.<Integer>newInstance());
    }

    @Test
    public void givenSomeIndexAndCreateVertex_whenContainsVertex_thenReturnTrue() throws Exception {
        //GIVEN
        Integer someIndex = 1;
        graphUtils.createVertex(someIndex);
        //WHEN
        boolean result = graphUtils.containsVertex(someIndex);
        //THEN
        assertTrue(result);
    }

    @Test
    public void givenSomeIndexAndCreateVertex_whenContainsVertex_thenReturnFalse() throws Exception {
        //GIVEN
        Integer someIndex = 1;
        graphUtils.createVertex(someIndex);
        //WHEN
        boolean result = graphUtils.containsVertex(2);
        //THEN
        assertFalse(result);
    }

    @Test
    public void givenSomeIndex_whenCreateOrFindVertex_thenReturnVertex() throws Exception {
        //GIVEN
        Integer someIndex = 1;
        //WHEN
        Vertex<Integer> result = graphUtils.findOrCreateVertex(someIndex);
        //THEN
        assertEquals(result.index, someIndex);
    }

    @Test
    public void givenSomeIndex_whenCreateVertex_thenReturnVertex() throws Exception {
        //GIVEN
        Integer someIndex = 1;
        //WHEN
        Vertex<Integer> result = graphUtils.createVertex(someIndex);
        //THEN
        assertEquals(result.index, someIndex);
    }

    @Test
    public void givenSomePredicate_whenFindVertex_thenReturnVertex() throws Exception {
        //GIVEN
        Integer someIndex = 1;
        graphUtils.createVertex(someIndex);
        Predicate<Vertex> somePredicate = v -> v.index.equals(someIndex);
        //WHEN
        Optional<Vertex<Integer>> result = graphUtils.findVertex(somePredicate);
        //THEN
        assertTrue(result.isPresent());
        assertEquals(result.get().index, someIndex);
    }

    @Test
    public void givenSomePredicate_whenFindEdge_thenReturnVertex() throws Exception {
        //GIVEN
        Integer someIndex = 1;
        graphUtils.createVertex(someIndex);
        Predicate<Vertex> somePredicate = v -> v.index.equals(someIndex);
        //WHEN
        Optional<Vertex<Integer>> result = graphUtils.findVertex(somePredicate);
        //THEN
        assertTrue(result.isPresent());
        assertEquals(result.get().index, someIndex);
    }

    @Test
    public void givenDifferentVertex_whenCreateEdge_thenSuccessCreateEdge() throws EdgeAlreadyExistException, NeighborAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Vertex<Integer> v1 = graphUtils.createVertex(firstVertexIndex);
        Integer secondVertexIndex = 2;
        Vertex<Integer> v2 = graphUtils.createVertex(secondVertexIndex);
        //WHEN
        graphUtils.createEdge(v1, v2);
        //THEN
        Optional<Vertex<Integer>> expectedVertex1 = graphUtils.findVertex(v -> v.index.equals(firstVertexIndex));
        Optional<Vertex<Integer>> expectedVertex2 = graphUtils.findVertex(v -> v.index.equals(secondVertexIndex));
        assertTrue(expectedVertex1.isPresent());
        assertTrue(expectedVertex2.isPresent());
        assertEquals(expectedVertex1.get().index, firstVertexIndex);
        assertEquals(expectedVertex2.get().index, secondVertexIndex);
    }

    @Test
    public void givenSameVertex_whenCreateEdge_thenSuccessCreateEdge() throws EdgeAlreadyExistException, NeighborAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Vertex<Integer> v1 = graphUtils.createVertex(firstVertexIndex);
        Integer secondVertexIndex = 1;
        Vertex<Integer> v2 = graphUtils.createVertex(secondVertexIndex);
        //WHEN
        graphUtils.createEdge(v1, v2);
        //THEN
        Optional<Vertex<Integer>> expectedVertex1 = graphUtils.findVertex(v -> v.index.equals(firstVertexIndex));
        Optional<Vertex<Integer>> expectedVertex2 = graphUtils.findVertex(v -> v.index.equals(secondVertexIndex));
        assertTrue(expectedVertex1.isPresent());
        assertTrue(expectedVertex2.isPresent());
        assertEquals(expectedVertex1.get().index, firstVertexIndex);
        assertEquals(expectedVertex2.get().index, secondVertexIndex);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenDifferentVertex_whenAddExistingEdge_thenThrowException() throws EdgeAlreadyExistException, NeighborAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Vertex<Integer> v1 = graphUtils.createVertex(firstVertexIndex);
        Integer secondVertexIndex = 2;
        Vertex<Integer> v2 = graphUtils.createVertex(secondVertexIndex);
        graphUtils.createEdge(v1, v2);
        //WHEN
        graphUtils.createEdge(new Vertex<>(firstVertexIndex), new Vertex<>(secondVertexIndex));
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenSameVertex_whenAddExistingEdge_thenThrowException() throws EdgeAlreadyExistException, NeighborAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Vertex<Integer> v1 = graphUtils.createVertex(firstVertexIndex);
        Integer secondVertexIndex = 1;
        Vertex<Integer> v2 = graphUtils.createVertex(secondVertexIndex);
        graphUtils.createEdge(v1, v2);
        //WHEN
        graphUtils.createEdge(new Vertex<>(firstVertexIndex), new Vertex<>(secondVertexIndex));
    }
}