package data.as.a.service.exception.adaptor;

import data.as.a.service.exception.UserException;

public class FieldTypeNotMatchDataModelException extends UserException {


	private static final long serialVersionUID = 902497834104524533L;

	public FieldTypeNotMatchDataModelException(String fieldName) {
		super("Type of field: " + fieldName + " doesn't match its data model.");
	}

}
