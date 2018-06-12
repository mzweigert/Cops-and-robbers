package com.copsandrobber.algorithm.two_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.GraphTemplate;

public class SelfCartesianProductOfC10GraphCase extends BenchmarkCase<Integer> {

    public SelfCartesianProductOfC10GraphCase() {
        super(GraphTemplate.getSelfCartesianProductOfCNGraph(10));
    }

}