package al.mili.preventive.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import al.mili.preventive.db.model.User;
import al.mili.preventive.db.service.UserDbService;

@ManagedBean(name = "complexMasterDetailController")
@RequestScoped
public class ComplexMasterDetailController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1193579500817332649L;

	private List<UserBean> userBeans;
	private List<User> users;
	private UserBean selectedUser;
	private UserDbService userDbService;
	
	
	
	private UserBean userBean;
	@PostConstruct
	public void init() {

		this.userBean = new UserBean();
		this.userDbService = new UserDbService();
		users = userDbService.getUsers();
		this.userBeans = convertUserList(users).stream()
				.sorted(Comparator.comparing(UserBean::getUserId))
				.collect(Collectors.toList());
	}

	public List<UserBean> getUserBeans() {
	
		return this.userBeans;
	}

	public void setUserBeans(List<UserBean> userBeans) {
		this.userBeans = userBeans;
	}

	
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}


	public String deleteUser() {
		userDbService.delete(selectedUser.getUserName());

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"User " + selectedUser.getUserName() + " has benn deleted", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

		return null;
	}

	public String addUser() {

		userDbService.addUser(userBean.getEmri(), userBean.getMbiemri(),
				userBean.getUserName(), userBean.getPassword());

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"User " + userBean.getUserName() + " has benn added", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

		return null;
	}
	
	
	public void onCellEdit(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			DataTable s = (DataTable) event.getSource();
			UserBean userBean = (UserBean) s.getRowData();
			userDbService.update(userBean.getUserId(),
					userBean.getEmri(), userBean.getMbiemri(),
					userBean.getUserName(), userBean.getPassword());
			
			FacesMessage msg = new FacesMessage("User u Editua", "User "
					+ userBean.getUserName() + " u editua");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			
		}
		
		
	
	}

	public void initDialog() {
		this.userBean.setEmri("");
		this.userBean.setMbiemri("");
		this.userBean.setUserName("");
		this.userBean.setPassword("");

		RequestContext.getCurrentInstance().reset(":userForm:addUser");
	}

	
	
	private List<UserBean> convertUserList(List<User> list) {
		List<UserBean> listUserBean = new ArrayList<UserBean>();
		for (User user : list) {
			UserBean userBean = new UserBean();
			userBean.setUserId(user.getUserId());
			userBean.setEmri(user.getEmri());
			userBean.setMbiemri(user.getMbiemri());
			userBean.setUserName(user.getUserName());
			userBean.setPassword(user.getPassword());
			listUserBean.add(userBean);
		}
		return listUserBean;
	}

	

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserBean getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserBean selectedUser) {
		this.selectedUser = selectedUser;
	}

	public UserDbService getUserDbService() {
		return userDbService;
	}

	public void setUserDbService(UserDbService userDbService) {
		this.userDbService = userDbService;
	}

}
