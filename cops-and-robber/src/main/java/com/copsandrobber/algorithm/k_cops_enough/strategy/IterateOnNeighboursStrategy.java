package com.copsandrobber.algorithm.k_cops_enough.strategy;

import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class IterateOnNeighboursStrategy implements KCopsEnoughStrategy {

    private GraphProductFactory productFactory;
    private static KCopsEnoughStrategy instance;

    private IterateOnNeighboursStrategy() {
        productFactory = new GraphProductFactory();
    }

    public static KCopsEnoughStrategy get() {
        if (instance == null) {
            instance = new IterateOnNeighboursStrategy();
        }
        return instance;
    }

    @Override
    public <E> boolean calculate(Graph<E> graph, int k) {
        //noinspection unchecked
        Graph<E> kStrongProduct = productFactory.createKStrongProduct(graph, k);
        Set<Vertex<E>> kStrongProductVertices = kStrongProduct.getVertices();
        Queue<Vertex<E>> queue = new LinkedList<>(kStrongProductVertices);
        Map<Vertex<E>, Set<Vertex<E>>> safeZonesForVertices = helper.findSafeZonesForVertices(kStrongProductVertices, graph);

        while (!queue.isEmpty()) {
            Vertex<E> v = queue.poll();
            Set<Vertex<E>> safeZonesT = safeZonesForVertices.get(v);
            Set<Vertex<E>> safeZonesNeighboursOfU = helper.getClosedNeighbourhoodUnion(safeZonesT);

            for (Vertex<E> u : v.getOpenNeighbourhood()) {
                Set<Vertex<E>> safeZonesV = safeZonesForVertices.get(u);
                boolean safeZoneVChanged = safeZonesV.retainAll(safeZonesNeighboursOfU);
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
