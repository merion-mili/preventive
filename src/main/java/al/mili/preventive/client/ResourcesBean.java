package al.mili.preventive.client;

import java.io.Serializable;

import al.mili.preventive.db.model.ResourceType;

public class ResourcesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5695182884931167235L;

	private int resourceId;
	private String emri;
	private String description;
	private ResourceType resourceType;

	public ResourcesBean() {

	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
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
