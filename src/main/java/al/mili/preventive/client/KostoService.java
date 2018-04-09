package al.mili.preventive.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import al.mili.preventive.db.model.Order;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.ResourceType;
import al.mili.preventive.db.model.Resources;
import al.mili.preventive.db.model.TaskSheet;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.WorkLog;
import al.mili.preventive.db.service.StandingDbService;

@ManagedBean(name = "standingKostoService")
@ViewScoped
public class KostoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5880673252392819864L;
	private StandingDbService standingDbService;

	@PostConstruct
	public void init() {

		System.out.println("Int call: ");
		standingDbService = new StandingDbService();

	}

	public List<StandingKostoPerResource> getTaskSheetPerPreventiveUser(
			Preventive preventive, User user) {
		List<TaskSheet> taskSheets = standingDbService
				.getTaskSheetPerPreventiveUser(preventive, user);

		List<StandingKostoPerResource> kostoItems = new ArrayList<StandingKostoPerResource>();

		for (TaskSheet sheet : taskSheets) {

			String resourceName = sheet.getResources().getEmri();
			int resourceId = sheet.getResources().getResourceId();
			String description = sheet.getResources().getDescription();
			String lloji = sheet.getResources().getResourceType().getEmri();
			String unitName = sheet.getUnit().getUnitName();

			long count = kostoItems.stream()
					.filter(s -> s.getResource().equals(resourceName)).count();
			if (count == 0) {

				StandingKostoPerResource item = new StandingKostoPerResource();
				item.setResource(resourceName);
				item.setDescription(description);
				item.setLloji(lloji);

				long frequence = taskSheets
						.stream()
						.filter(s -> s.getResources().getResourceId() == resourceId)
						.count();
				item.setFrequence(frequence);

				double totalDuration = taskSheets
						.stream()
						.filter(s -> s.getResources().getResourceId() == resourceId)
						.collect(
								Collectors
										.summingDouble(TaskSheet::getDuration));

				item.setTotalDuration(totalDuration);

				Double totalQuantity = taskSheets
						.stream()
						.filter(s -> s.getResources().getResourceId() == resourceId)
						.collect(
								Collectors
										.summingDouble(TaskSheet::getQuantity));

				item.setTotalQuantity(totalQuantity);

				item.setUnitName(unitName);

				kostoItems.add(item);
			}

		}

		return kostoItems
				.stream()
				.sorted(Comparator
						.comparing(StandingKostoPerResource::getResource))
				.collect(Collectors.toList());
	}

	public List<StandingPreventiveItem> getAllTaskSheetForResource(
			ResourceType resourceType, Resources resources) {
		List<TaskSheet> allTaskSheet = standingDbService
				.getAllTaskSheetForResource(resourceType, resources);

		List<StandingPreventiveItem> preventiveItems = new ArrayList<StandingPreventiveItem>();

		for (TaskSheet sheet : allTaskSheet) {
			String objekti = sheet.getPreventive().getObjekti();
			int preventiveId = sheet.getPreventive().getPreventiveId();
			String emriPlote = sheet.getPreventive().getCustomer()
					.getEmriPlote();
			Date protokollDate = sheet.getPreventive().getProtokollDate();

			long count = preventiveItems.stream()
					.filter(s -> s.getPrevetntiveCustomer().equals(emriPlote))
					.filter(s -> s.getPreventiveObject().equals(objekti))
					.filter(s -> s.getPreventiveDate().equals(protokollDate))
					.count();
			if (count == 0) {

				StandingPreventiveItem item = new StandingPreventiveItem();
				item.setPrevetntiveCustomer(emriPlote);
				item.setPreventiveObject(objekti);
				item.setPreventiveDate(protokollDate);

				long frequence = allTaskSheet
						.stream()
						.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
						.count();
				item.setFrequnecy(frequence);

				double totalDuration = allTaskSheet
						.stream()
						.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
						.collect(
								Collectors
										.summingDouble(TaskSheet::getDuration));
				if (totalDuration != 0) {
					item.setTotalDuration(totalDuration);
				} else {
					item.setTotalDuration(0);
				}

				Double totalQuantity = allTaskSheet
						.stream()
						.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
						.collect(
								Collectors
										.summingDouble(TaskSheet::getQuantity));

				if (totalQuantity != 0) {
					item.setTotalQuantity(totalQuantity);
				} else {
					item.setTotalQuantity(0);
				}

				String unitName = allTaskSheet
						.stream()
						.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
						.map(TaskSheet::getUnit).map(Unit::getUnitName)
						.collect(Collectors.toList()).get(0);
				item.setUnitName(unitName);

				preventiveItems.add(item);

			}

		}

		return preventiveItems
				.stream()
				.sorted(Comparator
						.comparing(
								StandingPreventiveItem::getPrevetntiveCustomer)
						.thenComparing(
								Comparator
										.comparing(
												StandingPreventiveItem::getPreventiveObject)
										.thenComparing(
												Comparator
														.comparing(StandingPreventiveItem::getPreventiveDate))))
				.collect(Collectors.toList());
	}

	public List<StandingAllElementPreventiveItem> getAllPrevetniveElement() {
		List<Order> orders = standingDbService.getOrders(true);
		List<WorkLog> allWorkLogs = standingDbService.getWorkLogs();

		List<StandingAllElementPreventiveItem> preventiveItems = new ArrayList<StandingAllElementPreventiveItem>();

		for (Order order : orders) {
			Preventive preventive = order.getPreventive();
			if (preventive.isHide() == true) {
				int preventiveId = preventive.getPreventiveId();
				String emriPlote = preventive.getCustomer().getEmriPlote();
				String objekti = preventive.getObjekti();
				// String varianti = order.getPreventive().getVarianti();

				String protokollNumber = preventive.getProtokollNumber();
				BigDecimal vlera = preventive.getVlera();

				long count = preventiveItems
						.stream()
						.filter(s -> s.getCustomerName().equals(emriPlote))
						.filter(s -> s.getProtokollNumber().equals(
								protokollNumber))

						.count();

				if (count == 0) {
					StandingAllElementPreventiveItem item = new StandingAllElementPreventiveItem();
					item.setPreventiveId(preventiveId);
					item.setCustomerName(emriPlote);
					item.setPreventiveObject(objekti);
					item.setProtokollNumber(protokollNumber);
					item.setVlera(vlera);

					BigDecimal artikullQuantityMbjelle = orders
							.stream()
							.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
							.filter(s -> s.getTrackingDate() != null)
							.map(Order::getQuantity)
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					BigDecimal artikullQuantityTotal = orders
							.stream()
							.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
							.map(Order::getQuantity)
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					BigDecimal artikullQuantity = artikullQuantityTotal
							.subtract(artikullQuantityMbjelle);

					item.setArtikullQuantity(artikullQuantity);

					BigDecimal trackQuantity = orders
							.stream()
							.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
							.filter(s -> s.isPrevortrack() == true)
							.map(Order::getTrackQuantity)
							.reduce(BigDecimal.ZERO, BigDecimal::add);
					if (trackQuantity != null) {
						item.setArtikullTrackQuantity(trackQuantity);

					} else {

						item.setArtikullTrackQuantity(BigDecimal.ZERO);

					}

					if (trackQuantity != null
							|| trackQuantity != BigDecimal.ZERO) {
						BigDecimal multiply = (trackQuantity.divide(
								artikullQuantity, 2, RoundingMode.HALF_UP))
								.multiply(new BigDecimal(100));
						int intValue = multiply.intValue();
						item.setProgress(intValue);

					} else {

						item.setProgress(null);
					}

					Date firstTrackDate = orders
							.stream()
							.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
							.filter(s -> s.isPrevortrack() == true)
							.sorted(Comparator
									.comparing(Order::getTrackingDate))
							.map(Order::getTrackingDate).findFirst()
							.orElse((Date) null);

					if (firstTrackDate != null) {
						item.setStartDate(firstTrackDate);
					} else {
						item.setStartDate(null);
					}

					Date lastTrackDate = orders
							.stream()
							.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
							.filter(s -> s.isPrevortrack() == true)
							.sorted(Comparator
									.comparing(Order::getTrackingDate))
							.reduce((a, b) -> b).map(Order::getTrackingDate)
							.orElse((Date) null);
					if (lastTrackDate != null) {
						item.setEndDate(lastTrackDate);
					} else {
						item.setEndDate(null);
					}

					if (firstTrackDate != null && lastTrackDate != null) {
						int diffInDays = (int) ((lastTrackDate.getTime() - firstTrackDate
								.getTime()) / (1000 * 60 * 60 * 24));
						if (diffInDays == 0) {
							item.setDiffInDays(1);
						} else {
							item.setDiffInDays(diffInDays);
						}

					} else {
						item.setDiffInDays(0);
					}

					Double duration = allWorkLogs
							.stream()
							.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
							.sorted(Comparator.comparing(WorkLog::getDate))
							.distinct()
							.collect(
									Collectors
											.summingDouble(WorkLog::getOretEPunuara));
					item.setOretEPunuara(duration);

					/*
					 * Double resourceQuantity = allTaskSheet.stream() .filter(s
					 * -> s.getPreventive().getPreventiveId() == preventiveId)
					 * .collect( Collectors
					 * .summingDouble(TaskSheet::getQuantity));
					 */

					long resourceNumber = allWorkLogs
							.stream()
							.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
							.sorted(Comparator.comparing(WorkLog::getDate))
							.distinct()
							.collect(
									Collectors
											.summingLong(WorkLog::getNumberofWorker));

					item.setNumberOfWorker(resourceNumber);

					long equipmentNumber = allWorkLogs
							.stream()
							.filter(s -> s.getPreventive().getPreventiveId() == preventiveId)
							.sorted(Comparator.comparing(WorkLog::getDate))
							.distinct()
							.collect(
									Collectors
											.summingLong(WorkLog::getNumberOfEquipment));

					item.setNumberOfEquipment(equipmentNumber);

					preventiveItems.add(item);
				}

			}

		}

		return preventiveItems
				.stream()
				.sorted(Comparator
						.comparing(StandingAllElementPreventiveItem::getProtokollNumber))
				.collect(Collectors.toList());
	}

	public List<StandingKostoPerResource> getTaskSheetResource() {
		List<TaskSheet> allTaskSheet = standingDbService.getTaskSheet();

		List<StandingKostoPerResource> kostoItems = new ArrayList<StandingKostoPerResource>();
		for (TaskSheet sheet : allTaskSheet) {
			Preventive preventive = sheet.getPreventive();
			int preventiveId = preventive.getPreventiveId();

			String resourceName = sheet.getResources().getEmri();
			int resourceId = sheet.getResources().getResourceId();
			String description = sheet.getResources().getDescription();
			String lloji = sheet.getResources().getResourceType().getEmri();
			String unitName = sheet.getUnit().getUnitName();

			long count = kostoItems.stream()
					.filter(s -> s.getResource().equals(resourceName)).count();
			if (count == 0) {

				StandingKostoPerResource item = new StandingKostoPerResource();
				item.setResource(resourceName);
				item.setDescription(description);
				item.setLloji(lloji);

				List<Preventive> collect = allTaskSheet
						.stream()
						.filter(s -> s.getResources().getResourceId() == resourceId)
						.map(TaskSheet::getPreventive).distinct()
						.collect(Collectors.toList());
				item.setPreventive(collect);

				long frequence = allTaskSheet
						.stream()
						.filter(s -> s.getResources().getResourceId() == resourceId)
						.filter(s -> s.getPreventive().isHide() == true)
						.map(TaskSheet::getPreventive)
						.map(Preventive::getPreventiveId).distinct().count();
				item.setFrequence(frequence);

				double totalDuration = allTaskSheet
						.stream()
						.filter(s -> s.getResources().getResourceId() == resourceId)
						.filter(s -> s.getPreventive().isHide() == true)
						.collect(
								Collectors
										.summingDouble(TaskSheet::getDuration));

				item.setTotalDuration(totalDuration);

				Double totalQuantity = allTaskSheet
						.stream()
						.filter(s -> s.getResources().getResourceId() == resourceId)
						.filter(s -> s.getPreventive().isHide() == true)
						.collect(
								Collectors
										.summingDouble(TaskSheet::getQuantity));

				item.setTotalQuantity(totalQuantity);

				item.setUnitName(unitName);

				kostoItems.add(item);

			}

		}

		return kostoItems
				.stream()
				.sorted(Comparator
						.comparing(StandingKostoPerResource::getResource))
				.collect(Collectors.toList());

	}
}
