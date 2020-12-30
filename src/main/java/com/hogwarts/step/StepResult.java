package com.hogwarts.step;


import org.junit.jupiter.api.function.Executable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author fyh
 * @date 2020/12/30
 */
public class StepResult extends BaseResult{

	private ArrayList<Executable> assertList;

	private HashMap<String, String> stepVariables;

	public ArrayList<Executable> getAssertList() {
		return assertList;
	}

	public void setAssertList(ArrayList<Executable> assertList) {
		this.assertList = assertList;
	}

	public HashMap<String, String> getStepVariables() {
		return stepVariables;
	}

	public void setStepVariables(HashMap<String, String> stepVariables) {
		this.stepVariables = stepVariables;
	}
}
