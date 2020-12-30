package com.hogwarts.step;

import io.restassured.response.Response;

/**
 * @author fyh
 * @date 2020/12/30
 */
public class BaseResult {

	public Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}
