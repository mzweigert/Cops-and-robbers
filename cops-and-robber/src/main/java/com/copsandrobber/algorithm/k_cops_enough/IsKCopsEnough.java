package com.copsandrobber.algorithm.k_cops_enough;


import com.copsandrobber.algorithm.k_cops_enough.strategy.KCopsEnoughStrategy;
import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.model.Graph;

public class IsKCopsEnough {

    private IsKCopsEnough() {

    }

    public static IsKCopsEnough.Calculate setStrategy(KCopsEnoughStrategy strategy) {
        return new IsKCopsEnough.Evaluator(strategy);
    }


    public interface Calculate {
        <E> boolean measureCalculate(Graph<E> graph, int k);

        <E> boolean calculate(Graph<E> graph, int k);
    }

    private static class Evaluator implements IsKCopsEnough.Calculate {

        private KCopsEnoughStrategy strategy;

        private Evaluator(KCopsEnoughStrategy strategy) {
            this.strategy = strategy;
        }

        @Override
        public <E> boolean measureCalculate(Graph<E> graph, int k) {
            return strategy.measureCalculate(graph, k);
        }

        @Override
        public <E> boolean calculate(Graph<E> graph, int k) {
            return strategy.calculate(graph, k);
        }
    }
}
