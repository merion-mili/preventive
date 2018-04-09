package al.mili.preventive.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "PREVENTIVES_ARTICLES")
@NamedQueries({
		@NamedQuery(name = "queryAllArtikleForPreventive", query = "from Order o where o.preventive.preventiveId=:preventiveId"),
		@NamedQuery(name = "queryDeleteOrder", query = "UPDATE from Order o SET o.flag=:flag WHERE o.orderId=:orderId"),
		@NamedQuery(name = "queryUpdateOrder", query = "UPDATE Order o SET o.trackingDate=:trackingDate, o.artikull=:artikull, o.unit=:unit, o.quantity=:quantity, o.price=:price, o.totalPrice=:totalPrice, o.percent=:percent, o.discount=:discount, o.gjendje=:gjendje,o.totalAfter=:totalAfter, o.flag=:flag, o.prevortrack=:prevortrack WHERE o.orderId=:orderId"),
		@NamedQuery(name = "queryDeleteTracker", query = "DELETE from Order o WHERE o.orderId=:orderId AND o.trackingDate=:trackingDate") })
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4831508489708689015L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ID")
	private int orderId;
	@Column(name = "TRACKING_DATE")
	private Date trackingDate;
	@ManyToOne
	@JoinColumn(name = "ARTIKULL_ID")
	private Artikull artikull;
	@ManyToOne
	@JoinColumn(name = "UNIT_ID")
	private Unit unit;
	@Column(name = "QUANTITY")
	private BigDecimal quantity;
	@Column(name = "TRACK_QUANTITY")
	private BigDecimal trackQuantity;
	@Column(name = "PRICE")
	private BigDecimal price;
	@Column(name = "TOTAL_PRICE")
	private BigDecimal totalPrice;
	@Column(name = "PERCENT")
	private BigDecimal percent;
	@Column(name = "DISCOUNT")
	private BigDecimal discount;

	@Column(name = "FLAG")
	@Type(type = "boolean")
	private boolean flag;

	@Column(name = "PREV_TRACK")
	@Type(type = "boolean")
	private boolean prevortrack;

	@Column(name = "GJENDJE")
	private BigDecimal gjendje;

	@Column(name = "TOTAL_AFTER")
	private BigDecimal totalAfter;

	@ManyToOne
	@JoinColumn(name = "PREVENTIVE_ID")
	private Preventive preventive;
	
	@Transient
	private boolean checked;

	public Order() {

	}

	public int getOrderId() {
		return orderId;
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

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Preventive getPreventive() {
		return preventive;
	}

	public void setPreventive(Preventive preventive) {
		this.preventive = preventive;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public BigDecimal getDiscount() {
		return discount;
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
	
	
	
	

}
