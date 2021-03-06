package data.as.a.service.controller.api;

import net.sf.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import data.as.a.service.adaptor.impl.CreateOneAdaptor;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.exception.common.ModelNotAvailableException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.executors.ModelCheckExistExecutor;

@Controller
@RequestMapping("/__data")
public class DOCreateController extends BaseController {
	
	@RequestMapping(value = "/{modelName}/{version}", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object create(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable int version,
			@RequestBody String json) throws UserException, SystemException {
		
		this.verify(appid, apiKey);

		DataModelObject dmo = new DataModelObject(appid, modelName, null,
				version);
		
		ModelCheckExistExecutor executor = new ModelCheckExistExecutor();
		if (!executor.execute(dmo)) {
			throw new ModelNotAvailableException(modelName, version);
		}

		CreateOneAdaptor adaptor = new CreateOneAdaptor();
		return adaptor.execute(dmo, JSONObject.fromObject(json));
	}

	@RequestMapping(value = "/{modelName}", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object create(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @RequestBody String json)
			throws UserException, SystemException {

		return this.create(appid, apiKey, modelName, 1, json);
	}
}
