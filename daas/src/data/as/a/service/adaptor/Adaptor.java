package data.as.a.service.adaptor;

import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.datamodel.DataModelObject;

public interface Adaptor<P, R> {

	R execute(DataModelObject dmo, P param)
			throws SystemException, UserException;

	
	static final String METHOD_SAVE = "save";
	static final String METHOD_DELETE = "delete";
}
