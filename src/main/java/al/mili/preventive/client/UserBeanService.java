package al.mili.preventive.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.User;
import al.mili.preventive.db.model.UsersRole;
import al.mili.preventive.db.service.PreventiveDbService;
import al.mili.preventive.db.service.UserDbService;

@ManagedBean(name = "userBeanService", eager = true)
@SessionScoped
public class UserBeanService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6383651273430547408L;
	private List<User> users;
	private UserDbService userDbService;

	@PostConstruct
	public void init() {

		userDbService = new UserDbService();
		this.users = userDbService.getUsers();

	}

	public User getUser(String username, String password) {
		return userDbService.getUser(username, password);

	}

	public List<User> getUser() {
		
		return this.users;

	}

	public void addUser(String emri, String mbiemri, String userName,
			String password) {
		userDbService.addUser(emri, mbiemri, userName, password);
	}

	public boolean delete(String userName) {
		return userDbService.delete(userName);
	}

	public boolean update(int userId, String emri, String mbiemri,
			String userName, String password) {
		return userDbService.update(userId, emri, mbiemri, userName, password);
	}
	
	public List<User> getUserPerRole(int role){
		this.users = userDbService.getUserPerRole(role);
		return this.users;
	}
}
