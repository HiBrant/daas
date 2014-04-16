package data.as.a.service.metadata.exception;

import data.as.a.service.exception.UserException;

public class NoModelReferToThisIDException extends UserException {

	private static final long serialVersionUID = 5721611928813858369L;

	public NoModelReferToThisIDException(String modelId) {
		super("There is no available model with the _id of " + modelId);
	}

}
