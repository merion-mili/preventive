package al.mili.preventive.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import al.mili.preventive.db.model.ResourceType;
import al.mili.preventive.db.model.Resources;

@ManagedBean(name = "resourcesDetailController")
@RequestScoped
public class ResourcesDetailController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4790315681374369853L;
	private List<Resources> resources;
	private List<ResourcesBean> resourcesBeans;
	private List<ResourcesBean> filteredResources;
	private ResourcesBean selectedResource;
	private List<ResourceType> resourceType;

	private ResourcesBean resourceBean;
	private boolean disable;

	@ManagedProperty("#{resourcesBeanService}")
	private ResourcesBeanService resourcesBeanService;

	@ManagedProperty("#{resourceTypeBeanService}")
	private ResourceTypeBeanService resourceTypeBeanService;

	@PostConstruct
	public void init() {

		resources = resourcesBeanService.getResources();
		this.resourcesBeans = convertResourcesList(resources).stream()
				.sorted(Comparator.comparing(ResourcesBean::getResourceId))
				.collect(Collectors.toList());

		this.resourceType = resourceTypeBeanService.getResourceType();
		this.resourceBean = new ResourcesBean();

	}

	public List<Resources> getResources() {
		return resources;
	}

	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}

	public List<ResourcesBean> getResourcesBeans() {
		return resourcesBeans;
	}

	public void setResourcesBeans(List<ResourcesBean> resourcesBeans) {
		this.resourcesBeans = resourcesBeans;
	}

	public List<ResourcesBean> getFilteredResources() {
		return filteredResources;
	}

	public void setFilteredResources(List<ResourcesBean> filteredResources) {
		this.filteredResources = filteredResources;
	}

	public ResourcesBean getSelectedResource() {
		return selectedResource;
	}

	public void setSelectedResource(ResourcesBean selectedResource) {
		this.selectedResource = selectedResource;
	}

	public ResourcesBean getResourceBean() {
		return resourceBean;
	}

	public void setResourceBean(ResourcesBean resourceBean) {
		this.resourceBean = resourceBean;
	}

	public ResourcesBeanService getResourcesBeanService() {
		return resourcesBeanService;
	}

	public void setResourcesBeanService(
			ResourcesBeanService resourcesBeanService) {
		this.resourcesBeanService = resourcesBeanService;
	}

	public ResourceTypeBeanService getResourceTypeBeanService() {
		return resourceTypeBeanService;
	}

	public void setResourceTypeBeanService(
			ResourceTypeBeanService resourceTypeBeanService) {
		this.resourceTypeBeanService = resourceTypeBeanService;
	}

	private List<ResourcesBean> convertResourcesList(List<Resources> list) {
		List<ResourcesBean> listResourceBean = new ArrayList<ResourcesBean>();
		for (Resources resource : list) {
			ResourcesBean resourceBean = new ResourcesBean();
			resourceBean.setResourceId(resource.getResourceId());
			resourceBean.setEmri(resource.getEmri());
			resourceBean.setDescription(resource.getDescription());
			resourceBean.setResourceType(resource.getResourceType());

			listResourceBean.add(resourceBean);
		}
		return listResourceBean;
	}

	public void addResource() throws HibernateException,
			ConstraintViolationException {

		try {
			resourcesBeanService.addResource(resourceBean.getEmri(),
					resourceBean.getDescription(),
					resourceBean.getResourceType());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Resoursi " + resourceBean.getEmri() + " u ruajt", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (ConstraintViolationException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Gabim ne entry:Objekti Ekziston.", "Duplicated Entry."));
		} catch (HibernateException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERROR! Access Database",
					"Failure to include data"));

		}

	}

	public void deleteResource() {

		resourcesBeanService.delete(selectedResource.getResourceId());

		FacesMessage msg = new FacesMessage("Resourcce  "
				+ selectedResource.getEmri() + " has been deleted");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) throws HibernateException,
			ConstraintViolationException {
		try {
			Object oldValue = event.getOldValue();
			Object newValue = event.getNewValue();

			if (newValue != null && !newValue.equals(oldValue)) {
				DataTable s = (DataTable) event.getSource();
				ResourcesBean resourcesBean = (ResourcesBean) s.getRowData();
				resourcesBeanService.update(resourcesBean.getResourceId(),
						resourcesBean.getEmri(),
						resourcesBean.getDescription(),
						resourcesBean.getResourceType());

				if (resourcesBeans.size() > 0) {
					disable = false;
				} else {

					disable = true;

				}

				FacesMessage msg = new FacesMessage("Resource u Editua",
						"Resource " + resourcesBean.getEmri() + " u editua");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (ConstraintViolationException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Gabim ne entry:Objekti Ekziston.", "Duplicated Entry."));
		} catch (HibernateException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERROR! Access Database",
					"Failure to include data"));

		}
	}

	public void initDialog() {
		this.resourceBean.setEmri("");
		this.resourceBean.setDescription("");
		this.resourceBean.setResourceType(null);

		RequestContext.getCurrentInstance().reset(":resourcesForm:addResource");
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public List<ResourceType> getResourceType() {
		return resourceType;
	}

	public void setResourceType(List<ResourceType> resourceType) {
		this.resourceType = resourceType;
	}

}
