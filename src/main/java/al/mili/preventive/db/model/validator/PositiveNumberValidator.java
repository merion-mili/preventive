package al.mili.preventive.db.model.validator;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("positiveNumberValidator")
public class PositiveNumberValidator implements Validator {

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		try {
			if (new BigDecimal(value.toString()).signum() < 0 ) {
				FacesMessage msg = new FacesMessage("Validation failed.",
						"Number must be strictly positive");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		} catch (NumberFormatException ex) {
			FacesMessage msg = new FacesMessage("Validation failed.",
					"Not a number");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}
}
