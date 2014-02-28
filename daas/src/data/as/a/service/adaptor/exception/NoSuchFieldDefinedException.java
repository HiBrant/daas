package data.as.a.service.adaptor.exception;

import data.as.a.service.exception.UserException;

public class NoSuchFieldDefinedException extends UserException {

	private static final long serialVersionUID = 1572779193107506354L;

	public NoSuchFieldDefinedException(String fieldName) {
		super("No field named " + fieldName + " defined in the data model");
	}

}
