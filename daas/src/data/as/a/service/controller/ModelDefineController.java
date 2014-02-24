package data.as.a.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import data.as.a.service.metadata.datamodel.DataModelObject;
import data.as.a.service.metadata.datamodel.FieldType;
import data.as.a.service.metadata.datamodel.SemanticsType;
import data.as.a.service.metadata.exception.ModelExistsException;
import data.as.a.service.metadata.exception.RepeatedVariableNameException;
import data.as.a.service.metadata.service.ModelDefinitionExecutor;
import data.as.a.service.util.ClassPathUtil;

@Controller
public class ModelDefineController {

	@RequestMapping(value = "/__model", method = RequestMethod.GET)
	@ResponseBody
	public String test() throws ModelExistsException, RepeatedVariableNameException {
		DataModelObject dmo = new DataModelObject("123123123", "department", SemanticsType.BASE);
		dmo.getFields().add("name", FieldType.STRING);
		dmo.getFields().add("time", FieldType.STRING);
		
		ModelDefinitionExecutor mde = new ModelDefinitionExecutor();
		mde.execute(dmo);
		
		System.out.println(ClassPathUtil.getEntityJavaClasspath(dmo));
		System.out.println(ClassPathUtil.getEntityFilePath(dmo));
		
		return "Done!";
	}
}
