package al.mili.preventive.client;

import java.io.Serializable;

public class CustomerBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5893601463594287649L;
	private int customerId;
	private String emriPlote;
	private String email;
	private String telefon;
	private String nipt;
	
	public CustomerBean() {
		
	}
	
	

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public int getCustomerId() {
		return customerId;
	}

	public String getEmriPlote() {
		return emriPlote;
	}

	public void setEmriPlote(String emriPlote) {
		this.emriPlote = emriPlote;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getNipt() {
		return nipt;
	}

	public void setNipt(String nipt) {
		this.nipt = nipt;
	}
	
	
}
