package com.copsandrobber.algorithm;

import com.copsandrobber.model.Graph;
import com.copsandrobber.model.Vertex;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Algorithm {

    public <E> boolean isCopWinGraph(Graph<E> graph){
        List<Vertex<E>> vertices = graph.getVertices();
        for (Iterator<Vertex<E>> iterator = vertices.iterator(); iterator.hasNext(); ) {
            Vertex<E> vertex = iterator.next();
            Collection<Vertex<E>> vertexNeighbors = vertex.getClosedNeighbourhood();
            for (Vertex<E> vNeighbor : vertexNeighbors) {
                Collection<Vertex<E>> neighborNeighbors = vNeighbor.getClosedNeighbourhood();
                if (canRemoveTrap(vertexNeighbors, neighborNeighbors) && !vertex.equals(vNeighbor)) {
                    vertices.stream().forEach(v -> v.removeFromNeighbourhood(vertex));
                    iterator.remove();
                    break;
                }
            }
        }
        return vertices.size() == 1;
    }

    private  <E> boolean canRemoveTrap(Collection<Vertex<E>> vertexNeighbors, Collection<Vertex<E>> neighborNeighbors) {
        return vertexNeighbors.size() <= neighborNeighbors.size() &&
                neighborNeighbors.containsAll(vertexNeighbors);

    }
}
