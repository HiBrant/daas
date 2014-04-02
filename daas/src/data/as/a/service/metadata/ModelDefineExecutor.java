package data.as.a.service.metadata;

import net.sf.json.JSONObject;
import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.exception.SystemException;
import data.as.a.service.generator.entity.EntityClassGenerator;
import data.as.a.service.metadata.convert.MetadataEntity2JSONConverter;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.exception.ModelExistsException;
import data.as.a.service.metadata.service.MetadataCreateService;

public class ModelDefineExecutor implements MetadataExecutor {

	@Override
	public JSONObject execute(DataModelObject dmo) throws ModelExistsException,
			SystemException {

		MetadataEntity meta = MetadataCreateService.saveModelIntoDatabase(dmo);
		EntityClassGenerator.generate(dmo);
		return new MetadataEntity2JSONConverter().convert(meta);
	}

}
