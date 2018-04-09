package al.mili.preventive.db.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import al.mili.preventive.db.model.Artikull;
import al.mili.preventive.db.model.KompoziteArtikel;

public class ArtikullDbService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2179784121133936544L;

	public List<Artikull> getAllArtikull() {
		Session session = null;
		List<Artikull> artikullList = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session.getNamedQuery("queryAllArtikull");

			artikullList = (List<Artikull>) query.list();

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return artikullList;

	}

	public List<Artikull> getAllArtikullByType(String type) {
		Session session = null;
		List<Artikull> artikullList = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session.getNamedQuery("queryAllArtikullByType");
			query.setParameter("type", type);

			artikullList = (List<Artikull>) query.list();

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return artikullList;

	}

	public void delete(int artikullId) {
		Session session = null;
		//boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

		/*	Query query = session.getNamedQuery("queryDeleteArtikull");
			query.setParameter("artikullId", artikullId);
			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}*/
			
			Artikull artikull = (Artikull) session.get(Artikull.class,
					artikullId);
			session.delete(artikull);

			tx.commit();

		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			throw new ConstraintViolationException(
					"Delete nuk ekzekutohet.Artikulli lidhet me Preventive", null,
					"Objekti eshte ne Preventive.");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		//return result;

	}

	public void update(int artikullId, String name, String description,
			List<KompoziteArtikel> kompozimet, String type) {
		Session session = null;
		//boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
				
			Artikull artikull = (Artikull) session.get(Artikull.class,
					artikullId);
			artikull.setName(name);
			artikull.setDescription(description);
			artikull.setKompozimet(kompozimet);
			//session.evict(artikull);
			session.merge(artikull);
		

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
		//return result;

	}

	public void addArtikull(String name, String description,
			List<KompoziteArtikel> kompozimet, String type) {
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Artikull artikull = new Artikull();
			artikull.setName(name);
			artikull.setDescription(description);
			artikull.setKompozimet(kompozimet);
			artikull.setType(type);

			session.save(artikull);

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
	}

	public void addKompozim(String name, String description,
			List<KompoziteArtikel> kompozimet, String type) {
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Artikull kompozim = new Artikull();
			kompozim.setName(name);
			kompozim.setDescription(description);
			kompozim.setKompozimet(kompozimet);
			kompozim.setType(type);

			session.save(kompozim);

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
	}

	/*public void addArtikullInKompozim(Artikull artikull, double quantity) {

		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			KompoziteArtikel kompozim = new KompoziteArtikel();
			kompozim.setArtikull(artikull);
			kompozim.setQuantity(quantity);

			session.save(kompozim);

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

	}
*/
	public Artikull updateArtikull(Artikull artikull) {

		final Session session = HibernateUtilities.getSessionFactory()
				.openSession();
		if (session.contains(artikull)) {
			session.update(artikull);
		} else {
			artikull = (Artikull) session.merge(artikull);
		}
		return artikull;
	}

	public Artikull getArtikull(int artikullId) {
		Session session = null;
		Artikull artikull = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session
					.createQuery("from Artikull a WHERE artikull.artikullId=:artikullId");
			query.setParameter("artikullId", artikullId);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return artikull;

	}

	public KompoziteArtikel getKompoArtikull(int kompoid) {
		Session session = null;
		KompoziteArtikel artikull = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session
					.createQuery("from KompoziteArtikel k WHERE k.kompoid=:kompoid");
			query.setParameter("kompoid", kompoid);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return artikull;

	}

	/*public KompoziteArtikel updateKompoziteArtikull(KompoziteArtikel artikull) {
		final Session session = HibernateUtilities.getSessionFactory()
				.openSession();
		if (session.contains(artikull)) {
			session.update(artikull);
		} else {
			artikull = (KompoziteArtikel) session.merge(artikull);
		}
		return artikull;
	}*/
	
	public List<KompoziteArtikel> getArtikujtEKompozuar(int artikullId) {
		Session session = null;
		List<KompoziteArtikel> artikujtEKompozuar = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();

			Query query = session.createQuery("select a.kompozimet from Artikull a WHERE a.artikullId=:artikullId");
			query.setParameter("artikullId", artikullId);
			//query.setParameter("type", type);
			artikujtEKompozuar = (List<KompoziteArtikel>) query.list();

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return artikujtEKompozuar;
	}


}
