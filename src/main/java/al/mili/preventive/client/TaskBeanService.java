package al.mili.preventive.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import al.mili.preventive.db.model.Task;
import al.mili.preventive.db.service.TaskDbService;

@ManagedBean(name = "taskBeanService", eager = true)
@ViewScoped
public class TaskBeanService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1829631892336369665L;
	private Task task;
	private TaskDbService taskDbService;
	private List<Task> tasks;
	
	@PostConstruct
	public void init() {

		taskDbService = new TaskDbService();
		this.tasks = taskDbService.getAllTasks();
		
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskDbService getTaskDbService() {
		return taskDbService;
	}

	public void setTaskDbService(TaskDbService taskDbService) {
		this.taskDbService = taskDbService;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	
	public boolean delete(int taskId) {
		return taskDbService.deleteTask(taskId);

	}
	
	public boolean update(int taskId, String name){
		return taskDbService.updateTask(taskId, name);
	}
	
	public void addTask(String name) throws HibernateException,
	ConstraintViolationException{
		try {
			taskDbService.addTask(name);
		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");

		}
	}

}
