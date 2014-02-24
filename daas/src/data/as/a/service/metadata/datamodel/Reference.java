package data.as.a.service.metadata.datamodel;

public class Reference {
	
	private String name;
	private String refClassFullPath;
	private ReferenceType refType;
	
	public Reference(String name, String refClassFullPath, ReferenceType refType) {
		this.name = name;
		this.refClassFullPath = refClassFullPath;
		this.refType = refType;
	}

	public String getName() {
		return name;
	}

	public ReferenceType getRefType() {
		return refType;
	}

	public String getRefClassFullPath() {
		return refClassFullPath;
	}
	
}
