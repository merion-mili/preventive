package al.mili.preventive.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import al.mili.preventive.db.model.Order;
import al.mili.preventive.db.service.OrderDbService;

@ManagedBean(name = "trackingService")
@SessionScoped
public class TrackingService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5880673252392819864L;
	private List<Order> orderList;
	private OrderDbService orderDbService;

	@PostConstruct
	public void init() {

		System.out.println("Int call: ");
		orderDbService = new OrderDbService();

	}

	
	
	
	public List<TrackingTableItem> getOrderForPreventiveId(int preventiveId,boolean flag, boolean prevortrack){
		List<Order> orders = orderDbService.getOrderForPreventiveIdBoolean(preventiveId, flag, prevortrack);
			

		List<TrackingTableItem> trackingtems = new ArrayList<TrackingTableItem>();

		for (Order order : orders) {

			String artikullName = order.getArtikull().getName();
			int artikullId = order.getArtikull().getArtikullId();
			BigDecimal quantity = order.getQuantity();
			

			long count = trackingtems.stream()
					.filter(s -> s.getArtikull().equals(artikullName)).count();
			if (count == 0) {

				TrackingTableItem item = new TrackingTableItem();
				item.setArtikull(artikullName);
		

				long frequence = orders.stream()
						.filter(s -> s.getArtikull().getArtikullId()==artikullId)
						.count();
				item.setFrequency(frequence);
				
				Date lastDate = orders.stream()
				.filter(s -> s.getArtikull().getArtikullId() == artikullId)
				.sorted(Comparator.comparing(Order::getTrackingDate))
				.reduce((a, b) -> b)
				.map(Order::getTrackingDate)
				.orElse((Date) null);
				
				item.setDate(lastDate);
				
				Date lastTrackDate = orders.stream()
				.sorted(Comparator.comparing(Order::getTrackingDate))
				.reduce((a, b) -> b)
				.map(Order::getTrackingDate)
				.orElse((Date) null);
				
				Date firstTrackDate = orders.stream()
						.sorted(Comparator.comparing(Order::getTrackingDate))
						.map(Order::getTrackingDate).findFirst()
						.orElse((Date) null);
				
				 int diffInDays = (int) ((lastTrackDate.getTime() - firstTrackDate.getTime()) / (1000 * 60 * 60 * 24));
				
				 item.setDiffInDays(diffInDays);
				
				item.setQuantity(quantity);
				
				 BigDecimal totalAfter = orders.stream()
							.filter(s -> s.getArtikull().getArtikullId() == artikullId)
							.sorted(Comparator.comparing(Order::getTrackingDate))
							.map(Order::getTotalAfter)
							.reduce((a, b) -> b)
							.orElse(BigDecimal.ZERO);
					
					item.setTotalAfter(totalAfter);
					

				BigDecimal totalTrackingQuanity = orders
							.stream()
							.filter(s -> s.getArtikull().getArtikullId() == artikullId)
							.map(Order::getTrackQuantity)
							.reduce(BigDecimal.ZERO, BigDecimal::add);	
				
				
				item.setTrackingQuanity(totalTrackingQuanity);
				
				
				BigDecimal multiply = (totalTrackingQuanity.divide(quantity, 2,RoundingMode.HALF_DOWN)).multiply(new BigDecimal(100));
				
				int intValue = multiply.intValue();
				item.setProgress(intValue);


				trackingtems.add(item);
			}

		}

		return trackingtems;
	}
	
	
	public List<Order> getOrderForPreventiveIdBoolean(int preventiveId, boolean flag,
			boolean prevortrack) {
		this.setOrderList(orderDbService.getOrdersForPreventiveIdBoolean(preventiveId, flag, prevortrack));
		return this.getOrderList();
	}




	public List<Order> getOrderList() {
		return orderList;
	}




	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	

}
