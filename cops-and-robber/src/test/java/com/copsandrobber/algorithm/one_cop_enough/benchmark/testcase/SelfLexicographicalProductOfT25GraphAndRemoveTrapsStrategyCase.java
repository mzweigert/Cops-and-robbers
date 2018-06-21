package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.Template;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;

public class SelfLexicographicalProductOfT25GraphAndRemoveTrapsStrategyCase extends BenchmarkCase<Integer> {

    public SelfLexicographicalProductOfT25GraphAndRemoveTrapsStrategyCase() {
        super(Template.selfLexicographicalOfTN.apply(25), RemoveTrapsStrategy.get());
    }

}