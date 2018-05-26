package com.copsandrobber.algorithm.one_cop_enough;

import com.copsandrobber.algorithm.one_cop_enough.strategy.MarkConfigurationsStrategy;
import com.copsandrobber.algorithm.one_cop_enough.strategy.OneCopEnoughStrategy;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;
import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicatesException;
import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class IsOneCopEnoughTest {

    private Graph<Integer> graph;
    private GraphProductFactory graphProductFactory = new GraphProductFactory();

    @Before
    public void setUp() {
        graph = Graph.newInstance();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndK3Graph_whenIsOneCopEnough_thenReturnTrue() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = MarkConfigurationsStrategy.get();
        graph.addEdgesToVertex(1, 2, 3);
        graph.addEdge(2, 3);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }


    @Test
    public void givenMarkConfigurationsStrategyAndC4Graph_whenIsOneCopEnough_thenReturnFalse() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = MarkConfigurationsStrategy.get();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndPathGraph_whenIsOneCopEnough_thenReturnTrue() throws Exception, PathContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = MarkConfigurationsStrategy.get();
        graph.addPath(1, 2, 3);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndCompleteGraph_whenIsOneCopEnough_thenReturnTrue() throws Exception, PathContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = MarkConfigurationsStrategy.get();
        graph.addPath(1, 2, 3, 4);
        graph.addEdgesToVertex(1, 4, 3);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndPetersenGraph_whenIsOneCopEnough_thenReturnTrue() {
        // GIVEN
        OneCopEnoughStrategy strategy = MarkConfigurationsStrategy.get();
        graph = Graph.petersen();

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndDodecahedronGraph_whenIsOneCopEnough_thenReturnTrue() {
        // GIVEN
        OneCopEnoughStrategy strategy = MarkConfigurationsStrategy.get();
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndLexicographicalProductOfTwoCopWinGraphs_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = MarkConfigurationsStrategy.get();
        graph.addPath(1, 2, 3, 4);
        Graph<Integer> H = Graph.newInstance();

        H.addPath(1, 2, 3, 4, 5);
        Graph<Pair<Integer, Integer>> lexicographicalProduct = graphProductFactory.createLexicographicalProduct(graph, H);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(lexicographicalProduct);

        // THEN
        assertThat(result).isTrue();
    }


    @Test
    public void givenMarkConfigurationsStrategyAndStrongProductOfTwoOneCopWinGraphs_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = MarkConfigurationsStrategy.get();
        graph.addPath(1, 2, 3, 4);
        Graph<Integer> H = Graph.newInstance();

        H.addPath(1, 2, 3, 4, 5);
        Graph<Pair<Integer, Integer>> lexicographicalProduct = graphProductFactory.createStrongProduct(graph, H);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(lexicographicalProduct);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndSequentialGraph_whenIsOneCopEnough_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = MarkConfigurationsStrategy.get();
        graph.addPath(1, 2, 3, 4, 5, 6);
        graph.addEdge(1, 6);


        graph.addEdgesToVertex(7, 1, 2, 3, 4, 5, 6);

        graph.addEdgesToVertex(8, 1, 2);
        graph.addEdgesToVertex(9, 2, 3);
        graph.addEdgesToVertex(10, 3, 4);
        graph.addEdgesToVertex(11, 4, 5);
        graph.addEdgesToVertex(12, 5, 6);
        graph.addEdgesToVertex(13, 6, 1);

        graph.addEdgesToVertex(14, 8, 2, 9);
        graph.addEdgesToVertex(15, 9, 3, 10);
        graph.addEdgesToVertex(16, 10, 4, 11);
        graph.addEdgesToVertex(17, 11, 5, 12);
        graph.addEdgesToVertex(18, 12, 6, 13);
        graph.addEdgesToVertex(19, 13, 1, 8);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }
    @Test
    public void givenRemoveTrapsStrategyAndK3Graph_whenIsOneCopEnough_thenReturnTrue() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addEdgesToVertex(1, 2, 3);
        graph.addEdge(2, 3);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }


    @Test
    public void givenRemoveTrapsStrategyAndC4Graph_whenIsOneCopEnough_thenReturnFalse() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenRemoveTrapsStrategyAndPathGraph_whenIsOneCopEnough_thenReturnTrue() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRemoveTrapsStrategyAndCompleteGraph_whenIsOneCopEnough_thenReturnTrue() throws Exception, PathContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addPath(1, 2, 3, 4);
        graph.addEdgesToVertex(1, 4, 3);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRemoveTrapsStrategyAndPetersenGraph_whenIsOneCopEnough_thenReturnTrue() {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph = Graph.petersen();

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenRemoveTrapsStrategyAndDodecahedronGraph_whenIsOneCopEnough_thenReturnTrue() {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenRemoveTrapsStrategyAndLexicographicalProductOfTwoCopWinGraphs_whenIsOneCopEnough_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addPath(1, 2, 3, 4);
        Graph<Integer> H = Graph.newInstance();

        H.addPath(1, 2, 3, 4, 5);
        Graph<Pair<Integer, Integer>> lexicographicalProduct = graphProductFactory.createLexicographicalProduct(graph, H);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(lexicographicalProduct);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRemoveTrapsStrategyAndStrongProductOfTwoOneCopWinGraphs_whenIsOneCopEnough_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addPath(1, 2, 3, 4);
        Graph<Integer> H = Graph.newInstance();

        H.addPath(1, 2, 3, 4, 5);
        Graph<Pair<Integer, Integer>> lexicographicalProduct = graphProductFactory.createStrongProduct(graph, H);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(lexicographicalProduct);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRemoveTrapsStrategyAndSequentialGraph_whenIsOneCopEnough_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addPath(1, 2, 3, 4, 5, 6);
        graph.addEdge(1, 6);

        graph.addEdgesToVertex(7, 1, 2, 3, 4, 5, 6);

        graph.addEdgesToVertex(8, 1, 2);
        graph.addEdgesToVertex(9, 2, 3);
        graph.addEdgesToVertex(10, 3, 4);
        graph.addEdgesToVertex(11, 4, 5);
        graph.addEdgesToVertex(12, 5, 6);
        graph.addEdgesToVertex(13, 6, 1);

        graph.addEdgesToVertex(14, 8, 2, 9);
        graph.addEdgesToVertex(15, 9, 3, 10);
        graph.addEdgesToVertex(16, 10, 4, 11);
        graph.addEdgesToVertex(17, 11, 5, 12);
        graph.addEdgesToVertex(18, 12, 6, 13);
        graph.addEdgesToVertex(19, 13, 1, 8);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }
}