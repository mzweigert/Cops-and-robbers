package com.graphrodite.model;


import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicates;
import com.graphrodite.exception.VertexAlreadyExistException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class GraphTest {

    @Test
    public void testNewInstance() {
        //WHEN
        Graph graph = Graph.newInstance();

        //THEN
        assertThat(graph).isNotNull();
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
        Vertex<Integer> v1 = graph.findVertex(firstVertexIndex).get();
        Vertex<Integer> v2 = graph.findVertex(secondVertexIndex).get();

        assertThat(v1.getIndex()).isEqualTo(1);
        assertThat(v2.getIndex()).isEqualTo(2);
        assertThat(v1.getOpenNeighbourhood().stream().findFirst().get()).isEqualTo(v2);
        assertThat(v2.getOpenNeighbourhood().stream().findFirst().get()).isEqualTo(v1);
        assertThat(graph.getEdges().size()).isEqualTo(1);
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
        assertThat(v1.getIndex()).isEqualTo(1);
        assertThat(graph.getEdges().size()).isEqualTo(1);
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
        assertThat(graph.getVertices().size()).isEqualTo(1);
        assertThat(graph.findVertex(firstVertexIndex).get()).isEqualTo(result);
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
        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    public void givenIndexes_whenAddPathAndGraphIsEmpty_thenSuccessAddPathToGraph() throws EdgeAlreadyExistException, PathContainsDuplicates {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();

        //WHEN
        List<Vertex<Integer>> result = graph.addPath(1, 2, 3, 4, 5);

        //THEN
        assertThat(result.size()).isEqualTo(5);
        assertThat(graph.getVertices().size()).isEqualTo(5);

    }

    @Test
    public void givenIndexes_whenAddPathAndGraphIsNotEmpty_thenSuccessAddPathToGraph() throws EdgeAlreadyExistException, PathContainsDuplicates {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        //WHEN
        List<Vertex<Integer>> result = graph.addPath(3, 4, 5, 6, 7);

        //THEN
        assertThat(result.size()).isEqualTo(5);
        assertThat(graph.getVertices().size()).isEqualTo(7);
        assertThat(graph.findVertex(3).get().getOpenNeighbourhood().size()).isEqualTo(3);
    }

    public void givenExistingIndex_whenFindVertex_thenReturnExistingVertex() throws UnsupportedOperationException, VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        graph.addVertex(firstVertexIndex);

        //WHEN
        Optional<Vertex<Integer>> vertex = graph.findVertex(firstVertexIndex);

        //THEN
        assertThat(vertex.isPresent()).isTrue();
        assertThat(vertex.get().getIndex()).isEqualTo(firstVertexIndex);
    }

    public void givenNotExistingIndex_whenFindVertex_thenReturnNull() throws UnsupportedOperationException, VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        graph.addVertex(firstVertexIndex);

        //WHEN
        Optional<Vertex<Integer>> vertex = graph.findVertex(firstVertexIndex);

        //THEN
        assertThat(vertex.isPresent()).isFalse();
    }

    public void givenExistingVertex_whenContainsVertex_thenReturnTrue() throws UnsupportedOperationException, VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer index = 1;
        graph.addVertex(index);

        //WHEN
        boolean result = graph.containsVertex(index);

        //THEN
        assertThat(result).isTrue();
    }

    public void givenNotExistingVertex_whenContainsVertex_thenReturnFalse() throws UnsupportedOperationException, VertexAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();

        //WHEN
        boolean result = graph.containsVertex(1);

        //THEN
        assertThat(result).isFalse();
    }

    public void givenExistingEdge_whenFindEdge_thenReturnExistingEdge() throws UnsupportedOperationException, EdgeAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;
        graph.addEdge(firstVertexIndex, secondVertexIndex);

        //WHEN
        Optional<Edge<Integer>> edge = graph.findEdge(firstVertexIndex, secondVertexIndex);

        //THEN
        assertThat(edge.isPresent()).isTrue();
        assertThat(edge.get().getFirst()).isEqualTo(firstVertexIndex);
        assertThat(edge.get().getSecond()).isEqualTo(firstVertexIndex);
    }

    public void givenNotExistingEdge_whenFindEdge_thenReturnNull() throws UnsupportedOperationException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;

        //WHEN
        Optional<Edge<Integer>> edge = graph.findEdge(firstVertexIndex, secondVertexIndex);

        //THEN
        assertThat(edge.isPresent()).isFalse();
    }

    public void givenExistingEdge_whenContainsEdge_thenReturnTrue() throws UnsupportedOperationException, EdgeAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;
        graph.addEdge(firstVertexIndex, secondVertexIndex);

        //WHEN
        boolean result = graph.containsEdge(firstVertexIndex, secondVertexIndex);

        //THEN
        assertThat(result).isTrue();
    }

    public void givenNotExistingEdge_whenContainsEdge_thenReturnFalse() throws UnsupportedOperationException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;

        //WHEN
        boolean result = graph.containsEdge(firstVertexIndex, secondVertexIndex);

        //THEN
        assertThat(result).isFalse();
    }

    public void givenNothing_whenPetersen_thenSuccessCreatePetersen() throws UnsupportedOperationException {
        //GIVEN

        //WHEN
        Graph<Integer> graph = Graph.petersen();

        //THEN
        assertThat(graph.getVertices().size()).isEqualTo(10);
        assertThat(graph.getVertices().size()).isEqualTo(15);

    }
}