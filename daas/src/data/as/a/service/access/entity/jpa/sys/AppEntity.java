package data.as.a.service.access.entity.jpa.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "app", catalog = "daas_sys")
public class AppEntity {

	@Id
	@GenericGenerator(name = "id_generator", strategy = "uuid")
	@GeneratedValue(generator = "id_generator")
	private String _id;
	
	@Column
	private String apiKey;
	
	@Column
	private String userId;
	
	@Column
	private String appName;
	
	@Column
	private long timestamp;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
