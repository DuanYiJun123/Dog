package com.qqduan.dog.core;

import java.util.HashMap;
import java.util.Map;

public class DBtype {
	public static Map<Class<?>, String> typemap = new HashMap<>();

	static {
		typemap.put(String.class, "VARCHAR(32)");
		typemap.put(Integer.class, "INT");
		typemap.put(String[].class, "VARCHAR(32)");
	}

	public static String get(Class<?> clz){
		return typemap.get(clz);
	}

}
