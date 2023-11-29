package ch.epfl.cs107.play.areagame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;


/**
 * AreaGraph is a specific kind of graph apply to Area.
 * The graph is composed of AreaNodes which are defined by their position in the graph (DiscreteCoordinates)
 * and the existence of directed edge between them (from) and their four neighbors (to).
 * Nodes are stored into a Map.
 * Note: DiscreteCoordinate are serializable reimplementing hashCode() and equals() making the keys dependant only from
 * the DiscreteCoordinate x and y values and not from the object itself.
 */
public class AreaGraph {

    /// Map containing all the node or vertices of the area graph
    private final Map<DiscreteCoordinates, AreaNode> nodes;

    /**
     * Default AreaGraph Constructor
     */
    public AreaGraph(){
        nodes = new HashMap<>();
    }


    /**
     * Add if absent a new Node into the graph.
     * Create a new Node and put it in the nodes map at given coordinates key.
     * Note: DiscreteCoordinate are serializable reimplementing hashCode() and equals() making the keys dependant only from
     * the DiscreteCoordinate x and y values and not from the object itself.
     * @param coordinates (DiscreteCoordinate): Position in the graph of the node to add, used as key for the map, not null
     * @param hasLeftEdge (boolean): indicate if directed edge to the left direction exists
     * @param hasUpEdge (boolean): indicate if directed edge to the up direction exists
     * @param hasRightEdge (boolean): indicate if directed edge to the right direction exists
     * @param hasDownEdge (boolean): indicate if directed edge to the down direction exists
     */
    public void addNode(DiscreteCoordinates coordinates, boolean hasLeftEdge, boolean hasUpEdge, boolean hasRightEdge, boolean hasDownEdge){
        nodes.putIfAbsent(coordinates, new AreaNode(coordinates, hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge));
    }

    protected Map<DiscreteCoordinates, AreaNode> getNodes() {
        return nodes;
    }


    /**
     * Return if a node exists in the graph
     * @param coordinates (DiscreteCoordinates): may be null
     * @return (boolean): true if the given node exists in the graph
     */
    public boolean nodeExists(DiscreteCoordinates coordinates){
        return nodes.containsKey(coordinates);
    }

    public void setSignal(DiscreteCoordinates coordinates, Logic signal) {
    	if(!nodes.containsKey(coordinates))
    		throw new IllegalArgumentException("The node do not exist");
    	nodes.get(coordinates).setSignal(signal);
    }

    protected class AreaNode{
        /// Position of the node into the graph, used as key for the map
        private final DiscreteCoordinates coordinates;
        /// a List of the connectedNode. May be null if getConnectedNodes is never called
        private List<AreaNode> connectedNodes;
        /// Flag: true if a directed edge between this and indicated direction (left, up, right, down) exists
        private final boolean hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge;
        // Signal indicating it the node is active
        private Logic isActive;

        /**
         * Default AreaNode Constructor
         * @param coordinates (DiscreteCoordinate): Position in the graph of the node to add, used as key for the map, not null
         * @param hasLeftEdge (boolean): indicate if directed edge to the left direction exists
         * @param hasUpEdge (boolean): indicate if directed edge to the up direction exists
         * @param hasRightEdge (boolean): indicate if directed edge to the right direction exists
         * @param hasDownEdge (boolean): indicate if directed edge to the down direction exists
         */
        protected AreaNode(DiscreteCoordinates coordinates, boolean hasLeftEdge, boolean hasUpEdge, boolean hasRightEdge, boolean hasDownEdge){
            this.coordinates = coordinates;
            this.hasLeftEdge = hasLeftEdge;
            this.hasUpEdge = hasUpEdge;
            this.hasRightEdge = hasRightEdge;
            this.hasDownEdge = hasDownEdge;
            
            isActive = Logic.TRUE;
        }

        /**
         * Neighbors getter
         *  see method addNeighbor()
         *  @return (Array of AreaNode): the array of four neighbor Nodes. Elements may be null if no connection exists
         */
        List<AreaNode> getConnectedNodes() {
            if(connectedNodes == null){
                connectedNodes = new ArrayList<>();

                addNeighbor("Left", hasLeftEdge, new DiscreteCoordinates(coordinates.x-1, coordinates.y));
                addNeighbor("Up", hasUpEdge, new DiscreteCoordinates(coordinates.x, coordinates.y+1));
                addNeighbor("Right", hasRightEdge, new DiscreteCoordinates(coordinates.x+1, coordinates.y));
                addNeighbor("Down", hasDownEdge, new DiscreteCoordinates(coordinates.x, coordinates.y-1));
            }

            return connectedNodes;
        }

