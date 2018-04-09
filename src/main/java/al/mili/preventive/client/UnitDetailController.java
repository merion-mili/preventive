package al.mili.preventive.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import al.mili.preventive.client.exceptions.SelectLevelListener;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.service.UnitDbService;
import al.mili.preventive.db.service.UserDbService;

@ManagedBean(name = "unitDetailController")
@RequestScoped
public class UnitDetailController implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7279874573551481896L;


	private List<UnitBean> unitBeans;

	
	private UnitDbService unitDbService;

	private UnitBean unitBean;

	public UnitDetailController() {

		this.unitBean = new UnitBean();
		this.unitDbService = new UnitDbService();

	}

	public List<UnitBean> getUnitBeans() {
		unitBeans = convertUnitList(unitDbService.getAllUnits()).stream()
				.sorted(Comparator.comparing(UnitBean::getUnitId))
				.collect(Collectors.toList());
		System.out.println("Hello +++++++");
		return unitBeans;
	}

	public void setUnitBeans(List<UnitBean> unitBeans) {
		this.unitBeans = unitBeans;
	}

	public UnitDbService getUnitDbService() {
		return unitDbService;
	}

	public void setUnitDbService(UnitDbService unitDbService) {
		this.unitDbService = unitDbService;
	}

	
	public UnitBean getUnitBean() {
		return unitBean;
	}

	public void setUnitBean(UnitBean unitBean) {
		this.unitBean = unitBean;
	}

	public String saveSuccess(UnitBean unitBean) {
		
		unitDbService.update(unitBean.getUnitId(), unitBean.getUnitName());
		unitBeans = convertUnitList(unitDbService.getAllUnits()).stream()
				.sorted(Comparator.comparing(UnitBean::getUnitId))
				.collect(Collectors.toList());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Unit " + unitBean.getUnitName() + " has benn saved", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

		return null;
	}

	public String delete(UnitBean unitBean) {
		unitDbService.delete(unitBean.getUnitId());
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Unit " + unitBean.getUnitName() + " has benn deleted", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

		return null;
	}

	public String addUnit() {

		unitDbService.addUnit(unitBean.getUnitName());
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Unit " + unitBean.getUnitName() + " has benn added", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

		return null;
	}

	public String saveFailure(Unit unit) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ELContext elContext = fc.getELContext();

		SelectLevelListener selectLevelListener;
		try {
			selectLevelListener = (SelectLevelListener) elContext
					.getELResolver().getValue(elContext, null,
							"selectLevelListener");
			selectLevelListener.setErrorOccured(true);
		} catch (RuntimeException e) {
			throw new FacesException(e.getMessage(), e);
		}

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Unit " + unit.getUnitName() + " could not be saved", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

		return null;
	}

	

	private List<UnitBean> convertUnitList(List<Unit> list) {
		List<UnitBean> listUnitBean = new ArrayList<UnitBean>();
		for (Unit unit : list) {
			UnitBean unitBean = new UnitBean();
			unitBean.setUnitId(unit.getUnitId());
			unitBean.setUnitName(unit.getUnitName());
			listUnitBean.add(unitBean);
		}
		return listUnitBean;
	}

}
