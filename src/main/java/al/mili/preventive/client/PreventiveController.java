package al.mili.preventive.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.component.datatable.DataTable;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.User;

@ManagedBean(name = "preventiveBeanController")
@ViewScoped
public class PreventiveController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8688041212109597604L;

	private List<Preventive> filteredPreventives;
	private List<Preventive> preventives;
	private List<Preventive> selectedItems;
	private List<String> listOfStatus;
	private String status;
	private List<String> listOfStatusJob;
	private String statusJob;

	/*
	 * private TrackingTableItem trackingTableItem; private
	 * List<TrackingTableItem> trackingTableItems;
	 */

	@ManagedProperty("#{preventiveBeanService}")
	private PreventiveBeanService preventiveBeanService;

	@ManagedProperty("#{trackingService}")
	private TrackingService trackingService;

	@PostConstruct
	public void init() {
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");

		this.preventives = preventiveBeanService
				.getPreventivesForUserAndBoolean(user.getUserName(),
						user.getRole().getRole(), true)
				.stream()
				.sorted(Comparator.comparing(Preventive::getProtokollDate)
						.reversed()).collect(Collectors.toList());

		this.listOfStatus = new ArrayList<String>();
		for (Preventive preventive : this.preventives) {
			if (!this.listOfStatus.contains(preventive.getStatus())) {
				this.listOfStatus.add(preventive.getStatus());
			}
		}

		this.listOfStatusJob = new ArrayList<String>();
		for (Preventive preventive : this.preventives) {
			if (!this.listOfStatusJob.contains(preventive.getStatusiPunimeve())) {
				this.listOfStatusJob.add(preventive.getStatusiPunimeve());
			}
		}

	}

	public List<Preventive> getPreventives() {
		return preventives;
	}

	public void setPreventives(List<Preventive> preventives) {

		this.preventives = preventives;
	}

	public PreventiveBeanService getPreventiveBeanService() {
		return preventiveBeanService;
	}

	public void setPreventiveBeanService(
			PreventiveBeanService preventiveBeanService) {
		this.preventiveBeanService = preventiveBeanService;
	}

	public List<Preventive> getFilteredPreventives() {
		return this.filteredPreventives;
	}

	public void setFilteredPreventives(List<Preventive> filteredPreventives) {
		this.filteredPreventives = filteredPreventives;
	}

	public List<Preventive> getSelectedItems() {
		return this.selectedItems;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusJob() {
		return statusJob;
	}

	public void setStatusJob(String statusJob) {
		this.statusJob = statusJob;
	}

	public List<String> getListOfStatus() {
		return listOfStatus;
	}

	public void setListOfStatus(List<String> listOfStatus) {
		this.listOfStatus = listOfStatus;
	}

	public List<String> getListOfStatusJob() {
		return listOfStatusJob;
	}

	public void setListOfStatusJob(List<String> listOfStatusJob) {
		this.listOfStatusJob = listOfStatusJob;
	}

	public void setSelectedItems(List<Preventive> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public boolean filterByPrice(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
	}

	public void getHidesPrevetnive() {
		preventiveBeanService.getHidesPrevetnives();
	}

	public void selectedPreventive(Preventive preventive) {

		selectedItems = new ArrayList<Preventive>();
		for (Preventive preventiveItem : preventives) {
			if (preventive.getPreventiveId() == preventiveItem
					.getPreventiveId()) {

				selectedItems.add(preventive);
				preventiveItem.setChangeColor(true);
			} else {

				preventiveItem.setChecked(false);
				preventiveItem.setChangeColor(false);
			}
		}

	}

	/*public void changeColorEvent(Preventive prevetnive) {

		if (selectedItems != null) {

			if (!selectedItems.isEmpty()) {

				prevetnive.setChangeColor(true);
			} else {
				prevetnive.setChangeColor(false);
			}
		}
	}*/

	public String getOrderPreventive() {

		/*
		 * selectedItems = new ArrayList<Preventive>(); for (Preventive
		 * preventiveItem : preventives) { if (preventiveItem.isChecked()) {
		 * 
		 * selectedItems.add(preventiveItem);
		 * 
		 * } else { preventiveItem.setChecked(false); } }
		 */
		if (selectedItems != null) {

			if (!selectedItems.isEmpty()) {
				Preventive preventive = selectedItems.get(0);

				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("preventive", preventive);

				return "editOrder.xhtml?faces-redirect=true";
			}
		}
		return "homeUser";

	}

	public String getWorkLogPrevetnive() {

		/*
		 * selectedItems = new ArrayList<Preventive>(); for (Preventive
		 * preventiveItem : preventives) { if (preventiveItem.isChecked()) {
		 * 
		 * selectedItems.add(preventiveItem);
		 * 
		 * } else { preventiveItem.setChecked(false); }
		 * 
		 * }
		 */
		if (selectedItems != null) {
			if (!selectedItems.isEmpty()) {
				Preventive preventive = selectedItems.get(0);

				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("preventive", preventive);

				return "editWorkLog.xhtml?faces-redirect=true";
			}
		}

		return "homeUser";

	}

	public String getTaskSheetPrevetnive() {

		/*
		 * selectedItems = new ArrayList<Preventive>(); for (Preventive
		 * preventiveItem : preventives) { if (preventiveItem.isChecked()) {
		 * 
		 * selectedItems.add(preventiveItem);
		 * 
		 * } else { preventiveItem.setChecked(false); }
		 * 
		 * }
		 */
		if (selectedItems != null) {
			if (!selectedItems.isEmpty()) {
				Preventive preventive = selectedItems.get(0);

				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("preventive", preventive);

				return "editTaskSheet.xhtml?faces-redirect=true";
			}
		}

		return "homeUser";

	}

	public String getAllElement() {

		/*
		 * selectedItems = new ArrayList<Preventive>(); for (Preventive
		 * preventiveItem : preventives) { if (preventiveItem.isChecked()) {
		 * 
		 * selectedItems.add(preventiveItem);
		 * 
		 * } else { preventiveItem.setChecked(false); }
		 * 
		 * }
		 */
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		if (selectedItems != null) {
			if (!selectedItems.isEmpty()
					&& user.getRole().getRole().equals("admin")) {
				int preventiveId = selectedItems.get(0).getPreventiveId();

				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("preventiveId", preventiveId);

				return "trackingStand1.xhtml?faces-redirect=true";
			}
		}

		return "homeUser";

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

	public TrackingService getTrackingService() {
		return trackingService;
	}

	public void setTrackingService(TrackingService trackingService) {
		this.trackingService = trackingService;
	}

}
