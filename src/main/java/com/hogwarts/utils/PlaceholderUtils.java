package com.hogwarts.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fyh
 * @date 2020/12/29
 *
 * 对${name} 中的内容进行替换
 */
public class PlaceholderUtils {

	/**
	 * 开始标记
	 */
	private static final String OPEN_TOKEN = "${";

	/**
	 * 结束标记
	 */
	private static final String CLOSE_TOKEN = "}";

	/**
	 * 解析${}
	 *
	 * @param text  解析的内容
	 * @return 该方法主要实现了配置文件、脚本等片段中占位符的解析、处理工作，并返回最终需要的数据。
	 * 其中，解析工作由该方法完成，处理工作是由处理器handler的handleToken()方法来实现
	 */
	public static String parseStr(String text, Map<String, String> map) {
		// 验证参数问题，如果是null，就返回空字符串。
		if (text == null || text.isEmpty()) {
			return "";
		}

		// 下面继续验证是否包含开始标签，如果不包含，默认不是占位符，直接原样返回即可，否则继续执行。
		int start = text.indexOf(OPEN_TOKEN, 0);
		if (start == -1) {
			return text;
		}

		// 把text转成字符数组src，并且定义默认偏移量offset=0、存储最终需要返回字符串的变量builder，
		// text变量中占位符对应的变量名expression。判断start是否大于-1(即text中是否存在openToken)，如果存在就执行下面代码
		char[] src = text.toCharArray();
		int offset = 0;
		final StringBuilder builder = new StringBuilder();
		StringBuilder expression = null;
		while (start > -1) {
			// 判断如果开始标记前如果有转义字符，就不作为openToken进行处理，否则继续处理
			if (start > 0 && src[start - 1] == '\\') {
				builder.append(src, offset, start - offset - 1).append(OPEN_TOKEN);
				offset = start + OPEN_TOKEN.length();
			} else {
				//重置expression变量，避免空指针或者老数据干扰。
				if (expression == null) {
					expression = new StringBuilder();
				} else {
					expression.setLength(0);
				}
				builder.append(src, offset, start - offset);
				offset = start + OPEN_TOKEN.length();
				int end = text.indexOf(CLOSE_TOKEN, offset);
				//存在结束标记时
				while (end > -1) {
					//如果结束标记前面有转义字符时
					if (end > offset && src[end - 1] == '\\') {
						// this close token is escaped. remove the backslash and continue.
						expression.append(src, offset, end - offset - 1).append(CLOSE_TOKEN);
						offset = end + CLOSE_TOKEN.length();
						end = text.indexOf(CLOSE_TOKEN, offset);
					} else {
						//不存在转义字符，即需要作为参数进行处理
						expression.append(src, offset, end - offset);
						offset = end + CLOSE_TOKEN.length();
						break;
					}
				}
				if (end == -1) {
					// close token was not found.
					builder.append(src, start, src.length - start);
					offset = src.length;
				} else {
					//首先根据参数的key（即expression）进行参数处理，返回?作为占位符
					builder.append(map.get(expression.toString()));
					offset = end + CLOSE_TOKEN.length();
				}
			}
			start = text.indexOf(OPEN_TOKEN, offset);
		}
		if (offset < src.length) {
			builder.append(src, offset, src.length - offset);
		}
		return builder.toString();
	}


	public static List<String> parseList(List<String> list, Map<String, String> parameter) {
		if (parameter == null || parameter.isEmpty() || list == null || list.isEmpty()) {
			return list;
		}
		ArrayList<String> retureList = new ArrayList<>();
		list.forEach(str -> {
			if (str.contains(OPEN_TOKEN)) {
				retureList.add(parseStr(str, parameter));
			} else {
				retureList.add(str);
			}
		});
		return retureList;
	}

	public static Map<String, String> parseMap(Map<String, String> map, Map<String, String> parameter) {
		if (parameter == null || parameter.isEmpty() || map == null || map.isEmpty()) {
			return map;
		}
		HashMap<String, String> returnMap = new HashMap<String, String>();
		map.forEach((key, value) -> {
			if (value.contains(OPEN_TOKEN)) {
				returnMap.put(key, parseStr(value, parameter));

			}
		});
		return returnMap;
	}

}
