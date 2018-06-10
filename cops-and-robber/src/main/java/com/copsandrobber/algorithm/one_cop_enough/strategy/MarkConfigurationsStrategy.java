package com.copsandrobber.algorithm.one_cop_enough.strategy;

import com.copsandrobber.algorithm.common.ConfigurationsWrapper;
import com.copsandrobber.algorithm.common.MarkConfigurationAlgorithm;
import com.copsandrobber.algorithm.one_cop_enough.PairConfigurationWrapper;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.Set;

/**
 * Class with implementation of algorithm which checking if one cop is enough to catch robber.
 * Strategy calculation based on mark configurations representing possible rounds in graph.
 * More information, description available in article
 * Randomized pursuit-evasion with localvisibility. Isler V., Kannan S., Khanna S.
 * SIAM Journal on Discrete Mathematics 2006,
 * volume 20, page 12.
 */
public class MarkConfigurationsStrategy implements OneCopEnoughStrategy {

    private static MarkConfigurationsStrategy instance;

    private MarkConfigurationsStrategy() {

    }

    /**
     * Method return RemoveTrapsStrategy of IterateOnNeighborsStrategy class
     * @return OneCopEnoughStrategy instance of RemoveTrapsStrategy
     */
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
