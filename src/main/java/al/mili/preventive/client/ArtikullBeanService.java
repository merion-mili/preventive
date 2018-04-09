package al.mili.preventive.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.extensions.component.masterdetail.MasterDetail;

import al.mili.preventive.db.model.Artikull;
import al.mili.preventive.db.model.KompoziteArtikel;
import al.mili.preventive.db.service.ArtikullDbService;

@ManagedBean(name = "artikullBeanService")
@ViewScoped
public class ArtikullBeanService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 921402786643254250L;
	private List<Artikull> artikullList;
	private List<Artikull> artikullListByType;
	private ArtikullDbService artikullDbService;
	

	@PostConstruct
	public void init() {

		this.artikullDbService = new ArtikullDbService();
		this.artikullList = artikullDbService.getAllArtikull();
		this.artikullListByType = artikullDbService.getAllArtikullByType("a");

	}

	public List<Artikull> getAllArtikull() {
		return this.artikullList;

	}

	public List<KompoziteArtikel> getKompoziteArtikull() {
		List<KompoziteArtikel> kompoziteArtikull = new ArrayList<KompoziteArtikel>();
		if (this.artikullListByType != null) {
			for (Artikull artikull : this.artikullListByType) {
				KompoziteArtikel kompoartikull = new KompoziteArtikel();
				kompoartikull.setArtikull(artikull);
				kompoartikull.setQuantity(0);
				kompoziteArtikull.add(kompoartikull);
			}
		}
		return kompoziteArtikull;

	}
	


	public ArtikullDbService getArtikullDbService() {
		return artikullDbService;
	}

	public void delete(int artikullId) {
		try{
			 artikullDbService.delete(artikullId);
		}catch(ConstraintViolationException e){
			throw new ConstraintViolationException(
					"Delete nuk ekzekutohet", null,
					"Objekti eshte ne Preventive.");
		}catch (HibernateException e) {
			throw e;
		}
		
	}

	public void update(int artikullId, String name, String description,
			List<KompoziteArtikel> kompozimet, String type) {
		try {
			artikullDbService.update(artikullId, name, description, kompozimet, type);
		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");

		} catch (HibernateException e) {
			throw e;
		}
	}
	
	
	public void addArtikull(String name, String description,
			List<KompoziteArtikel> kompozimet, String type)
			throws HibernateException, ConstraintViolationException {
		try {
			artikullDbService.addArtikull(name, description, null, type);
		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");

		} catch (HibernateException e) {
			throw e;
		}
	}

	public void addKompozim(String name, String description,
			List<KompoziteArtikel> kompozimet, String type)
			throws HibernateException, ConstraintViolationException {
		try {
			artikullDbService.addKompozim(name, description, kompozimet, type);
		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationException(
					"Gabim ne entry:Objekti Ekziston.", null,
					"Duplicated Entry.");

		} catch (HibernateException e) {
			throw e;
		}
	}


	/*public void addArtikllOfKompozim(Artikull artikull, double quantity) {
		artikullDbService.addArtikullInKompozim(artikull, quantity);

	}
*/
	

	public void setArtikullDbService(ArtikullDbService artikullDbService) {
		this.artikullDbService = artikullDbService;
	}

	public void updateArtikull(Artikull artikull) {
		 getArtikullDbService().updateArtikull(artikull);
		
	}

	public Artikull getArtikull(int artikullId) {
		return getArtikullDbService().getArtikull(artikullId);
	}

	public KompoziteArtikel getKompoziteArtikull(int kompoid) {
		return getArtikullDbService().getKompoArtikull(kompoid);
	}

	/*public void updateKompo1(KompoziteArtikel artikull) {
		 getArtikullDbService().updateKompoziteArtikull(artikull);
		
	}*/
	
	public List<KompoziteArtikel> getArtikujtEKompozuar(int artikullId) {
		return  artikullDbService.getArtikujtEKompozuar(artikullId);
	}
	
	
	
	
}
