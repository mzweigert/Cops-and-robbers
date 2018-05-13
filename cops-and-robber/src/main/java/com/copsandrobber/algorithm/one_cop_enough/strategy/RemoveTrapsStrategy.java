package com.copsandrobber.algorithm.one_cop_enough.strategy;

import com.copsandrobber.algorithm.one_cop_enough.helper.IsOneCopEnoughHelper;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RemoveTrapsStrategy implements OneCopEnoughStrategy {

    private IsOneCopEnoughHelper helper;

    private RemoveTrapsStrategy(){
        helper = new IsOneCopEnoughHelper();
    }

    public static OneCopEnoughStrategy get() {
        return new RemoveTrapsStrategy();
    }

    @Override
    public <E> boolean calculate(Graph<E> graph) {
        List<Vertex<E>> vertices = graph.getVertices();
        boolean trapsFound;
        do {
            trapsFound = false;
            ListIterator<Vertex<E>> iterator;
            for (iterator = vertices.listIterator(); iterator.hasNext();){
                Vertex<E> vertex =  iterator.next();
                for (Vertex<E> neighbor : vertex.getClosedNeighbourhood()) {
                    if (helper.isFirstDominatingSecond(neighbor, vertex) && !vertex.equals(neighbor)) {
                        vertices.forEach(v -> v.removeNeighbourhood(vertex));
                        iterator.remove();
                        if(!trapsFound){
                            trapsFound = true;
                        }
                        break;
                    }
                }
            }
        } while (vertices.size() > 1 && trapsFound);

        return vertices.size() == 1;
    }

}
