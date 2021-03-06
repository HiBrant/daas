package data.as.a.service.access.entity.jpa.sys;

import java.util.Set;

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
@Table(name = "model", catalog = "daas_sys")
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
	
	@Column
	private boolean discard = false;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "model_id")
	private Set<FieldEntity> fields;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "model_id")
	private Set<ReferenceEntity> references;
	
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
	
	public Set<FieldEntity> getFields() {
		return fields;
	}
	
	public void setFields(Set<FieldEntity> fields) {
		this.fields = fields;
	}
	
	public Set<ReferenceEntity> getReferences() {
		return references;
	}
	
	public void setReferences(Set<ReferenceEntity> references) {
		this.references = references;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public boolean isDiscard() {
		return discard;
	}

	public void setDiscard(boolean discard) {
		this.discard = discard;
	}
	
}
