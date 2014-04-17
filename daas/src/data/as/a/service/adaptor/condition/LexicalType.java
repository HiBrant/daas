package data.as.a.service.adaptor.condition;

public enum LexicalType {

	eq("", 2),
	ne("Not", 2),
	gt("GreaterThan", 2),
	lt("LessThan", 2),
	ge("GreaterThanOr", 2),
	le("LessThanOr", 2),
	and("And", 1),
	or("Or", 1),
	operand("", 0);
	
	private String tag;
	private int priority;
	
	private LexicalType(String tag, int priority) {
		this.tag = tag;
		this.priority = priority;
	}
	
	public String tag() {
		return tag;
	}
	
	public int priority() {
		return priority;
	}
	
	public static LexicalType get(String str) {
		
		if (str.equals("[eq]")) {
			return eq;
		} else if (str.equals("[ne]")) {
			return ne;
		} else if (str.equals("[gt]")) {
			return gt;
		} else if (str.equals("[lt]")) {
			return lt;
		} else if (str.equals("[ge]")) {
			return ge;
		} else if (str.equals("[le]")) {
			return le;
		} else if (str.equals("*")) {
			return and;
		} else if (str.equals("|")) {
			return or;
		} else {
			return operand;
		}
	}
}
