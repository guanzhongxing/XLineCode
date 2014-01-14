package com.vertonur.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TxtFileReader {
	public static String readTxtFile(InputStream inputStream) {
		StringBuilder contents = new StringBuilder();

		try {
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String line = null;
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {
					contents.append(line);
					// contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return contents.toString();
	}
}
