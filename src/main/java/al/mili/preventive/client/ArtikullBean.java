package al.mili.preventive.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import al.mili.preventive.db.model.Artikull;
import al.mili.preventive.db.model.KompoziteArtikel;
public class ArtikullBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7996837125866210262L;
	private int artikullId;
	// private String code;
	private String name;
	private String description;
	private List<KompoziteArtikel> kompozimet = new ArrayList<KompoziteArtikel>();
	private String type;

	
	public ArtikullBean() {
		// TODO Auto-generated constructor stub
	}

	public int getArtikullId() {
		return artikullId;
	}

	public void setArtikullId(int artikullId) {
		this.artikullId = artikullId;
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
