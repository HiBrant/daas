package data.as.a.service.metadata.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.access.entity.jpa.sys.FieldEntity;
import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.entity.jpa.sys.ReferenceEntity;
import data.as.a.service.access.repo.jpa.sys.MetadataRepository;
import data.as.a.service.metadata.config.MetadataAccessConfig;
import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.Field;
import data.as.a.service.metadata.datamodel.Reference;
import data.as.a.service.metadata.exception.ModelExistsException;

public class ModelDefinitionExecutor implements MetadataExecutor {

	@Override
	public void execute(DataModelObject dmo) throws ModelExistsException {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		MetadataRepository repo = ctx.getBean(MetadataRepository.class);
		MetadataEntity meta = repo.findByAppidAndModelNameAndVersion(
				dmo.getAppid(), dmo.getModelName(), dmo.getVersion());
		if (meta != null) {
			throw new ModelExistsException(dmo.getModelName(), dmo.getVersion());
		}
		
		meta = new MetadataEntity();
		meta.setAppid(dmo.getAppid());
		meta.setModelName(dmo.getModelName());
		meta.setSemantics(dmo.getSemantics().name());
		meta.setVersion(dmo.getVersion());
		
		List<FieldEntity> fieldList = new ArrayList<>();
		for (Field f: dmo.getFields()) {
			FieldEntity field = new FieldEntity();
			field.setName(f.getName());
			field.setType(f.getType().name());
			fieldList.add(field);
		}
		meta.setFields(fieldList);
		
		List<ReferenceEntity> refList = new ArrayList<>();
		for (Reference r: dmo.getReferences()) {
			ReferenceEntity ref = new ReferenceEntity();
			ref.setAttrName(r.getName());
			ref.setRefClassFullPath(r.getRefClassFullPath());
			ref.setRefType(r.getRefType().name());
			refList.add(ref);
		}
		meta.setReferences(refList);
		
		repo.save(meta);
		((ConfigurableApplicationContext) ctx).close();
	}

}
