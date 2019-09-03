package utilities;

public class Utility {

	public boolean isParamValid(String param) {
		if (param.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public String getData(String param) {

		String data = dbRequest(param);

		if (data != null) {
			return data;
		} else {
			data = webRequest(param);
		}

		return data;
	}

	public String dbRequest(String param) {
		return "";
	}

	public String webRequest(String param) {
		return "";
	}

}
