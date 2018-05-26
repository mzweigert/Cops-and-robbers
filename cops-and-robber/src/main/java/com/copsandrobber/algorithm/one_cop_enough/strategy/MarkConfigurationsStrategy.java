package com.copsandrobber.algorithm.one_cop_enough.strategy;

import com.copsandrobber.algorithm.common.ConfigurationsWrapper;
import com.copsandrobber.algorithm.common.MarkConfigurationAlgorithm;
import com.copsandrobber.algorithm.one_cop_enough.PairConfigurationWrapper;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.Set;

public class MarkConfigurationsStrategy implements OneCopEnoughStrategy {

    private static MarkConfigurationsStrategy instance;

    private MarkConfigurationsStrategy() {

    }

    public static OneCopEnoughStrategy get() {
        if (instance == null) {
            instance = new MarkConfigurationsStrategy();
        }
        return instance;
    }

    @Override
    public <E> boolean calculate(Graph<E> graph) {
        Set<Vertex<E>> graphVertices = graph.getVertices();
        ConfigurationsWrapper<Pair<Vertex<E>, Vertex<E>>, E> configurations = PairConfigurationWrapper.create(graphVertices);
        return MarkConfigurationAlgorithm.calculate(configurations);
    }

}
