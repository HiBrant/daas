package data.as.a.service.metadata.exception;

import data.as.a.service.exception.UserException;

public class UnsupportedFieldTypeException extends UserException {

	private static final long serialVersionUID = 5760459580263408138L;

	public UnsupportedFieldTypeException(String fieldName, String fieldType) {
		super("Type of field '" + fieldName + "' is not supported: "
				+ fieldType);
	}

}
