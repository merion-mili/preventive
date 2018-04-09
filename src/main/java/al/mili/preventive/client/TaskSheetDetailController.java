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

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Resources;
import al.mili.preventive.db.model.TaskSheet;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;

@ManagedBean(name = "taskSheetDetailController")
@RequestScoped
public class TaskSheetDetailController implements Serializable {

	private static final long serialVersionUID = -8717452717692386233L;

	private List<TaskSheet> taskSheets;
	private TaskSheetBean taskSheetBean;
	private List<TaskSheetBean> taskSheetBeans;
	private List<TaskSheetBean> filteredTaskSheets;
	private TaskSheetBean selectedTaskSheet;
	private List<Preventive> preventives;
	private List<Resources> listResources;
	private List<Unit> units;
	private Preventive preventive;

	@ManagedProperty("#{preventiveBeanService}")
	private PreventiveBeanService preventiveBeanService;

	@ManagedProperty("#{taskSheetBeanService}")
	private TaskSheetBeanService taskSheetBeanService;

	@ManagedProperty("#{resourcesBeanService}")
	private ResourcesBeanService resourcesBeanService;

	@ManagedProperty("#{unitBeanService}")
	private UnitBeanService unitBeanService;
	


	@PostConstruct
	public void init() {
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		this.preventive = (Preventive) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("preventive");
		taskSheets = taskSheetBeanService.getTaskSheetsForPrevetnive(this.preventive.getPreventiveId());

		this.taskSheetBeans = convertTaskSheetList(taskSheets).stream()
				.sorted(Comparator.comparing(TaskSheetBean::getSheetId))
				.collect(Collectors.toList());
		this.preventives = preventiveBeanService
				.getPreventivesForUserAndBoolean(user.getUserName(), user
						.getRole().getRole(), true);
		this.listResources = resourcesBeanService.getResources();

		this.units = unitBeanService.getUnits();

		this.taskSheetBean = new TaskSheetBean();
		
		

	}

	public List<TaskSheet> getTaskSheets() {
		return taskSheets;
	}

	public void setTaskSheets(List<TaskSheet> taskSheets) {
		this.taskSheets = taskSheets;
	}

	public List<TaskSheetBean> getTaskSheetBeans() {
		return this.taskSheetBeans;
	}

	public void setTaskSheetBeans(List<TaskSheetBean> taskSheetBeans) {
		this.taskSheetBeans = taskSheetBeans;
	}

	public TaskSheetBean getTaskSheetBean() {
		return taskSheetBean;
	}

	public void setTaskSheetBean(TaskSheetBean taskSheetBean) {
		this.taskSheetBean = taskSheetBean;
	}

	public List<TaskSheetBean> getFilteredTaskSheets() {
		return filteredTaskSheets;
	}

	public void setFilteredTaskSheets(List<TaskSheetBean> filteredTaskSheets) {
		this.filteredTaskSheets = filteredTaskSheets;
	}

	public TaskSheetBean getSelectedTaskSheet() {
		return selectedTaskSheet;
	}

	public void setSelectedTaskSheet(TaskSheetBean selectedTaskSheet) {
		this.selectedTaskSheet = selectedTaskSheet;
	}

	public List<Preventive> getPreventives() {
		return preventives;
	}

	public void setPreventives(List<Preventive> preventives) {
		this.preventives = preventives;
	}

	public List<Resources> getListResources() {
		return listResources;
	}

	public void setListResources(List<Resources> listResources) {
		this.listResources = listResources;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}


	public TaskSheetBeanService getTaskSheetBeanService() {
		return taskSheetBeanService;
	}

	public void setTaskSheetBeanService(
			TaskSheetBeanService taskSheetBeanService) {
		this.taskSheetBeanService = taskSheetBeanService;
	}

	public ResourcesBeanService getResourcesBeanService() {
		return resourcesBeanService;
	}

	public void setResourcesBeanService(
			ResourcesBeanService resourcesBeanService) {
		this.resourcesBeanService = resourcesBeanService;
	}

	public UnitBeanService getUnitBeanService() {
		return unitBeanService;
	}

	public void setUnitBeanService(UnitBeanService unitBeanService) {
		this.unitBeanService = unitBeanService;
	}

	public void onCellEdit(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			DataTable s = (DataTable) event.getSource();
			TaskSheetBean taskSheetBean = (TaskSheetBean) s.getRowData();
			taskSheetBeanService.updateSheet(
					taskSheetBean.getSheetId(), taskSheetBean.getDate(),taskSheetBean.getPreventive(),
					taskSheetBean.getResources(),
					taskSheetBean.getStartTime(),taskSheetBean.getEndTime(), 
					taskSheetBean.getQuantity(),taskSheetBean.getUnit());

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"TaskSheet " + taskSheetBean.getPreventive().getPreventiveId()
							+ " has benn updated", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	public void deleteTaskSheet() {

		taskSheetBeanService.deleteSheet(selectedTaskSheet.getSheetId());

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"TaskSheet per"
						+ selectedTaskSheet.getPreventive().getObjekti()
						+ " has benn deleted", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	public void addTaskSheet() throws HibernateException,
	ConstraintViolationException{
		try {

			User user = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("user");
			taskSheetBeanService.addTaskSheet(taskSheetBean.getDate(),
					this.preventive, user,
					taskSheetBean.getResources(),taskSheetBean.getStartTime(), 
					taskSheetBean.getEndTime(),	taskSheetBean.getQuantity(), 
					taskSheetBean.getUnit());

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
		this.taskSheetBean.setDate(null);
		this.taskSheetBean.setResources(null);
		this.taskSheetBean.setStartTime(null);
		this.taskSheetBean.setEndTime(null);
		this.taskSheetBean.setQuantity(0);
		

		RequestContext.getCurrentInstance().reset(":sheetForm:addSheet");
	}

	private List<TaskSheetBean> convertTaskSheetList(List<TaskSheet> list) {
		List<TaskSheetBean> taskSheetBeanList = new ArrayList<TaskSheetBean>();
		for (TaskSheet taskSheet : list) {
			TaskSheetBean taskSheetBean = new TaskSheetBean();
			taskSheetBean.setSheetId(taskSheet.getSheetId());
			taskSheetBean.setDate(taskSheet.getDate());
			taskSheetBean.setPreventive(taskSheet.getPreventive());
			taskSheetBean.setResources(taskSheet.getResources());
			taskSheetBean.setStartTime(taskSheet.getStartTime());
			taskSheetBean.setEndTime(taskSheet.getEndTime());
			taskSheetBean.setDuration(taskSheet.getDuration());
			taskSheetBean.setQuantity(taskSheet.getQuantity());
			taskSheetBean.setUnit(taskSheet.getUnit());

			taskSheetBeanList.add(taskSheetBean);
		}
		return taskSheetBeanList;
	}

	public PreventiveBeanService getPreventiveBeanService() {
		return preventiveBeanService;
	}

	public void setPreventiveBeanService(PreventiveBeanService preventiveBeanService) {
		this.preventiveBeanService = preventiveBeanService;
	}

	
	
	

}
