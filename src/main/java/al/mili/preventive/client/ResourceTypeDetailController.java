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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import al.mili.preventive.db.model.Customer;
import al.mili.preventive.db.model.ResourceType;

@ManagedBean(name = "resourceTypeDetailController")
@RequestScoped
public class ResourceTypeDetailController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4665999359138568380L;
	private List<ResourceType> types;
	private List<ResourceTypeBean> typesBeans;

	private ResourceTypeBean typeBean;
	private ResourceTypeBean selectedType;
	

	@ManagedProperty("#{resourceTypeBeanService}")
	private ResourceTypeBeanService resourceTypeBeanService;

	@PostConstruct
	public void init() {

		this.typeBean = new ResourceTypeBean();

		types = resourceTypeBeanService.getResourceType();
		this.typesBeans = convertResourceTypeList(types).stream()
				.sorted(Comparator.comparing(ResourceTypeBean::getTypeId))
				.collect(Collectors.toList());
	}

	public List<ResourceType> getTypes() {
		return types;
	}

	public void setTypes(List<ResourceType> types) {
		this.types = types;
	}

	public List<ResourceTypeBean> getTypesBeans() {
		return this.typesBeans;
	}

	public void setTypesBeans(List<ResourceTypeBean> typesBeans) {
		this.typesBeans = typesBeans;
	}

	public ResourceTypeBean getTypeBean() {
		return typeBean;
	}

	public void setTypeBean(ResourceTypeBean typeBean) {
		this.typeBean = typeBean;
	}

	public ResourceTypeBean getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(ResourceTypeBean selectedType) {
		this.selectedType = selectedType;
	}

	

	public ResourceTypeBeanService getResourceTypeBeanService() {
		return resourceTypeBeanService;
	}

	public void setResourceTypeBeanService(
			ResourceTypeBeanService resourceTypeBeanService) {
		this.resourceTypeBeanService = resourceTypeBeanService;
	}

	private List<ResourceTypeBean> convertResourceTypeList(
			List<ResourceType> list) {
		List<ResourceTypeBean> listResourceTypeBean = new ArrayList<ResourceTypeBean>();
		for (ResourceType type : list) {
			ResourceTypeBean typeBean = new ResourceTypeBean();
			typeBean.setTypeId(type.getTypeId());
			typeBean.setEmri(type.getEmri());

			listResourceTypeBean.add(typeBean);
		}
		return listResourceTypeBean;
	}

	public void addAction() {

		resourceTypeBeanService.addResourceType(typeBean.getEmri());

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Customer " + typeBean.getEmri() + " has benn saved", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	public void deleteResourceType() {

		resourceTypeBeanService.delete(selectedType.getTypeId());

		FacesMessage msg = new FacesMessage("ResourceType deleted",
				"ResourceType  " + selectedType.getEmri() + " has been deleted");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			DataTable s = (DataTable) event.getSource();
			ResourceTypeBean type = (ResourceTypeBean) s.getRowData();
			resourceTypeBeanService.update(type.getTypeId(), type.getEmri());

			FacesMessage msg = new FacesMessage("ResourceType u Editua",
					"ResourceType " + type.getEmri() + " u editua");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}

	}

	public void initDialog() {
		this.typeBean.setEmri("");

		RequestContext.getCurrentInstance().reset(":typeForm:addResourceType");
	}

}
