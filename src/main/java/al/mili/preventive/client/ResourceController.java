package al.mili.preventive.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import al.mili.preventive.db.model.Resources;
@ManagedBean(name = "resourcesController")
@RequestScoped
public class ResourceController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5736009213784274542L;
	
	private List<Resources> resourses;
	private List<Resources> fileteredResources;
	
	@ManagedProperty("#{resourcesBeanService}")
	private ResourcesBeanService resourcesBeanService;
	

	@PostConstruct
	public void init() {

		resourses = resourcesBeanService.getResources();

	}


	public List<Resources> getResourses() {
		return resourses;
	}

	public void setResourses(List<Resources> resourses) {
		this.resourses = resourses;
	}

	public List<Resources> getFileteredResources() {
		return fileteredResources;
	}

	public void setFileteredResources(List<Resources> fileteredResources) {
		this.fileteredResources = fileteredResources;
	}

	public ResourcesBeanService getResourcesBeanService() {
		return resourcesBeanService;
	}

	public void setResourcesBeanService(ResourcesBeanService resourcesBeanService) {
		this.resourcesBeanService = resourcesBeanService;
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
