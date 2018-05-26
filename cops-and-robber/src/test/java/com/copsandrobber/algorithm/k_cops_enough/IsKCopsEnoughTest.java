package com.copsandrobber.algorithm.k_cops_enough;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicatesException;
import com.graphrodite.model.Graph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IsKCopsEnoughTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = Graph.newInstance();
    }

    @Test
    public void givenPathGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        int k = 1;
        for (int i = 1; i < 100; i += 1) {
            graph.addPath(i, i + 1);
        }

        // WHEN
        boolean kCopWinGraph = IsKCopsEnough.evaluate(graph, k);

        // THEN
        assertThat(kCopWinGraph).isTrue();
    }

    @Test
    public void givenC4GraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        int k = 1;
        graph.addPath(1, 2, 3, 4);
        graph.addEdge(1, 4);

        // WHEN
        boolean result = IsKCopsEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenC4GraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        int k = 2;
        graph.addPath(1, 2, 3, 4);
        graph.addEdge(1, 4);

        // WHEN
        boolean result = IsKCopsEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }
    @Test
    public void givenDodecahedronGraphAndKEqualThree_whenIsKCopWinGraph_thenReturnTrue() {
        // GIVEN
        int k = 3;
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsKCopsEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenDodecahedronGraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        int k = 2;
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsKCopsEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenDodecahedronGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        int k = 1;
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsKCopsEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }
    @Test
    public void givenPetersenGraphAndKEqualThree_whenIsKCopWinGraph_thenReturnTrue() {
        // GIVEN
        int k = 3;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopsEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenPetersenGraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        int k = 2;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopsEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenPetersenGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        int k = 1;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopsEnough.evaluate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

}