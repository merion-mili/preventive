package al.mili.preventive.db.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "TASK")
@NamedQueries({
	@NamedQuery(name = "queryDeleteTask", query = "DELETE from Task ta WHERE ta.taskId=:taskId"),
	@NamedQuery(name = "queryUpdateTask", query = "UPDATE Task ta SET ta.name=:name WHERE ta.taskId=:taskId")})
public class Task implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1112617446010109657L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TASK_ID")
	private int taskId;
	@Column(name = "NAME")
	private String name;
	
	
	private WorkLog worklog;
	
	
	public Task() {
	
	}

	public int getTaskId() {
		return taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WorkLog getWorklog() {
		return worklog;
	}

	public void setWorklog(WorkLog worklog) {
		this.worklog = worklog;
	}

	
	

}
