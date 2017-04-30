package com.assessment.flitetrakr;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;
/**
 * <p>This class models a connections dataset. A dataset is built from a string that represent a price list;</p>
 * <p>The format of each connection will be defined by <br/><b><code>&lt;code-of-departure-airport&gt;-&lt;code-of-arrival-airport&gt;-&lt;price-in-euro&gt;</code></b>; so e.g. AMS-PDX-617. Multiple values will be separated by a comma and an optional whitespace. The line containing the price list will have the prefix <b><code>Connections:</code></b></p>
 * @author rsolano
 *
 */
public class DataSet {

	/**
	 * Regular expression that describes single connection in the connections string.
	 */
	public static String CONNECTION_PATTERN="\\w+\\-\\w+\\-\\d+";
	
	/**
	 * <p>Regular expression that describes separators</p> 
	 * <p>Multiple values will be separated by a comma (',') and an optional whitespace (\s).</p>
	 */
	public static String CONNECTION_SEPARATOR_PATTERN="\\,\\x20?";
	
	/**
	 * Regular expression that describes the whole connections string.
	 */
	public static Pattern CONNECTIONS_TABLE_PATTERN = Pattern.compile(
		String.format(
			"^CONNECTIONS:\\x20%s(%s%s)*$",
			CONNECTION_PATTERN,
			CONNECTION_SEPARATOR_PATTERN,
			CONNECTION_PATTERN
		)
	);

	
		
	/**
	 * <p>Connections table.</p>
	 * <p>This two dimensional array contain the fare prices (in euros) between airports. 
	 */
	int[][] connectionsTable;
	
	/**
	 *  This map indexes <b><code>this.connectionsTable</code></b>'s row/columns by airport codes.
	 */
	Map<String, Integer> connectionsIndex;
	
	/**
	 * <p>Parses the string representing the connections table. Airport codes are regarded as case insensitive.</p>
	 * <p>&nbsp;</p>
	 * 
	 * @param line A string representing a price list; this string is the first line in the input stream.
	 * @throws java.text.ParseException If <b><code>connections</code></b> is not a valid connections table.
	 */
	public DataSet(String connections) throws ParseException {
		String trimmed = connections.trim().toUpperCase();
		Matcher matcher = CONNECTIONS_TABLE_PATTERN.matcher(trimmed);
		
		if(!matcher.matches()){
			throw new ParseException("Invalid connections table '"+connections+"\'", 0);
		}
		
		String[][] connectionRecords = extractConnectionRecords(trimmed);
		
		this.connectionsIndex = createConnectionsIndex(connectionRecords);
		this.connectionsTable = createConnectionsTable(connectionRecords, this.connectionsIndex);
	}
	
	/**
	 * <p>Creates a two dimensional array whose elements are sub arrays that represent connection records</p>
	 * <p>For example: <br/> if <b><code>connections == &quot;Connections: AMS-PDX-617,NUE-AMS-123, AMS-LHR-43&quot;</code></b>, then this function returns<br/>
	 * <b><code>{{"AMS",PDX",617}, {"NUE","AMS",123}, {"AMS","LHR",43}}</code></b></p>
	 * <p>&nbsp;</p>
	 * 
	 * @param connections A string representing price list.
	 * @return A two dimensional array complying the aforementioned requirements.
	 */
	String[][] extractConnectionRecords(String connections) {
		int colon = connections.indexOf(':');
		int index = 0;
		String[] lines = connections.substring(colon+1).split(CONNECTION_SEPARATOR_PATTERN);
		String[][] connectionRecords = new String[lines.length][];
		
		for(String line: lines) {
			connectionRecords[index] = line.split("\\-");
			index++;
		}
		
		return connectionRecords;
	}
	
	/**
	 * @param connectionRecords A two dimensional array whose elements are sub arrays that represent connection records. This parameter is value returned by <b><code>createConnectionsIndex</code></b>.
	 * @return A map that indexes <b><code>this.connectionsTable</code></b>'s row/columns by airport codes.
	 */
	private Map<String, Integer> createConnectionsIndex(String[][] connectionRecords) {
		Map<String, Integer> connectionsIndex = new HashMap<String, Integer>();
		int offset = 0;
		
		for(String[] record: connectionRecords) {
			String firstCode = record[0];
			String secondCode = record[1];
			
			if(!connectionsIndex.containsKey(firstCode)) {
				connectionsIndex.put(firstCode, offset);
				offset++;
			}
			
			if(!connectionsIndex.containsKey(secondCode)) {
				connectionsIndex.put(secondCode, offset);
				offset++;
			}			
		}
		
		return connectionsIndex;
	}
	
	/**
	 * 
	 * @param connectionRecords A two dimensional array whose elements are sub arrays that represent connection records. This parameter is value returned by <b><code>createConnectionsIndex</code></b>.
	 * @param connectionsIndex  A map that indexes <b><code>this.connectionsTable</code></b>'s row/columns by airport codes.
	 * @return
	 */
	private int[][] createConnectionsTable(String[][] connectionRecords, Map<String, Integer> connectionsIndex) {
		int size = connectionsIndex.size();
		int [][] connectionsTable = new int[size][size];
		
		for(String[] record: connectionRecords) {
			String firstCode = record[0];
			String secondCode = record[1];
			int price = Integer.parseInt(record[2]);			
			int x = connectionsIndex.get(firstCode);
			int y = connectionsIndex.get(secondCode);
			
			// The table is filled symmetrically in order to speed up search algorithm.
			connectionsTable[x][y] = price;
			connectionsTable[y][x] = price;
		}
		return connectionsTable;
	}
	
	/**
	 * @param x Row number.
	 * @param y Column number. 
	 * @return <b></code>this.connectionsTable[x][y]</code></b>
	 */
	public int get(int x, int y) {
		return connectionsTable[x][y];
	}
	
	/**
	 * @param a Airport code
	 * @param b Airport code 
	 * @return The price corresponding to direct connection between airports a and b.
	 */
	public int get(String a, String b) {
		Integer x = connectionsIndex.get(a);
		Integer y = connectionsIndex.get(b);
		
		if(x == null || y == null){
			throw new ArrayIndexOutOfBoundsException(
				String.format(
					"No entry exists for a='%s', b='%s'", 
					a,
					b
				)		
			);
		}
		
		return get(x, y);
	}	
	
	/**
	 * <p></p>
	 * @return connectionsTable.length.
	 */
	public int length() {
		return connectionsTable.length;
	}
	
	
}
