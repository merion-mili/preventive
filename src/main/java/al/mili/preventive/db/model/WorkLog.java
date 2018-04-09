package al.mili.preventive.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "WORK_LOG")
@NamedQueries({
		@NamedQuery(name = "queryAllWorklog", query = "from WorkLog wl where wl.user.userName=:userName"),
		@NamedQuery(name = "queryDeleteWorklog", query = "DELETE from WorkLog wl WHERE wl.workLogId=:workLogId"),
		@NamedQuery(name = "queryUpdateWorklog", query = "UPDATE WorkLog wl SET wl.date=:date, wl.task=:task, wl.numberofWorker=:numberofWorker, wl.numberOfEquipment=:numberOfEquipment, wl.oretEPunuara=:oretEPunuara WHERE wl.workLogId=:workLogId") })
public class WorkLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6173158822960807040L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WORKLOG_ID")
	private int workLogId;
	@Column(name = "WORKLOG_DATE")
	private Date date;
	@Column(name = "NR_WORKER")
	private int numberofWorker;
	@Column(name = "NR_EQUIPMENT")
	private int numberOfEquipment;
	@Column(name = "ORET_PUNUARA")
	private double oretEPunuara;

	@ManyToOne
	@JoinColumn(name = "PREVENTIVE_ID")
	private Preventive preventive;
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "TASK_ID") private Task task;
	 */
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "TASK_WORKLOG", joinColumns = @JoinColumn(name = "WORKLOG_ID"), inverseJoinColumns = @JoinColumn(name = "TASK_ID"), uniqueConstraints = { @UniqueConstraint(columnNames = {
			"WORKLOG_ID", "TASK_ID" }) })
	private List<Task> task = new ArrayList<Task>();

	public WorkLog() {

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
