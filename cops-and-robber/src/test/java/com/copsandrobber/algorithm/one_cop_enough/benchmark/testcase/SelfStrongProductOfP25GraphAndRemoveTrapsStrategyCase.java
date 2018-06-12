package com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase;

import com.copsandrobber.algorithm.GraphTemplate;
import com.copsandrobber.algorithm.one_cop_enough.strategy.RemoveTrapsStrategy;

public class SelfStrongProductOfP25GraphAndRemoveTrapsStrategyCase extends BenchmarkCase<Integer> {

    public SelfStrongProductOfP25GraphAndRemoveTrapsStrategyCase() {
        super(GraphTemplate.getSelfStrongProductOfPNGraph(25), RemoveTrapsStrategy.get());
    }

}