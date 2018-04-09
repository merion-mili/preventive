package al.mili.preventive.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import al.mili.preventive.db.model.Artikull;
import al.mili.preventive.db.model.Order;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Unit;
import al.mili.preventive.db.service.OrderDbService;

@ManagedBean(name = "orderBeanService")
@ViewScoped
public class OrderBeanService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6703642510603119330L;
	private List<Order> orderList;
	OrderDbService orderDbService;

	@PostConstruct
	public void init() {

		orderDbService = new OrderDbService();

	}

	public List<Order> getOrderForPreventiveId(int preventiveId, boolean flag) {
		this.orderList = orderDbService.getOrderForPreventiveId(preventiveId, flag);
		return this.orderList;
	}
	

	
	public boolean delete(int orderId,boolean flag){
		return orderDbService.delete(orderId,flag);
	}
	
	public boolean update(int orderId,Date trackingDate, Artikull artikull, Unit unit,
			BigDecimal quantity, BigDecimal priceUnit, BigDecimal priceQuantity, BigDecimal percent, BigDecimal discount,BigDecimal gjendje, BigDecimal totalAfter,  boolean flag, boolean prevortrack){
		return orderDbService.update(orderId,trackingDate,artikull,unit, quantity, priceUnit, priceQuantity,percent,discount, gjendje, totalAfter, flag, prevortrack);
	}
	
	
	public void addOrder(Date date, Artikull artikull, Unit unit, BigDecimal quantity, BigDecimal trackQuantity,
			BigDecimal price, BigDecimal totalPriceQuantity, Preventive preventive,BigDecimal percent, BigDecimal discount, boolean flag, boolean prevortrack) {
		orderDbService.addOrder(date, artikull, unit, quantity, trackQuantity, price, totalPriceQuantity, preventive, percent, discount, flag, prevortrack);
	}
	
	
	public void addTracker(Date trackingDate, Artikull artikull, Unit unit, BigDecimal trackQuantity,
			BigDecimal price, BigDecimal totalPriceQuantity, Preventive preventive,BigDecimal percent, BigDecimal discount,
			boolean flag, boolean prevortrack)  {
		orderDbService.addTracker(trackingDate, artikull, unit, trackQuantity, price, totalPriceQuantity, 
				preventive,percent, discount, flag,  prevortrack);
	}
	
	public List<Artikull> getArtikullForPreventiveId(int preventiveId, boolean flag, boolean prevortrack){
			return orderDbService.getArtikullForPreventiveId(preventiveId, flag, prevortrack);
	}
	
	
	public List<Order> getOrders(int preventiveId){
		return orderDbService.getOrders(preventiveId);
	}

	public boolean deleteTrack(int orderId, boolean flag, boolean prevortrack) {
		return orderDbService.deleteTrack(orderId,flag,prevortrack);
		
	}

	
	
	
}
