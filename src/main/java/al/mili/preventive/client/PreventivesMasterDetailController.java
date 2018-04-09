package al.mili.preventive.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import al.mili.preventive.db.model.Customer;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.User;

@ManagedBean(name = "preventivesMasterDetailController")
@ViewScoped
public class PreventivesMasterDetailController implements Serializable {



	private static final long serialVersionUID = 2653749387411642630L;
	private List<Customer> customers;
	private List<PreventiveBean> filteredPreventives;
	private List<PreventiveBean> preventiveBeans;
	private List<Preventive> preventives;
	private PreventiveBean selectedPreventive;
	
	
	
	
	
	private PreventiveBean preventiveBean;

	@ManagedProperty("#{customerBeanService}")
	private CustomerBeanService customerBeanService;

	@ManagedProperty("#{preventiveBeanService}")
	private PreventiveBeanService preventiveBeanService;

	@PostConstruct
	public void init() {
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		 preventives = preventiveBeanService.getPreventivesForUser(
				user.getUserName(), user.getRole().getRole());
	
		this.preventiveBeans = convertPreventivesList(preventives)
				.stream()
				.sorted(Comparator.comparing(PreventiveBean::getProtokollDate).reversed())
				.collect(Collectors.toList());
		this.customers = customerBeanService.getCustomers();
		

		this.preventiveBean = new PreventiveBean();

		
	}

	

	public List<Preventive> getPreventives() {
		return preventives;
	}



	public void setPreventives(List<Preventive> preventives) {
		this.preventives = preventives;
	}



	public List<PreventiveBean> getPreventiveBeans() {
		

		return this.preventiveBeans;
	}
	
	
	public void setPreventiveBeans(List<PreventiveBean> preventiveBeans) {
		this.preventiveBeans = preventiveBeans;
	}

	public PreventiveBean getPreventiveBean() {
		return preventiveBean;
	}

	public void setPreventiveBean(PreventiveBean preventiveBean) {
		this.preventiveBean = preventiveBean;
	}
	
	
	public void onCellEdit(CellEditEvent event) {
		 
	        Object oldValue = event.getOldValue();
	        Object newValue = event.getNewValue();
	         
	        if(newValue != null && !newValue.equals(oldValue)) {
	        	 DataTable s = (DataTable) event.getSource();
	             PreventiveBean preventiveBean = (PreventiveBean) s.getRowData();
	             preventiveBeanService.update(preventiveBean.getPreventiveId(),
	     				preventiveBean.getCustomer(), preventiveBean.getEmail(),
	     				preventiveBean.getProtokollNumber(),preventiveBean.getVarianti(),
	     				preventiveBean.getObjekti(), preventiveBean.getLocation(),
	     				preventiveBean.getStatus(), preventiveBean.getProtokollDate(),
	     				preventiveBean.getStatusiPunimeve(), preventiveBean.getVlera(),
	     				preventiveBean.getParadhenie(),
	     				preventiveBean.getVleraMbetur(), preventiveBean.isKontrata(),
	     				preventiveBean.isHide());
	             
	            
	             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
	     				"Preventive " + preventiveBean.getObjekti()
	     						+ " has benn updated", null);
	     		FacesContext.getCurrentInstance().addMessage(null, message);
	        }
	        
	        
	    }
	


	public void deletePreventive() {
		
		preventiveBeanService.delete(selectedPreventive.getPreventiveId(),false);
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Preventive " + selectedPreventive.getObjekti()
						+ " has benn deleted", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}
	
		
	
	public void addPreventive() throws HibernateException,
	ConstraintViolationException{
		try{
			User user = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("user");
			preventiveBeanService.addPreventive(preventiveBean.getCustomer(),
					preventiveBean.getEmail(), user, preventiveBean.getProtokollNumber(),
					preventiveBean.getVarianti(), preventiveBean.getObjekti(),
					preventiveBean.getLocation(), preventiveBean.getStatus(),
					preventiveBean.getStatusiPunimeve(),preventiveBean.getProtokollDate(), 
					preventiveBean.getVlera(), preventiveBean.getParadhenie(),
					preventiveBean.getVleraMbetur(), preventiveBean.isKontrata(), true);

			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Preventive " + preventiveBean.getObjekti() + " has benn saved",
					null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch (ConstraintViolationException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Gabim ne entry:Objekti Ekziston.", "Duplicated Entry."));
		} catch (HibernateException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERROR! Access Database",
					"Failure to include data"));

		}
		

		
	}

	public void initDialog() {
	    this.preventiveBean.setEmail("");
	    this.preventiveBean.setObjekti("");
	    this.preventiveBean.setLocation("");
	    this.preventiveBean.setStatus("");
	    this.preventiveBean.setStatusiPunimeve("");
	    this.preventiveBean.setVlera(BigDecimal.ZERO);
	    this.preventiveBean.setParadhenie(BigDecimal.ZERO);
	    
	 
	    RequestContext.getCurrentInstance().reset(":orderForm:addPreventive");  
	}
	

	private List<PreventiveBean> convertPreventivesList(List<Preventive> list) {
		List<PreventiveBean> listPreventiveBean = new ArrayList<PreventiveBean>();
		for (Preventive preventive : list) {
			PreventiveBean preventiveBean = new PreventiveBean();
			preventiveBean.setPreventiveId(preventive.getPreventiveId());
			preventiveBean.setCustomer(preventive.getCustomer());
			preventiveBean.setProtokollNumber(preventive.getProtokollNumber());
			preventiveBean.setVarianti(preventive.getVarianti());
			preventiveBean.setEmail(preventive.getEmail());
			preventiveBean.setObjekti(preventive.getObjekti());
			preventiveBean.setLocation(preventive.getLocation());
			preventiveBean.setStatus(preventive.getStatus());
			preventiveBean.setStatusiPunimeve(preventive.getStatusiPunimeve());
			preventiveBean.setProtokollDate(preventive.getProtokollDate());
			preventiveBean.setVlera(preventive.getVlera());
			preventiveBean.setParadhenie(preventive.getParadhenie());
			preventiveBean.setVleraMbetur(preventive.getVleraMbetur());
			preventiveBean.setKontrata(preventive.isKontrata());
			preventiveBean.setHide(preventive.isHide());

			listPreventiveBean.add(preventiveBean);
		}
		return listPreventiveBean;
	}

	public CustomerBeanService getCustomerBeanService() {
		return customerBeanService;
	}

	public void setCustomerBeanService(CustomerBeanService customerBeanService) {
		this.customerBeanService = customerBeanService;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public PreventiveBeanService getPreventiveBeanService() {
		return preventiveBeanService;
	}

	public void setPreventiveBeanService(
			PreventiveBeanService preventiveBeanService) {
		this.preventiveBeanService = preventiveBeanService;
	}

	public PreventiveBean getSelectedPreventive() {
		return this.selectedPreventive;
	}

	public void setSelectedPreventive(PreventiveBean selectedPreventive) {
		this.selectedPreventive = selectedPreventive;
	}

	public List<PreventiveBean> getFilteredPreventives() {
		return filteredPreventives;
	}

	public void setFilteredPreventives(List<PreventiveBean> filteredPreventives) {
		this.filteredPreventives = filteredPreventives;
	}



}
