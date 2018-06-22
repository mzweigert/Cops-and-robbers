package com.graphrodite.internal.service;


import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.IndexesContainsDuplicatesException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.model.Edge;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CreatorGraphServiceTest {

    private FinderGraphService<Integer> finderGraphService;
    private CreatorGraphService<Integer> creatorGraphService;

    @Before
    public void setUp() {
        Graph<Integer> graph = Graph.newInstance();
        Set<Vertex<Integer>> vertices = graph.getVertices();
        Set<Edge<Integer>> edges = graph.getEdges();
        creatorGraphService = new CreatorGraphService<>(vertices, edges);
        finderGraphService = new FinderGraphService<>(vertices, edges);
    }

    @Test
    public void givenIndexes_whenAddVertices_thenSuccessAddVerticesAndReturnThem() throws VertexAlreadyExistException {

        //WHEN
        Set<Vertex<Integer>> result = creatorGraphService.addVertices(1, 2, 3, 4, 5);

        //THEN
        assertThat(result.size()).isEqualTo(5);
    }


    @Test
    public void givenSomeIndex_whenAddVertex_thenSuccessAddVertex() throws VertexAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;

        //WHEN
        Vertex<Integer> result = creatorGraphService.addVertex(firstVertexIndex);

        //THEN
        assertThat(creatorGraphService.vertices.size()).isEqualTo(1);
        assertThat(finderGraphService.findVertex(firstVertexIndex).get()).isEqualTo(result);
    }

    @Test(expected = VertexAlreadyExistException.class)
    public void givenSameIndex_whenAddExistingVertex_thenThrowException() throws VertexAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        creatorGraphService.addVertex(firstVertexIndex);

        //WHEN
        creatorGraphService.addVertex(firstVertexIndex);
    }

    @Test
    public void givenDifferentVertex_whenAddEdge_thenSuccessCreateEdge() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 2;

        //WHEN
        creatorGraphService.addEdge(firstVertexIndex, secondVertexIndex);

        //THEN
        Optional<Vertex<Integer>> expectedVertex1 = finderGraphService.findVertex(v -> v.getIndex().equals(firstVertexIndex));
        Optional<Vertex<Integer>> expectedVertex2 = finderGraphService.findVertex(v -> v.getIndex().equals(secondVertexIndex));
        assertThat(expectedVertex1.isPresent()).isTrue();
        assertThat(expectedVertex2.isPresent()).isTrue();
        assertThat(expectedVertex1.get().getIndex()).isEqualTo(firstVertexIndex);
        assertThat(expectedVertex2.get().getIndex()).isEqualTo(secondVertexIndex);
        assertThat(expectedVertex1.get().getOpenNeighborhood().stream().findFirst().get()).isEqualTo(expectedVertex2.get());
        assertThat(expectedVertex2.get().getOpenNeighborhood().stream().findFirst().get()).isEqualTo(expectedVertex1.get());
        assertThat(finderGraphService.containsEdge(expectedVertex1.get().getIndex(), expectedVertex2.get().getIndex())).isTrue();
    }

    @Test
    public void givenSameVertex_whenAddEdge_thenSuccessCreateEdge() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;

        //WHEN
        creatorGraphService.addEdge(firstVertexIndex, secondVertexIndex);

        //THEN
        Optional<Vertex<Integer>> expectedVertex1 = finderGraphService.findVertex(v -> v.getIndex().equals(firstVertexIndex));
        Optional<Vertex<Integer>> expectedVertex2 = finderGraphService.findVertex(v -> v.getIndex().equals(secondVertexIndex));
        assertThat(expectedVertex1.isPresent()).isTrue();
        assertThat(expectedVertex2.isPresent()).isTrue();
        assertThat(expectedVertex1.get().getIndex()).isEqualTo(firstVertexIndex);
        assertThat(expectedVertex2.get().getIndex()).isEqualTo(firstVertexIndex);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenDifferentVertex_whenAddExistingEdge_thenThrowException() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 2;
        creatorGraphService.addEdge(firstVertexIndex, secondVertexIndex);

        //WHEN
        creatorGraphService.addEdge(firstVertexIndex, secondVertexIndex);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenSameVertex_whenAddExistingEdge_thenThrowException() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;
        creatorGraphService.addEdge(firstVertexIndex, secondVertexIndex);

        //WHEN
        creatorGraphService.addEdge(firstVertexIndex, secondVertexIndex);
    }

    @Test
    public void givenSomeVertexAndHisNeighbors_whenAddEdgeToVertex_thenSuccessAddEdgesToVertex() throws EdgeAlreadyExistException {
        //GIVEN
        Integer someVertexIndex = 1;
        Integer someFirstNeighbor = 2;
        Integer someSecondNeighbor = 3;
        Integer someThirdNeighbor = 4;

        //WHEN
        Set<Edge<Integer>> edges = creatorGraphService.addEdgesToVertex(someVertexIndex, someFirstNeighbor, someSecondNeighbor, someThirdNeighbor);

        //THEN
        assertThat(edges).isNotNull();
        assertThat(edges.size()).isEqualTo(3);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenSomeVertexAndHisNeighborsWithDuplicated_whenAddEdgeToVertex_thenThrowException() throws EdgeAlreadyExistException {
        //GIVEN
        Integer someVertexIndex = 1;
        Integer someFirstNeighbor = 2;
        Integer someSecondNeighbor = 3;
        Integer someThirdNeighbor = 3;

        //WHEN
        creatorGraphService.addEdgesToVertex(someVertexIndex, someFirstNeighbor, someSecondNeighbor, someThirdNeighbor);
    }

    @Test
    public void givenIndexes_whenAddCycleAndGraphIsEmpty_thenSuccessAddCycleToGraph() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //WHEN
        Set<Vertex<Integer>> result = creatorGraphService.addCycle(1, 2, 3, 4, 5);

        //THEN
        assertThat(result.size()).isEqualTo(5);
        assertThat(creatorGraphService.vertices.size()).isEqualTo(5);
        assertThat(finderGraphService.containsCycle()).isTrue();
    }

    @Test
    public void givenIndexes_whenAddCycleAndGraphIsNotEmpty_thenSuccessAddCycleToGraph() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        creatorGraphService.addEdge(1, 2);
        creatorGraphService.addEdge(2, 3);
        creatorGraphService.addEdge(3, 1);

        //WHEN
        Set<Vertex<Integer>> result = creatorGraphService.addCycle(3, 4, 5, 6, 7);

        //THEN
        assertThat(result.size()).isEqualTo(5);
        assertThat(creatorGraphService.vertices.size()).isEqualTo(7);
        assertThat(finderGraphService.findVertex(3).get().getOpenNeighborhood().size()).isEqualTo(4);
        assertThat(finderGraphService.containsCycle()).isTrue();
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenIndexesWithExitingEdge_whenAddCycleAndGraphContainsGivenEdge_thenThrowException() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        creatorGraphService.addEdge(1, 2);

        //WHEN
        creatorGraphService.addCycle(1, 2, 3, 4, 5);
    }

    @Test(expected = IndexesContainsDuplicatesException.class)
    public void givenIndexesWithDuplicates_whenAddCycleAndGraphIsEmpty_thenThrowException() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //WHEN
        creatorGraphService.addCycle(1, 2, 3, 3, 4, 5);
    }

    @Test
    public void givenIndexes_whenAddPathAndGraphIsEmpty_thenSuccessAddPathToGraph() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {

        //WHEN
        Set<Vertex<Integer>> result = creatorGraphService.addPath(1, 2, 3, 4, 5);

        //THEN
        assertThat(result.size()).isEqualTo(5);
        assertThat(creatorGraphService.vertices.size()).isEqualTo(5);
        assertThat(finderGraphService.containsCycle()).isFalse();
    }

    @Test
    public void givenIndexes_whenAddPathAndGraphIsNotEmpty_thenSuccessAddPathToGraph() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        creatorGraphService.addEdge(1, 2);
        creatorGraphService.addEdge(2, 3);

        //WHEN
        Set<Vertex<Integer>> result = creatorGraphService.addPath(3, 4, 5, 6, 7);

        //THEN
        assertThat(result.size()).isEqualTo(5);
        assertThat(creatorGraphService.vertices.size()).isEqualTo(7);
        assertThat(finderGraphService.findVertex(3).get().getOpenNeighborhood().size()).isEqualTo(2);
        assertThat(finderGraphService.containsCycle()).isFalse();
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenIndexesWithExitingEdge_whenAddPathAndGraphContainsGivenEdge_thenThrowException() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        creatorGraphService.addEdge(1, 2);

        //WHEN
        creatorGraphService.addPath(1, 2, 3, 4, 5);
    }

    @Test(expected = IndexesContainsDuplicatesException.class)
    public void givenIndexesWithDuplicates_whenAddPathAndGraphIsEmpty_thenThrowException() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //WHEN
        creatorGraphService.addPath(1, 2, 3, 3, 4, 5);
    }
}
