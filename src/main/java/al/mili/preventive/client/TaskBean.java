package al.mili.preventive.client;

import java.io.Serializable;

public class TaskBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4139110594415565624L;
	private int taskId;
	private String name;

	public TaskBean() {
		
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
