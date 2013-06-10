/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package deadpixel.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import processing.core.*;

/**
 * 
 * @example Hello
 * 
 *          (the tag @example followed by the name of an example included in
 *          folder 'examples' will automatically include the example in the
 *          javadoc.)
 * 
 */

public class Command {

	private String command;
	private ArrayList<String> outputBuffer;
	private Runtime runtime;
	private boolean success = false;

	public final static String VERSION = "##library.prettyVersion##";

	/**
	 * 
	 */
	public Command(String theCommand) {
		command = theCommand;
		outputBuffer = new ArrayList<String>();
		runtime = Runtime.getRuntime();
	}

	/**
	 * Runs the command. Returns true if the command was successful, false
	 * otherwise. The output of the command can be accessed by calling
	 * getOutput()
	 * 
	 * @return true if the command ran successfully, false if there was an error running the command 
	 */
	public boolean run() {
		outputBuffer.clear();
		try {
			Process process = runtime.exec(command);

			// Capture the output
			InputStream stdin = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(stdin);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				outputBuffer.add(line);
			}

			// Capture the errors
			InputStream stderr = process.getErrorStream();
			isr = new InputStreamReader(stderr);
			br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				PApplet.println("COMMAND ERROR: " + line);
			}

			int returnCode = process.waitFor();
			if (returnCode == 0)
				success = true;
			else
				success = false;
		} catch (IOException e) {
			PApplet.println("COMMAND ERROR: " + e.getMessage());
			success = false;
		} catch (InterruptedException e) {
			PApplet.println("COMMAND INTERRUPTED! " + e.getMessage());
			success = false;
		}

		return success;
	}

	/**
	 * Returns each line of the command's output as an array of String objects. Useful if you need to capture the results 
	 * from running a command. 
	 */
	public String[] getOutput() {
		String[] dummy = new String[0];
		return (String[]) outputBuffer.toArray(dummy);
	}

	/**
	 * return the version of the library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}

}
