package com.copsandrobber.algorithm;

import com.graphrodite.model.Graph;

import org.junit.Assert;
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

        assertThat(algorithm.isCopWinGraph(graph)).isTrue();
    }


    @Test
    public void givenC4Graph_whenIsCopWinGraph_thenReturnFalse() throws Exception {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);

        assertThat(algorithm.isCopWinGraph(graph)).isFalse();
    }

    @Test
    public void givenPathGraph_whenIsCopWinGraph_thenReturnTrue() throws Exception {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        assertThat(algorithm.isCopWinGraph(graph)).isTrue();
    }
    @Test
    public void givenCompleteGraph_whenIsCopWinGraph_thenReturnFalse() throws Exception {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);
        graph.addEdge(2, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 3);
        graph.addEdge(3, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 6);
        graph.addEdge(7, 9);
        graph.addEdge(9, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 6);
        graph.addEdge(5, 3);
        graph.addEdge(3, 8);
        graph.addEdge(6, 7);
        graph.addEdge(3, 9);
        graph.addEdge(1, 8);
        assertThat(algorithm.isCopWinGraph(graph)).isTrue();
    }

}