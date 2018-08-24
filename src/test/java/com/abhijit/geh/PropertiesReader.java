package com.abhijit.geh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PropertiesReader {

	private static final Map<String, String> map = new HashMap<>();

	private PropertiesReader() {
	}

	public static PropertiesReader instance() throws IOException {
		return instance("project.properties", ":");
	}

	private static PropertiesReader instance(String string, String splitter) throws IOException {
		PropertiesReader reader = new PropertiesReader();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(ClassLoader.getSystemResource(string).openStream()));

		String text = "";
		while ((text = br.readLine()) != null) {
			String[] split = text.split(splitter);
			map.put(split[0].trim(), split[1].trim());
		}

		return reader;
	}

	public String read(String string) {
		return map.get(string);
	}

}
