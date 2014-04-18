package data.as.a.service.adaptor.condition;

public class Lexis {
	
	private String token;
	private LexicalType type;
	
	public Lexis(String token, LexicalType type) {
		this.token = token;
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LexicalType getType() {
		return type;
	}

	public void setType(LexicalType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "< " + token + ", " + type.name() + " >";
	}
	
}
