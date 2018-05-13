package com.copsandrobber.algorithm.one_cop_enough;

import com.copsandrobber.algorithm.one_cop_enough.strategy.OneCopEnoughStrategy;
import com.graphrodite.model.Graph;

public class IsOneCopEnough {

    private IsOneCopEnough(){

    }

    public static Calculate setStrategy(OneCopEnoughStrategy strategy){
        return new Evaluator(strategy);
    }

    private static class Evaluator implements Calculate {

        private OneCopEnoughStrategy strategy;

        private Evaluator(OneCopEnoughStrategy strategy){
            this.strategy = strategy;
        }

        @Override
        public <E> boolean calculate(Graph<E> graph) {
            return strategy.calculate(graph);
        }
    }

    public interface Calculate {
        <E> boolean calculate(Graph<E> graph);
    }
}
