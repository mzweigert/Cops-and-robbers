package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.GraphTemplate;
import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnNeighborsStrategy;

public class C10AndIterateOnNeighborsStrategyCase extends BenchmarkCase<Integer> {

    public C10AndIterateOnNeighborsStrategyCase() {
        super(GraphTemplate.getC10Graph(), IterateOnNeighborsStrategy.get(), 3);
    }

}
