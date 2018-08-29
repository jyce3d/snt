package be.sdlg.snt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
	private static final String PASSWORD_PATTERN = 
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&]).{6,20})";
	
	  private Pattern pattern;

	  public PasswordValidator() {
		  this.pattern = Pattern.compile(PASSWORD_PATTERN);
	  }
	  
	  public boolean validate(final String password){
		 Matcher matcher = pattern.matcher(password);
		 return matcher.matches();
	  }


}
