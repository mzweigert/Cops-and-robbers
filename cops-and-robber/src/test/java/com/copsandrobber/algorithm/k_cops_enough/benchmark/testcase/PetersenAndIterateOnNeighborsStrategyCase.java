package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.k_cops_enough.strategy.IterateOnNeighborsStrategy;
import com.graphrodite.model.Graph;

public class PetersenAndIterateOnNeighborsStrategyCase extends BenchmarkCase<Integer> {

    public PetersenAndIterateOnNeighborsStrategyCase() {
        super(Graph.petersen(), IterateOnNeighborsStrategy.get(), 3);
    }

}