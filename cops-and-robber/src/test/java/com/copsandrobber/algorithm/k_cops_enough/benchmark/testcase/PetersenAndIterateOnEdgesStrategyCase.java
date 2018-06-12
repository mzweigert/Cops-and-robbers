package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnEdgesStrategy;
import com.graphrodite.model.Graph;

public class PetersenAndIterateOnEdgesStrategyCase extends BenchmarkCase<Integer> {

    public PetersenAndIterateOnEdgesStrategyCase() {
        super(Graph.petersen(), IterateOnEdgesStrategy.get(), 3);
    }

}
