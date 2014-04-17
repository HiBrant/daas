package data.as.a.service.adaptor.condition;

import data.as.a.service.adaptor.exception.NullValueConditionException;

public class ConditionNode {

	private LexicalType type = null;
	private String str = null;
	
	private ConditionNode left = null;
	private ConditionNode right = null;
	
	public ConditionNode(LexicalType type) {
		this.type = type;
	}
	
	public ConditionNode(String str) {
		this.str = str;
	}
	
	public String get() throws NullValueConditionException {
		if (type == null && str == null) {
			throw new NullValueConditionException();
		}
		
		if (str == null) {
			return type.tag();
		}
		
		return str;
	}

	public ConditionNode getLeft() {
		return left;
	}

	public void setLeft(ConditionNode left) {
		this.left = left;
	}

	public ConditionNode getRight() {
		return right;
	}

	public void setRight(ConditionNode right) {
		this.right = right;
	}
}
