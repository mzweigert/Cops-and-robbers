package com.copsandrobber.algorithm.one_cop_enough;

import com.copsandrobber.algorithm.common.ConfigurationsWrapper;
import com.copsandrobber.algorithm.one_cop_enough.helper.PairConfigurationWrapper;
import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.IndexesContainsDuplicatesException;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class PairConfigurationWrapperTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = Graph.newInstance();
    }

    @Test
    public void givenGraphVertices_whenCreateConfigurations_thenSuccessMatchEachWithEachFromVertices() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5);
        Set<Vertex<Integer>> vertices = graph.getVertices();

        // WHEN
        ConfigurationsWrapper<Pair<Vertex<Integer>, Vertex<Integer>>, Integer> configurations = PairConfigurationWrapper.create(vertices);
        int expectedUnmarkedSize = ((int)Math.pow(graph.getVertices().size(), 2)) - configurations.getMarkedSize();

        // THEN
        assertThat(configurations).isNotNull();
        assertThat(configurations.getMarked()).isNotNull();
        assertThat(configurations.getUnmarked()).isNotNull();

        assertThat(configurations.getMarkedSize()).isEqualTo(vertices.size());
        assertThat(configurations.getUnmarkedSize()).isEqualTo(expectedUnmarkedSize);

        configurations.getMarked().forEach(pair -> assertThat(pair.getFirst()).isEqualTo(pair.getSecond()));
        configurations.getUnmarked().forEach(pair -> assertThat(pair.getFirst()).isNotEqualTo(pair.getSecond()));
    }


    @Test
    public void givenRobberInTrapAndCopBeforeHim_whenIsAllRobbersNeighbourAndAnyCopIsMarked_thenReturnTrue()
            throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {

        // GIVEN
        // Easy to show, that robber has no free vertex to move, and will be caught
        graph.addPath(0, 1, 2);
        Set<Vertex<Integer>> vertices = graph.getVertices();
        Vertex<Integer> robberPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        Vertex<Integer> copPosition = vertices.stream().filter(v -> v.getIndex().equals(1)).findFirst().get();
        Pair<Vertex<Integer>, Vertex<Integer>> configuration = new Pair<>(copPosition, robberPosition);
        ConfigurationsWrapper<Pair<Vertex<Integer>, Vertex<Integer>>, Integer> configurations =
                PairConfigurationWrapper.create(vertices);
        // WHEN
        boolean result = configurations.canMarkConfiguration(configuration);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRobberWithFreeButMarkedNeighbourAndCop_whenIsAllRobbersNeighbourAndAnyCopIsMarked_thenReturnTrue()
            throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {

        // GIVEN
        // Easy to show, that robber has no free vertex to move, and will be caught
        graph.addPath(0, 1, 2);
        Set<Vertex<Integer>> vertices = graph.getVertices();
        Vertex<Integer> robberPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        Vertex<Integer> copPosition = vertices.stream().filter(v -> v.getIndex().equals(0)).findFirst().get();
        Pair<Vertex<Integer>, Vertex<Integer>> configuration = new Pair<>(copPosition, robberPosition);
        ConfigurationsWrapper<Pair<Vertex<Integer>, Vertex<Integer>>, Integer> configurations =
                PairConfigurationWrapper.create(vertices);
        // add configuration where cop is in vertex 1 and robber on 2
        configurations.addToMarked(new Pair<>(Vertex.create(1), Vertex.create(2)));
        // WHEN
        boolean result = configurations.canMarkConfiguration(configuration);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRobberWithFreeNeighbourAndCopBeforeHim_whenIsAllRobbersNeighbourAndAnyCopIsMarked_thenReturnFalse()
            throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {

        // GIVEN
        // Easy to show, that robber has no free vertex to move, and will be caught
        graph.addPath(0, 1, 2, 3);
        graph.addEdge(0, 3);
        Set<Vertex<Integer>> vertices = graph.getVertices();
        Vertex<Integer> robberPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        Vertex<Integer> copPosition = vertices.stream().filter(v -> v.getIndex().equals(1)).findFirst().get();
        Pair<Vertex<Integer>, Vertex<Integer>> configuration = new Pair<>(copPosition, robberPosition);
        ConfigurationsWrapper<Pair<Vertex<Integer>, Vertex<Integer>>, Integer> configurations =
                PairConfigurationWrapper.create(vertices);

        // WHEN
        boolean result = configurations.canMarkConfiguration(configuration);
        // THEN
        assertThat(result).isFalse();
    }

}