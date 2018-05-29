package com.copsandrobber.algorithm.k_cops_enough.strategy;

import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.model.Edge;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;

import java.util.Map;
import java.util.Set;

public class IterateOnEdgesStrategy implements KCopsEnoughStrategy {

    private GraphProductFactory productFactory;
    private static KCopsEnoughStrategy instance;

    private IterateOnEdgesStrategy() {
        productFactory = new GraphProductFactory();
    }

    public static KCopsEnoughStrategy get() {
        if (instance == null) {
            instance = new IterateOnEdgesStrategy();
        }
        return instance;
    }

    @Override
    public <E> boolean calculate(Graph<E> graph, int k) {
        //noinspection unchecked
        Graph<E> kStrongProduct = productFactory.createKStrongProduct(graph, k);

        Map<Vertex<E>, Set<Vertex<E>>> safeZonesForVertices = helper.findSafeZonesForVertices(kStrongProduct.getVertices(), graph);
        boolean safeZoneUChanged = false, safeZoneVChanged = false;
        Set<Edge<E>> edges = kStrongProduct.getEdges();
        do {
            for (Edge<E> edge : edges) {
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
