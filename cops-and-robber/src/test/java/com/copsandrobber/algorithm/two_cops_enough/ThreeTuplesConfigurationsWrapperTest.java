package com.copsandrobber.algorithm.two_cops_enough;

import com.copsandrobber.algorithm.common.ConfigurationsWrapper;
import com.copsandrobber.algorithm.two_cops_enough.helper.ThreeTuples;
import com.copsandrobber.algorithm.two_cops_enough.helper.ThreeTuplesConfigurationsWrapper;
import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicatesException;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ThreeTuplesConfigurationsWrapperTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = Graph.newInstance();
    }

    @Test
    public void givenGraphVertices_whenCreateConfigurations_thenSuccessMatchEachWithEachFromVertices() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        graph.addPath(1, 2, 3, 4);
        Set<Vertex<Integer>> vertices = graph.getVertices();

        // WHEN
        ConfigurationsWrapper<ThreeTuples<Vertex<Integer>>, Integer> configurations = ThreeTuplesConfigurationsWrapper.create(vertices);
        int expectedUnmarkedSize = ((int) Math.pow(graph.getVertices().size(), 3)) - configurations.getMarkedSize();

        // THEN
        assertThat(configurations).isNotNull();
        assertThat(configurations.getMarked()).isNotNull();
        assertThat(configurations.getUnmarked()).isNotNull();

        assertThat(configurations.getUnmarkedSize()).isEqualTo(expectedUnmarkedSize);

        configurations.getMarked().forEach(threeTuples -> {
            boolean equal = threeTuples.getSecond().equals(threeTuples.getThird()) ||
                    threeTuples.getFirst().equals(threeTuples.getThird());
            assertThat(equal).isTrue();

        });
        configurations.getUnmarked().forEach(threeTuples -> {
            boolean equal = threeTuples.getSecond().equals(threeTuples.getThird()) ||
                    threeTuples.getFirst().equals(threeTuples.getThird());
            assertThat(equal).isFalse();

        });
    }


    @Test
    public void givenRobberSurroundedCops_whenCanMarkConfiguration_thenReturnTrue()
            throws EdgeAlreadyExistException, PathContainsDuplicatesException {

        // GIVEN
        graph.addPath(0, 1, 2, 3);
        graph.addEdge(0, 3);

        Set<Vertex<Integer>> vertices = graph.getVertices();
        Vertex<Integer> robberPosition = vertices.stream().filter(v -> v.getIndex().equals(3)).findFirst().get();
        Vertex<Integer> secondCopPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        Vertex<Integer> firstCopPosition = vertices.stream().filter(v -> v.getIndex().equals(0)).findFirst().get();
        ThreeTuples<Vertex<Integer>> configuration = new ThreeTuples<>(firstCopPosition, secondCopPosition, robberPosition);
        ConfigurationsWrapper<ThreeTuples<Vertex<Integer>>, Integer> configurations =
                ThreeTuplesConfigurationsWrapper.create(vertices);

        // WHEN
        boolean result = configurations.canMarkConfiguration(configuration);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRobberWithFreeButMarkedNeighbourAndCops_whenCanMarkConfiguration_thenReturnTrue()
            throws EdgeAlreadyExistException, PathContainsDuplicatesException {

        // GIVEN
        graph.addPath(0, 1, 2, 3);
        graph.addEdge(0, 3);

        Set<Vertex<Integer>> vertices = graph.getVertices();
        Vertex<Integer> robberPosition = vertices.stream().filter(v -> v.getIndex().equals(3)).findFirst().get();
        Vertex<Integer> secondCopPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        Vertex<Integer> firstCopPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        ThreeTuples<Vertex<Integer>> configuration = new ThreeTuples<>(firstCopPosition, secondCopPosition, robberPosition);
        ConfigurationsWrapper<ThreeTuples<Vertex<Integer>>, Integer> configurations =
                ThreeTuplesConfigurationsWrapper.create(vertices);

        // add configuration where one cop is on vertex 1, second cop in 3 and robber in 2
        configurations.addToMarked(new ThreeTuples<>(Vertex.create(1), Vertex.create(3), Vertex.create(2)));
        // add configuration where one cop is on vertex 1, second cop in 3 and robber in 0
        configurations.addToMarked(new ThreeTuples<>(Vertex.create(1), Vertex.create(3), Vertex.create(0)));

        // WHEN
        boolean result = configurations.canMarkConfiguration(configuration);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRobberWithFreeNeighbourAndCopsBeforeHim_whenCanMarkConfiguration_thenReturnFalse()
            throws EdgeAlreadyExistException, PathContainsDuplicatesException {

        // GIVEN
        graph.addPath(0, 1, 2, 3);
        graph.addEdge(0, 3);
        Set<Vertex<Integer>> vertices = graph.getVertices();
        Vertex<Integer> robberPosition = vertices.stream().filter(v -> v.getIndex().equals(3)).findFirst().get();
        Vertex<Integer> secondCopPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        Vertex<Integer> firstCopPosition = vertices.stream().filter(v -> v.getIndex().equals(2)).findFirst().get();
        ThreeTuples<Vertex<Integer>> configuration = new ThreeTuples<>(firstCopPosition, secondCopPosition, robberPosition);
        ConfigurationsWrapper<ThreeTuples<Vertex<Integer>>, Integer> configurations =
                ThreeTuplesConfigurationsWrapper.create(vertices);

        // WHEN
        boolean result = configurations.canMarkConfiguration(configuration);
        // THEN
        assertThat(result).isFalse();
    }

}