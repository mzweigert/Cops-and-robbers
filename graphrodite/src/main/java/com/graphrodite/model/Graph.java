package com.graphrodite.model;


import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicatesException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.internal.service.GraphService;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Class representing graph - basic mathematical object from graph theory.
 * @param <E> type of vertices in graph.
 */
public class Graph<E> implements Serializable {

    private Set<Vertex<E>> vertices;
    private Set<Edge<E>> edges;
    private GraphService<E> graphService;

    private Graph() {
        this(new LinkedHashSet<>(), new LinkedHashSet<>());
    }

    private Graph(Set<Vertex<E>> vertices, Set<Edge<E>> edges) {
        this.vertices = vertices;
        this.edges = edges;
        this.graphService = new GraphService<>(this.vertices, this.edges);
    }

    /**
     * Method create and return new instance of graph with empty vertices and edges sets.
     * @return Graph&lt;E&gt; new instance.
     */
    public static <E> Graph<E> newInstance() {
        return new Graph<>();
    }

    /**
     * Method create and return new instance of graph with given as parameters vertices and edges sets.
     * @param vertices vertices set.
     * @param edges edges set.
     * @return Graph&lt;E&gt; new instance.
     */
    public static <E> Graph<E> newInstance(Set<Vertex<E>> vertices, Set<Edge<E>> edges) {
        Optional<Edge<E>> incorrectEdge = edges.stream()
                .filter(e -> !vertices.contains(e.getFirst()) || !vertices.contains(e.getSecond()))
                .findFirst();
        if(incorrectEdge.isPresent()){
            throw new IllegalStateException("Edges contains not existing vertex.");
        }
        return new Graph<>(vertices, edges);
    }


    /**
     * Method create petersen graph with vertices type integer.
     * @return Graph&lt;Integer&gt; new petersen graph.
     */
    public static Graph<Integer> petersen() {
        Graph<Integer> petersen = Graph.newInstance();
        try {
            petersen.addPath(0, 1, 2, 3, 4);
            petersen.addPath(0, 4);
            petersen.addEdgesToVertex(5, 0, 7, 8);
            petersen.addEdgesToVertex(6, 1, 8, 9);
            petersen.addEdgesToVertex(7, 2, 9);
            petersen.addEdgesToVertex(8, 3);
            petersen.addEdgesToVertex(9, 4);
        } catch (EdgeAlreadyExistException | PathContainsDuplicatesException e) {
            e.printStackTrace();
        }
        return petersen;
    }

