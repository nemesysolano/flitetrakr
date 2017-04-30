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
}
