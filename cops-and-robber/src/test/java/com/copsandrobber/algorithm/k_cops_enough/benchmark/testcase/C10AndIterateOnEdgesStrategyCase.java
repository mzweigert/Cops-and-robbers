package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.GraphTemplate;
import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnEdgesStrategy;

public class C10AndIterateOnEdgesStrategyCase extends BenchmarkCase<Integer> {

    public C10AndIterateOnEdgesStrategyCase() {
        super(GraphTemplate.getC10Graph(), IterateOnEdgesStrategy.get(), 3);
    }

}
