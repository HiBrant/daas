package data.as.a.service.exception.common;

import data.as.a.service.exception.SystemException;

public class ModelClassMissingException extends SystemException {

	private static final long serialVersionUID = -9035117522578252273L;

	public ModelClassMissingException(String classname, Throwable e) {
		super("The Java class file named " + classname + " is missing.", e);
	}

}
