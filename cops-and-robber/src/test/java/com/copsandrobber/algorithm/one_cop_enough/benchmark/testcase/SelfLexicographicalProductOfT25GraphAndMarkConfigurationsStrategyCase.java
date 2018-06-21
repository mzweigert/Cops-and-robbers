package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.Template;
import com.copsandrobber.algorithm.one_cop_enough.strategy.MarkConfigurationsStrategy;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;

public class SelfLexicographicalProductOfT25GraphAndMarkConfigurationsStrategyCase extends BenchmarkCase<Integer> {

    public SelfLexicographicalProductOfT25GraphAndMarkConfigurationsStrategyCase() {
        super(Template.selfLexicographicalOfTN.apply(25), MarkConfigurationsStrategy.get());
    }

}