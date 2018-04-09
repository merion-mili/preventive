package al.mili.preventive.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "CUSTOMER", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"FULL_NAME" }) })
@NamedQueries({
		@NamedQuery(name = "queryCustomer", query = "from Customer"),
		@NamedQuery(name = "deleteCustomer", query = "DELETE from Customer c WHERE c.customerId=:customerId"),
		@NamedQuery(name = "updateCustomer", query = "UPDATE Customer c SET c.emriPlote=:emriPlote, c.email=:email, c.telefon=:telefon, c.nipt=:nipt WHERE c.customerId=:customerId") })
public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4987431622996896623L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CUSTOMER_ID")
	private int customerId;
	@Column(name = "FULL_NAME")
	private String emriPlote;
	@Column(name = "EMAIL")
	@Email(message="Must be a valid email")
	private String email;
	@Column(name = "TELEFON")
	private String telefon;
	@Column(name = "NIPT")
	private String nipt;

	public Customer() {

	}

	public String getEmriPlote() {
		return emriPlote;
	}

	public void setEmriPlote(String emriPlote) {
		this.emriPlote = emriPlote;
	}

	public int getCustomerId() {
		return customerId;
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
