package al.mili.preventive.db.model.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<Email, String>{
	private Pattern pattern;
	private static final String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
	private static final String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)+";
	private static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
	private static final String EMAIL_PATTERN = "^" + ATOM + "+(\\." + ATOM + "+)*@" 
                    + DOMAIN
                    + "|"
                    + IP_DOMAIN
                    + ")$";
	
	/*private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";*/
	@Override
	public void initialize(Email a) {
		 pattern = Pattern.compile(EMAIL_PATTERN);
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext cvc) {
		 if(value == null)
	            return true;
	        else
	            return pattern.matcher(value.toString()).matches();
		
	}

}
