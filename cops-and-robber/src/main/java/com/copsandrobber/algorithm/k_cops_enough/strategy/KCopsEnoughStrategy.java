package com.copsandrobber.algorithm.k_cops_enough.strategy;

import com.copsandrobber.algorithm.k_cops_enough.helper.IsKCopsEnoughHelper;
import com.graphrodite.model.Graph;

public interface KCopsEnoughStrategy {

    IsKCopsEnoughHelper helper = new IsKCopsEnoughHelper();

    <E> boolean calculate(Graph<E> graph, int k);

    default <E> boolean measureCalculate(Graph<E> graph, int k) {
        long startTime = System.currentTimeMillis();
        boolean result = calculate(graph, k);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        return result;
    }
}