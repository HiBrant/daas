package data.as.a.service.metadata.datamodel;

public class DataModelObject {

	private String appid;
	private String modelName;
	private SemanticsType semantics;
	private int version;
	
	private Fields fields;
	private References references;
	
	public DataModelObject(String appid, String modelName, SemanticsType semantics, int version) {
		this.appid = appid;
		this.modelName = modelName;
		this.semantics = semantics;
		this.version = version;
		this.fields = new Fields();
		this.references = new References();
	}
	
	public DataModelObject(String appid, String modelName, SemanticsType semantics) {
		this(appid, modelName, semantics, 1);
	}

	public String getAppid() {
		return appid;
	}

	public String getModelName() {
		return modelName;
	}

	public SemanticsType getSemantics() {
		return semantics;
	}

	public int getVersion() {
		return version;
	}

	public Fields getFields() {
		return fields;
	}

	public References getReferences() {
		return references;
	}

	public void setSemantics(SemanticsType semantics) {
		this.semantics = semantics;
	}
	
}
