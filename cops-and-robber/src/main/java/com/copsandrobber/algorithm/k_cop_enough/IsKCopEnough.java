package com.copsandrobber.algorithm.k_cop_enough;


import com.copsandrobber.algorithm.k_cop_enough.helper.IsKCopEnoughHelper;
import com.graphrodite.model.Edge;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;
import com.graphrodite.factory.GraphProductFactory;

import java.util.*;

public class IsKCopEnough {

    private IsKCopEnoughHelper helper = new IsKCopEnoughHelper();
    private GraphProductFactory productFactory = new GraphProductFactory();

    private IsKCopEnough() {

    }

    public static <E> boolean evaluate(Graph<E> graph, int k) {
        IsKCopEnough isKCopEnough = new IsKCopEnough();
        return isKCopEnough.calculate(graph, k);
    }

    private <E> boolean calculate(Graph<E> graph, int k) {
        Graph<E> resultGraph = graph.clone();
        for (int i = 1; i < k; i++) {
            //noinspection unchecked
            resultGraph = productFactory.createStrongProduct(resultGraph.clone(), graph.clone());
        }
        Map<Vertex<E>, Set<Vertex<E>>> safeZonesForVertices = helper.findSafeZonesForVertices(resultGraph.getVertices(), graph);
        boolean safeZoneUChanged = false, safeZoneVChanged = false;
        do {
            for (Edge<E> edge : resultGraph.getEdges()) {
                Set<Vertex<E>> safeZoneForU = safeZonesForVertices.get(edge.getFirst());
                Set<Vertex<E>> safeZoneForV = safeZonesForVertices.get(edge.getSecond());
                Set<Vertex<E>> SafeZonesNeighboursForV = helper.getClosedNeighbourhoodUnion(safeZoneForV);
                safeZoneUChanged = safeZoneForU.retainAll(SafeZonesNeighboursForV);

                Set<Vertex<E>> SafeZonesNeighboursForU = helper.getClosedNeighbourhoodUnion(safeZoneForU);
                safeZoneVChanged = safeZoneForV.retainAll(SafeZonesNeighboursForU);

                if (safeZoneForU.isEmpty() || safeZoneForV.isEmpty()) {
                    return true;
                }

                safeZonesForVertices.replace(edge.getFirst(), safeZoneForU);
                safeZonesForVertices.replace(edge.getSecond(), safeZoneForV);
            }
        } while (safeZoneUChanged || safeZoneVChanged);

        return safeZonesForVertices.values().stream()
                .anyMatch(Set::isEmpty);
    }
}
