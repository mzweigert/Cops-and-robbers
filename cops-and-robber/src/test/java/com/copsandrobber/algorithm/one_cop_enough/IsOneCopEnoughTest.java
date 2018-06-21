package com.copsandrobber.algorithm.one_cop_enough;

import com.graphrodite.factory.GraphTemplate;
import com.copsandrobber.algorithm.one_cop_enough.strategy.MarkConfigurationsStrategy;
import com.copsandrobber.algorithm.one_cop_enough.strategy.OneCopEnoughStrategy;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;
import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.IndexesContainsDuplicatesException;
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
        graph.addCycle(1, 2, 3);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }


    @Test
    public void givenMarkConfigurationsStrategyAndC4Graph_whenIsOneCopEnough_thenReturnFalse() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addCycle(1, 2, 3, 4);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndTreeGraph_whenIsOneCopEnough_thenReturnTrue() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph = GraphTemplate.getInstance().createTreeWithGivenLength(15);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndPathGraph_whenIsOneCopEnough_thenReturnTrue() throws Exception, IndexesContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addPath(1, 2, 3);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndCompleteGraph_whenIsOneCopEnough_thenReturnTrue() throws Exception, IndexesContainsDuplicatesException {
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
    public void givenMarkConfigurationsStrategyAndPetersenGraph_whenIsOneCopEnough_thenReturnTrue() {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph = GraphTemplate.getInstance().getPetersenGraph();

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndDodecahedronGraph_whenIsOneCopEnough_thenReturnTrue() {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph = GraphTemplate.getInstance().getDodecahedronGraph();

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndLexicographicalProductOfTwoCopWinGraphs_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
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
    public void givenMarkConfigurationsStrategyAndStrongProductOfTwoOneCopWinGraphs_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addPath(1, 2, 3, 4);
        Graph<Integer> H = Graph.newInstance();

        H.addPath(1, 2, 3, 4, 5);
        Graph<Pair<Integer, Integer>> strong = graphProductFactory.createStrongProduct(graph, H);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(strong);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenMarkConfigurationsStrategyAndSequentialGraph_whenIsOneCopEnough_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph = GraphTemplate.getInstance().createSequentialGraphWithDepth(25);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }
    @Test
    public void givenRemoveTrapsStrategyAndK3Graph_whenIsOneCopEnough_thenReturnTrue() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addCycle(1, 2, 3);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }


    @Test
    public void givenRemoveTrapsStrategyAndC4Graph_whenIsOneCopEnough_thenReturnFalse() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addCycle(1, 2, 3, 4);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenRemoveTrapsStrategyAndTreeGraph_whenIsOneCopEnough_thenReturnTrue() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph = GraphTemplate.getInstance().createTreeWithGivenLength(15);


        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRemoveTrapsStrategyAndPathGraph_whenIsOneCopEnough_thenReturnTrue() throws Exception {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addPath(1, 2, 3, 4, 5);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRemoveTrapsStrategyAndCompleteGraph_whenIsOneCopEnough_thenReturnTrue() throws Exception, IndexesContainsDuplicatesException {
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
        graph = GraphTemplate.getInstance().getPetersenGraph();

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenRemoveTrapsStrategyAndDodecahedronGraph_whenIsOneCopEnough_thenReturnTrue() {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph = GraphTemplate.getInstance().getDodecahedronGraph();

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenRemoveTrapsStrategyAndLexicographicalProductOfTwoCopWinGraphs_whenIsOneCopEnough_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
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
    public void givenRemoveTrapsStrategyAndStrongProductOfTwoOneCopWinGraphs_whenIsOneCopEnough_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph.addPath(1, 2, 3, 4);
        Graph<Integer> H = Graph.newInstance();

        H.addPath(1, 2, 3, 4, 5);
        Graph<Pair<Integer, Integer>> strongProduct = graphProductFactory.createStrongProduct(graph, H);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(strongProduct);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenRemoveTrapsStrategyAndSequentialGraph_whenIsOneCopEnough_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        OneCopEnoughStrategy strategy = RemoveTrapsStrategy.get();
        graph = GraphTemplate.getInstance().createSequentialGraphWithDepth(25);

        // WHEN
        boolean result = IsOneCopEnough.setStrategy(strategy).calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }
}