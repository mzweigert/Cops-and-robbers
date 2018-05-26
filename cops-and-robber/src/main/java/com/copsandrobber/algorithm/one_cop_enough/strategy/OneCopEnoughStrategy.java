package com.copsandrobber.algorithm.one_cop_enough.strategy;

import com.graphrodite.model.Graph;

public interface OneCopEnoughStrategy {
    <E> boolean calculate(Graph<E> graph);

    default <E> boolean measureCalculate(Graph<E> graph) {
        long startTime = System.currentTimeMillis();
        boolean result = calculate(graph);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        return result;
    }
}
