package al.mili.preventive.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import al.mili.preventive.db.model.Artikull;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Unit;

public class OrderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4506565970259931030L;
	private int orderId;
	private Date trackingDate;
	private Artikull artikull;
	private Preventive preventive;
	private Unit unit;
	private BigDecimal quantity;
	private BigDecimal trackQuantity;
	private BigDecimal price;
	private BigDecimal gjendje;
	private BigDecimal totalAfter;
	private BigDecimal totalPrice;
	private BigDecimal percent;
	private BigDecimal discount;

	private boolean flag;
	private boolean prevortrack;
	
	private boolean checked;

	public OrderBean() {

	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Artikull getArtikull() {
		return artikull;
	}

	public void setArtikull(Artikull artikull) {
		this.artikull = artikull;
	}
	
	

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getTotalPrice() {
		totalPrice = this.price.multiply(this.quantity);
		return totalPrice;
	}

	public BigDecimal getPercent() {
		if (percent != null) {
			percent.setScale(2);
		}

		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public BigDecimal getDiscount() {
		if(this.percent!=null){
			this.discount = this.totalPrice.multiply(this.percent.divide(
					new BigDecimal(100.0), 2, RoundingMode.HALF_UP));
		}else{
			this.discount = this.totalPrice.multiply(BigDecimal.ZERO.divide(
					new BigDecimal(100.0), 2, RoundingMode.HALF_UP));
		}
		
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Date getTrackingDate() {
		return trackingDate;
	}

	public void setTrackingDate(Date trackingDate) {
		this.trackingDate = trackingDate;
	}

	public BigDecimal getGjendje() {
		return gjendje;
	}

	public void setGjendje(BigDecimal gjendje) {
		this.gjendje = gjendje;
	}

	public BigDecimal getTotalAfter() {
		return totalAfter;
	}

	public void setTotalAfter(BigDecimal totalAfter) {
		this.totalAfter = totalAfter;
	}

	public boolean isPrevortrack() {
		return prevortrack;
	}

	public void setPrevortrack(boolean prevortrack) {
		this.prevortrack = prevortrack;
	}

	public BigDecimal getTrackQuantity() {
		return trackQuantity;
	}

	public void setTrackQuantity(BigDecimal trackQuantity) {
		this.trackQuantity = trackQuantity;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	
	public Preventive getPreventive() {
		return preventive;
	}

	public void setPreventive(Preventive preventive) {
		this.preventive = preventive;
	}

	
	
}
