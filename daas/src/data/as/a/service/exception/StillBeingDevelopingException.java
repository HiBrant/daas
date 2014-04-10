package data.as.a.service.exception;

public class StillBeingDevelopingException extends SystemException {

	private static final long serialVersionUID = 3853743556796479110L;

	public StillBeingDevelopingException(String classname, String methodname,
			String notes) {
		super("Module is still under development: [class: " + classname
				+ ", method: " + methodname + "]. " + notes, null);
	}

}
