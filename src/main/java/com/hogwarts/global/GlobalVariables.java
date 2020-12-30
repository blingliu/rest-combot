package com.hogwarts.global;

import java.util.HashMap;

/**
 * @author fyh
 * @date 2020/12/30
 *
 * 存放全局变量
 */
public class GlobalVariables {

	public static HashMap<String, String> globalVariables = new HashMap<>();

	public static HashMap<String, String> getGlobalVariables() {
		return globalVariables;
	}

	public static void setGlobalVariables(HashMap<String, String> globalVariables) {
		GlobalVariables.globalVariables = globalVariables;
	}
}

