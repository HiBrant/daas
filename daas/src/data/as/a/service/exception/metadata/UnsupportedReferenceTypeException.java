package data.as.a.service.exception.metadata;

import data.as.a.service.exception.UserException;

public class UnsupportedReferenceTypeException extends UserException {

	private static final long serialVersionUID = 6915661744179809506L;

	public UnsupportedReferenceTypeException(String attrName, String referType) {
		super("Type of reference '" + attrName + "' is not supported: "
				+ referType);
	}

	@Override
	public int getErrorCode() {
		return CODE_UNSUPPORTED_REFERENCE_TYPE;
	}

}
