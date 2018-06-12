package com.copsandrobber.algorithm.two_cops_enough;

import com.copsandrobber.algorithm.common.ConfigurationsWrapper;
import com.copsandrobber.algorithm.common.MarkConfigurationAlgorithm;
import com.copsandrobber.algorithm.two_cops_enough.helper.ThreeTuples;
import com.copsandrobber.algorithm.two_cops_enough.helper.ThreeTuplesConfigurationsWrapper;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;

import java.util.Set;

/**
 * Class with implementation of algorithm which checking if two cops is enough to catch robber.
 * Strategy calculation based on mark configurations representing possible rounds in graph.
 * More information, description available in article
 * Cops and robbers on planar graphs. Maurer A., McCauley J., Valeva S.
 * Institute for Mathematics and its Applications 2010,
 * volume 1, page 8.
 */
public class IsTwoCopsEnough {

    private IsTwoCopsEnough() {

    }

    /**
     * Method answer for question if two cops is enough to catch robber in a given as parameter graph.
     * @param graph graph on which will be performed calculations.
     * @return true if two cops is enough to catch robber in given graph, false otherwise.
     */
    public static <E> boolean calculate(Graph<E> graph) {
        Set<Vertex<E>> graphVertices = graph.getVertices();
        ConfigurationsWrapper<ThreeTuples<Vertex<E>>, E> configurations
                = ThreeTuplesConfigurationsWrapper.create(graphVertices);
        return MarkConfigurationAlgorithm.calculate(configurations);
    }
}
