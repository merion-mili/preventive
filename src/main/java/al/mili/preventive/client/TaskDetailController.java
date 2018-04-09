package al.mili.preventive.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import al.mili.preventive.db.model.Task;

@ManagedBean(name = "taskDetailController")
@RequestScoped
public class TaskDetailController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8531849043269374236L;
	private List<Task> tasks;
	private List<TaskBean> taskBeans;

	private TaskBean taskBean;
	private TaskBean selectedTask;

	@ManagedProperty("#{taskBeanService}")
	private TaskBeanService taskBeanService;

	@PostConstruct
	public void init() {

		this.taskBean = new TaskBean();

		tasks = taskBeanService.getTasks();
		this.taskBeans = convertTaskList(tasks).stream()
				.sorted(Comparator.comparing(TaskBean::getTaskId))
				.collect(Collectors.toList());
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<TaskBean> getTaskBeans() {
		return taskBeans;
	}

	public void setTaskBeans(List<TaskBean> taskBeans) {
		this.taskBeans = taskBeans;
	}

		
	public TaskBean getTaskBean() {
		return taskBean;
	}

	public void setTaskBean(TaskBean taskBean) {
		this.taskBean = taskBean;
	}

	
	public TaskBean getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(TaskBean selectedTask) {
		this.selectedTask = selectedTask;
	}

	public TaskBeanService getTaskBeanService() {
		return taskBeanService;
	}

	public void setTaskBeanService(TaskBeanService taskBeanService) {
		this.taskBeanService = taskBeanService;
	}

	private List<TaskBean> convertTaskList(List<Task> list) {
		List<TaskBean> listTaskBean = new ArrayList<TaskBean>();
		for (Task task : list) {
			TaskBean taskBean = new TaskBean();
			taskBean.setTaskId(task.getTaskId());
			taskBean.setName(task.getName());

			listTaskBean.add(taskBean);
		}
		return listTaskBean;
	}

	public void addTask() {

		taskBeanService.addTask(taskBean.getName());

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Task " + taskBean.getName() + " has benn saved", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	public void deleteTask() {

		taskBeanService.delete(selectedTask.getTaskId());

		FacesMessage msg = new FacesMessage("Task deleted",
				"Task  " + selectedTask.getName() + " has been deleted");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			DataTable s = (DataTable) event.getSource();
			TaskBean task = (TaskBean) s.getRowData();
			taskBeanService.update(task.getTaskId(), task.getName());

			FacesMessage msg = new FacesMessage("Ok",
					"Task " + task.getName() + " u editua");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}

	}

	public void initDialog() {
		this.taskBean.setName("");

		RequestContext.getCurrentInstance().reset(":taskForm:addTask");
	}

}
