package com.assessment.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <P>Helper class containing convenience methods for reading/writing strings.</P>
 * @author rsolano
 *
 */
public class StringIO {
	
	/**
	 * <p>Reads all lines in a text file and stores them into a list of strings</p> 
	 * @param input
	 * @return A new instance of <code>List&lt;String&gt;</code> containing all lines loaded <code>input</code>.
	 * @throws IOException
	 */
	public static List<String> readLines(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));		
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.trim();
			if(line.length() > 0)
				lines.add(line);
		}
		
		return lines;
		
	}
	
	
	/**
	 * <p>Removes all occurrences of <b><code>c</code></b> from <b><code>source</code></b> string.</p>
	 * @param source Non null character string.
	 * @param c The character to be removed.
	 * @return A new string not containing character <b><code>c</code></b.
	 */
	public static String removeAll(String source, char c) {
		StringBuilder buffer = new StringBuilder();
		
		for(int i = 0; i < source.length(); i++) {
			if(source.charAt(i) != c) {
				buffer.append(source.charAt(i));
			}
		}
		return buffer.toString();
	}
}
