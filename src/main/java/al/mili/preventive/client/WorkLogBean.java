package al.mili.preventive.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Task;
import al.mili.preventive.db.model.User;

public class WorkLogBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5268341980214331051L;

	private int workLogId;
	private Date date;
	private int numberofWorker;
	private int numberOfEquipment;
	private Preventive preventive;
	private User user;
	// private Task task;
	private List<Task> task = new ArrayList<Task>();
	private double oretEPunuara;

	public WorkLogBean() {

	}

	public int getWorkLogId() {
		return workLogId;
	}

	public void setWorkLogId(int workLogId) {
		this.workLogId = workLogId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumberofWorker() {
		return numberofWorker;
	}

	public void setNumberofWorker(int numberofWorker) {
		this.numberofWorker = numberofWorker;
	}

	public int getNumberOfEquipment() {
		return numberOfEquipment;
	}

	public void setNumberOfEquipment(int numberOfEquipment) {
		this.numberOfEquipment = numberOfEquipment;
	}

	public Preventive getPreventive() {
		return preventive;
	}

	public void setPreventive(Preventive preventive) {
		this.preventive = preventive;
	}

	/*
	 * public Task getTask() { return task; }
	 * 
	 * public void setTask(Task task) { this.task = task; }
	 */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getOretEPunuara() {
		return oretEPunuara;
	}

	public void setOretEPunuara(double oretEPunuara) {
		this.oretEPunuara = oretEPunuara;
	}

	public List<Task> getTask() {
		return task;
	}

	public void setTask(List<Task> task) {
		this.task = task;
	}

}
