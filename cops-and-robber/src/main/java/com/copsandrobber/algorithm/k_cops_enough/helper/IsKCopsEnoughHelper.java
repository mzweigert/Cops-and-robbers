package com.copsandrobber.algorithm.k_cops_enough.helper;

import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class IsKCopsEnoughHelper {

    /**
     * Method return closed neighborhood union of given as param vertices set.
     *
     * @param vertices vertices set
     * @return Set&lt;Vertex&lt;E&gt;&gt;
     */
    public <E> Set<Vertex<E>> getClosedNeighborhoodUnion(Set<Vertex<E>> vertices) {
        return vertices.stream()
                .map(Vertex::getClosedNeighborhood)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Method return safe zone for graph product vertices in factor graph.
     *
     * @param graphProductVertices vertices of graph product
     * @param factorGraph          factor of graph product
     * @return Map&lt;Vertex&lt;E&gt;, Set&lt;Vertex&lt;E&gt;&gt;&gt; map where key is graph product vertex and value is his safe zones.
     */
    public <E> Map<Vertex<E>, Set<Vertex<E>>> findSafeZonesForVertices(Set<Vertex<E>> graphProductVertices, Graph<E> factorGraph) {
        return graphProductVertices.stream().collect(
                Collectors.toMap(
                        key -> key,
                        value -> findSafeZonesForVertex(value, factorGraph)
                )
        );
    }

    /**
     * Method return safe zone for given vertex in a factor product graph.
     * Assume, that vertex of graph product is a set i. e. (1, 2),
     * then his safe zone is the common safe zones in factor graph
     * for all component in vertex of product graph, in our case common safe zone for 1 and 2.
     *
     * @param vertex      vertex for which method find safe zone
     * @param factorGraph factor of graph product
     * @return Set&lt;Vertex&lt;E&gt;&gt; safe zone for given vertex
     */
    public <E> Set<Vertex<E>> findSafeZonesForVertex(Vertex<E> vertex, Graph<E> factorGraph) {

        Supplier<Set<Vertex<E>>> complexNeighborhood = () -> {
            Set<Vertex<E>> complexVertex = extractVertices(vertex, factorGraph);
            return collectClosedNeighborhoodVertices(complexVertex);
        };

        Collection<Vertex<E>> vertices = factorGraph.findVertex(vertex.getIndex())
                .map(Vertex::getClosedNeighborhood)
                .orElseGet(complexNeighborhood);

        return factorGraph.getVertices().stream()
                .filter(v -> !vertices.contains(v))
                .collect(Collectors.toSet());
    }

    /**
     * Method collect closed neighborhood from given vertices and join it with one set.
     *
     * @param vertices vertices from which will be taken neighborhoods.
     * @return Set&lt;Vertex&lt;E&gt;&gt; with closed neighborhood all vertices given as param.
     */
    public <E> Set<Vertex<E>> collectClosedNeighborhoodVertices(Set<Vertex<E>> vertices) {
        return vertices.stream()
                .map(Vertex::getClosedNeighborhood)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Method extract vertices from a graph product, when we assume, that
     * each vertex of product graph is a set of vertices from a factor graph.
     *
     * @param vertex    graph product vertex
     * @param fromGraph factor graph
     * @return Set&lt;Vertex&lt;E&gt;&gt; vertex of graph product represented as set.
     */
    public <E> Set<Vertex<E>> extractVertices(Vertex<?> vertex, Graph<E> fromGraph) {
        Set<Vertex<E>> set = new HashSet<>();
        Object first, second;
        first = vertex.getIndex();
        while (Pair.class.isInstance(first)) {
            Pair pair = (Pair) first;
            first = pair.getFirst();
            second = pair.getSecond();
            fromGraph.findVertex((E) second).ifPresent(set::add);
        }
        fromGraph.findVertex((E) first).ifPresent(set::add);
        return set;
    }
}
