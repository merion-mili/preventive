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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Resources;
import al.mili.preventive.db.model.Task;
import al.mili.preventive.db.model.TaskSheet;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.WorkLog;

@ManagedBean(name = "workLogDetailController", eager=true)
@ViewScoped
public class WorkLogDetailController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5954721996888645776L;
	
	private List<WorkLog> workLogs;
	private WorkLogBean workLogBean;
	private List<WorkLogBean> workLogBeans;
	private List<WorkLogBean> filteredWorkLogs;
	private WorkLogBean selectedWorkLog;
	//private List<Preventive> preventives;
	private Preventive preventive;

	private List<Task> tasks;
	private List<Task> selectedTask;

	/*@ManagedProperty("#{preventiveBeanService}")
	private PreventiveBeanService preventiveBeanService;*/

	@ManagedProperty("#{workLogBeanService}")
	private WorKLogBeanService workLogBeanService;

	@ManagedProperty("#{taskBeanService}")
	private TaskBeanService taskBeanService;

	@PostConstruct
	public void init() {
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		this.preventive = (Preventive) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("preventive");
		workLogs = workLogBeanService.getWorkLogForPrevetnive(this.preventive.getPreventiveId());
		this.workLogBeans = convertWorkLogList(workLogs).stream()
				.sorted(Comparator.comparing(WorkLogBean::getWorkLogId))
				.collect(Collectors.toList());
		/*this.preventives = preventiveBeanService
				.getPreventivesForUserAndBoolean(user.getUserName(), user
						.getRole().getRole(), true);*/
		

		this.workLogBean = new WorkLogBean();
		
		this.tasks = taskBeanService.getTasks();

	}

	public List<WorkLog> getWorkLogs() {
		return workLogs;
	}

	public void setWorkLogs(List<WorkLog> workLogs) {
		this.workLogs = workLogs;
	}

	public List<WorkLogBean> getWorkLogBeans() {
		return this.workLogBeans;
	}

	public void setWorkLogBeans(List<WorkLogBean> workLogBeans) {
		this.workLogBeans = workLogBeans;
	}

	public WorkLogBean getWorkLogBean() {
		return workLogBean;
	}

	public void setWorkLogBean(WorkLogBean workLogBean) {
		this.workLogBean = workLogBean;
	}

	public List<WorkLogBean> getFilteredWorkLogs() {
		return filteredWorkLogs;
	}

	public void setFilteredWorkLogs(List<WorkLogBean> filteredWorkLogs) {
		this.filteredWorkLogs = filteredWorkLogs;
	}

	public WorkLogBean getSelectedWorkLog() {
		return selectedWorkLog;
	}

	public void setSelectedWorkLog(WorkLogBean selectedWorkLog) {
		this.selectedWorkLog = selectedWorkLog;
	}

	/*public List<Preventive> getPreventives() {
		return preventives;
	}

	public void setPreventives(List<Preventive> preventives) {
		this.preventives = preventives;
	}
*/

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	


	public void onCellEdit(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			DataTable s = (DataTable) event.getSource();
			WorkLogBean workLogBean = (WorkLogBean) s.getRowData();
			workLogBeanService.updateLog(
					workLogBean.getWorkLogId(), workLogBean.getDate(),
					workLogBean.getTask(), workLogBean.getNumberofWorker(),
					workLogBean.getNumberOfEquipment(), workLogBean.getOretEPunuara());

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"WorkLog " + workLogBean.getPreventive().getPreventiveId()
							+ " has benn updated", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	public void deleteWorkLog() {

		workLogBeanService.deleteLog(selectedWorkLog.getWorkLogId());

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"TaskSheet per"
						+ selectedWorkLog.getPreventive().getObjekti()
						+ " has benn deleted", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	public void addWorkLog() throws HibernateException,
	ConstraintViolationException{
		try {

			User user = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("user");
			workLogBeanService.addWorkLog(workLogBean.getDate(),
					this.preventive, user,
					workLogBean.getTask(),
					workLogBean.getNumberofWorker(), workLogBean.getNumberOfEquipment(),
					workLogBean.getOretEPunuara());

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"TaskSheet per"
							+ this.preventive.getObjekti()
							+ " has benn saved", null);
			FacesContext.getCurrentInstance().addMessage(null, message);

		} catch (ConstraintViolationException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Gabim ne entry:Objekti Ekziston.", "Duplicated Entry."));
		} catch (HibernateException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERROR! Access Database",
					"Failure to include data"));

		}
	}

	public void initDialog() {
		this.workLogBean.setDate(null);
		this.workLogBean.setTask(null);
		this.workLogBean.setNumberofWorker(0);
		this.workLogBean.setNumberOfEquipment(0);
	

		RequestContext.getCurrentInstance().reset(":logForm:addLog");
	}

	private List<WorkLogBean> convertWorkLogList(List<WorkLog> list) {
		List<WorkLogBean> workLogBeanList = new ArrayList<WorkLogBean>();
		for (WorkLog workLog : list) {
			WorkLogBean workLogBean = new WorkLogBean();
			workLogBean.setWorkLogId(workLog.getWorkLogId());
			workLogBean.setDate(workLog.getDate());
			workLogBean.setPreventive(workLog.getPreventive());
			workLogBean.setTask(workLog.getTask());
			workLogBean.setNumberofWorker(workLog.getNumberofWorker());
			workLogBean.setNumberOfEquipment(workLog.getNumberOfEquipment());
			workLogBean.setOretEPunuara(workLog.getOretEPunuara());
			

			workLogBeanList.add(workLogBean);
		}
		return workLogBeanList;
	}

	/*public PreventiveBeanService getPreventiveBeanService() {
		return preventiveBeanService;
	}

	public void setPreventiveBeanService(PreventiveBeanService preventiveBeanService) {
		this.preventiveBeanService = preventiveBeanService;
	}*/

	public TaskBeanService getTaskBeanService() {
		return taskBeanService;
	}

	public void setTaskBeanService(TaskBeanService taskBeanService) {
		this.taskBeanService = taskBeanService;
	}

	public WorKLogBeanService getWorkLogBeanService() {
		return workLogBeanService;
	}

	public void setWorkLogBeanService(WorKLogBeanService workLogBeanService) {
		this.workLogBeanService = workLogBeanService;
	}

	public Preventive getPreventive() {
		return preventive;
	}

	public void setPreventive(Preventive preventive) {
		this.preventive = preventive;
	}

	public List<Task> getSelectedTask() {
		return this.selectedTask;
	}

	public void setSelectedTask(List<Task> selectedTask) {
		this.selectedTask = selectedTask;
	}


	

}
