package data.as.a.service.access.entity.jpa.sys;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import data.as.a.service.util.DateFormatter;

@Entity
@Table(name = "model")
public class MetadataEntity {
	
	@Id
	@GenericGenerator(name = "id_generator", strategy = "uuid")
	@GeneratedValue(generator = "id_generator")
	private String _id;
	
	@Column
	private String appid;
	
	@Column
	private String modelName;
	
	@Column
	private String semantics;
	
	@Column
	private int version;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id")
	private List<FieldEntity> fields;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id")
	private List<ReferenceEntity> references;
	
	@Column
	private String lastModifyTime = DateFormatter.now();
	
	public String get_id() {
		return _id;
	}
	
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public String getAppid() {
		return appid;
	}
	
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public String getSemantics() {
		return semantics;
	}
	
	public void setSemantics(String semantics) {
		this.semantics = semantics;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public List<FieldEntity> getFields() {
		return fields;
	}
	
	public void setFields(List<FieldEntity> fields) {
		this.fields = fields;
	}
	
	public List<ReferenceEntity> getReferences() {
		return references;
	}
	
	public void setReferences(List<ReferenceEntity> references) {
		this.references = references;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
}
