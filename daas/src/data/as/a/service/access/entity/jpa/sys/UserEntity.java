package data.as.a.service.access.entity.jpa.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user", catalog = "daas_sys")
public class UserEntity {

	@Id
	@GenericGenerator(name = "id_generator", strategy = "uuid")
	@GeneratedValue(generator = "id_generator")
	private String _id;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String email;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
