package com.copsandrobber.algorithm.one_cop_enough.helper;

import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class IsOneCopEnoughHelper {

    public <E> boolean isFirstDominatingSecond(Vertex<E> first, Vertex<E> second) {
        Collection<Vertex<E>> firstClosedNeighbourhood = first.getClosedNeighbourhood();
        Collection<Vertex<E>> secondClosedNeighbourhood = second.getClosedNeighbourhood();
        return secondClosedNeighbourhood.size() <= firstClosedNeighbourhood.size() &&
                firstClosedNeighbourhood.containsAll(secondClosedNeighbourhood);

    }

    public <E> boolean isAllRobbersNeighbourAndAnyCopIsMarked(Collection<Vertex<E>> robberNeighbours,
                                                              Collection<Vertex<E>> copNeighbours,
                                                              Set<Pair<Vertex<E>, Vertex<E>>> marked) {

        BiFunction<Vertex<E>, Vertex<E>, Boolean> markedContainsConfiguration =
                (copNeighbour, robberNeighbour) -> marked.contains(new Pair<>(copNeighbour, robberNeighbour));

        BiFunction<Vertex<E>, Collection<Vertex<E>>, Boolean> anyCopNeighbourAndRobberNeighbourExist =
                (robberNeighbour, copNeighbourhood) -> copNeighbourhood.stream()
                        .anyMatch(copNeighbour -> markedContainsConfiguration.apply(copNeighbour, robberNeighbour));

        BiFunction<Collection<Vertex<E>>, Collection<Vertex<E>>, Boolean> allRobberNeighbourAndAnyCopIsMarked =
                (robberNeighbourhood, copNeighbourhood) -> robberNeighbourhood.stream()
                        .allMatch(robberNeighbour ->
                                anyCopNeighbourAndRobberNeighbourExist.apply(robberNeighbour, copNeighbourhood)
                        );

        return allRobberNeighbourAndAnyCopIsMarked.apply(robberNeighbours, copNeighbours);
    }

    public <E> List<Pair<Vertex<E>, Vertex<E>>> createUnmarkedConfigurations(Set<Pair<Vertex<E>, Vertex<E>>> marked) {
        List<Pair<Vertex<E>, Vertex<E>>> unmarked = new LinkedList<>();
        marked.forEach(first ->
                marked.forEach(second -> {
                    if (!first.equals(second)) {
                        unmarked.add(new Pair<>(first.getFirst(), second.getFirst()));
                    }
                }));

        return unmarked;
    }

    public <E> LinkedHashSet<Pair<Vertex<E>, Vertex<E>>> createMarkedConfigurations(List<Vertex<E>> graphVertices) {
        return graphVertices.stream()
                .map(v -> new Pair<>(v, v))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
