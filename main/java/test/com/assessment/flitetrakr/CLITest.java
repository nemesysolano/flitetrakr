package com.assessment.flitetrakr;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * <p>Ensures quality of <b><code>com.assessment.flitetrakr.CLI</code></b>'s methods.</p>
 * @author rsolano
 *
 */
public class CLITest {

	@Test
	public void testCLIWithResourceStream() throws IOException {
		InputStream input = getClass().getResourceAsStream("/com/assessment/flitetrakr/cli-test1.txt");
		CLI cli = new CLI(input, System.out);
		cli.process();
	}
}
