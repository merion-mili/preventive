package al.mili.preventive.db.model.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("custom.niptValidator")
public class NiptValidator implements Validator {

	private static final String nipt_PATTERN = "(^[A-Z]{1}\\d{8}[A-Z]{1}$)?";
	private Pattern pattern;
	private Matcher matcher;

	public NiptValidator() {
		pattern = Pattern.compile(nipt_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		matcher = pattern.matcher(value.toString());

		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage("Nipt validation failed",
					"Invalid nipt number.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

}
