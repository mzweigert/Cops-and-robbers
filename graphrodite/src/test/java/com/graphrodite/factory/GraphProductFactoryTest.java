package com.graphrodite.factory;

import com.graphrodite.exception.EdgeAlreadyExistException;
import com.graphrodite.exception.PathContainsDuplicatesException;
import com.graphrodite.model.Graph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GraphProductFactoryTest {

    @InjectMocks
    private GraphProductFactory factory = new GraphProductFactory();

    @Test
    public void givenKEqual3AndP4Graph_whenCreateKStrongProduct_thenSuccessCreateKStrongProduct() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        int k = 3;
        Graph<String> P4 = Graph.newInstance();
        P4.addPath("a", "b", "c", "d");
        // WHEN

        Graph kStrongProduct = factory.createKStrongProduct(P4, k);
        // THEN
        int P4VerticesSize = P4.getVertices().size();
        int P4EdgesSize = P4.getEdges().size();
        Set kStrongProductVertices = kStrongProduct.getVertices();
        Set kStrongProductEdges = kStrongProduct.getEdges();

        int expectedStrongProductEdgesSize = P4EdgesSize;
        int expectedStrongProductVerticesSize = P4VerticesSize;
        for (int i = 1; i < k; i++) {
            expectedStrongProductEdgesSize = expectedStrongProductVerticesSize * P4EdgesSize
                    + P4VerticesSize * expectedStrongProductEdgesSize
                    + 2 * expectedStrongProductEdgesSize * P4EdgesSize;
            expectedStrongProductVerticesSize *= P4VerticesSize;
        }

        assertThat(kStrongProductVertices.size()).isGreaterThan(0);
        assertThat(kStrongProductVertices.size()).isEqualTo(expectedStrongProductVerticesSize);
        assertThat(kStrongProductEdges.size()).isGreaterThan(0);
        assertThat(kStrongProductEdges.size()).isEqualTo(expectedStrongProductEdgesSize);

    }

    @Test
    public void givenKEqual4AndC4Graph_whenCreateKStrongProduct_thenSuccessCreateKStrongProduct() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        int k = 4;
        Graph<String> C4 = Graph.newInstance();
        C4.addPath("a", "b", "c", "d");
        C4.addEdge("a", "d");
        // WHEN

        Graph kStrongProduct = factory.createKStrongProduct(C4, k);
        // THEN
        int P4VerticesSize = C4.getVertices().size();
        int P4EdgesSize = C4.getEdges().size();
        Set kStrongProductVertices = kStrongProduct.getVertices();
        Set kStrongProductEdges = kStrongProduct.getEdges();

        int expectedStrongProductEdgesSize = P4EdgesSize;
        int expectedStrongProductVerticesSize = P4VerticesSize;
        for (int i = 1; i < k; i++) {
            expectedStrongProductEdgesSize = expectedStrongProductVerticesSize * P4EdgesSize
                    + P4VerticesSize * expectedStrongProductEdgesSize
                    + 2 * expectedStrongProductEdgesSize * P4EdgesSize;
            expectedStrongProductVerticesSize *= P4VerticesSize;
        }

        assertThat(kStrongProductVertices.size()).isGreaterThan(0);
        assertThat(kStrongProductVertices.size()).isEqualTo(expectedStrongProductVerticesSize);
        assertThat(kStrongProductEdges.size()).isGreaterThan(0);
        assertThat(kStrongProductEdges.size()).isEqualTo(expectedStrongProductEdgesSize);

    }

    @Test
    public void givenC5AndP4Graph_whenCreateCartesianProduct_thenSuccessCreateCartesianProduct() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        Graph<Integer> C5 = Graph.newInstance();
        C5.addEdge(1, 2);
        C5.addEdge(2, 3);
        C5.addEdge(3, 4);
        C5.addEdge(4, 5);
        C5.addEdge(5, 1);

        Graph<String> P4 = Graph.newInstance();
        P4.addPath("a", "b", "c", "d");
        // WHEN

        Graph cartesianProduct = factory.createCartesianProduct(C5, P4);
        // THEN
        int expectedCartesianProductVerticesSize = C5.getVertices().size() * P4.getVertices().size();
        int expectedCartesianProductEdgesSize = C5.getEdges().size() * P4.getVertices().size() + P4.getEdges().size() * C5.getVertices().size();

        assertThat(C5.getVertices().size()).isGreaterThan(0);
        assertThat(P4.getVertices().size()).isGreaterThan(0);
        assertThat(C5.getEdges().size()).isGreaterThan(0);
        assertThat(P4.getEdges().size()).isGreaterThan(0);
        assertThat(cartesianProduct.getVertices().size()).isEqualTo(expectedCartesianProductVerticesSize);
        assertThat(cartesianProduct.getEdges().size()).isEqualTo(expectedCartesianProductEdgesSize);
    }

    @Test
    public void givenC5AndP4Graph_whenCreateCategoricalProduct_thenSuccessCreateCategoricalProduct() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        Graph<Integer> C5 = Graph.newInstance();
        C5.addEdge(1, 2);
        C5.addEdge(2, 3);
        C5.addEdge(3, 4);
        C5.addEdge(4, 5);
        C5.addEdge(5, 1);

        Graph<String> P4 = Graph.newInstance();
        P4.addPath("a", "b", "c", "d");
        // WHEN

        Graph categoricalProduct = factory.createCategoricalProduct(C5, P4);
        // THEN
        int expectedCategoricalProductVerticesSize = C5.getVertices().size() * P4.getVertices().size();
        int expectedCategoricalProductEdgesSize = 2 * C5.getEdges().size() * P4.getEdges().size();

        assertThat(C5.getVertices().size()).isGreaterThan(0);
        assertThat(P4.getVertices().size()).isGreaterThan(0);
        assertThat(C5.getEdges().size()).isGreaterThan(0);
        assertThat(P4.getEdges().size()).isGreaterThan(0);
        assertThat(categoricalProduct.getVertices().size()).isEqualTo(expectedCategoricalProductVerticesSize);
        assertThat(categoricalProduct.getEdges().size()).isEqualTo(expectedCategoricalProductEdgesSize);
    }


    @Test
    public void givenC5AndP4Graph_whenCreateStrongProduct_thenSuccessCreateStrongProduct() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        Graph<Integer> C5 = Graph.newInstance();
        C5.addEdge(1, 2);
        C5.addEdge(2, 3);
        C5.addEdge(3, 4);
        C5.addEdge(4, 5);
        C5.addEdge(5, 1);

        Graph<String> P4 = Graph.newInstance();
        P4.addPath("a", "b", "c", "d");
        // WHEN

        Graph strongProduct = factory.createStrongProduct(C5, P4);
        // THEN
        int expectedStrongProductVerticesSize = C5.getVertices().size() * P4.getVertices().size();
        int expectedStrongProductEdgesSize = C5.getVertices().size() * P4.getEdges().size() + P4.getVertices().size() * C5.getEdges().size()
                + 2 * C5.getEdges().size() * P4.getEdges().size();

        assertThat(C5.getVertices().size()).isGreaterThan(0);
        assertThat(P4.getVertices().size()).isGreaterThan(0);
        assertThat(C5.getEdges().size()).isGreaterThan(0);
        assertThat(P4.getEdges().size()).isGreaterThan(0);
        assertThat(strongProduct.getVertices().size()).isEqualTo(expectedStrongProductVerticesSize);
        assertThat(strongProduct.getEdges().size()).isEqualTo(expectedStrongProductEdgesSize);
    }

    @Test
    public void givenC5AndP4Graph_whenCreateLexicographicalProduct_thenSuccessCreateLexicographicalProduct() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        Graph<Integer> C5 = Graph.newInstance();
        C5.addEdge(1, 2);
        C5.addEdge(2, 3);
        C5.addEdge(3, 4);
        C5.addEdge(4, 5);
        C5.addEdge(5, 1);

        Graph<String> P4 = Graph.newInstance();
        P4.addPath("a", "b", "c", "d");
        // WHEN

        Graph lexicographicalProduct = factory.createLexicographicalProduct(C5, P4);
        // THEN
        int expectedLexicographicalProductVerticesSize = C5.getVertices().size() * P4.getVertices().size();
        int expectedLexicographicalProductEdgesSize = P4.getEdges().size() * C5.getVertices().size()
                + C5.getEdges().size() * (P4.getVertices().size() * P4.getVertices().size());

        assertThat(C5.getVertices().size()).isGreaterThan(0);
        assertThat(P4.getVertices().size()).isGreaterThan(0);
        assertThat(C5.getEdges().size()).isGreaterThan(0);
        assertThat(P4.getEdges().size()).isGreaterThan(0);
        assertThat(lexicographicalProduct.getVertices().size()).isEqualTo(expectedLexicographicalProductVerticesSize);
        assertThat(lexicographicalProduct.getEdges().size()).isEqualTo(expectedLexicographicalProductEdgesSize);
    }

    @Test
    public void givenC5AndP4AndK3Graph_whenCreateThreeFactorsCartesianProduct_thenSuccessCreateKCCartesianProduct() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        Graph<Integer> C5 = Graph.newInstance();
        C5.addEdge(1, 2);
        C5.addEdge(2, 3);
        C5.addEdge(3, 4);
        C5.addEdge(4, 5);
        C5.addEdge(5, 1);

        Graph<String> P4 = Graph.newInstance();
        P4.addPath("a", "b", "c", "d");

        Graph<Integer> K3 = Graph.newInstance();
        K3.addEdgesToVertex(1, 2, 3);
        K3.addEdge(2, 3);
        // WHEN

        Graph<?> cartesianProduct = factory.createCartesianProduct(C5, P4);
        Graph KCartesianProduct = factory.createCartesianProduct(cartesianProduct, K3);
        // THEN
        int expectedCartesianProductVerticesSize = cartesianProduct.getVertices().size() * K3.getVertices().size();
        int expectedCartesianProductEdgesSize = cartesianProduct.getEdges().size() * K3.getVertices().size() + K3.getEdges().size() * cartesianProduct.getVertices().size();

        assertThat(KCartesianProduct.getEdges().size()).isGreaterThan(0);
        assertThat(KCartesianProduct.getVertices().size()).isGreaterThan(0);
        assertThat(KCartesianProduct.getVertices().size()).isEqualTo(expectedCartesianProductVerticesSize);
        assertThat(KCartesianProduct.getEdges().size()).isEqualTo(expectedCartesianProductEdgesSize);
    }

    @Test
    public void givenC5AndP4AndK3Graph_whenCreateThreeFactorsStrongProduct_thenSuccessCreateKStrongProduct() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        Graph<Integer> C5 = Graph.newInstance();
        C5.addEdge(1, 2);
        C5.addEdge(2, 3);
        C5.addEdge(3, 4);
        C5.addEdge(4, 5);
        C5.addEdge(5, 1);

        Graph<String> P4 = Graph.newInstance();
        P4.addPath("a", "b", "c", "d");

        Graph<Integer> K3 = Graph.newInstance();
        K3.addEdgesToVertex(1, 2, 3);
        K3.addEdge(2, 3);
        // WHEN

        Graph strongProduct = factory.createStrongProduct(C5, P4);
        Graph kStrongProduct = factory.createStrongProduct(strongProduct, K3);
        // THEN
        int expectedStrongProductVerticesSize = strongProduct.getVertices().size() * K3.getVertices().size();
        int expectedStrongProductEdgesSize = strongProduct.getVertices().size() * K3.getEdges().size() + K3.getVertices().size() * strongProduct.getEdges().size()
                + 2 * strongProduct.getEdges().size() * K3.getEdges().size();

        assertThat(kStrongProduct.getEdges().size()).isGreaterThan(0);
        assertThat(kStrongProduct.getVertices().size()).isGreaterThan(0);
        assertThat(kStrongProduct.getVertices().size()).isEqualTo(expectedStrongProductVerticesSize);
        assertThat(kStrongProduct.getEdges().size()).isEqualTo(expectedStrongProductEdgesSize);
    }

    @Test
    public void givenC5AndP4AndK3Graph_whenCreateThreeFactorsCategoricalProduct_thenSuccessCreateKCategoricalProduct() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        Graph<Integer> C5 = Graph.newInstance();
        C5.addEdge(1, 2);
        C5.addEdge(2, 3);
        C5.addEdge(3, 4);
        C5.addEdge(4, 5);
        C5.addEdge(5, 1);

        Graph<String> P4 = Graph.newInstance();
        P4.addPath("a", "b", "c", "d");

        Graph<Integer> K3 = Graph.newInstance();
        K3.addEdgesToVertex(1, 2, 3);
        K3.addEdge(2, 3);
        // WHEN

        Graph<?> categoricalProduct = factory.createCategoricalProduct(C5, P4);
        Graph KCategoricalProduct = factory.createCategoricalProduct(categoricalProduct, K3);
        // THEN
        int expectedCategoricalProductVerticesSize = categoricalProduct.getVertices().size() * K3.getVertices().size();
        int expectedCategoricalProductEdgesSize = 2 * categoricalProduct.getEdges().size() * K3.getEdges().size();

        assertThat(KCategoricalProduct.getEdges().size()).isGreaterThan(0);
        assertThat(KCategoricalProduct.getVertices().size()).isGreaterThan(0);
        assertThat(KCategoricalProduct.getVertices().size()).isEqualTo(expectedCategoricalProductVerticesSize);
        assertThat(KCategoricalProduct.getEdges().size()).isEqualTo(expectedCategoricalProductEdgesSize);

    }

    @Test
    public void givenC5AndP4AndK3Graph_whenCreateThreeFactorsLexicographicalProduct_thenSuccessCreateKLexicographicalProduct() throws EdgeAlreadyExistException, PathContainsDuplicatesException {
        // GIVEN
        Graph<Integer> C5 = Graph.newInstance();
        C5.addEdge(1, 2);
        C5.addEdge(2, 3);
        C5.addEdge(3, 4);
        C5.addEdge(4, 5);
        C5.addEdge(5, 1);

        Graph<String> P4 = Graph.newInstance();
        P4.addPath("a", "b", "c", "d");

        Graph<Integer> K3 = Graph.newInstance();
        K3.addEdgesToVertex(1, 2, 3);
        K3.addEdge(2, 3);
        // WHEN

        Graph<?> lexicographicalProduct = factory.createLexicographicalProduct(C5, P4);
        Graph KLexicographicalProduct = factory.createLexicographicalProduct(lexicographicalProduct, K3);
        // THEN
        int expectedLexicographicalProductVerticesSize = lexicographicalProduct.getVertices().size() * K3.getVertices().size();
        int expectedLexicographicalProductEdgesSize = K3.getEdges().size() * lexicographicalProduct.getVertices().size()
                + lexicographicalProduct.getEdges().size() * (K3.getVertices().size() * K3.getVertices().size());

        assertThat(KLexicographicalProduct.getEdges().size()).isGreaterThan(0);
        assertThat(KLexicographicalProduct.getVertices().size()).isGreaterThan(0);
        assertThat(KLexicographicalProduct.getVertices().size()).isEqualTo(expectedLexicographicalProductVerticesSize);
        assertThat(KLexicographicalProduct.getEdges().size()).isEqualTo(expectedLexicographicalProductEdgesSize);
    }

}