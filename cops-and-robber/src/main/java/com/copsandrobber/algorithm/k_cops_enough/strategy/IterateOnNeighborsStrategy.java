package com.copsandrobber.algorithm.k_cops_enough.strategy;

import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Class with implementation of algorithm which checking if cops number is less or equal some k integer.
 * Strategy calculation based on iterate od neighbors of k-strong product vertices.
 * More information, description and pseudocode available in article
 * Cops and robbers from a distance. Bonato A., Chiniforooshan E., Prałat P.
 * Theoretical Computer Science 2010,
 * volume 411, pages 3834–3844.
 */
public class IterateOnNeighborsStrategy implements KCopsEnoughStrategy {

    private static KCopsEnoughStrategy instance;

    private IterateOnNeighborsStrategy() {

    }

    /**
     * Method return singleton of IterateOnNeighborsStrategy class
     * @return KCopsEnoughStrategy instance of IterateOnNeighborsStrategy
     */
    public static KCopsEnoughStrategy get() {
        if (instance == null) {
            instance = new IterateOnNeighborsStrategy();
        }
        return instance;
    }

    @Override
    public <E> boolean calculate(Graph<E> graph, int k) {
        //noinspection unchecked
        Graph<E> kStrongProduct = factory.createKStrongProduct(graph, k);
        Set<Vertex<E>> kStrongProductVertices = kStrongProduct.getVertices();
        Queue<Vertex<E>> queue = new LinkedList<>(kStrongProductVertices);
        Map<Vertex<E>, Set<Vertex<E>>> safeZonesForVertices = helper.findSafeZonesForVertices(kStrongProductVertices, graph);

        while (!queue.isEmpty()) {
            Vertex<E> v = queue.poll();
            Set<Vertex<E>> safeZonesT = safeZonesForVertices.get(v);
            Set<Vertex<E>> safeZonesNeighborsOfU = helper.getClosedNeighborhoodUnion(safeZonesT);

            for (Vertex<E> u : v.getOpenNeighborhood()) {
                Set<Vertex<E>> safeZonesV = safeZonesForVertices.get(u);
                boolean safeZoneVChanged = safeZonesV.retainAll(safeZonesNeighborsOfU);
                if (safeZoneVChanged) {
                    safeZonesForVertices.replace(u, safeZonesV);
                    queue.offer(u);
                }
            }
        }
        return safeZonesForVertices.values().stream()
                .anyMatch(Set::isEmpty);
    }
}
