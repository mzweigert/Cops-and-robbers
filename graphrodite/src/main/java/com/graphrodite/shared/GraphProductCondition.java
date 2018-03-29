package com.graphrodite.shared;


import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;

public class GraphProductCondition<A, B> {

    private final Graph<A> firstGraph;
    private final Graph<B> secondGraph;
    private final Pair<A, B> firstVertexIndex;
    private final Pair<A, B> secondVertexIndex;

    private GraphProductCondition(Graph<A> firstGraph, Graph<B> secondGraph, Pair<A, B> firstVertex, Pair<A, B> secondVertex) {
        this.firstGraph = firstGraph;
        this.secondGraph = secondGraph;
        this.firstVertexIndex = firstVertex;
        this.secondVertexIndex = secondVertex;
    }

    public static <A, B>  GraphProductCondition<A, B> newInstance(Graph<A> firstGraph, Graph<B> secondGraph,
                                                                  Pair<A, B> firstVertex, Pair<A, B> secondVertex) {
        return new GraphProductCondition<>(firstGraph, secondGraph, firstVertex, secondVertex);
    }

    public boolean isFirstGraphHasEdgeWithFirstIndexes() {
        return firstGraph.findEdge(firstVertexIndex.getFirst(), secondVertexIndex.getFirst()).isPresent();
    }

    public boolean isSecondGraphHasEdgeWithSecondIndexes() {
        return secondGraph.findEdge(firstVertexIndex.getSecond(), secondVertexIndex.getSecond()).isPresent();
    }

    public boolean isSecondIndexesAreEquals() {
        return firstVertexIndex.getSecond().equals(secondVertexIndex.getSecond());
    }

    public boolean isFirstIndexesAreEquals() {
        return firstVertexIndex.getFirst().equals(secondVertexIndex.getFirst());
    }
}
