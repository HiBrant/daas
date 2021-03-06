package data.as.a.service.exception.adaptor;

import data.as.a.service.exception.UserException;

public class NoSuchFieldDefinedException extends UserException {

	private static final long serialVersionUID = 1572779193107506354L;

	public NoSuchFieldDefinedException(String fieldName) {
		super("No field named " + fieldName + " defined in the data model");
	}

	@Override
	public int getErrorCode() {
		return CODE_NO_SUCH_FIELD_DEFINED;
	}

}
