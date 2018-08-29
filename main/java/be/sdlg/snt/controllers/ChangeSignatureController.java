package be.sdlg.snt.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import be.sdlg.snt.AdministrationSvc;
import be.sdlg.snt.model.DBUser;

@Controller
@RequestMapping("secure/changeSignature.htm")
public class ChangeSignatureController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private AdministrationSvc administrationService;

	@RequestMapping(method = RequestMethod.GET)
	public String ctrlChangeSignature(@RequestParam("id") Long userId,
			Model model) {
		DBUser user = administrationService.getUserById(Long.valueOf(userId));
		model.addAttribute("user",user);
		return "changeSignature";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveSignature(Model model, HttpServletRequest request) {
		boolean validationError = false;

		String userId = request.getParameter("id");
		String oldSignature = request.getParameter("oldSignature");
		// Validation
		String signature = request.getParameter("signature");
		DBUser user = administrationService.getUserById(Long.valueOf(userId));
		model.addAttribute("user",user);
		if (user==null) {
			validationError = true;
			logger.info("Error : Empty user Id");
		} else if (!administrationService.isSignatureEquals(user, oldSignature)) {
			validationError = true;
			model.addAttribute("oldSignatureNotMatch", "t");
			
		} else  if (signature == null || signature.trim().isEmpty()) {
				model.addAttribute("emptySignature", "t");
				validationError = true;
			} else  if (!request.getParameter("confirmSignature").equals(signature)) {
					model.addAttribute("signatureNotMatch", "t");
					validationError = true;
			} else {	
					 validationError = !administrationService.isValidSignature(
							user, signature);
					
					if (validationError) 
						model.addAttribute("invalidSignature", "t");
				
			}
		
		if (validationError)
			return "changeSignature";
		else {
			try {
				administrationService.updateUserProperties(user.getId(),
					null, signature, user.isEnabled(), user.getUserMail());
			} catch(Exception e) {
				e.printStackTrace();
				return "technicalError";
			}
			return "redirect:studyList.htm";
		}
	}

}
