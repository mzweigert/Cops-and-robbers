package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.Template;
import com.copsandrobber.algorithm.one_cop_enough.strategy.MarkConfigurationsStrategy;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;

public class SelfCartesianProductOfC25GraphAndMarkConfigurationsStrategyCase extends BenchmarkCase<Integer> {

    public SelfCartesianProductOfC25GraphAndMarkConfigurationsStrategyCase() {
        super(Template.selfCartesianOfCN.apply(25), MarkConfigurationsStrategy.get());
    }

}