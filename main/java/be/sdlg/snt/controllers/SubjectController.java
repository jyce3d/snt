package be.sdlg.snt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import be.sdlg.snt.AdministrationSvc;
import be.sdlg.snt.ControllerAdvice;
import be.sdlg.snt.EDCSvc;
import be.sdlg.snt.model.ClinicalData;
import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.Grant;
import be.sdlg.snt.model.Location;
import be.sdlg.snt.model.SubjectData;


@Controller
@Transactional
@RequestMapping("secure/subject.htm")
public class SubjectController {
	private static final Logger logger = LoggerFactory
			.getLogger(SubjectController.class);

	@Autowired
	private EDCSvc edcService;
	@Autowired
	private AdministrationSvc administrationService;

	@RequestMapping(method = RequestMethod.GET)
	public String ctrlSubject( Locale locale, Model model,  HttpServletRequest request) {
		SubjectData subject =null;
		if (request.getParameter("id") == null) {
			subject = new SubjectData();
			subject.setActive(new Long(ClinicalData.ACTIVE_YES));
		}
		else {
			Long subjectId = new Long(request.getParameter("id"));
			subject =edcService.getSubjectDataById(subjectId);
		}
		String studyName = (String) request.getSession().getAttribute("studyName");
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long locationId = (Long) request.getSession().getAttribute("locationId");
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		
		String isDEA = administrationService.hasGrant( studyId, locationId, userId, new Long(Grant.DATA_ENTRY) ) ? "t" : "f";
		model.addAttribute("isDEA", isDEA);
		Long selectedLocationId;
		List<Location> locationList = administrationService.getStudyLocationByStudyByUser(studyId, userId);	
		if (subject.getId() == null)
			 selectedLocationId = locationList.iterator().next().getId();
		else 
			selectedLocationId = subject.getSiteRef().getId();
		List<DBUser> dbUserList = administrationService.getUserByStudyByLocation(studyId, selectedLocationId, new Long(Grant.INVESTIGATOR));
		List<Long> statusList = new ArrayList<Long>();
		statusList.add(new Long(SubjectData.SUBJECT_SCREENING));
		statusList.add(new Long(SubjectData.SUBJECT_ENROLLED));
		statusList.add(new Long(SubjectData.SUBJECT_DROPPEDOUT));
		statusList.add(new Long(SubjectData.SUBJECT_WITHDRAWN));

		model.addAttribute("locationList", locationList);
		model.addAttribute("investigatorList", dbUserList);
		model.addAttribute("statusList", statusList);
		model.addAttribute("subject",subject);
		return "subject";
		
	}
	@RequestMapping(method = RequestMethod.POST)
	public String addSubject(Model model, HttpServletRequest request) {
		SubjectData subject =null;
		if (request.getParameter("id").equals(""))
			subject = new SubjectData();
		else {
			Long subjectId = new Long(request.getParameter("id"));
			subject =edcService.getSubjectDataById(subjectId);
		}
		String studyName = (String) request.getSession().getAttribute("studyName");
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long locationId = (Long) request.getSession().getAttribute("locationId");
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		Long siteId = locationId;
		Long investigatorId = userId;
		String subjectKey = request.getParameter("subjectKey");
		//TODO: perform validation on the SubjectKey
		// check if the user is a DEA
		// if yes get the siteId and the investigatorId
		if (administrationService.hasGrant( studyId, locationId, userId, new Long(Grant.DATA_ENTRY) )) {
			siteId = new Long(request.getParameter("cboSite"));
			investigatorId = new Long(request.getParameter("cboInvestigator"));
		}
		Long active= request.getParameter("active").equals("t") ? new Long(ClinicalData.ACTIVE_YES) : new Long(ClinicalData.ACTIVE_NO);
		Long status = new Long(request.getParameter("cboStatus"));
		//TODO: add the reason for update
		String reasonForUpdate = request.getParameter("reasonForUpdate");
		
		try {
			if (subject.getId()==null)
				subject = edcService.createSubjectData(subjectKey,studyId, locationId, userId, siteId, investigatorId  );
			else
				edcService.updateSubject(subject.getId(), subjectKey, active, status, reasonForUpdate, studyId, locationId, userId, siteId, investigatorId);
			return "redirect:studyMenu.htm";
		} catch(Exception e) {
			return "technicalError";
		}
	}
	


}
