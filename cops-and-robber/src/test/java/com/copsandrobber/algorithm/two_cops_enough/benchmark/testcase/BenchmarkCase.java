package com.copsandrobber.algorithm.two_cops_enough.benchmark.testcase;

import com.copsandrobber.algorithm.two_cops_enough.IsTwoCopsEnough;
import com.graphrodite.model.Graph;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1) // repeat test only for one thread
@Warmup(iterations = 1, time = 5) // 1 warmup iterations for 5 second
@Measurement(iterations = 1, time = 5) // 1 measurement iterations for 5 second
@State(Scope.Thread)
abstract class BenchmarkCase<E> {

    private Graph<E> graph;

    public BenchmarkCase(Graph<E> graph) {
        this.graph = graph;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public boolean benchmark() {
        return IsTwoCopsEnough.calculate(graph);
    }

    public void runTest() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(getClass().getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
