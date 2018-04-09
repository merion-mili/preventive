package al.mili.preventive.db.test;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import al.mili.preventive.db.model.Artikull;
import al.mili.preventive.db.model.Customer;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.UsersRole;
import al.mili.preventive.db.model.Order;
import al.mili.preventive.db.service.HibernateUtilities;
import au.com.bytecode.opencsv.CSVReader;

@Ignore
public class PreventiveTest {

	@Test
	public void testPreventive() throws IOException, ParseException {

		CSVReader customerReader = new CSVReader(new FileReader(
				"src/test/resources/customer.csv"), ',', '"', 0);
		List<String[]> customerRecords = customerReader.readAll();

		CSVReader artikullReader = new CSVReader(new FileReader(
				"src/test/resources/artikujt.csv"), ',', '"', 0);
		List<String[]> artikullRecords = artikullReader.readAll();

		CSVReader readerPreventive = new CSVReader(new FileReader(
				"src/test/resources/preventive.csv"), ',', '"', 0);
		List<String[]> preventiveRecords = readerPreventive.readAll();

		CSVReader orderReader = new CSVReader(new FileReader(
				"src/test/resources/order.csv"), ',', '"', 0);
		List<String[]> orderRecords = orderReader.readAll();

		CSVReader unitReader = new CSVReader(new FileReader(
				"src/test/resources/unit.csv"), ',', '"', 0);
		List<String[]> unitRecords = unitReader.readAll();

		UsersRole userRole1 = new UsersRole();
		userRole1.setRole("admin");
		UsersRole userRole2 = new UsersRole();
		userRole2.setRole("user");

		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();

		session.save(userRole1);
		session.save(userRole2);

		UsersRole role1 = (UsersRole) session.get(UsersRole.class, 1);
		UsersRole role2 = (UsersRole) session.get(UsersRole.class, 2);

		User user1 = new User();
		user1.setEmri("Vilma");
		user1.setMbiemri("Zigori");
		user1.setUserName("vilma");
		user1.setPassword("1234");
		user1.setRole(role1);

		User user2 = new User();
		user2.setEmri("Joni");
		user2.setMbiemri("Joni");
		user2.setUserName("joni");
		user2.setPassword("1234");
		user2.setRole(role2);
		session.save(user1);
		session.save(user2);

		for (String[] rows : customerRecords) {

			String fullName = rows[0];
			String email = rows[1];
			String telefon = rows[2];
			String nipt = rows[3];

			Customer customer = new Customer();
			customer.setEmriPlote(fullName);
			customer.setEmail(email);
			customer.setTelefon(telefon);
			customer.setNipt(nipt);

			session.save(customer);

		}

		for (String[] rows : artikullRecords) {

			String name = rows[0];
			String description = rows[1];

			Artikull artikull = new Artikull();

			artikull.setName(name);
			artikull.setDescription(description);

			session.save(artikull);

		}

		List<User> userResult = (List<User>) session.createQuery("from User")
				.list();

		List<Customer> customerResult = (List<Customer>) session.getNamedQuery(
				"queryCustomer").list();

		for (String[] rows : preventiveRecords) {

			String emriKlientit = rows[0];
			String email = rows[1];
			String protokollNumber =rows[2];
			String varianti =rows[3];
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

			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "###,###.00";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date result = df.parse(date);
			BigDecimal parseVlera = (BigDecimal) decimalFormat.parse(vlera);
			BigDecimal parseParadhenie = (BigDecimal) decimalFormat
					.parse(paradhenie);
			BigDecimal parseVleraMbetur = (BigDecimal) decimalFormat
					.parse(vleraMbetur);
			//double parseprotokollNumber = Double.parseDouble(protokollNumber);
			boolean parseKontrata = Integer.parseInt(kontrata) == 1 ? true
					: false;

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

			session.save(preventive);

		}

		for (String[] rows : unitRecords) {
			String unitName = rows[0];

			Unit unit = new Unit();
			unit.setUnitName(unitName);
			session.save(unit);
		}

		List<Preventive> preventiveResult = (List<Preventive>) session
				.createQuery("from Preventive").list();

		List<Unit> unitResult = (List<Unit>) session.getNamedQuery(
				"queryAllUnit").list();

		List<Artikull> artikullResult = (List<Artikull>) session.getNamedQuery(
				"queryAllArtikull").list();

		for (String[] rows : orderRecords) {
			String artikullName = rows[0];
			String artikullNjesi = rows[1];
			String artikullSasi = rows[2];
			String artikullPrice = rows[3];
			String totalPrice = rows[4];
			String objektReaded = rows[5];
			String flagReaded = rows[6];

			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);

			BigDecimal parseArtikullSasi = (BigDecimal) decimalFormat
					.parse(artikullSasi);
			BigDecimal parseArtikullPrice = (BigDecimal) decimalFormat
					.parse(artikullPrice);
			BigDecimal parseTotalPrice = (BigDecimal) decimalFormat
					.parse(totalPrice);
			boolean parseflag = Integer.parseInt(flagReaded) == 1 ? true
					: false;

			Order order = new Order();
			order.setArtikull(getArtikullByKod(artikullName, artikullResult));
			order.setUnit(getUnitByName(artikullNjesi, unitResult));
			order.setQuantity(parseArtikullSasi);
			order.setPrice(parseArtikullPrice);
			order.setTotalPrice(parseTotalPrice);
			order.setPreventive(getObjektByName(objektReaded, preventiveResult));
			order.setPercent(BigDecimal.ZERO);
			order.setDiscount(BigDecimal.ZERO);
			order.setFlag(parseflag);
			session.save(order);

		}

		session.getTransaction().commit();
		session.close();

		session = HibernateUtilities.getSessionFactory().openSession();

		Assert.assertEquals(preventiveResult.size(), 3);

		session.close();

	}

	private Artikull getArtikullByKod(String artikullName,
			List<Artikull> artikullResult) {
		for (Artikull artikull : artikullResult) {
			if (artikull.getName().trim().equals(artikullName.trim())) {
				return artikull;
			}
		}
		return null;
	}

	private Customer getCustomerByName(String emriKlientit,
			List<Customer> customerResult) {

		for (Customer customer : customerResult) {

			if (customer.getEmriPlote().trim().equals(emriKlientit.trim())) {
				return customer;
			}
		}
		return null;
	}

	private Unit getUnitByName(String artikullNjesi, List<Unit> unitResult) {
		for (Unit unit : unitResult) {
			if (unit.getUnitName().equals(artikullNjesi)) {
				return unit;
			}
		}
		return null;
	}

	private Preventive getObjektByName(String objektReaded,
			List<Preventive> preventiveResult) {
		for (Preventive preventive : preventiveResult) {
			if (preventive.getObjekti().equals(objektReaded)) {
				return preventive;
			}
		}
		return null;
	}

	private User getUserByName(String emriManaxherit, List<User> userResult) {
		for (User user : userResult) {
			if (user.getEmri().equals(emriManaxherit)) {
				return user;
			}
		}
		return null;
	}

}
