package com.copsandrobber.algorithm;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.model.Graph;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class AlgorithmTest {

    private Graph<Integer> graph;

    @InjectMocks
    private Algorithm algorithm;

    @Before
    public void setUp() {
        graph = Graph.newInstance();
    }

    @Test
    public void givenK3Graph_whenIsCopWinGraph_thenReturnTrue() throws Exception {
        graph.addEdgesToVertex(1, 2, 3);
        graph.addEdge(2, 3);

        assertThat(algorithm.isOneCopEnough(graph)).isTrue();
    }


    @Test
    public void givenC4Graph_whenIsCopWinGraph_thenReturnFalse() throws Exception {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);

        assertThat(algorithm.isOneCopEnough(graph)).isFalse();
    }

    @Test
    public void givenPathGraph_whenIsCopWinGraph_thenReturnTrue() throws Exception {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        assertThat(algorithm.isOneCopEnough(graph)).isTrue();
    }

    @Test
    public void givenCompleteGraph_whenIsCopWinGraph_thenReturnFalse() throws Exception {
        graph.addPath(1, 2, 3, 4);

        assertThat(algorithm.isOneCopEnough(graph)).isTrue();
    }

    @Test
    public void givenPathGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException {
        int k = 1;
        for (int i=1; i <= 10; i+=1){
            graph.addPath(i, i + 1);

        }

        boolean kCopWinGraph = algorithm.isKCopEnough(graph, k);

        assertThat(kCopWinGraph).isTrue();
    }

    @Test
    public void givenC4GraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() throws EdgeAlreadyExistException {
        int k = 1;
        graph.addPath(1, 2, 3, 4);
        graph.addEdge(1, 4);

        boolean result = algorithm.isKCopEnough(graph, k);

        assertThat(result).isFalse();
    }

    @Test
    public void givenC4GraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException {
        int k = 2;
        graph.addPath(1, 2, 3, 4);
        graph.addEdge(1, 4);

        boolean result = algorithm.isKCopEnough(graph, k);

        assertThat(result).isTrue();
    }

    @Test
    public void givenPlanarGraphAndKEqualThree_whenIsKCopWinGraph_thenReturnTrue() throws EdgeAlreadyExistException {
        int k = 3;
        graph.addPath(0, 1, 2, 3, 4, 0);
        graph.addEdgesToVertex(5, 0, 7, 8);
        graph.addEdgesToVertex(6, 1, 8, 9);
        graph.addEdgesToVertex(7, 2, 9);
        graph.addEdgesToVertex(8, 3);
        graph.addEdgesToVertex(9, 4);

        boolean result = algorithm.isKCopEnough(graph, k);

        assertThat(result).isTrue();
    }

    @Test
    public void givenPlanarGraphAndKEqualTwo_whenIsKCopWinGraph_thenReturnFalse() throws EdgeAlreadyExistException {
        int k = 2;
        graph.addPath(1, 2, 3, 4, 5, 1);
        graph.addEdgesToVertex(6, 1, 8, 9);
        graph.addEdgesToVertex(7, 2, 9, 10);
        graph.addEdgesToVertex(8, 3, 10);
        graph.addEdgesToVertex(9, 4);
        graph.addEdgesToVertex(10, 5);

        boolean result = algorithm.isKCopEnough(graph, k);

        assertThat(result).isFalse();
    }

    @Test
    public void givenPlanarGraphAndKEqualOne_whenIsKCopWinGraph_thenReturnFalse() throws EdgeAlreadyExistException {
        int k = 1;
        graph.addPath(1, 2, 3, 4, 5, 1);
        graph.addEdgesToVertex(6, 1, 8, 9);
        graph.addEdgesToVertex(7, 2, 9, 10);
        graph.addEdgesToVertex(8, 3, 10);
        graph.addEdgesToVertex(9, 4);
        graph.addEdgesToVertex(10, 5);

        boolean result = algorithm.isKCopEnough(graph, k);

        assertThat(result).isFalse();
    }


}