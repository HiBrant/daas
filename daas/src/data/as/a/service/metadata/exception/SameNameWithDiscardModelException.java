package data.as.a.service.metadata.exception;

import data.as.a.service.exception.UserException;

public class SameNameWithDiscardModelException extends UserException {

	private static final long serialVersionUID = -1134551422086436113L;

	public SameNameWithDiscardModelException(String modelName, int version) {
		super("There has already been a discarded model with the name: "
				+ modelName + ", ver." + version);
	}

}
