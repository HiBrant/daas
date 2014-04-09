package data.as.a.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import data.as.a.service.adaptor.DeleteOneAdaptor;
import data.as.a.service.adaptor.exception.ModelNotExistsException;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.executors.ModelCheckExistExecutor;

@Controller
public class DODeleteController {

	@RequestMapping(value = "/{modelName}/{version}/{_id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable int version,
			@PathVariable String _id) throws UserException, SystemException {

		ModelCheckExistExecutor executor = new ModelCheckExistExecutor();
		DataModelObject dmo = new DataModelObject(appid, modelName, null, version);
		if (!executor.execute(dmo)) {
			throw new ModelNotExistsException(modelName, version);
		}

		DeleteOneAdaptor adaptor = new DeleteOneAdaptor();
		adaptor.execute(dmo, _id);
	}

	@RequestMapping(value = "/{modelName}/{_id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable String _id)
			throws UserException, SystemException {

		this.delete(appid, apiKey, modelName, 1, _id);
	}
}
