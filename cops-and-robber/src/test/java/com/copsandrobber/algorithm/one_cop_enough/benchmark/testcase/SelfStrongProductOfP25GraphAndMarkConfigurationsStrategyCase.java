package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.GraphTemplate;
import com.copsandrobber.algorithm.one_cop_enough.strategy.MarkConfigurationsStrategy;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;

public class SelfStrongProductOfP25GraphAndMarkConfigurationsStrategyCase extends BenchmarkCase<Integer> {

    public SelfStrongProductOfP25GraphAndMarkConfigurationsStrategyCase() {
        super(GraphTemplate.getSelfStrongProductOfPNGraph(25), MarkConfigurationsStrategy.get());
    }

}