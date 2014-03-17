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

import data.as.a.service.adaptor.CreateAdaptor;
import data.as.a.service.adaptor.exception.ModelNotExistsException;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.service.MetadataOperator;

@Controller
public class CreateController {

	@RequestMapping(value = "/{modelName}/{version}", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Object create(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable int version,
			@RequestBody String json) throws UserException, SystemException {

		DataModelObject dmo = new DataModelObject(appid, modelName, null, version);
		if (!MetadataOperator.dataModelExists(dmo)) {
			throw new ModelNotExistsException(modelName, version);
		}
		
		CreateAdaptor adaptor = new CreateAdaptor();
		return adaptor.execute(dmo, JSONObject.fromObject(json));
	}

}
