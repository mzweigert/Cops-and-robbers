package com.copsandrobber.algorithm.two_cops_enough;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.IndexesContainsDuplicatesException;
import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.factory.GraphTemplate;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IsTwoCopsEnoughTest {

    private Graph<Integer> graph;
    private GraphProductFactory graphProductFactory = new GraphProductFactory();

    @Before
    public void setUp() {
        graph = Graph.newInstance();
    }

    @Test
    public void givenK3Graph_whenIsTwoCopsEnough_thenReturnTrue() throws Exception {
        // GIVEN
        graph.addCycle(1, 2, 3);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }


    @Test
    public void givenC4Graph_whenIsTwoCopsEnough_thenReturnTrue() throws Exception {
        // GIVEN
        graph.addCycle(1, 2, 3, 4);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenPathGraph_whenIsTwoCopsEnough_thenReturnTrue() throws Exception {
        // GIVEN
        graph.addPath(1, 2, 3);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }


    @Test
    public void givenCycleGraph_whenIsTwoCopsEnough_thenReturnTrue() throws Exception {
        // GIVEN
        for (int i = 1; i < 100; i += 1) {
            graph.addPath(i, i + 1);
        }
        graph.addPath(1, 100);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenCompleteGraph_whenIsTwoCopsEnough_thenReturnTrue() throws Exception {
        // GIVEN
        graph.addPath(1, 2, 3, 4);
        graph.addEdgesToVertex(1, 4, 3);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenPetersenGraph_whenIsTwoCopsEnough_thenReturnFalse() {
        // GIVEN
        graph = GraphTemplate.getInstance().getPetersenGraph();

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenDodecahedronGraph_whenIsTwoCopsEnough_thenReturnFalse() {
        // GIVEN
        graph = GraphTemplate.getInstance().getDodecahedronGraph();

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenLexicographicalProductOfTwoCopWinGraphs_whenIsTwoCopsEnough_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        graph.addPath(1, 2, 3, 4);
        Graph<Integer> H = Graph.newInstance();

        H.addPath(1, 2, 3, 4, 5);
        Graph<Pair<Integer, Integer>> lexicographicalProduct = graphProductFactory.createLexicographicalProduct(graph, H);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(lexicographicalProduct);

        // THEN
        assertThat(result).isTrue();
    }


    @Test
    public void givenSequentialGraph_whenIsTwoCopsEnough_thenReturnTrue() throws EdgeAlreadyExistException, IndexesContainsDuplicatesException {
        // GIVEN
        graph = GraphTemplate.getInstance().createSequentialGraphWithDepth(25);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }
}