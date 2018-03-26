package com.graphrodite.model;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.NeighborAlreadyExistException;
import com.graphrodite.exception.VertexAlreadyExistException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class GraphTest {

    @Test
    public void testNewInstance() {
        //WHEN
        Graph<Integer> graph = Graph.newInstance();
        //THEN
        assertNotNull(graph);
    }

    @Test
    public void givenDifferentVertex_whenAddEdge_thenSuccessAddEdge() throws EdgeAlreadyExistException, NeighborAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 2;
        //WHEN
        graph.addEdge(firstVertexIndex, secondVertexIndex);
        //THEN
        Vertex<Integer> v1 =  graph.getVertex(firstVertexIndex).get();
        Vertex<Integer> v2 = graph.getVertex(secondVertexIndex).get();

        assertTrue(v1.index.equals(1));
        assertTrue(v2.index.equals(2));
        assertEquals(v1.neighbors.get(0), v2);
        assertEquals(v2.neighbors.get(0), v1);
        assertEquals(graph.getEdges().size(), 1);
    }

    @Test
    public void givenSameVertex_whenAddEdge_thenSuccessAddEdge() throws EdgeAlreadyExistException, NeighborAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;
        //WHEN
        graph.addEdge(firstVertexIndex, secondVertexIndex);
        //THEN
        Vertex<Integer> v1 = graph.getVertex(firstVertexIndex).get();

        assertTrue(v1.index.equals(1));
        assertEquals(v1.neighbors.get(0), v1);
        assertEquals(graph.getEdges().size(), 1);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenSomeVertex_whenAddExistingEdge_thenThrowException() throws EdgeAlreadyExistException, NeighborAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 2;
        Integer secondVertexIndex = 3;
        //WHEN
        graph.addEdge(firstVertexIndex, secondVertexIndex);
        graph.addEdge(firstVertexIndex, secondVertexIndex);
    }


    @Test
    public void givenSomeIndex_whenAddVertex_thenSuccessAddVertex() throws VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        //WHEN
        Vertex<Integer> result = graph.addVertex(firstVertexIndex);
        //THEN
        assertEquals(graph.getVertices().size(), 1);
        assertEquals(graph.getVertex(firstVertexIndex).get(), result);
    }

    @Test(expected = VertexAlreadyExistException.class)
    public void givenSameIndex_whenAddExistingVertex_thenThrowException() throws VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        graph.addVertex(firstVertexIndex);
        //WHEN
        graph.addVertex(firstVertexIndex);
    }

    @Test
    public void givenDifferentIndexes_whenAddVertices_thenSuccessAddVerticesAndReturnThem() throws VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        //WHEN
        List<Vertex<Integer>> result = graph.addVertices(1, 2, 3, 4, 5);
        //THEN
        assertEquals(result.size(), 5);
    }

    public void givenExistingIndex_whenGetVertex_thenReturnExistingVertex() throws UnsupportedOperationException, VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        graph.addVertex(firstVertexIndex);
        //WHEN
        Optional<Vertex<Integer>> vertex = graph.getVertex(firstVertexIndex);

        assertTrue(vertex.isPresent());
        assertEquals(vertex.get().index, firstVertexIndex);
    }

    public void givenNotExistingIndex_whenGetVertex_thenReturnNull() throws UnsupportedOperationException, VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        graph.addVertex(firstVertexIndex);
        //WHEN
        Optional<Vertex<Integer>> vertex = graph.getVertex(firstVertexIndex);

        assertFalse(vertex.isPresent());
    }

}