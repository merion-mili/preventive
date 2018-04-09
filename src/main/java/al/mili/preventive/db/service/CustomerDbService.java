package al.mili.preventive.db.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import al.mili.preventive.db.model.Customer;

public class CustomerDbService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5646814097926150046L;

	public List<Customer> getCustomers() {
		Session session = null;
		List<Customer> customers = null;
		
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
		

			Query query = session.getNamedQuery("queryCustomer");
			customers = (List<Customer>) query.list();
			
			
		} catch (Exception ex) {
		
			ex.printStackTrace();
			
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}

		return customers;

	}

	public void addCustomer(String emriPlote, String email, String telefon,
			String nipt) {

		Session session = null;
		Transaction tx=null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Customer customer = new Customer();
			customer.setEmriPlote(emriPlote);
			;
			customer.setEmail(email);
			customer.setTelefon(telefon);
			customer.setNipt(nipt);

			session.save(customer);

			tx.commit();

		}  catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");

		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}
	}

	public boolean update(int customerId, String emriPlote, String email,
			String telefon, String nipt) {
		Session session = null;
		boolean result = false;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Query query = session.getNamedQuery("updateCustomer");

			query.setParameter("customerId", customerId);
			query.setParameter("emriPlote", emriPlote);
			;
			query.setParameter("email", email);
			query.setParameter("telefon", telefon);
			query.setParameter("nipt", nipt);

			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

			tx.commit();

		}  catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();

			}
		}
		return result;
	}

	public boolean delete(int customerId) {
		Session session = null;
		boolean result = false;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Query query = session.getNamedQuery("deleteCustomer");
			query.setParameter("customerId", customerId);
			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

			tx.commit();

		} catch (Exception ex) {
			if (tx!=null) tx.rollback();
			ex.printStackTrace();
			
		} finally {
			if (session != null && session.isOpen()){
				session.close();

			}
		}
		return result;

	}

}