        private void addNeighbor(String neighborString, boolean hasNeighbor, DiscreteCoordinates c) {

            if(hasNeighbor){
                if(nodes.containsKey(c)){
                    connectedNodes.add(nodes.get(c));
                }else{
                    // TODO throw exception
                    System.out.println(neighborString + " neighbor for "+ coordinates.toString() + " Node does not exists");
                }
            }
        }


        /**
         * Indicate the orientation we need to follow to reach previous node from this one
         * Assume the previous node is a neighbor node
         * @param previous (AreaNode), not null
         * @return (Orientation)
         */
        Orientation getOrientation(AreaNode previous){

            if(previous.coordinates.x < coordinates.x)
                return Orientation.LEFT;
            if(previous.coordinates.y > coordinates.y)
                return Orientation.UP;
            if(previous.coordinates.x > coordinates.x)
                return Orientation.RIGHT;
            if(previous.coordinates.y < coordinates.y)
                return Orientation.DOWN;

            System.out.println("Should never print");
            return null;
        }

        public void setSignal(Logic signal) {
        	isActive = signal;
        }
        
        public boolean isActive() {
        	return isActive.isOn();
        }
        
    }


    /**
     * Compute the shortest path in this AreaGraph from given DiscreteCoordinate to given DiscreteCoordinates
     * @param from (DiscreteCoordinates): source node of the desired path, not null
     * @param to (DiscreteCoordinates): sink node of the desired path, not null
     * @return (Iterator of Orientation): return an iterator containing the shortest path from source to sink, or null if the path does not exists !
     */
    public Queue<Orientation> shortestPath(DiscreteCoordinates from, DiscreteCoordinates to){

        AreaNode start = nodes.get(from);
        AreaNode goal = nodes.get(to);

        if( goal == null || start == null || start == goal)
            return null;

        //System.out.println("Looking for path from: " + start.coordinates.toString() + " to : "+ goal.coordinates.toString());

        // used to size data structures appropriately
        final int size = nodes.size();
        // The set of nodes already evaluated.
        final Set<AreaNode> visitedSet = new HashSet<>(size);
        // The set of tentative nodes to be evaluated, initially containing the start node
        final List<AreaNode> toVisitSet = new ArrayList<>(size);
        toVisitSet.add(start);
        // The map of navigated nodes. <neighbor, current>
        final Map<AreaNode, AreaNode> cameFrom = new HashMap<>(size);

        while (!toVisitSet.isEmpty()) {
            // Get the first node, we will now evaluate it
            final AreaNode current = toVisitSet.get(0);

            // If the current is the goal one, we can end
            if (current.equals(goal))
                return reconstructPath(cameFrom, goal);

            // Otherwise we remove it from non-evaluated node and put it inside evaluated node
            toVisitSet.remove(0);
            visitedSet.add(current);

            // For all its neighbors
            for (AreaNode neighbor : current.getConnectedNodes()) {
                if (visitedSet.contains(neighbor))
                    // Ignore the neighbor which is already evaluated.
                    continue;
                if(!neighbor.isActive())
                	// Ignore inactive neighbors
                	continue;

                toVisitSet.add(neighbor);

                // This path is the best. Record it!
                cameFrom.put(neighbor, current);
            }
        }

        return null;
    }

    private Queue<Orientation> reconstructPath(Map<AreaNode, AreaNode> cameFrom, AreaNode current) {
        final List<Orientation> totalPath = new ArrayList<>();

        while (current != null) {
            final AreaNode previous = current;
            current = cameFrom.get(current);
            if (current != null) {
                final Orientation edge = current.getOrientation(previous);
                totalPath.add(edge);
            }
        }

        Collections.reverse(totalPath);

        /*
        // Print the path for debug purpose
        System.out.println("--------------------Path : ");
        for(Orientation o : totalPath){
            System.out.println(o.toString());
        }
        */

        return new LinkedList<Orientation>(totalPath);
    }
}
