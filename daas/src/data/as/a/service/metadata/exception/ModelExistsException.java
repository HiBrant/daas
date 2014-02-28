package data.as.a.service.metadata.exception;

import data.as.a.service.exception.UserException;

public class ModelExistsException extends UserException {

	private static final long serialVersionUID = -4272514443823769972L;

	public ModelExistsException(String modelName, int version) {
		super("There has been a existing model named \"" + modelName
				+ "\", ver." + version);
	}

}
