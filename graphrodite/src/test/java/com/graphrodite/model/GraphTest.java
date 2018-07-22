package com.graphrodite.model;


import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.IndexesContainsDuplicatesException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.factory.GraphTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.Set;

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
        assertThat(v1.getOpenNeighborhood().stream().findFirst().get()).isEqualTo(v2);
        assertThat(v2.getOpenNeighborhood().stream().findFirst().get()).isEqualTo(v1);
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
    public void givenSomeVertexAndHisNeighbors_whenAddEdgeToVertex_thenSuccessAddEdgesToVertex() throws EdgeAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer someVertexIndex = 1;
        Integer someFirstNeighbor = 2;
        Integer someSecondNeighbor = 3;
        Integer someThirdNeighbor = 4;

        //WHEN
        Set<Edge<Integer>> edges = graph.addEdgesToVertex(someVertexIndex, someFirstNeighbor, someSecondNeighbor, someThirdNeighbor);

        //THEN
        assertThat(edges).isNotNull();
        assertThat(edges.size()).isEqualTo(3);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenSomeVertexAndHisNeighborsWithDuplicated_whenAddEdgeToVertex_thenThrowException() throws EdgeAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer someVertexIndex = 1;
        Integer someFirstNeighbor = 2;
        Integer someSecondNeighbor = 3;
        Integer someThirdNeighbor = 3;

        //WHEN
        graph.addEdgesToVertex(someVertexIndex, someFirstNeighbor, someSecondNeighbor, someThirdNeighbor);
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
        Set<Vertex<Integer>> result = graph.addVertices(1, 2, 3, 4, 5);

        //THEN
        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    public void givenIndexes_whenAddPathAndGraphIsEmpty_thenSuccessAddPathToGraph() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();

        //WHEN
        Set<Vertex<Integer>> result = graph.addPath(1, 2, 3, 4, 5);

        //THEN
        assertThat(result.size()).isEqualTo(5);
        assertThat(graph.getVertices().size()).isEqualTo(5);
        assertThat(graph.containsCycle()).isFalse();
    }

    @Test
    public void givenIndexes_whenAddPathAndGraphIsNotEmpty_thenSuccessAddPathToGraph() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);


        //WHEN
        Set<Vertex<Integer>> result = graph.addPath(3, 4, 5, 6, 7);

        //THEN
        assertThat(result.size()).isEqualTo(5);
        assertThat(graph.getVertices().size()).isEqualTo(7);
        assertThat(graph.findVertex(3).get().getOpenNeighborhood().size()).isEqualTo(2);
        assertThat(graph.containsCycle()).isFalse();
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenIndexesWithExitingEdge_whenAddPathAndGraphContainsGivenEdge_thenThrowException() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        graph.addEdge(1, 2);

        //WHEN
        graph.addPath(1, 2, 3, 4, 5);
    }

    @Test(expected = IndexesContainsDuplicatesException.class)
    public void givenIndexesWithDuplicates_whenAddPathAndGraphIsEmpty_thenThrowException() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();

        //WHEN
        graph.addPath(1, 2, 3, 3, 4, 5);
    }

    @Test
    public void givenIndexes_whenAddCycleAndGraphIsEmpty_thenSuccessAddCycleToGraph() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();

        //WHEN
        Set<Vertex<Integer>> result = graph.addCycle(1, 2, 3, 4, 5);

        //THEN
        assertThat(result.size()).isEqualTo(5);
        assertThat(graph.getVertices().size()).isEqualTo(5);
        assertThat(graph.containsCycle()).isTrue();
    }

    @Test
    public void givenIndexes_whenAddCycleAndGraphIsNotEmpty_thenSuccessAddCycleToGraph() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        //WHEN
        Set<Vertex<Integer>> result = graph.addCycle(3, 4, 5, 6, 7);

        //THEN
        assertThat(result.size()).isEqualTo(5);
        assertThat(graph.getVertices().size()).isEqualTo(7);
        assertThat(graph.findVertex(3).get().getOpenNeighborhood().size()).isEqualTo(4);
        assertThat(graph.containsCycle()).isTrue();
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenIndexesWithExitingEdge_whenAddCycleAndGraphContainsGivenEdge_thenThrowException() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        graph.addEdge(1, 2);

        //WHEN
        graph.addCycle(1, 2, 3, 4, 5);
    }

    @Test(expected = IndexesContainsDuplicatesException.class)
    public void givenIndexesWithDuplicates_whenAddCycleAndGraphIsEmpty_thenThrowException() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();

        //WHEN
        graph.addCycle(1, 2, 3, 3, 4, 5);
    }

    @Test
    public void givenC5Graph_whenContainsCycle_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        graph.addCycle(1, 2, 3, 4, 5);

        //WHEN
        boolean result = graph.containsCycle();

        //THEN
        assertThat(result).isTrue();
        assertThat(graph.getVertices().size()).isEqualTo(5);
    }

    @Test
    public void givenP5Graph_whenContainsCycle_thenReturnFalse() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        graph.addPath(1, 2, 3, 4, 5);

        //WHEN
        boolean result = graph.containsCycle();

        //THEN
        assertThat(result).isFalse();
        assertThat(graph.getVertices().size()).isEqualTo(5);
    }

    @Test
    public void givenT5Graph_whenContainsCycle_thenReturnFalse() {
        //GIVEN
        Graph<Integer> graph = GraphTemplate.getInstance().createTreeWithGivenLength(10);

        //WHEN
        boolean result = graph.containsCycle();

        //THEN
        assertThat(result).isFalse();
        assertThat(graph.getVertices().size()).isEqualTo(10);
    }

    @Test
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

    @Test
    public void givenNotExistingIndex_whenFindVertex_thenReturnNull() throws UnsupportedOperationException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        Integer firstVertexIndex = 1;

        //WHEN
        Optional<Vertex<Integer>> vertex = graph.findVertex(firstVertexIndex);

        //THEN
        assertThat(vertex.isPresent()).isFalse();
    }

    @Test
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

    @Test
    public void givenNotExistingVertex_whenContainsVertex_thenReturnFalse() throws UnsupportedOperationException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();

        //WHEN
        boolean result = graph.containsVertex(1);

        //THEN
        assertThat(result).isFalse();
    }

    @Test
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
        assertThat(edge.get().getFirst().getIndex()).isEqualTo(firstVertexIndex);
        assertThat(edge.get().getSecond().getIndex()).isEqualTo(firstVertexIndex);
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
    public void givenGraph_whenClone_thenReturnClonedGraph() throws EdgeAlreadyExistException {
        //GIVEN
        Graph<Integer> graph = Graph.newInstance();
        graph.addEdge(1, 2);
        graph.addEdgesToVertex(1, 3, 4, 5, 6);

        //WHEN
        Graph<Integer> result = graph.clone();

        //THEN
        assertThat(result.getEdges().size()).isEqualTo(5);
        assertThat(result.getVertices().size()).isEqualTo(6);
    }

}