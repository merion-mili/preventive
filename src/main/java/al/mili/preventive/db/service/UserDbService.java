package al.mili.preventive.db.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.UsersRole;

public class UserDbService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3858083631133217587L;

	public List<User> getUsers() {
		Session session = null;
		List<User> users = null;
	
		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session.getNamedQuery("queryAllUser");
			users = (List<User>) query.list();

		} catch (Exception ex) {
			ex.printStackTrace();
			/*if (session != null && session.isOpen()) {
				session.close();
			}
*/
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return users;

	}

	public User getUser(String username, String password) {
		Session session = null;
		List<User> users = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session.getNamedQuery("loginControll");
			query.setParameter("username", username);
			query.setParameter("password", password);
			users = (List<User>) query.list();

			if (users != null && users.size() == 1) {
				return users.get(0);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			/*if (session != null && session.isOpen()) {
				session.close();
			}*/

		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}

		return null;
	}
	
	public List<User> getUserPerRole(int role) {
		Session session = null;
		List<User> users = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session.createQuery("from User u where u.role.roleId=:role");
			query.setParameter("role", role);
			
			users = (List<User>) query.list();

			
		} catch (Exception ex) {
			ex.printStackTrace();
			/*if (session != null && session.isOpen()) {
				session.close();
			}*/

		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}

		return users;
	}


	public void addUser(String emri, String mbiemri, String userName,
			String password) {

		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			UsersRole role = (UsersRole) session.get(UsersRole.class, 2);
			if (role != null && role.getRoleId() == 2) {
				User user = new User();
				user.setEmri(emri);
				user.setMbiemri(mbiemri);
				user.setUserName(userName);
				user.setPassword(password);
				user.setRole(role);
				session.save(user);

			}

			tx.commit();

		} catch (Exception ex) {
			if (tx!=null) tx.rollback();
			ex.printStackTrace();
			/*if (session != null && session.isOpen()) {
				session.close();
			}*/
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

	}

	public boolean update(int userId,String emri, String mbiemri,String userName, String password ) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Query query = session.getNamedQuery("queryUpdateUser");
			
			query.setParameter("emri", emri);
			query.setParameter("mbiemri", mbiemri);
			query.setParameter("username", userName);
			query.setParameter("password", password);
			query.setParameter("userId", userId);
			
			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

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
		return result;
	}

	public boolean delete(String userName) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Query query = session.getNamedQuery("queryDeleteUser");
			query.setParameter("userName", userName);
			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

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
		return result;
		

	}

}
