package al.mili.preventive.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import al.mili.preventive.db.model.Customer;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.service.PreventiveDbService;

@ManagedBean(name = "preventiveBeanService", eager = true)
@ViewScoped
public class PreventiveBeanService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8431883891489333033L;
	private List<Preventive> preventives;
	private PreventiveDbService preventiveService;

	@PostConstruct
	public void init() {

		preventiveService = new PreventiveDbService();

	}

	public List<Preventive> getPreventives() {
		return preventives;
	}

	public List<Preventive> getPreventivesForUser(String userName, String role) {
		this.preventives = preventiveService.getPreventivesForUser(userName,
				role);
		return this.preventives;

	}
	
	public List<Preventive> getPreventivesForUserAndBoolean(String userName, String role, boolean hide){
		this.preventives = preventiveService.getPreventivesForUserAndBoolean(userName,
				role, hide);
		return this.preventives;
	}
	
	
	public boolean update(int preventiveId, Customer customer, String email,String protokollNumber,
			 String varianti,String objekti, String location, String status, Date protokollDate,
			String statusiPunimeve, BigDecimal vlera, BigDecimal paradhenie,
			BigDecimal vleraMbetur, boolean kontrata, boolean hide) {

		return preventiveService.update(preventiveId, customer, email, protokollNumber,varianti,objekti,
				location, status, protokollDate, statusiPunimeve, vlera,
				paradhenie, vleraMbetur, kontrata, hide);
	}

	public boolean delete(int preventiveId, boolean hide) {
		return preventiveService.delete(preventiveId, hide);

	}

	public void addPreventive(Customer customer, String email, User user,String protokollNumber,String varianti,
			String objekti, String location, String status,
			String statusiPunimeve, Date protokollDate, BigDecimal vlera,
			BigDecimal paradhenie, BigDecimal vleraMbetur, boolean kontrata, boolean hide) {
		preventiveService.addPreventive(customer, email, user,protokollNumber, varianti,objekti,
				location, status, statusiPunimeve, protokollDate, vlera,
				paradhenie, vleraMbetur, kontrata, hide);

	}

	public void updateVlera(int preventiveId, BigDecimal vlera) {
		preventiveService.updateVlera(preventiveId, vlera);
	}
	
	public void  getHidesPrevetnives(){
		 preventiveService.getHidesPrevetnives();
	}
}
