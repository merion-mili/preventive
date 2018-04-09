package al.mili.preventive.db.model.converter;

import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import al.mili.preventive.client.ArtikullBeanService;
import al.mili.preventive.db.model.Artikull;

@FacesConverter("artikullConverter")
public class ArtikullConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && value.trim().length() > 0) {
            try {
            	Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            	ArtikullBeanService artikullService=(ArtikullBeanService)viewMap.get("artikullBeanService");
            	
            	/*ArtikullBeanService artikullService = (ArtikullBeanService) fc.getELContext().getELResolver().getValue(fc.getELContext(), null,"artikullBeanService");*/
                
                return artikullService.getAllArtikull().stream().filter(s->s.getArtikullId() == Integer.parseInt(value))
                		.collect(Collectors.toList()).get(0);
               
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid A."));
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
    	 if(object != null && object instanceof Artikull) {
      
           return new Integer(((Artikull)object).getArtikullId()).toString();
        }
        return null;
    }   
}
