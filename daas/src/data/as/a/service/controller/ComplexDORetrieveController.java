package data.as.a.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;

@Controller
public class ComplexDORetrieveController {

	@RequestMapping(value = "/{modelName}/{version}/q", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieve(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable int version,
			@RequestParam("c") String conditions) throws UserException,
			SystemException {

		return conditions;
	}

	@RequestMapping(value = "/{modelName}/q", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieve(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @RequestParam("c") String conditions)
			throws UserException, SystemException {

		return this.retrieve(appid, apiKey, modelName, 1, conditions);
	}
}
