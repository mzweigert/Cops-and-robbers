package com.graphrodite.factory;

import com.graphrodite.exception.NeighborAlreadyExistException;
import com.graphrodite.internal.enums.GraphProduct;
import com.graphrodite.internal.wrapper.ProductConditionWrapper;
import com.graphrodite.model.Edge;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;

import java.util.*;

/**
 * Class which creating graph products.
 */
public class GraphProductFactory {

    /**
     * Method creating k-repeated strong product of given graph.
     * @param graph factor of k-strong product.
     * @param k counter of repeated graph product creating.
     * @return Graph k-repeated strong product graph.
     */
    public <E> Graph createKStrongProduct(Graph<E> graph, int k) {
        Graph<E> resultGraph = graph.clone();
        for (int i = 1; i < k; i++) {
            //noinspection unchecked
            resultGraph = createStrongProduct(resultGraph, graph);
        }
        return resultGraph;
    }

    /**
     * Method create strong product of given as parameters graphs.
     * @param firstGraph first graph factor.
     * @param secondGraph second graph factor.
     * @return Graph strong product of graph.
     */
    public <A, B> Graph createStrongProduct(Graph<A> firstGraph, Graph<B> secondGraph) {
        return createProduct(firstGraph, secondGraph, GraphProduct.STRONG);
    }

    /**
     * Method create cartesian product of given as parameters graphs.
     * @param firstGraph first graph factor.
     * @param secondGraph second graph factor.
     * @return Graph cartesian product of graph.
     */
    public <A, B> Graph createCartesianProduct(Graph<A> firstGraph, Graph<B> secondGraph) {
        return createProduct(firstGraph, secondGraph, GraphProduct.CARTESIAN);
    }

    /**
     * Method create categorical product of given as parameters graphs.
     * @param firstGraph first graph factor.
     * @param secondGraph second graph factor.
     * @return Graph categorical product of graph.
     */
    public <A, B> Graph createCategoricalProduct(Graph<A> firstGraph, Graph<B> secondGraph) {
        return createProduct(firstGraph, secondGraph, GraphProduct.CATEGORICAL);
    }

    /**
     * Method create lexicographical product of given as parameters graphs.
     * @param firstGraph first graph factor.
     * @param secondGraph second graph factor.
     * @return Graph lexicographical product of graph.
     */
    public <A, B> Graph createLexicographicalProduct(Graph<A> firstGraph, Graph<B> secondGraph) {
        return createProduct(firstGraph, secondGraph, GraphProduct.LEXICOGRAPHICAL);
    }

    private <A, B> Graph<Pair<A, B>> createProduct(Graph<A> firstGraph, Graph<B> secondGraph, GraphProduct graphProduct) {
        try {
            Set<Vertex<Pair<A, B>>> vertices = createGraphProductVertices(firstGraph, secondGraph);
            Set<Edge<Pair<A, B>>> edges = createGraphProductEdges(firstGraph, secondGraph, graphProduct, vertices);
            return Graph.newInstance(vertices, edges);
        } catch (NeighborAlreadyExistException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <A, B> Set<Vertex<Pair<A, B>>> createGraphProductVertices(Graph<A> firstGraph, Graph<B> secondGraph) {
        Set<Vertex<Pair<A, B>>> verticesOfGraphProduct = new LinkedHashSet<>();
        for (Vertex<A> firstVertex : firstGraph.getVertices()) {
            for (Vertex<B> secondVertex : secondGraph.getVertices()) {
                Pair<A, B> vertex = new Pair<>(firstVertex.getIndex(), secondVertex.getIndex());
                verticesOfGraphProduct.add(Vertex.create(vertex));
            }
        }
        return verticesOfGraphProduct;
    }

    private <A, B> Set<Edge<Pair<A, B>>> createGraphProductEdges(Graph<A> firstGraph, Graph<B> secondGraph, GraphProduct graphProduct, Set<Vertex<Pair<A, B>>> vertices) throws NeighborAlreadyExistException {
        Set<Edge<Pair<A, B>>> edgesOfGraphProduct = new LinkedHashSet<>();
        for (Vertex<Pair<A, B>> firstVertex : vertices) {
            for (Vertex<Pair<A, B>> nextVertex : vertices) {
                ProductConditionWrapper<A, B> condition =
                        ProductConditionWrapper.newInstance(firstGraph, secondGraph, firstVertex.getIndex(), nextVertex.getIndex());

                Edge<Pair<A, B>> edgeCandidate = Edge.create(firstVertex, nextVertex);
                if (graphProduct.getCondition(condition) && !edgesOfGraphProduct.contains(edgeCandidate)) {
                    edgesOfGraphProduct.add(edgeCandidate);
                    firstVertex.createNeighborhoodWith(nextVertex);
                }
            }
        }
        return edgesOfGraphProduct;
    }


}
