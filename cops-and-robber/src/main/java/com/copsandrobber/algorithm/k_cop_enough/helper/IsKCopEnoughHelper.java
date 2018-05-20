package com.copsandrobber.algorithm.k_cop_enough.helper;

import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class IsKCopEnoughHelper {

    public <E> Set<Vertex<E>> getClosedNeighbourhoodUnion(Set<Vertex<E>> vertices) {
        return vertices.stream()
                .map(Vertex::getClosedNeighbourhood)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }


    public <E> Map<Vertex<E>, Set<Vertex<E>>> findSafeZonesForVertices(Set<Vertex<E>> newVertices, Graph<E> originalGraph) {
        return newVertices.stream().collect(
                Collectors.toMap(
                        key -> key,
                        value -> findSafeZonesForVertex(value, originalGraph)
                )
        );
    }

    public <E> Set<Vertex<E>> findSafeZonesForVertex(Vertex<E> first, Graph<E> graph) {
        Collection<Vertex<E>> vertices = graph.findVertex(first.getIndex())
                .map(Vertex::getClosedNeighbourhood)
                .orElse(extractVertices(first, graph).stream()
                        .map(Vertex::getClosedNeighbourhood)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet()));


        return graph.getVertices().stream()
                .filter(v -> !vertices.contains(v))
                .collect(Collectors.toSet());
    }

    public <T> Set<Vertex<T>> extractVertices(Vertex<?> vertex, Graph<T> fromGraph) {
        Set<Vertex<T>> set = new HashSet<>();
        Object first, second;
        first = vertex.getIndex();
        while (Pair.class.isInstance(first)) {
            Pair pair = (Pair) first;
            first = pair.getFirst();
            second = pair.getSecond();
            fromGraph.findVertex((T) second).ifPresent(set::add);
        }
        fromGraph.findVertex((T) first).ifPresent(set::add);
        return set;
    }
}
