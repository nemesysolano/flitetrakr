package com.assessment.flitetrakr;


import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.assessment.data.AdjacencyMatrix;
import com.assessment.data.DirectedGraph;


public class Query {
	
	/**
	 * Adjacency matrix whose weights are the flight fares and its row/columns coordinates are mapped to airport codes.
	 */
	AdjacencyMatrix adjacencyMatrix;
	
		
	/**
	 * @param adjacencyMatrix Adjacency matrix whose weights are the flight fares and its row/columns coordinates are mapped to airport codes.
	 */
	public Query(AdjacencyMatrix adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
	/**
	 * <p>This function addresses the question of how many different connections with maximum ??? stops exist between ??? and ??? ? </p>
	 * @param stops Maximum number of stops. A stop is a landing in an intermediary city.
	 * @param sourceCode Departure airport's code.
	 * @param destinationCode Destination airport's code.
	 * @return How many connections comply with the aforementioned criteria.
	 */
	public int connectionsWithMaximumStops(int stops, String sourceCode, String destinationCode) {
		List<LinkedList<String>> connections = this.adjacencyMatrix.
				getDirectedGraph().
				depthFirst(sourceCode, destinationCode);		
		int count = 0;
		
		for(LinkedList<String> connection: connections) {
			int flightStops = connection.size()-2; // source and destination airports are not stops.
			
			if(flightStops <= stops) { 
				DirectedGraph.printPath(connection);
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * This function addresses the question of how many different connections with exactly ??? stops exist between ??? and ??? ?
	 * @param stops Number of expected stops. A stop is a landing in an intermediary city.
	 * @param sourceCode Departure airport's code.
	 * @param destinationCode Destination airport's code.
	 * @return How many connections comply with the aforementioned criteria.
	 */
	public int connectionsWithExactStops(int stops, String sourceCode, String destinationCode) {
		List<LinkedList<String>> connections = this.adjacencyMatrix.
				getDirectedGraph().
				depthFirst(sourceCode, destinationCode);		
		int count = 0;
		
		for(LinkedList<String> connection: connections) {
			int flightStops = connection.size()-2; // source and destination airports are not stops.
			
			if(flightStops == stops) { 
				DirectedGraph.printPath(connection);
				count++;
			}
		}
		
		return count;
	}	
	/**
	 * <p>This function addresses the question of what is the price of the connection ???-???-... ? </p>
	 * @param codes An array of airport codes representing a connection; 1st element is the connection's source and last one is the connection's destionation.
	 * @return A non zero/negative integer if <b><code>code</code></b> represents a valid connection. On the contrary, this function returns -1.
	 */
	public int connectionPrice(String[] codes) {
		if(!this.adjacencyMatrix.getDirectedGraph().connectionExists(codes)) {
			return -1;
		}
				
		String sourceCode = codes[0];
		String destinationCode = codes[1];
		int price = this.adjacencyMatrix.get(sourceCode, destinationCode);
		
		for(int i = 2; i < codes.length ; i++) {
			sourceCode = destinationCode;
			destinationCode = codes[i];
			price += this.adjacencyMatrix.get(sourceCode, destinationCode);
		}
		
		return price;
	}
	
	/**
	 * <p>Calculates the shortest path between two airports.</p>
	 * <p>This function addresses the question of what is the cheapest connection from &lt;code&gt; to &lt;code&gt; </p>
	 * @param sourceCode Departure airport's code.
	 * @param destinationCode Destination airport's code.
	 * 
	 * @return The shortest path between <code><b>sourceCode</b></code> and <code><b>destinationCode</b></code>.
	 * The returned string contains a sequence of airport codes followed by the total distance. Example <code><b>NUE-FRA-AMS-60</b></code>.
	 */
	public String cheapestConnection(String sourceCode, String destinationCode) {
		final String CONNECTION_NOT_FOUND_ERROR= "No such connection found!";
		String result;
		
		try {
			/* Dirty check. ArrayIndexOutOfBoundsException is thrown when any of these codes doesn't exist in the adjacencyMatrix. */
			adjacencyMatrix.getIndex(sourceCode);
			adjacencyMatrix.getIndex(destinationCode);
			
			List<LinkedList<String>> connections = this.adjacencyMatrix.
					getDirectedGraph().
					depthFirst(sourceCode, destinationCode);
			
			if(connections.size() > 0) {
				LinkedList<String> connection = this.cheapestConnection(connections);
				
				result = this.formatConnection(connection);
			}else {
				result = CONNECTION_NOT_FOUND_ERROR;
			}
			
		}catch(ArrayIndexOutOfBoundsException e) {
			result = CONNECTION_NOT_FOUND_ERROR;
		}
		
		return result;
		
	}
	
	/**
	 * <p>Finds the cheapest connection the given connection list.</p>
	 * 
	 * @param connections A list whose elements are sublists. Each sublist represent a connection between two airports.
	 * @return The shortest path in the given path list or null if the list is empty.
	 */
	private LinkedList<String> cheapestConnection(List<LinkedList<String>> connections) {
		ListIterator<LinkedList<String>> iterator = connections.listIterator();
		LinkedList<String> shortestConnection = iterator.next();
		int currentTotalDistance = calculateTotalDistance(shortestConnection);
		
		
		while(iterator.hasNext()) {		
			LinkedList<String> path = iterator.next();
			int totalDistance = this.calculateTotalDistance(path);
			
			if(totalDistance < currentTotalDistance) {
				shortestConnection = path;
				currentTotalDistance = totalDistance;
			}
			
		}
		return shortestConnection; 
	}	
	
	private String formatConnection(LinkedList<String> connection) {
		int totalDistance = calculateTotalDistance(connection); //TODO: This step is suboptimal.
		StringBuilder buffer = new StringBuilder();
		
		for(String code: connection) {
			buffer.append(code);
			buffer.append('-');
		}
		buffer.append(totalDistance);
		
		return buffer.toString();
	}
	
	
	
	/**
	 * <p>Calculates the total distance between path's starting and ending node.</p>
	 * @param connection A list of string representing airport codes.
	 * @return An non-negative integer.
	 */
	private int calculateTotalDistance(LinkedList<String> connection) {
		ListIterator<String> iterator = connection.listIterator();
		String sourceCode = iterator.next();
		String destinationCode = iterator.next();
		
		int totalDistance = adjacencyMatrix.get(sourceCode, destinationCode);
		
		while(iterator.hasNext()) {
			sourceCode = destinationCode;
			destinationCode = iterator.next();
			totalDistance += adjacencyMatrix.get(sourceCode, destinationCode);
		}
		return totalDistance;
		
	}
}
