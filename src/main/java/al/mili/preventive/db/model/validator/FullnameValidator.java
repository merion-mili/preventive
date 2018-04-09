package al.mili.preventive.db.model.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
@FacesValidator("custom.fullnameValidator")
public class FullnameValidator implements Validator{
	
	
	private static final String fullname_PATTERN ="(^[\\p{L} .'-]+$)?";
	private  Pattern pattern;
    private Matcher matcher;
    
    public FullnameValidator(){
          pattern = Pattern.compile(fullname_PATTERN);
    }

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		 matcher = pattern.matcher(value.toString());
		 
		 if(!matcher.matches()){
			FacesMessage msg = 
		                new FacesMessage("Name validation failed", 
		                        "Invalid Name.");
		            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		            throw new ValidatorException(msg);
		 }
		 
	  	
	}

}
