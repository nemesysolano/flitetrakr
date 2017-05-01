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
		"Connections: NUE-FRA-43,NUE-AMS-67,FRA-AMS-17,FRA-LHR-27,LHR-NUE-23",
		"Connections: NUE-FRA-43,NUE-AMS-67, FRA-AMS-17,FRA-LHR-27, LHR-NUE-23"
	};

	
	/**
	 * This method ensures that Query class is able to find shortest distances for valid connections..
	 * @throws ParseException 
	 */
	@Test
	public void testCheapestConnection() throws ParseException { //What is the cheapest connection from ??? to ???? Q
		AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(connections[0]);
		Query query = new Query(adjacencyMatrix);
		
		System.out.println(String.format("QueryTest.testCheapestConnection"));
		
		Assert.assertEquals("NUE-FRA-AMS-60", query.cheapestConnection("NUE", "AMS"));
		Assert.assertEquals(CONNECTION_NOT_FOUND_ERR, query.cheapestConnection("AMS", "FRA"));		
		Assert.assertEquals("LHR-NUE-FRA-LHR-93", query.cheapestConnection("LHR", "LHR"));
		
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
}
