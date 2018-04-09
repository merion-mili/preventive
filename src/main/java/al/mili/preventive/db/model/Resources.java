package al.mili.preventive.db.model;

import java.io.Serializable;

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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "RESOURCE", uniqueConstraints = {@UniqueConstraint(columnNames = {"EMRI"}) })
@NamedQueries({
	@NamedQuery(name="queryAllResource", query= "from Resources"),
	@NamedQuery(name = "queryDeleteResource", query = "DELETE from Resources rsource WHERE rsource.resourceId=:resourceId"),
	@NamedQuery(name = "queryUpdateResource", query = "UPDATE Resources rsource SET rsource.emri=:emri, rsource.description=:description, rsource.resourceType=:resourceType WHERE rsource.resourceId=:resourceId")})
public class Resources implements Serializable{
	
	
	private static final long serialVersionUID = 9031879078206504967L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESOURCE_ID")
	private int resourceId;
	
	@Column(name = "EMRI")
	private String emri;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "TYPE_ID")
	private ResourceType resourceType;
	
	public Resources() {
		
	}

	public int getResourceId() {
		return resourceId;
	}

	
	public String getEmri() {
		return emri;
	}

	public void setEmri(String emri) {
		this.emri = emri;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
	
	
	

}
