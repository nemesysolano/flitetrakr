package com.assessment.data;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * <p>This class helps to compare linked list of strings.</p>
 * @author rsolano
 *
 */
public class ComparableStringList extends LinkedList<String> implements Comparable<ComparableStringList>{

	private static final long serialVersionUID = 1643523667638289070L;

	/**
	 * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive 
	 * integer as this object is less than, equal to, or greater than the specified object.
	 * @return An integer whose value is determined by the aforementioned criteria.
	 */	
	public int compareTo(ComparableStringList other) {
		
		if(other == null || this.size() < other.size() ){
			return -1;
		}
		
		if(this.size() > other.size()) {
			return 1;
		}
		
		
		Iterator<String> myStrings = this.iterator();
		Iterator<String> yourStrings = this.iterator();
		int result = 0;
		
		while(myStrings.hasNext()) {
			String myString = myStrings.next();
			String yourString = yourStrings.next();
			
			if(myString != null && yourString == null){
				result += 1;
			}else if(myString == null && yourString != null) {
				result -= 1;
			} if(myString != null && yourString != null)  {
				result += myString.compareTo(yourString);
			}
		}
		
		return result;		
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object other) {
		if(other instanceof ComparableStringList && other != null) {
			return this.compareTo((ComparableStringList) other) == 0;
		}
		
		return false;
	}
}
