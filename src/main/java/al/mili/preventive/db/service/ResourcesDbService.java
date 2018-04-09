package al.mili.preventive.db.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import al.mili.preventive.db.model.ResourceType;
import al.mili.preventive.db.model.Resources;

public class ResourcesDbService implements Serializable {

	private static final long serialVersionUID = 8813915056368503333L;

	public List<Resources> getAllResources() {

		Session session = null;
		List<Resources> resources = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session.createQuery("from Resources");

			resources = (List<Resources>) query.list();

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return resources;

	}

	public boolean delete(int resourceId) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("queryDeleteResource");
			query.setParameter("resourceId", resourceId);
			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

			tx.commit();

		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
		
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return result;

	}

	public boolean update(int resourceId, String emri, String description,
			ResourceType resourceType)  {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("queryUpdateResource");

			query.setParameter("resourceId", resourceId);
			query.setParameter("emri", emri);
			query.setParameter("description", description);
			query.setParameter("resourceType", resourceType);

			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

			tx.commit();

		} catch (HibernateException e) {
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

	public void addResource(String emri, String description,
			ResourceType resourceType) {
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Resources resource = new Resources();
			resource.setEmri(emri);
			resource.setDescription(description);
			resource.setResourceType(resourceType);

			session.save(resource);

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
	}

	public List<Resources> getResourcesForType(ResourceType resourceType) {
		Session session = null;
		List<Resources> resources = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session
					.createQuery("from Resources rs where rs.resourceType=:resourceType");
			query.setParameter("resourceType", resourceType);

			resources = (List<Resources>) query.list();

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return resources;

	}

}
