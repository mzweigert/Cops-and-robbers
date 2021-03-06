package com.copsandrobber.algorithm.one_cop_enough.strategy;

import com.graphrodite.model.Graph;

public interface OneCopEnoughStrategy {

    /**
     * Method answer for question if one cop is enough to catch robber in a given as parameter graph.
     * @param graph graph on which will be performed calculations.
     * @return true if one cop is enough to catch robber in given graph, false otherwise.
     */
    <E> boolean calculate(Graph<E> graph);
}
