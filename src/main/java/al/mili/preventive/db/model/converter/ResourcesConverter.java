package al.mili.preventive.db.model.converter;

import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import al.mili.preventive.client.ResourcesBeanService;
import al.mili.preventive.db.model.Resources;

@FacesConverter("resourcesConverter")
public class ResourcesConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext fc, UIComponent component,
			String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				/*ResourcesBeanService resourceService = (ResourcesBeanService) fc
						.getCurrentInstance().
						getExternalContext().getRequestMap().get("resourcesBeanService");*/
				Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
				ResourcesBeanService resourceService=(ResourcesBeanService)viewMap.get("resourcesBeanService");
				return resourceService.getResources().stream()
						.filter(s -> s.getResourceId() == Integer.parseInt(value))
						.collect(Collectors.toList()).get(0);

			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Conversion Error",
						"Not a valid Resource."));
			}
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object object) {
		if (object != null && object instanceof Resources) {

			return new Integer(((Resources) object).getResourceId()).toString();
		}
		return null;
	}
}
