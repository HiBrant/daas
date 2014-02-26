package data.as.a.service.metadata.exception;

import data.as.a.service.exception.SystemException;

public class ModelClassMissingException extends SystemException {

	private static final long serialVersionUID = -9035117522578252273L;

	public ModelClassMissingException(String classname) {
		super("The Java class file named " + classname + " is missing.", null);
	}

}
