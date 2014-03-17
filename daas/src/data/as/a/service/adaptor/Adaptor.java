package data.as.a.service.adaptor;

import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import net.sf.json.JSONObject;

public interface Adaptor {

	JSONObject execute(DataModelObject dmo, JSONObject entityJson)
			throws SystemException, UserException;

	
	static final String METHOD_SAVE = "save";
}
