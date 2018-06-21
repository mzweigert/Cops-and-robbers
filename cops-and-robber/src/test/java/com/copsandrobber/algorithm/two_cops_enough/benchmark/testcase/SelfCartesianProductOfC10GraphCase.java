package com.copsandrobber.algorithm.two_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.Template;

public class SelfCartesianProductOfC10GraphCase extends BenchmarkCase<Integer> {

    public SelfCartesianProductOfC10GraphCase() {
        super(Template.selfCartesianOfCN.apply(10));
    }

}