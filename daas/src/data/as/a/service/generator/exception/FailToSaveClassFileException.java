package data.as.a.service.generator.exception;

import data.as.a.service.exception.SystemException;

public class FailToSaveClassFileException extends SystemException {

	private static final long serialVersionUID = 5632708331341883101L;

	public FailToSaveClassFileException(Throwable e) {
		super("Generation of Java class file is failed because of "
				+ e.getClass().getSimpleName(), e);
	}

}
