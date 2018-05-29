package com.copsandrobber.algorithm.one_cop_enough;

import com.copsandrobber.algorithm.common.ConfigurationsWrapper;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class PairConfigurationWrapper<T> extends ConfigurationsWrapper<Pair<Vertex<T>, Vertex<T>>, T> {


    private PairConfigurationWrapper(Set<Vertex<T>> vertices) {
        super(vertices);
    }

    public static <T> ConfigurationsWrapper<Pair<Vertex<T>, Vertex<T>>, T> create(Set<Vertex<T>> vertices) {
        return new PairConfigurationWrapper<>(vertices);
    }

    @Override
    protected void initFrom(Set<Vertex<T>> vertices) {
        Set<Pair<Vertex<T>, Vertex<T>>> marked = new LinkedHashSet<>();
        List<Pair<Vertex<T>, Vertex<T>>> unmarked = new LinkedList<>();

        BiConsumer<Vertex<T>, Vertex<T>> markRobberPosition = (copPosition, robberPosition) -> {
            Pair<Vertex<T>, Vertex<T>> pair = new Pair<>(copPosition, robberPosition);
            if (copPosition.equals(robberPosition)) {
                marked.add(pair);
            } else {
                unmarked.add(pair);
            }
        };
        Consumer<Vertex<T>> markCopPosition = (copPosition) ->
                vertices.forEach(robberPosition -> markRobberPosition.accept(copPosition, robberPosition));

        Runnable createConfiguration = () -> vertices.forEach(markCopPosition);

        createConfiguration.run();
        this.unmarked = unmarked;
        this.marked = marked;
    }

    @Override
    public boolean canMarkConfiguration(Pair<Vertex<T>, Vertex<T>> currentConfiguration) {
        Collection<Vertex<T>> copNeighbours = currentConfiguration.getFirst().getClosedNeighbourhood();
        Collection<Vertex<T>> robberNeighbours = currentConfiguration.getSecond().getClosedNeighbourhood();


        BiFunction<Vertex<T>, Vertex<T>, Boolean> markedContainsConfiguration =
                (copNeighbour, robberNeighbour) -> marked.contains(new Pair<>(copNeighbour, robberNeighbour));

        BiFunction<Vertex<T>, Collection<Vertex<T>>, Boolean> anyCopNeighbourAndRobberNeighbourExist =
                (robberNeighbour, copNeighbourhood) -> copNeighbourhood.stream()
                        .anyMatch(copNeighbour -> markedContainsConfiguration.apply(copNeighbour, robberNeighbour));

        BiFunction<Collection<Vertex<T>>, Collection<Vertex<T>>, Boolean> allRobberNeighbourAndAnyCopIsMarked =
                (robberNeighbourhood, copNeighbourhood) -> robberNeighbourhood.stream()
                        .allMatch(robberNeighbour ->
                                anyCopNeighbourAndRobberNeighbourExist.apply(robberNeighbour, copNeighbourhood)
                        );

        return allRobberNeighbourAndAnyCopIsMarked.apply(robberNeighbours, copNeighbours);
    }

}
