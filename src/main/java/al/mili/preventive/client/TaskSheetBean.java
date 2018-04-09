package al.mili.preventive.client;

import java.io.Serializable;
import java.util.Date;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Resources;
import al.mili.preventive.db.model.Task;
import al.mili.preventive.db.model.Unit;


public class TaskSheetBean implements Serializable{


	private static final long serialVersionUID = -797321161218621698L;
	
	private int sheetId;
	private Date date;
	private Preventive preventive;
	private Resources resources;

	private Date startTime;
	private Date endTime;
	private double quantity;
	private double duration;
	private Unit unit;
	
	public TaskSheetBean() {
		
	}

	public int getSheetId() {
		return sheetId;
	}

	public void setSheetId(int sheetId) {
		this.sheetId = sheetId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Preventive getPreventive() {
		return preventive;
	}

	public void setPreventive(Preventive preventive) {
		this.preventive = preventive;
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	
	

}
