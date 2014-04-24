package data.as.a.service.convert.metadata;

import java.io.File;

import data.as.a.service.convert.Converter;
import data.as.a.service.exception.UserException;
import data.as.a.service.exception.metadata.ReferenceModelNotExistsException;
import data.as.a.service.exception.metadata.UnsupportedFieldTypeException;
import data.as.a.service.exception.metadata.UnsupportedReferenceTypeException;
import data.as.a.service.exception.metadata.UnsupportedSemanticsTypeException;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.FieldType;
import data.as.a.service.metadata.datamodel.Reference;
import data.as.a.service.metadata.datamodel.ReferenceType;
import data.as.a.service.metadata.datamodel.SemanticsType;
import data.as.a.service.util.ClassPathUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSON2DataModelConverter implements
		Converter<JSONObject, DataModelObject, UserException> {

	private String appid;

	public JSON2DataModelConverter(String appid) {
		this.appid = appid;
	}

	@Override
	public DataModelObject convert(JSONObject source) throws UserException {
		DataModelObject dmo = null;

		String modelName = source.getString("modelName");
		String semantics = source.getString("semantics");
		SemanticsType semanticsType = null;
		if (semantics.equals("ACID")) {
			semanticsType = SemanticsType.ACID;
		} else if (semantics.equals("BASE")) {
			semanticsType = SemanticsType.BASE;
		} else {
			throw new UnsupportedSemanticsTypeException();
		}

		if (source.containsKey("version")) {
			int version = source.getInt("version");
			dmo = new DataModelObject(appid, modelName, semanticsType, version);
		} else {
			dmo = new DataModelObject(appid, modelName, semanticsType);
		}

		if (source.containsKey("__fields")) {
			JSONArray fieldJsonArray = source.getJSONArray("__fields");
			for (int i = 0; i < fieldJsonArray.size(); i++) {
				JSONObject fieldJsonObj = fieldJsonArray.getJSONObject(i);
				String fieldName = fieldJsonObj.getString("name");
				String strFieldType = fieldJsonObj.getString("type");

				FieldType fieldType = null;
				if (strFieldType.equals("int")) {
					fieldType = FieldType.INTEGER;
				} else if (strFieldType.equals("double")) {
					fieldType = FieldType.DOUBLE;
				} else if (strFieldType.equals("string")) {
					fieldType = FieldType.STRING;
				} else if (strFieldType.equals("bool")) {
					fieldType = FieldType.BOOLEAN;
				} else {
					throw new UnsupportedFieldTypeException(fieldName,
							strFieldType);
				}
				dmo.getFields().add(fieldName, fieldType);
			}
		}

		if (source.containsKey("__referenceModels")) {
			JSONArray referJsonArray = source.getJSONArray("__referenceModels");
			for (int i = 0; i < referJsonArray.size(); i++) {
				JSONObject referJsonObj = referJsonArray.getJSONObject(i);
				String attrName = referJsonObj.getString("attrName");
				String referModelName = referJsonObj.getString("modelName");
				String referType = referJsonObj.getString("referType");
				DataModelObject refModel = null;
				if (referJsonObj.containsKey("modelVer")) {
					int modelVersion = referJsonObj.getInt("modelVer");
					refModel = new DataModelObject(appid, referModelName,
							semanticsType, modelVersion);
				} else {
					refModel = new DataModelObject(appid, referModelName,
							semanticsType);
				}
				String refClassFullPath = ClassPathUtil
						.getEntityFilePath(refModel);
				if (!new File(refClassFullPath).isFile()) {
					throw new ReferenceModelNotExistsException(referModelName);
				}

				ReferenceType refType = null;
				if (referType.equals("one-to-one")) {
					refType = ReferenceType.OneToOne;
				} else if (referType.equals("one-to-many")) {
					refType = ReferenceType.OneToMany;
				} else {
					throw new UnsupportedReferenceTypeException(attrName,
							referType);
				}
				Reference ref = new Reference(attrName, refClassFullPath,
						refType);
				dmo.getReferences().add(ref);
			}
		}

		return dmo;
	}

}
