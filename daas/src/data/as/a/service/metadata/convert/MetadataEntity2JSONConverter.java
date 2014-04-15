package data.as.a.service.metadata.convert;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import data.as.a.service.access.entity.jpa.sys.FieldEntity;
import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.entity.jpa.sys.ReferenceEntity;
import data.as.a.service.convert.Converter;
import data.as.a.service.exception.SystemException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.exception.ModelClassMissingException;

public class MetadataEntity2JSONConverter implements
		Converter<MetadataEntity, JSONObject, SystemException> {

	@Override
	public JSONObject convert(MetadataEntity meta)
			throws ModelClassMissingException {
		JSONObject json = new JSONObject();

		json.put("_id", meta.get_id());
		json.put("lastModifyTime", meta.getLastModifyTime());
		json.put("modelName", meta.getModelName());
		json.put("semantics", meta.getSemantics());
		json.put("version", meta.getVersion());

		if (meta.getFields() != null && meta.getFields().size() > 0) {
			JSONArray farray = new JSONArray();
			for (FieldEntity field : meta.getFields()) {
				JSONObject fjson = new JSONObject();
				fjson.put("name", field.getName());
				fjson.put("type", field.getType());
				farray.add(fjson);
			}
			json.put("__fields", farray);
		}

		if (meta.getReferences() != null && meta.getReferences().size() > 0) {
			Classpath2ReferModelObjectConverter s2o = 
					new Classpath2ReferModelObjectConverter();
			DataModelObject referDMO = null;
			JSONArray rarray = new JSONArray();
			for (ReferenceEntity refer : meta.getReferences()) {
				JSONObject rjson = new JSONObject();
				rjson.put("attrName", refer.getAttrName());

				referDMO = s2o.convert(refer.getRefClassFullPath());
				rjson.put("modelName", referDMO.getModelName());
				rjson.put("modelVer", referDMO.getVersion());
				rjson.put("referType", refer.getRefType());

				rarray.add(rjson);
			}
			json.put("__referenceModels", rarray);
		}
		
		json.put("discard", meta.isDiscard());

		return json;
	}

}
