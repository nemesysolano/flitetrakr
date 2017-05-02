package com.assessment.flitetrakr;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.assessment.data.AdjacencyMatrix;


public class SampleQuestionsTest {
	public static final String CONNECTION1="Connections: NUE-FRA-43, NUE-AMS-67, FRA-AMS-17, FRA-LHR-27, LHR-NUE-23";

	@Test
	public void testSample1() throws ParseException {
		AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(CONNECTION1);
		Query query = new Query(adjacencyMatrix);
		
		//#1
		Assert.assertEquals(70, query.connectionPrice(new String[] {"NUE", "FRA", "LHR"}));
		
		//#2
		Assert.assertEquals(-1, query.connectionPrice(new String[] {"NUE", "AMS", "LHR"}));	
		
		//#3
		Assert.assertEquals(93, query.connectionPrice(new String[] {"NUE", "FRA", "LHR", "NUE"}));			
		
		//#4:
		Assert.assertEquals("NUE-FRA-AMS-60", query.cheapestConnection("NUE", "AMS"));
		
		//#5
		Assert.assertEquals("No such connection found!", query.cheapestConnection("AMS", "FRA"));
		
		//#6
		Assert.assertEquals("LHR-NUE-FRA-LHR-93", query.cheapestConnection("LHR", "LHR"));	
		
		//#7:
		Assert.assertEquals(2, query.connectionsWithMaximumStops(3, "NUE", "FRA"));
		
		//#8
		Assert.assertEquals(1, query.connectionsWithExactStops(1, "LHR", "AMS"));
		
		Assert.assertEquals("NUE-FRA-LHR-70, NUE-FRA-LHR-NUE-FRA-LHR-163", query.connectionsBelowPrice(170, "NUE", "LHR"));
	}
}
