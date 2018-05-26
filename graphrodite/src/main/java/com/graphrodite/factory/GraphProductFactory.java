package com.graphrodite.factory;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.internal.enums.GraphProduct;
import com.graphrodite.internal.wrapper.ProductConditionWrapper;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GraphProductFactory {

    public <A, B> Graph createStrongProduct(Graph<A> firstGraph, Graph<B> secondGraph) {
        return createProduct(firstGraph, secondGraph, GraphProduct.STRONG);
    }

    public <A, B> Graph createCartesianProduct(Graph<A> firstGraph, Graph<B> secondGraph) {
        return createProduct(firstGraph, secondGraph, GraphProduct.CARTESIAN);
    }

    public <A, B> Graph createCategoricalProduct(Graph<A> firstGraph, Graph<B> secondGraph) {
        return createProduct(firstGraph, secondGraph, GraphProduct.CATEGORICAL);
    }

    public <A, B> Graph createLexicographicalProduct(Graph<A> firstGraph, Graph<B> secondGraph) {
        return createProduct(firstGraph, secondGraph, GraphProduct.LEXICOGRAPHICAL);
    }

    private <A, B> Graph<Pair<A, B>> createProduct(Graph<A> firstGraph, Graph<B> secondGraph, GraphProduct graphProduct) {
        try {
            Graph<Pair<A, B>> resultGraph = Graph.newInstance();
            List<Vertex<Pair<A, B>>> vertices = new LinkedList<>();
            for (Vertex<A> firstVertex : firstGraph.getVertices()) {
                for (Vertex<B> secondVertex : secondGraph.getVertices()) {
                    Pair<A, B> vertex = new Pair<>(firstVertex.getIndex(), secondVertex.getIndex());
                    vertices.add(Vertex.create(vertex));
                }
            }
            ListIterator<Vertex<Pair<A, B>>> vertexIterator = vertices.listIterator();
            while (vertexIterator.hasNext()) {
                Vertex<Pair<A, B>> firstVertex = vertexIterator.next();
                ListIterator<Vertex<Pair<A, B>>> secondIterator = vertices.listIterator(vertexIterator.nextIndex());
                while (secondIterator.hasNext()) {
                    Vertex<Pair<A, B>> nextVertex = secondIterator.next();
                    ProductConditionWrapper<A, B> condition =
                            ProductConditionWrapper.newInstance(firstGraph, secondGraph, firstVertex.getIndex(), nextVertex.getIndex());

                    if (graphProduct.getCondition(condition)) {
                        resultGraph.addEdge(firstVertex.getIndex(), nextVertex.getIndex());
                    }
                }
            }
            return resultGraph;
        } catch (EdgeAlreadyExistException e) {
            e.printStackTrace();
        }
        return null;
    }
}
