package com.graphrodite.factory;

import com.graphrodite.model.Graph;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GraphTemplateTest {

    @Test
    public void givenNDepthSize_whenCreateSequentialGraphWithGivenDepth_thenSuccessCreateSequentialGraphWithGivenDepth() {
        //GIVEN
        int n = 25;

        //WHEN
        Graph<Integer> graph = GraphTemplate.getInstance().createSequentialGraphWithDepth(n);

        //THEN
        assertThat(graph.getVertices().size()).isEqualTo(n * 3 + 3);
        assertThat(graph.containsCycle()).isTrue();
    }

    @Test
    public void givenNVerticesSize_whenCreatePathWithGivenLength_thenSuccessCreatePathWithGivenLength() {
        //GIVEN
        int n = 25;

        //WHEN
        Graph<Integer> graph = GraphTemplate.getInstance().createPathWithGivenLength(n);

        //THEN
        assertThat(graph.getVertices().size()).isEqualTo(n);
        assertThat(graph.getEdges().size()).isEqualTo(n - 1);
        assertThat(graph.containsCycle()).isFalse();
    }

    @Test
    public void givenNVerticesSize_whenCreateTreeWithGivenLength_thenSuccessCreateTreeWithGivenLength() {
        //GIVEN
        int n = 50;

        //WHEN
        Graph<Integer> graph = GraphTemplate.getInstance().createTreeWithGivenLength(n);

        //THEN
        assertThat(graph.getVertices().size()).isEqualTo(n);
        assertThat(graph.getEdges().size()).isEqualTo(n - 1);
        assertThat(graph.containsCycle()).isFalse();
    }

    @Test
    public void givenNVerticesSize_whenCreateCycleWithGivenLength_thenSuccessCreateCycleWithGivenLength() {
        //GIVEN
        int n = 100;

        //WHEN
        Graph<Integer> graph = GraphTemplate.getInstance().createCycleWithGivenLength(n);

        //THEN
        assertThat(graph.getVertices().size()).isEqualTo(n);
        assertThat(graph.getEdges().size()).isEqualTo(n);
        assertThat(graph.containsCycle()).isTrue();
    }


    @Test
    public void givenNothing_whenGetPetersen_thenSuccessCreatePetersen() throws UnsupportedOperationException {
        //GIVEN

        //WHEN
        Graph<Integer> graph = GraphTemplate.getInstance().getPetersenGraph();

        //THEN
        assertThat(graph.getVertices().size()).isEqualTo(10);
        assertThat(graph.getEdges().size()).isEqualTo(15);
    }

    @Test
    public void givenNothing_whenGetDodecahedron_thenSuccessCreateDodecahedron() throws UnsupportedOperationException {
        //GIVEN

        //WHEN
        Graph<Integer> graph = GraphTemplate.getInstance().getDodecahedronGraph();

        //THEN
        assertThat(graph.getVertices().size()).isEqualTo(20);
        assertThat(graph.getEdges().size()).isEqualTo(30);
    }
}