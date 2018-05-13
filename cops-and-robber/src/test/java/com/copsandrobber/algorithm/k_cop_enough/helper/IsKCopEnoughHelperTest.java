package com.copsandrobber.algorithm.k_cop_enough.helper;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicates;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.factory.GraphProductFactory;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Pair;
import com.graphrodite.model.Vertex;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IsKCopEnoughHelperTest {

    private Graph<Integer> graph;
    private GraphProductFactory factory = new GraphProductFactory();

    @InjectMocks
    private IsKCopEnoughHelper helper;

    @Before
    public void setUp() {
        graph = Graph.newInstance();
    }

    @Test
    public void givenAnyGraph_whenGetClosedNeighbourhoodUnion_thenReturnUnionOfClosedNeighbourhood() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Set<Vertex<Integer>> vertices = new HashSet<>();
        Vertex<Integer> anyVertex_1 = graph.findVertex(3).get();
        vertices.add(anyVertex_1);
        Vertex<Integer> anyVertex_2 = graph.findVertex(8).get();
        vertices.add(anyVertex_2);

        // WHEN
        Set<Vertex<Integer>> result = helper.getClosedNeighbourhoodUnion(vertices);

        // THEN
        assertThat(result.containsAll(anyVertex_1.getClosedNeighbourhood())).isTrue();
        assertThat(result.containsAll(anyVertex_2.getClosedNeighbourhood())).isTrue();
    }

    @Test
    public void givenPathGraph_whenFindSafeZonesForVertices_thenReturnSafeZones() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5, 6, 7, 8);
        Vertex<Integer> vertex_1 = graph.findVertex(2).get();
        Vertex<Integer> vertex_2 = graph.findVertex(4).get();
        List<Vertex<Integer>> vertices = Lists.newArrayList(vertex_1, vertex_2);
        // WHEN
        Map<Vertex<Integer>, Set<Vertex<Integer>>> safeZones = helper.findSafeZonesForVertices(vertices, graph);
        // THEN
        Set<Vertex<Integer>> expectedSafeZoneForFirstVertex = Sets.newHashSet(Lists.newArrayList(
                new Vertex<>(4), new Vertex<>(5), new Vertex<>(6),
                new Vertex<>(7), new Vertex<>(8)));

        assertThat(safeZones.get(vertex_1)).isEqualTo(expectedSafeZoneForFirstVertex);

        Set<Vertex<Integer>> expectedSafeZoneForSecondVertex = Sets.newHashSet(Lists.newArrayList(
                new Vertex<>(1), new Vertex<>(2), new Vertex<>(6),
                new Vertex<>(7), new Vertex<>(8)));

        assertThat(safeZones.get(vertex_2)).isEqualTo(expectedSafeZoneForSecondVertex);

    }

    @Test
    public void givenC5Graph_whenFindSafeZonesForVertices_thenReturnSafeZones() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5);
        graph.addPath(1, 5);
        Vertex<Integer> vertex_1 = graph.findVertex(1).get();
        Vertex<Integer> vertex_2 = graph.findVertex(4).get();
        List<Vertex<Integer>> vertices = Lists.newArrayList(vertex_1, vertex_2);
        // WHEN
        Map<Vertex<Integer>, Set<Vertex<Integer>>> safeZones = helper.findSafeZonesForVertices(vertices, graph);
        // THEN
        Set<Vertex<Integer>> expectedSafeZoneForFirstVertex = Sets.newHashSet(Lists.newArrayList(
                new Vertex<>(3), new Vertex<>(4)));

        assertThat(safeZones.get(vertex_1)).isEqualTo(expectedSafeZoneForFirstVertex);

        Set<Vertex<Integer>> expectedSafeZoneForSecondVertex = Sets.newHashSet(Lists.newArrayList(
                new Vertex<>(1), new Vertex<>(2)));

        assertThat(safeZones.get(vertex_2)).isEqualTo(expectedSafeZoneForSecondVertex);

    }

    @Test
    public void givenC5Graph_whenFindSafeZonesForVertex_thenReturnSafeZones() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5);
        graph.addPath(1, 5);
        Vertex<Integer> vertex_1 = graph.findVertex(1).get();
        // WHEN
        Set<Vertex<Integer>> safeZonesForVertex = helper.findSafeZonesForVertex(vertex_1, graph);
        // THEN
        Set<Vertex<Integer>> expectedSafeZoneForFirstVertex = Sets.newHashSet(Lists.newArrayList(
                new Vertex<>(3), new Vertex<>(4)));

        assertThat(safeZonesForVertex).isEqualTo(expectedSafeZoneForFirstVertex);
    }

    @Test
    public void givenC5ProductGraphGraph_whenFindSafeZonesForVertex_thenReturnIntersectionSafeZones() throws EdgeAlreadyExistException, PathContainsDuplicates {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5);
        graph.addPath(1, 5);
        Graph<Pair<Integer, Integer>> product = factory.createCartesianProduct(graph.clone(), graph.clone());

        Vertex vertex = product.findVertex(new Pair<>(1, 5)).get();
        // WHEN
        Set<Vertex<Integer>> safeZonesForVertex = helper.findSafeZonesForVertex(vertex, graph);
        // THEN
        Set<Vertex<Integer>> expectedSafeZoneForFirstVertex = Sets.newHashSet(Lists.newArrayList(new Vertex<>(3)));

        assertThat(safeZonesForVertex).isEqualTo(expectedSafeZoneForFirstVertex);
    }

    @Test
    public void givenIndentedVertex_whenExtractVertices_thenReturnSetOfExtractedVertices() throws VertexAlreadyExistException {
        // GIVEN
        Graph<Integer> graph = Graph.newInstance();
        graph.addVertices(3, 2, 5, 6);

        Vertex vertex = new Vertex(new Pair<>(new Pair<>(new Pair<>(3, 2), 5), 6));
        // WHEN
        Set<Vertex<Integer>> result = helper.extractVertices(vertex, graph);
        // THEN
        Set<Vertex<Integer>> expectedSafeZoneForFirstVertex = Sets.newHashSet(Lists.newArrayList(
                new Vertex<>(3),
                new Vertex<>(2),
                new Vertex<>(5),
                new Vertex<>(6)
        ));

        assertThat(result).isEqualTo(expectedSafeZoneForFirstVertex);
    }

}