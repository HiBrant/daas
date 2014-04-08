package data.as.a.service.metadata;

import net.sf.json.JSONObject;
import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.adaptor.exception.ModelNotExistsException;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;
import data.as.a.service.metadata.convert.MetadataEntity2JSONConverter;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.service.MetadataRetrieveService;

public class ModelRetrieveOneExecutor implements MetadataExecutor {

	@Override
	public JSONObject execute(DataModelObject dmo) throws UserException,
			SystemException {

		MetadataEntity meta = MetadataRetrieveService.retrieveOne(
				dmo.getAppid(), dmo.getModelName(), dmo.getVersion());
		if (meta == null) {
			throw new ModelNotExistsException(dmo.getModelName(), dmo.getVersion());
		}
		return new MetadataEntity2JSONConverter().convert(meta);
	}

}
