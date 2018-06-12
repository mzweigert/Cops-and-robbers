package com.copsandrobber.algorithm.two_cops_enough.benchmark;

import com.copsandrobber.algorithm.two_cops_enough.benchmark.testcase.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openjdk.jmh.runner.RunnerException;

@RunWith(MockitoJUnitRunner.class)
public class IsTwoCopsEnoughBenchmarkTest {


    @Test
    public void benchmarkTestSelfStrongProductOfP10GraphCase() throws RunnerException {
        SelfStrongProductOfP10GraphCase state = new SelfStrongProductOfP10GraphCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestSelfCartesianProductOfC10GraphState() throws RunnerException {
        SelfCartesianProductOfC10GraphCase state = new SelfCartesianProductOfC10GraphCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestSelfLexicographicalProductOfT10GraphState() throws RunnerException {
        SelfLexicographicalProductOfT10GraphCase state = new SelfLexicographicalProductOfT10GraphCase();
        state.runTest();
    }
}
