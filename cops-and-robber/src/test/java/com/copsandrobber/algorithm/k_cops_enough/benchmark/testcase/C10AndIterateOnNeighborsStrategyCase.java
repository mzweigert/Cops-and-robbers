package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.graphrodite.factory.GraphTemplate;
import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnNeighborsStrategy;

public class C10AndIterateOnNeighborsStrategyCase extends BenchmarkCase<Integer> {

    public C10AndIterateOnNeighborsStrategyCase() {
        super(GraphTemplate.getInstance().createCycleWithGivenLength(10), IterateOnNeighborsStrategy.get(), 3);
    }

}
