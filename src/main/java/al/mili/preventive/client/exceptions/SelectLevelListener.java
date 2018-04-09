package al.mili.preventive.client.exceptions;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.extensions.component.masterdetail.SelectLevelEvent;


@ManagedBean
@RequestScoped
public class SelectLevelListener implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2485190288725231674L;
	private boolean errorOccured = false;

	public int handleNavigation(SelectLevelEvent selectLevelEvent) {
		if (errorOccured) {
			return 2;
		} else {
			return selectLevelEvent.getNewLevel();
		}
	}

	public void setErrorOccured(boolean errorOccured) {
		this.errorOccured = errorOccured;
	}
}
