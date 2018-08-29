package be.sdlg.snt.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import be.sdlg.snt.AdministrationSvc;
import be.sdlg.snt.model.DBUser;

@Controller
@RequestMapping("secure/changePassword.htm")
public class ChangePasswordController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private AdministrationSvc administrationService;

	@RequestMapping(method = RequestMethod.GET)
	public String ctrlChangePassword(@RequestParam("id") Long userId,
			Model model) {
		DBUser user = administrationService.getUserById(Long.valueOf(userId));
		model.addAttribute("user",user);
		return "changePassword";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String savePassword(Model model, HttpServletRequest request) {
		boolean validationError = false;

		String userId = request.getParameter("id");
		String oldPassword = request.getParameter("oldPassword");
		// Validation
		String password = request.getParameter("password");
		DBUser user = administrationService.getUserById(Long.valueOf(userId));
		model.addAttribute("user",user);
		if (user==null) {
			validationError = true;
			logger.info("Error : Empty user Id");
		} else if (!administrationService.isPasswordEquals(user, oldPassword)) {
			validationError = true;
			model.addAttribute("oldPasswordNotMatch", "t");
			
		} else  if (password == null || password.trim().isEmpty()) {
				model.addAttribute("emptyPassword", "t");
				validationError = true;
			} else  if (!request.getParameter("confirmPassword").equals(password)) {
					model.addAttribute("passwordNotMatch", "t");
					validationError = true;
			} else {	
					 validationError = !administrationService.isValidPassword(
							user, password);
					
					if (validationError) 
						model.addAttribute("invalidPassword", "t");
				
			}
		
		if (validationError)
			return "changePassword";
		else {
			try {
				administrationService.updateUserProperties(user.getId(),
					password, null, user.isEnabled(), user.getUserMail());
			} catch(Exception e) {
				e.printStackTrace();
				return "technicalError";
			}
			return "redirect:studyList.htm";
		}
	}

}
