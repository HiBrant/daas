package data.as.a.service.adaptor.condition;

public enum LexicalType {

	eq(2),
	ne(2),
	gt(2),
	lt(2),
	ge(2),
	le(2),
	and(1),
	or(1),
	lb(3),
	rb(3),
	sw(2),
	ew(2),
	ct(2),
	operand(0);
	
	private int priority;
	
	private LexicalType(int priority) {
		this.priority = priority;
	}
	
	public int priority() {
		return priority;
	}
	
	public static LexicalType fromString(String str) {
		
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
		} else if (str.equals("(")) {
			return lb;
		} else if (str.equals(")")) {
			return rb;
		} else if (str.equals("[sw]")) {
			return sw;
		} else if (str.equals("[ew]")) {
			return ew;
		} else if (str.equals("[co]")) {
			return ct;
		} else {
			return operand;
		}
	}
}
