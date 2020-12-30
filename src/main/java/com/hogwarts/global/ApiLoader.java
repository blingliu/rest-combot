package com.hogwarts.global;

import com.hogwarts.action.ApiActionModel;
import com.hogwarts.model.ApiModel;
import com.hogwarts.utils.ResourcesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author fyh
 * @date 2020/12/30
 */
public class ApiLoader {

	public static Logger logger = LoggerFactory.getLogger(ApiLoader.class);

	/**
	 * 存放所有的ApiModel
	 */
	private static List<ApiModel> apis = new ArrayList<>();

	/**
	 * 加载该目录下的所有文件
	 * @param dir	目录地址
	 */
	public static void load(String dir){
		Arrays.stream(Objects.requireNonNull(new File(dir).list())).forEach(filename->{
			apis.add(ApiModel.load(dir + File.separator + filename));
		});
	}

	/**
	 * 获取ApiActionModel
	 * @param apiName	api的name
	 * @param actionName	action的name
	 * @return	ApiActionModel
	 */
	public static ApiActionModel getAction(String apiName, String actionName){
		final ApiActionModel[] apiActionModel = {new ApiActionModel()};
		apis.stream().filter(api -> api.getName().equals(apiName)).forEach(
				api->{
					apiActionModel[0] = api.getActions().get(actionName);
				}
		);

		if (apiActionModel[0] != null){
			return apiActionModel[0];
		}

		logger.info("没有找到接口对象的：" + apiName + "中的action:" + actionName);
		return null;
	}
}
