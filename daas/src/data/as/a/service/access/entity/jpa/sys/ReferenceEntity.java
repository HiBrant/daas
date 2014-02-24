package data.as.a.service.access.entity.jpa.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "refer")
public class ReferenceEntity {
	
	@Id
	@GenericGenerator(name = "id_generator", strategy = "uuid")
	@GeneratedValue(generator = "id_generator")
	private String _id;
	
	@Column(name = "name")
	private String attrName;
	
	@Column(name = "class")
	private String refClassFullPath;
	
	@Column(name = "type")
	private String refType;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getRefClassFullPath() {
		return refClassFullPath;
	}

	public void setRefClassFullPath(String refClassFullPath) {
		this.refClassFullPath = refClassFullPath;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}
	
}
