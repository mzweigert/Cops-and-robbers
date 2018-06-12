package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnNeighborsStrategy;
import com.graphrodite.model.Graph;

public class DodecahedronAndIterateOnNeighborsStrategyCase extends BenchmarkCase<Integer> {

    public DodecahedronAndIterateOnNeighborsStrategyCase() {
        super(Graph.dodecahedron(), IterateOnNeighborsStrategy.get(), 3);
    }

}
