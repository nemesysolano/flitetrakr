package com.assessment.io;

import java.io.*;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;

/**
 * <P>Helper class containing convenience methods for reading, writing  strings.</P>
 * @author rsolano
 *
 */
public class StringIO {
	
	/**
	 * Default list separator.
	 */
	public static final String DEFAULT_LIST_SEPARATOR = ", ";
	
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
	 * Converts an array of strings into a linked list of strings. Null strings are ignored.
	 * @param strings A non-null array of strings.
	 * @return A new linked list containing all strings contained in <code><b>strings</b></code> array.
	 */
	public static LinkedList<String> toLinkedList(String[] strings) {
		LinkedList<String> list = new LinkedList<String>();
		
		for(String string: strings) {
			if(string != null) {
				list.add(string);
			}
		}
		
		return list;
	}	
	
	/**
	 * Joins all strings contained in an array into a single string.
	 * @param strings The array containing all strings being merged. 
	 * @param separator Optional (null == means not provided) string used as value separator in the new string.
	 * @return
	 */
	public static String join(String[] strings, String separator) {
		StringBuilder buffer = new StringBuilder();
		
		
		for(String string: strings) {
			if(string != null) {
				
				buffer.append(string);
				
				if(separator != null) {
					buffer.append(separator);
				}
					
			}
		}
		
		if(separator != null && buffer.length() > 0) {
			buffer.delete(buffer.length() - separator.length(), buffer.length()-1);
		}
		return buffer.toString().trim();
	}	

	
}
