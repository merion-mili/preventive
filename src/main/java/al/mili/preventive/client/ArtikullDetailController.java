package al.mili.preventive.client;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;

import al.mili.preventive.client.exceptions.SelectLevelListener;
import al.mili.preventive.db.model.Artikull;
import al.mili.preventive.db.model.KompoziteArtikel;

@ManagedBean(name = "artikullDetailController")
@ViewScoped
public class ArtikullDetailController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4790315681374369853L;
	private List<Artikull> listofArtikull;
	private List<KompoziteArtikel> artikujtJoTeKompozuar;
	private List<KompoziteArtikel> artikujtEKompozuar;

	private List<KompoziteArtikel> mergeArtikujt;

	private List<KompoziteArtikel> filteredArtikujtJoTeKompozuar;
	private List<KompoziteArtikel> selectedArtikullsTeKompozuar;
	private List<KompoziteArtikel> selectedArtikullsTeKompozuarForUpdate;

	private List<ArtikullBean> artikullBeans;
	private List<ArtikullBean> filteredArtikull;
	private ArtikullBean selectedArticle;

	private ArtikullBean artikullBean;
	private boolean disable;
	private boolean flag;
	private boolean flag1;

	private String total;

	@ManagedProperty("#{unitBeanService}")
	private UnitBeanService unitBeanService;

	@ManagedProperty("#{artikullBeanService}")
	ArtikullBeanService artikullBeanService;

	@PostConstruct
	public void init() {

		this.artikullBean = new ArtikullBean();

		listofArtikull = artikullBeanService.getAllArtikull();
		artikujtJoTeKompozuar = artikullBeanService.getKompoziteArtikull();
		this.artikullBeans = convertArtikullList(listofArtikull).stream()
				.sorted(Comparator.comparing(ArtikullBean::getArtikullId))
				.collect(Collectors.toList());

	}

	public List<ArtikullBean> getArtikullBeans() {
		return this.artikullBeans;
	}

	public void setArtikullBeans(List<ArtikullBean> artikullBeans) {
		this.artikullBeans = artikullBeans;
	}

	public void setArtikullBeanService(ArtikullBeanService artikullBeanService) {
		this.artikullBeanService = artikullBeanService;
	}

	public ArtikullBean getArtikullBean() {
		return artikullBean;
	}

	public void setArtikullBean(ArtikullBean artikullBean) {
		this.artikullBean = artikullBean;
	}

	public List<Artikull> getListofArtikull() {
		return listofArtikull;
	}

	public void setListofArtikull(List<Artikull> listofArtikull) {
		this.listofArtikull = listofArtikull;
	}

	public List<ArtikullBean> getFilteredArtikull() {
		return filteredArtikull;
	}

	public void setFilteredArtikull(List<ArtikullBean> filteredArtikull) {
		this.filteredArtikull = filteredArtikull;
	}

	public ArtikullBeanService getArtikullBeanService() {
		return artikullBeanService;
	}

	public void setCustomerBeanService(ArtikullBeanService artikullBeanService) {
		this.artikullBeanService = artikullBeanService;
	}

	public ArtikullBean getSelectedArticle() {
		return this.selectedArticle;
	}

	public void setSelectedArticle(ArtikullBean selectedArticle) {
		this.selectedArticle = selectedArticle;
	}

	public List<KompoziteArtikel> selectedKopoziteArtikulls() {

		selectedArtikullsTeKompozuar = new ArrayList<KompoziteArtikel>();
		for (KompoziteArtikel kompoItem : getArtikujtJoTeKompozuar()) {

			if (kompoItem.isSelected()) {

				selectedArtikullsTeKompozuar.add(kompoItem);

			}
			kompoItem.setSelected(false);
		}
		return selectedArtikullsTeKompozuar;
	}

	public List<KompoziteArtikel> selectedKopoziteArtikullsUpdate() {

		selectedArtikullsTeKompozuarForUpdate = new ArrayList<KompoziteArtikel>();
		for (KompoziteArtikel kompoItem : getArtikujtJoTeKompozuar()) {

			if (kompoItem.isSelected()) {

				selectedArtikullsTeKompozuarForUpdate.add(kompoItem);

			}
			kompoItem.setSelected(false);
		}
		return selectedArtikullsTeKompozuarForUpdate;
	}

	private List<ArtikullBean> convertArtikullList(List<Artikull> list) {
		List<ArtikullBean> listArtikullBean = new ArrayList<ArtikullBean>();
		for (Artikull artikull : list) {
			ArtikullBean artikullBean = new ArtikullBean();
			artikullBean.setArtikullId(artikull.getArtikullId());
			// artikullBean.setCode(artikull.getCode());
			artikullBean.setName(artikull.getName());
			artikullBean.setDescription(artikull.getDescription());
			artikullBean.setKompozimet(artikull.getKompozimet());
			artikullBean.setType(artikull.getType());
			listArtikullBean.add(artikullBean);
		}
		return listArtikullBean;
	}

	public void addArtikull() throws HibernateException,
			ConstraintViolationException {
		try {

			artikullBeanService.addArtikull(artikullBean.getName(),
					artikullBean.getDescription(), null, "a");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Artikull " + artikullBean.getName() + " u ruajt", null);
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

	public void addKompozim() throws HibernateException,
			ConstraintViolationException {

		try {
			List<KompoziteArtikel> kopoziteArtikulls = selectedKopoziteArtikulls();

			artikullBeanService.addKompozim(artikullBean.getName(),
					artikullBean.getDescription(), kopoziteArtikulls, "k");

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Artikull " + artikullBean.getName() + " u ruajt", null);
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

	public void deleteArticle(ArtikullBean artikullBean) {
		try {
			artikullBeanService.delete(artikullBean.getArtikullId());

			FacesMessage msg = new FacesMessage("Article  "
					+ artikullBean.getName() + " has been deleted");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (ConstraintViolationException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Delete nuk ekzekutohet",
					"Objekti eshte ne Preventive."));
		} catch (HibernateException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERROR! Access Database",
					"Failure to include data"));
		}

	}

	public void updateArtikull(ArtikullBean artikullBean)
			throws HibernateException, ConstraintViolationException {
		try {

			artikullBeanService.update(artikullBean.getArtikullId(),
					artikullBean.getName(), artikullBean.getDescription(),
					null, "a");

			FacesMessage msg = new FacesMessage("Artikull u Editua",
					"Artikulli " + artikullBean.getName() + " u editua");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");
		} catch (HibernateException e) {
			throw e;
		}
	}

	public void updateKompo(ArtikullBean artikullBean) {
		try {
			List<KompoziteArtikel> kopoziteArtikulls =selectedKopoziteArtikullsUpdate();

			artikullBeanService.update(artikullBean.getArtikullId(),
					artikullBean.getName(), artikullBean.getDescription(),
					kopoziteArtikulls, "k");

			FacesMessage msg = new FacesMessage("Artikull u Editua",
					"Artikulli " + artikullBean.getName() + " u editua");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");
		} catch (HibernateException e) {
			throw e;
		}
	}

	public void initDialog() {
		this.artikullBean.setName(null);
		this.artikullBean.setDescription(null);

		RequestContext.getCurrentInstance().reset(":artikullForm:addArtikle");
	}

	public void initUpdateDialog() {
		this.artikullBean.setName(null);
		this.artikullBean.setDescription(null);

		RequestContext.getCurrentInstance()
				.reset(":artikullForm:updateArtikle");
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);

			cell.setCellStyle(cellStyle);
		}
	}

	public UnitBeanService getUnitBeanService() {
		return unitBeanService;
	}

	public void setUnitBeanService(UnitBeanService unitBeanService) {
		this.unitBeanService = unitBeanService;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public List<KompoziteArtikel> getArtikujtJoTeKompozuar() {

		return artikujtJoTeKompozuar;
	}

	public void setArtikujtJoTeKompozuar(
			List<KompoziteArtikel> artikujtJoTeKompozuar) {
		this.artikujtJoTeKompozuar = artikujtJoTeKompozuar;
	}

	public List<KompoziteArtikel> getSelectedArtikullsTeKompozuar() {
		return selectedArtikullsTeKompozuar;
	}

	public void setSelectedArtikullsTeKompozuar(
			List<KompoziteArtikel> selectedArtikullsTeKompozuar) {
		this.selectedArtikullsTeKompozuar = selectedArtikullsTeKompozuar;
	}

	public List<KompoziteArtikel> getFilteredArtikujtJoTeKompozuar() {
		return filteredArtikujtJoTeKompozuar;
	}

	public void setFilteredArtikujtJoTeKompozuar(
			List<KompoziteArtikel> filteredArtikujtJoTeKompozuar) {
		this.filteredArtikujtJoTeKompozuar = filteredArtikujtJoTeKompozuar;
	}

	public List<KompoziteArtikel> getMergeArtikujt() {
		
		if (getSelectedArticle() == null) {
			return new ArrayList<KompoziteArtikel>();
		}
			List<KompoziteArtikel> selectedKopoziteArtikulls = getSelectedArticle()
					.getKompozimet();

			List<KompoziteArtikel> artikujtJoTeKompozuar2 = getArtikujtJoTeKompozuar();
			for (KompoziteArtikel x : selectedKopoziteArtikulls) {
				artikujtJoTeKompozuar2
						.removeIf(s -> s.getArtikull().getArtikullId() == x
								.getArtikull().getArtikullId());
				x.setSelected(true);
				artikujtJoTeKompozuar2.add(x);

			
		}
		return artikujtJoTeKompozuar2
				.stream()
				.sorted(Comparator.comparing(KompoziteArtikel::getQuantity)
						.reversed()).collect(Collectors.toList());

	}

	public String saveFailure(ArtikullBean artikullBean) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ELContext elContext = fc.getELContext();

		SelectLevelListener selectLevelListener;
		try {
			selectLevelListener = (SelectLevelListener) elContext
					.getELResolver().getValue(elContext, null,
							"selectLevelListener");
			selectLevelListener.setErrorOccured(true);
		} catch (RuntimeException e) {
			throw new FacesException(e.getMessage(), e);
		}

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Person " + artikullBean.getName() + " could not be saved",
				null);
		FacesContext.getCurrentInstance().addMessage(null, message);

		return null;
	}

	public List<KompoziteArtikel> getSelectedArtikullsTeKompozuarForUpdate() {
		return selectedArtikullsTeKompozuarForUpdate;
	}

	public void setSelectedArtikullsTeKompozuarForUpdate(
			List<KompoziteArtikel> selectedArtikullsTeKompozuarForUpdate) {
		this.selectedArtikullsTeKompozuarForUpdate = selectedArtikullsTeKompozuarForUpdate;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setMergeArtikujt(List<KompoziteArtikel> mergeArtikujt) {
		this.mergeArtikujt = mergeArtikujt;
	}

	public List<KompoziteArtikel> getArtikujtEKompozuar() {
		if (this.selectedArticle == null) {
			return new ArrayList<KompoziteArtikel>();
		}
		this.artikujtEKompozuar = artikullBeanService
				.getArtikujtEKompozuar(selectedArticle.getArtikullId());
		calculateTotalPriceOfKompozim();
		return this.artikujtEKompozuar;

	}

	private String calculateTotalPriceOfKompozim() {
		double totalPrice = 0;

		for (KompoziteArtikel kompo : selectedArticle.getKompozimet()) {
			double quantity = kompo.getQuantity();
			double price = kompo.getPrice();
			totalPrice = totalPrice + (quantity * price);
		}

		this.total = new DecimalFormat("###,###.00").format(totalPrice);

		return this.total;

	}

	public void setArtikujtEKompozuar(List<KompoziteArtikel> artikujtEKompozuar) {
		this.artikujtEKompozuar = artikujtEKompozuar;
	}

}
