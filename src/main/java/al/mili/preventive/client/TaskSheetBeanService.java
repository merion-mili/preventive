package al.mili.preventive.client;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Resources;
import al.mili.preventive.db.model.Task;
import al.mili.preventive.db.model.TaskSheet;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.service.TaskSheetDbService;
@ManagedBean(name = "taskSheetBeanService", eager=true)
@ViewScoped
public class TaskSheetBeanService implements Serializable{

	
	private static final long serialVersionUID = 2838740452585826314L;
	
	private TaskSheet taskSheet;
	private List<TaskSheet> taskSheets;
	private TaskSheetDbService taskSheetDbService;
	
	@PostConstruct
	public void init(){
		taskSheetDbService = new TaskSheetDbService();
		
	}

	public List<TaskSheet> getTaskSheets() {
		return taskSheets;
	}

	public void setTaskSheets(List<TaskSheet> taskSheets) {
		this.taskSheets = taskSheets;
	}
	
	public List<TaskSheet> getTaskSheetsForUser(String userName, String role){
		this.taskSheets = taskSheetDbService.getTaskSheetForUser(userName, role);
		return this.taskSheets;
	}
	
	public boolean deleteSheet(int sheetId){
		return taskSheetDbService.deleteSheet(sheetId);
	}
	
	public boolean updateSheet(int sheetId, Date date, Preventive preventive,
			Resources resources, Date startTime, Date endTime,
			double qunatity, Unit unit) {
		return taskSheetDbService.update(sheetId, date, preventive, resources, startTime,
				endTime, qunatity, unit);
	}

	public void addTaskSheet(Date date, Preventive preventive, User user, Resources resources, 
			Date startTime, Date endTime, double quantity, Unit unit){
		taskSheetDbService.addTaskSheet(date, preventive, user, resources, startTime, endTime, quantity, unit);
	}
	
	public List<TaskSheet> getTaskSheetsForPrevetnive(int preventive){
		this.taskSheets = taskSheetDbService.getTaskSheetForPrevetnive(preventive);
		return this.taskSheets;
	}

}
