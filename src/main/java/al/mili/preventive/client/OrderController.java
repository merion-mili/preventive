package al.mili.preventive.client;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import al.mili.preventive.db.model.Artikull;
import al.mili.preventive.db.model.KompoziteArtikel;
import al.mili.preventive.db.model.Order;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.service.ArtikullDbService;
import al.mili.preventive.db.service.OrderDbService;

@ManagedBean(name = "orderBeanController")
@ViewScoped
public class OrderController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8688041212109597604L;
	private List<Order> orderList;
	private OrderBean selectedOrder;
	private Order order;
	private OrderBean orderBean;
	private List<OrderBean> listOrderBean;
	private List<OrderBean> filteredOrders;
	private List<Unit> units;
	private BigDecimal discount;
	private List<Artikull> listOfArtikull;
	private List<OrderBean> selectedOrderItems;
	private TrackingTableItem trackingTableItem;
	private List<TrackingTableItem> trackingTableItems;
	private List<KompoziteArtikel> artikujtEKompozuar;
	private Preventive preventive;
	private boolean flag;

	private boolean disable;

	private String total;
	private String totalPriceKompozim;
	private String totalDiscount;
	private String totalPriceDiscount;

	@ManagedProperty("#{preventiveBeanService}")
	private PreventiveBeanService preventiveBeanService;

	@ManagedProperty("#{orderBeanService}")
	private OrderBeanService orderBeanService;

	@ManagedProperty("#{unitBeanService}")
	private UnitBeanService unitBeanService;

	@ManagedProperty("#{artikullBeanService}")
	private ArtikullBeanService artikullBeanService;

	// private ArtikullDbService artikullDbService;
	// private OrderDbService orderDbService;

	@ManagedProperty("#{trackingService}")
	private TrackingService trackingService;

	@PostConstruct
	public void init() {
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		this.preventive = (Preventive) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("preventive");
		// this.orderDbService = new OrderDbService();
		units = unitBeanService.getUnits();

		this.orderBean = new OrderBean();
		reloadAndCalculateArticlePrice(this.preventive);

	}

	public List<Artikull> getListOfArtikull() {
		this.listOfArtikull = artikullBeanService.getAllArtikull();
		return this.listOfArtikull;
	}

	public void setListOfArtikull(List<Artikull> listOfArtikull) {
		this.listOfArtikull = listOfArtikull;
	}

	public List<OrderBean> getListOrderBean() {
		return listOrderBean;
	}

	public void setListOrderBean(List<OrderBean> listOrderBean) {
		this.listOrderBean = listOrderBean;
	}

	public PreventiveBeanService getPreventiveBeanService() {
		return preventiveBeanService;
	}

	public void setPreventiveBeanService(
			PreventiveBeanService preventiveBeanService) {
		this.preventiveBeanService = preventiveBeanService;
	}

	public void setOrderBeanService(OrderBeanService orderBeanService) {
		this.orderBeanService = orderBeanService;
	}

	public OrderBeanService getOrderBeanService() {
		return orderBeanService;
	}

	public ArtikullBeanService getArtikullBeanService() {
		return artikullBeanService;
	}

	public void setArtikullBeanService(ArtikullBeanService artikullBeanService) {
		this.artikullBeanService = artikullBeanService;
	}

	public List<Order> getOrderList() {
		return this.orderList;
	}

	public String getTotal() {
		return this.total;
	}

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	public List<OrderBean> getOrderBeans() {

		return listOrderBean;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderBean> getFilteredOrders() {
		return filteredOrders;
	}

	public void setFilteredOrders(List<OrderBean> filteredOrders) {
		this.filteredOrders = filteredOrders;
	}

	public UnitBeanService getUnitBeanService() {
		return this.unitBeanService;
	}

	public void setUnitBeanService(UnitBeanService unitBeanService) {
		this.unitBeanService = unitBeanService;
	}

	public List<Unit> getUnits() {

		return this.units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public BigDecimal getDiscont() {
		return this.discount;
	}

	public void setDiscont(BigDecimal discount) {
		this.discount = discount;
	}

	public String getTotalDiscount() {
		return this.totalDiscount;
	}

	public OrderBean getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(OrderBean selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public String getTotalPriceDiscount() {
		return this.totalPriceDiscount;
	}

	public void setTotalPriceDiscount(String totalPriceDiscount) {
		this.totalPriceDiscount = totalPriceDiscount;
	}

	private List<OrderBean> convertPreventivesList(List<Order> listOrder) {
		List<OrderBean> listOrderBean = new ArrayList<OrderBean>();
		for (Order order : listOrder) {
			OrderBean orderBean = new OrderBean();
			orderBean.setOrderId(order.getOrderId());
			orderBean.setTrackingDate(order.getTrackingDate());
			orderBean.setArtikull(order.getArtikull());
			orderBean.setUnit(order.getUnit());
			orderBean.setPreventive(order.getPreventive());
			orderBean.setPrice(order.getPrice());
			orderBean.setQuantity(order.getQuantity());
			orderBean.setTrackQuantity(order.getTrackQuantity());
			orderBean.setTotalPrice(order.getTotalPrice());
			orderBean.setPercent(order.getPercent());
			orderBean.setDiscount(order.getDiscount());
			orderBean.setGjendje(order.getGjendje());
			orderBean.setTotalAfter(order.getTotalAfter());
			orderBean.setPrevortrack(order.isPrevortrack());
			orderBean.setFlag(order.isFlag());
			listOrderBean.add(orderBean);
		}
		return listOrderBean;
	}

	private String calculateTotalPrice() {
		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal totalDiscount = BigDecimal.ZERO;

		// Preventive preventive = this.selectedItems.get(0);

		for (Order order : getOrderList()) {
			totalPrice = totalPrice.add(order.getTotalPrice());
			totalDiscount = totalDiscount.add(order.getDiscount());

		}

		BigDecimal totalPriceWithDiscount = totalPrice.subtract(totalDiscount);

		preventiveBeanService.updateVlera(preventive.getPreventiveId(),
				totalPriceWithDiscount);

		this.total = new DecimalFormat("###,###.00").format(totalPrice);

		return this.total;

	}

	private String calculateTotalPricewithDiscount() {
		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal totalDiscount = BigDecimal.ZERO;

		// Preventive preventive = this.selectedItems.get(0);

		for (Order order : getOrderList()) {
			totalPrice = totalPrice.add(order.getTotalPrice());
			totalDiscount = totalDiscount.add(order.getDiscount());

		}

		BigDecimal totalPriceWithDiscount = totalPrice.subtract(totalDiscount);
		this.totalPriceDiscount = new DecimalFormat("###,###.00")
				.format(totalPriceWithDiscount);

		return this.totalPriceDiscount;

	}

	private String calculateTotalDiscount() {
		BigDecimal totalDiscount = BigDecimal.ZERO;

		// Preventive preventive = this.selectedItems.get(0);

		for (OrderBean order : getOrderBeans()) {
			totalDiscount = totalDiscount.add(order.getDiscount());

		}

		this.totalDiscount = new DecimalFormat("###,###.00")
				.format(totalDiscount);

		return this.totalDiscount;
	}

	public void selectedOrder() throws IOException {

		selectedOrderItems = new ArrayList<OrderBean>();
		for (OrderBean orderItem : listOrderBean) {
			if (orderItem.isChecked()) {

				selectedOrderItems.add(orderItem);

			} else {
				orderItem.setChecked(false);
			}

		}

		int preventiveId = selectedOrderItems.get(0).getPreventive()
				.getPreventiveId();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("preventiveId", preventiveId);
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		if (user.getRole().getRole().equals("admin")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("./trackingStand1.xhtml");
		} else {
			return;
		}

	}

	public String getWorkLogPrevetnive() {

		selectedOrderItems = new ArrayList<OrderBean>();
		for (OrderBean orderItem : listOrderBean) {
			if (orderItem.isChecked()) {

				selectedOrderItems.add(orderItem);

			} else {
				orderItem.setChecked(false);
			}

		}

		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		if (!selectedOrderItems.isEmpty()
				&& user.getRole().getRole() != "admin") {
			Preventive preventive = selectedOrderItems.get(0).getPreventive();

			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("preventive", preventive);

			return "editWorkLog.xhtml?faces-redirect=true";
		}

		return "editOrder";

	}

	public String getTaskSheetPrevetnive() {
		selectedOrderItems = new ArrayList<OrderBean>();
		for (OrderBean orderItem : listOrderBean) {
			if (orderItem.isChecked()) {

				selectedOrderItems.add(orderItem);

			} else {
				orderItem.setChecked(false);
			}

		}
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		if (!selectedOrderItems.isEmpty()
				&& user.getRole().getRole() != "admin") {
			Preventive preventive = selectedOrderItems.get(0).getPreventive();

			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("preventive", preventive);

			return "editTaskSheet.xhtml?faces-redirect=true";
		}

		return "editOrder";

	}

	public void getAllOrders() {
		try {
			// Preventive preventive = this.selectedItems.get(0);
			orderBeanService.getOrders(preventive.getPreventiveId());
			reloadAndCalculateArticlePriceForAll(this.preventive);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void reloadAndCalculateArticlePriceForAll(Preventive preventive) {
		orderList = orderBeanService.getOrders(preventive.getPreventiveId());
		listOrderBean = convertPreventivesList(orderList);
		calculateTotalPrice();
		calculateTotalDiscount();
		calculateTotalPricewithDiscount();

	}

	public void addAction() throws HibernateException,
			ConstraintViolationException {
		try {
			// Preventive preventive = this.selectedItems.get(0);
			Artikull artikull = this.orderBean.getArtikull();
			if (artikull != null) {

				orderBeanService.addOrder(null, this.orderBean.getArtikull(),
						this.orderBean.getUnit(), this.orderBean.getQuantity(),
						BigDecimal.ZERO, this.orderBean.getPrice(),
						this.orderBean.getTotalPrice(), this.preventive,
						this.orderBean.getPercent(),
						this.orderBean.getDiscount(), true, false);
				reloadAndCalculateArticlePrice(this.preventive);
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Artikull "
								+ orderBean.getArtikull().getDescription()
								+ " u ruajt", null);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (ConstraintViolationException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Gabim ne entry:Objekti Ekziston.", "Duplicated Entry."));
		} catch (HibernateException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERROR! Access Database",
					"Failure to include data"));

		}

	}

	public void addTrack() throws HibernateException {
		try {
			Artikull artikull = this.orderBean.getArtikull();
			if (artikull != null) {
				// Preventive preventive = this.selectedItems.get(0);
				orderBeanService.addTracker(this.orderBean.getTrackingDate(),
						this.orderBean.getArtikull(), this.orderBean.getUnit(),
						this.orderBean.getTrackQuantity(), BigDecimal.ZERO,
						BigDecimal.ZERO, this.preventive, BigDecimal.ZERO,
						BigDecimal.ZERO, true, true);
				reloadAndCalculateArticlePrice(this.preventive);

			}
		} catch (HibernateException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERROR! Access Database",
					"Failure to include data"));

		}

	}

	public void initDialog() {
		this.orderBean.setArtikull(null);
		this.orderBean.setTrackingDate(null);
		this.orderBean.setQuantity(BigDecimal.ZERO);
		this.orderBean.setPrice(BigDecimal.ZERO);
		this.orderBean.setPercent(BigDecimal.ZERO);
		this.orderBean.setTrackQuantity(BigDecimal.ZERO);

		RequestContext.getCurrentInstance().reset(":Form1:addArticlPreventive");
	}

	private void reloadAndCalculateArticlePrice(Preventive preventive) {

		orderList = orderBeanService
				.getOrderForPreventiveId(preventive.getPreventiveId(), true)
				.stream().sorted(Comparator.comparing(Order::isPrevortrack))
				.collect(Collectors.toList());
		listOrderBean = convertPreventivesList(orderList);
		if (listOrderBean.size() > 0) {
			disable = false;
		} else {

			disable = true;

		}
		calculateTotalPrice();
		calculateTotalDiscount();
		calculateTotalPricewithDiscount();

	}

	public void deleteOrder() {
		// Preventive preventive = this.selectedItems.get(0);

		orderBeanService.delete(selectedOrder.getOrderId(), false);
		reloadAndCalculateArticlePrice(this.preventive);

		FacesMessage msg = new FacesMessage("Article  "
				+ selectedOrder.getArtikull().getName() + " has been deleted");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void deleteTrack() {

		orderBeanService.deleteTrack(selectedOrder.getOrderId(), true, true);
		reloadAndCalculateArticlePrice(this.preventive);

		FacesMessage msg = new FacesMessage("Article  "
				+ selectedOrder.getArtikull().getName() + " has been deleted");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			DataTable s = (DataTable) event.getSource();
			OrderBean orderBean = (OrderBean) s.getRowData();
			orderBeanService.update(orderBean.getOrderId(),
					orderBean.getTrackingDate(), orderBean.getArtikull(),
					orderBean.getUnit(), orderBean.getQuantity(),
					orderBean.getPrice(), orderBean.getTotalPrice(),
					orderBean.getPercent(), orderBean.getDiscount(),
					orderBean.getGjendje(), orderBean.getTotalAfter(),
					orderBean.isFlag(), orderBean.isPrevortrack());

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cell Changed", "Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);

			cell.setCellStyle(cellStyle);
		}
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public List<OrderBean> getSelectedOrderItems() {
		return selectedOrderItems;
	}

	public void setSelectedOrderItems(List<OrderBean> selectedOrderItems) {
		this.selectedOrderItems = selectedOrderItems;
	}

	public TrackingService getTrackingService() {
		return trackingService;
	}

	public void setTrackingService(TrackingService trackingService) {
		this.trackingService = trackingService;
	}

	public TrackingTableItem getTrackingTableItem() {
		return trackingTableItem;
	}

	public void setTrackingTableItem(TrackingTableItem trackingTableItem) {
		this.trackingTableItem = trackingTableItem;
	}

	public List<TrackingTableItem> getTrackingTableItems() {
		return this.trackingTableItems;
	}

	public void setTrackingTableItems(List<TrackingTableItem> trackingTableItems) {
		this.trackingTableItems = trackingTableItems;
	}

	public Preventive getPreventive() {
		return preventive;
	}

	public void setPreventive(Preventive preventive) {
		this.preventive = preventive;
	}

	public boolean isFlag() {
		return this.flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<KompoziteArtikel> getArtikujtEKompozuar() {
		if (this.selectedOrder == null) {
			return new ArrayList<KompoziteArtikel>();
		}
		this.artikujtEKompozuar = artikullBeanService
				.getArtikujtEKompozuar(selectedOrder.getArtikull()
						.getArtikullId());
		calculateTotalPriceOfKompozim();
		return this.artikujtEKompozuar;
	
	}

	public void setArtikujtEKompozuar(List<KompoziteArtikel> artikujtEKompozuar) {
		this.artikujtEKompozuar = artikujtEKompozuar;
	}

	public String getTotalPriceKompozim() {
		return totalPriceKompozim;
	}

	public void setTotalPriceKompozim(String totalPriceKompozim) {
		this.totalPriceKompozim = totalPriceKompozim;
	}
	
	private String calculateTotalPriceOfKompozim() {
		double totalPrice = 0;
		

		for (KompoziteArtikel kompo : selectedOrder.getArtikull().getKompozimet()) {
			double quantity = kompo.getQuantity();
			double price = kompo.getPrice();
			totalPrice = totalPrice+(quantity*price);
		}

		
		this.totalPriceKompozim = new DecimalFormat("###,###.00").format(totalPrice);

		return this.totalPriceKompozim;

	}

}
