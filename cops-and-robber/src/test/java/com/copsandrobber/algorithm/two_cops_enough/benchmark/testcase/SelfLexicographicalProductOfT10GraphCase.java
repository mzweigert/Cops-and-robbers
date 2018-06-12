package com.copsandrobber.algorithm.two_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.GraphTemplate;

public class SelfLexicographicalProductOfT10GraphCase extends BenchmarkCase<Integer> {

    public SelfLexicographicalProductOfT10GraphCase() {
        super(GraphTemplate.getSelfLexicographicalProductOfTNGraph(10));
    }

}