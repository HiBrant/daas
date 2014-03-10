package data.as.a.service.generator.exception;

import data.as.a.service.exception.SystemException;

public class FailToSaveClassFileException extends SystemException {

	private static final long serialVersionUID = 5632708331341883101L;

	public FailToSaveClassFileException(String classname, Throwable e) {
		super("Fail to save the file of Java class: " + classname + ", because of "
				+ e.getClass().getSimpleName(), e);
	}

}
