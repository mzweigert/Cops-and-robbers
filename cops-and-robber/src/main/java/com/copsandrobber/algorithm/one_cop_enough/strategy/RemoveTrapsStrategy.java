package com.copsandrobber.algorithm.one_cop_enough.strategy;

import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class RemoveTrapsStrategy implements OneCopEnoughStrategy {

    private static RemoveTrapsStrategy instance;

    private RemoveTrapsStrategy() {

    }

    public static OneCopEnoughStrategy get() {
        if (instance == null) {
            instance = new RemoveTrapsStrategy();
        }
        return instance;
    }

    @Override
    public <E> boolean calculate(Graph<E> graph) {
        Set<Vertex<E>> vertices = graph.getVertices();
        boolean trapsFound;
        do {
            trapsFound = false;
            Iterator<Vertex<E>> iterator;
            for (iterator = vertices.iterator(); iterator.hasNext(); ) {
                Vertex<E> vertex = iterator.next();
                for (Vertex<E> neighbor : vertex.getClosedNeighbourhood()) {
                    if (isFirstDominatingSecond(neighbor, vertex) && !vertex.equals(neighbor)) {
                        vertices.forEach(v -> v.removeNeighbourhood(vertex));
                        iterator.remove();
                        if (!trapsFound) {
                            trapsFound = true;
                        }
                        break;
                    }
                }
            }
        } while (vertices.size() > 1 && trapsFound);

        return vertices.size() == 1;
    }

    private <E> boolean isFirstDominatingSecond(Vertex<E> first, Vertex<E> second) {
        Collection<Vertex<E>> firstClosedNeighbourhood = first.getClosedNeighbourhood();
        Collection<Vertex<E>> secondClosedNeighbourhood = second.getClosedNeighbourhood();
        return secondClosedNeighbourhood.size() <= firstClosedNeighbourhood.size() &&
                firstClosedNeighbourhood.containsAll(secondClosedNeighbourhood);

    }

}
