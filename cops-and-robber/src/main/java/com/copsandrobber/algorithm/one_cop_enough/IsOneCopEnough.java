package com.copsandrobber.algorithm.one_cop_enough;

import com.copsandrobber.algorithm.one_cop_enough.strategy.OneCopEnoughStrategy;
import com.graphrodite.model.Graph;

/**
 * Class container to holding and evaluate strategy algorithm to checking if one cop is enough to catch robber and
 * graph on which will be performed calculations.
 */
public class IsOneCopEnough {

    private IsOneCopEnough() {

    }

    public static OneCopEnoughStrategy setStrategy(OneCopEnoughStrategy strategy) {
        return new Evaluator(strategy);
    }

    private static class Evaluator implements OneCopEnoughStrategy {

        private OneCopEnoughStrategy strategy;

        private Evaluator(OneCopEnoughStrategy strategy) {
            this.strategy = strategy;
        }

        @Override
        public <E> boolean calculate(Graph<E> graph) {
            return strategy.calculate(graph);
        }
    }
}
