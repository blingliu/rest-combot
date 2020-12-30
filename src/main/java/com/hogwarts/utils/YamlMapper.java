package com.hogwarts.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author fyh
 * @date 2020/12/30
 *
 * yaml文件解析类
 *
 */
public class YamlMapper {

	private static Logger logger = LoggerFactory.getLogger(YamlMapper.class);

	private static ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

	/**
	 * 解析yaml文件
	 * @param path	文件路径
	 * @param valueType	映射的对象类型
	 * @param <T>	泛型  返回值类型
	 * @return	返回映射后的对象
	 */
	public static <T>T readValue(String path, Class<T> valueType){
		try {
			return objectMapper.readValue(ResourcesUtils.getResourceAsStream(path), valueType);
		} catch (IOException e) {
			logger.error("the path [{}] load fail", path);
			throw new RuntimeException(e);
		}
	}

}
