package com.copsandrobber.algorithm;

import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.factory.GraphTemplate;
import com.graphrodite.model.Graph;

import java.util.function.Function;

public class Template {

    private static GraphProductFactory factory = new GraphProductFactory();
    private static GraphTemplate template = GraphTemplate.getInstance();

    public static final Function<Integer, Graph> selfStrongProductOfPN = (n) -> {
        Graph<Integer> graph = template.createPathWithGivenLength(n);
        return factory.createStrongProduct(graph, graph);
    };

    public static final Function<Integer, Graph> selfLexicographicalOfTN = (n) -> {
        Graph<Integer> graph = template.createTreeWithGivenLength(n);
        return factory.createLexicographicalProduct(graph, graph);
    };

    public static final Function<Integer, Graph> selfCartesianOfCN = (n) -> {
        Graph<Integer> graph = template.createCycleWithGivenLength(n);
        return factory.createCartesianProduct(graph, graph);
    };
}

