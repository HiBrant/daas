package data.as.a.service.metadata;

import net.sf.json.JSONObject;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.datamodel.DataModelObject;

public interface MetadataExecutor {

	JSONObject execute(DataModelObject dmo) throws UserException, SystemException;
}
