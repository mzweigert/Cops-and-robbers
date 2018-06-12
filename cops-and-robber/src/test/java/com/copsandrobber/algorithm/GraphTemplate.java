package com.copsandrobber.algorithm;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicatesException;
import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.model.Graph;

public class GraphTemplate {

    private static GraphProductFactory graphProductFactory = new GraphProductFactory();

    public static Graph<Integer> getSequentialGraph() {
        Graph<Integer> sequentialGraph = Graph.newInstance();
        try {
            sequentialGraph.addPath(1, 2, 3, 4, 5, 6);
            sequentialGraph.addEdge(1, 6);

            sequentialGraph.addEdgesToVertex(7, 1, 2, 3, 4, 5, 6);

            sequentialGraph.addEdgesToVertex(8, 1, 2);
            sequentialGraph.addEdgesToVertex(9, 2, 3);
            sequentialGraph.addEdgesToVertex(10, 3, 4);
            sequentialGraph.addEdgesToVertex(11, 4, 5);
            sequentialGraph.addEdgesToVertex(12, 5, 6);
            sequentialGraph.addEdgesToVertex(13, 6, 1);

            sequentialGraph.addEdgesToVertex(14, 8, 2, 9);
            sequentialGraph.addEdgesToVertex(15, 9, 3, 10);
            sequentialGraph.addEdgesToVertex(16, 10, 4, 11);
            sequentialGraph.addEdgesToVertex(17, 11, 5, 12);
            sequentialGraph.addEdgesToVertex(18, 12, 6, 13);
            sequentialGraph.addEdgesToVertex(19, 13, 1, 8);
        } catch (PathContainsDuplicatesException | EdgeAlreadyExistException e) {
            e.printStackTrace();
        }
        return sequentialGraph;
    }

    public static Graph<Integer> getSelfStrongProductOfPNGraph(int n) {
        Graph<Integer> PN = Graph.newInstance();
        try {
            for (int i=1; i<n; i++){
                PN.addEdge(i, i+1);
            }
        } catch (EdgeAlreadyExistException e) {
            e.printStackTrace();
        }
        return graphProductFactory.createStrongProduct(PN, PN);
    }

    public static Graph<Integer> getSelfCartesianProductOfCNGraph(int n) {
        Graph<Integer> CN = Graph.newInstance();
        try {
            for (int i=1; i<n; i++){
                 CN.addEdge(i, i+1);
            }
            CN.addEdge(1, n);
        } catch (EdgeAlreadyExistException e) {
            e.printStackTrace();
        }
        return graphProductFactory.createCartesianProduct(CN, CN);
    }

    public static Graph<Integer> getSelfLexicographicalProductOfTNGraph(int n) {
        Graph<Integer> TN = Graph.newInstance();
        try {
            int j = 1;
            n = (int) Math.floor((double) n/2 ) + 1;
            for (int i=1; i < n; i++){
                TN.addEdgesToVertex(i, ++j, ++j);
            }

        } catch (EdgeAlreadyExistException e) {
            e.printStackTrace();
        }
        return graphProductFactory.createLexicographicalProduct(TN, TN);
    }

    public static Graph<Integer> getC10Graph(){
        Graph<Integer> c10 = Graph.newInstance();
        try {
            c10.addPath(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            c10.addEdge(1, 10);
        } catch (EdgeAlreadyExistException | PathContainsDuplicatesException e) {
            e.printStackTrace();
        }
        return c10;
    }
}
