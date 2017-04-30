package com.assessment.flitetrakr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * <p>Ensures quality of <b><code>com.assessment.flitetrakr.CLI</code></b>'s methods.</p>
 * @author rsolano
 *
 */
public class CLITest {
	String dataDir = System.getProperty("CONNECTIONS_DATA") + File.separatorChar + "data";
	
	/**
	 * <p>Reads and processes the resource file named as <code>/com/assessment/flitetrakr/cli-test1.txt</code>.</p>
	 * @throws IOException
	 */
	@Test
	public void testCLIWithResourceStream() throws IOException {
		String resourcePath = "/com/assessment/flitetrakr/cli-test1.txt";
		InputStream input = getClass().getResourceAsStream(resourcePath);
		CLI cli = new CLI(input, System.out);
		
		System.out.println(
				String.format(
					"testCLIWithResourceStream('%s')",
					resourcePath
				)
			);		
		cli.process();
		
		
	}
	
	/**
	 * <p>Reads and processes the a physical file specified via the system property named as <code>CONNECTIONS_DATA</code>.</p>
	 * @throws IOException
	 */
	@Test
	public void testCLIWithFileStream() throws IOException {		
		String dataFilePath = dataDir + File.separatorChar + "europe.txt";		
		CLI cli = new CLI(new FileInputStream(dataFilePath), System.out);

		System.out.println(
				String.format(
					"testCLIWithFileStream('%s')",
					dataFilePath
				)
			);		
		cli.process();

		
	}
}
