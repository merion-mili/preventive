package al.mili.preventive.client;

import java.io.Serializable;
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

@ManagedBean(name = "customerDetailController")
@ViewScoped
public class CustomerDetailController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4665999359138568380L;
	private List<Customer> customers;
	private List<CustomerBean> customerBeans;
	private List<CustomerBean> filteredCustomers;

	private CustomerBean customerBean;
	private CustomerBean selectedCustomer;
	private boolean disable;

	@ManagedProperty("#{customerBeanService}")
	private CustomerBeanService customerBeanService;

	@PostConstruct
	public void init() {

		this.customerBean = new CustomerBean();

		customers = customerBeanService.getCustomers();
		this.customerBeans = convertCustomerList(customers).stream()
				.sorted(Comparator.comparing(CustomerBean::getCustomerId))
				.collect(Collectors.toList());
	}

	public List<CustomerBean> getCustomerBeans() {

		return this.customerBeans;
	}

	public void setCustomerBeans(List<CustomerBean> customerBeans) {
		this.customerBeans = customerBeans;
	}

	public CustomerBean getCustomerBean() {
		return customerBean;
	}

	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public CustomerBean getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(CustomerBean selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public List<CustomerBean> getFilteredCustomers() {
		return filteredCustomers;
	}

	public void setFilteredCustomers(List<CustomerBean> filteredCustomers) {
		this.filteredCustomers = filteredCustomers;
	}

	public CustomerBeanService getCustomerBeanService() {
		return customerBeanService;
	}

	public void setCustomerBeanService(CustomerBeanService customerBeanService) {
		this.customerBeanService = customerBeanService;
	}

	private List<CustomerBean> convertCustomerList(List<Customer> list) {
		List<CustomerBean> listCustomerBean = new ArrayList<CustomerBean>();
		for (Customer customer : list) {
			CustomerBean customerBean = new CustomerBean();
			customerBean.setCustomerId(customer.getCustomerId());
			customerBean.setEmriPlote(customer.getEmriPlote());
			customerBean.setEmail(customer.getEmail());
			customerBean.setTelefon(customer.getTelefon());
			customerBean.setNipt(customer.getNipt());
			listCustomerBean.add(customerBean);
		}
		return listCustomerBean;
	}

	public void addAction() throws HibernateException,
			ConstraintViolationException {
		try {
			customerBeanService.addCustomer(customerBean.getEmriPlote(),
					customerBean.getEmail(), customerBean.getTelefon(),
					customerBean.getNipt());

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Customer " + customerBean.getEmriPlote()
							+ " has benn saved", null);
			FacesContext.getCurrentInstance().addMessage(null, message);

		} catch (ConstraintViolationException e) {
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

	public void deleteCustomer() {

		customerBeanService.delete(selectedCustomer.getCustomerId());

		FacesMessage msg = new FacesMessage("Customer deleted", "Customer  "
				+ selectedCustomer.getEmriPlote() + " has been deleted");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) throws HibernateException,
			ConstraintViolationException {
		try {

			Object oldValue = event.getOldValue();
			Object newValue = event.getNewValue();

			if (newValue != null && !newValue.equals(oldValue)) {
				DataTable s = (DataTable) event.getSource();
				CustomerBean customer = (CustomerBean) s.getRowData();
				customerBeanService.update(customer.getCustomerId(),
						customer.getEmriPlote(), customer.getEmail(),
						customer.getTelefon(), customer.getNipt());

				FacesMessage msg = new FacesMessage("Klienti u Editua",
						"Klienti " + customer.getEmriPlote() + " u editua");
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}

			if (customerBeans.size() > 0) {
				disable = false;
			} else {

				disable = true;

			}
		} catch (ConstraintViolationException e) {
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
		this.customerBean.setEmriPlote(null);
		this.customerBean.setEmail(null);
		this.customerBean.setTelefon(null);
		this.customerBean.setNipt(null);

		RequestContext.getCurrentInstance().reset(":customerForm:addCustomer");
	}

}
