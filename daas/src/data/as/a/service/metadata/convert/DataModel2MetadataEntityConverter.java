package data.as.a.service.metadata.convert;

import java.util.ArrayList;
import java.util.List;

import data.as.a.service.access.entity.jpa.sys.FieldEntity;
import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.entity.jpa.sys.ReferenceEntity;
import data.as.a.service.convert.Converter;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.Field;
import data.as.a.service.metadata.datamodel.Reference;

public class DataModel2MetadataEntityConverter implements
		Converter<DataModelObject, MetadataEntity, Throwable> {

	@Override
	public MetadataEntity convert(DataModelObject dmo) {
		
		MetadataEntity meta = new MetadataEntity();
		meta.setAppid(dmo.getAppid());
		meta.setModelName(dmo.getModelName());
		meta.setSemantics(dmo.getSemantics().name());
		meta.setVersion(dmo.getVersion());

		List<FieldEntity> fieldList = new ArrayList<>();
		for (Field f : dmo.getFields()) {
			FieldEntity field = new FieldEntity();
			field.setName(f.getName());
			field.setType(f.getType().name());
			fieldList.add(field);
		}
		meta.setFields(fieldList);

		List<ReferenceEntity> refList = new ArrayList<>();
		for (Reference r : dmo.getReferences()) {
			ReferenceEntity ref = new ReferenceEntity();
			ref.setAttrName(r.getName());
			ref.setRefClassFullPath(r.getRefClassFullPath());
			ref.setRefType(r.getRefType().name());
			refList.add(ref);
		}
		meta.setReferences(refList);
		
		return meta;
	}

}
