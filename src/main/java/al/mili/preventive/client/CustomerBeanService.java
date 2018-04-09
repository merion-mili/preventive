package al.mili.preventive.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import al.mili.preventive.db.model.Customer;
import al.mili.preventive.db.service.CustomerDbService;

@ManagedBean(name = "customerBeanService", eager = true)
@ViewScoped
public class CustomerBeanService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 167047219448056781L;
	private List<Customer> customers;
	private CustomerDbService customerDbService;

	@PostConstruct
	public void init() {

		this.customerDbService = new CustomerDbService();
		this.customers = this.customerDbService.getCustomers();
	}

	public List<Customer> getCustomers() {
		return this.customers;

	}

	public void addCustomer(String emriPlote, String email, String telefon,
			String nipt) {
		try {
			customerDbService.addCustomer(emriPlote, email, telefon, nipt);
		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");
		} catch (HibernateException e) {
			throw e;
		}
	}

	public boolean update(int customerId, String emriPlote, String email,
			String telefon, String nipt) {
		try {
			return customerDbService.update(customerId, emriPlote, email,
					telefon, nipt);

		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");
		} catch (HibernateException e) {
			throw e;
		}
	}

	public boolean delete(int customerId) {
		return customerDbService.delete(customerId);
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

}
