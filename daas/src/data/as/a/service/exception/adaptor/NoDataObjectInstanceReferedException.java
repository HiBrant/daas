package data.as.a.service.exception.adaptor;

import data.as.a.service.exception.UserException;

public class NoDataObjectInstanceReferedException extends UserException {

	private static final long serialVersionUID = 1270811451820390990L;

	public NoDataObjectInstanceReferedException(String modelName, int version,
			String _id) {
		super("There is no instance with _id: " + _id
				+ " existing in the data object: " + modelName + ", ver."
				+ version);
	}

}
