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
import data.as.a.service.adaptor.impl.RetrieveBatchAdaptor;
import data.as.a.service.adaptor.impl.RetrieveOneByIdAdaptor;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.executors.ModelCheckExistExecutor;

@Controller
public class SimpleDORetrieveController {

	@RequestMapping(value = "/{modelName}/{version}/all", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieveAll(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable int version)
			throws UserException, SystemException {

		DataModelObject dmo = new DataModelObject(appid, modelName, null,
				version);
		ModelCheckExistExecutor executor = new ModelCheckExistExecutor();
		if (!executor.execute(dmo)) {
			throw new ModelNotExistsException(modelName, version);
		}

		RetrieveBatchAdaptor adaptor = new RetrieveBatchAdaptor();
		return adaptor.execute(dmo, null);
	}

	@RequestMapping(value = "/{modelName}/all", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieveAll(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName) throws UserException,
			SystemException {

		return this.retrieveAll(appid, apiKey, modelName, 1);
	}

	@RequestMapping(value = "/{modelName}/{version}/{_id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieveOneById(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable int version,
			@PathVariable String _id) throws UserException, SystemException {

		DataModelObject dmo = new DataModelObject(appid, modelName, null,
				version);
		ModelCheckExistExecutor executor = new ModelCheckExistExecutor();
		if (!executor.execute(dmo)) {
			throw new ModelNotExistsException(modelName, version);
		}

		RetrieveOneByIdAdaptor adaptor = new RetrieveOneByIdAdaptor();
		return adaptor.execute(dmo, _id);
	}

	@RequestMapping(value = "/{modelName}/{_id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieveOneById(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable String modelName, @PathVariable String _id)
			throws UserException, SystemException {

		return this.retrieveOneById(appid, apiKey, modelName, 1, _id);
	}
}
