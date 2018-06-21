package com.graphrodite.factory;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.IndexesContainsDuplicatesException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.model.Graph;

public class GraphTemplate {

    private static GraphTemplate instance;

    private GraphTemplate() {

    }

    public static GraphTemplate getInstance() {
        if (instance == null) {
            instance = new GraphTemplate();
        }
        return instance;
    }

    /**
     * Method create sequential graph based on K3 with given depth.
     *
     * @param n depth size
     * @return Graph&lt;Integer&gt; new sequential graph with given depth.
     */
    public Graph<Integer> createSequentialGraphWithDepth(int n){
        Graph<Integer> sequentialGraph = Graph.newInstance();
        int j = 0, first = ++j, second = ++j, third = ++j;

        try {
            sequentialGraph.addCycle(first, second, third);
            for (int i =0; i<n; i++){
                sequentialGraph.addCycle(first, ++j, second, ++j, third, ++j);
                first +=3;
                second +=3;
                third +=3;
                if(i > 0){
                    sequentialGraph.addEdge(first, second - 6);
                    sequentialGraph.addEdge(second, third - 6);
                    sequentialGraph.addEdge(third, first - 6);
                }
            }

        } catch (IndexesContainsDuplicatesException | EdgeAlreadyExistException e) {
            e.printStackTrace();
        }
        return sequentialGraph;
    }

    /**
     * Method create path graph with given vertices size.
     *
     * @param n vertices size
     * @return Graph&lt;Integer&gt; new path graph with given size.
     */
    public Graph<Integer> createPathWithGivenLength(int n) {
        Graph<Integer> PN = Graph.newInstance();
        try {
            for (int i = 1; i < n; i++) {
                PN.addEdge(i, i + 1);
            }
        } catch (EdgeAlreadyExistException e) {
            e.printStackTrace();
        }
        return PN;
    }

    /**
     * Method create tree graph with given vertices size.
     *
     * @param n vertices size
     * @return Graph&lt;Integer&gt; new tree graph with given size.
     */
    public Graph<Integer> createTreeWithGivenLength(int n) {
        Graph<Integer> TN = Graph.newInstance();
        try {
            int j = 1, i,
            deepSize = (int) Math.floor((double) n / 2);
            for (i = 1; i <= deepSize; i++) {
                if(i == deepSize && n % 2 == 0) {
                    TN.addEdge(i, ++j);
                } else {
                    TN.addEdgesToVertex(i, ++j, ++j);
                }
            }

        } catch (EdgeAlreadyExistException e) {
            e.printStackTrace();
        }
        return TN;
    }

    /**
     * Method create cycle graph with given vertices size.
     *
     * @param n vertices size
     * @return Graph&lt;Integer&gt; new cycle graph with given size.
     */
    public Graph<Integer> createCycleWithGivenLength(int n) {
        Graph<Integer> CN = Graph.newInstance();
        try {
            for (int i = 1; i < n; i++) {
                CN.addEdge(i, i + 1);
            }
            CN.addEdge(1, n);
        } catch (EdgeAlreadyExistException e) {
            e.printStackTrace();
        }
        return CN;
    }

    /**
     * Method create petersen graph with vertices type integer.
     *
     * @return Graph&lt;Integer&gt; new petersen graph.
     */
    public Graph<Integer> getPetersenGraph() {
        Graph<Integer> petersen = Graph.newInstance();
        try {
            petersen.addPath(0, 1, 2, 3, 4);
            petersen.addPath(0, 4);
            petersen.addEdgesToVertex(5, 0, 7, 8);
            petersen.addEdgesToVertex(6, 1, 8, 9);
            petersen.addEdgesToVertex(7, 2, 9);
            petersen.addEdgesToVertex(8, 3);
            petersen.addEdgesToVertex(9, 4);
        } catch (EdgeAlreadyExistException | IndexesContainsDuplicatesException e) {
            e.printStackTrace();
        }
        return petersen;
    }

    /**
     * Method create dodecahedron graph with vertices type integer.
     *
     * @return Graph&lt;Integer&gt; new dodecahedron graph.
     */
    public Graph<Integer> getDodecahedronGraph() {
        Graph<Integer> dodecahedron = Graph.newInstance();
        try {
            dodecahedron.addCycle(1, 2, 3, 4, 5);

            dodecahedron.addCycle(6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

            dodecahedron.addCycle(16, 17, 18, 19, 20);

            dodecahedron.addEdge(1, 6);
            dodecahedron.addEdge(2, 8);
            dodecahedron.addEdge(3, 10);
            dodecahedron.addEdge(4, 12);
            dodecahedron.addEdge(5, 14);

            dodecahedron.addEdge(7, 17);
            dodecahedron.addEdge(9, 18);
            dodecahedron.addEdge(11, 19);
            dodecahedron.addEdge(13, 20);
            dodecahedron.addEdge(15, 16);

        } catch (EdgeAlreadyExistException | IndexesContainsDuplicatesException e) {
            e.printStackTrace();
        }
        return dodecahedron;
    }

}
