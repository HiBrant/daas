package data.as.a.service.metadata.service;

import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.datamodel.DataModelObject;

public interface MetadataExecutor {

	void execute(DataModelObject dmo) throws UserException;
}
