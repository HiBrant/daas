package data.as.a.service.convert.controller;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import data.as.a.service.convert.Converter;
import data.as.a.service.exception.DaaSException;
import data.as.a.service.exception.common.UnhandledException;

public class Exception2JSONConverter implements
		Converter<Exception, JSONObject, Throwable> {

	@Override
	public JSONObject convert(Exception e) {
		
		JSONObject json = new JSONObject();
		if (e instanceof DaaSException) {
			DaaSException de = (DaaSException) e;
			json.put("code", de.getErrorCode());
			json.put("msg", de.getMessage());
		} else if (e instanceof JSONException) {
			JSONException je = (JSONException) e;
			json.put("code", DaaSException.CODE_JSON);
			json.put("msg", "JSON Error: " + je.getMessage());
		} else {
			UnhandledException ue = new UnhandledException(e);
			json.put("code", ue.getErrorCode());
			json.put("msg", ue.getMessage());
		}
		return json;
	}

}
