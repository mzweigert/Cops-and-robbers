package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.Template;
import com.copsandrobber.algorithm.one_cop_enough.strategy.MarkConfigurationsStrategy;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;

public class SelfStrongProductOfP25GraphAndRemoveTrapsStrategyCase extends BenchmarkCase<Integer> {


    public SelfStrongProductOfP25GraphAndRemoveTrapsStrategyCase() {
        super(Template.selfStrongProductOfPN.apply(25), RemoveTrapsStrategy.get());
    }

}