package data.as.a.service.controller;

import net.sf.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.convert.JSON2DataModelConverter;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.ModelDefineExecutor;

@Controller
public class ModelDefineController {

	@RequestMapping(value = "/__model", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Object defineModel(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@RequestBody String json) throws UserException, SystemException {

		JSON2DataModelConverter converter = new JSON2DataModelConverter(appid);
		DataModelObject dmo = converter.convert(JSONObject.fromObject(json));

		ModelDefineExecutor mde = new ModelDefineExecutor();
		return mde.execute(dmo);

	}
}
