package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        Map<V, E> edgeTo = new HashMap<>();
        if (Objects.equals(start, end)) {
            return edgeTo;
        }
        HashSet<V> known = new HashSet<>();
        Map<V, Double> distTo = new HashMap<>();
        DoubleMapMinPQ<V> unknownVertices = new DoubleMapMinPQ<>();
        // if (Objects.equals(start, end)) {
        //     for (E edge : graph.outgoingEdgesFrom(start)) {
        //         edgeTo.put(start, edge);
        //     }
        //     return edgeTo;
        // }



        unknownVertices.add(start, 0.0);
        distTo.put(start, 0.0);

        // System.out.println("test");
        while (!unknownVertices.isEmpty()) {
            V currNode = unknownVertices.removeMin();
            known.add(currNode);
            for (E edge: graph.outgoingEdgesFrom(currNode)) {
                //if the the edge.to node does not exist, add to distTo
                //set it to infinity so we can calculate the new distance
                //for each unknown edge.to()

                if (!distTo.containsKey(edge.to())) {
                    distTo.put(edge.to(), Double.POSITIVE_INFINITY);
                }
                double oldDist = distTo.get(edge.to());
                double newDist = distTo.get(edge.from()) + edge.weight();
                // System.out.println(oldDist);
                // System.out.println(newDist);
                if (newDist < oldDist) {
                    distTo.put(edge.to(), newDist);
                    edgeTo.put(edge.to(), edge);
                    //change priority if priority queue has the edge. otherwise add it
                    //we want to change the priority only in this method since the new distance
                    //is better than the old one.
                    if (unknownVertices.contains(edge.to())) {
                        unknownVertices.changePriority(edge.to(), newDist);
                    } else {
                        unknownVertices.add(edge.to(), newDist);
                    }
                }

            }
            if (known.contains(end)) {
                return edgeTo;
            }

        }
        // dijkstraShortestPath(G graph, V start)
        // Set known; Map edgeTo, distTo;
        // initialize distTo with all nodes mapped to ∞, except start to 0
        //
        // while (there are unknown vertices):
        // let u be the closest unknown vertex
        // known.add(u)
        // for each edge (u,v) to unknown v with weight w:
        // oldDist = distTo.get(v)      // previous best path to v
        // newDist = distTo.get(u) + w  // what if we went through u?
        // if (newDist < oldDist):
        // distTo.put(v, newDist)
        // edgeTo.put(v, u)
        // update distance in list of unknown vertices






        // while (!toProcess.isEmpty()) {
        //     V currNode = toProcess.removeMin();
        //     known.add(currNode);
        //     for (E edge: graph.outgoingEdgesFrom(currNode)) {
        //         if (!distTo.containsKey(edge.to())) {
        //             distTo.put(edge.to(), edge.weight());
        //             edgeTo.put(edge.to(), edge);
        //             if (!known.contains(edge.to())) {
        //                 toProcess.add(edge.to(), edge.weight());
        //             } else {
        //                 toProcess.changePriority(edge.to(), edge.weight());
        //             }
        //         } else {
        //             double oldDist = distTo.get(edge.to());
        //             double newDist = distTo.get(edge.from()) + edge.weight();
        //             if (newDist < oldDist) {
        //                 distTo.put(edge.to(), newDist);
        //                 edgeTo.put(edge.to(), edge);
        //                 //toProcess.add(edge.to(), edge.weight());
        //             }
        //
        //         }
        //
        //     }
        //     if (known.contains(end)) {
        //         return edgeTo;
        //     }
        //
        // }

        // System.out.println(edgeTo);
        // System.out.println("test");
        return edgeTo;
    }



        @Override
        protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
            //backtrace from end node or start node? to star/end node
            //Use the spt, grab spt key value of start/end
            //use to to get edge.from()
            List<E> edges = new LinkedList<>();
            V currNode = end;
            if (Objects.equals(start, end)) {
                // edges.add(spt.get(end));

                List<BaseEdge<V, E>> edge = new ArrayList<>();
                // System.out.println(new ShortestPath.SingleVertex<>(start).edges());
                return new ShortestPath.SingleVertex<>(start);
            }

            if (spt.get(currNode) == null) {
                return new ShortestPath.Failure<>();
            }

            while (!currNode.equals(start)) {
                if (edges.isEmpty()) {
                    edges.add(spt.get(currNode));
                } else {
                    edges.add(0, spt.get(currNode));
                }
                currNode = spt.get(currNode).from();
            }



            return new ShortestPath.Success<>(edges);








            // throw new UnsupportedOperationException("Not implemented yet.");
        }

    }


