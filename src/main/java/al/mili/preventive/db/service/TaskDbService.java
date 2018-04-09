package al.mili.preventive.db.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import al.mili.preventive.db.model.Task;

public class TaskDbService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 579459588786257929L;

	public List<Task> getAllTasks(){
		Session session = null;
		List<Task> tasks = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session.createQuery("from Task");
			tasks = (List<Task>) query.list();

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return tasks;

	}

	public void addTask(String name) {

		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Task task = new Task();
			task.setName(name);
			session.save(task);

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
	}

	public boolean updateTask(int taskId, String name) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("queryUpdateTask");

			query.setParameter("taskId", taskId);
			query.setParameter("name", name);

			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

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
		return result;
	}

	public boolean deleteTask(int taskId) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("queryDeleteTask");
			query.setParameter("typeId", taskId);
			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

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
		return result;
	}

}
