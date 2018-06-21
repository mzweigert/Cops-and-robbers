package com.graphrodite.internal.wrapper;


import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;

public class ProductConditionWrapper<A, B> {

    private final Graph<A> firstGraph;
    private final Graph<B> secondGraph;
    private final Pair<A, B> firstVertexIndex;
    private final Pair<A, B> secondVertexIndex;

    private ProductConditionWrapper(Graph<A> firstGraph, Graph<B> secondGraph, Pair<A, B> firstVertex, Pair<A, B> secondVertex) {
        this.firstGraph = firstGraph;
        this.secondGraph = secondGraph;
        this.firstVertexIndex = firstVertex;
        this.secondVertexIndex = secondVertex;
    }

    public static <A, B> ProductConditionWrapper<A, B> newInstance(Graph<A> firstGraph, Graph<B> secondGraph,
                                                                   Pair<A, B> firstVertex, Pair<A, B> secondVertex) {
        return new ProductConditionWrapper<>(firstGraph, secondGraph, firstVertex, secondVertex);
    }

    public boolean isFirstGraphHasEdgeWithFirstIndexes() {
        return firstGraph.findEdge(firstVertexIndex.getFirst(), secondVertexIndex.getFirst()).isPresent();
    }

    public boolean isSecondGraphHasEdgeWithSecondIndexes() {
        return secondGraph.findEdge(firstVertexIndex.getSecond(), secondVertexIndex.getSecond()).isPresent();
    }

    public boolean areSecondIndexesAreEquals() {
        return firstVertexIndex.getSecond().equals(secondVertexIndex.getSecond());
    }

    public boolean areFirstIndexesAreEquals() {
        return firstVertexIndex.getFirst().equals(secondVertexIndex.getFirst());
    }
}
