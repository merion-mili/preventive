package al.mili.preventive.db.model.converter;

import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import al.mili.preventive.client.CustomerBeanService;
import al.mili.preventive.db.model.Customer;

@FacesConverter("customerConverter")
public class CustomerConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && value.trim().length() > 0) {
            try {
            	/*CustomerBeanService customerService = (CustomerBeanService) fc.getCurrentInstance().
            			getExternalContext().getRequestMap().get("customerBeanService");*/
            	Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            	CustomerBeanService customerService=(CustomerBeanService)viewMap.get("customerBeanService");
                return customerService.getCustomers().stream().filter(s->s.getCustomerId() == Integer.parseInt(value))
                		.collect(Collectors.toList()).get(0);
               
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Customer."));
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
    	 if(value != null && value instanceof Customer) {
    		 
    		return new Integer(((Customer)value).getCustomerId()).toString();
    		 
    		       
           
        }
        return null;
    }   
	
}
