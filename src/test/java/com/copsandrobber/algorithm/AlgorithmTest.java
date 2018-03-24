package com.copsandrobber.algorithm;

import com.copsandrobber.model.Graph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

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
        graph.addEdge(1, 2, 3)
                .addEdge(2, 3);

        assertTrue(algorithm.isCopWinGraph(graph));
    }


    @Test
    public void givenC4Graph_whenIsCopWinGraph_thenReturnFalse() throws Exception {
        graph.addEdge(1, 2)
                .addEdge(2, 3)
                .addEdge(3, 4)
                .addEdge(4, 1);

        Assert.assertFalse(algorithm.isCopWinGraph(graph));
    }

    @Test
    public void givenPathGraph_whenIsCopWinGraph_thenReturnTrue() throws Exception {
        graph.addEdge(1, 2)
                .addEdge(2, 3)
                .addEdge(3, 4)
                .addEdge(4, 5);

        assertTrue(algorithm.isCopWinGraph(graph));
    }
    @Test
    public void givenCompleteGraph_whenIsCopWinGraph_thenReturnFalse() throws Exception {
        graph.addEdge(1, 2)
                .addEdge(2, 3)
                .addEdge(3, 4)
                .addEdge(4, 1)
                .addEdge(2, 5)
                .addEdge(5, 6)
                .addEdge(6, 3)
                .addEdge(3, 7)
                .addEdge(7, 8)
                .addEdge(8, 6)
                .addEdge(7, 9)
                .addEdge(9, 2)
                .addEdge(1, 3)
                .addEdge(2, 4)
                .addEdge(2, 6)
                .addEdge(5, 3)
                .addEdge(3, 8)
                .addEdge(6, 7)
                .addEdge(3, 9)
                .addEdge(1, 8);
        Assert.assertTrue(algorithm.isCopWinGraph(graph));
    }

}