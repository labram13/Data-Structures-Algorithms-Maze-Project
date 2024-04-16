package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.UnionBySizeCompressingDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        // return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        // Here's some code to get you started; feel free to change or rearrange it if you'd like.

        // sort edges in the graph in ascending weight order

        if (graph.allVertices().size() == 2 && graph.allEdges().isEmpty()) {
            return new MinimumSpanningTree.Failure<>();
        }


        List<E> edges = new ArrayList<>(graph.allEdges());
        // if (edges.size() == 0) {
        //         return new MinimumSpanningTree.Failure<>();
        // }

        edges.sort(Comparator.comparingDouble(E::weight));
        DisjointSets<V> disjointSets = createDisjointSets();
        HashSet<V> set = new HashSet<>();

        //notable methods for edge
        //edge.from() = starting vertice
        //edge.to() = ending vertice
        //edge.weight() = returns weght

        List<E> finalMST = new ArrayList<>();

        for (E edge : edges) {
            if (!set.contains(edge.from())) {
                set.add(edge.from());
                disjointSets.makeSet(edge.from());
            }
            if (!set.contains(edge.to())) {
                set.add(edge.to());
                disjointSets.makeSet(edge.to());
            }
            int uMST = disjointSets.findSet(edge.from());
            int vMST = disjointSets.findSet(edge.to());
            // if (set.size() - 1 != finalMST.size()) {
            //     break;
            // }
            if (uMST != vMST) {
                finalMST.add(edge);
                disjointSets.union(edge.from(), edge.to());
            }

            //decrement size of edges list

        }

        //not checking for connectedness

        // if (set.size() - 1 != finalMST.size()) {
        //     return new MinimumSpanningTree.Failure<>();
        // }
        //
        // if (finalMST.isEmpty() || set.size() - 1 == finalMST.size()) {
        //     return new MinimumSpanningTree.Success<>(finalMST);
        //
        // }
        //if (se)

        //[V: a,b ; Edges: 1] --> success
        //[V: a,b ; Edges: 0] --> failure because disconnected
        //[V: a; edges:0]--> success
        //[V: ; edges:0]--> success
        //[V: a, b; edges:0
        if (set.size() == 0 && finalMST.size() == 0) {
            return new MinimumSpanningTree.Success<>(finalMST);

        }

        if ((set.size() == 1 && finalMST.size() == 1) ||
            (set.size() == 1 && finalMST.size() == 0)) {
            return new MinimumSpanningTree.Success<>(finalMST);

        }

        // if (set.size() == 2 && finalMST.size() == 0) {
        //     return new MinimumSpanningTree.Failure<>();
        //
        // }
        if (set.size() - 1 != finalMST.size()) {
            return new MinimumSpanningTree.Failure<>();
        }
        return new MinimumSpanningTree.Success<>(finalMST);

    }
}



