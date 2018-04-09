package al.mili.preventive.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ARTIKULL", uniqueConstraints = { @UniqueConstraint(columnNames = { "EMRI" }) })
@NamedQueries({
		@NamedQuery(name = "queryAllArtikull", query = "from Artikull"),
		@NamedQuery(name = "queryAllArtikullByType", query = "from Artikull a where a.type=:type"),
		@NamedQuery(name = "queryDeleteArtikull", query = "DELETE from Artikull a WHERE a.artikullId=:artikullId"), })
public class Artikull implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7107326755752588554L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ARTIKULL_ID")
	private int artikullId;

	@Column(name = "EMRI")
	private String name;

	@Column(name = "KLASIFIKIMI")
	private String description;
	
	@Column(name = "TYPE")
	private String type;

	/*@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "KOMPOZIM_ARTIKULL", joinColumns = { @JoinColumn(name = "ARTIKULL_ID") }, 
	inverseJoinColumns = @JoinColumn(name = "KOMPOZIM_ID", insertable = true, updatable = true), 
	uniqueConstraints = { @UniqueConstraint(columnNames = {
			"ARTIKULL_ID", "KOMPOZIM_ID" }) })*/
	
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	private List<KompoziteArtikel> kompozimet = new ArrayList<KompoziteArtikel>();
	
	
	

	public Artikull() {

	}

	public int getArtikullId() {
		return artikullId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<KompoziteArtikel> getKompozimet() {
		return kompozimet;
	}

	public void setKompozimet(List<KompoziteArtikel> kompozimet) {
		this.kompozimet = kompozimet;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}