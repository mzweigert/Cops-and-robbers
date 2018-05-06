package com.copsandrobber.helper;

import com.graphrodite.exception.EdgeAlreadyExistException;
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
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class AlgorithmHelperTest {

    private Graph<Integer> graph;

    @InjectMocks
    private GraphProductFactory factory = new GraphProductFactory();

    @InjectMocks
    private AlgorithmHelper algorithmHelper;

    @Before
    public void setUp() {
        graph = Graph.newInstance();
    }

    @Test
    public void givenAnyGraph_whenGetClosedNeighbourhoodUnion_thenReturnUnionOfClosedNeighbourhood() throws EdgeAlreadyExistException {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Set<Vertex<Integer>> vertices = new HashSet<>();
        Vertex<Integer> anyVertex_1 = graph.findVertex(3).get();
        vertices.add(anyVertex_1);
        Vertex<Integer> anyVertex_2 = graph.findVertex(8).get();
        vertices.add(anyVertex_2);

        // WHEN
        Set<Vertex<Integer>> result = algorithmHelper.getClosedNeighbourhoodUnion(vertices);

        // THEN
        assertThat(result.containsAll(anyVertex_1.getClosedNeighbourhood())).isTrue();
        assertThat(result.containsAll(anyVertex_2.getClosedNeighbourhood())).isTrue();
    }

    @Test
    public void givenTwoDominatingVertex_whenIsFirstDominatingSecond_thenReturnFalse() throws EdgeAlreadyExistException {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Vertex<Integer> dominating_1 = graph.findVertex(2).get();
        Vertex<Integer> dominating_2 = graph.findVertex(9).get();

        // WHEN
        boolean result = algorithmHelper.isFirstDominatingSecond(dominating_1, dominating_2);
        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenDominatingAndDominatedVertex_whenIsFirstDominatingSecond_thenReturnFalse() throws EdgeAlreadyExistException {
        // GIVEN
        graph.addPath(1, 2, 3);
        graph.addEdge(1, 3);
        Vertex<Integer> dominating = graph.findVertex(3).get();
        Vertex<Integer> dominated = graph.findVertex(2).get();

        // WHEN
        boolean result = algorithmHelper.isFirstDominatingSecond(dominating, dominated);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenSameMap_whenSafeZoneChanged_thenReturnFalse() {
        // GIVEN
        Map<Vertex<Integer>, Set<Vertex<Integer>>> firstMap = new HashMap<>();
        Set<Vertex<Integer>> set = new HashSet<>();
        set.add(new Vertex<>(2));
        set.add(new Vertex<>(3));
        set.add(new Vertex<>(4));
        firstMap.put(new Vertex<>(1), set);

        Map<Vertex<Integer>, Integer> secondMap = firstMap.entrySet().stream()
                .collect(Collectors.toMap(
                        k -> k.getKey(),
                        v -> v.getValue().size()
                ));

        // WHEN
        boolean result = algorithmHelper.safeZoneChanged(firstMap, secondMap);
        // THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenDifferentMap_whenIsMapEquals_thenReturnFalse() {
        // GIVEN
        Map<Vertex<Integer>, Set<Vertex<Integer>>> firstMap = new HashMap<>();
        Set<Vertex<Integer>> set_1 = new HashSet<>();
        set_1.add(new Vertex<>(2));
        set_1.add(new Vertex<>(3));
        set_1.add(new Vertex<>(4));
        firstMap.put(new Vertex<>(1), set_1);

        Map<Vertex<Integer>, Integer> secondMap = new HashMap<>();
        secondMap.put(new Vertex<>(1), 2);

        // WHEN
        boolean result = algorithmHelper.safeZoneChanged(firstMap, secondMap);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenPathGraph_whenFindSafeZonesForVertices_thenReturnSafeZones() throws EdgeAlreadyExistException {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5, 6, 7, 8);
        Vertex<Integer> vertex_1 = graph.findVertex(2).get();
        Vertex<Integer> vertex_2 = graph.findVertex(4).get();
        List<Vertex<Integer>> vertices = Lists.newArrayList(vertex_1, vertex_2);
        // WHEN
        Map<Vertex<Integer>, Set<Vertex<Integer>>> safeZones = algorithmHelper.findSafeZonesForVertices(vertices, graph);
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
    public void givenC5Graph_whenFindSafeZonesForVertices_thenReturnSafeZones() throws EdgeAlreadyExistException {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5);
        graph.addPath(1, 5);
        Vertex<Integer> vertex_1 = graph.findVertex(1).get();
        Vertex<Integer> vertex_2 = graph.findVertex(4).get();
        List<Vertex<Integer>> vertices = Lists.newArrayList(vertex_1, vertex_2);
        // WHEN
        Map<Vertex<Integer>, Set<Vertex<Integer>>> safeZones = algorithmHelper.findSafeZonesForVertices(vertices, graph);
        // THEN
        Set<Vertex<Integer>> expectedSafeZoneForFirstVertex = Sets.newHashSet(Lists.newArrayList(
                new Vertex<>(3), new Vertex<>(4)));

        assertThat(safeZones.get(vertex_1)).isEqualTo(expectedSafeZoneForFirstVertex);

        Set<Vertex<Integer>> expectedSafeZoneForSecondVertex = Sets.newHashSet(Lists.newArrayList(
                new Vertex<>(1), new Vertex<>(2)));

        assertThat(safeZones.get(vertex_2)).isEqualTo(expectedSafeZoneForSecondVertex);

    }

    @Test
    public void givenC5Graph_whenFindSafeZonesForVertex_thenReturnSafeZones() throws EdgeAlreadyExistException {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5);
        graph.addPath(1, 5);
        Vertex<Integer> vertex_1 = graph.findVertex(1).get();
        // WHEN
        Set<Vertex<Integer>> safeZonesForVertex = algorithmHelper.findSafeZonesForVertex(vertex_1, graph);
        // THEN
        Set<Vertex<Integer>> expectedSafeZoneForFirstVertex = Sets.newHashSet(Lists.newArrayList(
                new Vertex<>(3), new Vertex<>(4)));

        assertThat(safeZonesForVertex).isEqualTo(expectedSafeZoneForFirstVertex);
    }

    @Test
    public void givenC5ProductGraphGraph_whenFindSafeZonesForVertex_thenReturnIntersectionSafeZones() throws EdgeAlreadyExistException {
        // GIVEN
        graph.addPath(1, 2, 3, 4, 5);
        graph.addPath(1, 5);
        Graph<Pair<Integer, Integer>> product = factory.createCartesianProduct(graph.clone(), graph.clone());

        Vertex vertex = product.findVertex(new Pair<>(1, 5)).get();
        // WHEN
        Set<Vertex<Integer>> safeZonesForVertex = algorithmHelper.findSafeZonesForVertex(vertex, graph);
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
        Set<Vertex<Integer>> result = algorithmHelper.extractVertices(vertex, graph);
        // THEN
        Set<Vertex<Integer>> expectedSafeZoneForFirstVertex = Sets.newHashSet(Lists.newArrayList(
                new Vertex<>(3),
                new Vertex<>(2),
                new Vertex<>(5),
                new Vertex<>(6)
        ));

        assertThat(result).isEqualTo(expectedSafeZoneForFirstVertex);
    }

    @Test
    public void givenSafeZonesMap_whenCreateSafeZonesCountMap_thenReturnSafeZonesCountMap() {
        // GIVEN
        Map<Vertex<Integer>, Set<Vertex<Integer>>> safeZones = new HashMap<>();
        Set<Vertex<Integer>> set_1 = new HashSet<>();
        set_1.add(new Vertex<>(2));
        set_1.add(new Vertex<>(3));
        set_1.add(new Vertex<>(4));
        safeZones.put(new Vertex<>(1), set_1);
        // WHEN
        Map<Vertex<Integer>, Integer> result = algorithmHelper.createSafeZonesCountMap(safeZones);
        // THEN
        assertThat(result.get(new Vertex<>(1))).isEqualTo(3);
    }
}