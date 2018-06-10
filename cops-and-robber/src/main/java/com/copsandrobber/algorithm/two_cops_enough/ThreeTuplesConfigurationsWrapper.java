package com.copsandrobber.algorithm.two_cops_enough;

import com.copsandrobber.algorithm.common.ConfigurationsWrapper;
import com.graphrodite.model.Vertex;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.*;

/**
 * Class which holding information about marked and unmarked positions of two cops and robber,
 * where each position is representing for ThreeTuples (c1, c2, r),
 * where c1 is the first cop position, c2 is the second cop position and r is the robber position.
 * Used in IsTwoCopsEnough algorithm which check that need two cops to catch a robber.
 * @param <T> type of vertex position.
 */
class ThreeTuplesConfigurationsWrapper<T> extends ConfigurationsWrapper<ThreeTuples<Vertex<T>>, T> {


    private ThreeTuplesConfigurationsWrapper(Set<Vertex<T>> vertices) {
        super(vertices);
    }

    public static <T> ConfigurationsWrapper<ThreeTuples<Vertex<T>>, T> create(Set<Vertex<T>> vertices) {
        return new ThreeTuplesConfigurationsWrapper<>(vertices);
    }

    @Override
    protected void initFrom(Set<Vertex<T>> vertices) {

        Set<ThreeTuples<Vertex<T>>> marked = new LinkedHashSet<>();
        List<ThreeTuples<Vertex<T>>> unmarked = new LinkedList<>();

        TriConsumer<Vertex<T>> markRobberPosition = (firstCopPosition, secondCopPosition, robberPosition) -> {
            ThreeTuples<Vertex<T>> configuration = new ThreeTuples<>(firstCopPosition, secondCopPosition, robberPosition);
            if (firstCopPosition.equals(robberPosition) || secondCopPosition.equals(robberPosition)) {
                marked.add(configuration);
            } else {
                unmarked.add(configuration);
            }
        };

        BiConsumer<Vertex<T>, Vertex<T>> markSecondCopPosition = (firstCopPosition, secondCopPosition) ->
                vertices.forEach(robberPosition -> markRobberPosition.accept(firstCopPosition, secondCopPosition, robberPosition));

        Consumer<Vertex<T>> markFirstCopPosition = (firstCopPosition) ->
                vertices.forEach(secondCopPosition -> markSecondCopPosition.accept(firstCopPosition, secondCopPosition));

        Runnable createConfiguration = () -> vertices.forEach(markFirstCopPosition);

        createConfiguration.run();
        this.unmarked = unmarked;
        this.marked = marked;
    }

    @Override
    public boolean canMarkConfiguration(ThreeTuples<Vertex<T>> currentConfiguration) {

        TriFunction<Vertex<T>, Vertex<T>, Vertex<T>, Boolean> markedContainsConfiguration =
                (robberNeighbour, secondCopNeighbour, firstCopNeighbour) ->
                        marked.contains(new ThreeTuples<>(firstCopNeighbour, secondCopNeighbour, robberNeighbour));

        BiFunction<Vertex<T>, Vertex<T>, Boolean> firstAndSecondCopAndRobberNeighbourExist =
                (robberNeighbour, secondCopNeighbour) -> currentConfiguration.getFirst().getClosedNeighborhood().stream()
                        .anyMatch(firstCopNeighbour ->
                                markedContainsConfiguration.apply(robberNeighbour, secondCopNeighbour, firstCopNeighbour));

        Function<Vertex<T>, Boolean> secondCopAndRobberNeighbourExist = (robberNeighbour) ->
                currentConfiguration.getSecond().getClosedNeighborhood().stream()
                        .anyMatch(secondCopNeighbour ->
                                firstAndSecondCopAndRobberNeighbourExist.apply(robberNeighbour, secondCopNeighbour));

        Supplier<Boolean> configurationExist = () ->
                currentConfiguration.getThird().getClosedNeighborhood().stream()
                        .allMatch(secondCopAndRobberNeighbourExist::apply);


        return configurationExist.get();
    }

    @FunctionalInterface
    interface TriConsumer<T> {
        void accept(T first, T second, T third);
    }

    @FunctionalInterface
    interface TriFunction<FIRST, SECOND, THIRD, RETURN_VALUE> {
        RETURN_VALUE apply(FIRST first, SECOND second, THIRD third);

    }

}
