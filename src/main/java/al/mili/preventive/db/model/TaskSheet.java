package al.mili.preventive.db.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TASK_SHEET", uniqueConstraints = { @UniqueConstraint(columnNames = { "SHEET_ID" }) })
@NamedQueries({
		@NamedQuery(name = "queryAllTaskSheet", query = "from TaskSheet ts where ts.user.userName=:userName"),
		@NamedQuery(name = "queryDeleteSheet", query = "DELETE from TaskSheet ts WHERE ts.sheetId=:sheetId"),
		@NamedQuery(name = "queryUpdateSheet", query = "UPDATE TaskSheet ts SET ts.date=:date, ts.resources=:resources, ts.resourceType=:resourceType, ts.startTime=:startTime, ts.endTime=:endTime, ts.duration=:duration, ts.quantity=:quantity, ts.unit=:unit WHERE ts.sheetId=:sheetId") })
public class TaskSheet implements Serializable {

	private static final long serialVersionUID = 4648676461392947687L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SHEET_ID")
	private int sheetId;
	@Column(name = "TASK_DATE")
	private Date date;
	@ManyToOne
	@JoinColumn(name = "PREVENTIVE_ID")
	private Preventive preventive;
	@ManyToOne
	@JoinColumn(name = "RESOURCE_ID")
	private Resources resources;
	@ManyToOne
	@JoinColumn(name = "TYPE_ID")
	private ResourceType resourceType;
	
	@Column(name = "START_TIME")
	private Date startTime;
	@Column(name = "END_TIME")
	private Date endTime;
	@Column(name = "DURATION")
	private double duration;
	@Column(name = "ALLQUANTITY")
	private double quantity;
	@ManyToOne
	@JoinColumn(name = "UNIT_ID")
	private Unit unit;
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	public TaskSheet() {

	}

	public int getSheetId() {
		return sheetId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

}
