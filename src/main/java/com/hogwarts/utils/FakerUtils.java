package com.hogwarts.utils;


import org.apache.commons.lang.RandomStringUtils;

/**
 * @author fyh
 * @date 2020/12/21
 * @desc 工具类
 */
public class FakerUtils {

	public static String getRandomStr(int length){
		return RandomStringUtils.randomAlphanumeric(length);
	}

	private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

	public static int getNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}

	/**
	 * 返回生成的手机号码
	 * @return 手机号码
	 */
	public static String getTel() {
		int index = getNum(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
		String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
		return first + second + third;
	}

	public static String getTimeStamp() {

		return null;
	}
}
