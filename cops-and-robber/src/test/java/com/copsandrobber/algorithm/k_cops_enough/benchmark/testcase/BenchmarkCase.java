package com.copsandrobber.algorithm.k_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.k_cops_enough.IsKCopsEnough;
import com.copsandrobber.algorithm.k_cops_enough.strategy.KCopsEnoughStrategy;
import com.graphrodite.model.Graph;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1) // repeat test only for one thread
@Warmup(iterations = 3, time = 5) // three warmup iterations for 5 second
@Measurement(iterations = 3, time = 5) // ten measurement iterations for 5 second
@State(Scope.Thread)
abstract class BenchmarkCase<E> {

    private Graph<E> graph;
    private KCopsEnoughStrategy strategy;
    private int k;

    public BenchmarkCase(Graph<E> graph, KCopsEnoughStrategy strategy, int k) {
        this.graph = graph;
        this.strategy = strategy;
        this.k = k;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public boolean benchmark() {
        return IsKCopsEnough.setStrategy(strategy).calculate(graph, k);
    }

    public void runTest() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(getClass().getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
