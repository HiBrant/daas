package data.as.a.service.exception.adaptor;

import data.as.a.service.adaptor.condition.LexicalType;
import data.as.a.service.exception.UserException;

public class InvalidQueryOperatorException extends UserException {

	private static final long serialVersionUID = 6002449802695748952L;

	public InvalidQueryOperatorException(String fieldName, Class<?> fieldType,
			LexicalType operator) {
		super(
				"One query operator is invalid, because of the type of the field: ["
						+ fieldName + "] is " + fieldType.getName()
						+ ", while the operator is [" + operator.name() + "].");
	}

	@Override
	public int getErrorCode() {
		return CODE_INVALID_QUERY_OPERATOR;
	}

}
