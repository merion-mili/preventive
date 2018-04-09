package al.mili.preventive.db.model.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.text.WordUtils;

@FacesConverter("toUppercaseConverter")
public class ToUppercaseConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		if(submittedValue == null || submittedValue.trim().isEmpty()){
			return submittedValue;
		}
		 return WordUtils.capitalize(submittedValue);
		
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		 return (value != null) ? value.toString() : "";
		
	}

}
