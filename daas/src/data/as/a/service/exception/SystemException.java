package data.as.a.service.exception;

public class SystemException extends Exception {
	
	private static final long serialVersionUID = -4533382718576520495L;
	
	public SystemException(String msg, Throwable e) {
		super(msg, e);
	}

}
