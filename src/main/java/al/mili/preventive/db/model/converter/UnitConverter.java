package al.mili.preventive.db.model.converter;

import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import al.mili.preventive.client.UnitBeanService;
import al.mili.preventive.db.model.Unit;

@FacesConverter("unitConverter")
public class UnitConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && value.trim().length() > 0) {
            try {
            	UnitBeanService unitService = (UnitBeanService) fc.getCurrentInstance().
            			getExternalContext().getSessionMap().get("unitBeanService");
                return unitService.getUnits().stream().filter(s->s.getUnitId() == Integer.parseInt(value))
                		.collect(Collectors.toList()).get(0);
               
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Unit."));
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
    	 if(object != null && object instanceof Unit) {
      
           return new Integer(((Unit)object).getUnitId()).toString();
        }
        return null;
    }   
	
}
