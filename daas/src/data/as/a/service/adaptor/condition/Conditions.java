package data.as.a.service.adaptor.condition;

import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;

public class Conditions {

	private ConditionNode root;
	
	public Conditions(ConditionNode root) {
		this.root = root;
	}
	
	public boolean judge(Object entity) throws SystemException, UserException {
		return root.judge(entity);
	}
	
}
