package mazes.logic.carvers;

import graphs.EdgeWithData;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {
        List<EdgeWithData<Room, Wall>> edges = new ArrayList<>();
        for (Wall wall: walls) {
            edges.add(new EdgeWithData<>(wall.getRoom1(), wall.getRoom2(), rand.nextDouble(), wall));
        }
        // Hint: you'll probably need to include something like the following:

        Set<EdgeWithData<Room, Wall>> mst = new HashSet<>(
            this.minimumSpanningTreeFinder.findMinimumSpanningTree(
            new MazeGraph(edges)).edges());
        Set<Wall> set = new HashSet<>();

        for (EdgeWithData<Room, Wall> edge: mst) {
            set.add(edge.data());
        }
        return set;

        // throw new UnsupportedOperationException("Not implemented yet.");
    }
}
