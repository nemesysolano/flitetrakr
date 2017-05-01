package com.assessment.data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * <p>This class implements a simple directed graph which is used to answer questions about connections.</p>
 * @author rsolano
 *
 */
public class DirectedGraph {
    private Map<String, LinkedHashSet<String>> map = new HashMap<String, LinkedHashSet<String>>();

    public void addEdge(String node1, String node2) {
        LinkedHashSet<String> adjacent = map.get(node1);
        if(adjacent==null) {
            adjacent = new LinkedHashSet<String>();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
    }

    public void addTwoWayVertex(String node1, String node2) {
        addEdge(node1, node2);
        addEdge(node2, node1);
    }

    public boolean isConnected(String node1, String node2) {
        Set<String> adjacent = map.get(node1);
        if(adjacent==null) {
            return false;
        }
        return adjacent.contains(node2);
    }

    public LinkedList<String> adjacentNodes(String last) {
        LinkedHashSet<String> adjacent = map.get(last);
        if(adjacent==null) {
            return new LinkedList<String>();
        }
        return new LinkedList<String>(adjacent);
    }
    
    public Set<String> getNodeSet() {
    	SortedSet<String> copy = new TreeSet<String>();
    	
    	copy.addAll(map.keySet());
    	return copy;
    }
    
    public boolean connectionExists(String[] nodes) {
    	return connectionExists(this, nodes);    	
    }
    
   static public boolean connectionExists(DirectedGraph graph, String... nodes) {
    	
    	String start = nodes[0];
    	String end = nodes[nodes.length-1];
    	List<LinkedList<String>> connections = depthFirst(graph, start, end);
    	
    	next: for(LinkedList<String> connection: connections) {
    		int i = 0;
    		
    		for(String node: connection) {
    			if(nodes[i].compareTo(node) != 0) {
    				continue next;
    			}
    			i++;
    		}
    		
    		if( i == connection.size())
    			return true;
    	}
    	
    	return false;
    }
    
    /**
     * <p>Finds all paths between two nodes.</p>
     * @param start Source node.
     * @param end Destination node.
     * 
     * @return A list whose elements are sublists. Each sublist represent a path from <code><b>start</b></code> to <code><b>end</b></code>.
     */
   	public List<LinkedList<String>> depthFirst(String start, String end){
   		return depthFirst(this, start, end);
   	}
   	
   	/**
   	 * <p>Finds all paths between two nodes.</p>
   	 * @param graph The directed graph wherein paths are sought.
   	 * @param start Source node.
   	 * @param end Destination node.
   	 * 
   	 * @return A list whose elements are sublists. Each sublist represent a path from <code><b>start</b></code> to <code><b>end</b></code>.
   	 */
    static private List<LinkedList<String>> depthFirst(DirectedGraph graph, String start, String end) {
    	List<LinkedList<String>> result;
    	
		if (start.compareTo(end) == 0) {
    		result = depthFirstRoundTrip(graph, start);
    	} else {    	
    		result = depthFirstNoRoundTrip(graph, start, end);
    	}
    	
		/* Enable this section for debugging purposes. *	
		for(LinkedList<String> path : result) {
			printPath(path);
		}
		/* */
		return result;
    }
    

    static private List<LinkedList<String>> depthFirstRoundTrip(DirectedGraph graph, String start) {
		Collection<String> nodes = graph.getNodeSet();
		List<LinkedList<String>> rounTrips = new ArrayList<LinkedList<String>>();
		
		nodes.remove(start);
		
		for(String end: nodes){
			List<LinkedList<String>> departures =  depthFirstNoRoundTrip(graph, start, end);			
			List<LinkedList<String>> arrivals =  depthFirstNoRoundTrip(graph, end, start);
				
			/* Enable this section for debugging purposes. *	
			System.out.println("departures");
			printPaths(departures);
			
			System.out.println("arrivals");
			printPaths(arrivals);
						
			System.out.println("roundtrips");
			/* */
			
			for(LinkedList<String> departure: departures) {
				
				for(LinkedList<String> arrival: arrivals ) {		
					LinkedList<String> roundTrip = new LinkedList<String>();
					
					rounTrips.add(roundTrip);
					roundTrip.addAll(departure);									
					roundTrip.addAll(arrival);
					roundTrip.remove(departure.size()-1);
					/* Enable this break for debugging purposes *
					printPath(roundTrip);
					/* */
				}
				
			}
			/* Enable this break for debugging purposes.*			
			break;
			/* */
		}
		
		return rounTrips;
	}
    
    static private List<LinkedList<String>> depthFirstNoRoundTrip(DirectedGraph graph, String start, String end) {
        LinkedList<String> visited = new LinkedList<String>();
        List<LinkedList<String>> result = new LinkedList<LinkedList<String>>();
        
        visited.add(start);
        depthFirst(graph, visited, result, end);
        
        return result;
        
    }    
    static private void depthFirst(DirectedGraph graph, LinkedList<String> visited, List<LinkedList<String>> result, String end) {
        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
        
        // examine adjacent nodes
        for (String node : nodes) {
        	
            if (visited.contains(node)) {
                continue;
            }
            
            if (node.equals(end)) {
            	LinkedList<String> list = new LinkedList<String>();
            	
                visited.add(node);
                list.addAll(visited);
                result.add(list);                
                
                visited.removeLast();
                break;
            }
        }
        
        for (String node : nodes) {
            if (visited.contains(node) || node.equals(end)) {
                continue;
            }
            visited.addLast(node);
            depthFirst(graph, visited, result, end);
            visited.removeLast();
        }
    }

	
    public static void printPath(LinkedList<String> visited) {
        for (String node : visited) {
            System.out.print(node);
            System.out.print(' ');
        }
        System.out.println();
    }
    
    public static void printPaths(List<LinkedList<String>> paths) {
        for (LinkedList<String> path : paths) {
        	printPath(path);
        }
        
    }        
}