package data.as.a.service.exception.generator;

import data.as.a.service.exception.SystemException;

public class FailToLoadClassException extends SystemException {

	private static final long serialVersionUID = 6444192760332450091L;

	public FailToLoadClassException(String classname, Throwable e) {
		super("Fail to load java class: " + classname + " to JVM, because of "
				+ e.getClass().getSimpleName(), e);
	}

	@Override
	public int getErrorCode() {
		return CODE_FAIL_TO_LOAD_CLASS;
	}

}
