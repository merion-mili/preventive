package al.mili.preventive.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="RESOURCE_TYPE", uniqueConstraints={ @UniqueConstraint(columnNames = { "EMRI" }) })
@NamedQueries({
	@NamedQuery(name = "queryDeleteResourceType", query = "DELETE from ResourceType rt WHERE rt.typeId=:typeId"),
	@NamedQuery(name = "queryUpdateResourceType", query = "UPDATE ResourceType rt SET rt.emri=:emri WHERE rt.typeId=:typeId")
	})
public class ResourceType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 662167192156262492L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TYPE_ID")
	private int typeId;
	@Column(name = "EMRI")
	private String emri;
	
	public ResourceType() {
		
	}

	public int getTypeId() {
		return typeId;
	}


	public String getEmri() {
		return emri;
	}

	public void setEmri(String emri) {
		this.emri = emri;
	}
	
	
	
	
}
