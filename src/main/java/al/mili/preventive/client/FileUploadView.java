package al.mili.preventive.client;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.sun.el.parser.ParseException;

import al.mili.preventive.db.service.FileUploadDbService;

@ManagedBean(name = "fileUpload")
@SessionScoped
public class FileUploadView implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4135097920896751113L;
	private UploadedFile file;
	private boolean disable = false;

	public UploadedFile getFile() {
		return this.file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public boolean isDisable() {

		return disable;
	}

	public void uploadPreventives(FileUploadEvent e) throws IOException {
		this.file = e.getFile();

		if (file != null) {
			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FileUploadDbService fileUploadService = new FileUploadDbService();

			fileUploadService.savePreventives(file.getInputstream());

		} else {
			FacesMessage message = new FacesMessage("Unssuccesful",
					"File is not uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	public void uploadOrders(FileUploadEvent e) throws IOException {

		this.file = e.getFile();

		if (file != null) {

			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FileUploadDbService fileUploadService = new FileUploadDbService();

			fileUploadService.saveOrder(file.getInputstream());

		} else {
			FacesMessage message = new FacesMessage("Unssuccesful",
					"File is not uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}
	
	public void uploadResources(FileUploadEvent e) throws IOException {

		this.file = e.getFile();

		if (file != null) {

			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FileUploadDbService fileUploadService = new FileUploadDbService();

			fileUploadService.saveResources(file.getInputstream());

		} else {
			FacesMessage message = new FacesMessage("Unssuccesful",
					"File is not uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}
	
	public void uploadArticles(FileUploadEvent e) throws IOException {

		this.file = e.getFile();

		if (file != null) {

			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FileUploadDbService fileUploadService = new FileUploadDbService();

			fileUploadService.saveArticles(file.getInputstream());

		} else {
			FacesMessage message = new FacesMessage("Unssuccesful",
					"File is not uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}
	
	public void uploadTracks(FileUploadEvent e) throws IOException {

		this.file = e.getFile();

		if (file != null) {

			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FileUploadDbService fileUploadService = new FileUploadDbService();

			fileUploadService.saveTracker(file.getInputstream());

		} else {
			FacesMessage message = new FacesMessage("Unssuccesful",
					"File is not uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}
	
	public void uploadWorkLog(FileUploadEvent e) throws IOException {

		this.file = e.getFile();

		if (file != null) {

			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FileUploadDbService fileUploadService = new FileUploadDbService();

			try {
				fileUploadService.saveWorkLog(file.getInputstream());
			} catch (ParseException e1) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "ERROR!",
						"Not correct time"));
			}

		} else {
			FacesMessage message = new FacesMessage("Unssuccesful",
					"File is not uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}
	
	public void uploadTaskSheet(FileUploadEvent e) throws IOException {

		this.file = e.getFile();

		if (file != null) {

			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FileUploadDbService fileUploadService = new FileUploadDbService();

			try {
				fileUploadService.saveTaskSheet(file.getInputstream());
			} catch (ParseException e1) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "ERROR!",
						"Not correct time"));
			}

		} else {
			FacesMessage message = new FacesMessage("Unssuccesful",
					"File is not uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}
	
	public void uploadCustomers(FileUploadEvent e) throws IOException {

		this.file = e.getFile();

		if (file != null) {

			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FileUploadDbService fileUploadService = new FileUploadDbService();

			fileUploadService.saveCustomers(file.getInputstream());

		} else {
			FacesMessage message = new FacesMessage("Unssuccesful",
					"File is not uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

}
