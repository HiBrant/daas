package data.as.a.service.adaptor.exception;

import data.as.a.service.exception.SystemException;

public class NullValueConditionException extends SystemException {

	private static final long serialVersionUID = 581504167952095011L;

	public NullValueConditionException() {
		super("Both lexical type and operand string of this condition is null.",
				new NullPointerException());
	}

}
