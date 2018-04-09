package al.mili.preventive.db.model.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("statusJobConverter")
public class StatusJobConverter implements Converter{
	@Override
		public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	        if(value != null && value.trim().length() > 0) {
	            try {
	                Object object = value;
	            	return object;
	                		
	               
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid statusJob."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 @Override
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	          
	           return object.toString();
	        }
	        else {
	            return null;
	        }
	    }

		
}
