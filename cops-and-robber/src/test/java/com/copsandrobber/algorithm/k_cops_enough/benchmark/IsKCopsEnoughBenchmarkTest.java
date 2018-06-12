package com.copsandrobber.algorithm.k_cops_enough.benchmark;

import com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openjdk.jmh.runner.RunnerException;

@RunWith(MockitoJUnitRunner.class)
public class IsKCopsEnoughBenchmarkTest {

    @Test
    public void benchmarkTestDodecahedronAndIterateOnEdgesStrategyCase() throws RunnerException {
        DodecahedronAndIterateOnEdgesStrategyCase state = new DodecahedronAndIterateOnEdgesStrategyCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestDodecahedronAndIterateOnNeighborsStrategyCase() throws RunnerException {
        DodecahedronAndIterateOnNeighborsStrategyCase state = new DodecahedronAndIterateOnNeighborsStrategyCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestPetersenAndIterateOnEdgesStrategyCase() throws RunnerException {
        PetersenAndIterateOnEdgesStrategyCase state = new PetersenAndIterateOnEdgesStrategyCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestPetersenAndIterateOnNeighborsStrategyCase() throws RunnerException {
        PetersenAndIterateOnEdgesStrategyCase state = new PetersenAndIterateOnEdgesStrategyCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestC10AndIterateOnEdgesStrategyCase() throws RunnerException {
        C10AndIterateOnEdgesStrategyCase state = new C10AndIterateOnEdgesStrategyCase();
        state.runTest();
    }

    @Test
    public void benchmarkTestC10AndIterateOnNeighborsStrategyCase() throws RunnerException {
        C10AndIterateOnNeighborsStrategyCase state = new C10AndIterateOnNeighborsStrategyCase();
        state.runTest();
    }
}
