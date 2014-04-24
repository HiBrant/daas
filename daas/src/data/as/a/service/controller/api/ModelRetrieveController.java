package data.as.a.service.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.MetadataExecutor;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.executors.ModelRetrieveAllExecutor;
import data.as.a.service.metadata.executors.ModelRetrieveByNameExecutor;
import data.as.a.service.metadata.executors.ModelRetrieveOneExecutor;

@Controller
public class ModelRetrieveController extends BaseController {

	@RequestMapping(value = "/__model", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieveAll(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey)
			throws UserException, SystemException {
		
		this.verify(appid, apiKey);

		DataModelObject dmo = new DataModelObject(appid, null, null);
		MetadataExecutor executor = new ModelRetrieveAllExecutor();
		return executor.execute(dmo);
	}

	@RequestMapping(value = "/__model/{modelName}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieveByModelName(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable("modelName") String modelName) throws UserException,
			SystemException {
		
		this.verify(appid, apiKey);

		DataModelObject dmo = new DataModelObject(appid, modelName, null);
		MetadataExecutor executor = new ModelRetrieveByNameExecutor();
		return executor.execute(dmo);
	}

	@RequestMapping(value = "/__model/{modelName}/{version}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Object retrieveOne(
			@RequestHeader(value = "daas-app-id", required = false) String appid,
			@RequestHeader(value = "daas-api-key", required = false) String apiKey,
			@PathVariable("modelName") String modelName,
			@PathVariable("version") int version) throws UserException,
			SystemException {
		
		this.verify(appid, apiKey);

		DataModelObject dmo = new DataModelObject(appid, modelName, null, version);
		MetadataExecutor executor = new ModelRetrieveOneExecutor();
		return executor.execute(dmo);
	}
}
