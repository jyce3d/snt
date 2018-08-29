package be.sdlg.snt.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import be.sdlg.snt.AdministrationSvc;
import be.sdlg.snt.model.Authority;
import be.sdlg.snt.model.DBProper;
import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.Grant;
import be.sdlg.snt.model.Location;

import javax.servlet.http.HttpServletRequest;

@Controller
@Transactional
@RequestMapping("secure/participants.htm")
public class ParticipantController {
	public class ParticipantModel {
		protected Long userId;
		protected String userName;
		protected Long grant;
		protected Long locationId;

		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Long getGrant() {
			return grant;
		}
		public void setGrant(Long grant) {
			this.grant = grant;
		}
		public Long getLocationId() {
			return locationId;
		}
		public void setLocationId(Long locationId) {
			this.locationId = locationId;
		}

		
		
	}
	private static final Logger logger = LoggerFactory
			.getLogger(ParticipantController.class);

	@Autowired
	private AdministrationSvc administrationService;

	@RequestMapping(method = RequestMethod.GET)
	public String ctrlParticipants( Model model,  HttpServletRequest request) {

		Long locationId = new Long(request.getParameter("id"));
		Grant grant=null;
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		if (studyId == null || locationId == null )
			return "technicalError";
		List<DBProper> properList = administrationService.getProperList(studyId, locationId);
		Location location = administrationService.getLocation(locationId);
		
		List<DBUser> userList = administrationService.getUserList();
		List<ParticipantModel> pmList = new ArrayList<ParticipantModel>();
		for (DBUser user : userList) {
			ParticipantModel pm = new ParticipantModel();
			pm.setUserId(user.getId());
			pm.setUserName (user.getName());
			pm.setLocationId( locationId);
			pm.setGrant(null);
			for (DBProper prop : properList) {
				if (prop.getStudyUser().getUser().getId().equals(user.getId()) && prop.getStudyLocation().getLocation().getId().equals(locationId)) {
						while (prop.getGrantList().iterator().hasNext() ) {
							grant = prop.getGrantList().iterator().next();
							pm.setGrant(grant.getValue());
							break;
						}
				}			
			}
			pmList.add(pm);
		}

		List<Integer> grantList = new ArrayList<Integer>();
		grantList.add(Grant.INVESTIGATOR);
		grantList.add(Grant.DATA_ENTRY);
		grantList.add(Grant.MONITOR);
		grantList.add(Grant.UNBLIND);

		model.addAttribute("location", location);
		model.addAttribute("participantList", pmList);
		model.addAttribute("grantList", grantList);

		return "participants";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addParticipants(Model model, HttpServletRequest request) {
		Long locationId = new Long(request.getParameter("id"));

		Long studyId = (Long) request.getSession().getAttribute("studyId");
		if (studyId == null )
			return "technicalError";

		List<DBUser> userList = administrationService.getUserList();
		List<Grant> grantList = new ArrayList<Grant>();
		
		for (DBUser user : userList) {
			grantList.clear();
			if (request.getParameter("chk_" + user.getId().toString()) != null) {
				Grant g = new Grant();
				g.setValue(new Long (request.getParameter("cbo_"+ user.getId().toString())));
				grantList.add(g);
			}
			try {
				administrationService.updateGrants(studyId, user.getId(), locationId, grantList);
			} catch (Exception e) {
				e.printStackTrace();
				return "technicalError";
			}
		}

		return "redirect:studyLocations.htm";
	}
	

}
