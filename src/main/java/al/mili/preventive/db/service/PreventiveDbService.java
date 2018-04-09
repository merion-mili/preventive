package al.mili.preventive.db.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import al.mili.preventive.db.model.Customer;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.WorkLog;

public class PreventiveDbService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6478857948952875241L;

	public List<Preventive> getPreventives() {
		Session session = null;
		List<Preventive> preventives = null;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();
			Query createQuery = session.createQuery("from Preventive");

			preventives = createQuery.list();
			tx.commit();
		} catch (Exception ex) {
			if (tx!=null) tx.rollback();
			ex.printStackTrace();
			/*if (session != null && session.isOpen()) {
				session.close();

			}*/
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}

		return preventives;

	}

	public List<Preventive> getPreventivesForUserAndBoolean(String userName, String role, boolean hide) {
		Session session = null;
		List<Preventive> preventives = null;
		
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			
			if (role != null && role.equals("admin")) {
				Query createQuery = session.createQuery("from Preventive p where p.hide=:hide");
				createQuery.setParameter("hide", hide);
				return createQuery.list();
			} else {
				Query namedQuery = session.getNamedQuery("queryByUserName");
				namedQuery.setParameter("userName", userName);
				namedQuery.setParameter("hide", hide);
				return namedQuery.list();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		/*	if (session != null && session.isOpen()) {
				session.close();

			}*/
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}

		return preventives;

	}
	
	
	
	
	public List<Preventive> getPreventivesForUser(String userName, String role) {
		Session session = null;
		List<Preventive> preventives = null;
		
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			
			if (role != null && role.equals("admin")) {
				Query createQuery = session.createQuery("from Preventive");
				
				return createQuery.list();
			} else {
				Query query = session.createQuery("from Preventive p where p.user.userName=:userName");
				query.setParameter("userName", userName);
				return query.list();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		/*	if (session != null && session.isOpen()) {
				session.close();

			}*/
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}

		return preventives;

	}

	public boolean delete(int preventiveId, boolean hide) {
		Session session = null;
		boolean result = false;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Query query = session.getNamedQuery("queryDeletePreventive");
			query.setParameter("preventiveId", preventiveId);
			query.setParameter("hide", hide);
			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

			tx.commit();

		} catch (Exception ex) {
			if (tx!=null) tx.rollback();
			ex.printStackTrace();
			
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}
		return result;

	}

	public boolean update(int preventiveId, Customer customer, String email,String protokollNumber,String varianti,
			String objekti, String location, String status,
			Date date, String statusiPunimeve, BigDecimal vlera, BigDecimal paradhenie, 
			BigDecimal vleraMbetur, boolean kontrata, boolean hide) {
		Session session = null;
		boolean result = false;
		Transaction tx =null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Query query = session.getNamedQuery("queryUpdatePreventive");
			query.setParameter("preventiveId", preventiveId);
			query.setParameter("emri", customer);
			query.setParameter("email", email);
			query.setParameter("protokollNumber", protokollNumber);
			query.setParameter("varianti", varianti);
			query.setParameter("objekti", objekti);
			query.setParameter("location", location);
			query.setParameter("status", status);
			query.setParameter("punimet", statusiPunimeve);
			query.setParameter("date", date);
			query.setParameter("vlera", vlera);
			query.setParameter("paradhenie", paradhenie);
			query.setParameter("mbetje", vleraMbetur);
			query.setParameter("kontrata", kontrata);
			query.setParameter("hide", hide);
			
			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

			tx.commit();

		} catch (Exception ex) {
			if (tx!=null) tx.rollback();
			ex.printStackTrace();
			
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}
		return result;

	}
	
	public void updateVlera(int preventiveId,  BigDecimal vlera) {
		Session session = null;
		boolean result = false;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

		
			Preventive preventive = (Preventive) session.get(Preventive.class, preventiveId);
			BigDecimal paradhenie = preventive.getParadhenie();
			BigDecimal vleraMbetur=BigDecimal.ZERO;
			if(paradhenie!=null){
				vleraMbetur = vlera.subtract(paradhenie);
			}else{
			 vleraMbetur = vlera.subtract(BigDecimal.ZERO);
			}
			preventive.setVlera(vlera);
			preventive.setVleraMbetur(vleraMbetur);
		
			session.save(preventive);
	

			tx.commit();

		} catch (Exception ex) {
			if (tx!=null) tx.rollback();
			ex.printStackTrace();
			
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}
		

	}

	
	public void getHidesPrevetnives() {
		Session session = null;
		boolean result = false;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			String hql  = "update PREVENTIVE set HIDE = 1";
			Query query = session.createSQLQuery(hql);
			query.executeUpdate();
			
			tx.commit();

		} catch (Exception ex) {
			if (tx!=null) tx.rollback();
			ex.printStackTrace();
			
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}
		

	}

	public void addPreventive(Customer customer, String email, User user,String protokollNumber,String varianti,
			String objekti, String location, String status,
			String statusiPunimeve, Date date, BigDecimal vlera, BigDecimal paradhenie, 
			BigDecimal vleraMbetur, boolean kontrata, boolean hide) {
		Session session = null;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

		

			Preventive preventive = new Preventive();
			preventive.setCustomer(customer);
			preventive.setEmail(email);
			preventive.setUser(user);
			preventive.setProtokollNumber(protokollNumber);
			preventive.setVarianti(varianti);
			preventive.setObjekti(objekti);
			preventive.setLocation(location);
			preventive.setStatus(status);
			preventive.setStatusiPunimeve(statusiPunimeve);
			preventive.setProtokollDate(date);
			if(vlera!=null){
				preventive.setVlera(vlera);
			}else{
				preventive.setVlera(BigDecimal.ZERO);
			}
			if(paradhenie!=null){
				preventive.setParadhenie(paradhenie);
			}else{
				preventive.setParadhenie(BigDecimal.ZERO);
			}
			
			
			preventive.setVleraMbetur(vleraMbetur);
			preventive.setKontrata(kontrata);
			preventive.setHide(hide);

			session.save(preventive);

			tx.commit();

		} catch (Exception ex) {
			if (tx!=null) tx.rollback();
			ex.printStackTrace();
			
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}
	}
	
	

}
