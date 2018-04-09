package al.mili.preventive.db.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.Order;

public class UnitDbService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1007434413256601907L;

	public List<Unit> getAllUnits() {
		Session session = null;
		List<Unit> units = null;
		//Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			//tx=session.beginTransaction();
			Query query = session.getNamedQuery("queryAllUnit");
			units = (List<Unit>) query.list();
			//tx.commit();
		} catch (Exception ex) {
			//if (tx!=null) tx.rollback();
			ex.printStackTrace();
			/*if (session != null && session.isOpen()) {
				session.close();
			}*/

		} finally {
			if (session != null && session.isOpen())  {
				session.close();
			}
		}

		return units;

	}

	public boolean update(int unitId, String unitName) {
		Session session = null;
		boolean result = false;
		Transaction tx=null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Query query = session.getNamedQuery("updateUnit");

			query.setParameter("unitId", unitId);
			query.setParameter("unitName", unitName);

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

	public boolean delete(int unitId) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Query query = session.getNamedQuery("deleteUnit");
			query.setParameter("unitId", unitId);
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

	public void addUnit(String unitName) {
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx=session.beginTransaction();

			Unit unit = new Unit();
			unit.setUnitName(unitName);

			session.save(unit);

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
	}
}
