package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.GraphTemplate;
import com.copsandrobber.algorithm.one_cop_enough.strategy.MarkConfigurationsStrategy;

public class SelfCartesianProductOfC25GraphAndMarkConfigurationsStrategyCase extends BenchmarkCase<Integer> {

    public SelfCartesianProductOfC25GraphAndMarkConfigurationsStrategyCase() {
        super(GraphTemplate.getSelfCartesianProductOfCNGraph(25), MarkConfigurationsStrategy.get());
    }

}