package data.as.a.service.metadata.datamodel;

public class DataModelObject {

	private String _id;
	
	private String appid;
	private String modelName;
	private SemanticsType semantics;
	private int version;
	
	private Fields fields = null;
	private References references = null;
	
	public DataModelObject(String appid, String modelName, SemanticsType semantics, int version) {
		this.appid = appid;
		this.modelName = modelName;
		this.semantics = semantics;
		this.version = version;
	}
	
	public DataModelObject(String appid, String modelName, SemanticsType semantics) {
		this(appid, modelName, semantics, 1);
	}
	
	public DataModelObject(String _id) {
		this._id = _id;
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
		if (fields == null) {
			fields = new Fields();
		}
		return fields;
	}

	public References getReferences() {
		if (references == null) {
			references = new References();
		}
		return references;
	}

	public void setSemantics(SemanticsType semantics) {
		this.semantics = semantics;
	}

	public String get_id() {
		return _id;
	}
	
}
