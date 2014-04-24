package data.as.a.service.exception.common;

import data.as.a.service.exception.UserException;

public class ModelNotAvailableException extends UserException {

	private static final long serialVersionUID = 1173945000161999132L;

	public ModelNotAvailableException(String modelName, int version) {
		super("There has been no available model named \"" + modelName
				+ "\", ver." + version);
	}

}
