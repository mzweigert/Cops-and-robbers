package com.copsandrobber.algorithm.one_cop_enough.benchmark;

import com.copsandrobber.algorithm.one_cop_enough.benchmark.testcase.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openjdk.jmh.runner.RunnerException;

@RunWith(MockitoJUnitRunner.class)
public class IsOneCopEnoughBenchmarkTest {


    @Test
    public void benchmarkTestSelfStrongProductOfP25GraphAndRemoveTrapsStrategyCase() throws RunnerException {
        SelfStrongProductOfP25GraphAndRemoveTrapsStrategyCase state = new SelfStrongProductOfP25GraphAndRemoveTrapsStrategyCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestSelfStrongProductOfP25GraphAndMarkConfigurationsStrategyState() throws RunnerException {
        SelfStrongProductOfP25GraphAndMarkConfigurationsStrategyCase state = new SelfStrongProductOfP25GraphAndMarkConfigurationsStrategyCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestSelfCartesianProductOfC25GraphAndRemoveTrapsStrategyCase() throws RunnerException {
        SelfCartesianProductOfC25GraphAndRemoveTrapsStrategyCase state = new SelfCartesianProductOfC25GraphAndRemoveTrapsStrategyCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestSelfCartesianProductOfC25GraphAndMarkConfigurationsStrategyState() throws RunnerException {
        SelfCartesianProductOfC25GraphAndMarkConfigurationsStrategyCase state = new SelfCartesianProductOfC25GraphAndMarkConfigurationsStrategyCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestSelfLexicographicalProductOfT25GraphAndRemoveTrapsStrategyCase() throws RunnerException {
        SelfLexicographicalProductOfT25GraphAndRemoveTrapsStrategyCase state = new SelfLexicographicalProductOfT25GraphAndRemoveTrapsStrategyCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestSelfLexicographicalProductOfT25GraphAndMarkConfigurationsStrategyState() throws RunnerException {
        SelfLexicographicalProductOfT25GraphAndMarkConfigurationsStrategyCase state = new SelfLexicographicalProductOfT25GraphAndMarkConfigurationsStrategyCase();
        state.runTest();
    }
}
