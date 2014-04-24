package data.as.a.service.exception.adaptor;

import data.as.a.service.exception.SystemException;

public class FailToAccessEntityFieldException extends SystemException {

	private static final long serialVersionUID = 7765567044018521688L;

	public FailToAccessEntityFieldException(String entityName,
			String fieldName, Throwable e) {
		super("Fail to access the field: " + fieldName + " of entity: "
				+ entityName + ", because of exception: "
				+ e.getClass().getSimpleName(), e);
	}

	@Override
	public int getErrorCode() {
		return CODE_FAIL_TO_ACCESS_ENTITY_FIELD;
	}

}
