package data.as.a.service.exception.common;

import data.as.a.service.exception.SystemException;

public class UnhandledException extends SystemException {

	private static final long serialVersionUID = -3658114457503899152L;

	public UnhandledException(Throwable e) {
		super("Sorry, this exception has not been handled yet! Please contact us when you meet it.", e);
	}

	@Override
	public int getErrorCode() {
		return CODE_UNHANDLED;
	}

}
