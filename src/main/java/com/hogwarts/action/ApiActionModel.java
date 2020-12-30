package com.hogwarts.action;

import com.hogwarts.global.GlobalVariables;
import com.hogwarts.utils.PlaceholderUtils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @author fyh
 * @date 2020/12/29
 *
 * http api 接口对象
 */
public class ApiActionModel implements Action {

	private String method = "get";

	private String url;

	private String body;

	private HashMap<String, String> headers;

	private HashMap<String, String> queryParam;

	private HashMap<String, String> params;

	private Response response;

	private String get;

	private String post;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

	public HashMap<String, String> getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(HashMap<String, String> queryParam) {
		this.queryParam = queryParam;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public String getGet() {
		return get;
	}

	public void setGet(String get) {
		this.get = get;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Response run(Map<String, String> requestData){
		Map<String, String> requestQuery = null;
		Map<String, String> requestParam = null;
		String runUrl = this.url;
		String runBody = this.body;

		// 创建请求对象
		RequestSpecification requestSpecification = given().log().all();

		if (this.get != null){
			runUrl = get;
			method = "get";
		}

		if (this.post != null){
			runUrl = post;
			method = "post";
		}

		// 先进行全局变量的替换
		// 对runUrl进行替换
		runUrl = PlaceholderUtils.parseStr(runUrl, GlobalVariables.getGlobalVariables());

		// 对runBody进行替换
		runBody = PlaceholderUtils.parseStr(runBody, GlobalVariables.getGlobalVariables());

		// 对query进行替换
		if (queryParam != null){
			requestQuery = new HashMap<>(PlaceholderUtils.parseMap(queryParam, GlobalVariables.getGlobalVariables()));
		}

		// 对params进行替换
		if (params != null){
			requestParam = new HashMap<>(PlaceholderUtils.parseMap(params, GlobalVariables.getGlobalVariables()));
		}

		// 用传入的参数进行替换
		runUrl = PlaceholderUtils.parseStr(runUrl, requestData);
		runBody = PlaceholderUtils.parseStr(runBody, requestData);

		// 对query进行替换
		if (requestQuery != null){
			requestQuery = PlaceholderUtils.parseMap(requestQuery, requestData);
			// 设置query参数
			requestSpecification.queryParams(requestQuery);
		}

		if (requestParam != null){
			requestParam = new HashMap<>(PlaceholderUtils.parseMap(params, requestData));
			// 设置params参数
			requestSpecification.params(requestParam);
		}

		// 设置body
		if (StringUtils.isNotEmpty(runBody)){
			requestSpecification.body(runBody);
		}

		Response response = requestSpecification.request(method, runUrl).then().log().all().extract().response();

		this.response = response;

		return response;
	}
}
