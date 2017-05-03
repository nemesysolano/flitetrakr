package com.assessment.flitetrakr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.List;

import com.assessment.util.StringIO;

/** FliteTrakr's command line interface.
 * @author rsolano
 *
 */
public class CLI {
	
	/**
	 * Input stream through which the application receives connections data and questions. 
	 */
	InputStream input;
	
	/**
	 * Output stream used to provide feedback.
	 */
	OutputStream output;
	
	/**
	 * <p>Initializes instance fields whose names match parameters'.</p>
	 * 
	 * @param input Input stream through which the application receives connections data and questions.
	 * @param output Output stream used to provide feedback. 
	 */
	public CLI(InputStream input, OutputStream output) {
		this.input = input;
		this.output = output;
	}
	
	/**
	 * <p>Reads connections data and questions from <code>this.input</code> and the prints expected feedback.</p>
	 * 
	 * <p>This method must be called after successful instantiation.</p>
	 * @throws IOException If <code>this.input</code> is closed.
	 */
	public void process() throws IOException {
		List<String> lines = StringIO.readLines(input);
		PrintWriter writer = new PrintWriter(this.output);
		String connections = lines.get(0);
		
		writer.println(connections);
		
		writer.flush();
	}
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String args[]) throws IOException {
		InputStream input = null;
		CLI instance;
		
		try{
			
			if(args.length > 0){
				input = new FileInputStream(args[0]);
			}
			else {
				input = System.in;
			}
			
			instance = new CLI(input, System.out);
			instance.process();
			
		}finally {
			if(input != System.in)
				input.close();
		}
	}
}
