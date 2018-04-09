package al.mili.preventive.client;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "language")
@SessionScoped
public class LanguageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2085743024424427163L;
	private String localeCode;
	private static Map<String, Object> countries;

	static {
		countries = new LinkedHashMap<String, Object>();
		countries.put("English", Locale.ENGLISH);
		countries.put("Italian", Locale.ITALIAN);

	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public  Map<String, Object> getCountries() {
		return countries;
	}

	public void countryLocaleCodeChanged(ValueChangeEvent e){
		String newLocaleValue = e.getNewValue().toString();
		
		for(Map.Entry<String, Object> entry:countries.entrySet()){
			if(entry.getValue().toString().equals(newLocaleValue)){
				FacesContext.getCurrentInstance()
    			.getViewRoot().setLocale((Locale)entry.getValue());
			}
		}
	}
	
}
