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
	 * Convenience constant for &quot;&quot; value. 
	 */
	public static final String EMPTY_STRING = "";
	
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

	/**
	 * <p>Remove all whitespace characters (\\s+)</p>
	 * @param string Source string.
	 * @return <code><b>null</b></code> if string is null or a new string without any whitespace.
	 */
	public static String removeSpaces(String string) {
		if(string == null) {
			return string;
		}
		
		return string.trim().replaceAll("\\s", EMPTY_STRING);
	}

	
	public static int questionMarkPos(String questionText) {
		int end = questionText.lastIndexOf('?');
		
		if(end == -1) {
			end = questionText.length();
		} 		
		
		return end;
	}
	
	public static String[] getTerminals(String source, String startKeyWord, String delimiterKeyWord) {
		int start = source.lastIndexOf(startKeyWord) + startKeyWord.length();
		int end = questionMarkPos(source);
		
		if(end == -1) {
			end = source.length();
		} 
		
		return source.substring(start, end).trim().split(delimiterKeyWord);
	}
	
	public static String substring(String source, int fromIndex, String start, String end) {
		int startPos = source.indexOf(start, fromIndex);
		int endPos = source.indexOf(end, fromIndex);
		
		if(startPos == -1 && startPos > endPos) {
			return null;
		}
		
		return source.substring(startPos + start.length(), endPos).trim();
	}		
	
	
	public static String substring(String source, String start, String end) {
		return substring(source, 0, start, end);
	}
	
}
