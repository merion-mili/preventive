package al.mili.preventive.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "PREVENTIVE", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"CUSTOMER_ID", "OBJEKTI","PROTOKOLL_NUMBER","LOCATION","VARIANT" }) })
@NamedQueries({
		@NamedQuery(name = "queryByUserName", query = "from Preventive p where p.user.userName=:userName and p.hide=:hide"),
		@NamedQuery(name = "queryDeletePreventive", query = "UPDATE from Preventive p SET p.hide=:hide WHERE p.preventiveId=:preventiveId"),
		@NamedQuery(name = "queryUpdatePreventive", query = "UPDATE Preventive p SET p.customer=:emri, p.email=:email, p.protokollNumber=:protokollNumber, p.varianti=:varianti, p.objekti=:objekti, p.location=:location, p.status=:status, p.statusiPunimeve=:punimet, p.protokollDate=:date, p.vlera=:vlera, p.paradhenie=:paradhenie, p.vleraMbetur=:mbetje, p.kontrata=:kontrata, p.hide=:hide WHERE p.preventiveId=:preventiveId"),
		@NamedQuery(name = "updateVlera", query = "UPDATE Preventive p SET p.vlera=:vlera WHERE p.preventiveId=:preventiveId")})
public class Preventive implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1924520658795301844L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PREVENTIVE_ID")
	private int preventiveId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "OBJEKTI")
	private String objekti;
	
	@Column(name= "PROTOKOLL_NUMBER")
	private String protokollNumber;
	
	@Column(name= "VARIANT")
	private String varianti;

	@Column(name = "LOCATION")
	private String location;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "VLERA")
	private BigDecimal vlera;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "STATUSI_PUNIMEVE")
	private String statusiPunimeve;

	@Column(name = "PARADHENIE")
	private BigDecimal paradhenie;

	@Column(name = "VLERA_MBETUR")
	private BigDecimal vleraMbetur;

	@Column(name = "KONTRATA")
	@Type(type = "boolean")
	private boolean kontrata;
	
	@Column(name = "HIDE")
	@Type(type = "boolean")
	private boolean hide;

	@Transient
	private boolean checked;
	
	@Transient
	private boolean changeColor=false;
	
	@Column(name = "PROTOKOLL_DATE")
	private Date protokollDate;

	public Date getProtokollDate() {
		return protokollDate;
	}

	public void setProtokollDate(Date protokollDate) {
		this.protokollDate = protokollDate;
	}

	public Preventive() {

	}

	public int getPreventiveId() {
		return preventiveId;
	}

	public void setPreventiveId(int preventiveId) {
		this.preventiveId = preventiveId;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObjekti() {
		return objekti;
	}

	public void setObjekti(String objekti) {
		this.objekti = objekti;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public BigDecimal getVlera() {
		return vlera;
	}

	public void setVlera(BigDecimal vlera) {
		this.vlera = vlera;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusiPunimeve() {
		return statusiPunimeve;
	}

	public void setStatusiPunimeve(String statusiPunimeve) {
		this.statusiPunimeve = statusiPunimeve;
	}

	
	public BigDecimal getParadhenie() {
		return paradhenie;
	}

	public void setParadhenie(BigDecimal paradhenie) {
		this.paradhenie = paradhenie;
	}

	public boolean isKontrata() {
		return kontrata;
	}

	public void setKontrata(boolean kontrata) {
		this.kontrata = kontrata;
	}

	
	public BigDecimal getVleraMbetur() {

		return vleraMbetur;
	}

	public void setVleraMbetur(BigDecimal vleraMbetur) {
		this.vleraMbetur = vleraMbetur;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getProtokollNumber() {
		return protokollNumber;
	}

	public void setProtokollNumber(String protokollNumber) {
		this.protokollNumber = protokollNumber;
	}

	public String getVarianti() {
		return varianti;
	}

	public void setVarianti(String varianti) {
		this.varianti = varianti;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public boolean isChangeColor() {
		return changeColor;
	}

	public void setChangeColor(boolean changeColor) {
		this.changeColor = changeColor;
	}

	
}
