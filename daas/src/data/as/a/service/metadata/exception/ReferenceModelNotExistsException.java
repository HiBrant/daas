package data.as.a.service.metadata.exception;

import data.as.a.service.exception.UserException;

public class ReferenceModelNotExistsException extends UserException {

	private static final long serialVersionUID = 1948803395227560793L;

	public ReferenceModelNotExistsException(String modelName) {
		super("There is no model named \"" + modelName + "\", ver."
				+ " which can be refered to.", null);

	}

}
