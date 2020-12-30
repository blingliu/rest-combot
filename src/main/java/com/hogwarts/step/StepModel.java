package com.hogwarts.step;

import com.hogwarts.global.ApiLoader;
import com.hogwarts.global.GlobalVariables;
import com.hogwarts.utils.PlaceholderUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author fyh
 * @date 2020/12/30
 */
public class StepModel {

	private static Logger logger = LoggerFactory.getLogger(StepModel.class);

	private String api;

	private String action;

	private Map<String, String> requestData;

	private Map<String, String> saveGlobal;

	private Map<String, String> savePart;

	private HashMap<String, String> stepVariables;

	private List<AssertModel> asserts;

	private ArrayList<Executable> assertList = new ArrayList<>();

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, String> getRequestData() {
		return requestData;
	}

	public void setRequestData(Map<String, String> requestData) {
		this.requestData = requestData;
	}

	public Map<String, String> getSaveGlobal() {
		return saveGlobal;
	}

	public void setSaveGlobal(Map<String, String> saveGlobal) {
		this.saveGlobal = saveGlobal;
	}

	public Map<String, String> getSavePart() {
		return savePart;
	}

	public void setSavePart(Map<String, String> savePart) {
		this.savePart = savePart;
	}

	public HashMap<String, String> getStepVariables() {
		return stepVariables;
	}

	public void setStepVariables(HashMap<String, String> stepVariables) {
		this.stepVariables = stepVariables;
	}

	public List<AssertModel> getAsserts() {
		return asserts;
	}

	public void setAsserts(List<AssertModel> asserts) {
		this.asserts = asserts;
	}

	public ArrayList<Executable> getAssertList() {
		return assertList;
	}

	public void setAssertList(ArrayList<Executable> assertList) {
		this.assertList = assertList;
	}

	public StepResult run(Map<String, String> testCaseVariables){
		Map<String, String> caseData = new HashMap<>();

		if (requestData != null){
			caseData.putAll(PlaceholderUtils.parseMap(requestData, testCaseVariables));
		}

		// 请求
		Response response = Objects.requireNonNull(ApiLoader.getAction(api, action)).run(caseData);

		// 存储savePart
		if (savePart != null){
			savePart.forEach((key, path)->{
				String value = response.path(path).toString();
				stepVariables.put(key, value);
			});
			logger.info("step变量更新： " + stepVariables);
		}

		// 存储saveGlobal
		if (saveGlobal != null) {
			saveGlobal.forEach((variablesName, path) -> {
				String value = response.path(path).toString();
				GlobalVariables.getGlobalVariables().put(variablesName, value);
				logger.info("全局变量更新： " + GlobalVariables.getGlobalVariables());
			});
		}

		// 存储断言
		if (asserts != null) {
			asserts.stream().forEach(assertModel -> {
				assertList.add(() -> {
					assertThat(assertModel.getReason(), response.path(assertModel.getActual()).toString(), equalTo(assertModel.getExpect()));
				});

			});
		}

		// 将结果封装到StepResult中
		StepResult stepResult = new StepResult();
		stepResult.setStepVariables(stepVariables);
		stepResult.setAssertList(assertList);
		return stepResult;
	}
}
