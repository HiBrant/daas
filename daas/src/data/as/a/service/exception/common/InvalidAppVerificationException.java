package data.as.a.service.exception.common;

import data.as.a.service.exception.UserException;

public class InvalidAppVerificationException extends UserException {

	private static final long serialVersionUID = -6822786644507356105L;

	public InvalidAppVerificationException(String appid, String apiKey) {
		super("No app matches app id: [ " + appid + " ] with api key: [ " + apiKey + " ]");
	}

	@Override
	public int getErrorCode() {
		return CODE_INVALID_APP_VERIFICATION;
	}

}
