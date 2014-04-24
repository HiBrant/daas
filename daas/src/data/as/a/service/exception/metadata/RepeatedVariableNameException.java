package data.as.a.service.exception.metadata;

import data.as.a.service.exception.UserException;

public class RepeatedVariableNameException extends UserException {

	private static final long serialVersionUID = -8489850960163474576L;

	public RepeatedVariableNameException(String variableName) {
		super("Field with the name of \"" + variableName + "\" has already existed.");
	}

}
