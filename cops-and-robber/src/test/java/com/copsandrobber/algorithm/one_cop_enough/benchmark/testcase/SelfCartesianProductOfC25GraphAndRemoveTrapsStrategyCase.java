package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.Template;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;

public class SelfCartesianProductOfC25GraphAndRemoveTrapsStrategyCase extends BenchmarkCase<Integer> {

    public SelfCartesianProductOfC25GraphAndRemoveTrapsStrategyCase() {
        super(Template.selfCartesianOfCN.apply(25), RemoveTrapsStrategy.get());
    }

}
