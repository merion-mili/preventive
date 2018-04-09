package al.mili.preventive.client;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import al.mili.preventive.db.model.User;

@ManagedBean(name = "menuBean")
@RequestScoped
public class MenuBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1686718521340469210L;
	private MenuModel menuModel;

	public MenuBean() {
		this.menuModel = new DefaultMenuModel();

		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");

		if (user.getRole().getRoleId() == 1) {

			DefaultSubMenu preventivat = new DefaultSubMenu("Preventivat");

			DefaultMenuItem openPreventive = new DefaultMenuItem("Open");
			openPreventive.setCommand("#{menuBean.navigateOpenPreventive}");
			preventivat.addElement(openPreventive);
			DefaultMenuItem editPreventive = new DefaultMenuItem("Edit");
			editPreventive.setCommand("#{menuBean.navigateEditimPrevent}");
			preventivat.addElement(editPreventive);
			DefaultMenuItem logOut = new DefaultMenuItem("Logout");
			logOut.setCommand("#{menuBean.navigateLogout}");
			preventivat.addElement(logOut);

			DefaultSubMenu users = new DefaultSubMenu("User");
			DefaultMenuItem ediUser = new DefaultMenuItem("Edit Users");
			ediUser.setCommand("#{menuBean.navigateEditUsers}");
			users.addElement(ediUser);

			DefaultSubMenu customers = new DefaultSubMenu("Customers");
			DefaultMenuItem editCustomer = new DefaultMenuItem("Edit");
			editCustomer.setCommand("#{menuBean.navigateEditCustomers}");
			customers.addElement(editCustomer);

			DefaultSubMenu artikull = new DefaultSubMenu("Artikull");
			DefaultMenuItem editArtikull = new DefaultMenuItem("Edit");
			editArtikull.setCommand("#{menuBean.navigateEditArtikull}");
			artikull.addElement(editArtikull);
			
			DefaultSubMenu workLog = new DefaultSubMenu("WorkLog");
			DefaultMenuItem openWorkLog = new DefaultMenuItem("Open");
			openWorkLog.setCommand("#{menuBean.navigateOpenWorkLog}");
			/*DefaultMenuItem editWorkLog = new DefaultMenuItem("Edit");
			editWorkLog.setCommand("#{menuBean.navigateEditWorkLog}");*/
			workLog.addElement(openWorkLog);
			//workLog.addElement(editWorkLog);

			DefaultSubMenu taskSheet = new DefaultSubMenu("TaskSheet");
			DefaultMenuItem openSheet = new DefaultMenuItem("Open");
			openSheet.setCommand("#{menuBean.navigateOpenSheet}");
			/*DefaultMenuItem editSheet = new DefaultMenuItem("Edit");
			editSheet.setCommand("#{menuBean.navigateEditSheet}");*/
			taskSheet.addElement(openSheet);
			//taskSheet.addElement(editSheet);

			DefaultSubMenu resource = new DefaultSubMenu("Resourset");
			DefaultMenuItem open = new DefaultMenuItem("Open");
			open.setCommand("#{menuBean.navigateOpenResource}");
			DefaultMenuItem edit = new DefaultMenuItem("Edit");
			edit.setCommand("#{menuBean.navigateEditResource}");
			DefaultMenuItem allPreventive = new DefaultMenuItem("All prevetnive");
			allPreventive.setCommand("#{menuBean.navigateAllElement}");
			DefaultMenuItem pasqyra = new DefaultMenuItem("Kosto/Preventive");
			pasqyra.setCommand("#{menuBean.navigateKostoResource}");
			DefaultMenuItem pasqyraprev = new DefaultMenuItem("Kosto/Resource");
			pasqyraprev.setCommand("#{menuBean.navigateKostoPreventive}");
			DefaultMenuItem pasqyraallres = new DefaultMenuItem("Kosto/AllResource");
			pasqyraallres.setCommand("#{menuBean.navigateKostoAllResources}");
			DefaultSubMenu task = new DefaultSubMenu("Task");
			DefaultMenuItem taskEdit = new DefaultMenuItem("Edit");
			taskEdit.setCommand("#{menuBean.navigateEditTask}");
			task.addElement(taskEdit);
			DefaultSubMenu resourceType = new DefaultSubMenu("ResourceType");
			DefaultMenuItem resourceTypeEdit = new DefaultMenuItem("Edit");
			resourceTypeEdit.setCommand("#{menuBean.navigateEditResourceType}");
			resourceType.addElement(resourceTypeEdit);
			resource.addElement(open);
			resource.addElement(edit);
			resource.addElement(allPreventive);
			resource.addElement(pasqyra);
			resource.addElement(pasqyraprev);
			resource.addElement(pasqyraallres);
			resource.addElement(task);
			resource.addElement(resourceType);

			
		
			
			

			DefaultSubMenu upload = new DefaultSubMenu("Upload");
			DefaultMenuItem uploadPivotTable = new DefaultMenuItem(
					"Preventivat");
			uploadPivotTable.setCommand("#{menuBean.navigateUploadPivotTable}");
			DefaultMenuItem uploadPreventivin = new DefaultMenuItem(
					"Preventivin");
			uploadPreventivin
					.setCommand("#{menuBean.navigateUploadPreventivin}");
			DefaultMenuItem uploadTracks = new DefaultMenuItem(
					"Tracks");
			uploadTracks
					.setCommand("#{menuBean.navigateUploadTracks}");
			DefaultMenuItem uploadArticles = new DefaultMenuItem("Artikujt");
			uploadArticles.setCommand("#{menuBean.navigateUploadArtikujt}");
			DefaultMenuItem uploadCustomers = new DefaultMenuItem("Klientet");
			uploadCustomers.setCommand("#{menuBean.navigateUploadKlientet}");
			DefaultMenuItem uploadWorkLog = new DefaultMenuItem("WorkLog");
			uploadWorkLog.setCommand("#{menuBean.navigateUploadWorkLog}");
			DefaultMenuItem uploadtaskSheet = new DefaultMenuItem("TaskSheet");
			uploadtaskSheet.setCommand("#{menuBean.navigateUploadTaskSheet}");
			DefaultMenuItem uploadtaskResources = new DefaultMenuItem("Resources");
			uploadtaskResources.setCommand("#{menuBean.navigateUploadResources}");

			upload.addElement(uploadPivotTable);
			upload.addElement(uploadPreventivin);
			upload.addElement(uploadTracks);
			upload.addElement(uploadArticles);
			upload.addElement(uploadCustomers);
			upload.addElement(uploadWorkLog);
			upload.addElement(uploadtaskSheet);
			upload.addElement(uploadtaskResources);

			this.menuModel.addElement(preventivat);

			this.menuModel.addElement(users);
			this.menuModel.addElement(artikull);
			this.menuModel.addElement(customers);
			this.menuModel.addElement(workLog);
			this.menuModel.addElement(taskSheet);
			this.menuModel.addElement(resource);
			this.menuModel.addElement(upload);

		} else {

			DefaultSubMenu preventivat = new DefaultSubMenu("Preventivat");
			DefaultMenuItem openPreventive = new DefaultMenuItem("Open");
			openPreventive.setCommand("#{menuBean.navigateOpenPreventive}");
			preventivat.addElement(openPreventive);
			DefaultMenuItem editPreventive = new DefaultMenuItem("Edit");
			editPreventive.setCommand("#{menuBean.navigateEditimPrevent}");
			preventivat.addElement(editPreventive);
			DefaultMenuItem logOut = new DefaultMenuItem("Logout");
			logOut.setCommand("#{menuBean.navigateLogout}");
			preventivat.addElement(logOut);

			DefaultSubMenu unit = new DefaultSubMenu("Unit");
			DefaultMenuItem editUnit = new DefaultMenuItem("Edit");
			editUnit.setCommand("#{menuBean.navigateEditUnit}");
			unit.addElement(editUnit);

			DefaultSubMenu customers = new DefaultSubMenu("Customers");
			DefaultMenuItem editCustomer = new DefaultMenuItem("Edit");
			editCustomer.setCommand("#{menuBean.navigateEditCustomers}");
			customers.addElement(editCustomer);

			DefaultSubMenu artikull = new DefaultSubMenu("Artikull");
			DefaultMenuItem editArtikull = new DefaultMenuItem("Edit");
			editArtikull.setCommand("#{menuBean.navigateEditArtikull}");
			artikull.addElement(editArtikull);
			
			DefaultSubMenu workLog = new DefaultSubMenu("WorkLog");
			DefaultMenuItem openWorkLog = new DefaultMenuItem("Open");
			openWorkLog.setCommand("#{menuBean.navigateOpenWorkLog}");
			/*DefaultMenuItem editWorkLog = new DefaultMenuItem("Edit");
			editWorkLog.setCommand("#{menuBean.navigateEditWorkLog}");*/
			workLog.addElement(openWorkLog);
			//workLog.addElement(editWorkLog);

			DefaultSubMenu taskSheet = new DefaultSubMenu("TaskSheet");
			DefaultMenuItem openSheet = new DefaultMenuItem("Open");
			openSheet.setCommand("#{menuBean.navigateOpenSheet}");
			/*DefaultMenuItem editSheet = new DefaultMenuItem("Edit");
			editSheet.setCommand("#{menuBean.navigateEditSheet}");*/
			taskSheet.addElement(openSheet);
			//taskSheet.addElement(editSheet);

			DefaultSubMenu resource = new DefaultSubMenu("Resourset");
			DefaultMenuItem open = new DefaultMenuItem("Open");
			open.setCommand("#{menuBean.navigateOpenResource}");
			DefaultMenuItem edit = new DefaultMenuItem("Edit");
			edit.setCommand("#{menuBean.navigateEditResource}");
			DefaultSubMenu task = new DefaultSubMenu("Task");
			DefaultMenuItem taskEdit = new DefaultMenuItem("Edit");
			taskEdit.setCommand("#{menuBean.navigateEditTask}");
			task.addElement(taskEdit);
			DefaultSubMenu resourceType = new DefaultSubMenu("ResourceType");
			DefaultMenuItem resourceTypeEdit = new DefaultMenuItem("Edit");
			resourceTypeEdit.setCommand("#{menuBean.navigateEditResourceType}");
			resourceType.addElement(resourceTypeEdit);
			resource.addElement(open);
			resource.addElement(edit);
			resource.addElement(task);
			resource.addElement(resourceType);

			this.menuModel.addElement(preventivat);
			this.menuModel.addElement(unit);
			this.menuModel.addElement(customers);
			this.menuModel.addElement(artikull);
			this.menuModel.addElement(workLog);
			this.menuModel.addElement(taskSheet);
			this.menuModel.addElement(resource);
		}

	}

	public MenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public String navigateOpenPreventive() {
		return "homeUser.xhtml?faces-redirect=true";

	}

	public String navigateEditimPrevent() {
		return "editPreventives.xhtml?faces-redirect=true";
	}

	public String navigateEditUsers() {
		return "editUser1.xhtml?faces-redirect=true";

	}

	public String navigateUploadPivotTable() {
		return "uploadPreventives.xhtml?faces-redirect=true";
	}

	public String navigateUploadPreventivin() {
		return "uploadOrders.xhtml?faces-redirect=true";

	}
	
	public String navigateUploadTracks() {
		return "uploadTracks.xhtml?faces-redirect=true";

	}

	public String navigateUploadArtikujt() {
		return "uploadArticles.xhtml?faces-redirect=true";

	}

	public String navigateUploadKlientet() {
		return "uploadCustomers.xhtml?faces-redirect=true";
	}
	
	public String navigateUploadWorkLog(){
		return "uploadWorkLog.xhtml?faces-redirect=true";
	}
	
	
	public String navigateUploadTaskSheet(){
		return "uploadTaskSheet.xhtml?faces-redirect=true";
	}
	
	public String navigateUploadResources(){
		return "uploadResources.xhtml?faces-redirect=true";
	}

	public String navigateLogout() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return "/login.xhtml?faces-redirect=true";

	}

	public String navigateEditUnit() {
		return "editUnit.xhtml?faces-redirect=true";
	}

	public String navigateEditCustomers() {
		return "editCustomer.xhtml?faces-redirect=true";

	}
	
	public  String navigateOpenWorkLog(){
		return "workLogUser.xhtml?faces-redirect=true";
	}
	
	
	/*public  String navigateEditWorkLog(){
		return "editWorkLog.xhtml?faces-redirect=true";
	}
*/
	public String navigateOpenSheet() {
		return "taskSheetUser.xhtml?faces-redirect=true";
	}

	/*public String navigateEditSheet() {
		return "editTaskSheet.xhtml?faces-redirect=true";
	}*/

	public String navigateOpenResource() {
		return "resourcesUser.xhtml?faces-redirect=true";
	}

	public String navigateEditResource() {
		return "editResource.xhtml?faces-redirect=true";
	}
	
	public String navigateKostoResource(){
		return "standing.xhtml?faces-redirect=true";
	}
	
	public String navigateKostoAllResources(){
		return "standingAllResources.xhtml?faces-redirect=true";
	}
	
	public String navigateAllElement(){
		return "allElementPreventiveStand.xhtml?faces-redirect=true";
	}
	
	public String navigateKostoPreventive(){
		return "standingPerPreventive.xhtml?faces-redirect=true";
	}

	public String navigateEditTask() {
		return "editTask.xhtml?faces-redirect=true";
	}

	public String navigateEditResourceType() {
		return "editResourceType.xhtml?faces-redirect=true";
	}

	public String navigateEditArtikull() {
		return "editArtikull.xhtml?faces-redirect=true";

	}

}
