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
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @example Hello
 *
 * (the tag @example followed by the name of an example included in
 * folder 'examples' will automatically include the example in the javadoc.)
 *
 */

public class Command {
  public static final String VERSION = "##library.prettyVersion##";

  protected final ArrayList<String> outputBuffer = new ArrayList<>();
  protected final Runtime runtime = Runtime.getRuntime();

  public String command;
  public boolean success;

  /**
   * 
   */
  public Command(final String theCommand) {
    command = theCommand;
  }

  /**
   * Runs the command. Returns true if the command was successful.
   * The output of the command can be accessed by calling getOutput().
   *
   * @return true if the command ran successfully,
   * false if there was an error running the command.
   */
  public boolean run() {
    success = false;
    outputBuffer.clear();

    try {
      final Process process = runtime.exec(command);

      final BufferedReader
        out = new BufferedReader(new InputStreamReader(process.getInputStream())), 
        err = new BufferedReader(new InputStreamReader(process.getErrorStream()));

      success = process.waitFor() == 0;

      String read;
      while ((read = out.readLine()) != null)  outputBuffer.add(read);

      final String msg = "COMMAND ERROR(s):\n";
      final StringBuilder sb = new StringBuilder(msg);

      while ((read = err.readLine()) != null)  sb.append(read).append('\n');
      if (sb.length() != msg.length())  System.err.println(sb);
    }
    catch (final IOException e) {
      System.err.println("COMMAND ERROR: " + e.getMessage());
    }
    catch (final InterruptedException e) {
      System.err.println("COMMAND INTERRUPTED: " + e.getMessage());
    }

    return success;
  }

  /**
   * Returns each line of the command's output as an Array of String objects.
   * Useful if you need to capture the results from running a command. 
   */
  public String[] getOutput() {
    return outputBuffer.toArray(new String[outputBuffer.size()]);
  }

  /**
   * Returns each line of the command's output as a List of String objects.
   * Useful if you need to capture the results from running a command. 
   */
  @SuppressWarnings("unchecked") public List<String> getOutputAsList() {
    return (List<String>) outputBuffer.clone();
  }

  /**
   * Returns the command String being used.
   *
   * @return String
   */
  @Override public String toString() {
    return command + ' ' + success;
  }
}
