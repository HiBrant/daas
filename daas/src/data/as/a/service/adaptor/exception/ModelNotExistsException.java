package data.as.a.service.adaptor.exception;

import data.as.a.service.exception.UserException;

public class ModelNotExistsException extends UserException {

	private static final long serialVersionUID = 1173945000161999132L;

	public ModelNotExistsException(String modelName, int version) {
		super("There has been no existing model named \"" + modelName
				+ "\", ver." + version);
	}

}
