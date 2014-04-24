package data.as.a.service.exception.metadata;

import data.as.a.service.exception.UserException;

public class UnsupportedSemanticsTypeException extends UserException {

	private static final long serialVersionUID = 8814181294687557367L;

	public UnsupportedSemanticsTypeException() {
		super("Only 2 values are allowed for Attribute 'semantics': ACID or BASE.");
	}

	@Override
	public int getErrorCode() {
		return CODE_UNSUPPORTED_SEMANTICS_TYPE;
	}

}
