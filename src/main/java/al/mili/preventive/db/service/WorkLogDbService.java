package al.mili.preventive.db.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Task;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.WorkLog;

public class WorkLogDbService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7649978584507777247L;
	
	
	public List<WorkLog> getWorkLogForUser(String userName, String role) {
		Session session = null;
		List<WorkLog> worklogs = null;
		
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			
			if (role != null && role.equals("admin")) {
				Query createQuery = session.createQuery("from WorkLog");
				
				return createQuery.list();
			} else {
				Query query = session.getNamedQuery("queryAllWorklog").setParameter("userName", userName);
				return query.list();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}

		return worklogs;

	}

	public void deleteLog(int workLogId) {
		Session session = null;
		
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

		
			
			WorkLog workLog = (WorkLog) session.get(WorkLog.class, workLogId);
			session.delete(workLog);
		

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

	public void updateLog(int workLogId, Date date,  
			List<Task> task, int numberofWorker, int numberOfEquipment,double oretEPunuara
			) {
		Session session = null;
	
		Transaction tx =null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

		
			WorkLog workLog = (WorkLog) session.get(WorkLog.class, workLogId);
			workLog.setDate(date);
			workLog.setNumberOfEquipment(numberOfEquipment);
			workLog.setNumberofWorker(numberofWorker);
			workLog.setOretEPunuara(oretEPunuara);
			workLog.setTask(task);
			
			session.save(workLog);
	
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
	
	

	public void addWorkLog(Date date, Preventive preventive, User user, 
			List<Task> task, int numberofWorker, int numberOfEquipment, double oretEPunuara) {
		Session session = null;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			

			WorkLog workLog = new WorkLog();
	
			workLog.setDate(date);
			workLog.setPreventive(preventive);
			workLog.setUser(user);
			workLog.setTask(task);
			workLog.setNumberofWorker(numberofWorker);
			workLog.setNumberOfEquipment(numberOfEquipment);
			workLog.setOretEPunuara(oretEPunuara);
			

			session.save(workLog);

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
	
	public List<WorkLog> getWorkLogForPrevetnive(int preventiveId) {
		Session session = null;
		List<WorkLog> workLog = null;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query query = session
					.createQuery("from WorkLog wl where wl.preventive.preventiveId=:preventiveId");
			query.setParameter("preventiveId", preventiveId);
			
			workLog = (List<WorkLog>) query.list();
			tx.commit();
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
			
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return workLog;
	}
	
	
}
