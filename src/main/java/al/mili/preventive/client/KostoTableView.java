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

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.User;

@ManagedBean(name = "kostoTableView")
@ViewScoped
public class KostoTableView implements Serializable {

	private static final long serialVersionUID = -2173197082543696514L;
	private User user ;
	private List<User> users;
	private Preventive preventive;
	private List<Preventive> preventives;

	private StandingKostoPerResource standingKostoPerResource;
	private List<StandingKostoPerResource> listStangingKostoPerResource;
	
	private StandingKostoPerResource standingKosto;
	private List<StandingKostoPerResource> listStangingKosto;

	@ManagedProperty("#{preventiveBeanService}")
	private PreventiveBeanService preventiveBeanService;

	@ManagedProperty("#{userBeanService}")
	private UserBeanService userBeanService;

	@ManagedProperty("#{standingKostoService}")
	private KostoService kostoService;

	private boolean disable;

	@PostConstruct
	public void init() {
		
	/*	User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");*/

		this.users = userBeanService.getUserPerRole(2);
		this.preventives = preventiveBeanService.getPreventives();		

	}

	public List<StandingKostoPerResource> getListStangingKostoPerResource() {
		if (this.user == null || this.preventive == null) {
			return new ArrayList<StandingKostoPerResource>();
		}
		this.listStangingKostoPerResource = kostoService
				.getTaskSheetPerPreventiveUser(this.preventive, this.user);

		return this.listStangingKostoPerResource;
	}
	
	public List<StandingKostoPerResource> getListStangingKosto() {
		
		this.listStangingKosto = kostoService
				.getTaskSheetResource();

		return this.listStangingKosto;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Preventive getPreventive() {
		return preventive;
	}

	public void setPreventive(Preventive preventive) {
		this.preventive = preventive;
	}

	public StandingKostoPerResource getStandingKostoPerResource() {
		return standingKostoPerResource;
	}

	public void setStandingKostoPerResource(
			StandingKostoPerResource standingKostoPerResource) {
		this.standingKostoPerResource = standingKostoPerResource;
	}

	public PreventiveBeanService getPreventiveBeanService() {
		return preventiveBeanService;
	}

	public void setPreventiveBeanService(
			PreventiveBeanService preventiveBeanService) {
		this.preventiveBeanService = preventiveBeanService;
	}

	public UserBeanService getUserBeanService() {
		return userBeanService;
	}

	public void setUserBeanService(UserBeanService userBeanService) {
		this.userBeanService = userBeanService;
	}

	public KostoService getKostoService() {
		return kostoService;
	}

	public void setKostoService(KostoService kostoService) {
		this.kostoService = kostoService;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Preventive> getPreventives() {
		return preventives;
	}

	public void setPreventives(List<Preventive> preventives) {
		this.preventives = preventives;
	}

	public void setListStangingKostoPerResource(
			List<StandingKostoPerResource> listStangingKostoPerResource) {
		this.listStangingKostoPerResource = listStangingKostoPerResource;
	}
	
	
	public StandingKostoPerResource getStandingKosto() {
		return standingKosto;
	}

	public void setStandingKosto(StandingKostoPerResource standingKosto) {
		this.standingKosto = standingKosto;
	}

	public void setListStangingKosto(
			List<StandingKostoPerResource> listStangingKosto) {
		this.listStangingKosto = listStangingKosto;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public void handleUserChange() {
		if (getUser() != null)
			setPreventives(preventiveBeanService
					.getPreventivesForUserAndBoolean(getUser().getUserName(),
							getUser().getRole().getRole(), true));
		if (getUser() != null || getPreventive() != null) {
			disable = true;
		} else {

			disable = false;
		}
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
