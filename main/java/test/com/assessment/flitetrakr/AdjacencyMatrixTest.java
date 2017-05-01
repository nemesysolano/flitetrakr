package com.assessment.flitetrakr;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.assessment.data.AdjacencyMatrix;
import com.assessment.data.DirectedGraph;

public class AdjacencyMatrixTest {
	
	/**
	 * 
	 */
	final static String[] connections = {
		"Connections: NUE-FRA-43,NUE-AMS-67,FRA-AMS-17,FRA-LHR-27,LHR-NUE-23",
		"Connections: NUE-FRA-43,NUE-AMS-67, FRA-AMS-17,FRA-LHR-27, LHR-NUE-23"
	};

	
	/**
	 * A price list that violates implementation note 1, which demands that the whitespace is single occurrence of the ASCII '\x32' character
	 */
	final static String IMPLEMENTATION_NOTE1_VIOLATION = "Connections: NUE-FRA-43,\tNUE-AMS-67, FRA-AMS-17, FRA-LHR-27, LHR-NUE-23";
	
	/**
	 * A price list that violates implementation note 1, which requires no whitespace between the word 'Connections' and the ':' (colon) character.
	 */
	final static String IMPLEMENTATION_NOTE2_VIOLATION = "Connections : NUE-FRA-43, NUE-AMS-67, FRA-AMS-17, FRA-LHR-27, LHR-NUE-23";
	
	/**
	 * A price list that violates implementation note 3, which expects a whitespace between ':' and the 1st value.
	 */
	final static String IMPLEMENTATION_NOTE3_VIOLATION = "Connections:NUE-FRA-43, NUE-AMS-67, FRA-AMS-17, FRA-LHR-27, LHR-NUE-23";
	
	/**
	 * A price list that Violates implementation note 4, which expects no whitespace between a value and the following comma.
	 */
	final static String IMPLEMENTATION_NOTE4_VIOLATION = "Connections: NUE-FRA-43 , NUE-AMS-67, FRA-AMS-17, FRA-LHR-27, LHR-NUE-23";
	
	
	

	/**
	 * This method ensures that all valid strings are accepted by <b><code>com.assessment.flitetrakr.DataSet</code></b>'s constructor.
	 * @throws ParseException 
	 */
	@Test
	public void testValidConnectionStrings() throws ParseException {
		
		System.out.println(String.format("DataSetTest.testValidConnectionStrings with '%s'", AdjacencyMatrix.CONNECTIONS_TABLE_PATTERN));
		
		for(String connection: connections) {
			System.out.println(String.format("DataSetTest.testValidConnectionStrings(%s)", connection));
			new AdjacencyMatrix(connection);
		}
	}
	
	/**
	 * <p>This method verifies that datasets are symmetric.</p>
	 * <p>The symmetry feature speeds up search algorithms.</p>
	 * @throws ParseException
	 */
	@Test
	public void testSymmetricDataset() throws ParseException {
		
		System.out.println(String.format("DataSetTest.testSymmetricDataset with '%s'", AdjacencyMatrix.CONNECTIONS_TABLE_PATTERN));
		
		for(String connection: connections) {			
			AdjacencyMatrix dataSet = new AdjacencyMatrix(connection);
			int length = dataSet.length();
			
			System.out.println(String.format("DataSetTest.testSymmetricDataset(%s)", connection));
			
			for(int x = 0; x < length; x++) {
				for(int y = 0; y < length; y++) {
					Assert.assertEquals(dataSet.get(x, y) , dataSet.get(y, x));
				}
			}
		}
	}	
	
	public void testImplementationNote(int number, String connection) throws ParseException {
		System.out.println(String.format("testImplementationNote: Confirming that price lists violating implementation note # %d are rejected.",number));
		new AdjacencyMatrix(connection);		
	}
	
	@Test(expected=ParseException.class)
	public void testImplementationNote1() throws ParseException {
		testImplementationNote(1, IMPLEMENTATION_NOTE1_VIOLATION);
	}
	
	@Test(expected=ParseException.class)
	public void testImplementationNote2() throws ParseException {
		testImplementationNote(2, IMPLEMENTATION_NOTE2_VIOLATION);
	}
	
	@Test(expected=ParseException.class)
	public void testImplementationNote3() throws ParseException {
		testImplementationNote(3, IMPLEMENTATION_NOTE3_VIOLATION);
	}
	
	@Test(expected=ParseException.class)
	public void testImplementationNote4() throws ParseException {
		testImplementationNote(4, IMPLEMENTATION_NOTE4_VIOLATION);
	}	
}
