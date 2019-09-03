package controller;

import javax.inject.Inject;

import utilities.Utility;

public class MainFrontContoller {

	@Inject
	Utility utility;

	public String invokingFunctionToGetData(String param){
		if (param == null) {
			throw new NullPointerException();
		}
		else if (!utility.isParamValid(param)) {
			throw new IllegalArgumentException();
		}
		
		String data = utility.getData(param);
		
		if (data == null) {
			throw new Error();
		}
		
		return data;
		
	}

}
