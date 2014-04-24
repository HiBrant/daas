package data.as.a.service.exception.adaptor;

import data.as.a.service.exception.UserException;

public class IllegalQueryExpressionException extends UserException {

	private static final long serialVersionUID = 1034496994930430273L;

	public IllegalQueryExpressionException(String expression) {
		super("The query expression: " + expression + ", contains syntax errors.");
	}

	@Override
	public int getErrorCode() {
		return CODE_ILLEGAL_QUERY_EXPRESSION;
	}

}
