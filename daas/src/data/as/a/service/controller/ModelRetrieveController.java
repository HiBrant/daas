package data.as.a.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.MetadataExecutor;
import data.as.a.service.metadata.ModelRetrieveAllExecutor;
import data.as.a.service.metadata.datamodel.DataModelObject;

@Controller
public class ModelRetrieveController {

	@RequestMapping(value = "/__model", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieveAll(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey)
			throws UserException, SystemException {

		DataModelObject dmo = new DataModelObject(appid, null, null);
		MetadataExecutor executor = new ModelRetrieveAllExecutor();
		return executor.execute(dmo);
	}
}
