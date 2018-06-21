package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnEdgesStrategy;
import com.graphrodite.factory.GraphTemplate;
import com.graphrodite.model.Graph;

public class DodecahedronAndIterateOnEdgesStrategyCase extends BenchmarkCase<Integer> {
    public DodecahedronAndIterateOnEdgesStrategyCase() {
        super(GraphTemplate.getInstance().getDodecahedronGraph(), IterateOnEdgesStrategy.get(), 3);
    }
}
