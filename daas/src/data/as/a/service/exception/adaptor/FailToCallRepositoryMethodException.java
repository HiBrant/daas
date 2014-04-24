package data.as.a.service.exception.adaptor;

import data.as.a.service.exception.SystemException;

public class FailToCallRepositoryMethodException extends SystemException {

	private static final long serialVersionUID = -4087633056917672762L;

	public FailToCallRepositoryMethodException(Class<?> repoClass,
			String methodName, Throwable e) {
		super("Fail to call the [method: " + methodName + "] of repository: "
				+ repoClass.getName() + ", because of exception: "
				+ e.getClass().getSimpleName(), e);
	}

	@Override
	public int getErrorCode() {
		return CODE_FAIL_TO_CALL_REPOSITORY_METHOD;
	}

}
