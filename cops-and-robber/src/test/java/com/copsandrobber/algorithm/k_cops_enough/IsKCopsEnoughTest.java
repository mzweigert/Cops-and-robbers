package com.copsandrobber.algorithm.k_cops_enough;

import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnEdgesStrategy;
import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnNeighboursStrategy;
import com.copsandrobber.algorithm.k_cops_enough.strategy.KCopsEnoughStrategy;
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
    public void givenIterateOnEdgesStrategyAndPathGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 1;
        for (int i = 1; i < 100; i += 1) {
            graph.addPath(i, i + 1);
        }

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnEdgesStrategyAndC4GraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 1;
        graph.addPath(1, 2, 3, 4);
        graph.addEdge(1, 4);

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnEdgesStrategyAndC4GraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 2;
        graph.addPath(1, 2, 3, 4);
        graph.addEdge(1, 4);

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnEdgesStrategyAndDodecahedronGraphAndKEqualThree_whenIsKCopWinGraph_thenReturnTrue() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 3;
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnEdgesStrategyAndDodecahedronGraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 2;
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnEdgesStrategyAndDodecahedronGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 1;
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnEdgesStrategyAndPetersenGraphAndKEqualThree_whenIsKCopWinGraph_thenReturnTrue() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 3;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnEdgesStrategyAndPetersenGraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 2;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnEdgesStrategyAndPetersenGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 1;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnNeighboursStrategyAndPathGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighboursStrategy.get();
        int k = 1;
        for (int i = 1; i < 100; i += 1) {
            graph.addPath(i, i + 1);
        }

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnNeighboursStrategyAndC4GraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighboursStrategy.get();
        int k = 1;
        graph.addPath(1, 2, 3, 4);
        graph.addEdge(1, 4);

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnNeighboursStrategyAndC4GraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighboursStrategy.get();
        int k = 2;
        graph.addPath(1, 2, 3, 4);
        graph.addEdge(1, 4);

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnNeighboursStrategyAndDodecahedronGraphAndKEqualThree_whenIsKCopWinGraph_thenReturnTrue() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighboursStrategy.get();
        int k = 3;
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnNeighboursStrategyAndDodecahedronGraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighboursStrategy.get();
        int k = 2;
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnNeighboursStrategyAndDodecahedronGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighboursStrategy.get();
        int k = 1;
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnNeighboursStrategyAndPetersenGraphAndKEqualThree_whenIsKCopWinGraph_thenReturnTrue() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighboursStrategy.get();
        int k = 3;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnNeighboursStrategyAndPetersenGraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighboursStrategy.get();
        int k = 2;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnNeighboursStrategyAndPetersenGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighboursStrategy.get();
        int k = 1;
        graph = Graph.petersen();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

}