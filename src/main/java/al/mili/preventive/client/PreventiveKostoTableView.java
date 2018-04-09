package al.mili.preventive.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import al.mili.preventive.db.model.ResourceType;
import al.mili.preventive.db.model.Resources;

@ManagedBean(name = "preventiveTableView")
@ViewScoped
public class PreventiveKostoTableView implements Serializable {

	private static final long serialVersionUID = -2173197082543696514L;
	private Resources resource;
	private ResourceType resourceType;
	private List<Resources> resources;
	private List<ResourceType> resourceTypes;

	private StandingPreventiveItem standingPreventiveItem;
	private List<StandingPreventiveItem> standingPreventiveItems;

	@ManagedProperty("#{resourcesBeanService}")
	private ResourcesBeanService resourceBeanSerives;

	@ManagedProperty("#{resourceTypeBeanService}")
	private ResourceTypeBeanService resourceTypeBeanService;

	@ManagedProperty("#{standingKostoService}")
	private KostoService kostoService;

	private boolean disable;

	@PostConstruct
	public void init() {

		this.resourceTypes = resourceTypeBeanService.getResourceType();
		this.resources = resourceBeanSerives.getResources();

	}

	public List<StandingPreventiveItem> getStandingPreventiveItems() {
		if (this.resource == null || this.resourceType == null) {
			return new ArrayList<StandingPreventiveItem>();
		}
		this.standingPreventiveItems = kostoService.getAllTaskSheetForResource(
				this.resourceType, this.resource);

		return this.standingPreventiveItems;
	}

	public void handleResourceTypeChange() {
		if (getResourceType() != null)
			setResources(resourceBeanSerives.getResourcesForType(getResourceType()));
		if (getResourceType() != null || getResource() != null) {
			disable = true;
		} else {

			disable = false;
		}
	}

	
	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public Resources getResource() {
		return resource;
	}

	public void setResource(Resources resource) {
		this.resource = resource;
	}

	public List<Resources> getResources() {
		return resources;
	}

	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}

	public List<ResourceType> getResourceTypes() {
		return resourceTypes;
	}

	public void setResourceTypes(List<ResourceType> resourceTypes) {
		this.resourceTypes = resourceTypes;
	}

	public StandingPreventiveItem getStandingPreventiveItem() {
		return standingPreventiveItem;
	}

	public void setStandingPreventiveItem(
			StandingPreventiveItem standingPreventiveItem) {
		this.standingPreventiveItem = standingPreventiveItem;
	}

	public ResourcesBeanService getResourceBeanSerives() {
		return resourceBeanSerives;
	}

	public void setResourceBeanSerives(ResourcesBeanService resourceBeanSerives) {
		this.resourceBeanSerives = resourceBeanSerives;
	}

	public ResourceTypeBeanService getResourceTypeBeanService() {
		return resourceTypeBeanService;
	}

	public void setResourceTypeBeanService(
			ResourceTypeBeanService resourceTypeBeanService) {
		this.resourceTypeBeanService = resourceTypeBeanService;
	}

	public KostoService getKostoService() {
		return kostoService;
	}

	public void setKostoService(KostoService kostoService) {
		this.kostoService = kostoService;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public void setStandingPreventiveItems(
			List<StandingPreventiveItem> standingPreventiveItems) {
		this.standingPreventiveItems = standingPreventiveItems;
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
