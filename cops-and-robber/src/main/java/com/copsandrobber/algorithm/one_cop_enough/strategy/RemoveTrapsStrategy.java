package com.copsandrobber.algorithm.one_cop_enough.strategy;

import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Class with implementation of algorithm which checking if one cop is enough to catch robber.
 * Strategy calculation based on sequentially remove traps from graph.
 * More information, description available in article
 * A game of cops and robbers. Aigner M., Fromme M.
 * Theoretical Discrete AppliedMathematics 1984,
 * volume 411, pages 1–12.
 */
public class RemoveTrapsStrategy implements OneCopEnoughStrategy {

    private static RemoveTrapsStrategy instance;

    private RemoveTrapsStrategy() {

    }

    /**
     * Method return RemoveTrapsStrategy of IterateOnNeighborsStrategy class
     * @return OneCopEnoughStrategy instance of RemoveTrapsStrategy
     */
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
                for (Vertex<E> neighbor : vertex.getClosedNeighborhood()) {
                    if (isFirstDominatingSecond(neighbor, vertex) && !vertex.equals(neighbor)) {
                        vertices.forEach(v -> v.removeFromNeighborhood(vertex));
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
        Collection<Vertex<E>> firstClosedNeighborhood = first.getClosedNeighborhood();
        Collection<Vertex<E>> secondClosedNeighborhood = second.getClosedNeighborhood();
        return secondClosedNeighborhood.size() <= firstClosedNeighborhood.size() &&
                firstClosedNeighborhood.containsAll(secondClosedNeighborhood);

    }

}
