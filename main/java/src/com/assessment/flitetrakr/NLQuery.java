package com.assessment.flitetrakr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.regex.Pattern;

import com.assessment.io.StringIO;



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
	private static final String FROM_KEWORD="FROM";
	private static final String TO_KEWORD="TO";
	private static final String BETWEEN_KEWORD="BETWEEN";
	private static final String AND_KEWORD="AND";
	private static final String BELOW_KEWORD="BELOW";
	private static final String STOP_KEYWORD="STOP";
	private static final String MINIMUM_KEWORD="MINIMUM";
	private static final String MAXIMUM_KEWORD="MAXIMUM";
	
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
	

	public String connectionPrice(String questionText) {		
		int start = questionText.lastIndexOf(CONNECTION_KEYWORD) + CONNECTION_KEYWORD.length();
		int end = StringIO.questionMarkPos(questionText);
		
		if(end == -1) {
			end = questionText.length();
		} 
		
		String connection = questionText.substring(start, end).trim();
		int price = query.connectionPrice(connection.split("\\-"));
				
		return Integer.toString(price);
		
	}
	
	
	public String cheapestConnection(String questionText) {
		String terminalPoints[] = StringIO.getTerminals(questionText, FROM_KEWORD, TO_KEWORD);
		String connection = query.cheapestConnection(terminalPoints[0].trim(), terminalPoints[1].trim());
		
		return connection; 
	}

	
	public String connectionsWithMinimumOrMaximum(String questionText) {
		String terminalPoints[] = StringIO.getTerminals(questionText, BETWEEN_KEWORD, AND_KEWORD);
		int stops;
		int result = 0;
		
		if(questionText.contains(MAXIMUM_KEWORD)) {
			
			stops = Integer.parseInt(StringIO.substring(questionText, MAXIMUM_KEWORD, STOP_KEYWORD));
			result = query.connectionsWithMaximumStops(stops, terminalPoints[0].trim(), terminalPoints[1].trim());
			
		} else if (questionText.contains(MINIMUM_KEWORD)) {
			
			stops = Integer.parseInt(StringIO.substring(questionText, MINIMUM_KEWORD, STOP_KEYWORD));
			result = query.connectionsWithMinimumStops(stops, terminalPoints[0].trim(), terminalPoints[1].trim());
			
		}
		return Integer.toString(result);
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
