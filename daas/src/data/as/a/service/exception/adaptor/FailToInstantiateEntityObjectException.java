package data.as.a.service.exception.adaptor;

import data.as.a.service.exception.SystemException;

public class FailToInstantiateEntityObjectException extends SystemException {

	private static final long serialVersionUID = 8873368218910066793L;

	public FailToInstantiateEntityObjectException(String classname, Throwable e) {
		super("Fail to instantiate entity named " + classname
				+ "because of exception: " + e.getClass().getSimpleName(), e);
	}

	@Override
	public int getErrorCode() {
		return CODE_FAIL_TO_INSTANTIATE_ENTITY_OBJECT;
	}

}
