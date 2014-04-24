package data.as.a.service.exception;

public abstract class UserException extends DaaSException {

	private static final long serialVersionUID = 4671874969512036175L;
	
	public UserException(String msg) {
		super(msg);
	}

}
