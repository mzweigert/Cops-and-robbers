package com.copsandrobber.algorithm.two_cops_enough;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicatesException;
import com.graphrodite.factory.GraphProductFactory;
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
        graph.addEdgesToVertex(1, 2, 3);
        graph.addEdge(2, 3);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }


    @Test
    public void givenC4Graph_whenIsTwoCopsEnough_thenReturnFalse() throws Exception {
        // GIVEN
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenPathGraph_whenIsTwoCopsEnough_thenReturnTrue() throws Exception, PathContainsDuplicatesException {
        // GIVEN
        graph.addPath(1, 2, 3);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }


    @Test
    public void givenCycleGraph_whenIsTwoCopsEnough_thenReturnTrue() throws Exception, PathContainsDuplicatesException {
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
    public void givenCompleteGraph_whenIsTwoCopsEnough_thenReturnTrue() throws Exception, PathContainsDuplicatesException {
        // GIVEN
        graph.addPath(1, 2, 3, 4);
        graph.addEdgesToVertex(1, 4, 3);

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenPetersenGraph_whenIsTwoCopsEnough_thenReturnTrue() {
        // GIVEN
        graph = Graph.petersen();

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenDodecahedronGraph_whenIsTwoCopsEnough_thenReturnTrue() {
        // GIVEN
        graph = Graph.dodecahedron();

        // WHEN
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenLexicographicalProductOfTwoCopWinGraphs_whenIsTwoCopsEnough_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
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
    public void givenSequentialGraph_whenIsTwoCopsEnough_thenReturnTrue() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
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
        boolean result = IsTwoCopsEnough.calculate(graph);

        // THEN
        assertThat(result).isTrue();
    }
}