package al.mili.preventive.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.User;

@ManagedBean(name = "userBeanController")
@SessionScoped
public class UserBeanController implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1188107938934540383L;
	private User user;
	// private List<User> users;
	private String userName;
	private String password;

	@ManagedProperty("#{userBeanService}")
	private UserBeanService userBeanService;

	@PostConstruct
	public void init() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserBeanService getUserBeanService() {
		return userBeanService;
	}

	public void setUserBeanService(UserBeanService userBeanService) {
		this.userBeanService = userBeanService;
	}

	public String loginControll() {
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		boolean loggedIn = false;
		this.user = userBeanService.getUser(this.userName, this.password);
		if (this.user != null) {
			loggedIn = true;
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("user", user);

			return "homeUser.xhtml?faces-redirect=true";

		}

		else {

			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Loggin Error", "Invalid credentials");
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		context.addCallbackParam("loggedIn", loggedIn);

		return "";
	}

}
