package com.hogwarts.model;

import com.hogwarts.action.ApiActionModel;
import com.hogwarts.utils.YamlMapper;

import java.util.HashMap;

/**
 * @author fyh
 * @date 2020/12/30
 */
public class ApiModel {

	private String name;

	private HashMap<String, ApiActionModel> actions = new HashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, ApiActionModel> getActions() {
		return actions;
	}

	public void setActions(HashMap<String, ApiActionModel> actions) {
		this.actions = actions;
	}

	/**
	 * 加载yaml文件
	 * @param path	文件路径
	 * @return	ApiModel
	 */
	public static ApiModel load(String path){
		return YamlMapper.readValue(path, ApiModel.class);
	}
}
