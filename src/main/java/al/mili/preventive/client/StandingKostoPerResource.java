package al.mili.preventive.client;

import java.io.Serializable;
import java.util.List;

import al.mili.preventive.db.model.Preventive;

public class StandingKostoPerResource implements Serializable {

	private static final long serialVersionUID = -2248586103909333021L;
	private int standingId;
	private String resource;
	private String description;
	private String lloji;
	private long frequence;
	private double totalDuration;
	private double totalQuantity;
	private String unitName;
	private List<Preventive> preventive;

	public StandingKostoPerResource() {

	}

	public int getStandingId() {
		return standingId;
	}

	public void setStandingId(int standingId) {
		this.standingId = standingId;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLloji() {
		return lloji;
	}

	public void setLloji(String lloji) {
		this.lloji = lloji;
	}

	public long getFrequence() {
		return frequence;
	}

	public void setFrequence(long frequence) {
		this.frequence = frequence;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public List<Preventive> getPreventive() {
		return preventive;
	}

	public void setPreventive(List<Preventive> preventive) {
		this.preventive = preventive;
	}


}
