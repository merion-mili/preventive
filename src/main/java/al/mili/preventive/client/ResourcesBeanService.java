package al.mili.preventive.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import al.mili.preventive.db.model.ResourceType;
import al.mili.preventive.db.model.Resources;
import al.mili.preventive.db.service.ResourcesDbService;

@ManagedBean(name = "resourcesBeanService", eager=true)
@ViewScoped
public class ResourcesBeanService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1155427931333084922L;
	private Resources resource;
	private List<Resources> resources;
	private ResourcesDbService resourcesDbService;

	@PostConstruct
	public void init() {

		this.resourcesDbService = new ResourcesDbService();
		this.resources = resourcesDbService.getAllResources();
		
	}
	
	

	public List<Resources> getResources() {
		
		return this.resources;
	}
	


	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}

	public ResourcesDbService getResourcesDbService() {
		return resourcesDbService;
	}

	public void setResourcesDbService(ResourcesDbService resourcesDbService) {
		this.resourcesDbService = resourcesDbService;
	}

	public boolean delete(int resourceId) {
		return resourcesDbService.delete(resourceId);

	}
	
	public boolean update(int resourceId, String emri, String description, ResourceType resourceType){
		try {
		return resourcesDbService.update(resourceId, emri, description, resourceType);
		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");
		}catch (HibernateException e) {
			throw e;
		}
	}
	
	public void addResource(String emri, String description, ResourceType resourceType) {
		try {
			resourcesDbService.addResource(emri, description, resourceType);
		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");

		}catch (HibernateException e) {
			throw e;
		}
	}
	
	
	public List<Resources> getResourcesForType(ResourceType resourceType){
		this.resources= resourcesDbService.getResourcesForType(resourceType);
		return this.resources;
	
	}



	public Resources getResource() {
		return resource;
	}



	public void setResource(Resources resource) {
		this.resource = resource;
	}
	
	

}
