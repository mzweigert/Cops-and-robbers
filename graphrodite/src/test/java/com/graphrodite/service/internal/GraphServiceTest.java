package com.graphrodite.service.internal;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.VertexAlreadyExistException;
import com.graphrodite.model.Graph;
import com.graphrodite.model.Vertex;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class GraphServiceTest {

    private GraphService<Integer> graphService;

    @Before
    public void setUp() {
        Graph<Integer> graph = Graph.newInstance();
        graphService = new GraphService<>(graph.getVertices(), graph.getEdges());
    }

    @Test
    public void givenSomeIndexAndAddVertex_whenContainsVertex_thenReturnTrue() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        graphService.addVertex(someIndex);
        //WHEN
        boolean result = graphService.containsVertex(someIndex);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void givenSomeIndexAndAddVertex_whenContainsVertex_thenReturnFalse() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        graphService.addVertex(someIndex);
        //WHEN
        boolean result = graphService.containsVertex(2);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void givenSomeIndex_whenCreateOrFindVertex_thenReturnVertex() {
        //GIVEN
        Integer someIndex = 1;
        //WHEN
        Vertex<Integer> result = graphService.findOrCreateVertex(someIndex);
        //THEN
        assertThat(result.getIndex()).isEqualTo(someIndex);
    }

    @Test
    public void givenSomeIndex_whenAddVertex_thenReturnVertex() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        //WHEN
        Vertex<Integer> result = graphService.addVertex(someIndex);
        //THEN
        assertThat(result.getIndex()).isEqualTo(someIndex);
    }

    @Test
    public void givenSomePredicate_whenFindVertex_thenReturnVertex() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        graphService.addVertex(someIndex);
        Predicate<Vertex> somePredicate = v -> v.getIndex().equals(someIndex);
        //WHEN
        Optional<Vertex<Integer>> result = graphService.findVertex(somePredicate);
        //THEN
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getIndex()).isEqualTo(someIndex);
    }

    @Test
    public void givenSomePredicate_whenFindEdge_thenReturnVertex() throws VertexAlreadyExistException {
        //GIVEN
        Integer someIndex = 1;
        graphService.addVertex(someIndex);
        Predicate<Vertex> somePredicate = v -> v.getIndex().equals(someIndex);
        //WHEN
        Optional<Vertex<Integer>> result = graphService.findVertex(somePredicate);
        //THEN
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getIndex()).isEqualTo(someIndex);
    }

    @Test
    public void givenDifferentVertex_whenCreateEdge_thenSuccessCreateEdge() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 2;
        //WHEN
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
        //THEN
        Optional<Vertex<Integer>> expectedVertex1 = graphService.findVertex(v -> v.getIndex().equals(firstVertexIndex));
        Optional<Vertex<Integer>> expectedVertex2 = graphService.findVertex(v -> v.getIndex().equals(secondVertexIndex));
        assertThat(expectedVertex1.isPresent()).isTrue();
        assertThat(expectedVertex2.isPresent()).isTrue();
        assertThat(expectedVertex1.get().getIndex()).isEqualTo(firstVertexIndex);
        assertThat(expectedVertex2.get().getIndex()).isEqualTo(secondVertexIndex);
        assertThat(expectedVertex1.get().getOpenNeighbourhood().stream().findFirst().get()).isEqualTo(expectedVertex2.get());
        assertThat(expectedVertex2.get().getOpenNeighbourhood().stream().findFirst().get()).isEqualTo(expectedVertex1.get());
        assertThat(graphService.containsEdge(expectedVertex1.get().getIndex(), expectedVertex2.get().getIndex())).isTrue();
    }

    @Test
    public void givenSameVertex_whenCreateEdge_thenSuccessCreateEdge() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;
        //WHEN
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
        //THEN
        Optional<Vertex<Integer>> expectedVertex1 = graphService.findVertex(v -> v.getIndex().equals(firstVertexIndex));
        Optional<Vertex<Integer>> expectedVertex2 = graphService.findVertex(v -> v.getIndex().equals(secondVertexIndex));
        assertThat(expectedVertex1.isPresent()).isTrue();
        assertThat(expectedVertex2.isPresent()).isTrue();
        assertThat(expectedVertex1.get().getIndex()).isEqualTo(firstVertexIndex);
        assertThat(expectedVertex2.get().getIndex()).isEqualTo(firstVertexIndex);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenDifferentVertex_whenAddExistingEdge_thenThrowException() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 2;
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
        //WHEN
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
    }

    @Test(expected = EdgeAlreadyExistException.class)
    public void givenSameVertex_whenAddExistingEdge_thenThrowException() throws EdgeAlreadyExistException {
        //GIVEN
        Integer firstVertexIndex = 1;
        Integer secondVertexIndex = 1;
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
        //WHEN
        graphService.addEdge(firstVertexIndex, secondVertexIndex);
    }
}