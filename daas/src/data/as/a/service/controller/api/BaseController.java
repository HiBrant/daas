package data.as.a.service.controller.api;

import net.sf.json.JSONException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import data.as.a.service.access.entity.jpa.sys.AppEntity;
import data.as.a.service.convert.controller.Exception2JSONConverter;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.exception.common.InvalidAppVerificationException;
import data.as.a.service.metadata.executors.AppVerifyExecutor;

public class BaseController {

	protected void verify(String appid, String apiKey)
			throws InvalidAppVerificationException {
		if (appid == null || appid.trim().length() == 0) {
			throw new InvalidAppVerificationException(appid, apiKey);
		}
		
		if (apiKey == null || apiKey.trim().length() == 0) {
			throw new InvalidAppVerificationException(appid, apiKey);
		}
		
		AppVerifyExecutor executor = new AppVerifyExecutor();
		AppEntity app = new AppEntity();
		app.set_id(appid);
		app.setApiKey(apiKey);
		if (!executor.execute(app)) {
			throw new InvalidAppVerificationException(appid, apiKey);
		}
	}
	
	@ExceptionHandler(UserException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object handleUserException(UserException e) {
		Exception2JSONConverter converter = new Exception2JSONConverter();
		return converter.convert(e);
	}
	
	@ExceptionHandler(SystemException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object handleSystemException(SystemException e) {
		Exception2JSONConverter converter = new Exception2JSONConverter();
		return converter.convert(e);
	}
	
	@ExceptionHandler(JSONException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object handleJSONException(JSONException e) {
		Exception2JSONConverter converter = new Exception2JSONConverter();
		return converter.convert(e);
	}
}
