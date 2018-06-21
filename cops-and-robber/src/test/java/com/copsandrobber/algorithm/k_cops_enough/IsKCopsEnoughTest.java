package com.copsandrobber.algorithm.k_cops_enough;

import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnEdgesStrategy;
import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnNeighborsStrategy;
import com.copsandrobber.algorithm.k_cops_enough.strategy.KCopsEnoughStrategy;
import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.IndexesContainsDuplicatesException;
import com.graphrodite.factory.GraphTemplate;
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
    public void givenIterateOnEdgesStrategyAndPathGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
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
    public void givenIterateOnEdgesStrategyAndC4GraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 1;
        graph.addCycle(1, 2, 3, 4);

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnEdgesStrategyAndC4GraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnEdgesStrategy.get();
        int k = 2;
        graph.addCycle(1, 2, 3, 4);

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
        graph = GraphTemplate.getInstance().getDodecahedronGraph();

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
        graph = GraphTemplate.getInstance().getDodecahedronGraph();

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
        graph = GraphTemplate.getInstance().getDodecahedronGraph();

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
        graph = GraphTemplate.getInstance().getPetersenGraph();

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
        graph = GraphTemplate.getInstance().getPetersenGraph();

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
        graph = GraphTemplate.getInstance().getPetersenGraph();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnNeighborsStrategyAndPathGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighborsStrategy.get();
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
    public void givenIterateOnNeighborsStrategyAndC4GraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighborsStrategy.get();
        int k = 1;
        graph.addCycle(1, 2, 3, 4);

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnNeighborsStrategyAndC4GraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighborsStrategy.get();
        int k = 2;
        graph.addCycle(1, 2, 3, 4);

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnNeighborsStrategyAndDodecahedronGraphAndKEqualThree_whenIsKCopWinGraph_thenReturnTrue() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighborsStrategy.get();
        int k = 3;
        graph = GraphTemplate.getInstance().getDodecahedronGraph();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnNeighborsStrategyAndDodecahedronGraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighborsStrategy.get();
        int k = 2;
        graph = GraphTemplate.getInstance().getDodecahedronGraph();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnNeighborsStrategyAndDodecahedronGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighborsStrategy.get();
        int k = 1;
        graph = GraphTemplate.getInstance().getDodecahedronGraph();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnNeighborsStrategyAndPetersenGraphAndKEqualThree_whenIsKCopWinGraph_thenReturnTrue() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighborsStrategy.get();
        int k = 3;
        graph = GraphTemplate.getInstance().getPetersenGraph();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenIterateOnNeighborsStrategyAndPetersenGraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighborsStrategy.get();
        int k = 2;
        graph = GraphTemplate.getInstance().getPetersenGraph();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenIterateOnNeighborsStrategyAndPetersenGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() {
        // GIVEN
        KCopsEnoughStrategy strategy = IterateOnNeighborsStrategy.get();
        int k = 1;
        graph = GraphTemplate.getInstance().getPetersenGraph();

        // WHEN
        boolean result = IsKCopsEnough.setStrategy(strategy).calculate(graph, k);

        // THEN
        assertThat(result).isFalse();
    }

}