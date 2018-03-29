package com.graphrodite.service.internal;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;
import com.graphrodite.service.internal.GraphService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class GraphServiceTest {

    private GraphService<Integer> graphService;

    @Before
    public void setUp() {
        Graph<Integer> graph = Graph.newInstance();
        graphService = new GraphService<>(graph.getVertices(), graph.getEdges());
    }

    @Test
    public void givenSomeIndexAndAddVertex_whenContainsVertex_thenReturnTrue() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        graphService.addVertex(someIndex);
        //WHEN
        boolean result = graphService.containsVertex(someIndex);
        //THEN
        assertTrue(result);
    }

    @Test
    public void givenSomeIndexAndAddVertex_whenContainsVertex_thenReturnFalse() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        graphService.addVertex(someIndex);
        //WHEN
        boolean result = graphService.containsVertex(2);
        //THEN
        assertFalse(result);
    }

    @Test
    public void givenSomeIndex_whenCreateOrFindVertex_thenReturnVertex() {
        //GIVEN
        Integer someIndex = 1;
        //WHEN
        Vertex<Integer> result = graphService.findOrCreateVertex(someIndex);
        //THEN
        assertEquals(result.getIndex(), someIndex);
    }

    @Test
    public void givenSomeIndex_whenAddVertex_thenReturnVertex() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        //WHEN
        Vertex<Integer> result = graphService.addVertex(someIndex);
        //THEN
        assertEquals(result.getIndex(), someIndex);
    }

    @Test
    public void givenSomePredicate_whenFindVertex_thenReturnVertex() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        graphService.addVertex(someIndex);
        Predicate<Vertex> somePredicate = v -> v.getIndex().equals(someIndex);
        //WHEN
        Optional<Vertex<Integer>> result = graphService.findVertex(somePredicate);
        //THEN
        assertTrue(result.isPresent());
        assertEquals(result.get().getIndex(), someIndex);
    }

    @Test
    public void givenSomePredicate_whenFindEdge_thenReturnVertex() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        graphService.addVertex(someIndex);
        Predicate<Vertex> somePredicate = v -> v.getIndex().equals(someIndex);
        //WHEN
        Optional<Vertex<Integer>> result = graphService.findVertex(somePredicate);
        //THEN
        assertTrue(result.isPresent());
        assertEquals(result.get().getIndex(), someIndex);
    }

    @Test
    public void givenDifferentVertex_whenCreateEdge_thenSuccessCreateEdge() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 2;
        //WHEN
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
        //THEN
        Optional<Vertex<Integer>> expectedVertex1 = graphService.findVertex(v -> v.getIndex().equals(firstVertexIndex));
        Optional<Vertex<Integer>> expectedVertex2 = graphService.findVertex(v -> v.getIndex().equals(secondVertexIndex));
        assertTrue(expectedVertex1.isPresent());
        assertTrue(expectedVertex2.isPresent());
        assertEquals(expectedVertex1.get().getIndex(), firstVertexIndex);
        assertEquals(expectedVertex2.get().getIndex(), secondVertexIndex);
        assertEquals(expectedVertex1.get().getOpenNeighbourhood().stream().findFirst().get(), expectedVertex2.get());
        assertEquals(expectedVertex2.get().getOpenNeighbourhood().stream().findFirst().get(), expectedVertex1.get());
        assertTrue(graphService.containsEdge(expectedVertex1.get().getIndex(), expectedVertex2.get().getIndex()));
    }

    @Test
    public void givenSameVertex_whenCreateEdge_thenSuccessCreateEdge() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;
        //WHEN
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
        //THEN
        Optional<Vertex<Integer>> expectedVertex1 = graphService.findVertex(v -> v.getIndex().equals(firstVertexIndex));
        Optional<Vertex<Integer>> expectedVertex2 = graphService.findVertex(v -> v.getIndex().equals(secondVertexIndex));
        assertTrue(expectedVertex1.isPresent());
        assertTrue(expectedVertex2.isPresent());
        assertEquals(expectedVertex1.get().getIndex(), firstVertexIndex);
        assertEquals(expectedVertex2.get().getIndex(), secondVertexIndex);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenDifferentVertex_whenAddExistingEdge_thenThrowException() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 2;
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
        //WHEN
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenSameVertex_whenAddExistingEdge_thenThrowException() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
        //WHEN
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
    }
}