package al.mili.preventive.db.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import al.mili.preventive.db.model.Customer;
import al.mili.preventive.db.model.Order;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.ResourceType;
import al.mili.preventive.db.model.Resources;
import al.mili.preventive.db.model.Task;
import al.mili.preventive.db.model.TaskSheet;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;

public class TaskSheetDbService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7649978584507777247L;
	
	
	public List<TaskSheet> getTaskSheetForUser(String userName, String role) {
		Session session = null;
		List<TaskSheet> preventives = null;
		
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			
			if (role != null && role.equals("admin")) {
				Query createQuery = session.createQuery("from TaskSheet");
				
				return createQuery.list();
			} else {
				Query query = session.getNamedQuery("queryAllTaskSheet").setParameter("userName", userName);
				return query.list();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		
		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}

		return preventives;

	}

	public boolean deleteSheet(int sheetId) {
		Session session = null;
		boolean result = false;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Query query = session.getNamedQuery("queryDeleteSheet");
			query.setParameter("sheetId", sheetId);
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

	public boolean update(int sheetId, Date date, Preventive preventive,
			Resources resources, Date startTime, Date endTime,
			double quantity, Unit unit) {
		Session session = null;
		boolean result = false;
		Transaction tx =null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Query query = session.createQuery("UPDATE TaskSheet ts SET ts.date=:date, ts.preventive=:preventive,ts.resources=:resources, ts.resourceType=:resourceType, ts.startTime=:startTime, ts.endTime=:endTime, ts.duration=:duration, ts.quantity=:quantity, ts.unit=:unit WHERE ts.sheetId=:sheetId");
			ResourceType resourceType = resources.getResourceType();
			if(resourceType.getTypeId()== 2){
			double diff = endTime.getTime()-startTime.getTime();
			double diffHours = diff / (60 * 60 * 1000);
			query.setParameter("sheetId", sheetId);
			query.setParameter("date", date);
			query.setParameter("preventive", preventive);
			query.setParameter("resources", resources);
			query.setParameter("resourceType", resourceType);
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
			query.setParameter("duration", diffHours);
			query.setParameter("quantity", quantity);
			query.setParameter("unit", unit);
			}else{
				
				query.setParameter("sheetId", sheetId);
				query.setParameter("date", date);
				query.setParameter("preventive", preventive);
				query.setParameter("resources", resources);
				query.setParameter("resourceType", resourceType);
				query.setParameter("startTime", null);
				query.setParameter("endTime", null);
				query.setParameter("duration", 0.0);
				query.setParameter("quantity", quantity);
				query.setParameter("unit", unit);
			}
			
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
	
	

	public void addTaskSheet(Date date, Preventive preventive, User user, Resources resources, 
			Date startTime,
			Date endTime, double quantity,
			Unit unit) {
		Session session = null;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			

			TaskSheet taskSheet = new TaskSheet();
			ResourceType resourceType = resources.getResourceType();
			if(resourceType.getTypeId()==2){
				
				double diff = endTime.getTime()- startTime.getTime();
				double diffHours = diff / (60 * 60 * 1000);
				taskSheet.setDate(date);
				taskSheet.setPreventive(preventive);
				taskSheet.setUser(user);
				taskSheet.setResources(resources);
				taskSheet.setResourceType(resourceType);
				
				taskSheet.setStartTime(startTime);
				taskSheet.setEndTime(endTime);
				taskSheet.setDuration(diffHours);
				taskSheet.setQuantity(0);
				taskSheet.setUnit(unit);
			}else{
				taskSheet.setDate(date);
				taskSheet.setPreventive(preventive);
				taskSheet.setUser(user);
				taskSheet.setResources(resources);
				taskSheet.setResourceType(resourceType);
				taskSheet.setStartTime(null);
				taskSheet.setEndTime(null);
				taskSheet.setDuration(0);
				taskSheet.setQuantity(quantity);
				taskSheet.setUnit(unit);
			}
			
			
			
			

			session.save(taskSheet);

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
	
	public List<TaskSheet> getTaskSheetForPrevetnive(int preventiveId) {
		Session session = null;
		List<TaskSheet> taskSheet = null;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query query = session
					.createQuery("from TaskSheet ts where ts.preventive.preventiveId=:preventiveId");
			query.setParameter("preventiveId", preventiveId);
			
			taskSheet = (List<TaskSheet>) query.list();
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

		return taskSheet;
	}
	
	
}
