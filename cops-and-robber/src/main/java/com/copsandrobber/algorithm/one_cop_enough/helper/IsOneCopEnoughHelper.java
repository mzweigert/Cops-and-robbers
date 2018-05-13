package com.copsandrobber.algorithm.one_cop_enough.helper;

import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiFunction;
import java.util.function.Function;
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
                                                               List<Pair<Vertex<E>, Vertex<E>>> marked) {

        Function<Pair, Boolean> pairExistInMarked = pair -> marked.stream().anyMatch(p -> p.equals(pair));

        BiFunction<Vertex<E>, Vertex<E>, Boolean> markedContainsConfiguration =
                (copNeighbour, robberNeighbour) -> pairExistInMarked.apply(new Pair<>(copNeighbour, robberNeighbour));

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


    public <E> List<Pair<Vertex<E>, Vertex<E>>> createMarkedConfigurations(List<Vertex<E>> graphVertices) {
        return graphVertices.stream()
                .map(v -> new Pair<>(v, v))
                .collect(Collectors.toList());
    }

    public <E> List<Pair<Vertex<E>, Vertex<E>>> createUnmarkedConfigurations(List<Pair<Vertex<E>, Vertex<E>>> marked) {
        int i = 0, j = 0;
        List<Pair<Vertex<E>, Vertex<E>>> unmarked = new ArrayList<>();
        while ((j != i) || i < marked.size() - 1) {
            Vertex<E> current = marked.get(i).getFirst();
            Vertex<E> next = marked.get(j == i ? ++j : j).getFirst();
            unmarked.add(new Pair<>(current, next));
            if (j == marked.size() - 1) {
                i++;
                j = 0;
            } else {
                j++;
            }
        }
        return unmarked;
    }

    public <E> boolean isPath(List<Vertex<E>> vertices) {
        ListIterator<Vertex<E>> vertexListIterator = vertices.listIterator();
        boolean result = true;
        while (vertexListIterator.hasNext()) {
            int index = vertexListIterator.nextIndex();
            Vertex<E> vertex = vertexListIterator.next();
            Collection<Vertex<E>> openNeighbourhood = vertex.getOpenNeighbourhood();
            if(index == 0 || index == vertices.size() - 1 && openNeighbourhood.size() > 1){
                return false;
            } else if(openNeighbourhood.size() > 2){
                return false;
            } else {
                result = result && openNeighbourhood.stream()
                        .allMatch(neighbour -> neighbour.getOpenNeighbourhood().contains(vertex));
            }

        }
        return result;
    }
}
