package be.sdlg.snt;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

// Remark : Pointcut definition in AspectJ http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/aop.html#aop-pointcuts
@Aspect
public class ControllerAdvice {
	private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);
	public static String getFormattedDate (Date date, Locale locale, boolean time) {
		DateFormat dateFormat;
		if (time) 
			dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);		
		else
			dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		return dateFormat.format(date);
	}
	public static String getFormattedName(String firstName, String lastName, String login) {
		return login+" - "+firstName+", "+lastName;
	}
	public static String getFormattedLocation(String shortName, String name) {
		if (shortName != null && shortName.length()>0)
			return "["+shortName+"]"+" "+name;
		return name;
	}
	private void addDateToModel(Model model, Locale locale) {
		Date date = new Date();

		String formattedDate = getFormattedDate(date, locale, true);
		model.addAttribute("serverTime", formattedDate );	

	}
	private void addStandards(Model model, HttpServletRequest request) {
		String studyName = null;
		if ( (studyName = (String ) request.getSession().getAttribute("studyName"))!=null)
			model.addAttribute("studyName", studyName );
		if ( (studyName = (String ) request.getSession().getAttribute("locationName"))!=null)
			model.addAttribute("locationName", studyName );
		if ( (studyName = (String ) request.getSession().getAttribute("locationShortName"))!=null)
			model.addAttribute("locationShortName", studyName );
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		model.addAttribute("userName", auth.getName());
		model.addAttribute("userFirstName", request.getSession().getAttribute("userFirstName"));
		model.addAttribute("userLastName", request.getSession().getAttribute("userLastName"));

	}
	@Pointcut("execution(* be.sdlg.snt.controllers.*.ctrl*(..))")
	void ctrl() {
		
	}
	@Before("ctrl()")
	protected void beforeController(JoinPoint joinPoint) {
		Model model=null;
		Locale locale = null;
		HttpServletRequest request = null;
		logger.info("Enterring aop before");
		for( Object arg : joinPoint.getArgs() ) {
			if (arg instanceof Model ) 
				model = (Model) arg;
			if (arg instanceof Locale )
				locale = (Locale) arg;
			if (arg instanceof HttpServletRequest )
				request = (HttpServletRequest) arg;
			if (model !=null && locale !=null && request != null) break;
		}
		if (model !=null && locale !=null)
			addDateToModel(model, locale);
		if (model !=null && request != null)
			addStandards(model, request);
			
	}

}
