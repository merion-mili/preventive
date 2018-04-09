package al.mili.preventive.client;

import java.io.Serializable;
import java.util.Date;

public class StandingPreventiveItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5147242191015236855L;
	private int standingId;
	private String preventiveObject;
	private String prevetntiveCustomer;
	private Date preventiveDate;
	private long frequnecy;
	private double totalDuration;
	private double totalQuantity;
	private String unitName;
	
	public StandingPreventiveItem() {
		
	}

	public String getPreventiveObject() {
		return preventiveObject;
	}

	public void setPreventiveObject(String preventiveObject) {
		this.preventiveObject = preventiveObject;
	}

	public String getPrevetntiveCustomer() {
		return prevetntiveCustomer;
	}

	public void setPrevetntiveCustomer(String prevetntiveCustomer) {
		this.prevetntiveCustomer = prevetntiveCustomer;
	}

	public Date getPreventiveDate() {
		return preventiveDate;
	}

	public void setPreventiveDate(Date preventiveDate) {
		this.preventiveDate = preventiveDate;
	}

	
	public double getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(double totalDuration) {
		this.totalDuration = totalDuration;
	}

	public double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public int getStandingId() {
		return standingId;
	}

	public void setStandingId(int standingId) {
		this.standingId = standingId;
	}

	public long getFrequnecy() {
		return frequnecy;
	}

	public void setFrequnecy(long frequnecy) {
		this.frequnecy = frequnecy;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	

}
