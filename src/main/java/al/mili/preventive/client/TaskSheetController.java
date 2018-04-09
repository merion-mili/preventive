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
@ManagedBean(name = "taskSheetBeanController")
@RequestScoped
public class TaskSheetController implements Serializable{

	private static final long serialVersionUID = -2392657522225049053L;
	
	private List<TaskSheet> taskSheets;
	private List<TaskSheet> fileteredSheets;
	
	@ManagedProperty("#{taskSheetBeanService}")
	private TaskSheetBeanService taskSheetBeanService;
	
	@PostConstruct
	public void init(){
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		this.taskSheets = taskSheetBeanService.getTaskSheetsForUser(user.getUserName(),
				user.getRole().getRole()).stream()
				.sorted(Comparator.comparing(TaskSheet::getDate).reversed())
				.collect(Collectors.toList());;
		
	}

	public List<TaskSheet> getTaskSheets() {
		return taskSheets;
	}

	public void setTaskSheets(List<TaskSheet> taskSheets) {
		this.taskSheets = taskSheets;
	}

	public List<TaskSheet> getFileteredSheets() {
		return fileteredSheets;
	}

	public void setFileteredSheets(List<TaskSheet> fileteredSheets) {
		this.fileteredSheets = fileteredSheets;
	}

	public TaskSheetBeanService getTaskSheetBeanService() {
		return taskSheetBeanService;
	}

	public void setTaskSheetBeanService(TaskSheetBeanService taskSheetBeanService) {
		this.taskSheetBeanService = taskSheetBeanService;
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
