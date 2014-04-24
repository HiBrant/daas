package data.as.a.service.exception.adaptor;

import data.as.a.service.exception.SystemException;

public class FailToAssignEntityFieldException extends SystemException {

	private static final long serialVersionUID = -1850402209014130543L;

	public FailToAssignEntityFieldException(String classname, String fieldName,
			Throwable e) {
		super("Unable to assign field: " + fieldName + " of data object: "
				+ classname + ", because of exception: "
				+ e.getClass().getSimpleName(), e);
	}

}
