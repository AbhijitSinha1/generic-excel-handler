package com.abhijit.geh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassCast {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassCast.class);

	private ClassCast() {
	}

	@SuppressWarnings("unchecked")
	public static <R extends Object, T extends Object> R cast(T value, Class<R> clz) {
		LOGGER.debug("cast {} to {}", value, clz);
		switch (clz.getName()) {
			case "short": {
				return (R) cast(value, Short.class);
			}
			case "int": {
				return (R) cast(value, Integer.class);
			}
			case "long": {
				return (R) cast(value, Long.class);
			}
			case "double": {
				return (R) cast(value, Double.class);
			}
			case "boolean": {
				return (R) cast(value, Boolean.class);
			}
			case "char": {
				return (R) cast(value, Character.class);
			}
			case "java.lang.Short": {
				return (R) Short.valueOf(value.toString());
			}
			case "java.lang.Integer": {
				try {
					return (R) Integer.valueOf(value.toString());
				} catch (Exception e) {
					return (R) (Integer) (Double.valueOf(value.toString())
					    .intValue());
				}
			}
			case "java.lang.Long": {
				return (R) Long.valueOf(value.toString());
			}
			case "java.lang.Double": {
				return (R) Double.valueOf(value.toString());
			}
			case "java.lang.Boolean": {
				return (R) Boolean.valueOf(value.toString());
			}
			case "java.lang.Character": {
				return (R) Character.valueOf(value.toString()
				    .charAt(0));
			}
			case "java.lang.String": {
				return (R) value.toString();
			}
		}

		// Object
		return (R) value;
	}
}
