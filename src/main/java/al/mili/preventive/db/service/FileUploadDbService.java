package al.mili.preventive.db.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.el.parser.ParseException;

import al.mili.preventive.db.model.Artikull;
import al.mili.preventive.db.model.Customer;
import al.mili.preventive.db.model.Order;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.ResourceType;
import al.mili.preventive.db.model.Resources;
import al.mili.preventive.db.model.Task;
import al.mili.preventive.db.model.TaskSheet;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.WorkLog;
import au.com.bytecode.opencsv.CSVReader;

public class FileUploadDbService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7300817126585789923L;
	private transient final Logger slf4jLogger = LoggerFactory
			.getLogger(FileUploadDbService.class);

	public void savePreventives(InputStream uploadedFile) throws IOException {
		Reader reader = new InputStreamReader(uploadedFile);
		CSVReader preventivesReader = null;
		List<String[]> preventivesRecords = null;
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			preventivesReader = new CSVReader(reader, ',', '"', 0);
			preventivesRecords = preventivesReader.readAll();

			if (preventivesRecords == null || preventivesRecords.isEmpty()) {
				slf4jLogger.warn("No items in Preventive records find");
				throw new Exception("No items in Preventive records find");
			}

			List<User> userResult = (List<User>) session.createQuery(
					"from User").list();
			List<Customer> customerResult = (List<Customer>) session
					.getNamedQuery("queryCustomer").list();

			for (String[] rows : preventivesRecords) {

				String emriKlientit = rows[0];
				String email = rows[1];
				String protokollNumber = rows[2];
				String varianti = rows[3];
				String objekti = rows[4];
				String location = rows[5];
				String emriManaxherit = rows[6];
				String vlera = rows[7];
				String status = rows[8];
				String statusiPunimeve = rows[9];
				String date = rows[10];
				String paradhenie = rows[11];
				String vleraMbetur = rows[12];
				String kontrata = rows[13];
				String hide = rows[14];

				DecimalFormatSymbols symbols = new DecimalFormatSymbols();
				symbols.setGroupingSeparator(',');
				symbols.setDecimalSeparator('.');
				String pattern = "###,###.00";
				DecimalFormat decimalFormat = new DecimalFormat(pattern,
						symbols);
				decimalFormat.setParseBigDecimal(true);

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date result = df.parse(date);
				BigDecimal parseVlera = (BigDecimal) decimalFormat.parse(vlera);
				BigDecimal parseParadhenie = (BigDecimal) decimalFormat
						.parse(paradhenie);
				BigDecimal parseVleraMbetur = (BigDecimal) decimalFormat
						.parse(vleraMbetur);

				boolean parseKontrata = Integer.parseInt(kontrata) == 1 ? true
						: false;
				boolean parseHide = Integer.parseInt(hide) == 1 ? true : false;

				Preventive preventive = new Preventive();
				preventive.setCustomer(getCustomerByName(emriKlientit,
						customerResult));
				preventive.setEmail(email);
				preventive.setProtokollNumber(protokollNumber);
				preventive.setVarianti(varianti);
				preventive.setObjekti(objekti);
				preventive.setLocation(location);
				preventive.setUser(getUserByName(emriManaxherit, userResult));
				preventive.setVlera(parseVlera);
				preventive.setProtokollDate(result);
				preventive.setStatus(status);
				preventive.setStatusiPunimeve(statusiPunimeve);
				preventive.setParadhenie(parseParadhenie);
				preventive.setVleraMbetur(parseVleraMbetur);
				preventive.setKontrata(parseKontrata);
				preventive.setHide(parseHide);

				session.save(preventive);

			}
			tx.commit();

		}

		catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.getMessage();

		} finally {
			if (session != null && session.isOpen()) {

				session.close();

			}
			if (preventivesReader != null) {
				preventivesReader.close();
			}
		}

	}

	public void saveOrder(InputStream uploadedFile) throws IOException {
		Reader reader = new InputStreamReader(uploadedFile);
		CSVReader orderReader = null;
		List<String[]> orderRecords = null;
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			orderReader = new CSVReader(reader, ',', '"', 0);
			orderRecords = orderReader.readAll();

			if (orderRecords == null || orderRecords.isEmpty()) {
				slf4jLogger.warn("No items in Zerat records find");
				throw new Exception("No items in Zerat records find");
			}

			List<Preventive> preventiveResult = (List<Preventive>) session
					.createQuery("from Preventive").list();

			List<Unit> unitResult = (List<Unit>) session.getNamedQuery(
					"queryAllUnit").list();

			List<Artikull> artikullResult = (List<Artikull>) session
					.getNamedQuery("queryAllArtikull").list();

			for (String[] rows : orderRecords) {
				String artikullName = rows[0];
				String artikullNjesi = rows[1];
				//String gjendje = rows[2];
				String artikullSasi = rows[2];
				String artikullPrice = rows[3];
				//String totalPrice = rows[5];
				//String percent = rows[6];
				//String discount = rows[7];
				String objektReaded = rows[4];
				/*String flagReaded = rows[9];
				String prevtrackReaded = rows[10];
*/
				DecimalFormatSymbols symbols = new DecimalFormatSymbols();
				symbols.setGroupingSeparator(',');
				symbols.setDecimalSeparator('.');
				String pattern = "#,##0.0#";
				DecimalFormat decimalFormat = new DecimalFormat(pattern,
						symbols);
				decimalFormat.setParseBigDecimal(true);

				BigDecimal parseArtikullSasi = (BigDecimal) decimalFormat
						.parse(artikullSasi);
/*
				BigDecimal parseGjendje = (BigDecimal) decimalFormat
						.parse(gjendje);*/
				BigDecimal parseArtikullPrice = (BigDecimal) decimalFormat
						.parse(artikullPrice);
				/*BigDecimal parseTotalPrice = (BigDecimal) decimalFormat
						.parse(totalPrice);*/
				/*BigDecimal parsePercent = (BigDecimal) decimalFormat
						.parse(percent);*/

				/*BigDecimal parseDiscount = (BigDecimal) decimalFormat
						.parse(discount);*/

				/*boolean parseflag = Integer.parseInt(flagReaded) == 1 ? true
						: false;
				boolean parsePrevtrack = Integer.parseInt(prevtrackReaded) == 1 ? true
						: false;
*/
				Order order = new Order();
				
				order.setArtikull(getArtikullByKod(artikullName, artikullResult));
				order.setUnit(getUnitByName(artikullNjesi, unitResult));
				order.setGjendje(parseArtikullSasi);
				order.setQuantity(parseArtikullSasi);
				order.setTrackQuantity(BigDecimal.ZERO);
				order.setPrice(parseArtikullPrice);
				BigDecimal totalprice = parseArtikullSasi.multiply(parseArtikullPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
				order.setTotalPrice(totalprice);
				order.setPreventive(getObjektByName(objektReaded,
						preventiveResult));
				order.setPercent(BigDecimal.ZERO);
					
				order.setDiscount(BigDecimal.ZERO);
				
				order.setDiscount(BigDecimal.ZERO);
				order.setFlag(true);
				order.setPrevortrack(false);
				session.save(order);

			}

			tx.commit();

		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();

		} finally {
			if (session != null && session.isOpen()) {

				session.close();

			}

			if (orderReader != null) {
				orderReader.close();
			}
		}

	}

	public void saveTracker(InputStream uploadedFile) throws IOException {
		Reader reader = new InputStreamReader(uploadedFile);
		CSVReader trackReader = null;
		List<String[]> trackRecords = null;
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			trackReader = new CSVReader(reader, ',', '"', 0);
			trackRecords = trackReader.readAll();

			if (trackRecords == null || trackRecords.isEmpty()) {
				slf4jLogger.warn("No items in Zerat records find");
				throw new Exception("No items in Zerat records find");
			}

			List<Preventive> preventiveResult = (List<Preventive>) session
					.createQuery("from Preventive").list();

			List<Unit> unitResult = (List<Unit>) session.getNamedQuery(
					"queryAllUnit").list();

			List<Artikull> artikullResult = (List<Artikull>) session
					.getNamedQuery("queryAllArtikull").list();

			for (String[] rows : trackRecords) {
				String date = rows[0];
				String artikullName = rows[1];
				String artikullNjesi = rows[2];
				String mbjellje = rows[3];
				String objektReaded = rows[4];

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date result = df.parse(date);

				Preventive preventive = getObjektByName(objektReaded,
						preventiveResult);
				int preventiveId = preventive.getPreventiveId();

				Artikull artikull = getArtikullByKod(artikullName,
						artikullResult);
				Unit unit = getUnitByName(artikullNjesi, unitResult);

				Query query = session
						.createQuery("from Order o where o.preventive.preventiveId=:preventiveId and o.flag=:flag and o.prevortrack=:prevortrack");

				query.setParameter("preventiveId", preventiveId);
				query.setParameter("flag", true);
				query.setParameter("prevortrack", false);
				List<Order> orderList = (List<Order>) query.list();

				DecimalFormatSymbols symbols = new DecimalFormatSymbols();
				symbols.setGroupingSeparator(',');
				symbols.setDecimalSeparator('.');
				String pattern = "#,##0.0#";
				DecimalFormat decimalFormat = new DecimalFormat(pattern,
						symbols);
				decimalFormat.setParseBigDecimal(true);

				BigDecimal parseMbjellje = (BigDecimal) decimalFormat
						.parse(mbjellje);

				Order order = new Order();
				order.setTrackingDate(result);
				order.setArtikull(artikull);
				order.setUnit(unit);

				order.setTrackQuantity(parseMbjellje);

				order.setPrice(BigDecimal.ZERO);
				order.setTotalPrice(BigDecimal.ZERO);
				order.setPercent(BigDecimal.ZERO);
				order.setDiscount(BigDecimal.ZERO);
				order.setPreventive(preventive);
				for (Order orderP : orderList) {
					if (orderP.getArtikull().getName()
							.equalsIgnoreCase(artikull.getName())) {
						BigDecimal quantity = orderP.getQuantity();
						BigDecimal gjendje2 = orderP.getGjendje();
						BigDecimal totalAfter = gjendje2
								.subtract(parseMbjellje);
						order.setQuantity(quantity);
						order.setTotalAfter(totalAfter);
						orderP.setGjendje(totalAfter);
					}

				}

				order.setFlag(true);
				order.setPrevortrack(true);
				session.save(order);

			}

			tx.commit();

		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();

		} finally {
			if (session != null && session.isOpen()) {

				session.close();

			}

			if (trackReader != null) {
				trackReader.close();
			}
		}

	}

	public void saveResources(InputStream uploadedFile) throws IOException {
		Reader reader = new InputStreamReader(uploadedFile);
		CSVReader resourceReader = null;
		List<String[]> resourceRecords = null;
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			resourceReader = new CSVReader(reader, ',', '"', 0);
			resourceRecords = resourceReader.readAll();

			if (resourceRecords == null || resourceRecords.isEmpty()) {
				slf4jLogger.warn("No items in Zerat records find");
				throw new Exception("No items in Zerat records find");
			}

			List<ResourceType> resourceTypeResult = (List<ResourceType>) session
					.createQuery("from ResourceType").list();

			for (String[] rows : resourceRecords) {

				String emri = rows[0];
				String description = rows[1];
				String resourceType = rows[2];

				Resources resources = new Resources();
				resources.setEmri(emri);
				resources.setDescription(description);
				resources.setResourceType(getResourceType(resourceType,
						resourceTypeResult));

				session.save(resources);

			}

			tx.commit();

		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();

		} finally {
			if (session != null && session.isOpen()) {

				session.close();

			}

			if (resourceReader != null) {
				resourceReader.close();
			}
		}

	}

	public void saveWorkLog(InputStream uploadedFile) throws IOException,
			ParseException {
		Reader reader = new InputStreamReader(uploadedFile);
		CSVReader workLogReader = null;
		List<String[]> workLogRecords = null;
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			workLogReader = new CSVReader(reader, ',', '"', 0);
			workLogRecords = workLogReader.readAll();

			if (workLogRecords == null || workLogRecords.isEmpty()) {
				slf4jLogger.warn("No items in Zerat records find");
				throw new Exception("No items in Zerat records find");
			}

			List<Preventive> preventiveResult = (List<Preventive>) session
					.createQuery("from Preventive").list();

			List<User> userResult = (List<User>) session.createQuery(
					"from User").list();

			List<Task> taskResult = (List<Task>) session.createQuery(
					"from Task").list();

			for (String[] rows : workLogRecords) {
				String date = rows[0];
				String preventive = rows[1];
				String user = rows[2];
				String[] taskArray = rows[3].split(",");
				String numberOfWorker = rows[4];
				String numberOfEquipment = rows[5];
				String oretEpunuara = rows[6];

				List<Task> taskList = new ArrayList<Task>();

				for (String task : taskArray) {
					Task taskByName = getTaskByName(task, taskResult);
					if (taskByName != null) {
						taskList.add(taskByName);
					}
				}

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date result = df.parse(date);
				int parseNumberOfWorker = Integer.parseInt(numberOfWorker);
				int parseNumberOfEquipment = Integer
						.parseInt(numberOfEquipment);
				double parseOretEPunuara = Double.parseDouble(oretEpunuara);

				WorkLog workLog = new WorkLog();
				workLog.setDate(result);
				workLog.setPreventive(getObjektByName(preventive,
						preventiveResult));
				workLog.setUser(getUserByName(user, userResult));
				workLog.setTask(taskList);
				workLog.setNumberofWorker(parseNumberOfWorker);
				workLog.setNumberOfEquipment(parseNumberOfEquipment);
				workLog.setOretEPunuara(parseOretEPunuara);

				session.save(workLog);

			}

			tx.commit();

		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();

		} finally {
			if (session != null && session.isOpen()) {

				session.close();

			}

			if (workLogReader != null) {
				workLogReader.close();
			}
		}

	}

	public void saveTaskSheet(InputStream uploadedFile) throws IOException,
			ParseException {
		Reader reader = new InputStreamReader(uploadedFile);
		CSVReader taskSheetReader = null;
		List<String[]> taskSheetRecords = null;
		Session session = null;
		Transaction tx = null;
		try {

			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();
			taskSheetReader = new CSVReader(reader, ',', '"', 0);
			taskSheetRecords = taskSheetReader.readAll();

			if (taskSheetRecords == null || taskSheetRecords.isEmpty()) {
				slf4jLogger.warn("No items in Zerat records find");
				throw new Exception("No items in Zerat records find");
			}

			List<Preventive> preventiveResult = (List<Preventive>) session
					.createQuery("from Preventive").list();

			List<Resources> resourcesResult = (List<Resources>) session
					.createQuery("from Resources").list();

			List<User> userResult = (List<User>) session.createQuery(
					"from User").list();

			List<Unit> unitResult = (List<Unit>) session.getNamedQuery(
					"queryAllUnit").list();

			List<Task> taskResult = (List<Task>) session.createQuery(
					"from Task").list();

			for (String[] rows : taskSheetRecords) {
				String date = rows[0];
				String preventive = rows[1];
				String user = rows[2];
				String resourceName = rows[3];
				// String task = rows[4];
				String startTime = rows[4];
				String endTime = rows[5];
				String quantity = rows[6];
				String unit = rows[7];

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date result = df.parse(date);
				Date start = new SimpleDateFormat("HH:mm").parse(startTime);
				Date end = new SimpleDateFormat("HH:mm").parse(endTime);

				double diff = end.getTime() - start.getTime();
				double diffHours = diff / (60 * 60 * 1000);

				double doubleQuantity = Double.parseDouble(quantity);

				TaskSheet taskSheet = new TaskSheet();
				taskSheet.setDate(result);
				taskSheet.setPreventive(getObjektByName(preventive,
						preventiveResult));
				taskSheet.setUser(getUserByName(user, userResult));
				taskSheet.setResources(getResourcesByName(resourceName,
						resourcesResult));
				Resources resourcesByName = getResourcesByName(resourceName,
						resourcesResult);

				taskSheet.setResourceType(getResourcesByName(resourceName,
						resourcesResult).getResourceType());
				taskSheet.setStartTime(start);
				taskSheet.setEndTime(end);
				taskSheet.setDuration(diffHours);

				taskSheet.setQuantity(doubleQuantity);

				taskSheet.setUnit(getUnitByName(unit, unitResult));

				session.save(taskSheet);

			}

			tx.commit();

		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();

		} finally {
			if (session != null && session.isOpen()) {

				session.close();

			}

			if (taskSheetReader != null) {
				taskSheetReader.close();
			}
		}

	}

	public void saveArticles(InputStream uploadedFile) throws IOException {
		Reader reader = new InputStreamReader(uploadedFile);
		CSVReader artikullReader = null;
		List<String[]> artikullRecords = null;
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			artikullReader = new CSVReader(reader, ',', '"', 0);
			artikullRecords = artikullReader.readAll();

			if (artikullRecords == null || artikullRecords.isEmpty()) {
				slf4jLogger.warn("No items in Preventive records find");
				throw new Exception("No items in Preventive records find");
			}

			for (String[] rows : artikullRecords) {

				String name = rows[0];
				String description = rows[1];

				Query query = session
						.createQuery("from Artikull a where a.name=:name");
				query.setString("name", name);

				Artikull uniqResult = (Artikull) query.uniqueResult();
				if (uniqResult == null) {
					Artikull artikull = new Artikull();

					artikull.setName(name);
					artikull.setDescription(description);

					session.save(artikull);
				}

			}

			tx.commit();

		}

		catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.getMessage();

		} finally {
			if (session != null && session.isOpen()) {

				session.close();

			}

			if (artikullReader != null) {
				artikullReader.close();
			}
		}

	}

	public void saveCustomers(InputStream uploadedFile) throws IOException {
		Reader reader = new InputStreamReader(uploadedFile);
		CSVReader customerReader = null;
		List<String[]> customerRecords = null;
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtilities.getSessionFactory().openSession();
			tx = session.beginTransaction();

			customerReader = new CSVReader(reader, ',', '"', 0);
			customerRecords = customerReader.readAll();

			if (customerRecords == null || customerRecords.isEmpty()) {
				slf4jLogger.warn("No items in Customer records find");
				throw new Exception("No items in Customer records find");
			}

			for (String[] rows : customerRecords) {

				String fullName = rows[0];
				String email = rows[1];
				String telefon = rows[2];
				String nipt = rows[3];

				Query query = session
						.createQuery("from Customer c where c.emriPlote=:fullName");
				query.setString("fullName", fullName);

				Customer uniqResult = (Customer) query.uniqueResult();
				if (uniqResult == null) {

					Customer customer = new Customer();
					customer.setEmriPlote(fullName);
					customer.setEmail(email);
					customer.setTelefon(telefon);
					customer.setNipt(nipt);

					session.save(customer);
				}

			}

			tx.commit();
		}

		catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.getMessage();

		} finally {
			if (session != null && session.isOpen()) {

				session.close();

			}
			if (customerReader != null) {
				customerReader.close();
			}
		}

	}

	private Customer getCustomerByName(String emriKlientit,
			List<Customer> customerResult) {

		for (Customer customer : customerResult) {

			if (customer.getEmriPlote().equalsIgnoreCase(emriKlientit)) {
				return customer;
			}
		}
		return null;
	}

	private Unit getUnitByName(String artikullNjesi, List<Unit> unitResult) {
		for (Unit unit : unitResult) {
			if (unit.getUnitName().equalsIgnoreCase(artikullNjesi)) {
				return unit;
			}
		}
		return null;
	}

	private Preventive getObjektByName(String objektReaded,
			List<Preventive> preventiveResult) {
		for (Preventive preventive : preventiveResult) {
			if (preventive.getPreventiveId() == Integer.parseInt(objektReaded)) {
				return preventive;
			}
		}
		return null;
	}

	private Artikull getArtikullByKod(String artikullName,
			List<Artikull> artikullResult) {
		for (Artikull artikull : artikullResult) {
			if (artikull.getName().equalsIgnoreCase(artikullName)) {
				return artikull;
			}
		}
		return null;
	}

	private Resources getResourcesByName(String resourceName,
			List<Resources> resourcesResult) {
		for (Resources resources : resourcesResult) {
			if (resources.getEmri().equalsIgnoreCase(resourceName)) {
				return resources;
			}
		}
		return null;

	}

	private ResourceType getResourceType(String resourceType,
			List<ResourceType> resourceTypeResult) {
		for (ResourceType resourceT : resourceTypeResult) {
			if (resourceT.getEmri().equalsIgnoreCase(resourceType)) {
				return resourceT;
			}
		}
		return null;
	}

	private Task getTaskByName(String taskName, List<Task> taskResult) {
		for (Task task : taskResult) {
			if (task.getName().equalsIgnoreCase(taskName)) {
				return task;
			}
		}
		return null;
	}

	private User getUserByName(String emriManaxherit, List<User> userResult) {
		for (User user : userResult) {
			if (user.getEmri().equalsIgnoreCase(emriManaxherit)) {
				return user;
			}
		}
		return null;

	}

}
