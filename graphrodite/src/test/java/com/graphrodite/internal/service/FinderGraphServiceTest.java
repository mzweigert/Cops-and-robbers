package com.graphrodite.internal.service;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.IndexesContainsDuplicatesException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.factory.GraphTemplate;
import com.graphrodite.model.Edge;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FinderGraphServiceTest {

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
    public void givenSomeIndexOfNotExistingVertex_whenFindVertex_thenReturnEmptyOptional() {
        //GIVEN
        Integer someIndex = 1;

        //WHEN
        Optional<Vertex<Integer>> result = finderGraphService.findVertex(someIndex);

        //THEN
        assertThat(result.isPresent()).isFalse();
    }


    @Test
    public void givenSomeIndexOfExistingVertex_whenFindVertex_thenReturnVertex() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        creatorGraphService.addVertex(someIndex);

        //WHEN
        Optional<Vertex<Integer>> result = finderGraphService.findVertex(someIndex);

        //THEN
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getIndex()).isEqualTo(someIndex);
    }

    @Test
    public void givenSomePredicateAndAddVertex_whenFindVertex_thenReturnVertex() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        creatorGraphService.addVertex(someIndex);
        Predicate<Vertex> somePredicate = v -> v.getIndex().equals(someIndex);
        //WHEN
        Optional<Vertex<Integer>> result = finderGraphService.findVertex(somePredicate);
        //THEN
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getIndex()).isEqualTo(someIndex);
    }

    @Test
    public void givenSomePredicate_whenFindVertex_thenReturnEmptyOptional() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        Predicate<Vertex> somePredicate = v -> v.getIndex().equals(someIndex);
        //WHEN
        Optional<Vertex<Integer>> result = finderGraphService.findVertex(somePredicate);
        //THEN
        assertThat(result.isPresent()).isFalse();
    }


    @Test
    public void givenAddVertex_whenContainsVertex_thenReturnTrue() throws VertexAlreadyExistException {
        //GIVEN
        creatorGraphService.addVertex(1);

        //WHEN
        boolean result = finderGraphService.containsVertex(1);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenAddVertex_whenContainsOtherVertex_thenReturnFalse() throws VertexAlreadyExistException {
        //GIVEN
        creatorGraphService.addVertex(1);

        //WHEN
        boolean result = finderGraphService.containsVertex(2);
        //THEN
        assertThat(result).isFalse();
    }


    @Test
    public void givenSomePredicateAndAddEdge_whenFindEdge_thenReturnVertex() throws EdgeAlreadyExistException {
        //GIVEN
        Integer someFirstIndex = 1;
        Integer someSecondIndex = 2;
        Predicate<Edge> somePredicate = e ->
                e.getFirst().getIndex().equals(someFirstIndex) &&
                        e.getSecond().getIndex().equals(someSecondIndex);
        creatorGraphService.addEdge(someFirstIndex, someSecondIndex);
        //WHEN
        Optional<Edge<Integer>> result = finderGraphService.findEdge(somePredicate);
        //THEN
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getFirst().getIndex()).isEqualTo(someFirstIndex);
        assertThat(result.get().getSecond().getIndex()).isEqualTo(someSecondIndex);
    }

    @Test
    public void givenSomePredicate_whenFindEdge_thenReturnEmptyOptional() {
        //GIVEN
        Integer someFirstIndex = 1;
        Integer someSecondIndex = 2;
        Predicate<Edge> somePredicate = e ->
                e.getFirst().getIndex().equals(someFirstIndex) &&
                        e.getSecond().getIndex().equals(someSecondIndex);

        //WHEN
        Optional<Edge<Integer>> result = finderGraphService.findEdge(somePredicate);
        //THEN
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void givenAddEdge_whenContainsEdge_thenReturnTrue() throws EdgeAlreadyExistException {
        //GIVEN
        creatorGraphService.addEdge(1, 2);

        //WHEN
        boolean result = finderGraphService.containsEdge(1, 2);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenNothing_whenContainsEdge_thenReturnFalse() {
        //GIVEN

        //WHEN
        boolean result = finderGraphService.containsEdge(1, 2);

        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenSomeExistingEdge_whenContainsEdgeWithReverseIndexes_thenReturnTrue() throws EdgeAlreadyExistException {
        //GIVEN
        creatorGraphService.addEdge(1, 2);

        //WHEN
        boolean result = finderGraphService.containsEdge(2, 1);

        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenSomeExistingEdge_whenContainsOtherEdgeThanAdded_thenReturnFalse() throws EdgeAlreadyExistException {
        //GIVEN
        creatorGraphService.addEdge(1, 2);

        //WHEN
        boolean result = finderGraphService.containsEdge(1, 3);

        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIndexesWithDuplicates_whenFindDuplicatedIndexes_thenReturnDuplicatedIndexes() {
        //GIVEN

        //WHEN
        Set<Integer> duplicatedIndexes = finderGraphService.findDuplicatedIndexes(1, 2, 3, 3, 4, 4, 5, 5);

        //THEN
        assertThat(duplicatedIndexes).isNotNull();
        assertThat(duplicatedIndexes.size()).isEqualTo(3);
    }

    @Test
    public void givenIndexesWithoutDuplicates_whenFindDuplicatedIndexes_thenReturnEmptySet() {
        //GIVEN

        //WHEN
        Set<Integer> duplicatedIndexes = finderGraphService.findDuplicatedIndexes(1, 2, 3, 4, 5);

        //THEN
        assertThat(duplicatedIndexes).isNotNull();
        assertThat(duplicatedIndexes.isEmpty()).isTrue();
    }

    @Test
    public void givenP5Graph_whenContainsCycle_thenReturnFalse() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        creatorGraphService.addPath(1, 2, 3, 4, 5);

        //WHEN
        boolean result = finderGraphService.containsCycle();

        //THEN
        assertThat(result).isFalse();
        assertThat(finderGraphService.vertices.size()).isEqualTo(5);
    }

    @Test
    public void givenC5Graph_whenContainsCycle_thenReturnFalse() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        //GIVEN
        creatorGraphService.addCycle(1, 2, 3, 4, 5);

        //WHEN
        boolean result = finderGraphService.containsCycle();

        //THEN
        assertThat(result).isTrue();
        assertThat(finderGraphService.vertices.size()).isEqualTo(5);
    }
}