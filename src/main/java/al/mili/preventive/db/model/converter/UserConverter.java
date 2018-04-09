package al.mili.preventive.db.model.converter;

import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import al.mili.preventive.client.UserBeanService;
import al.mili.preventive.db.model.User;

@FacesConverter("userConverter")
public class UserConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && value.trim().length() > 0) {
            try {
            	UserBeanService userService = (UserBeanService) fc.getCurrentInstance().
            			getExternalContext().getSessionMap().get("userBeanService");
                return userService.getUser().stream().filter(s->s.getUserId() == Integer.parseInt(value))
                		.collect(Collectors.toList()).get(0);
               
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid User."));
            }
        }
        else {
            return null;
        }
	}
	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null && object instanceof User) {
			return new Integer(((User) object).getUserId()).toString();
		}
		return null;
	}
}
