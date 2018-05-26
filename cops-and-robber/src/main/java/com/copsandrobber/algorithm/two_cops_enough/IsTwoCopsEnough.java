package com.copsandrobber.algorithm.two_cops_enough;

import com.copsandrobber.algorithm.common.ConfigurationsWrapper;
import com.copsandrobber.algorithm.common.MarkConfigurationAlgorithm;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;

import java.util.Set;

public class IsTwoCopsEnough {

    private IsTwoCopsEnough() {

    }

    public static <E> boolean calculate(Graph<E> graph) {
        Set<Vertex<E>> graphVertices = graph.getVertices();
        ConfigurationsWrapper<ThreeTuples<Vertex<E>>, E> configurations
                = ThreeTuplesConfigurationsWrapper.create(graphVertices);
        return MarkConfigurationAlgorithm.calculate(configurations);
    }
}
