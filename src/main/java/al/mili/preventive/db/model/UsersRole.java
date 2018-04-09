package al.mili.preventive.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USERS_ROLE", uniqueConstraints = { @UniqueConstraint(columnNames = { "ROLE" }) })
public class UsersRole implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1693051175982479065L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID")
	private int roleId;
	@Column(name = "ROLE")
	private String role;
	


	public UsersRole() {

	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


}
