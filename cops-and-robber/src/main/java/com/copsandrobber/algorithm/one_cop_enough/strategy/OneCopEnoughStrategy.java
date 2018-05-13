package com.copsandrobber.algorithm.one_cop_enough.strategy;

import com.graphrodite.model.Graph;

public interface OneCopEnoughStrategy {
    <E> boolean calculate(Graph<E> graph);
}
