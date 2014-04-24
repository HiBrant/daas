package data.as.a.service.exception.metadata;

import data.as.a.service.exception.UserException;

public class ReferenceModelNotExistsException extends UserException {

	private static final long serialVersionUID = 1948803395227560793L;

	public ReferenceModelNotExistsException(String modelName) {
		super("There is no model named \"" + modelName + "\" which can be refered to.");

	}

	@Override
	public int getErrorCode() {
		return CODE_REFERENCE_MODEL_NOT_EXISTS;
	}

}
