package com.graphrodite.internal.wrapper;

import com.graphrodite.model.Vertex;

import java.util.*;

public class FindCycleDFSWrapper<E> {

    private Vertex<E> startVertex;
    private LinkedList<Vertex<E>> cycleVertices;
    private Set<Vertex<E>> visitedVertices;

    public FindCycleDFSWrapper(Vertex<E> startVertex) {
        this(startVertex, new LinkedList<>(), new HashSet<>());
    }

    public FindCycleDFSWrapper(Vertex<E> startVertex, LinkedList<Vertex<E>> cycleVertices, Set<Vertex<E>> visitedVertices) {
        this.startVertex = startVertex;
        this.cycleVertices = cycleVertices;
        this.visitedVertices = visitedVertices;
    }

    public Vertex<E> getStartVertex() {
        return startVertex;
    }

    public LinkedList<Vertex<E>> getCycleVertices() {
        return cycleVertices;
    }

    public boolean addToCycle(Vertex<E> item){
        return cycleVertices.offer(item);
    }

    public Optional<Vertex<E>> removeFromCycle(){
        return cycleVertices.isEmpty() ? Optional.empty() : Optional.of(cycleVertices.pollLast());
    }

    public Optional<Vertex<E>> getLastFromCycle(){
        return cycleVertices.isEmpty() ? Optional.empty() : Optional.of(cycleVertices.peekLast());
    }

    public boolean addToVisited(Vertex<E> item){
        return visitedVertices.add(item);
    }

    public boolean startVertexEqualToNotVisited(Vertex<E> neighbor) {
        return neighbor.equals(startVertex) || !visitedVertices.contains(neighbor);
    }

    public boolean IsNotInVisited(Vertex<E> neighbor) {
        return false;
    }

}
