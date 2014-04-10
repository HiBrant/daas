package data.as.a.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import data.as.a.service.adaptor.exception.ModelNotExistsException;
import data.as.a.service.adaptor.impl.RetrieveAllAdaptor;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.executors.ModelCheckExistExecutor;

@Controller
public class DORetrieveController {

	@RequestMapping(value = "/{modelName}/{version}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieveAll(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable int version)
			throws UserException, SystemException {

		DataModelObject dmo = new DataModelObject(appid, modelName, null, version);
		ModelCheckExistExecutor executor = new ModelCheckExistExecutor();
		if (!executor.execute(dmo)) {
			throw new ModelNotExistsException(modelName, version);
		}
		
		RetrieveAllAdaptor adaptor = new RetrieveAllAdaptor();
		return adaptor.execute(dmo, null);
	}

}
