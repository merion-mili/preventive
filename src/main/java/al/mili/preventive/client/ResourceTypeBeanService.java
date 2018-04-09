package al.mili.preventive.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import al.mili.preventive.db.model.Customer;
import al.mili.preventive.db.model.ResourceType;
import al.mili.preventive.db.service.CustomerDbService;
import al.mili.preventive.db.service.ResourceTypeDbService;

@ManagedBean(name = "resourceTypeBeanService", eager = true)
@ViewScoped
public class ResourceTypeBeanService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 167047219448056781L;
	private ResourceType type;
	private List<ResourceType> resourceType;
	private ResourceTypeDbService resourceTypeDbService;

	@PostConstruct
	public void init() {

		resourceTypeDbService = new ResourceTypeDbService();
		this.resourceType=resourceTypeDbService.getResourceType();
		
	}
	
	
	
	public List<ResourceType> getResourceType() {
		return this.resourceType;
	}


	public void setResourceType(List<ResourceType> resourceType) {
		this.resourceType = resourceType;
	}





	public ResourceTypeDbService getResourceTypeDbService() {
		return resourceTypeDbService;
	}

	public void setResourceTypeDbService(
			ResourceTypeDbService resourceTypeDbService) {
		this.resourceTypeDbService = resourceTypeDbService;
	}

	public void addResourceType(String emri) {
		resourceTypeDbService.addResourceType(emri);
	}

	public boolean update(int typeId, String emri){
		return resourceTypeDbService.update(typeId, emri);
	}

	public boolean delete(int typeId) {
		return resourceTypeDbService.delete(typeId);
	}



	public ResourceType getType() {
		return type;
	}



	public void setType(ResourceType type) {
		this.type = type;
	}

}
