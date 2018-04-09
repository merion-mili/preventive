package al.mili.preventive.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_NAME"}) })
@NamedQueries({
	@NamedQuery(name="loginControll", query="SELECT u from User u  where u.userName=:username and u.password=:password"),
	@NamedQuery(name="queryAllUser", query="from User"),
	@NamedQuery(name="queryDeleteUser", query="DELETE from User u WHERE u.userName=:userName"),
	@NamedQuery(name="queryUpdateUser", query="UPDATE User u SET u.emri=:emri, u.mbiemri=:mbiemri, u.userName=:username, u.password=:password WHERE u.userId=:userId")
})

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7849728695209521907L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	@Column(name = "EMRI")
	private String emri;
	@Column(name = "MBIEMRI")
	private String mbiemri;
	@Column(name = "USER_NAME")
	private String userName;
	@Column(name = "PASSWORD")
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	private UsersRole role;

	public User() {

	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmri() {
		return emri;
	}

	public void setEmri(String emri) {
		this.emri = emri;
	}

	public String getMbiemri() {
		return mbiemri;
	}

	public void setMbiemri(String mbiemri) {
		this.mbiemri = mbiemri;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsersRole getRole() {
		return role;
	}

	public void setRole(UsersRole role) {
		this.role = role;
	}

}
