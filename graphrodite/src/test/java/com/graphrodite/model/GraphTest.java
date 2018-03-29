package com.graphrodite.model;


import com.graphrodite.exception.EdgeAlreadyExistException;
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
        Graph graph = Graph.newInstance();
        //THEN
        assertNotNull(graph);
    }

    @Test
    public void givenDifferentVertex_whenAddEdge_thenSuccessAddEdge() throws EdgeAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 2;
        //WHEN
        graph.addEdge(firstVertexIndex, secondVertexIndex);
        //THEN
        Vertex<Integer> v1 =  graph.findVertex(firstVertexIndex).get();
        Vertex<Integer> v2 = graph.findVertex(secondVertexIndex).get();

        assertTrue(v1.getIndex().equals(1));
        assertTrue(v2.getIndex().equals(2));
        assertEquals(v1.getOpenNeighbourhood().stream().findFirst().get(), v2);
        assertEquals(v2.getOpenNeighbourhood().stream().findFirst().get(), v1);
        assertEquals(graph.getEdges().size(), 1);
    }

    @Test
    public void givenSameVertex_whenAddEdge_thenSuccessAddEdge() throws EdgeAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;
        //WHEN
        graph.addEdge(firstVertexIndex, secondVertexIndex);
        //THEN
        Vertex<Integer> v1 = graph.findVertex(firstVertexIndex).get();

        assertTrue(v1.getIndex().equals(1));
        assertEquals(graph.getEdges().size(), 1);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenSomeVertex_whenAddExistingEdge_thenThrowException() throws EdgeAlreadyExistException {
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
        assertEquals(graph.findVertex(firstVertexIndex).get(), result);
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

    @Test
    public void givenIndexes_whenAddPathAndGraphIsEmpty_thenSuccessAddPathToGraph() throws EdgeAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        //WHEN
        List<Vertex<Integer>> result = graph.addPath(1, 2, 3, 4, 5);
        //THEN
        assertEquals(result.size(), 5);
        assertEquals(graph.getVertices().size(), 5);

    }

    @Test
    public void givenIndexes_whenAddPathAndGraphIsNotEmpty_thenSuccessAddPathToGraph() throws EdgeAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
            graph.addEdge(1, 2)
                    .addEdge(2, 3)
                    .addEdge(3, 1);
        //WHEN
        List<Vertex<Integer>> result = graph.addPath(3, 4, 5, 6, 7);
        //THEN
        assertEquals(result.size(), 5);
        assertEquals(graph.getVertices().size(), 7);
        assertEquals(graph.findVertex(3).get().getOpenNeighbourhood().size(), 3);
    }

    public void givenExistingIndex_whenGetVertex_thenReturnExistingVertex() throws UnsupportedOperationException, VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        graph.addVertex(firstVertexIndex);
        //WHEN
        Optional<Vertex<Integer>> vertex = graph.findVertex(firstVertexIndex);

        assertTrue(vertex.isPresent());
        assertEquals(vertex.get().getIndex(), firstVertexIndex);
    }

    public void givenNotExistingIndex_whenGetVertex_thenReturnNull() throws UnsupportedOperationException, VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        graph.addVertex(firstVertexIndex);
        //WHEN
        Optional<Vertex<Integer>> vertex = graph.findVertex(firstVertexIndex);

        assertFalse(vertex.isPresent());
    }

}