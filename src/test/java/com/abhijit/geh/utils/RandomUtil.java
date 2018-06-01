package com.abhijit.geh.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtil.class);

	private RandomUtil() {
	}

	public static String randomWords(int wordCount) {
		StringBuilder paragraph = new StringBuilder();
		for (int i = 0; i < wordCount; i++) {
			paragraph.append(randomString(randomInteger(10)));
			if (randomDouble(1) > .8) {
				paragraph.append(randomCharacter(".,:;!"));
			}
			paragraph.append(" ");
		}
		return paragraph.toString()
		    .trim() + ".";
	}

	public static String randomString(int strLength) {
		return randomString(strLength, false);
	}

	private static String randomString(int strLength, boolean camelCase) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(randomCharacter(camelCase));
		for (int i = 1; i < strLength; i++) {
			stringBuilder.append(randomCharacter(false));
		}
		return stringBuilder.toString();
	}

	public static char randomCharacter(boolean upperCase) {
		return (char) (randomInteger(26) + (upperCase ? 'A' : 'a'));
	}

	public static char randomCharacter(String string) {
		return string.charAt(randomInteger(string.length()));
	}

	public static int randomInteger(int limit) {
		return (int) randomDouble(limit);
	}

	public static int randomInteger(int lowerlimit, int upperlimit) {
		return (int) randomDouble(lowerlimit, upperlimit);
	}

	public static double randomDouble(int limit) {
		return randomDouble(0, limit);
	}

	public static double randomDouble(int lowerlimit, int upperlimit) {
		return lowerlimit + Math.random() * (upperlimit - lowerlimit);
	}

	public static <T> List<T> randomize(List<T> list) {
		List<T> result = new ArrayList<>();
		int index = 0;
		while (result.size() < list.size()) {
			T t = list.get((int) (Math.random() * list.size()));
			index++;
			if (!result.contains(t)) {
				result.add(t);
			}
		}

		LOGGER.debug("size: {}, count: {}", list.size(), index);

		return result;
	}

}
