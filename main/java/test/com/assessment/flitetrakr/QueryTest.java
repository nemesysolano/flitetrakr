package com.assessment.flitetrakr;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.assessment.data.AdjacencyMatrix;

/**
 * <p>This test case validates that <b><code>com.assessment.flitetrakr.Query</code></b> is working properly.</p>
 * @author rsolano
 *
 */
public class QueryTest {
	/**
	 * Convenience constant to avoid repetition of &quot;No such connection found!&quot;
	 */
	private static final String CONNECTION_NOT_FOUND_ERR="No such connection found!";
	
	/**
	 * Data used in this test class
	 */
	final static String[] connections = {
		"Connections: NUE-FRA-43, NUE-AMS-67, FRA-AMS-17, FRA-LHR-27, LHR-NUE-23",
		"Connections: a-x-3, a-b-1, a-c-2, b-d-2, c-d-3, d-e-1, x-b-1, x-c-5"
	};

	
	/**
	 * This method ensures that Query class is able to find shortest distances for valid connections..
	 * @throws ParseException 
	 */
	@Test
	public void testCheapestConnection() throws ParseException { //What is the cheapest connection from ??? to ???? Q
		AdjacencyMatrix adjacencyMatrix1 = new AdjacencyMatrix(connections[0]);
		AdjacencyMatrix adjacencyMatrix2 = new AdjacencyMatrix(connections[1]);	
		Query query1 = new Query(adjacencyMatrix1);
		Query query2 = new Query(adjacencyMatrix2);
		
		System.out.println(String.format("QueryTest.testCheapestConnection"));
		
		Assert.assertEquals("NUE-FRA-AMS-60", query1.cheapestConnection("NUE", "AMS"));
		Assert.assertEquals(CONNECTION_NOT_FOUND_ERR, query1.cheapestConnection("AMS", "FRA"));		
		Assert.assertEquals("LHR-NUE-FRA-LHR-93", query1.cheapestConnection("LHR", "LHR"));
		Assert.assertEquals("a-b-d-e-4", query2.cheapestConnection("a", "e"));
		Assert.assertEquals("x-b-d-e-4", query2.cheapestConnection("x", "e"));
		
	}
	
	/**
	 * This method ensures that Query class can calculate the total distance for a given path.
	 * @throws ParseException 
	 */	
	@Test
	public void testConnectionPrice() throws ParseException { //What is the price of the connection ...? 
		AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(connections[0]);
		Query query = new Query(adjacencyMatrix);
		
		System.out.println(String.format("QueryTest.testConnectionPrice"));
		Assert.assertEquals(70, query.connectionPrice(new String[] {"NUE", "FRA", "LHR"}));
		Assert.assertEquals(-1, query.connectionPrice(new String[] {"NUE", "AMS", "LHR"}));
		Assert.assertEquals(93, query.connectionPrice(new String[] {"NUE", "FRA", "LHR", "NUE"}));
	}
	
	
	@Test
	public void testConnectionsWithMaximumStops() throws ParseException { //What is the price of the connection ...? 
		AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(connections[1]);
		Query query = new Query(adjacencyMatrix);
		
		System.out.println(String.format("QueryTest.testConnectionsWithMaximumStops"));
		Assert.assertEquals(4, query.connectionsWithMaximumStops(3, "a", "e"));
		Assert.assertEquals(2, query.connectionsWithMaximumStops(2, "a", "e"));
		Assert.assertEquals(1, query.connectionsWithMaximumStops(0, "a", "b"));
		Assert.assertEquals(1, query.connectionsWithMaximumStops(0, "a", "c"));		
		Assert.assertEquals(2, query.connectionsWithMaximumStops(1, "a", "d"));
		
	}	
	
	@Test
	public void testConnectionsWithExactStops() throws ParseException { //What is the price of the connection ...? 
		AdjacencyMatrix adjacencyMatrix1 = new AdjacencyMatrix(connections[0]);
		AdjacencyMatrix adjacencyMatrix2 = new AdjacencyMatrix(connections[1]);	
		Query query1 = new Query(adjacencyMatrix1);		
		Query query2 = new Query(adjacencyMatrix2);
		
		System.out.println(String.format("QueryTest.testConnectionsWithExactStops"));
		Assert.assertEquals(1, query1.connectionsWithExactStops(1, "LHR", "AMS"));
		Assert.assertEquals(1, query2.connectionsWithExactStops(0, "a", "b"));
		Assert.assertEquals(2, query2.connectionsWithExactStops(2, "a", "d"));
	}
	
}
