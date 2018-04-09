package al.mili.preventive.client;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Task;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.WorkLog;
import al.mili.preventive.db.service.WorkLogDbService;

@ManagedBean(name = "workLogBeanService",eager=true)
@ViewScoped
public class WorKLogBeanService implements Serializable{
	
	private List<WorkLog> workLogs;
	private WorkLogDbService workLogDbService;
	
	@PostConstruct
	public void init(){
		
		workLogDbService = new WorkLogDbService();
		
	}
	
	public WorkLogDbService getWorkLogDbService() {
		return workLogDbService;
	}
	public void setWorkLogDbService(WorkLogDbService workLogDbService) {
		this.workLogDbService = workLogDbService;
	}
	public List<WorkLog> getWorkLogs() {
		return workLogs;
	}
	public void setWorkLogs(List<WorkLog> workLogs) {
		this.workLogs = workLogs;
	}
	
	public List<WorkLog> getWorkLogsForUser(String userName, String role){
		this.workLogs = workLogDbService.getWorkLogForUser(userName, role);
		return this.workLogs;
	}
	
	public void deleteLog(int workLogId){
		workLogDbService.deleteLog(workLogId);
	}
	
	public void updateLog(int workLogId, Date date, 
		 List<Task> task, int numberofWorker, int numberOfEquipment, double oretEPunuara) {
		 workLogDbService.updateLog(workLogId, date, task, numberofWorker,
				numberOfEquipment,oretEPunuara);
	}

	public void addWorkLog(Date date, Preventive preventive, User user, List<Task> task,
			int numberofWorker, int numberOfEquipment,double oretEPunuara){
		workLogDbService.addWorkLog(date, preventive, user,
				task, numberofWorker, numberOfEquipment,oretEPunuara);
	}
	
	public List<WorkLog> getWorkLogForPrevetnive(int preventive){
		this.workLogs = workLogDbService.getWorkLogForPrevetnive(preventive);
		return this.workLogs;
	}

}
