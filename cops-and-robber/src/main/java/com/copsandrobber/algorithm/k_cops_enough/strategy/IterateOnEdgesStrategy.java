package com.copsandrobber.algorithm.k_cops_enough.strategy;

import com.graphrodite.model.Edge;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;

import java.util.Map;
import java.util.Set;

/**
 * Class with implementation of algorithm which checking if cops number is less or equal some k integer.
 * Strategy calculation based on iterate od edges k-strong product.
 * More information, description and pseudocode available in book
 * The game of cops and robbers on graphs. Bonato A., R. J. Nowakowski.
 * American Mathematical Society 2010,
 * page 122
 */
public class IterateOnEdgesStrategy implements KCopsEnoughStrategy {

    private static KCopsEnoughStrategy instance;

    private IterateOnEdgesStrategy() {

    }

    /**
     * Method return singleton of IterateOnEdgesStrategy class
     * @return KCopsEnoughStrategy instance of IterateOnEdgesStrategy
     */
    public static KCopsEnoughStrategy get() {
        if (instance == null) {
            instance = new IterateOnEdgesStrategy();
        }
        return instance;
    }

    @Override
    public <E> boolean calculate(Graph<E> graph, int k) {
        //noinspection unchecked
        Graph<E> kStrongProduct = factory.createKStrongProduct(graph, k);

        Map<Vertex<E>, Set<Vertex<E>>> safeZonesForVertices = helper.findSafeZonesForVertices(kStrongProduct.getVertices(), graph);
        boolean safeZoneUChanged = false, safeZoneVChanged = false;
        Set<Edge<E>> edges = kStrongProduct.getEdges();
        do {
            for (Edge<E> edge : edges) {
                Set<Vertex<E>> safeZoneForU = safeZonesForVertices.get(edge.getFirst());
                Set<Vertex<E>> safeZoneForV = safeZonesForVertices.get(edge.getSecond());
                Set<Vertex<E>> SafeZonesNeighborsForV = helper.getClosedNeighborhoodUnion(safeZoneForV);
                safeZoneUChanged |= safeZoneForU.retainAll(SafeZonesNeighborsForV);

                Set<Vertex<E>> SafeZonesNeighborsForU = helper.getClosedNeighborhoodUnion(safeZoneForU);
                safeZoneVChanged |= safeZoneForV.retainAll(SafeZonesNeighborsForU);

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
