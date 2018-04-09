package al.mili.preventive.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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

import al.mili.preventive.db.model.User;

@ManagedBean(name = "allElementPreventiveController")
@ViewScoped
public class AllElementPreventiveItemController implements Serializable {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -8688041212109597604L;

	private List<StandingAllElementPreventiveItem> standingAllElementPrevetniveItems;
	
	private List<StandingAllElementPreventiveItem> selectedItems;

	@ManagedProperty("#{standingKostoService}")
	private KostoService kostoService;

	@PostConstruct
	public void init() {
		this.standingAllElementPrevetniveItems = kostoService
				.getAllPrevetniveElement();
	}


	public List<StandingAllElementPreventiveItem> getStandingAllElementPrevetniveItems() {

		return standingAllElementPrevetniveItems;
	}

	public void setStandingAllElementPrevetniveItems(
			List<StandingAllElementPreventiveItem> standingAllElementPrevetniveItems) {
		this.standingAllElementPrevetniveItems = standingAllElementPrevetniveItems;
	}

	public KostoService getKostoService() {
		return kostoService;
	}

	public void setKostoService(KostoService kostoService) {
		this.kostoService = kostoService;
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

public void selectedOrder(StandingAllElementPreventiveItem standingAllElementPrevetniveItem) throws IOException{

		
	selectedItems = new ArrayList<StandingAllElementPreventiveItem>();
		for (StandingAllElementPreventiveItem standing : standingAllElementPrevetniveItems) {
			if (standingAllElementPrevetniveItem.getStandingId() == standing.getStandingId()
					) {

				selectedItems.add(standingAllElementPrevetniveItem);

			} else {
				standing.setChecked(false);
			}

		}
		
		
		int preventiveId = selectedItems.get(0).getPreventiveId();
		FacesContext.getCurrentInstance()
        .getExternalContext().getSessionMap().put("preventiveId", preventiveId);
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		if(user.getRole().getRole().equals("admin")){
			FacesContext.getCurrentInstance()
	        .getExternalContext().redirect("./trackingStand1.xhtml");
		}else{
			return ;
		}
		
		
		
	}


public List<StandingAllElementPreventiveItem> getSelectedItems() {
	return selectedItems;
}


public void setSelectedItems(List<StandingAllElementPreventiveItem> selectedItems) {
	this.selectedItems = selectedItems;
}
	
	


}
