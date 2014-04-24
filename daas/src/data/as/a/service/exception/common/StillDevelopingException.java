package data.as.a.service.exception.common;

import data.as.a.service.exception.SystemException;

public class StillDevelopingException extends SystemException {

	private static final long serialVersionUID = 3853743556796479110L;

	public StillDevelopingException(Class<?> clazz, String methodname,
			String notes) {
		super("Module is still under development: [class: " + clazz.getName()
				+ ", method: " + methodname + "]. " + notes, null);
	}

}
