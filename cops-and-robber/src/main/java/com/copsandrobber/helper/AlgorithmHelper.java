package com.copsandrobber.helper;

import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class AlgorithmHelper {

    public <E> Set<Vertex<E>> getClosedNeighbourhoodUnion(Set<Vertex<E>> vertices) {
        return vertices.stream()
                .map(Vertex::getClosedNeighbourhood)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public <E> boolean isFirstDominatingSecond(Vertex<E> first, Vertex<E> second) {
        Collection<Vertex<E>> firstClosedNeighbourhood = first.getClosedNeighbourhood();
        Collection<Vertex<E>> secondClosedNeighbourhood = second.getClosedNeighbourhood();
        return secondClosedNeighbourhood.size() <= firstClosedNeighbourhood.size() &&
                firstClosedNeighbourhood.containsAll(secondClosedNeighbourhood);

    }

    public <E> boolean safeZoneChanged(Map<Vertex<E>, Set<Vertex<E>>> first, Map<Vertex<E>, Integer> second) {
        return first.entrySet().stream()
                .anyMatch(entry -> second.get(entry.getKey()) != entry.getValue().size());
    }

    public <E> Map<Vertex<E>, Integer> createSafeZonesCountMap(Map<Vertex<E>, Set<Vertex<E>>> safeZonesForVertices) {
        return safeZonesForVertices.entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey(),
                e -> e.getValue().size()
        ));
    }

    public <E> Map<Vertex<E>, Set<Vertex<E>>> findSafeZonesForVertices(List<Vertex<E>> newVertices, Graph<E> originalGraph) {
        return newVertices.stream().collect(
                Collectors.toMap(
                        key -> key,
                        value -> findSafeZonesForVertex(value, originalGraph)
                )
        );
    }

    public <E> Set<Vertex<E>> findSafeZonesForVertex(Vertex<E> first, Graph<E> graph) {
        Set<Vertex<E>> extractedVerticesClosedNeighbourhood = extractVertices(first, graph).stream()
                .map(Vertex::getClosedNeighbourhood)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return graph.getVertices().stream()
                .filter(v -> !extractedVerticesClosedNeighbourhood.contains(v))
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
