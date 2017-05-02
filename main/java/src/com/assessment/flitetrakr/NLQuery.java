package com.assessment.flitetrakr;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.regex.Pattern;



/**
 * <p>This class provides a natural language interface to functions defined <code><b>com.assessment.flitetrakr.Query></b></code> class.</p>
 * @author rsolano
 *
 */
public class NLQuery {
	/**
	 * <p>&quot;CONNECTION&quot;</p>
	 * <p>Convenience constant to avoid repetition everywhere.</p>
	 */
	private static final String CONNECTION_KEYWORD="CONNECTION";
	
	private Pattern[] questions;	
	private Method[] methods;
	
	/**
	 * The <code><b>com.assessment.flitetrakr.Query></b></code> instance whose functions are called via this NL Interface.
	 * 
	 */
	Query query;
	
	public NLQuery(Query query) throws NoSuchMethodException, SecurityException {
		this.query = query;
		this.questions = new Pattern[] {
			Pattern.compile("^(WHAT\\s+IS\\s+)?(THE\\s+)?PRICE\\s+(OF\\s+(THE\\s+)?)?CONNECTION\\s+\\w+(\\-\\w+)*\\s*\\??$"),
			Pattern.compile("^(WHAT\\s+IS\\s+)?(THE\\s+)?CHEAPEST\\s+CONNECTION\\s+FROM\\s+\\w+\\s+TO\\s+\\w+\\s*\\?$"),
			Pattern.compile("^(HOW\\s+MANY\\s+)?(DIFFERENT\\s+)?CONNECTIONS\\s+(WITH\\s+)?(MAXIMUM|MINIMUM)\\s+\\d+\\s+(STOP(S)?\\s+)(EXIST(S)?\\s+)?BETWEEN\\s+\\w+\\s+AND\\s+\\w+\\s*\\?$"),
			Pattern.compile("^(HOW\\s+MANY\\s+)?(DIFFERENT\\s+)?CONNECTIONS\\s+WITH\\s+(EXACTLY\\s+)?\\d+\\s+(STOP(S)?\\s+)(EXIST(S)?\\s+)BETWEEN\\s+\\w+\\s+AND\\s+\\w+\\s*\\?$"),
			Pattern.compile("^((FIND\\s+)?(ALL\\s+)?)?CONNECTIONS\\s+FROM\\s+\\w+\\s+TO\\s+\\w+\\s+BELOW\\s+\\d+\\s+(EUROS?)?\\s*\\?$")				
		};
		this.methods = new Method[] {
			NLQuery.class.getMethod("connectionPrice", String.class),
			NLQuery.class.getMethod("cheapestConnection", String.class),
			NLQuery.class.getMethod("connectionsWithMinimumOrMaximum", String.class),
			NLQuery.class.getMethod("connectionsWithExactStops", String.class),
			NLQuery.class.getMethod("connectionsBelowPrice", String.class)
		};
		
	}
	
	int questionMarkPos(String questionText) {
		int end = questionText.lastIndexOf('?');
		
		if(end == -1) {
			end = questionText.length();
		} 		
		
		return end;
	}
	public String connectionPrice(String questionText) {		
		int start = questionText.lastIndexOf(CONNECTION_KEYWORD) + CONNECTION_KEYWORD.length();
		int end = questionMarkPos(questionText);
		
		if(end == -1) {
			end = questionText.length();
		} 
		
		String connection = questionText.substring(start, end).trim();
		int price = query.connectionPrice(connection.split("\\-"));
		
		
		return Integer.toString(price);
		
	}
	
	public String cheapestConnection(String questionText) {
		return null;
	}
	
	public String connectionsWithMinimumOrMaximum(String questionText) {
		return null;
	}

	public String connectionsWithExactStops(String questionText) {
		return null;
	}
	
	public String connectionsBelowPrice(String questionText) {
		return null;
	}
	
	public String evaluate(String questionText) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
		String trimmed = questionText.trim().toUpperCase();
		int index = 0;
		String result;
		
		for(Pattern question: questions) {
			
			if(question.matcher(trimmed).matches()) {
				Method method = methods[index];
				
				result = (String)method.invoke(this, trimmed);
				return result;
			}
			
			index++;
		}
		
		throw  new ParseException("The received text doesn't comply the minimum requirements to be a valid question.", 0);
		
	}
	
}
