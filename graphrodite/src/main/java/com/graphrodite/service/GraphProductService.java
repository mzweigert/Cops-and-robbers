package com.graphrodite.service;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.model.Pair;
import com.graphrodite.enums.GraphProduct;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;
import com.graphrodite.shared.GraphProductCondition;

import java.util.ListIterator;

public class GraphProductService {

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
            for (Vertex<A> firstVertex : firstGraph.getVertices()) {
                for (Vertex<B> secondVertex : secondGraph.getVertices()) {
                    resultGraph.addVertex(new Pair<>(firstVertex, secondVertex));
                }
            }
            ListIterator<Vertex<Pair<A, B>>> vertexIterator = resultGraph.getVertices().listIterator();
            while (vertexIterator.hasNext()) {
                Vertex<Pair<A, B>> firstVertex = vertexIterator.next();
                ListIterator<Vertex<Pair<A, B>>> secondIterator = resultGraph.getVertices().listIterator(vertexIterator.nextIndex());
                while (secondIterator.hasNext()) {
                    Vertex<Pair<A, B>> nextVertex = secondIterator.next();
                    GraphProductCondition<A, B> condition = GraphProductCondition.newInstance(firstGraph, secondGraph, firstVertex.getIndex(), nextVertex.getIndex());
                    if (graphProduct.getCondition(condition)) {
                        resultGraph.addEdge(firstVertex.getIndex(), nextVertex.getIndex());
                    }
                }
            }
            return resultGraph;
        } catch (VertexAlreadyExistException | EdgeAlreadyExistException e) {
            e.printStackTrace();
        }
        return null;

    }
}
