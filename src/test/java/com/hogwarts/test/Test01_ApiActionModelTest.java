package com.hogwarts.test;

import com.hogwarts.action.ApiActionModel;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @author fyh
 * @date 2020/12/30
 */
public class Test01_ApiActionModelTest {

	public static final Logger logger = LoggerFactory.getLogger(Test01_ApiActionModelTest.class);

	@Test
	void runTest(){
		ApiActionModel actionModel = new ApiActionModel();
		actionModel.setUrl("https://qyapi.weixin.qq.com/cgi-bin/gettoken");

		HashMap<String, String> query = new HashMap<>();
		query.put("corpid", "${corpid}");
		query.put("corpsecret", "${corpsecret}");
		actionModel.setQueryParam(query);

		HashMap<String, String> requestData = new HashMap<>();
		requestData.put("corpid", "ww0596505f44736493");
		requestData.put("corpsecret", "09s8oDrnIjhheZ0vmBSsVAWGnV7Rvf_ynD8BTDz84xI");

		Response response = actionModel.run(requestData);
		logger.info("response:" + response);
	}
}
