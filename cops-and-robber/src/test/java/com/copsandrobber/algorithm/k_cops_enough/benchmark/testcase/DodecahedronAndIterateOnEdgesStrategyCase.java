package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnEdgesStrategy;
import com.graphrodite.model.Graph;

public class DodecahedronAndIterateOnEdgesStrategyCase extends BenchmarkCase<Integer> {
    public DodecahedronAndIterateOnEdgesStrategyCase() {
        super(Graph.dodecahedron(), IterateOnEdgesStrategy.get(), 3);
    }
}
