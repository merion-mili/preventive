package al.mili.preventive.db.model.converter;

import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import al.mili.preventive.client.ResourceTypeBeanService;
import al.mili.preventive.client.TaskBeanService;
import al.mili.preventive.db.model.Task;

@FacesConverter("taskConverter")
public class TaskConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext fc, UIComponent component,
			String value) {
		if (value != null && value.trim().length() > 0) {
				try {
					
					Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
					TaskBeanService taskService=(TaskBeanService)viewMap.get("taskBeanService");
					/*TaskBeanService taskService = (TaskBeanService) fc.
							getCurrentInstance().
							getExternalContext().getRequestMap().get("taskBeanService");*/
					return taskService.getTasks().stream()
							.filter(s -> s.getTaskId() == Integer.parseInt(value))
							.collect(Collectors.toList()).get(0);

				} catch (NumberFormatException e) {
					throw new ConverterException(new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Conversion Error",
							"Not a valid Type."));
				}
			} else {
				return null;
			}
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent component,
			Object object) {
		if (object != null && object instanceof Task) {

			return new Integer(((Task) object).getTaskId()).toString();
		}
		return null;
	}

}
