package al.mili.preventive.client;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.TaskSheet;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.WorkLog;
@ManagedBean(name = "workLogController")
@RequestScoped
public class WorkLogController implements Serializable{

	private static final long serialVersionUID = -2392657522225049053L;
	
	private List<WorkLog> workLogs;
	private List<WorkLog> fileteredLogs;
	
	@ManagedProperty("#{workLogBeanService}")
	private WorKLogBeanService workLogBeanService;
	
	@PostConstruct
	public void init(){
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		this.workLogs = workLogBeanService.getWorkLogsForUser(user.getUserName(),
				user.getRole().getRole()).stream()
				.sorted(Comparator.comparing(WorkLog::getDate).reversed())
				.collect(Collectors.toList());;
		
	}

	public List<WorkLog> getWorkLogs() {
		return workLogs;
	}

	public void setWorkLogs(List<WorkLog> workLogs) {
		this.workLogs = workLogs;
	}

	public List<WorkLog> getFileteredLogs() {
		return fileteredLogs;
	}

	public void setFileteredLogs(List<WorkLog> fileteredLogs) {
		this.fileteredLogs = fileteredLogs;
	}

		
	public WorKLogBeanService getWorkLogBeanService() {
		return workLogBeanService;
	}

	public void setWorkLogBeanService(WorKLogBeanService workLogBeanService) {
		this.workLogBeanService = workLogBeanService;
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);

			cell.setCellStyle(cellStyle);
		}
	}

	
	
}
