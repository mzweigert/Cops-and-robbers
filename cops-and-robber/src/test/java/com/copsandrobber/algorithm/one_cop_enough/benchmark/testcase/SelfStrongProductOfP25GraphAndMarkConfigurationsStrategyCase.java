package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.Template;
import com.copsandrobber.algorithm.one_cop_enough.strategy.MarkConfigurationsStrategy;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;
import com.graphrodite.factory.GraphTemplate;
import com.graphrodite.model.Graph;

public class SelfStrongProductOfP25GraphAndMarkConfigurationsStrategyCase extends BenchmarkCase<Integer> {

    public SelfStrongProductOfP25GraphAndMarkConfigurationsStrategyCase() {
        super(Template.selfStrongProductOfPN.apply(25), MarkConfigurationsStrategy.get());
    }

}