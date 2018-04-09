package al.mili.preventive.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.service.PreventiveDbService;
import al.mili.preventive.db.service.UnitDbService;
import al.mili.preventive.db.service.UserDbService;

@ManagedBean(name = "unitBeanService", eager = true)
@SessionScoped
public class UnitBeanService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1542817754819284963L;
	private List<Unit> units;
	private UnitDbService unitDbService;

	@PostConstruct
	public void init() {

		unitDbService = new UnitDbService();
		this.units =unitDbService.getAllUnits();
	}

	public List<Unit> getUnits() {
		return units;
	}

	
}
