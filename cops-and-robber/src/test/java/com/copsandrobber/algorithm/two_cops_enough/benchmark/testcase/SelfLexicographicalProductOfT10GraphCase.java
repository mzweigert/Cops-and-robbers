package com.copsandrobber.algorithm.two_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.Template;

public class SelfLexicographicalProductOfT10GraphCase extends BenchmarkCase<Integer> {

    public SelfLexicographicalProductOfT10GraphCase() {
        super(Template.selfLexicographicalOfTN.apply(10));
    }

}