package al.mili.preventive.client;

import java.io.Serializable;

public class UserBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2016853191312919721L;
	private int userId;
	private String emri;
	private String mbiemri;
	private String userName;
	private String password;

	public UserBean() {

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

}
