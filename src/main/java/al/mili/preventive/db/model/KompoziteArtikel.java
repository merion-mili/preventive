package al.mili.preventive.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "KOMPO_ARTIKULL")
public class KompoziteArtikel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8913414017144397499L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "KOMPO_ID")
	private int kompoid;
	@Column(name = "QUANTITY")
	private double quantity;
	@Column(name = "PRICE")
	private double price;
	
	@Transient
	private boolean selected;

	@ManyToOne
	@JoinColumn(name = "ARTIKULL_ID")
	private Artikull artikull;

	public KompoziteArtikel() {
		
	}

	public int getKompoid() {
		return kompoid;
	}

	public void setKompoid(int kompoid) {
		this.kompoid = kompoid;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Artikull getArtikull() {
		return artikull;
	}

	public void setArtikull(Artikull artikull) {
		this.artikull = artikull;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
