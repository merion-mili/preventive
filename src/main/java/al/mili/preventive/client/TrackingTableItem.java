package al.mili.preventive.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TrackingTableItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8544888521898166066L;
	private int tackingId;
	private Date date;
	private String artikull;
	private long frequency;
	private BigDecimal quantity;
	private BigDecimal trackingQuanity;
	private BigDecimal totalAfter;
	private Integer progress;
	private int diffInDays;

	public TrackingTableItem() {

	}

	public int getTackingId() {
		return tackingId;
	}

	public void setTackingId(int tackingId) {
		this.tackingId = tackingId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTrackingQuanity() {
		return trackingQuanity;
	}

	public void setTrackingQuanity(BigDecimal trackingQuanity) {
		this.trackingQuanity = trackingQuanity;
	}

	public BigDecimal getTotalAfter() {
		return totalAfter;
	}

	public void setTotalAfter(BigDecimal totalAfter) {
		this.totalAfter = totalAfter;
	}

	public String getArtikull() {
		return artikull;
	}

	public void setArtikull(String artikull) {
		this.artikull = artikull;
	}

	public long getFrequency() {
		return frequency;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

	public Integer getProgress() {

		return progress;

	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public int getDiffInDays() {
		return diffInDays;
	}

	public void setDiffInDays(int diffInDays) {
		this.diffInDays = diffInDays;

	}

}
