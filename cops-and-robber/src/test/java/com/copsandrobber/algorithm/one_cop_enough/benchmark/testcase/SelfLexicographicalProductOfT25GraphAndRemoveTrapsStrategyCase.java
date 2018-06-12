package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.GraphTemplate;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;

public class SelfLexicographicalProductOfT25GraphAndRemoveTrapsStrategyCase extends BenchmarkCase<Integer> {

    public SelfLexicographicalProductOfT25GraphAndRemoveTrapsStrategyCase() {
        super(GraphTemplate.getSelfLexicographicalProductOfTNGraph(25), RemoveTrapsStrategy.get());
    }

}