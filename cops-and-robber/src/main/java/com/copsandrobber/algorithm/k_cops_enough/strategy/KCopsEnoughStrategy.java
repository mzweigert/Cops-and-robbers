package com.copsandrobber.algorithm.k_cops_enough.strategy;

import com.copsandrobber.algorithm.k_cops_enough.helper.IsKCopsEnoughHelper;
import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.model.Graph;

public interface KCopsEnoughStrategy {

    IsKCopsEnoughHelper helper = new IsKCopsEnoughHelper();
    GraphProductFactory factory = new GraphProductFactory();
    /**
     * Method answer for question if cops number in given graph is less or equal given integer k
     *
     * @param graph graph for which will be checking above inequality.
     * @param k     potentially upper limit for cops number in given as param graph.
     * @return true if cops number is less or equal k param for given graph, false otherwise.
     */
    <E> boolean calculate(Graph<E> graph, int k);
}