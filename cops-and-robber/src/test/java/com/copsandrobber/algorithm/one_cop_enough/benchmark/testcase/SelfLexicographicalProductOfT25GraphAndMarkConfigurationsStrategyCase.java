package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.GraphTemplate;
import com.copsandrobber.algorithm.one_cop_enough.strategy.MarkConfigurationsStrategy;

public class SelfLexicographicalProductOfT25GraphAndMarkConfigurationsStrategyCase extends BenchmarkCase<Integer> {

    public SelfLexicographicalProductOfT25GraphAndMarkConfigurationsStrategyCase() {
        super(GraphTemplate.getSelfLexicographicalProductOfTNGraph(25), MarkConfigurationsStrategy.get());
    }

}