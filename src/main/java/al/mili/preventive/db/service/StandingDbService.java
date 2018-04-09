package al.mili.preventive.db.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import al.mili.preventive.db.model.Order;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.ResourceType;
import al.mili.preventive.db.model.Resources;
import al.mili.preventive.db.model.TaskSheet;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.WorkLog;

public class StandingDbService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4041271373175965655L;

	public List<TaskSheet> getTaskSheetPerPreventiveUser(Preventive preventive,
			User user) {

		Session session = null;
		List<TaskSheet> sheetList = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session
					.createQuery("from TaskSheet ts where ts.preventive=:preventive and ts.user=:user");

			query.setParameter("preventive", preventive);
			query.setParameter("user", user);

			sheetList = query.list();

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return sheetList;

	}

	public List<TaskSheet> getAllTaskSheetForResource(
			ResourceType resourceType, Resources resources) {
		Session session = null;
		List<TaskSheet> sheetList = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session
					.createQuery("from TaskSheet ts where ts.resourceType=:resourceType and ts.resources=:resources");
			query.setParameter("resourceType", resourceType);
			query.setParameter("resources", resources);

			sheetList = query.list();

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return sheetList;

	}

	public List<Preventive> getAllPrevetnivesElement() {
		Session session = null;
		List<Preventive> preventives = null;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query createQuery = session
					.createQuery("select preventive, order, taskSheet from Preventive as preventive,Order as order, TaskSheet as taskSheet"
							+ "where order.preventive.preventiveId=preventive.prevetniveId and taskSheet.preventive.preventiveId=prevetnive.prevetniveId");

			preventives = createQuery.list();
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

		return preventives;

	}

	public List<Order> getOrders(boolean flag) {
		Session session = null;
		List<Order> orderList = null;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Order o where o.flag=:flag");
			query.setParameter("flag", flag);

			orderList = (List<Order>) query.list();
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

		return orderList;

	}

	public List<TaskSheet> getTaskSheet() {
		Session session = null;
		List<TaskSheet> preventives = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query createQuery = session.createQuery("from TaskSheet");

			return createQuery.list();

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return preventives;

	}
	
	public List<WorkLog> getWorkLogs() {
		Session session = null;
		List<WorkLog> workLogs = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query createQuery = session.createQuery("from WorkLog");

			return createQuery.list();

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return workLogs;

	}

}
