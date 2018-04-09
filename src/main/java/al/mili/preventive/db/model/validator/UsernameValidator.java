package al.mili.preventive.db.model.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
@FacesValidator("custom.usernameValidator")
public class UsernameValidator implements Validator{
	
	
	
	private static final String username_PATTERN ="(^[a-z0-9_-]{3,15}$)?";
	private  Pattern pattern;
    private Matcher matcher;
    
    public UsernameValidator(){
          pattern = Pattern.compile(username_PATTERN);
    }

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		 matcher = pattern.matcher(value.toString());
		 
		 if(!matcher.matches()){
			FacesMessage msg = 
					new FacesMessage("Username validation failed", 
	                        "Invalid username number.");
		                
		            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		            throw new ValidatorException(msg);
		 }
		 
	  	
	}

}
