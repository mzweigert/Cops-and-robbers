package com.copsandrobber.algorithm;


import com.copsandrobber.helper.AlgorithmHelper;
import com.graphrodite.model.Edge;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;
import com.graphrodite.factory.GraphProductFactory;

import java.util.*;

public class Algorithm {

    private AlgorithmHelper helper = new AlgorithmHelper();
    private GraphProductFactory productFactory = new GraphProductFactory();

    public <E> boolean isOneCopEnough(Graph<E> graph) {
        List<Vertex<E>> vertices = graph.getVertices();
        for (Iterator<Vertex<E>> iterator = vertices.iterator(); iterator.hasNext(); ) {
            Vertex<E> vertex = iterator.next();
            for (Vertex<E> neighbor : vertex.getClosedNeighbourhood()) {
                if (helper.isFirstDominatingSecond(neighbor, vertex) && !vertex.equals(neighbor)) {
                    vertices.forEach(v -> v.removeNeighbourhood(vertex));
                    iterator.remove();
                    break;
                }
            }
        }
        return vertices.size() == 1;
    }

    public <E> boolean isKCopEnough(Graph<E> graph, int k) {
        Graph<E> resultGraph = graph.clone();
        for (int i = 1; i < k; i++) {
            //noinspection unchecked
            resultGraph = productFactory.createStrongProduct(resultGraph.clone(), graph.clone());
        }
        Map<Vertex<E>, Set<Vertex<E>>> safeZonesForVertices = helper.findSafeZonesForVertices(resultGraph.getVertices(), graph);
        Map<Vertex<E>, Integer> safeZonesCountForVertices;

        do {
            safeZonesCountForVertices = helper.createSafeZonesCountMap(safeZonesForVertices);
            for (Edge<E> edge : resultGraph.getEdges()) {
                Set<Vertex<E>> safeZoneForU = safeZonesForVertices.get(edge.getFirst());
                Set<Vertex<E>> safeZoneForV = safeZonesForVertices.get(edge.getSecond());
                Set<Vertex<E>> SafeZonesNeighboursForV = helper.getClosedNeighbourhoodUnion(safeZoneForV);
                safeZoneForU.retainAll(SafeZonesNeighboursForV);

                Set<Vertex<E>> SafeZonesNeighboursForU = helper.getClosedNeighbourhoodUnion(safeZoneForU);
                safeZoneForV.retainAll(SafeZonesNeighboursForU);

                if (safeZoneForU.isEmpty() || safeZoneForV.isEmpty()) {
                    return true;
                }

                safeZonesForVertices.replace(edge.getFirst(), safeZoneForU);
                safeZonesForVertices.replace(edge.getSecond(), safeZoneForV);
            }
        } while (helper.safeZoneChanged(safeZonesForVertices, safeZonesCountForVertices));

        return safeZonesForVertices.values().stream()
                .anyMatch(Set::isEmpty);
    }
}
