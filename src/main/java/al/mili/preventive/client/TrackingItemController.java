package al.mili.preventive.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import al.mili.preventive.db.model.Order;
import al.mili.preventive.db.model.TaskSheet;
import al.mili.preventive.db.model.WorkLog;

@ManagedBean(name = "trackingBeanController")
@ViewScoped
public class TrackingItemController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8688041212109597604L;
	private List<TaskSheet> taskSheets;
	private List<WorkLog> workLogs;
	private List<Order> orderList;
	private List<TrackingTableItem> trackingTableItems;

	@ManagedProperty("#{trackingService}")
	private TrackingService trackingService;
	
	@ManagedProperty("#{taskSheetBeanService}")
	private TaskSheetBeanService taskSheetBeanService;
	
	@ManagedProperty("#{workLogBeanService}")
	private WorKLogBeanService workLogBeanService;


	@PostConstruct
	public void init() {
		
		Integer preventiveId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("preventiveId");
		getTrackingTableItems1(preventiveId);
		getOrderForPrevetnive(preventiveId);
		getTaskSheetForPrevetnive(preventiveId);
		getWorkLogsForPreventive(preventiveId);
		
	}

	
	public void getTrackingTableItems1(int preventiveId) {
		this.trackingTableItems = trackingService.getOrderForPreventiveId(
				preventiveId, true, true);

	}
	
	public void getOrderForPrevetnive(int preventiveId) {
		this.orderList = trackingService.getOrderForPreventiveIdBoolean(
				preventiveId, true, false);

	}
	
	public void getTaskSheetForPrevetnive(int preventiveId) {
		this.taskSheets = taskSheetBeanService.getTaskSheetsForPrevetnive(preventiveId);

	}
	
	public void getWorkLogsForPreventive(int preventiveId){
		this.setWorkLogs(workLogBeanService.getWorkLogForPrevetnive(preventiveId));
	}

	public TrackingService getTrackingService() {
		return trackingService;
	}

	public void setTrackingService(TrackingService trackingService) {
		this.trackingService = trackingService;
	}

	public List<TrackingTableItem> getTrackingTableItems() {
		return this.trackingTableItems;
	}

	public void setTrackingTableItems(List<TrackingTableItem> trackingTableItems) {
		this.trackingTableItems = trackingTableItems;
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



	public List<Order> getOrderList() {
		return orderList;
	}


	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}


	public List<TaskSheet> getTaskSheets() {
		return taskSheets;
	}


	public void setTaskSheets(List<TaskSheet> taskSheets) {
		this.taskSheets = taskSheets;
	}


	public TaskSheetBeanService getTaskSheetBeanService() {
		return taskSheetBeanService;
	}


	public void setTaskSheetBeanService(TaskSheetBeanService taskSheetBeanService) {
		this.taskSheetBeanService = taskSheetBeanService;
	}


	public WorKLogBeanService getWorkLogBeanService() {
		return workLogBeanService;
	}


	public void setWorkLogBeanService(WorKLogBeanService workLogBeanService) {
		this.workLogBeanService = workLogBeanService;
	}


	public List<WorkLog> getWorkLogs() {
		return workLogs;
	}


	public void setWorkLogs(List<WorkLog> workLogs) {
		this.workLogs = workLogs;
	}


}
