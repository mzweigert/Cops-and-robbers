package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnEdgesStrategy;
import com.graphrodite.factory.GraphTemplate;
import com.graphrodite.model.Graph;

public class PetersenAndIterateOnEdgesStrategyCase extends BenchmarkCase<Integer> {

    public PetersenAndIterateOnEdgesStrategyCase() {
        super(GraphTemplate.getInstance().getPetersenGraph(), IterateOnEdgesStrategy.get(), 3);
    }

}
