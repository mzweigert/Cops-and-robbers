package com.copsandrobber.algorithm.one_cop_enough.strategy;

import com.copsandrobber.algorithm.one_cop_enough.helper.IsOneCopEnoughHelper;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class MarkConfigurationsStrategy implements OneCopEnoughStrategy {

    private IsOneCopEnoughHelper helper;

    private MarkConfigurationsStrategy() {
        helper = new IsOneCopEnoughHelper();
    }

    public static OneCopEnoughStrategy get() {
        return new MarkConfigurationsStrategy();
    }

    @Override
    public <E> boolean calculate(Graph<E> graph) {
        Set<Vertex<E>> graphVertices = graph.getVertices();
        Set<Pair<Vertex<E>, Vertex<E>>> marked = helper.createMarkedConfigurations(graphVertices);
        List<Pair<Vertex<E>, Vertex<E>>> unmarked = helper.createUnmarkedConfigurations(marked);

        int prevUnmarkedCount;

        do {
            prevUnmarkedCount = unmarked.size();
            Iterator<Pair<Vertex<E>, Vertex<E>>> unmarkedIterator = unmarked.iterator();

            while (unmarkedIterator.hasNext()) {

                Pair<Vertex<E>, Vertex<E>> configuration = unmarkedIterator.next();
                Collection<Vertex<E>> copNeighbours = configuration.getFirst().getClosedNeighbourhood();
                Collection<Vertex<E>> robberNeighbours = configuration.getSecond().getClosedNeighbourhood();

                if (helper.isAllRobbersNeighbourAndAnyCopIsMarked(robberNeighbours, copNeighbours, marked)) {
                    unmarkedIterator.remove();
                    marked.add(configuration);
                }
            }
        } while (prevUnmarkedCount > unmarked.size() && !unmarked.isEmpty());

        return unmarked.size() == 0;
    }


}
