package com.copsandrobber.algorithm.two_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.GraphTemplate;

public class SelfStrongProductOfP10GraphCase extends BenchmarkCase<Integer> {

    public SelfStrongProductOfP10GraphCase() {
        super(GraphTemplate.getSelfStrongProductOfPNGraph(10));
    }

}