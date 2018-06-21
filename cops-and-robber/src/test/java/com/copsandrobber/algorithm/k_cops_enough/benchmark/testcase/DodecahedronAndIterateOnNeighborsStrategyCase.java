package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnNeighborsStrategy;
import com.graphrodite.factory.GraphTemplate;
import com.graphrodite.model.Graph;

public class DodecahedronAndIterateOnNeighborsStrategyCase extends BenchmarkCase<Integer> {

    public DodecahedronAndIterateOnNeighborsStrategyCase() {
        super(GraphTemplate.getInstance().getDodecahedronGraph(), IterateOnNeighborsStrategy.get(), 3);
    }

}
