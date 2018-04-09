package al.mili.preventive.db.model.converter;

import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import al.mili.preventive.client.PreventiveBeanService;
import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.model.Task;

@FacesConverter("preventiveConverter")
public class PreventiveConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if(value != null && value.trim().length() > 0) {
            try {
            	Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            	PreventiveBeanService preventiveService=(PreventiveBeanService)viewMap.get("preventiveBeanService");
            	
            	/*ArtikullBeanService artikullService = (ArtikullBeanService) fc.getELContext().getELResolver().getValue(fc.getELContext(), null,"resourcesBeanService");*/
                
                return preventiveService.getPreventives().stream().filter(s->s.getPreventiveId() == Integer.parseInt(value))
                		.collect(Collectors.toList()).get(0);
               
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid preventive"));
            }
        }
        else {
            return null;
        }
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object object) {
		if (object != null && object instanceof Preventive) {

			return new Integer(((Preventive) object).getPreventiveId()).toString();
		}
		return null;
	}

}
