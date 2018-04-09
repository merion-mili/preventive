package al.mili.preventive.db.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import al.mili.preventive.db.model.Customer;
import al.mili.preventive.db.model.ResourceType;

public class ResourceTypeDbService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -702440913244985639L;

	public List<ResourceType> getResourceType() {
		Session session = null;
		List<ResourceType> types = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session.createQuery("from ResourceType");
			types = (List<ResourceType>) query.list();

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return types;

	}

	public void addResourceType(String emri) {

		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			ResourceType type = new ResourceType();
			type.setEmri(emri);
			session.save(type);

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

	public boolean update(int typeId, String emri) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("queryUpdateResourceType");

			query.setParameter("typeId", typeId);
			query.setParameter("emri", emri);

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

	public boolean delete(int typeId) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("queryDeleteResourceType");
			query.setParameter("typeId", typeId);
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
