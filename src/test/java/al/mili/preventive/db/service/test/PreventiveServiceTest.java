package al.mili.preventive.db.service.test;

import java.util.List;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.service.HibernateUtilities;
import al.mili.preventive.db.service.PreventiveDbService;

@Ignore
public class PreventiveServiceTest {

	@Test
	public void testPreventiveSerivece() {

		PreventiveDbService preventiveDbService = new PreventiveDbService();

		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();

		List<Preventive> preventives = preventiveDbService.getPreventives();

		session.getTransaction().commit();
		session.close();

		session = HibernateUtilities.getSessionFactory().openSession();

		Assert.assertEquals(preventives.size(), 10);
		session.close();

	}
}
