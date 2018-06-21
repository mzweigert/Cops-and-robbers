package com.copsandrobber.algorithm.two_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.Template;

public class SelfStrongProductOfP10GraphCase extends BenchmarkCase<Integer> {

    public SelfStrongProductOfP10GraphCase() {
        super(Template.selfLexicographicalOfTN.apply(10));
    }

}