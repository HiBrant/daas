package data.as.a.service.controller;

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

import data.as.a.service.adaptor.UpdateOneAdaptor;
import data.as.a.service.adaptor.exception.ModelNotExistsException;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.executors.ModelCheckExistExecutor;

@Controller
public class DOUpdateController {

	@RequestMapping(value = "/{modelName}/{version}/{_id}", method = RequestMethod.PUT)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object update(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable int version,
			@PathVariable String _id, @RequestBody String json)
			throws UserException, SystemException {

		DataModelObject dmo = new DataModelObject(appid, modelName, null,
				version);
		ModelCheckExistExecutor executor = new ModelCheckExistExecutor();
		if (!executor.execute(dmo)) {
			throw new ModelNotExistsException(modelName, version);
		}

		JSONObject updateJson = JSONObject.fromObject(json);
		updateJson.put("_id", _id);

		UpdateOneAdaptor adaptor = new UpdateOneAdaptor();
		return adaptor.execute(dmo, updateJson);
	}

	@RequestMapping(value = "/{modelName}/{_id}", method = RequestMethod.PUT)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object update(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable String _id,
			@RequestBody String json) throws UserException, SystemException {

		return this.update(appid, apiKey, modelName, 1, _id, json);
	}
}
