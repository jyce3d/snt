package be.sdlg.snt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import be.sdlg.snt.model.Authority;
import be.sdlg.snt.model.DBUser;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("secure/user.htm")
@Transactional
public class UserController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private AdministrationSvc administrationService;

	@RequestMapping(method = RequestMethod.GET)
	
	public String ctrlCreateUser(@RequestParam("id") Long userId, Model model) {
		DBUser user;

		if (userId == null || userId <= 0)
			user = new DBUser();
		else
			user = administrationService.getUserById(userId);
		List<Authority> authorityList = administrationService
				.getAuthorityList(false);
		List<String> userAuthorityList = new ArrayList<String>();
		for (Authority auth : user.getAuthorityList()) 
			userAuthorityList.add(auth.getAuthority());
			
		model.addAttribute("user", user);
		model.addAttribute("authorityList", authorityList);
		model.addAttribute("userAuthorityList", userAuthorityList);

		return "user";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addUser(Model model, HttpServletRequest request) {
		List<Authority> authorityList = administrationService
				.getAuthorityList(false);
		DBUser user = null;
		int validationError = 0;
	
		if (request.getParameter("id") == null
				|| request.getParameter("id").trim().isEmpty())
			user = new DBUser();
		else
			user = administrationService.getUserById(new Long(request
					.getParameter("id")));
		if (request.getParameter("userName") != null) {
			Pattern userNamePattern = Pattern.compile("^[a-z0-9_-]{3,15}$");
			Matcher matcher = userNamePattern.matcher(request
					.getParameter("userName"));
			if (matcher.matches())
				user.setName(request.getParameter("userName"));
			else {
				validationError++;
				model.addAttribute("invalidUserName", "t");
			}
		} else {
			validationError++;
			model.addAttribute("invalidUserName", "t");
		}
		user.setEnabled(request.getParameter("enabled") != null);

		user.setUserMail(request.getParameter("userMail"));


		model.addAttribute("authorityList", authorityList);
		// Password Validation
		String password = request.getParameter("password");
		
		if (request.getParameter("passwordDirty").equals("t")) {
			if (password.trim().isEmpty()) {
				model.addAttribute("emptyPassword", "t");
				validationError++;
			} else {
				if (!request.getParameter("confirmPassword").equals(password)) {
					model.addAttribute("passwordNotMatch", "t");
					validationError ++;
				} else {
					// TODO validate password
					 if (!administrationService.isValidPassword(
							user, password)) {
						validationError++;
						model.addAttribute("invalidPassword", "t");
					 }
					else
						user.setPassword(password);
				}

			}
		} else password = null;
		// Password Validation
		String signature = request.getParameter("signature");
		if (request.getParameter("signatureDirty").equals("t")) {
			if (signature.trim().isEmpty()) {
				model.addAttribute("emptySignature", "t");
				validationError++;
			} else {
				if (!request.getParameter("confirmSignature").equals(signature)) {
					model.addAttribute("signatureNotMatch", "t");
					validationError++;
				} else {
					// TODO validate password
					 if(!administrationService.isValidSignature(
							user, signature)) {
					    validationError++;
						model.addAttribute("invalidSignature", "t");
					 }
					else
						user.setSignature(signature);
				}

			}
		} else 
			signature=null;

		String userMail = request.getParameter("userMail");
		boolean invalidMail = true;
		if (userMail != null) {
			Pattern pattern = Pattern.compile("\\w+@\\w+\\.\\w{2,3}");
			Matcher matcher = pattern.matcher(userMail);
			if (matcher.matches())
				invalidMail = false;
		}
		if (invalidMail) {
			model.addAttribute("wrongUserMail", "t");
			validationError++;
		} else
			user.setUserMail(userMail);
		model.addAttribute("user", user);
		if (validationError > 0 )
			return "user";
		List<String> authorities = new ArrayList<String>();
		authorities.add("ROLE_USER");
		for (Authority auth : authorityList) {
			if (request.getParameter("chk_" + auth.getAuthority().toString()) != null)
				authorities.add(auth.getAuthority());
		}
		try {
			if (user.getId() == null) {

				administrationService.createUser(user.getName(), password,
						user.isEnabled(), user.getUserMail(), authorities);

			} else {

//				if (changedPassword && changedSignature )
					administrationService.updateUserProperties(user.getId(),
							password, signature, user.isEnabled(), user.getUserMail());
//				else
//					administrationService.updateUserProperties(user.getId(),
//							null, user.isEnabled(), user.getUserMail());

				administrationService.updateAuthorities(user.getId(),
						authorities);

				// updateUser(user,authorities, changedPassword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:users.htm";
	}
	

}
