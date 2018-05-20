package com.copsandrobber.algorithm.one_cop_enough.helper;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicates;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IsOneCopEnoughHelperTest {
    private Graph<Integer> graph;

    @InjectMocks
    private IsOneCopEnoughHelper helper;

    @Before
    public void setUp() {
        graph = Graph.newInstance();
    }
    @Test
    public void givenTwoDominatingVertex_whenIsFirstDominatingSecond_thenReturnFalse() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Vertex<Integer> dominating_1 = graph.findVertex(2).get();
        Vertex<Integer> dominating_2 = graph.findVertex(9).get();

        // WHEN
        boolean result = helper.isFirstDominatingSecond(dominating_1, dominating_2);
        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenDominatingAndDominatedVertex_whenIsFirstDominatingSecond_thenReturnFalse() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        graph.addPath(1, 2, 3);
        graph.addEdge(1, 3);
        Vertex<Integer> dominating = graph.findVertex(3).get();
        Vertex<Integer> dominated = graph.findVertex(2).get();

        // WHEN
        boolean result = helper.isFirstDominatingSecond(dominating, dominated);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRobberInTrapAndCopBeforeHim_whenIsAllRobbersNeighbourAndAnyCopIsMarked_thenReturnTrue()
            throws EdgeAlreadyExistException, PathContainsDuplicates {

        // GIVEN
        // Easy to show, that robber has no free vertex to move, and will be caught
        graph.addPath(0, 1, 2);
        Set<Vertex<Integer>> vertices = graph.getVertices();
        Vertex<Integer> robberPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        Vertex<Integer> copPosition = vertices.stream().filter(v -> v.getIndex().equals(1)).findFirst().get();

        Collection<Vertex<Integer>> robberNeighbourhood = robberPosition.getClosedNeighbourhood();
        Collection<Vertex<Integer>> copNeighbourhood = copPosition.getClosedNeighbourhood();
        Set<Pair<Vertex<Integer>, Vertex<Integer>>> marked = helper.createMarkedConfigurations(vertices);
        // WHEN
        boolean result = helper.isAllRobbersNeighbourAndAnyCopIsMarked(robberNeighbourhood, copNeighbourhood, marked);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRobberWithFreeButMarkedNeighbourAndCop_whenIsAllRobbersNeighbourAndAnyCopIsMarked_thenReturnTrue()
            throws EdgeAlreadyExistException, PathContainsDuplicates {

        // GIVEN
        // Easy to show, that robber has no free vertex to move, and will be caught
        graph.addPath(0, 1, 2);
        Set<Vertex<Integer>> vertices = graph.getVertices();
        Vertex<Integer> robberPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        Vertex<Integer> copPosition = vertices.stream().filter(v -> v.getIndex().equals(0)).findFirst().get();
        Collection<Vertex<Integer>> robberNeighbourhood = robberPosition.getClosedNeighbourhood();
        Collection<Vertex<Integer>> copNeighbourhood = copPosition.getClosedNeighbourhood();
        Set<Pair<Vertex<Integer>, Vertex<Integer>>> marked = helper.createMarkedConfigurations(vertices);
        // add configuration where cop is in vertex 1 and robber on 2
        marked.add(new Pair<>(Vertex.create(1), Vertex.create(2)));
        // WHEN
        boolean result = helper.isAllRobbersNeighbourAndAnyCopIsMarked(robberNeighbourhood, copNeighbourhood, marked);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRobberWithFreeNeighbourAndCopBeforeHim_whenIsAllRobbersNeighbourAndAnyCopIsMarked_thenReturnFalse()
            throws EdgeAlreadyExistException, PathContainsDuplicates {

        // GIVEN
        // Easy to show, that robber has no free vertex to move, and will be caught
        graph.addPath(0, 1, 2, 3);
        graph.addEdge(0, 3);
        Set<Vertex<Integer>> vertices = graph.getVertices();
        Vertex<Integer> robberPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        Vertex<Integer> copPosition = vertices.stream().filter(v -> v.getIndex().equals(1)).findFirst().get();

        Collection<Vertex<Integer>> robberNeighbourhood = robberPosition.getClosedNeighbourhood();
        Collection<Vertex<Integer>> copNeighbourhood = copPosition.getClosedNeighbourhood();
        Set<Pair<Vertex<Integer>, Vertex<Integer>>> marked = helper.createMarkedConfigurations(vertices);
        // WHEN
        boolean result = helper.isAllRobbersNeighbourAndAnyCopIsMarked(robberNeighbourhood, copNeighbourhood, marked);
        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenGraphVertices_whenCreateMarkedConfigurations_thenSuccessMatchEachWithEachFromVertices() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5);
        Set<Vertex<Integer>> vertices = graph.getVertices();

        // WHEN
        Set<Pair<Vertex<Integer>, Vertex<Integer>>> marked = helper.createMarkedConfigurations(vertices);

        // THEN
        assertThat(marked).isNotNull();
        assertThat(marked.size()).isEqualTo(vertices.size());
        marked.forEach(pair -> assertThat(pair.getFirst()).isEqualTo(pair.getSecond()));
    }

    @Test
    public void givenMarkedCollection_whenCreateUnmarkedConfigurations_thenSuccessMatchEachWithEachFromMarked() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5);
        Set<Pair<Vertex<Integer>, Vertex<Integer>>> marked = helper.createMarkedConfigurations(graph.getVertices());

        // WHEN
        List<Pair<Vertex<Integer>, Vertex<Integer>>> unmarked = helper.createUnmarkedConfigurations(marked);
        int expectedUnmarkedSize = ((int)Math.pow(graph.getVertices().size(), 2)) - marked.size();

        // THEN
        assertThat(unmarked).isNotNull();
        assertThat(unmarked.size()).isEqualTo(expectedUnmarkedSize);
        unmarked.forEach(pair -> assertThat(pair.getFirst()).isNotEqualTo(pair.getSecond()));
    }
}