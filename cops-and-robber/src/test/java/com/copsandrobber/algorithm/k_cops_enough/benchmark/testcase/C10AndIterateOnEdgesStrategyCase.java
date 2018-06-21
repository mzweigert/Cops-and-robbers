package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnEdgesStrategy;
import com.graphrodite.factory.GraphTemplate;

public class C10AndIterateOnEdgesStrategyCase extends BenchmarkCase<Integer> {

    public C10AndIterateOnEdgesStrategyCase() {
        super(GraphTemplate.getInstance().createCycleWithGivenLength(10), IterateOnEdgesStrategy.get(), 3);
    }

}
