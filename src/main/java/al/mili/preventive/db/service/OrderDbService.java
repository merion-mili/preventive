package al.mili.preventive.db.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import al.mili.preventive.db.model.Artikull;
import al.mili.preventive.db.model.Order;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Unit;

public class OrderDbService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1826948669833713165L;

	public List<Order> getOrders(int preventiveId) {
		Session session = null;
		List<Order> orderList = null;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("queryAllArtikleForPreventive");
			query.setParameter("preventiveId", preventiveId);

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

	public List<Order> getOrderForPreventiveId(int preventiveId, boolean flag) {
		Session session = null;
		List<Order> orderList = null;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query query = session
					.createQuery("from Order o where o.preventive.preventiveId=:preventiveId and o.flag=:flag");
			query.setParameter("preventiveId", preventiveId);
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

	public List<Artikull> getArtikullForPreventiveId(int preventiveId,
			boolean flag, boolean prevortrack) {
		Session session = null;
		List<Artikull> orderList = null;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query query = session
					.createQuery("select o.artikull from Order o where o.preventive.preventiveId=:preventiveId and o.flag=:flag and o.prevortrack=:prevortrack");
			query.setParameter("preventiveId", preventiveId);
			query.setParameter("flag", flag);
			query.setParameter("prevortrack", prevortrack);
			orderList = (List<Artikull>) query.list();

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

	public List<Order> getOrderForPreventiveIdBoolean(int preventiveId,
			boolean flag, boolean prevortrack) {
		Session session = null;
		List<Order> orderList = null;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query query = session
					.createQuery("from Order o where o.preventive.preventiveId=:preventiveId and o.flag=:flag and o.prevortrack=:prevortrack");
			query.setParameter("preventiveId", preventiveId);
			query.setParameter("flag", flag);
			query.setParameter("prevortrack", prevortrack);
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

	public boolean delete(int orderId, boolean flag) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("queryDeleteOrder");
			query.setParameter("orderId", orderId);
			query.setParameter("flag", flag);
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

	public boolean update(int orderId, Date trackingDate, Artikull artikull,
			Unit unit, BigDecimal quantity, BigDecimal priceUnit,
			BigDecimal priceQuantity, BigDecimal percent, BigDecimal discount,
			BigDecimal gjendje, BigDecimal totalAfter, boolean flag,
			boolean prevortrack) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session.getNamedQuery("queryUpdateOrder");

			query.setParameter("orderId", orderId);
			query.setParameter("trackingDate", trackingDate);
			query.setParameter("artikull", artikull);
			query.setParameter("unit", unit);
			query.setParameter("quantity", quantity);
			query.setParameter("price", priceUnit);
			query.setParameter("totalPrice", priceQuantity);
			query.setParameter("percent", percent);
			query.setParameter("discount", discount);
			query.setParameter("gjendje", gjendje);
			query.setParameter("totalAfter", totalAfter);
			query.setParameter("flag", flag);
			query.setParameter("prevortrack", prevortrack);
			int row = query.executeUpdate();
			if (row == 1) {
				result = true;
			}

			tx.commit();

		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
			/*
			 * if (session != null && session.isOpen()) { session.close();
			 * 
			 * }
			 */
		} finally {
			if (session != null && session.isOpen()) {
				session.close();

			}
		}
		return result;

	}

	public void addOrder(Date date, Artikull artikull, Unit unit,
			BigDecimal quantity, BigDecimal trackQuantity, BigDecimal price,
			BigDecimal totalPriceQuantity, Preventive preventive,
			BigDecimal percent, BigDecimal discount, boolean flag,
			boolean prevortrack) {
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Order order = new Order();
			order.setTrackingDate(date);
			order.setArtikull(artikull);
			order.setUnit(unit);
			order.setQuantity(quantity);
			order.setTrackQuantity(trackQuantity);
			order.setPrice(price);
			order.setTotalPrice(totalPriceQuantity);
			order.setPreventive(preventive);
			order.setPercent(percent);
			order.setDiscount(discount);
			order.setGjendje(quantity);
			order.setFlag(flag);
			order.setPrevortrack(prevortrack);

			session.save(order);

			tx.commit();

		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
			/*
			 * if (session != null && session.isOpen()) { session.close(); }
			 */
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public void addTracker(Date trackingDate, Artikull artikull, Unit unit,
			BigDecimal trackQuantity, BigDecimal price,
			BigDecimal totalPriceQuantity, Preventive preventive,
			BigDecimal percent, BigDecimal discount, boolean flag,
			boolean prevortrack) {
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Order o where o.preventive.preventiveId=:preventiveId and o.flag=:flag and o.prevortrack=:prevortrack");
			int preventiveId = preventive.getPreventiveId();
			query.setParameter("preventiveId", preventiveId);
			query.setParameter("flag", true);
			query.setParameter("prevortrack", false);
			List<Order> orderList = (List<Order>) query.list();
			Optional<Order> findFirst = orderList
					.stream()
					.filter(s -> s.getArtikull().getArtikullId() == artikull
							.getArtikullId()).findFirst();

			if (findFirst.isPresent()) {
				Order orderP = findFirst.get();

				Order order = new Order();
				order.setTrackingDate(trackingDate);
				order.setArtikull(artikull);
				order.setUnit(unit);
				order.setTrackQuantity(trackQuantity);
				order.setPrice(price);
				order.setTotalPrice(totalPriceQuantity);
				order.setPreventive(preventive);
				order.setPercent(percent);
				order.setDiscount(discount);
				BigDecimal quantity = orderP.getQuantity();
				BigDecimal gjendje2 = orderP.getGjendje();
				BigDecimal totalAfter = gjendje2.subtract(trackQuantity);
				order.setQuantity(quantity);
				order.setTotalAfter(totalAfter);
				orderP.setGjendje(totalAfter);
				order.setFlag(flag);

				order.setPrevortrack(prevortrack);

				session.save(order);
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Artikull "
								+ artikull.getName()
								+ " u ruajt", null);
				FacesContext.getCurrentInstance().addMessage(null, message);
				tx.commit();

			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Artikull "
								+artikull.getName()
								+ " nuk eshte i njete", null);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}

			

		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public List<Order> getOrdersForPreventiveIdBoolean(int preventiveId,
			boolean flag, boolean prevortrack) {
		Session session = null;
		List<Order> orderList = null;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query query = session
					.createQuery("from Order o where o.preventive.preventiveId=:preventiveId and o.flag=:flag and o.prevortrack=:prevortrack");
			query.setParameter("preventiveId", preventiveId);
			query.setParameter("flag", flag);
			query.setParameter("prevortrack", prevortrack);
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

	public boolean deleteTrack(int orderId, boolean flag, boolean prevortrack) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("delete from Order o where o.orderId=:orderId and o.flag=:flag and o.prevortrack=:prevortrack");
			query.setParameter("orderId", orderId);
			query.setParameter("flag", flag);
			query.setParameter("prevortrack", prevortrack);
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
