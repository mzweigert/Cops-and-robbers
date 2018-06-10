package com.copsandrobber.algorithm.k_cops_enough;


import com.copsandrobber.algorithm.k_cops_enough.strategy.KCopsEnoughStrategy;
import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.model.Graph;

/**
 * Class container to holding and evaluate strategy algorithm to checking if need k cops is enough to catch robber,
 * graph on which will be performed calculations and
 * k integer to checking upper limits for cops number in graph.
 */
public class IsKCopsEnough {

    private IsKCopsEnough() {

    }

    public static KCopsEnoughStrategy setStrategy(KCopsEnoughStrategy strategy) {
        return new Evaluator(strategy);
    }

    private static class Evaluator implements KCopsEnoughStrategy {

        private KCopsEnoughStrategy strategy;

        private Evaluator(KCopsEnoughStrategy strategy) {
            this.strategy = strategy;
        }

        @Override
        public <E> boolean calculate(Graph<E> graph, int k) {
            return strategy.calculate(graph, k);
        }
    }
}
