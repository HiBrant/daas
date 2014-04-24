package data.as.a.service.convert.generator;

import data.as.a.service.convert.Converter;
import data.as.a.service.metadata.datamodel.FieldType;

public class FieldType2JavaTypeConverter implements
		Converter<FieldType, String, Throwable> {
	
	public static final String TYPE_INTEGER = "Ljava/lang/Integer;";
	public static final String TYPE_DOUBLE = "Ljava/lang/Double;";
	public static final String TYPE_BOOLEAN = "Ljava/lang/Boolean;";
	public static final String TYPE_STRING = "Ljava/lang/String;";

	@Override
	public String convert(FieldType fType) {
		
		String sType = TYPE_STRING;
		
		if (fType == FieldType.INTEGER) {
			sType = TYPE_INTEGER;
		} else if (fType == FieldType.DOUBLE) {
			sType = TYPE_DOUBLE;
		} else if (fType == FieldType.BOOLEAN) {
			sType = TYPE_BOOLEAN;
		}
		
		return sType;
	}

}