    /**
     * Method create dodecahedron graph with vertices type integer.
     * @return Graph&lt;Integer&gt; new dodecahedron graph.
     */
    public static Graph<Integer> dodecahedron() {
        Graph<Integer> dodecahedron = Graph.newInstance();
        try {
            dodecahedron.addPath(0, 1, 2, 3, 4);
            dodecahedron.addPath(0, 4);

            dodecahedron.addPath(5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
            dodecahedron.addPath(5, 14);

            dodecahedron.addPath(15, 16, 17, 18, 19);
            dodecahedron.addPath(15, 19);

            dodecahedron.addEdge(0, 5);
            dodecahedron.addEdge(1, 7);
            dodecahedron.addEdge(2, 9);
            dodecahedron.addEdge(3, 11);
            dodecahedron.addEdge(4, 13);

            dodecahedron.addEdge(6, 16);
            dodecahedron.addEdge(8, 17);
            dodecahedron.addEdge(10, 18);
            dodecahedron.addEdge(12, 19);
            dodecahedron.addEdge(14, 15);

        } catch (EdgeAlreadyExistException | PathContainsDuplicatesException e) {
            e.printStackTrace();
        }
        return dodecahedron;
    }

    /**
     * Method create edge between two vertices given as parameter.
     * Method use rules of {@link Edge#equals(Object)} method,
     * and throw exception even if edge exist with reversed order of given indexes.
     * @param first first vertex index. If vertex doesn't exist, method create it.
     * @param second second vertex index. If vertex doesn't exist, method create it.
     * @return Edge&lt;E&gt; created edge
     * @throws EdgeAlreadyExistException throw when edge exist in graph edges set.
     */
    public Edge<E> addEdge(E first, E second) throws EdgeAlreadyExistException {
        return graphService.addEdge(first, second);
    }

    /**
     * Method create edges between first given index parameter and other parameters.
     * @param first first vertex index. If vertex doesn't exist, method create it.
     * @param neighbors neighbors of vertex with given as first parameter index.
     * @return Set&lt;Edge&lt;E&gt;&gt; created edges set.
     * @throws EdgeAlreadyExistException throw when edge exist in graph edges set.
     */
    @SafeVarargs
    public final Set<Edge<E>> addEdgesToVertex(E first, E... neighbors) throws EdgeAlreadyExistException {
        return graphService.addEdgesToVertex(first, neighbors);
    }

    /**
     * Method create vertex in graph with given index.
     * @param index vertex index. If vertex doesn't exist, method create it.
     * @return Vertex&lt;E&gt;&gt; created vertex.
     * @throws VertexAlreadyExistException throw when vertex with given index exist in graph vertices set.
     */
    public Vertex<E> addVertex(E index) throws VertexAlreadyExistException {
        return graphService.addVertex(index);
    }

    /**
     * Method create vertices in graph with given indexes.
     * @param indexes vertices indexes. If vertices don't exist, method create them.
     * @return Set&lt;Vertex&lt;E&gt;&gt; created vertices.
     * @throws VertexAlreadyExistException throw when vertex with any given index exist in graph vertices set.
     */
    @SafeVarargs
    public final Set<Vertex<E>> addVertices(E... indexes) throws VertexAlreadyExistException {
        return graphService.addVertices(indexes);
    }

    /**
     * Method add path to graph.
     * @param indexes vertices indexes. If vertices don't exist, method create them.
     * @return Set&lt;Vertex&lt;E&gt;&gt; created vertices in path.
     * @throws EdgeAlreadyExistException throw when edges between any given vertices indexes in graph exist.
     * @throws PathContainsDuplicatesException throw when indexes contains duplicated. Path has unique vertex.
     */
    @SafeVarargs
    public final Set<Vertex<E>> addPath(E... indexes) throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        return graphService.addPath(indexes);
    }

    /**
     * Method find edge in graph.
     * Method use {@link Edge#equals(Object)} method,
     * and return edge even with reversed order of given indexes.
     * @param first first index of vertex.
     * @param second second index of vertex.
     * @return Optional&lt;Edge&lt;E&gt;&gt; filled optional object with edge if method found it, empty otherwise.
     */
    public Optional<Edge<E>> findEdge(E first, E second) {
        if (containsEdge(first, second)) {
            return graphService.findEdge(e -> e.containsVertices(first, second));
        }
        return Optional.empty();
    }

    /**
     * Method answer for question if the graph contains a edge.
     * Method use {@link Edge#equals(Object)} method,
     * and method return true, even if exist edge with reversed order of given indexes.
     * @param first first index of vertex.
     * @param second second index of vertex.
     * @return true if edge in graph exist, false otherwise.
     */
    public boolean containsEdge(E first, E second) {
        return graphService.containsEdge(first, second);
    }

    /**
     * Method find vertex in graph.
     * @param index index of searched vertex.
     * @return Optional&lt;Vertex&lt;E&gt;&gt; filled optional object with vertex if method found it, empty otherwise.
     */
    public Optional<Vertex<E>> findVertex(E index) {
        return graphService.findVertex(v -> v.getIndex().equals(index));
    }

    /**
     * Method answer for question if the graph contains a vertex.
     * @param index index of searched vertex.
     * @return true if vertex in graph exist, false otherwise.
     */
    public boolean containsVertex(E index) {
        return graphService.containsVertex(index);
    }

    /**
     * Method return copy of graph vertices.
     * @return Set&lt;Vertex&lt;E&gt;&gt; copy of graph vertices set.
     */
    public Set<Vertex<E>> getVertices() {
        return new LinkedHashSet<>(vertices);
    }

    /**
     * Method return copy of graph edges.
     * @return Set&lt;Vertex&lt;E&gt;&gt; copy of graph edges set.
     */
    public Set<Edge<E>> getEdges() {
        return new LinkedHashSet<>(edges);
    }

    /**
     * Method clone graph.
     * @return Graph&lt;E&gt; cloned graph.
     */
    public Graph<E> clone() {
        return (Graph<E>) SerializationUtils.clone(this);
    }
}
