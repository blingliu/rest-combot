package com.hogwarts.testcase;

import com.hogwarts.step.StepModel;
import com.hogwarts.step.StepResult;
import com.hogwarts.utils.FakerUtils;
import com.hogwarts.utils.YamlMapper;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @author fyh
 * @date 2020/12/30
 */
public class ApiTestCaseModel {

	private static Logger logger = LoggerFactory.getLogger(ApiTestCaseModel.class);

	private String name;

	private String description;

	private List<StepModel> steps;

	private ArrayList<Executable> assertList =  new ArrayList<>();

	private HashMap<String,String> testCaseVariables = new HashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<StepModel> getSteps() {
		return steps;
	}

	public void setSteps(List<StepModel> steps) {
		this.steps = steps;
	}

	public ArrayList<Executable> getAssertList() {
		return assertList;
	}

	public void setAssertList(ArrayList<Executable> assertList) {
		this.assertList = assertList;
	}

	public HashMap<String, String> getTestCaseVariables() {
		return testCaseVariables;
	}

	public void setTestCaseVariables(HashMap<String, String> testCaseVariables) {
		this.testCaseVariables = testCaseVariables;
	}

	public static ApiTestCaseModel load(String path){
		return YamlMapper.readValue(path, ApiTestCaseModel.class);
	}


	public void run(){
		this.testCaseVariables.put("getTimeStamp", FakerUtils.getTimeStamp());
		logger.info("用例变量更新： "+testCaseVariables);

		// 执行用例
		steps.forEach(step -> {
			// 执行请求
			StepResult stepResult = step.run(testCaseVariables);

			// 处理step返回的savePart变量
			if (step.getStepVariables().size() > 0){
				testCaseVariables.putAll(step.getStepVariables());
				logger.info("testcase变量更新： "+ testCaseVariables);
			}

			// 处理断言
			if(stepResult.getAssertList().size()>0){
				assertList.addAll(stepResult.getAssertList());
			}
		});

		// 进行断言
		assertAll("",assertList.stream());
	}
}
