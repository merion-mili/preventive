package al.mili.preventive.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import al.mili.preventive.db.model.Customer;

public class PreventiveBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2002188804907011382L;
	private int preventiveId;
	private Customer customer;
	private String email;
	private String protokollNumber;
	private String varianti;
	private String objekti;
	private String location;
	private BigDecimal vlera;
	private String status;
	private String statusiPunimeve;
	private BigDecimal paradhenie;
	private BigDecimal vleraMbetur;
	private Date protokollDate;
	private boolean kontrata;
	private boolean hide;

	public PreventiveBean() {

	}

	public int getPreventiveId() {
		return preventiveId;
	}

	public void setPreventiveId(int preventiveId) {
		this.preventiveId = preventiveId;
	}

	
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
		if(this.paradhenie!=null && this.vlera !=null){
			this.vleraMbetur = (this.vlera.subtract(this.paradhenie));
		}else{
			this.vleraMbetur = (BigDecimal.ZERO.subtract(BigDecimal.ZERO));
		}
		
		return this.vleraMbetur;
	}

	public void setVleraMbetur(BigDecimal vleraMbetur) {
		this.vleraMbetur = vleraMbetur;
	}

	public Date getProtokollDate() {
		return protokollDate;
	}

	public void setProtokollDate(Date protokollDate) {
		this.protokollDate = protokollDate;
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

}
