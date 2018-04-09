package al.mili.preventive.db.model.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("custom.codeValidator")
public class CodeValidator implements Validator {

	private static final String code_PATTERN = "([A-Z0-9\\-]+)?";
	private Pattern pattern;
	private Matcher matcher;

	public CodeValidator() {
		pattern = Pattern.compile(code_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		matcher = pattern.matcher(value.toString());

		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage("Code validation failed",
					"Invalid code number.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

}
