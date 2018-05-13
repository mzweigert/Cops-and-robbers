package com.copsandrobber.algorithm.k_cop_enough;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicates;
import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.model.Graph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IsKCopEnoughTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = Graph.newInstance();
    }

    @Test
    public void givenPathGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        int k = 1;
        for (int i = 1; i <= 100; i += 1) {
            graph.addPath(i, i + 1);
        }

        // WHEN
        boolean kCopWinGraph = IsKCopEnough.evaluate(graph, k);

        // THEN
        assertThat(kCopWinGraph).isTrue();
    }

    @Test
    public void givenC4GraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        int k = 1;
        graph.addPath(1, 2, 3, 4);
        graph.addEdge(1, 4);

        // WHEN
        boolean result = IsKCopEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenC4GraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        int k = 2;
        graph.addPath(1, 2, 3, 4);
        graph.addEdge(1, 4);

        // WHEN
        boolean result = IsKCopEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenPlanarGraphAndKEqualThree_whenIsKCopWinGraph_thenReturnTrue() {
        // GIVEN
        int k = 3;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenPlanarGraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        int k = 2;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenPlanarGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        int k = 1;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

}