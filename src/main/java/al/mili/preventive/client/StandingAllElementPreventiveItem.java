package al.mili.preventive.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StandingAllElementPreventiveItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 24246127922482347L;
	private int standingId;
	private int preventiveId;
	private String protokollNumber;
	//private String varianti;
	private String customerName;
	private String preventiveObject;
	private Date preventiveDate;
	private BigDecimal vlera;
	private Date startDate;
	private Date endDate;
	private int diffInDays;
	private BigDecimal artikullQuantity;
	private BigDecimal artikullTrackQuantity;
	private double oretEPunuara;
	private long numberOfWorker;
	private long numberOfEquipment;
	private Integer progress;
	private boolean checked;

	public StandingAllElementPreventiveItem() {

	}

	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPreventiveObject() {
		return preventiveObject;
	}

	public void setPreventiveObject(String preventiveObject) {
		this.preventiveObject = preventiveObject;
	}

	public Date getPreventiveDate() {
		return preventiveDate;
	}

	public void setPreventiveDate(Date preventiveDate) {
		this.preventiveDate = preventiveDate;
	}

	public BigDecimal getArtikullQuantity() {
		return artikullQuantity;
	}

	public void setArtikullQuantity(BigDecimal artikullQuantity) {
		this.artikullQuantity = artikullQuantity;
	}

	public BigDecimal getArtikullTrackQuantity() {
		return artikullTrackQuantity;
	}

	public void setArtikullTrackQuantity(BigDecimal artikullTrackQuantity) {
		this.artikullTrackQuantity = artikullTrackQuantity;
	}

	
	
	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getDiffInDays() {
		return diffInDays;
	}

	public void setDiffInDays(int diffInDays) {
		this.diffInDays = diffInDays;
	}

	

	public int getStandingId() {
		return standingId;
	}

	public void setStandingId(int standingId) {
		this.standingId = standingId;
	}




	public String getProtokollNumber() {
		return protokollNumber;
	}



	public void setProtokollNumber(String protokollNumber) {
		this.protokollNumber = protokollNumber;
	}



	public BigDecimal getVlera() {
		return vlera;
	}



	public void setVlera(BigDecimal vlera) {
		this.vlera = vlera;
	}



	public int getPreventiveId() {
		return preventiveId;
	}



	public void setPreventiveId(int preventiveId) {
		this.preventiveId = preventiveId;
	}



	public boolean isChecked() {
		return checked;
	}



	public void setChecked(boolean checked) {
		this.checked = checked;
	}



	public double getOretEPunuara() {
		return oretEPunuara;
	}



	public void setOretEPunuara(double oretEPunuara) {
		this.oretEPunuara = oretEPunuara;
	}



	public long getNumberOfWorker() {
		return numberOfWorker;
	}



	public void setNumberOfWorker(long numberOfWorker) {
		this.numberOfWorker = numberOfWorker;
	}



	public long getNumberOfEquipment() {
		return numberOfEquipment;
	}



	public void setNumberOfEquipment(long numberOfEquipment) {
		this.numberOfEquipment = numberOfEquipment;
	}



	/*public String getVarianti() {
		return varianti;
	}



	public void setVarianti(String varianti) {
		this.varianti = varianti;
	}
*/


	
	

}
